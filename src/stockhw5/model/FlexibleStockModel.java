package stockhw5.model;

import java.util.List;

public interface FlexibleStockModel extends StockModel{

  boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date);

  List<String> calculateCostBasis(User user, String portfolioUUID, String date);
}
