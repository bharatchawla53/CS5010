package stockhw5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

  @Override
  public List<String> getPortfolioContents(User user, String portfolioUUID) {
    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + ".csv";
    List<String> portfolioContents = new ArrayList<>();

    try {
      BufferedReader fr = new BufferedReader(new FileReader(portfolioFileName));
      String strLine;

      while ((strLine = fr.readLine()) != null) {
        String ticker = strLine.split(",")[0];
        String noOfShares = strLine.split(",")[1];
        String stockPrice = strLine.split(",")[2];
        String tickerNoOfShares = ticker + "," + noOfShares + "," + stockPrice;
        portfolioContents.add(tickerNoOfShares);
      }
      return portfolioContents;
    } catch (IOException e) {
      return portfolioContents;
    }
  }

  /**
   * Javadoc for validate tickerShare
   * @param tickerShare input received from the user.
   * @return
   */
  @Override
  public boolean validateTickerShare(String tickerShare) {
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+");
    Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
    return validator.matches();
  }

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

  @Override
  public boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath) {
    File f = new File(filePath);
    try {
      BufferedReader fr = new BufferedReader(new FileReader(filePath));
      String strLine;

      while ((strLine = fr.readLine()) != null) {

        if (!validatePortfolioRow(strLine)) {
          return false;
        }
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }


  @Override
  public String saveExternalUserPortfolio(String filePath, User user) {
    String uuid = generateUUID();
    String portfolioFileName = user.getUserName() + "_" + uuid + ".csv";
    List<String> portfolioContents = new ArrayList<>();
    File f = new File(portfolioFileName);
    try {
      BufferedReader fr = new BufferedReader(new FileReader(filePath));

      String strLine;

      while ((strLine = fr.readLine()) != null) {
        if (validatePortfolioRow(strLine)) {
          String ticker = strLine.split(",")[0];
          String noOfShares = strLine.split(",")[1];
          String stockPrice = strLine.split(",")[2];
          String tickerNoOfShares = ticker + " " + noOfShares + " " + stockPrice;
          portfolioContents.add(tickerNoOfShares);
        } else {
          return null;
        }
      }
      if (f.createNewFile()) {
        try {
          FileWriter fw = new FileWriter(portfolioFileName, true);
          for (String s : portfolioContents) {
            fw.write(s.split(" ")[0] + "," + s.split(" ")[1] + "," + s.split(" ")[2] + "\n");
          }

          fw.close();
          return uuid;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    } catch (IOException e) {
      return null;
    }
    return null;
  }

  /**
   * It validates if the correct sequence of combination is provided by the user in the external
   * file.
   *
   * @param row it contains stock details, expected format : symbol,shares,price.
   * @return true if the sequence is valid, false, otherwise.
   */
  private boolean validatePortfolioRow(String row) {
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+[,](\\d|\\.)+");
    Matcher validator = ticketShareValidationPattern.matcher(row);
    return validator.matches();
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
