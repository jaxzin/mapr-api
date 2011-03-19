import us.mapr.api.model.Friend

request['privateName'] = 'Brian!!'

request['model'] = new Friend(first: 'Brian', last: 'Jackson');

response.contentType = 'application/json'

forward '/friends-json.gtpl'