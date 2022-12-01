package stockhw6.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation Class for a Flexible Portfolio while extending inflexible portfolio
 * functionality.
 */
public class FlexibleStockModelImpl extends AbstractStockModel implements FlexibleStockModel {

  @Override
  public List<String> getPortfolioContents(User user, String portfolioUUID) {
    boolean isUserValid = isUserNameExists(user.getUserName());
    boolean isPortfolioUUIDUserValid = validatePortfolioUUID(portfolioUUID, user);
    if (isUserValid && isPortfolioUUIDUserValid) {
      List<String> portfolioRows = new ArrayList<>();

      try {
        BufferedReader fr = new BufferedReader(new FileReader(user.getUserName()
                + "_" + portfolioUUID + "_" + "fl_" + ".csv"));
        String strLine;

        while ((strLine = fr.readLine()) != null) {
          if (strLine.equals("")) {
            break;
          }
          String ticker = strLine.split(",")[0];
          String noOfShares = strLine.split(",")[1];
          String stockPrice = strLine.split(",")[2];
          String rdate = strLine.split(",")[3];
          String commissionRate = strLine.split(",")[4];
          String tickerNoOfShares = ticker + "," + noOfShares + ","
                  + stockPrice + "," + rdate + "," + commissionRate;
          portfolioRows.add(tickerNoOfShares);
        }
        return portfolioRows;
      } catch (IOException e) {
        return portfolioRows;
      }
    } else {
      return Collections.singletonList(" ");
    }
  }

