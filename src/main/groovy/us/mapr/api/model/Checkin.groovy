package us.mapr.api.model

import org.joda.time.DateTime

class Checkin {
  GeoLocation where
  DateTime when
  CheckinSource source
}
