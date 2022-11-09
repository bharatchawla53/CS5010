package stockhw5.model;

public interface FlexibleStockModel {

  boolean buyStockOnASpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  boolean sellStockOnASpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  int calculateCostBasis(User user, String portfolioUUID, String date);
}
