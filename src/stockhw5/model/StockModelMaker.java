package stockhw5.model;

import java.util.List;
import java.util.Map;

public class StockModelMaker {

  private final StockModel inflexibleModel;
  private final FlexibleStockModel flexibleModel;

  public StockModelMaker() {
    inflexibleModel = new StockModelImpl();
    flexibleModel = new FlexibleStockModelImpl();
  }

  public User getUserFromUsername(String username) {
    return inflexibleModel.getUserFromUsername(username);
  }

  public boolean isUserNameExists(String username) {
    return inflexibleModel.isUserNameExists(username);
  }

  public String generateUUID() {
    return inflexibleModel.generateUUID();
  }

  public boolean validateInflexibleTickerShare(String tickerShare) {
    return inflexibleModel.validateTickerShare(tickerShare);
  }

  public boolean isValidTicker(String inputTicker) {
    return inflexibleModel.isValidTicker(inputTicker);
  }

  public boolean saveInflexibleStock(User user, String portfolioUuid, String ticker, String noOfShares) {
    return inflexibleModel.saveStock(user, portfolioUuid, ticker, noOfShares);
  }

  public List<String> getPortfolioContents(User user, String portfolioUuid) {
    return inflexibleModel.getPortfolioContents(user, portfolioUuid);
  }

  public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
    return flexibleModel.portfolioCompositionFlexible(portfolioUUID,user,date);
  }

  public Map<Integer, List<String>> calculateTotalValueOfAInflexiblePortfolio(String certainDate, User user, String portfolioUuid) {
    return inflexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
  }

  public List<String> getPortfoliosForUser(User user) {
    return inflexibleModel.getPortfoliosForUser(user);
  }

  public boolean validatePortfolioUUID(String portfolioUuid, User user) {
    return inflexibleModel.validatePortfolioUUID(portfolioUuid, user);
  }

  public boolean validateUserPortfolioExternalPathAndContentsStructure(String filepath) {
    return inflexibleModel.validateUserPortfolioExternalPathAndContentsStructure(filepath);
  }

  public String saveExternalUserPortfolio(String filepath, User user) {
    return inflexibleModel.saveExternalUserPortfolio(filepath, user);
  }

  public User saveUser(User user) {
    return inflexibleModel.saveUser(user);
  }

  public boolean validateFlexibleTickerShare(String tickerShare) {
    return flexibleModel.validateTickerShare(tickerShare);
  }

  public boolean saveFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date) {
    return flexibleModel.buyStockOnSpecificDate(user, portfolioUuid, ticker, noOfShares, date);
  }

  public boolean sellFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date) {
    return flexibleModel.sellStockOnSpecifiedDate(user, portfolioUuid, ticker, noOfShares, date);
  }

  public Map<Integer, List<String>> calculateTotalValueOfAFlexiblePortfolio(String certainDate, User user, String portfolioUuid) {
    return flexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
  }

  public double calculateCostBasis(User user, String portfolioUuid, String date) {
    return flexibleModel.calculateCostBasis(user, portfolioUuid, date);
  }
}
