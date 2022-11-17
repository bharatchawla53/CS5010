package stockhw5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlexibleStockModelImpl extends AbstractStockModel implements FlexibleStockModel {

  private final double commissionRate;

  public FlexibleStockModelImpl() {
    this.commissionRate = 0.10;
  }


  @Override
  public List<String> getPortfolioContents(User user, String portfolioUUID) {
    List<String> portfolioRows = new ArrayList<>();
    File f = new File(user.getUserName()+"_"+portfolioUUID+"_"+"fl_"+".csv");
    try {
      BufferedReader fr = new BufferedReader(new FileReader(user.getUserName()+"_"+portfolioUUID+"_"+"fl_"+".csv"));
      String strLine;

      while ((strLine = fr.readLine()) != null) {
        if (strLine.equals(""))
        {
          break;
        }

        String ticker = strLine.split(",")[0];
        String noOfShares = strLine.split(",")[1];
        String stockPrice = strLine.split(",")[2];
        String rdate = strLine.split(",")[3];
        String tickerNoOfShares = ticker + "," + noOfShares + "," + stockPrice+","+rdate;
        portfolioRows.add(tickerNoOfShares);
      }
      return portfolioRows;
    } catch (IOException e) {
      return portfolioRows;
    }
  }

  @Override
  public boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    //DESC Similar to dump ticker share, open up the portfolio, then add the ticker, no of shares, and the date to the portfolio
    // Validating user input for tickershare changes based on what sort of portfolio you deal with
    // So separating validate Ticker share into 2 different implementations seems to be the way to go( Hence remove from abstract)
    // Remember that this time around you only combine stocks from the same ticker if they are bought on the same date
    // And any other type of input, even if the ticker is the same, is inserted into the portfolio IN A SORTED MANNER.
    // This is going to involve a date based sorting and a ticker based sorting right after

    // NEED TO TEST, getStockPrice now has a date param as individual tickers now need dates
    boolean isSuccessful = false;
    boolean isOverwritten = false;

    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    File f = new File(portfolioFileName);


    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(LocalDate.parse(date)));

    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares, String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));
    }

    if (f.exists() && !f.isDirectory()) {
      List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
      List<String> sortedPortfolioContents = sortPortfolioOnDate(portfolioContents);
      String record = ticker + "," + noOfShares + "," + stockPrice + "," + date + "\n";
      List<String> updatedPortfolioContents = insertIntoSortedPortfolio(portfolioUUID, user, record,date);
      try {
        FileWriter fw = new FileWriter(portfolioFileName, false);
        for (String row : updatedPortfolioContents) {
          fw.write(row+"\n");

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

            fw.write(ticker + "," + noOfShares + "," + stockPrice + "," + date + "\n");
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
  public boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker, String noOfShares, String date) {
    // Iterate through portfolio till iterate date is more than input date
    // If exact date match found, and it belongs to the same ticker, then subtract noOfShares from the shares from this date
    // Else, add this to the portfolio as ticker, -noOfShares , sharevalue, date
    //TODO throw exceptions - catch at the controller level
    Map<String, Integer> tickerNumShares = getTickerNumSharesGivenDate(user, portfolioUUID, LocalDate.parse(date));
    if (!tickerNumShares.containsKey(ticker)) {
      return false;
    }
    if (tickerNumShares.get(ticker) < Integer.parseInt(noOfShares)) {
      return false;
    }
    boolean isSuccessful = false;
    boolean isOverwritten = false;

    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    File f = new File(portfolioFileName);

    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares},
            getCurrentDateSkippingWeekends(LocalDate.parse(date)));

    if (stockPrice == null) {
      stockPrice = getStockPrice(ticker, noOfShares, String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(date))));

    }

    if (f.exists() && !f.isDirectory()) {
      List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
      String record = ticker + "," + "-" + noOfShares + "," + stockPrice + "," + date + "\n";
      //fw.write(ticker + "," + "-"+ noOfShares + "," + stockPrice + ","+date+"\n");
      List<String> updatedPortfolioContents = insertIntoSortedPortfolio(portfolioUUID, user, record,date);
      if (!isSuccessful) {
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
      }
    }

    return isSuccessful;
  }

  @Override
  public double calculateCostBasis(User user, String portfolioUUID, String date) {
    // add the cost of each share till the user provided date?  + TODO commission ?

    //TODO push commissionRate to enum
    // get ticker wise number of shares given the date, and then take sum of shares
    // multiply by constant fixed value
    // TODO push commisionRate as field variable

    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);

    double totalCommissionValue = 0.0;
    for (String row : portfolioContents) {
      if (LocalDate.parse(row.split(",")[3]).isBefore(LocalDate.parse(date)) || LocalDate.parse(row.split(",")[3]).isEqual(LocalDate.parse(date))) {
        int shares = Math.abs(Integer.parseInt(row.split(",")[1]));
        double shareValue = Double.parseDouble(row.split(",")[2]);
        double transactionValue = shares * shareValue;
        double commissionFromTransaction = transactionValue * this.commissionRate;
        totalCommissionValue += transactionValue + commissionFromTransaction;

      }
    }
    return totalCommissionValue;
  }

  @Override
  public boolean validateTickerShare(String tickerShareDate) {
    Pattern pattern = Pattern.compile("([A-Z]+[,]\\d+[,]+\\d{4}-\\d{2}-\\d{2})");
    Matcher validator = pattern.matcher(tickerShareDate);
    return validator.matches();
  }


  // search through a portfolio and find all the stocks before or equal to user given date to
  // calculate portfolio value
  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                                    User user, String portfolioUUID) {

    List<String> totalValueOfPortfolio = new ArrayList<>();
    List<String> portfolioContents = portfolioCompositionFlexible(portfolioUUID, user, certainDate);

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


  private int checkIfTickerInPortfolio(String ticker, List<String> portfolioList)
  {
    for(int i =0;i<portfolioList.size();i++)
    {
      String row = portfolioList.get(i);
      if(row.split(",")[0].equals(ticker))
      {
        return i;
      }
    }
    return -1;
  }

  private String getMinDateInRange(String date1,String date2, User user, String portfolioUUID)
  {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    LocalDate minDate = LocalDate.MAX;
    for(String row: portfolioContents)
    {
      LocalDate rDate = LocalDate.parse(row.split(",")[3]);
      if(rDate.isBefore(minDate) && (rDate.isAfter(LocalDate.parse(date1)) || rDate.equals(LocalDate.parse(date1)))
      && rDate.isBefore(LocalDate.parse(date2)))
      {
        minDate = rDate;
      }
    }
    return String.valueOf(minDate);
  }

  private String getMaxDate(String date1, String date2, User user, String portfolioUUID)
  {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    LocalDate minDate = LocalDate.MIN;
    for(String row: portfolioContents)
    {
      LocalDate rDate = LocalDate.parse(row.split(",")[3]);
      if(rDate.isAfter(minDate) && (rDate.isAfter(LocalDate.parse(date1)) || rDate.equals(LocalDate.parse(date1)))
              && rDate.isBefore(LocalDate.parse(date2)))
      {
        minDate = rDate;
      }
    }
    return String.valueOf(minDate);
  }

  private List<LocalDate> getNumDays(String minDateInRange, String getMaxDateInRange)
  {

   List<LocalDate> dateListDays = new ArrayList<>();
   LocalDate cursorDate = LocalDate.parse(minDateInRange);
   dateListDays.add(cursorDate);
   while(cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))  )
   {
     LocalDate nextDay = cursorDate.plusDays(1);
     dateListDays.add(nextDay);
     cursorDate = cursorDate.plusDays(1);
   }
   return dateListDays;
  }

  private List<LocalDate> getNumMonths(String minDateInRange, String getMaxDateInRange)
  {
    List<LocalDate> dateListMonths = new ArrayList<>();
    LocalDate cursorDate = LocalDate.parse(minDateInRange);
    //Get month

    LocalDate lastDayOfCursorMonth = cursorDate.with(TemporalAdjusters.lastDayOfMonth());

    // Get month, decide if 30 or 31, then get entire date

    
    dateListMonths.add(lastDayOfCursorMonth);
    while(cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))  )
    {
      LocalDate nextMonth = cursorDate.plusMonths(1);
      dateListMonths.add(nextMonth);
      cursorDate = cursorDate.plusMonths(1);
    }
    return dateListMonths;
  }

  private List<LocalDate> getNumYears(String minDateInRange, String getMaxDateInRange)
  {
    List<LocalDate> dateListYears = new ArrayList<>();
    LocalDate cursorDate = LocalDate.parse(minDateInRange);
    //Get month

    LocalDate lastDayOfCursorYear = cursorDate.with(TemporalAdjusters.lastDayOfYear());

    // Get month, decide if 30 or 31, then get entire date


    dateListYears.add(lastDayOfCursorYear);
    while(cursorDate.isBefore(LocalDate.parse(getMaxDateInRange))  )
    {
      LocalDate nextYear = cursorDate.plusYears(1);
      dateListYears.add(nextYear);
      cursorDate = cursorDate.plusYears(1);
    }
    return dateListYears;
  }

  private String getGranularity(String date1,String date2, User user, String portfolioUUID)
  {
    LocalDate min_date_in_range = LocalDate.parse(getMinDateInRange(date1,date2,user, portfolioUUID));
    LocalDate max_date_in_range = LocalDate.parse(getMaxDate(date1,date2,user,portfolioUUID));

    List<LocalDate> numDays = getNumDays(String.valueOf(min_date_in_range),String.valueOf(max_date_in_range));
    List<LocalDate> numMonths = getNumMonths(String.valueOf(min_date_in_range),String.valueOf(max_date_in_range));
    List<LocalDate> numYears = getNumYears(String.valueOf(min_date_in_range),String.valueOf(max_date_in_range));

    String granularity = "";
    if(numYears.size() > 5 && numYears.size()<=30)
    {
      granularity = "YY";
    }
    else if(numMonths.size()>5 && numYears.size()<=30)
    {
      granularity = "MMYY";
    }
    else
    {
      granularity = "DDMM";
    }
    return granularity;
  }
  private  Map<Map<String,String>,Integer> getDatePerformanceMap(String date1,String date2, User user, String portfolioUUID)
  {
    LocalDate min_date_in_range = LocalDate.parse(getMinDateInRange(date1,date2,user, portfolioUUID));
    LocalDate max_date_in_range = LocalDate.parse(getMaxDate(date1,date2,user,portfolioUUID));

    List<LocalDate> numDays = getNumDays(date1,date2);
    List<LocalDate> numMonths = getNumMonths(date1,date2);
    List<LocalDate> numYears = getNumYears(date1,date2);

    List<LocalDate> graphContentsDate = new ArrayList<>();
    if(numYears.size() > 5 && numYears.size()<=30)
    {
      graphContentsDate = numYears;
    }
    else if(numMonths.size()>5 && numYears.size()<=30)
    {
      graphContentsDate = numMonths;
    }
    else
    {

      graphContentsDate = numDays;
    }
    Map<String,Double> graphContentsMap = new HashMap<>();
    List<Double> graphContentsPerf= new ArrayList<>();

    for(LocalDate gDate: graphContentsDate)
    {
      double totalVal = 0.0;
      Map<Integer,List<String>> totalValResp = calculateTotalValueOfAPortfolio(String.valueOf(gDate),user,portfolioUUID);
      for (String row : totalValResp.values().stream().findFirst().get()) {
        totalVal += Double.parseDouble(row.split(",")[2]);
      }

      graphContentsMap.put(String.valueOf(gDate),totalVal);

    }




    Map<Map<String,String>,Integer> graphContentsMapStars = cvtGraphMapToMMDD(graphContentsMap);

    return graphContentsMapStars;


  }

  private String getStarsFromVal(double val, int scale)
  {
    int numStars =  (int) Math.floor(val/scale);
    String starString = "";
    for(int i =0;i<numStars;i++)
    {
      starString+= "*";
    }
    return starString;
  }

  private int getScale(List<Double> vals)
  {

    int minScale = 100000;
    int numZeroes = 0;
    int minZeroes = 9999;
    int numStarsMax = 25;
    int idealScaleVar = 0;
    int maxIdealScaleVar = -1;
    int chosenMinScale = 0;
    while(minScale > 1) {
      for (double val : vals) {
        if (val / minScale < 1) {

          numZeroes += 1;
        }
        int numStars = (int) Math.floor(val/minScale);
        if (numStars <= numStarsMax && !(numStars < 1))
        {
          idealScaleVar+=1;
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
  private Map<Map<String,String>,Integer> cvtGraphMapToMMDD(Map<String,Double> gMap)
  {
    // i have a date object
    //
    Map<String,String> MMDDMap = new HashMap<>();
    List<Double> allValues = new ArrayList<>();

    for (String key: gMap.keySet())
    {

      allValues.add(gMap.get(key));
    }
    int scale = getScale(allValues);

    for (String key: gMap.keySet())
    {
      String nMapKey =  String.valueOf(key);

      MMDDMap.put(nMapKey,getStarsFromVal(gMap.get(key),scale));
    }

    Map<Map<String,String>,Integer> gMapContentScale = new HashMap<>();
    gMapContentScale.put(MMDDMap,scale);

    return gMapContentScale;
  }

  private String cvtToMMD(String graphObj)
  {

    String date = graphObj.split(":")[0];
    String stars = "";
    if(graphObj.split(":").length == 1)
    {
      stars = "";
    }
    else {
    stars = graphObj.split(":")[1];}

    String gDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .format(LocalDate.parse(date));
    return gDate+":"+stars;
  }

  private List<String> sortPerformanceList(List<String> performanceList)
  {
    List<String> sortedPerformanceList = new ArrayList<>();
    while(performanceList.size() > 0) {
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
  @Override
  public List<String> getPortfolioPerformance(String date1,String date2, String portfolioUUID, User user)
  {
    Map<Map<String,String>,Integer> nMap = getDatePerformanceMap(date1,date2,user, portfolioUUID);
    Map<String,String> resMap = new HashMap<>();
    int scale = 0;
    for(Map<String,String> res:nMap.keySet())
    {
      resMap = res;
      scale = nMap.get(res);
    }

    List<String> graphContents = new ArrayList<>();
    for(String graphObj: resMap.keySet())
    {
      graphContents.add(graphObj+":"+resMap.get(graphObj));
    }

    List<String> sortedPerformanceListDesc = sortPerformanceList(graphContents);
    sortedPerformanceListDesc.add("Scale: 1 * is = "+scale+" USD.");
    return sortedPerformanceListDesc;

  }

  @Override
  public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
    Map<String, Integer> tickerNumShareIntraDay = getTickerNumShareIntraDay(portfolioUUID, user,date);
    List<String> portfolioList = new ArrayList<>();
    for (String key : tickerNumShareIntraDay.keySet()) {
      if (LocalDate.parse(key.split("%")[1]).isBefore(LocalDate.parse(date)) || LocalDate.parse(key.split("%")[1]).isEqual(LocalDate.parse(date))) {
        String ticker = key.split("%")[0];
        String bDate = key.split("%")[1];
        String numShares = String.valueOf(tickerNumShareIntraDay.get(key));
        String shareValue = String.valueOf(getStockPrice(ticker, numShares, String.valueOf(getCurrentDateSkippingWeekends(LocalDate.parse(bDate)))));
        String pRow = ticker + "," + numShares + "," + shareValue +"," +bDate;
        portfolioList.add(pRow);
      }
    }

    List<String> fPortfolioList = new ArrayList<>();
    for (String row:portfolioList)
    {
      if(checkIfTickerInPortfolio(row.split(",")[0],fPortfolioList)>=0)
      {
        int index = checkIfTickerInPortfolio(row.split(",")[0],fPortfolioList);
        String gRow = fPortfolioList.get(index);
        String ticker = row.split(",")[0];
        String shareVal = row.split(",")[2];
        String gDate = gRow.split(",")[3];
        String gShareNum = gRow.split(",")[1];
        String iDate = row.split(",")[3];
        String iShareNum = row.split(",")[1];
        String tShareNum = String.valueOf(Integer.parseInt(gShareNum)+Integer.parseInt(iShareNum));
        String tDate = String.valueOf(LocalDate.MAX);
        if(LocalDate.parse(iDate).isBefore(LocalDate.parse(gDate)))
        {
          tDate = String.valueOf(LocalDate.parse(gDate));
        }
        else
        {
          tDate = String.valueOf(LocalDate.parse(iDate));
        }
        String tRow = ticker+","+tShareNum+ ","+shareVal+","+tDate;
        fPortfolioList.set(index,tRow);
      }
      else
      {
        fPortfolioList.add(row);
      }
    }
    return fPortfolioList;
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
          String tickerNoOfShares = ticker + " " + noOfShares + " " + stockPrice + " " + date;
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

  /**
   * It validates if the correct sequence of combination is provided by the user in the external
   * file.
   *
   * @param row it contains stock details, expected format : symbol,shares,price.
   * @return true if the sequence is valid, false, otherwise.
   */
  private boolean validatePortfolioRow(String row) {
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+[,](\\d|\\.)+[,]+\\d{4}-\\d{2}-\\d{2}");
    Matcher validator = ticketShareValidationPattern.matcher(row);
    return validator.matches();
  }

  private Double getStockPrice(String ticker, String noOfShares, String date) {
    //TODO move from abstract if present, and add to
    Double stockPrice;
    // call the API
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

  private Map<String, Integer> getTickerNumSharesGivenDate(User user, String portfolioUUID, LocalDate date) {

    Map<String, Integer> tickerNumShares = new HashMap<>();
    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);
    for (String row : portfolioContents) {
      if (LocalDate.parse(row.split(",")[3]).isBefore(date)) {
        if (tickerNumShares.containsKey(row.split(",")[0])) {
          tickerNumShares.put(row.split(",")[0], tickerNumShares.get(row.split(",")[0]) + Integer.parseInt(row.split(",")[1]));

        } else {
          tickerNumShares.put(row.split(",")[0], Integer.parseInt(row.split(",")[1]));
        }
      }

    }
    return tickerNumShares;
  }

  private List<String> sortPortfolioOnDate(List<String> portfolioContents) {
    List<String> sortedPortfolioContents = new ArrayList<>();
    while(portfolioContents.size() > 0) {
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

  private List<String> insertIntoSortedPortfolio(String portfolioUUID, User user, String record,String date) {
    //TODO Make sure everything is comma
    String portfolioFileName = user.getUserName() + "_" + portfolioUUID + "_fl_" + ".csv";
    //FileWriter fw = new FileWriter(portfolioFileName,false);
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    portfolioContents.add(record);
    List<String> updatedPortfolioContents = sortPortfolioOnDate(portfolioContents);
    return updatedPortfolioContents;
  }

  private Map<String, Integer> getTickerNumShareIntraDay(String portfolioUUID, User user,String date) {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID);
    Map<String,Integer> tickerShares = getTickerNumSharesGivenDate(user,portfolioUUID,LocalDate.parse(date));
    Map<String, Integer> tickerNumShareIntraDay = new HashMap<>();
    for (String row : portfolioContents) {
      String tickerDate = row.split(",")[0] + "%" + row.split(",")[3];
      if (tickerNumShareIntraDay.containsKey(tickerDate))  {
        tickerNumShareIntraDay.put(tickerDate, tickerNumShareIntraDay.get(tickerDate) + Integer.parseInt(row.split(",")[1]));

      } else {
        tickerNumShareIntraDay.put(tickerDate, Integer.parseInt(row.split(",")[1]));
      }
    }
    return tickerNumShareIntraDay;
  }




}
