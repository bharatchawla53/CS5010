package stockhw5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FlexibleStockModelImpl extends AbstractStockModel implements FlexibleStockModel {

  private final double commissionRate;

  public FlexibleStockModelImpl() {
    this.commissionRate = 1.02;
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

    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID,date);
    int totalShares = 0;

    for (String row : portfolioContents) {
      if (LocalDate.parse(row.split(",")[3]).isBefore(LocalDate.parse(date)) || LocalDate.parse(row.split(",")[3]).isEqual(LocalDate.parse(date))) {
        int shares = Integer.parseInt(row.split(",")[1]);
        totalShares += Math.abs(shares);
      }
    }
    return this.commissionRate * totalShares;
  }

  @Override
  public boolean validateTickerShare(String tickerShareDate) {
    Pattern pattern = Pattern
            .compile("([A-Z]+[,]\\d+[,][2][0][0-9][0-9][\\-][0-1][0-2][\\-][0-3][0-9])|([A-Z]+[,]\\d+)");
    Matcher validator = pattern.matcher(tickerShareDate);
    return validator.matches();
  }


  public List<String> getPortfolioContents(User user, String portfolioUUID, String date) {
    List<String> portfolioRows = new ArrayList<>();
    File f = new File(user.getUserName()+"_"+portfolioUUID+"_"+"fl_"+".csv");
    try {
      BufferedReader fr = new BufferedReader(new FileReader(user.getUserName()+"_"+portfolioUUID+"_"+"fl_"+".csv"));
      String strLine;

      while ((strLine = fr.readLine()) != null || fr.readLine() != "") {
        if (strLine==null || strLine.equals(""))
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

  // search through a portfolio and find all the stocks before or equal to user given date to
  // calculate portfolio value
  @Override
  public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                                    User user, String portfolioUUID) {

    List<String> totalValueOfPortfolio = new ArrayList<>();

    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);
    //Map<String, Integer> tickerShare = getTickerNumSharesGivenDate(user, portfolioUUID, LocalDate.parse(certainDate));

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
        //String pRow = ticker + "," + bDate + "," + numShares + "," + shareValue;
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
    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID,String.valueOf(date));
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
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID,date);
    portfolioContents.add(record);
    List<String> updatedPortfolioContents = sortPortfolioOnDate(portfolioContents);
    return updatedPortfolioContents;
  }

  private Map<String, Integer> getTickerNumShareIntraDay(String portfolioUUID, User user,String date) {
    List<String> portfolioContents = getPortfolioContents(user, portfolioUUID,date);
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
