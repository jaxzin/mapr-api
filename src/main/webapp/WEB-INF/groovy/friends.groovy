import us.mapr.api.model.Friend

request['model'] = [
        new Friend(first: 'Brian', last: 'Jackson'),
        new Friend(first: 'Meridith', last: 'Jackson'),
        new Friend(first: 'Brad', last: 'Lage')
]

forward '/friends-json.gtpl'