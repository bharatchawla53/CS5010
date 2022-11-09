package stockhw5.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StockModelImpl class represents user portfolio's and features supported for it.
 */
public class StockModelImpl extends AbstractStockModel {

  // TODO refactor protected methods access changes
  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                                    User user, String portfolioUUID) {
    List<String> totalValueOfPortfolio = new ArrayList<>();

    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);

    for (String content : portfolioContents) {
      String[] shareDetail = content.split(",");
      Double stockPrice = getStockPrice(shareDetail,
              getCurrentDateSkippingWeekends(dateParser(certainDate)));

      if (stockPrice == null) {
        // call the API
        getStockDataFromApi(AlphaVantageOutputSize.FULL.getInput(), shareDetail[0]);
        if (stockHashMapList.stream().noneMatch(map -> map.containsKey(shareDetail[0]))) {
          return new HashMap<>() {
            {
              put(portfolioContents.size(), totalValueOfPortfolio);
            }
          };
        }

        stockPrice = getStockPrice(shareDetail,
                getCurrentDateSkippingWeekends(dateParser(certainDate)));
      }

      String symbolCached = calculateTotalStockWorth(shareDetail, stockPrice);
      totalValueOfPortfolio.add(symbolCached);
    }
    return new HashMap<>() {
      {
        put(totalValueOfPortfolio.size(), totalValueOfPortfolio);
      }
    };
  }
}
