package us.mapr.api.resources

import com.google.appengine.api.users.UserServiceFactory
import com.google.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import us.mapr.gae.security.GaeUserAuthentication
import us.mapr.users.AppRole
import us.mapr.users.GaeUser
import us.mapr.users.UserRegistry

@Path("register")
class RegisterResource {

  @Inject
  UserRegistry registry

//  @Inject
//  SecurityContext securityContext
//
//  @Inject
//  UserService userService

  @POST
  @Consumes("application/x-www-form-urlencoded")
  Response register(@FormParam("forename") String forename, @FormParam("surname") String surname, @Context UriInfo uriInfo) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    Authentication authentication = securityContext.getAuthentication()
    GaeUser currentUser = (GaeUser)authentication.getPrincipal();
    // Wrapping this EnumSet in a HashSet is necessary otherwise roles.add() will fail on GAE with IllegalAccessExceptio
    Set<AppRole> roles = new HashSet<AppRole>(EnumSet.of(AppRole.USER));

    if (UserServiceFactory.getUserService().isUserAdmin()) {
//    if (userService.isUserAdmin()) {
       roles.add(AppRole.ADMIN);
    }

    GaeUser user = new GaeUser(currentUser.getUserId(), currentUser.getNickname(), currentUser.getEmail(),
           forename, surname, roles, true);

    registry.registerUser(user);

    // Update the context with the full authentication
    SecurityContextHolder.getContext().setAuthentication(new GaeUserAuthentication(user, authentication.getDetails()));
//    securityContext.setAuthentication(new GaeUserAuthentication(user, authentication.getDetails()));

    Response.seeOther(uriInfo.baseUriBuilder.path("home").build()).build()
  }

}
