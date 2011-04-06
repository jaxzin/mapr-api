// example routes
/*
get "/blog/@year/@month/@day/@title", forward: "/WEB-INF/groovy/blog.groovy?year=@year&month=@month&day=@day&title=@title"
get "/something", redirect: "/blog/2008/10/20/something", cache: 2.hours
get "/book/isbn/@isbn", forward: "/WEB-INF/groovy/book.groovy?isbn=@isbn", validate: { isbn ==~ /\d{9}(\d|X)/ }
*/

// routes
get "/register",      forward: "/register.gtpl"
//get "/",              forward: "/landing.gtpl"
get "/home",          forward: "/home.gtpl"
//get "/disabled",      forward: "/disabled.gtpl"
get "/logout",        forward: "/logout.groovy"
get "/loggedout",     forward: "/loggedout.gtpl"
