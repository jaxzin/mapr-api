package us.mapr.api.resources

import com.google.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import org.codehaus.jackson.node.ObjectNode
import org.springframework.security.oauth2.consumer.OAuth2RestTemplate
import us.mapr.api.config.Facebook
import us.mapr.api.model.Friend

@Path("friends")
class FriendsResource {

  OAuth2RestTemplate facebookRestTemplate;


  @Inject
  FriendsResource(@Facebook OAuth2RestTemplate facebookRestTemplate) {
    this.facebookRestTemplate = facebookRestTemplate
  }

  @GET
  @Produces('application/json')
  List<Friend> getFriends() {
    def objectNode = facebookRestTemplate.getForObject("https://graph.facebook.com/me/friends", ObjectNode.class);
    def data = objectNode["data"]
    data.collect {new Friend(first: it["name"].getTextValue())}
  }
//    [
//        new Friend(first: 'Brian', last: 'Jackson'),
//        new Friend(first: 'Meridith', last: 'Jackson'),
//        new Friend(first: 'Brad', last: 'Lage')
//    ]
}
