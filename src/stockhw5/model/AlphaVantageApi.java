package stockhw5.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The AlphaVantageApi class represents the third party API call to retrieve stock data.
 */
public class AlphaVantageApi implements StockApi {

  private final String API_KEY = "TIKUGQGX5SGSNYAE";
  private final String API_URL = "https://www.alphavantage.co/query?";
  private List<HashMap<String, List<StockApiResponse>>> stockTradedValues;

  /**
   * Constructs an empty AlphaVantageApi constructor which initializes stockTradedValues.
   */
  public AlphaVantageApi() {
    this.stockTradedValues = new ArrayList<>();
  }

  /**
   * Given the output size and stock, it retrieved the data from the third-party.
   *
   * @param outputSize based on size, it returns the 100 data points or full-length of 20+ years of
   *                   historical data.
   * @param symbol     the name of the stock.
   * @return a list of map of stock with its list of historical data.
   */
  @Override
  public List<HashMap<String, List<StockApiResponse>>> getStockTradedValue(String outputSize,
                                                                           String symbol) {
    URL url;
    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL(API_URL + "function=TIME_SERIES_DAILY"
              + "&outputsize=" + outputSize
              + "&symbol=" + symbol
              + "&apikey=" + API_KEY
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + symbol);
    }

    // return what we have calculated so far to process in the view
    // while we attempt to compute again the remaining ones
    if (output.toString().contains("5 calls per minute")) {
      return stockTradedValues;
    } else {
      return parseResponseToObject(output.toString().split("\n"), outputSize, symbol);
    }
  }

  /**
   * It parses the API JSON response to a list of map of stock with it's list of historical data.
   *
   * @param response   an array of JSON response from the API.
   * @param outputSize based on size, it parses the 100 data points or full-length of 20+ years of *
   *                   historical data.
   * @param symbol     the name of the stock.
   * @return a list of map of stock with its list of historical data.
   */
  private List<HashMap<String, List<StockApiResponse>>> parseResponseToObject(String[] response,
                                                                              String outputSize, String symbol) {
    List<StockApiResponse> timeSeries = new ArrayList<>();

    // only need the current date stock price
    if (outputSize.equals(AlphaVantageOutputSize.COMPACT.getInput())) {
      String[] currDailyTimeStock = response[1].split(",");
      timeSeries.add(new StockApiResponse(dateParser(currDailyTimeStock[0]),
              currDailyTimeStock[1], currDailyTimeStock[4]));
    } else {
      for (int i = 1; i < response.length; i++) {
        String[] currDailyTimeStock = response[i].split(",");
        timeSeries.add(new StockApiResponse(dateParser(currDailyTimeStock[0]),
                currDailyTimeStock[1], currDailyTimeStock[4]));
      }
    }

    // add it to the list
    stockTradedValues.add(new HashMap<>() {
      {
        put(symbol, timeSeries);
      }
    });

    return stockTradedValues;
  }

  /**
   * Given a string date, it parses to LocalDate.
   *
   * @param date string date to be parsed.
   * @return parsed LocalDate.
   */
  @Override
  public LocalDate dateParser(String date) {
    return LocalDate.parse(date);
  }

}
