package stockhw5.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlexibleStockModelImpl extends AbstractStockModel implements FlexibleStockModel {

  @Override
  public boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    return false;
  }

  @Override
  public boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    return false;
  }

  @Override
  public List<String> calculateCostBasis(User user, String portfolioUUID, String date) {
    // add the cost of each share till the user provided date?  + commission ?

    List<String> result = new ArrayList<>();
    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);

    for (String content : portfolioContents) {
      String[] shareDetail = content.split(",");
      // TODO expecting another column, test once other impl is done

      LocalDate purchaseDate = getCurrentDateSkippingWeekends(dateParser(shareDetail[3]));
      LocalDate userInputDate = getCurrentDateSkippingWeekends(dateParser(date));

      if (userInputDate.isBefore(purchaseDate) || userInputDate.isEqual(purchaseDate)) {
        double stockCost = Double.parseDouble(shareDetail[1]) * Double.parseDouble(shareDetail[2]);

        result.add(shareDetail[0] + "," + shareDetail[1] + "," + shareDetail[2] + "," + stockCost);
      }
    }

    return result;
  }

  // search through a portfolio and find all the stocks before or equal to user given data to
  // calculate portfolio value
  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                                    User user, String portfolioUUID) {

    List<String> totalValueOfPortfolio = new ArrayList<>();

    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);

    for (String content : portfolioContents) {
      String[] shareDetail = content.split(",");
      // TODO expecting another column, test once other impl is done

      LocalDate purchaseDate = getCurrentDateSkippingWeekends(dateParser(shareDetail[3]));
      LocalDate userInputDate = getCurrentDateSkippingWeekends(dateParser(certainDate));

      if (userInputDate.isBefore(purchaseDate) || userInputDate.isEqual(purchaseDate)) {
        Double stockPrice = getStockPrice(shareDetail, userInputDate);

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
    }

    return new HashMap<>() {
      {
        put(totalValueOfPortfolio.size(), totalValueOfPortfolio);
      }
    };
  }

  /**
   * It returns a closing stock price for a given stock on a certain date.
   *
   * @param shareDetail an array containing stock symbol and number of shares.
   * @param certainDate the date for retrieving stock price on.
   * @return the stock price.
   */
  @Override
  public Double getStockPrice(String[] shareDetail, LocalDate certainDate) {
    Double stockPrice = null;

    for (HashMap<String, List<StockApiResponse>> symbolMap : stockHashMapList) {
      if (symbolMap.containsKey(shareDetail[0])) {
        // iterate to find the stock value on a certain date
        for (StockApiResponse timeSeries : symbolMap.get(shareDetail[0])) {
          if (timeSeries.getDate().equals(certainDate)) {
            stockPrice = Double.parseDouble(timeSeries.getCloseVal());
            break;
          }
        }
      }
    }
    return stockPrice;
  }
}
