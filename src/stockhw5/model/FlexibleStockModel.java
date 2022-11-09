package stockhw5.model;

public interface FlexibleStockModel {

  boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  int calculateCostBasis(User user, String portfolioUUID, String date);
}
