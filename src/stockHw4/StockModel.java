package stockHw4;

// processes the input from controller
public interface StockModel {

  User saveUser(User user);

  boolean isUserNameExists(String userName);

  boolean validateTicker(String inputTicker);

  boolean validateTickerShare(String tickerShare);

  String[] getAllPortfoliosFromUser(User user);

  void dumpTickerShares(User user, String portfolioUUID, String ticker, String shares);


}
