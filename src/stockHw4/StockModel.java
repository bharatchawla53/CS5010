package stockHw4;

// processes the input from controller
public interface StockModel {

  User saveUser(User user);

  boolean isUserNameExists(String userName);
}
