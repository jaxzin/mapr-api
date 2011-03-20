package us.mapr.api.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import us.mapr.api.model.Friend

@Path("friends")
class FriendsResource {

  @GET
  @Produces('application/json')
  List<Friend> getFriends() {
    [
        new Friend(first: 'Brian', last: 'Jackson'),
        new Friend(first: 'Meridith', last: 'Jackson'),
        new Friend(first: 'Brad', last: 'Lage')
    ]
  }
}
