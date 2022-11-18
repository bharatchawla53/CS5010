package stockhw5.model;

import java.util.List;
import java.util.Map;

/**
 * This class represents all the features supported for models including flexible and inflexible
 * portfolios.
 */
public class StockModelMaker implements IStockModelMaker {

  private final StockModel inflexibleModel;
  private final FlexibleStockModel flexibleModel;

  public StockModelMaker() {
    inflexibleModel = new StockModelImpl();
    flexibleModel = new FlexibleStockModelImpl();
  }

  @Override
  public User getUserFromUsername(String username) {
    return inflexibleModel.getUserFromUsername(username);
  }

  @Override
  public boolean isUserNameExists(String username) {
    return inflexibleModel.isUserNameExists(username);
  }

  @Override
  public String generateUUID() {
    return inflexibleModel.generateUUID();
  }

  @Override
  public boolean validateInflexibleTickerShare(String tickerShare) {
    return inflexibleModel.validateTickerShare(tickerShare);
  }

  @Override
  public boolean isValidTicker(String inputTicker) {
    return inflexibleModel.isValidTicker(inputTicker);
  }

  @Override
  public boolean saveInflexibleStock(User user, String portfolioUuid, String ticker, String noOfShares) {
    return inflexibleModel.saveStock(user, portfolioUuid, ticker, noOfShares);
  }

  @Override
  public List<String> getPortfolioContents(User user, String portfolioUuid) {
    return inflexibleModel.getPortfolioContents(user, portfolioUuid);
  }

  @Override
  public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
    return flexibleModel.portfolioCompositionFlexible(portfolioUUID, user, date);
  }

  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAInflexiblePortfolio(String certainDate, User user, String portfolioUuid) {
    return inflexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
  }

  @Override
  public List<String> getPortfoliosForUser(User user) {
    return inflexibleModel.getPortfoliosForUser(user);
  }

  @Override
  public boolean validatePortfolioUUID(String portfolioUuid, User user) {
    return inflexibleModel.validatePortfolioUUID(portfolioUuid, user);
  }

  @Override
  public boolean validateInflexibleUserPortfolioExternalPathAndContentsStructure(String filepath) {
    return inflexibleModel.validateUserPortfolioExternalPathAndContentsStructure(filepath);
  }

  @Override
  public String saveExternalInflexibleUserPortfolio(String filepath, User user) {
    return inflexibleModel.saveExternalUserPortfolio(filepath, user);
  }

  @Override
  public User saveUser(User user) {
    return inflexibleModel.saveUser(user);
  }

  @Override
  public boolean validateFlexibleTickerShare(String tickerShare) {
    return flexibleModel.validateTickerShare(tickerShare);
  }

  @Override
  public boolean saveFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date) {
    return flexibleModel.buyStockOnSpecificDate(user, portfolioUuid, ticker, noOfShares, date);
  }

  @Override
  public boolean sellFlexibleStock(User user, String portfolioUuid, String ticker, String noOfShares, String date) {
    return flexibleModel.sellStockOnSpecifiedDate(user, portfolioUuid, ticker, noOfShares, date);
  }

  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAFlexiblePortfolio(String certainDate, User user, String portfolioUuid) {
    return flexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
  }

  @Override
  public double calculateCostBasis(User user, String portfolioUuid, String date) {
    return flexibleModel.calculateCostBasis(user, portfolioUuid, date);
  }

  @Override
  public boolean validateFlexibleUserPortfolioExternalPathAndContentsStructure(String filepath) {
    return flexibleModel.validateUserPortfolioExternalPathAndContentsStructure(filepath);
  }

  @Override
  public String saveExternalFlexibleUserPortfolio(String filepath, User user) {
    return flexibleModel.saveExternalUserPortfolio(filepath, user);
  }

  @Override
  public List<String> getFlexiblePortfolioPerformance(String date1, String date2, String portfolioUuid, User user) {
    return flexibleModel.getPortfolioPerformance(date1, date2, portfolioUuid, user);
  }
}
