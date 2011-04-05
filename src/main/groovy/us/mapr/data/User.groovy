package us.mapr.data

import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class User {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  Long id;

  @Persistent
  String userName;

  @Persistent
  String facebookOAuthToken

}