  @Override
  public boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker,
                                        String noOfShares, String date, int commissionRate)
          throws IllegalArgumentException, DateTimeParseException {

    boolean isSuccessful = false;
    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    File f = new File(portfolioFileName);
    boolean isValidated = validateTransactionInputs(noOfShares, date, user,
            commissionRate, ticker);
    if (!isValidated) {
      return false;
    }

    if (!isFutureDateAllowed(date, false)) {
      throw new IllegalArgumentException("Future Date is not allowed here!");
    }
    Double stockPrice;
    if (LocalDate.parse(date).isAfter(LocalDate.now())) {
      stockPrice = null;
    } else {
      stockPrice = getStockPrice(new String[]{ticker, noOfShares},
              getCurrentDateSkippingWeekends(LocalDate.parse(date)));
      if (stockPrice == null) {
        stockPrice = getStockPrice(ticker, noOfShares,
                String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));
      }
      if (stockPrice == null) {
        stockPrice = getFirstNonNullStockResp(ticker,
                String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))),
                Double.parseDouble(noOfShares));
      }
    }
    if (f.exists() && !f.isDirectory()) {
      String record = ticker + "," + noOfShares + "," + stockPrice + ","
              + date + "," + commissionRate + "\n";

      List<String> updatedPortfolioContents =
              insertIntoSortedPortfolio(portfolioUUID, user, record);
      try {
        FileWriter fw = new FileWriter(portfolioFileName, false);
        for (String row : updatedPortfolioContents) {
          fw.write(row + "\n");
        }
        isSuccessful = true;
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        if (f.createNewFile()) {

          try {
            FileWriter fw = new FileWriter(portfolioFileName, true);
            fw.write(ticker + "," + noOfShares + "," + stockPrice + ","
                    + date + "," + commissionRate + "\n");
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
  public boolean addBuyStockToPortfolio(User user, String portfolioUUID, String ticker,
                                        String noOfShares, String date, int commissionRate)
          throws IllegalArgumentException {


    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";

    boolean isValidated = validateTransactionInputs(noOfShares, date, user,
            commissionRate, ticker);
    if (!isValidated) {
      return false;
    }

    if (!isFutureDateAllowed(date, false)) {
      throw new IllegalArgumentException();
    }
    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(LocalDate.parse(date)));
    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares,
              String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));
    }
    if (stockPrice == null) {
      stockPrice = getFirstNonNullStockResp(ticker, date, Double.parseDouble(noOfShares));
    }
    String record = ticker + "," + noOfShares + "," + stockPrice + ","
            + date + "," + commissionRate + "\n";

    List<String> updatedPortfolioContents =
            insertIntoSortedPortfolio(portfolioUUID, user, record);
    try {
      FileWriter fw = new FileWriter(portfolioFileName, false);
      for (String row : updatedPortfolioContents) {
        fw.write(row + "\n");
      }
      fw.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker,
                                          String noOfShares, String date, int commissionRate)
          throws IllegalArgumentException {

    boolean isValidated = validateTransactionInputs(noOfShares, date, user,
            commissionRate, ticker);
    if (!isValidated) {
      return false;
    }
    if (LocalDate.parse(date).isAfter(LocalDate.now())) {
      throw new IllegalArgumentException();
    }
    Map<String, Integer> tickerNumShares = getTickerNumSharesGivenDate(user, portfolioUUID,
            LocalDate.parse(date));
    if (!tickerNumShares.containsKey(ticker)) {
      return false;
    }
    if (tickerNumShares.get(ticker) < Integer.parseInt(noOfShares)) {
      return false;
    }

    boolean isSuccessful = false;

    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    File f = new File(portfolioFileName);

    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(LocalDate.parse(date)));

    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares,
              String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));

    }
    if (stockPrice == null) {
      stockPrice = getFirstNonNullStockResp(ticker,
              String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))),
              Double.parseDouble(noOfShares));
    }
    if (f.exists() && !f.isDirectory()) {
      String record = ticker + "," + "-" + noOfShares + ","
              + stockPrice + "," + date + "," + commissionRate + "\n";

      List<String> updatedPortfolioContents =
              insertIntoSortedPortfolio(portfolioUUID, user, record);
      try {
        FileWriter fw = new FileWriter(portfolioFileName, false);

        for (String row : updatedPortfolioContents) {
          fw.write(row + "\n");
        }
        isSuccessful = true;
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }

    }

    return isSuccessful;
  }

  @Override
  public double calculateCostBasis(User user, String portfolioUUID, String date) {
    if (!validateTransactionInputs(user, portfolioUUID, date)) {
      return 0.0;
    }
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    double totalCommissionValue = 0.0;
    for (String row : portfolioContents) {
      if (LocalDate.parse(row.split(",")[3]).isBefore(LocalDate.parse(date))
              ||
              LocalDate.parse(row.split(",")[3]).isEqual(LocalDate.parse(date))) {
        double shares = Double.parseDouble(row.split(",")[1]);

        double shareValue = Double.parseDouble(row.split(",")[2]);
        double transactionValue = shares * shareValue;
        int commissionRate = Integer.parseInt(row.split(",")[4]);

        totalCommissionValue += transactionValue + commissionRate;

      }
    }
    return totalCommissionValue;
  }

  /**
   * Validates the user input for ticker, number of bought shares, and the date of purchase.
   *
   * @param tickerShareDate input received from the user.
   * @return if validation is successful, return true, else return false.
   */
  @Override
  public boolean validateTickerShare(String tickerShareDate) {
    Pattern pattern = Pattern.compile("([A-Z]+[,]\\d+[,]+\\d{4}-\\d{2}-\\d{2}[,]\\d+)");
    Matcher validator = pattern.matcher(tickerShareDate);
    return validator.matches();
  }

  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                                    User user,
                                                                    String portfolioUUID) {
    if (!validateTransactionInputs(user, portfolioUUID, certainDate)) {
      Map<Integer, List<String>> valMap = new HashMap<>();
      valMap.put(0, Collections.singletonList(" "));
      return valMap;
    }
    List<String> totalValueOfPortfolio = new ArrayList<>();
    List<String> portfolioContents =
            portfolioCompositionFlexible(portfolioUUID, user, certainDate);
    for (String content : portfolioContents) {
      String[] shareDetail = content.split(",");

      LocalDate purchaseDate = getCurrentDateSkippingWeekends(dateParser(shareDetail[3]));
      LocalDate userInputDate = getCurrentDateSkippingWeekends(dateParser(certainDate));

      if (purchaseDate.isBefore(userInputDate) || purchaseDate.isEqual(userInputDate)) {
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
          if (stockPrice == null) {
            stockPrice = getFirstNonNullStockResp(shareDetail[0],
                    certainDate, Double.parseDouble(shareDetail[1]));
          }
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

  @Override
  public Double getStockPrice(String[] shareDetail, LocalDate certainDate) {
    if (!validateTransactionInputs(shareDetail[0], shareDetail[1], certainDate.toString())) {
      return 0.0;
    }
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

  /**
   * Gets the stock price given the ticker, number of shares, and the date of purchase.
   *
   * @param ticker     the ticker to calculate stock price for.
   * @param noOfShares the number of shares of the ticker.
   * @param date       the date of purchase.
   * @return the total stock price.
   */
  protected Double getStockPrice(String ticker, String noOfShares, String date) {
    if (!validateTransactionInputs(ticker, noOfShares, date)) {
      return 0.0;
    }
    Double stockPrice;
    getStockDataFromApi(AlphaVantageOutputSize.FULL.getInput(), ticker);
    if (stockHashMapList.stream().noneMatch(map -> map.containsKey(ticker))) {
      try {
        Thread.sleep(60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      getStockDataFromApi(AlphaVantageOutputSize.FULL.getInput(), ticker);
    }
    stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            LocalDate.parse(date));
    return stockPrice;
  }

  @Override
  public List<String> getPortfolioPerformance(String date1, String date2,
                                              String portfolioUUID, User user) {
    if (!validateTransactionInputs(date1, date2, user)) {
      return Collections.singletonList(" ");
    }
    Map<Map<String, String>, Integer> nMap = getDatePerformanceMap(date1,
            date2, user, portfolioUUID);
    Map<String, String> resMap = new HashMap<>();
    int scale = 0;
    for (Map<String, String> res : nMap.keySet()) {
      resMap = res;
      scale = nMap.get(res);
    }

    List<String> graphContents = new ArrayList<>();
    for (String graphObj : resMap.keySet()) {
      graphContents.add(graphObj + ":" + resMap.get(graphObj));
    }
    List<String> sortedPerformanceListDesc = sortPerformanceList(graphContents);
    List<String> alignedPerformanceListDesc = alignPerformanceList(sortedPerformanceListDesc);
    alignedPerformanceListDesc.add("Scale: 1 * is = " + scale + " USD.");
    return alignedPerformanceListDesc;

  }

  @Override
  public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
    if (!validateTransactionInputs(date, date, user)) {
      return Collections.singletonList(" ");
    }
    Map<String, Double> tickerNumShareIntraDay = getTickerNumShareIntraDay(portfolioUUID, user);
    List<String> portfolioList = new ArrayList<>();
    for (String key : tickerNumShareIntraDay.keySet()) {
      if (LocalDate.parse(key.split("%")[1]).isBefore(LocalDate.parse(date))
              ||
              LocalDate.parse(key.split("%")[1]).isEqual(LocalDate.parse(date))) {
        String ticker = key.split("%")[0];
        String bDate = key.split("%")[1];
        String numShares = String.valueOf(tickerNumShareIntraDay.get(key));
        String shareValue = String.valueOf(getStockPrice(ticker, numShares,
                String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(bDate)))));

        if (shareValue == null) {
          shareValue = String.valueOf(getFirstNonNullStockResp(ticker,
                  String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(bDate))),
                  Double.parseDouble(numShares)));
        }
        String pRow = ticker + "," + numShares + "," + shareValue + "," + bDate;
        portfolioList.add(pRow);
      }
    }
    List<String> fPortfolioList = new ArrayList<>();
    for (String row : portfolioList) {
      if (checkIfTickerInPortfolio(row.split(",")[0], fPortfolioList) >= 0) {
        int index = checkIfTickerInPortfolio(row.split(",")[0], fPortfolioList);
        String gRow = fPortfolioList.get(index);
        String ticker = row.split(",")[0];
        String shareVal = row.split(",")[2];
        String gDate = gRow.split(",")[3];
        String gShareNum = gRow.split(",")[1];
        String iDate = row.split(",")[3];
        String iShareNum = row.split(",")[1];
        String tShareNum = String.valueOf(Double.parseDouble(gShareNum)
                + Double.parseDouble(iShareNum));
        String tDate = String.valueOf(LocalDate.MAX);
        if (LocalDate.parse(iDate).isBefore(LocalDate.parse(gDate))) {
          tDate = String.valueOf(LocalDate.parse(gDate));
        } else {
          tDate = String.valueOf(LocalDate.parse(iDate));
        }
        String tRow = ticker + "," + tShareNum + "," + shareVal + "," + tDate;
        fPortfolioList.set(index, tRow);
      } else {
        fPortfolioList.add(row);
      }
    }
    List<String> nonZeroPortfolioList = new ArrayList<>();
    for (String row : fPortfolioList) {

      if (!row.split(",")[1].equals(String.valueOf("0"))) {
        nonZeroPortfolioList.add(row);
      }
    }
    return nonZeroPortfolioList;
  }

  /**
   * Validate the contents of a portfolio file.
   *
   * @param filePath input received from the user.
   * @return if all the contents are valid, then return true. Else, return false.
   */
  @Override
  public boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath) {
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
  public boolean updatePortfolioBasedOnInvestment(User user, String portfolioUUID,
                                                  List<String> tickerList,
                                                  String startDate, int capital,
                                                  List<Integer> weightList,
                                                  int commissionRate) {

    if (!validateTransactionInputs(user, tickerList, startDate,
            startDate, capital, weightList, commissionRate)) {
      return false;
    }
    //UUID chosen from menu in controller
    LocalDate startDateLocal = LocalDate.parse(startDate);
    LocalDate cursorDate = startDateLocal;
    boolean isSuccessful;


    for (int i = 0; i < tickerList.size(); i++) {
      cursorDate = startDateLocal;
      Double stockVal;
      stockVal = getStockPrice(tickerList.get(i), "1",
              getCurrentDateSkippingWeekends(cursorDate).toString());
      if (stockVal == null) {
        stockVal = getFirstNonNullStockResp(tickerList.get(i),
                String.valueOf(getCurrentDateSkippingWeekends(cursorDate)), 1);
      }
      if (stockVal == null) {
        stockVal = getFirstNonNullStockResp(tickerList.get(i),
                getCurrentDateSkippingWeekends(cursorDate).toString(), 1);
      }

      double numShares = (double) (capital * (weightList.get(i)) / 100.00) / stockVal;
      isSuccessful = buyStockOnSpecificDate(user, portfolioUUID, tickerList.get(i),
              String.valueOf(numShares), getCurrentDateSkippingWeekends(cursorDate).toString(),
              commissionRate);

      if (!isSuccessful) {
        return isSuccessful;
      }
    }
    isSuccessful = true;
    return isSuccessful;
  }

  private boolean buyStockOnSpecificDateGivenPortfolio(User user, String portfolioUUID,
                                                       String ticker,
                                                       String noOfShares, String date,
                                                       int commissionRate) {
    boolean isSuccessful = false;
    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    File f = new File(portfolioFileName);
    boolean isValidated = validateTransactionInputs(noOfShares, date,
            user, commissionRate, ticker);
    if (!isValidated) {
      return false;
    }

    if (!isFutureDateAllowed(date, false)) {
      throw new IllegalArgumentException("Future Date is not allowed here!");
    }
    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(LocalDate.parse(date)));
    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares,
              String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));
    }
    if (stockPrice == null) {
      stockPrice = getFirstNonNullStockResp(ticker,
              String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))),
              Double.parseDouble(noOfShares));
    }
    if (f.exists() && !f.isDirectory()) {
      String record = ticker + "," + noOfShares + "," + stockPrice + ","
              + date + "," + commissionRate + "\n";

      List<String> updatedPortfolioContents =
              insertIntoSortedPortfolio(portfolioUUID, user, record);
      try {
        FileWriter fw = new FileWriter(portfolioFileName, false);
        for (String row : updatedPortfolioContents) {
          fw.write(row + "\n");
        }
        isSuccessful = true;
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      return false;
    }
    return false;
  }


  /**
   * The filepath of the file to be externally loaded as a flexible portfolio.
   *
   * @param filePath input received from the user.
   * @param user     that is requesting to save external provided portfolio file.
   * @return the uuid of the flexible portfolio loaded onto the app.
   */
  @Override
  public String saveExternalUserPortfolio(String filePath, User user) {
    String uuid = generateUUID();
    String portfolioFileName = user.getUserName() + "_" + uuid + "_fl_.csv";
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
          String date = strLine.split(",")[3];
          String commissionRate = strLine.split(",")[4];
          String tickerNoOfShares = ticker + " " + noOfShares + " "
                  + stockPrice + " " + date + " " + commissionRate;
          portfolioContents.add(tickerNoOfShares);
        } else {
          return null;
        }
      }
      if (f.createNewFile()) {
        try {
          FileWriter fw = new FileWriter(portfolioFileName, true);
          for (String s : portfolioContents) {
            fw.write(s.split(" ")[0] + "," + s.split(" ")[1]
                    + "," + s.split(" ")[2]
                    + "," + s.split(" ")[3]
                    + "," + s.split(" ")[4]
                    + "\n");
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


  @Override
  public boolean createPortfolioBasedOnPlan(User user, String portfolioUUID,
                                            List<String> tickerList,
                                            String startDate, String endDate,
                                            int daySkip, int capital, List<Integer> weightList,
                                            int commissionRate) {

    if (!validateTransactionInputs(user, tickerList, startDate,
            endDate, capital, weightList, commissionRate)) {
      return false;
    }

    try {
      if (LocalDate.parse(startDate).isAfter(LocalDate.parse(endDate))) {
        return false;
      }


      if ((LocalDate.parse(startDate).isAfter(LocalDate.now())
              && LocalDate.parse(endDate).isAfter(LocalDate.now())))
      {
        return true;
      }

      StockPlan dollarCostPlan = new DollarCostAveragingPlan(tickerList, weightList, capital,
              startDate,
              endDate, daySkip, "quantum", 10000000);

      return dollarCostPlan.createPortfolio(portfolioUUID, user, commissionRate);
    } catch (DateTimeParseException e) {
      return false;
    }
  }


  @Override
  public Map<String, Integer> getBarChartContents(String date1, String date2, String portfolioUUID,
                                                  User user) {
    if (!validateTransactionInputs(date1, date2, user)) {
      Map<String, Integer> emptyChart = new HashMap<>();
      emptyChart.put("NA", 0);
      return emptyChart;
    }
    List<String> graphContents = getPortfolioPerformance(date1, date2, portfolioUUID, user);
    graphContents = graphContents.subList(0, graphContents.size() - 1);
    Map<String, Integer> barChartContents = new LinkedHashMap<>();
    for (String item : graphContents) {
      String[] split = item.split(":");
      String colName = split[0];
      int colVal = split.length > 1 ? item.split(":")[1].strip().length() : 0;
      barChartContents.put(colName, colVal);
    }
    return barChartContents;
  }

  /**
   * Checks if a ticker is in a portfolio.
   *
   * @param ticker        the ticker.
   * @param portfolioList the portfolio contents.
   * @return first index of ticker occurrence in portfolio. -1 if not found.
   */
  private int checkIfTickerInPortfolio(String ticker, List<String> portfolioList) {
    for (int i = 0; i < portfolioList.size(); i++) {
      String row = portfolioList.get(i);
      if (row.split(",")[0].equals(ticker)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Get all LocalDate objects one day apart given 2 dates, start and end.
   *
   * @param minDateInRange    the starting date.
   * @param getMaxDateInRange the ending date.
   * @return a list of local date objects.
   */
  private List<LocalDate> getNumDays(String minDateInRange, String getMaxDateInRange) {

    List<LocalDate> dateListDays = new ArrayList<>();
    LocalDate cursorDate = LocalDate.parse(minDateInRange);
    dateListDays.add(cursorDate);
    while (cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))) {
      LocalDate nextDay = cursorDate.plusDays(1);
      dateListDays.add(nextDay);
      cursorDate = cursorDate.plusDays(1);
    }
    return dateListDays;
  }

  /**
   * Get all LocalDate objects one month apart given 2 dates, start and end.
   *
   * @param minDateInRange    the starting date.
   * @param getMaxDateInRange the ending date.
   * @return a list of local date objects.
   */
  private List<LocalDate> getNumMonths(String minDateInRange, String getMaxDateInRange) {
    List<LocalDate> dateListMonths = new ArrayList<>();
    LocalDate cursorDate = LocalDate.parse(minDateInRange);
    LocalDate lastDayOfCursorMonth = cursorDate.with(TemporalAdjusters.lastDayOfMonth());
    dateListMonths.add(lastDayOfCursorMonth);
    while (cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))) {
      LocalDate nextMonthLastDay = cursorDate.with(TemporalAdjusters.lastDayOfMonth());
      dateListMonths.add(nextMonthLastDay);
      cursorDate = cursorDate.plusMonths(1);
    }
    return dateListMonths;
  }

  /**
   * Get all LocalDate objects one year apart given 2 dates, start and end.
   *
   * @param minDateInRange    the starting date.
   * @param getMaxDateInRange the ending date.
   * @return a list of local date objects.
   */

  private List<LocalDate> getNumYears(String minDateInRange, String getMaxDateInRange) {
    List<LocalDate> dateListYears = new ArrayList<>();
    LocalDate cursorDate = LocalDate.parse(minDateInRange);
    LocalDate lastDayOfCursorYear = cursorDate.with(TemporalAdjusters.lastDayOfYear());
    dateListYears.add(lastDayOfCursorYear);
    while (cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))
            ||
            cursorDate.equals(LocalDate.parse(getMaxDateInRange))) {
      LocalDate nextYearLastDay = cursorDate.with(TemporalAdjusters.lastDayOfYear());
      dateListYears.add(nextYearLastDay);
      cursorDate = cursorDate.plusYears(1);
    }
    return dateListYears;
  }

  /**
   * Get datewise performance between 2 dates for a given portfolio belonging to a given user.
   *
   * @param date1         the start date.
   * @param date2         the end date.
   * @param user          the user that owns the portfolio.
   * @param portfolioUUID the portfolioUUID of the portfolio.
   * @return a Hashmap with datewise values of portfolio and a scale to map these values to *.
   */
  private Map<Map<String, String>, Integer> getDatePerformanceMap(String date1, String date2,
                                                                  User user, String portfolioUUID) {

    List<LocalDate> numDays = getNumDays(date1, date2);
    List<LocalDate> numMonths = getNumMonths(date1, date2);
    List<LocalDate> numYears = getNumYears(date1, date2);
    List<LocalDate> graphContentsDate;
    if (numYears.size() > 5 && numYears.size() <= 30) {
      graphContentsDate = numYears;
    } else if (numMonths.size() > 5 && numYears.size() <= 30) {
      graphContentsDate = numMonths;
    } else {
      graphContentsDate = numDays.subList(0, Math.min(numDays.size(), 30));
    }
    Map<String, Double> graphContentsMap = new HashMap<>();
    for (LocalDate gDate : graphContentsDate) {
      double totalVal = 0.0;
      Map<Integer, List<String>> totalValResp =
              calculateTotalValueOfAPortfolio(String.valueOf(gDate), user, portfolioUUID);
      for (String row : totalValResp.values().stream().findFirst().get()) {
        totalVal += Double.parseDouble(row.split(",")[2]);
      }

      graphContentsMap.put(String.valueOf(gDate), totalVal);

    }
    return cvtGraphMapToMMDD(graphContentsMap);
  }

  /**
   * Renders a string of * based on the entered value and a scale that is the value of one *.
   *
   * @param val   the value to decompose into *'s.
   * @param scale the value of one *.
   * @return a string of *'s.
   */
  private String getStarsFromVal(double val, int scale) {
    int numStars = (int) Math.floor(val / scale);
    String starString = "";
    if (numStars > 30) {
      numStars = 30;
    }
    for (int i = 0; i < numStars; i++) {
      starString += "*";
    }
    return starString;
  }

  /**
   * Get an ideal scale given a list of values.
   *
   * @param vals the list of values to get a scale out of.
   * @return a scale between 1000000 and 1.
   */
  private int getScale(List<Double> vals) {
    int minScale = 1000000;
    int numZeroes = 0;
    int minZeroes = 9999;
    int numStarsMax = 25;
    int idealScaleVar = 0;
    int maxIdealScaleVar = -1;
    int chosenMinScale = 0;
    while (minScale > 1) {
      for (double val : vals) {
        if (val / minScale < 1) {

          numZeroes += 1;
        }
        int numStars = (int) Math.floor(val / minScale);
        if (numStars <= numStarsMax && numStars >= 1) {
          idealScaleVar += 1;
        }
      }
      if (numZeroes < minZeroes && idealScaleVar > maxIdealScaleVar) {
        minZeroes = numZeroes;

        chosenMinScale = minScale;
        maxIdealScaleVar = idealScaleVar;

      }
      minScale = minScale / 10;
      numZeroes = 0;
    }
    return chosenMinScale;
  }

  /**
   * Convert DDMMYY formatted LocalDate Objects to Month Day Year Formatted Strings.
   *
   * @param gMap A hashmap containing a DDMMYY formatted String as Key mapped to Portfolio Value.
   * @return a Hashmap containing the contents of the performance graph mapped to the scale used.
   */
  private Map<Map<String, String>, Integer> cvtGraphMapToMMDD(Map<String, Double> gMap) {
    Map<String, String> mmDdMap = new HashMap<>();
    List<Double> allValues = new ArrayList<>();

    for (String key : gMap.keySet()) {
      allValues.add(gMap.get(key));
    }
    int scale = getScale(allValues);
    for (String key : gMap.keySet()) {
      String nMapKey = String.valueOf(key);
      mmDdMap.put(nMapKey, getStarsFromVal(gMap.get(key), scale));
    }
    Map<Map<String, String>, Integer> gMapContentScale = new HashMap<>();
    gMapContentScale.put(mmDdMap, scale);
    return gMapContentScale;
  }

  /**
   * Converts one line of the performance graph into a Month Day Year formatted line.
   *
   * @param graphObj the line of the graph to convert.
   * @return a formatted line of the graph.
   */
  private String cvtToMMD(String graphObj) {

    String date = graphObj.split(":")[0];
    String stars = "";
    if (graphObj.split(":").length == 1) {
      stars = "";
    } else {
      stars = graphObj.split(":")[1];
    }
    String gDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .format(LocalDate.parse(date));
    return gDate + ":" + stars;
  }

  /**
   * Sorts each datapoint in the graph based on the date of portfolio value calculation.
   *
   * @param performanceList the list of portfolio values on different dates.
   * @return a sorted list of portfolio values based on the date on which they were calculated.
   */
  private List<String> sortPerformanceList(List<String> performanceList) {
    List<String> sortedPerformanceList = new ArrayList<>();
    while (performanceList.size() > 0) {
      LocalDate maxDate = LocalDate.MAX;
      String minDateRow = "";
      int mIndex = 0;
      for (int i = 0; i < performanceList.size(); i++) {
        String row = performanceList.get(i);

        if (LocalDate.parse(row.split(":")[0]).isBefore(maxDate)) {
          minDateRow = row;
          maxDate = LocalDate.parse(row.split(":")[0]);
          mIndex = i;
        }
      }
      sortedPerformanceList.add(cvtToMMD(minDateRow));
      performanceList.remove(mIndex);
    }
    return sortedPerformanceList;
  }

  /**
   * Aligns a list of sorted portfolio values and renders it into a graph line.
   *
   * @param performanceList the list of portfolio values to be rendered into an aligned graph line.
   * @return a list of graph lines.
   */
  private List<String> alignPerformanceList(List<String> performanceList) {
    int maxLen = 0;
    List<String> dateCol = new ArrayList<>();
    List<String> starCol = new ArrayList<>();
    for (String item : performanceList) {
      String dateItem = item.split(":")[0];
      dateCol.add(dateItem.strip());
      if (item.split(":").length > 1) {
        starCol.add(item.split(":")[1]);
      } else {
        starCol.add("");
      }
      if (dateItem.strip().length() > maxLen) {
        maxLen = dateItem.strip().length();
      }

    }
    for (int k = 0; k < dateCol.size(); k++) {
      int spacesToAdd = maxLen - dateCol.get(k).length();
      String nDate = dateCol.get(k);
      for (int i = 0; i < spacesToAdd; i++) {
        nDate += " ";
      }
      nDate += ":";
      dateCol.set(k, nDate + starCol.get(k));
    }
    return dateCol;
  }

  /**
   * It validates if the correct sequence of combination is provided by the user in the external
   * file.
   *
   * @param row it contains stock details, expected format : symbol,shares,price.
   * @return true if the sequence is valid, false, otherwise.
   */
  private boolean validatePortfolioRow(String row) {
    Pattern ticketShareValidationPattern =
            Pattern.compile("[A-Z]+[,]\\d+[,](\\d|\\.)+[,]+\\d{4}-\\d{2}-\\d{2}[,]\\d+");
    Matcher validator = ticketShareValidationPattern.matcher(row);
    return validator.matches();
  }

  /**
   * Gets the number of shares per ticker on a given date.
   *
   * @param user          the user that owns the portfolio.
   * @param portfolioUUID the portfolio identifier.
   * @param date          the date on which the shares per ticker need to be returned to the user.
   * @return a hashmap with the ticker as key and the number of shares for that ticker.
   */
  private Map<String, Integer> getTickerNumSharesGivenDate(User user,
                                                           String portfolioUUID,
                                                           LocalDate date) {

    Map<String, Integer> tickerNumShares = new HashMap<>();
    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);
    for (String row : portfolioContents) {
      if (LocalDate.parse(row.split(",")[3]).isBefore(date)) {
        if (tickerNumShares.containsKey(row.split(",")[0])) {
          tickerNumShares.put(row.split(",")[0],
                  tickerNumShares.get(row.split(",")[0])
                          + Integer.parseInt(row.split(",")[1]));

        } else {
          tickerNumShares.put(row.split(",")[0],
                  Integer.parseInt(row.split(",")[1]));
        }
      }
    }
    return tickerNumShares;
  }

  /**
   * Sorts a list of portfolio records by date.
   *
   * @param portfolioContents the list of rows of the input portfolio.
   * @return sorted list of rows of the input portfolio.
   */
  private List<String> sortPortfolioOnDate(List<String> portfolioContents) {
    List<String> sortedPortfolioContents = new ArrayList<>();
    while (portfolioContents.size() > 0) {
      LocalDate maxDate = LocalDate.MAX;
      String minDateRow = "";
      int mIndex = 0;
      for (int i = 0; i < portfolioContents.size(); i++) {
        String row = portfolioContents.get(i);
        row = row.split("\n")[0];
        if (LocalDate.parse(row.split(",")[3]).isBefore(maxDate)) {
          minDateRow = row;
          maxDate = LocalDate.parse(row.split(",")[3]);
          mIndex = i;
        }
      }
      sortedPortfolioContents.add(minDateRow);
      portfolioContents.remove(mIndex);
    }
    return sortedPortfolioContents;
  }

  /**
   * insert a transaction into a sorted portfolio, in a sorted manner.
   *
   * @param portfolioUUID the portfolio UUID.
   * @param user          the user that owns the portfolio.
   * @param record        the transaction that needs to be inserted correctly into the portfolio.
   * @return the sorted list of portfolio records with the input transaction.
   */
  private List<String> insertIntoSortedPortfolio(String portfolioUUID, User user, String record) {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    portfolioContents.add(record);
    return sortPortfolioOnDate(portfolioContents);
  }

  /**
   * Aggregates transactions with the same ticker together. if the date of purchase and selling.
   * does not change.
   *
   * @param portfolioUUID the portfolio UUID
   * @param user          the user that owns the portfolio.
   * @return a hashmap mapping a ticker to the number of shares across the portfolio time range.
   */
  private Map<String, Double> getTickerNumShareIntraDay(String portfolioUUID, User user) {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    Map<String, Double> tickerNumShareIntraDay = new HashMap<>();
    for (String row : portfolioContents) {
      String tickerDate = row.split(",")[0] + "%" + row.split(",")[3];
      if (tickerNumShareIntraDay.containsKey(tickerDate)) {
        tickerNumShareIntraDay.put(tickerDate, tickerNumShareIntraDay.get(tickerDate)
                + Integer.parseInt(row.split(",")[1]));

      } else {
        tickerNumShareIntraDay.put(tickerDate, Double.parseDouble(row.split(",")[1]));
      }
    }
    return tickerNumShareIntraDay;
  }

  private boolean isFutureDateAllowed(String date, boolean isFutureDateAllowed) {
    if (!isFutureDateAllowed) {
      if (LocalDate.parse(date).isAfter(LocalDate.now())) {
        return false;
      }
    }
    return true;
  }

  private boolean validateTransactionInputs(String noOfShares, String date, User user,
                                            int commissionRate, String ticker)
          throws IllegalArgumentException {
    if (Double.parseDouble(noOfShares) < 0) {
      throw new IllegalArgumentException("Number of shares is negative!");
    }
    try {
      LocalDate testDate = LocalDate.parse(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("This date is not valid!");
    }
    if (!isUserNameExists(user.getUserName())) {
      return false;
    }
    if (commissionRate < 0) {
      return false;
    }
    return isValidTicker(ticker);
  }

  private boolean validateTransactionInputs(User user, String portfolioUUID, String date)
          throws IllegalArgumentException {
    try {
      LocalDate testDate = LocalDate.parse(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("This date is not valid!");
    }
    return isUserNameExists(user.getUserName());
  }

  private boolean validateTransactionInputs(String ticker, String numShares, String date)
          throws IllegalArgumentException {
    try {
      LocalDate testDate = LocalDate.parse(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("This date is not valid!");
    }

    if (Double.parseDouble(numShares) < 0) {
      return false;
    }
    return isValidTicker(ticker);
  }

  private boolean validateTransactionInputs(String startDate, String endDate,
                                            User user) throws IllegalArgumentException {
    try {
      LocalDate startDateL = LocalDate.parse(startDate);
      LocalDate endDateL = LocalDate.parse(endDate);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("This date is not valid!");
    }
    return isUserNameExists(user.getUserName());
  }

  private boolean validateTransactionInputs(User user, List<String> tickerList,
                                            String startDate, String endDate, int capital,
                                            List<Integer> weightList, int commissionRate)
          throws IllegalArgumentException {
    if (!isUserNameExists(user.getUserName())) {
      return false;
    }

    for (String ticker : tickerList) {
      if (!isValidTicker(ticker)) {
        return false;
      }
    }
    try {
      LocalDate startDateL = LocalDate.parse(startDate);
      LocalDate endDateL = LocalDate.parse(endDate);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("This date is not valid!");
    }
    if (capital < 0) {
      return false;
    }
    int weightSum = 0;
    for (int weight : weightList) {
      weightSum += weight;
      if (weight < 0) {

        return false;
      }
    }
    if (weightSum < 100) {
      return false;
    }
    return commissionRate >= 0;
  }

}
