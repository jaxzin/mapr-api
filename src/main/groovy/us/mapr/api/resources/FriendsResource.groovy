package us.mapr.api.resources

import com.google.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import org.codehaus.jackson.node.ObjectNode
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.consumer.OAuth2RestTemplate
import us.mapr.api.config.Facebook
import us.mapr.api.model.Friends
import us.mapr.api.model.Link

@Path("friends")
class FriendsResource {

  OAuth2RestTemplate facebookRestTemplate;


  @Inject
  FriendsResource(@Facebook OAuth2RestTemplate facebookRestTemplate) {
    this.facebookRestTemplate = facebookRestTemplate
  }

  @GET
  @Produces('application/json')
  Friends getFriends(@Context UriInfo uriInfo) {
    def json = facebookRestTemplate.getForObject("https://graph.facebook.com/me/checkins", ObjectNode.class);
    new Friends(
//        friends:
//        json.data.collect {
//          checkin ->
//          def location = checkin["place"]["location"]
//          String friendName = checkin["from"]["name"].textValue
//          def friendNameUriFriendly = friendName.toLowerCase().replace(" ", "-")

//          URI friendUri =
//              uriInfo.requestUriBuilder
//                      .path(friendName)
//                      .build()
//
//            new Friend(
//              name: friendName,
//              lastSeen:
//                new Checkin(
//                  where:
//                    new GeoLocation(
//                      category: "home",
//                      latitude: location.latitude?.doubleValue,
//                      longitude: location.longitude?.doubleValue,
//                      accuracy: location.accuracy?.doubleValue
//                    ),
//                  when: facebookDateTimeFormatter.parseDateTime(checkin["created_time"].textValue),
//                  source: CheckinSource.FACEBOOK
//                )
//            )
//        },
          // accumulate a Map<URI,Friend>
//          json.data.inject([:]) {
//            friends, checkin ->
//            def location = checkin.place.location
//            String friendName = checkin.from.name.textValue
//            def friendNameUriFriendly = friendName.toLowerCase().replace(" ", "-")
//
//            URI friendUri =
//                uriInfo.requestUriBuilder
//                        .path(friendNameUriFriendly)
//                        .build()
//
//            // Add the friend to the Map
//            friends[friendUri] =
//              new Friend(
//                name: friendName,
//                lastSeen:
//                  new Checkin(
//                    where:
//                      new GeoLocation(
//                        category: "home",
//                        latitude: location.latitude?.doubleValue,
//                        longitude: location.longitude?.doubleValue,
//                        accuracy: location.accuracy?.doubleValue
//                      ),
//                    when: facebookDateTimeFormatter.parseDateTime(checkin.created_time.textValue),
//                    source: CheckinSource.FACEBOOK
//                  )
//              )
//
//            friends
//          },
        links: [
            self: new Link(href: uriInfo.requestUri, op: HttpMethod.GET)
        ]
    )
  }
}
