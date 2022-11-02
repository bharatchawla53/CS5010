package stockHw4.model;

import java.util.UUID;

/**
 * The User class represents a user object which contains user id and username.
 */
public class User {
  private final String id;
  private final String userName;

  /**
   * Returns a new UserBuilder object.
   *
   * @return UserBuilder object.
   */
  public static UserBuilder builder() {
    return new UserBuilder();
  }

  /**
   * Constructs a user object given id and username.
   *
   * @param id       user id.
   * @param userName username.
   */
  public User(String id, String userName) {
    this.id = id;
    this.userName = userName;
  }

  public String getId() {
    return this.id;
  }

  public String getUserName() {
    return this.userName;
  }

  /**
   * The UserBuilder class represents individual operations to set id and username.
   */
  public static class UserBuilder {
    private String id;
    private String userName;

    /**
     * Constructs an empty UserBuilder object.
     */
    public UserBuilder() {
    }

    private void userId() {
      this.id = UUID
              .randomUUID()
              .toString()
              .replace("-", "")
              .substring(0, 8);
    }

    public UserBuilder userName(String userName) {
      this.userName = userName;
      return this;
    }

    public User build() {
      this.userId(); // initialize a random id for this user
      return new User(id, userName);
    }
  }
}
