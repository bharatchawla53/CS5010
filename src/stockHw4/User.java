package stockHw4;

import java.util.UUID;

public class User {
  private final String id;
  private final String userName;

  public static UserBuilder builder() {
    return new UserBuilder();
  }

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

  public static class UserBuilder {
    private String id;
    private String userName;

    public UserBuilder() {}

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
