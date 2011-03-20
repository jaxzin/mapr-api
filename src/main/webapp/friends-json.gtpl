<% response.setContentType("application/json") %>
[<% request['model'].eachWithIndex { friend, i -> %>
  {
    'first': '${friend.first}',
    'last': '${friend.last}
  }${i!=request['model'].size()-1?',':''}
<%} %>]
