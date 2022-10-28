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

  public String generateUUID();


  public boolean validatePortfolioUUID(String portfolioUUID, User user);

  public


}
