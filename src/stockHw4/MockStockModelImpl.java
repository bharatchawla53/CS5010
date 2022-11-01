package stockHw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The StockModelImpl class represents user portfolio's and features supported for it.
 */
public class MockStockModelImpl implements StockModel {

  private static List<String> tickerList;
  private final AlphaVantageApi alphaVantageApi;

  private StringBuilder log;
  private static List<HashMap<String, List<AlphaVantageApi.AlphaDailyTimeSeries>>> stockHashMapList;

  /**
   * Constructs an empty StockModelImpl constructor which initializes alphaVantageApi, tickerList,
   * stockHashMapList, and load the ticker list from the csv file.
   */
  public MockStockModelImpl() {
    this.log = log;
    alphaVantageApi = new AlphaVantageApi();
    stockHashMapList = new ArrayList<>();
    tickerList = new ArrayList<>();
    loadTickerList();
  }

  @Override
  public User saveUser(User user) {
    // If its valid, persist it to the user file
    // else return a signal back to controller its invalid
    log.append(user.getUserName());

    return null; // indicates user never persisted
  }

  @Override
  public boolean isUserNameExists(String userName) {
    log.append(userName);

    return false;
  }

  @Override
  public boolean isValidTicker(String ticker) {
    log.append(ticker);
    return false;
  }

  @Override
  public String generateUUID() {

    return UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 8);
  }

  @Override
  public boolean validateTickerShare(String tickerShare) {
    log.append(tickerShare);

    return false;
  }

  @Override
  public List<String> getPortfoliosForUser(User user) {
    log.append(user.getUserName());
    List<String> vals = new ArrayList<String>();
    return  vals;
  }

  @Override
  public boolean validatePortfolioUUID(String portfolioUUID, User user) {
    log.append(portfolioUUID);
    log.append(user.getUserName());

    return false;
  }

  @Override
  public User getUserFromUsername(String username) {
    log.append(username);

    return null;
  }

  @Override
  public boolean saveStock(User user, String portfolioUUID, String ticker, String noOfShares) {
    log.append(user.getUserName());
    log.append(portfolioUUID);
    log.append(ticker);
    log.append(noOfShares);
    boolean isSuccessful =false;

    return isSuccessful;
  }

  @Override
  public List<String> getPortfolioContents(User user, String portfolioUUID) {
    log.append(user.getUserName());
    log.append(portfolioUUID);
    List<String> vals = new ArrayList<String>();
    return vals;
  }

  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate, User user, String portfolioUUID) {
    log.append(certainDate);
    log.append(user);
    log.append(portfolioUUID);
    List<String> vals = new ArrayList<String>();
    vals.add("0.00");
    return new HashMap<>() {{
      put(1,vals );
    }};
  }

  @Override
  public boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath) {
    log.append(filePath);
    return false;
  }

  @Override
  public String saveExternalUserPortfolio(String filePath, User user) {
    log.append(filePath);
    log.append(user.getUserName());

    return null;
  }

  /**
   * It gets the total stock price for a given ticker.
   *
   * @param ticker     the symbol to calculate the stock price on.
   * @param noOfShares number of shares for that ticker.
   * @return stock price.
   */
  private Double getStockPrice(String ticker, String noOfShares) {
    log.append(ticker);
    log.append(noOfShares);
    Double stockPrice;
    // call the API

    return 0.00;
  }

  /**
   * Given a string date, it parses to LocalDate.
   *
   * @param certainDate string date to be parsed.
   * @return parsed LocalDate.
   */
  private LocalDate dateParser(String certainDate) {
    log.append(certainDate);
    LocalDate ldate = LocalDate.of(12,20,2022);
    return ldate;

  }

  /**
   * It retrieves the stock data for a given symbol from the third party.
   *
   * @param outputSize based on size, it returns the 100 data points or full-length of 20+ years of
   *                   historical data.
   * @param symbol     the name of the stock.
   */
  private void getStockDataFromApi(String outputSize, String symbol) {
    log.append(outputSize);
    log.append(symbol);

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

  /**
   * It returns a stock price for a given stock on a certain date.
   *
   * @param shareDetail an array containing stock symbol and number of shares.
   * @param certainDate the date for retrieving stock price on.
   * @return the stock price.
   */
  private Double getStockPrice(String[] shareDetail, LocalDate certainDate) {
    log.append(shareDetail);
    log.append(certainDate);
    Double stockPrice = null;



    return stockPrice;
  }

  /**
   * It builds a string with stock symbol, number of shares, and the total price for that stock.
   *
   * @param shareDetail an array containing stock symbol and number of shares.
   * @param stockPrice  price for that given stock.
   * @return a string of symbol, number of shares, and total price.
   */
  private String calculateTotalStockWorth(String[] shareDetail, Double stockPrice) {
    log.append(shareDetail);
    log.append(stockPrice);
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
   * It loads the tickers from the csv file at the initial start of the application.
   */
  private void loadTickerList() {
    try {
      BufferedReader in = new BufferedReader(new FileReader("tickers.csv"));
      String str;

      List<String> list = new ArrayList<>();
      while ((str = in.readLine()) != null) {
        list.add(str);
      }
      tickerList = list;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Given a valid user, it attempts to persist the user to the users.csv file.
   *
   * @param user that needs to be added to the file.
   */
  private void persistUser(User user) {
    log.append(user.getUserName());
    // get existing user for re-persisting with the new user

  }

  /**
   * Builds a set of users that are saved in the users.csv file.
   *
   * @return the set of saved users.
   */
  private Set<User> getUsers() {

    Set<User> userSet = new HashSet<>();
    FileInputStream fstream = null;
    try {
      fstream = new FileInputStream("users.csv");
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

      String strLine;
      while ((strLine = br.readLine()) != null) {
        String[] userDetails = strLine.split(",");
        userSet.add(new User(userDetails[0], userDetails[1]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //Close the input stream
      try {
        fstream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return userSet;
  }

  /**
   * Given a stock symbol, it checks if it's a valid ticker or not.
   *
   * @param ticker user provided ticker.
   * @return true if it's a valid ticker, false, otherwise.
   */
  private boolean searchTickerList(String ticker) {
    log.append(ticker);

    return false;
  }

  /**
   * Given a date, it returns a non-weekend date.
   *
   * @param date that needs to be validated for.
   * @return a non-weekend date.
   */
  private LocalDate getCurrentDateSkippingWeekends(LocalDate date) {
    log.append(date);
    LocalDate now = date;


    return now;
  }
}

