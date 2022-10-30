package stockHw4;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlphaVantageApi {

  private final String API_KEY = "EHQVGM4192TAX7FI";
  private final String API_URL = "https://www.alphavantage.co/query?";

  private List<HashMap<String, List<AlphaDailyTimeSeries>>> stockTradedValues;

  public AlphaVantageApi() {
    this.stockTradedValues = new ArrayList<>();
  }

  // TODO handle API call limits
  public List<HashMap<String, List<AlphaDailyTimeSeries>>> getStockTradedValue(String outputSize, String symbol) {
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

    return parseResponseToObject(output.toString().split("\n"), outputSize, symbol);
  }

  private List<HashMap<String, List<AlphaDailyTimeSeries>>> parseResponseToObject(String[] response, String outputSize, String symbol) {
    List<AlphaDailyTimeSeries> timeSeries = new ArrayList<>();

    // only need the current date stock price
    if (outputSize.equals(AlphaVantageOutputSize.COMPACT.getInput())) {
      String[] currDailyTimeStock = response[1].split(",");
      timeSeries.add(new AlphaDailyTimeSeries(dateParser(currDailyTimeStock[0]), currDailyTimeStock[1], currDailyTimeStock[4]));
    } else {
      for (int i = 1; i < response.length; i++) {
        String[] currDailyTimeStock = response[i].split(",");
        timeSeries.add(new AlphaDailyTimeSeries(dateParser(currDailyTimeStock[0]), currDailyTimeStock[1], currDailyTimeStock[4]));
      }
    }

    // create a hashmap
    HashMap<String, List<AlphaDailyTimeSeries>> map = new HashMap<>();
    map.put(symbol, timeSeries);

    // add it to the list
    stockTradedValues.add(map);

    return stockTradedValues;
  }

  LocalDate dateParser(String date) {
    return LocalDate.parse(date);
  }

  static class AlphaDailyTimeSeries {

    private final LocalDate date;
    private final String openVal;
    private final String closeVal;

    public AlphaDailyTimeSeries(LocalDate date, String openVal, String closeVal) {
      this.date = date;
      this.openVal = openVal;
      this.closeVal = closeVal;
    }

    public LocalDate getDate() {
      return this.date;
    }

    public String getOpenVal() {
      return this.openVal;
    }

    public String getCloseVal() {
      return this.closeVal;
    }
  }
}
