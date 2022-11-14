package stockhw5.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  /**
   * Javadoc for validate tickerShare
   * @param tickerShare input received from the user.
   * @return
   */


  /**
   * It returns a stock price for a given stock on a certain date.
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
            stockPrice = isCurrentTimeBeforeNoon()
                    ? Double.parseDouble(timeSeries.getOpenVal())
                    : Double.parseDouble(timeSeries.getCloseVal());
            break;
          }
        }
      }
    }
    return stockPrice;
  }

  /**
   * It checks if the current time is before noon or not.
   *
   * @return true if current time is before noon, false, otherwise.
   */
  private boolean isCurrentTimeBeforeNoon() {
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    String formattedDate = formatter.format(now);
    boolean isTimeBeforeNoon = false;
    try {
      if (formatter.parse(formattedDate).before(formatter.parse("12:00"))) {
        isTimeBeforeNoon = true;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return isTimeBeforeNoon;
  }

}
