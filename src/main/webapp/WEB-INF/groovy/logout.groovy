request.session.invalidate()
def logoutUrl = users.createLoginURL("/loggedout")

redirect logoutUrl

