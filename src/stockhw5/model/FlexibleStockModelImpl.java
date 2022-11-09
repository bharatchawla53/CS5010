package stockhw5.model;

import java.util.List;
import java.util.Map;

public class FlexibleStockModelImpl extends AbstractStockModel implements FlexibleStockModel {

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
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                             User user, String portfolioUUID){
    return null;
  }
}
