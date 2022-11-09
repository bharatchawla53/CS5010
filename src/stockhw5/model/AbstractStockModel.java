package stockhw5.model;

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
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractStockModel implements StockModel {

  private static List<String> tickerList;
  private final StockApi alphaVantageApi;
  protected static List<HashMap<String, List<StockApiResponse>>> stockHashMapList;

  /**
   * Constructs an empty StockModelImpl constructor which initializes alphaVantageApi, tickerList,
   * stockHashMapList, and load the ticker list from the csv file.
   */
  public AbstractStockModel() {
    alphaVantageApi = new AlphaVantageApi();
    stockHashMapList = new ArrayList<>();
    tickerList = new ArrayList<>();
    loadTickerList();
  }

  @Override
  public User saveUser(User user) {
    // If its valid, persist it to the user file
    // else return a signal back to controller its invalid
    if (!isUserNameExists(user.getUserName())) {
      persistUser(user);
      return user;
    }
    return null; // indicates user never persisted
  }

  @Override
  public boolean isUserNameExists(String userName) {
    Set<User> userSet = getUsers();
    for (User u : userSet) {
      if (u.getUserName().equals(userName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isValidTicker(String ticker) {
    return searchTickerList(ticker);
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
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+");
    Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
    return validator.matches();
  }

  @Override
  public List<String> getPortfoliosForUser(User user) {
    List<String> userPortfolios = new ArrayList<>();
    File folder = new File("./");

    if (folder.listFiles() == null || folder.listFiles().length == 0) {
      return userPortfolios;
    }

    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().split("_")[0].equals(user.getUserName())) {
          userPortfolios.add(listOfFiles[i].getName().split("_")[1].split("\\.")[0]);
        }
      }
    }
    return userPortfolios;
  }

  @Override
  public boolean validatePortfolioUUID(String portfolioUUID, User user) {
    File folder = new File("./");
    File[] listOfFiles = folder.listFiles();

    if (folder.listFiles() == null || folder.listFiles().length == 0) {
      return false;
    }

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().contains(".csv") && listOfFiles[i].getName().contains("_")) {
          if (listOfFiles[i].getName().split("_")[1].split("\\.")[0].equals(portfolioUUID)
                  && listOfFiles[i].getName().split("_")[0].equals(user.getUserName())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public User getUserFromUsername(String username) {
    Set<User> userSet = getUsers();
    for (User u : userSet) {
      if (u.getUserName().equals(username)) {
        return u;
      }
    }
    return null;
  }

  @Override
  public boolean saveStock(User user, String portfolioUUID, String ticker, String noOfShares) {
    boolean isSuccessful = false;
    boolean isOverwritten = false;

    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + ".csv";
    File f = new File(portfolioFileName);

    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(checkCallIsWithinMarketHours()));
    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares);
    }

    if (f.exists() && !f.isDirectory()) {
      List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
      for (int i = 0; i < portfolioContents.size(); i++) {
        if (portfolioContents.get(i).split(",")[0].equals(ticker)) {
          String newRow = ticker
                  + ","
                  + (Integer.parseInt(portfolioContents.get(i).split(",")[1])
                  + Integer.parseInt(noOfShares))
                  + ","
                  + portfolioContents.get(i).split(",")[2];
          portfolioContents.set(i, newRow);
          isOverwritten = true;
        }
      }
      if (isOverwritten) {
        try {
          FileWriter fw = new FileWriter(portfolioFileName, false);
          for (String row : portfolioContents) {
            fw.write(row + "\n");
          }
          isSuccessful = true;
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        try {
          FileWriter fw = new FileWriter(portfolioFileName, true);
          fw.write(ticker + "," + noOfShares + "," + stockPrice + "\n");
          isSuccessful = true;
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      try {
        if (f.createNewFile()) {
          try {
            FileWriter fw = new FileWriter(portfolioFileName, true);
            fw.write(ticker + "," + noOfShares + "," + stockPrice + "\n");
            isSuccessful = true;
            fw.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isSuccessful;
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
   * It returns a stock price for a given stock on a certain date.
   *
   * @param shareDetail an array containing stock symbol and number of shares.
   * @param certainDate the date for retrieving stock price on.
   * @return the stock price.
   */
  protected Double getStockPrice(String[] shareDetail, LocalDate certainDate) {
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
   * It gets the total stock price for a given ticker.
   *
   * @param ticker     the symbol to calculate the stock price on.
   * @param noOfShares number of shares for that ticker.
   * @return stock price.
   */
  private Double getStockPrice(String ticker, String noOfShares) {
    Double stockPrice;
    // call the API
    getStockDataFromApi(AlphaVantageOutputSize.COMPACT.name(), ticker);
    if (stockHashMapList.stream().noneMatch(map -> map.containsKey(ticker))) {
      try {
        Thread.sleep(60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      getStockDataFromApi(AlphaVantageOutputSize.COMPACT.name(), ticker);
    }
    stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(checkCallIsWithinMarketHours()));
    return stockPrice;
  }

  /**
   * Given a string date, it parses to LocalDate.
   *
   * @param certainDate string date to be parsed.
   * @return parsed LocalDate.
   */
  protected LocalDate dateParser(String certainDate) {
    return alphaVantageApi.dateParser(certainDate);
  }

  /**
   * It retrieves the stock data for a given symbol from the third party.
   *
   * @param outputSize based on size, it returns the 100 data points or full-length of 20+ years of
   *                   historical data.
   * @param symbol     the name of the stock.
   */
  protected void getStockDataFromApi(String outputSize, String symbol) {
    stockHashMapList = alphaVantageApi.getStockTradedValue(outputSize, symbol);
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
   * Checks if the current date is within market hours, otherwise, it uses the previous day for
   * computing stock price.
   *
   * @return LocalDate
   */
  private LocalDate checkCallIsWithinMarketHours() {
    LocalDate date = null;

    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    String formattedDate = formatter.format(now);
    boolean isWithinMarketHours = true;
    try {
      if (formatter.parse(formattedDate).after(formatter.parse("24:00"))
              || formatter.parse(formattedDate).before(formatter.parse("08:00"))) {
        isWithinMarketHours = false;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    date = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    if (!isWithinMarketHours) {
      date = date.minusDays(1);
    }

    return date;
  }

  /**
   * It builds a string with stock symbol, number of shares, and the total price for that stock.
   *
   * @param shareDetail an array containing stock symbol and number of shares.
   * @param stockPrice  price for that given stock.
   * @return a string of symbol, number of shares, and total price.
   */
  protected String calculateTotalStockWorth(String[] shareDetail, Double stockPrice) {
    return shareDetail[0] + "," + shareDetail[1] + ","
            + stockPrice * Double.parseDouble(shareDetail[1]);
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
    // get existing user for re-persisting with the new user
    Set<User> userSet = getUsers();

    // write to text file comma separated
    try {
      FileWriter fileWriter = new FileWriter("users.csv");
      // add the existing users
      for (User u : userSet) {
        fileWriter.write(u.getId() + "," + u.getUserName() + "\n");
      }
      // add the new user
      fileWriter.write(user.getId() + "," + user.getUserName() + "\n");
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    for (String val : tickerList) {
      if (val.split(",")[0].equals(ticker)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Given a date, it returns a non-weekend date.
   *
   * @param date that needs to be validated for.
   * @return a non-weekend date.
   */
  protected LocalDate getCurrentDateSkippingWeekends(LocalDate date) {
    LocalDate now = date;

    DayOfWeek dayOfWeek = DayOfWeek.of(now.get(ChronoField.DAY_OF_WEEK));
    if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
      now = now.minusDays(1);
    } else if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
      now = now.minusDays(2);
    }
    return now;
  }
}
