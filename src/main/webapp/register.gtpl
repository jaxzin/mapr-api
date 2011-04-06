<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/static/css/gae.css" type="text/css" />
<title>Registration</title>
</head>
<body>
<div id="content">
<p>
Welcome to Mapr.us, ${user.nickname}.
Please enter your registration details in order to use the application.
</p>
<p>
The data you enter here will be registered in the application's GAE data store, keyed under your unique
Google Accounts identifier. It doesn't have to be accurate. When you log in again, the information will be automatically
retrieved.
</p>

<form id="register" method="post"  action="/register">
  	<fieldset>
  		<label for="forename">
  		First Name:
 		</label><br />
  		<input id="forename" type="text" name="forename"/> <br />

  		<label for="surname">
  		Last Name:
 		</label><br />
  		<input id="surname" type="text" name="surname"/><br />
	</fieldset>
	<input type="submit" value="Register">
</form>
</body>
</div>
</html>