package stockHw4;

import java.util.List;

// processes the input from controller
public interface StockModel {

  User saveUser(User user);

  boolean isUserNameExists(String userName);

  boolean isValidTicker(String inputTicker);

  boolean validateTickerShare(String tickerShare);

  List<String> getAllPortfoliosFromUser(User user);

  boolean dumpTickerShare(User user, String portfolioUUID, String ticker, String shares);

  String generateUUID();


  boolean validatePortfolioUUID(String portfolioUUID, User user);

  List<String> getPortfolioContents(User user, String uuid);


  User getUserFromUsername(String username);

  List<String> calculateTotalValueOfAPortfolio(String certainDate, User user, String portfolioUUID);

  boolean validateUserPortfolioExternal(String filePath, User user);

  boolean saveExternalUserPortfolio(String filePath, User user);


}
