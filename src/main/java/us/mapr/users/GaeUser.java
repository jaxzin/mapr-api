package us.mapr.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * Custom user object for the application.
 *
 * @author Luke Taylor
 */
public class GaeUser implements Serializable {
   private final String userId;
   private final String email;
   private final String nickname;
   private final String forename;
   private final String surname;
   private final Set<AppRole> authorities;
   private final boolean enabled;

   /**
     * Pre-registration constructor.
     *
     * Assigns the user the "NEW_USER" role only.
    * @param userId self-explanatory
    * @param nickname self-explanatory
    * @param email self-explanatory
    */
   public GaeUser(String userId, String nickname, String email) {
       this.userId = userId;
       this.nickname = nickname;
       this.authorities = EnumSet.of(AppRole.NEW_USER);
       this.forename = null;
       this.surname = null;
       this.email = email;
       this.enabled = true;
   }

   /**
     * Post-registration constructor
    * @param userId self-explanatory
    * @param nickname self-explanatory
    * @param email self-explanatory
    * @param forename self-explanatory
    * @param surname self-explanatory
    * @param authorities self-explanatory
    * @param enabled self-explanatory
    */
   public GaeUser(String userId, String nickname, String email, String forename, String surname, Set<AppRole> authorities, boolean enabled) {
       this.userId = userId;
       this.nickname = nickname;
       this.email = email;
       this.authorities = authorities;
       this.forename = forename;
       this.surname = surname;
       this.enabled= enabled;
   }

   public String getUserId() {
       return userId;
   }

   public String getNickname() {
       return nickname;
   }

   public String getEmail() {
       return email;
   }

   public String getForename() {
       return forename;
   }

   public String getSurname() {
       return surname;
   }

   public boolean isEnabled() {
       return enabled;
   }

   public Collection<AppRole> getAuthorities() {
       return authorities;
   }

   @Override
   public String toString() {
       return "GaeUser{" +
               "userId='" + userId + '\'' +
               ", nickname='" + nickname + '\'' +
               ", forename='" + forename + '\'' +
               ", surname='" + surname + '\'' +
               ", authorities=" + authorities +
               '}';
   }
}