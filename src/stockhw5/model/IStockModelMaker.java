package stockhw5.model;

import java.util.List;
import java.util.Map;

public interface IStockModelMaker {

  User getUserFromUsername(String username);

  boolean isUserNameExists(String username);

  String generateUUID();

  boolean validateInflexibleTickerShare(String tickerShare);

  boolean isValidTicker(String inputTicker);

  boolean saveInflexibleStock(User user, String portfolioUuid, String ticker, String noOfShares);

  List<String> getPortfolioContents(User user, String portfolioUuid);

  List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date);

  Map<Integer, List<String>> calculateTotalValueOfAInflexiblePortfolio(String certainDate, User user, String portfolioUuid);

  List<String> getPortfoliosForUser(User user);

  boolean validatePortfolioUUID(String portfolioUuid, User user);

  boolean validateInflexibleUserPortfolioExternalPathAndContentsStructure(String filepath);

  String saveExternalInflexibleUserPortfolio(String filepath, User user);

  User saveUser(User user);

  boolean validateFlexibleTickerShare(String tickerShare);

  boolean saveFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date);

  boolean sellFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date);

  Map<Integer, List<String>> calculateTotalValueOfAFlexiblePortfolio(String certainDate, User user, String portfolioUuid);

  double calculateCostBasis(User user, String portfolioUuid, String date);

  boolean validateFlexibleUserPortfolioExternalPathAndContentsStructure(String filepath);

  String saveExternalFlexibleUserPortfolio(String filepath, User user);

  List<String> getFlexiblePortfolioPerformance(String date1, String date2, String portfolioUuid, User user);
}
