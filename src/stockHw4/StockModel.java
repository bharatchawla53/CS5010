package stockHw4;

import java.util.UUID;

// processes the input from controller
public interface StockModel {

  User saveUser(User user);

  boolean isUserNameExists(String userName);

  boolean validateTicker(String inputTicker);

  boolean validateTickerShare(String tickerShare);

  String[] getAllPortfoliosFromUser(User user);

  void dumpTickerShare(User user, String portfolioUUID, String ticker, String shares);

  String generateUUID();


  boolean validatePortfolioUUID(String portfolioUUID, User user);

  String[]  getPortfolioContents(User user, String uuid);


  User getUserFromUsername(String username);


}
