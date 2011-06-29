package us.mapr.api.resources

import com.google.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import org.codehaus.jackson.node.ObjectNode
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequest
import org.springframework.security.oauth2.consumer.OAuth2RestTemplate
import org.springframework.web.client.HttpMessageConverterExtractor
import org.springframework.web.client.RequestCallback
import us.mapr.api.config.Facebook
import us.mapr.api.model.*

@Path("users/my/friends")
class FriendsResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(FriendsResource.class);

  OAuth2RestTemplate facebookRestTemplate;


  @Inject
  FriendsResource(@Facebook OAuth2RestTemplate facebookRestTemplate) {
    this.facebookRestTemplate = facebookRestTemplate
  }

  @GET
  @Produces('application/json')
  Friends getFriends(@Context UriInfo uriInfo) {
    RequestCallback requestCallback = new RequestCallback() {
      void doWithRequest(ClientHttpRequest request) {
        LOGGER.info("{} {}",request.method, request.URI)
        request.headers.accept = [new MediaType("text","javascript")]
      }
    }

    HttpMessageConverterExtractor<ObjectNode> responseExtractor =
            new HttpMessageConverterExtractor<ObjectNode>(ObjectNode.class, facebookRestTemplate.getMessageConverters());

    def json = facebookRestTemplate.execute("https://graph.facebook.com/search?type=checkin", HttpMethod.GET, requestCallback, responseExtractor);
//    def json = facebookRestTemplate.getForObject("https://graph.facebook.com/search?type=checkin", ObjectNode.class);
    //noinspection GroovyAssignabilityCheck
    new Friends(
        friends:
          // accumulate a Map<URI,Friend>
          json.data.inject([:]) {
            friends, checkin ->
            def location = checkin.place.location
            String friendName = checkin.from.name.textValue
            def friendNameUriFriendly = friendName.toLowerCase().replace(" ", "-")

            String friendKey =
                uriInfo.requestUriBuilder
                        .path(friendNameUriFriendly)
                        .build()
                        .toString()

            def checkinWhen = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").parseDateTime(checkin.created_time.textValue)

            // Add the friend to the Map iff they don't have a younger checkin recorded already
            if(friends[friendKey] == null || friends[friendKey]?.lastSeen?.when?.isBefore(checkinWhen)) {
              friends[friendKey] =
                new Friend(
                  name: friendName,
                  lastSeen:
                    new Checkin(
                      where:
                        new GeoLocation(
                          category: "home",
                          latitude: location.latitude?.doubleValue,
                          longitude: location.longitude?.doubleValue,
                          accuracy: location.accuracy?.doubleValue
                        ),
                      when: checkinWhen/*,
                      source: CheckinSource.FACEBOOK*/
                    )
                )
            }

            friends
          },
        links: [
            self: new Link(href: uriInfo.requestUri, op: HttpMethod.GET)
        ]
    )
  }
}
