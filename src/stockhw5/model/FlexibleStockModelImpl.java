package stockhw5.model;

public class FlexibleStockModelImpl extends StockModelImpl implements FlexibleStockModel {

  @Override
  public boolean buyStockOnASpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    return false;
  }

  @Override
  public boolean sellStockOnASpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    return false;
  }

  @Override
  public int calculateCostBasis(User user, String portfolioUUID, String date) {
    return 0;
  }

  @Override
  public int calculatePortfolioOnASpecificDate(User user, String portfolioUUID, String date) {
    return 0;
  }
}
