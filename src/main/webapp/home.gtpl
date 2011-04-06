<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
  import us.mapr.users.GaeUser
  import org.springframework.security.core.context.SecurityContextHolder
  import org.springframework.security.core.Authentication

  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  GaeUser gaeUser = (GaeUser)authentication.getPrincipal();
%>
<html>
 <head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" href="/static/css/gae.css" type="text/css" />
   <title>Home Page</title>
 </head>
 <body>
 <div id="content">
    <h3>The Home Page</h3>
    <p>Welcome back ${user.nickname}.</p>
    <p>
    You can get to this page if you have authenticated and are a registered user.
    You are registered as
    ${gaeUser.forename} ${gaeUser.surname}.
    </p>
    <p>
    <a href="/friends">Friends</a>.
    </p>
    <p>
    <a href="/logout">Logout</a>.
    </p>
 </div>
 </body>
</html>