package stockhw5.model;

import java.util.List;

public interface FlexibleStockModel extends StockModel {

  boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  double calculateCostBasis(User user, String portfolioUUID, String date);

  boolean validateTickerShare(String tickerShareDate);

  List<String> portfolioCompositionFlexible(String portfolioUUID, User user,  String date);

  List<String> getPortfolioPerformance(String date1, String date2, String portfolioUuid, User user);
}
