package stockhw7.portfolio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stockhw7.resources.FileIO;
import stockhw7.resources.Indices;
import stockhw7.resources.IndicesImpl;


//TODO break up long functions into helper methods b/c it's disgusting
//TODO adjust save/load to add new feature -- should not adjust actual portfolio contents

/**
 * Flex Portfolio implementation  of the Flex portfolio interface. designed to have more
 * functionality than a generic Portfolio.
 */
public class FlexPortfolioImpl implements FlexPortfolio {

  private String name;
  private double brokerFee;
  private List<Stock> stocks;
  private List<String> log;
  private Indices indices;

  /**
   * creates an empty FlexPortfolio so that information can be added later.
   */
  public FlexPortfolioImpl() {
    //this is a dummy portfolio for information to be added
  }

  /**
   * this constructor is used primarily for file IO to create a portfolio with the all the
   * information given.
   *
   * @param name      the name of the portfolio
   * @param brokerFee the broker fee of the portfolio
   * @param stocks    the list of stocks in the portfolio
   * @param log       the log of the Buy and sells occurred in the portfolio
   * @param indices   the list of index that hold the index info for a stock
   */
  public FlexPortfolioImpl(String name, double brokerFee, List<Stock> stocks,
                           List<String> log, Indices indices) {
    this.name = name;
    this.brokerFee = brokerFee;
    this.stocks = stocks;
    this.log = log;
    this.indices = indices;
  }

  /**
   * the initial constructor that creates a new portfolio with fresh fields.
   *
   * @param name      the name of the stock
   * @param brokerFee the broker fee of the stock
   */
  public FlexPortfolioImpl(String name, double brokerFee) {
    this.name = name;
    this.brokerFee = brokerFee;
    this.stocks = new ArrayList<>();
    this.log = new ArrayList<>();
    this.indices = new IndicesImpl();
  }


  //This will end up the same mostly (flex/static portfolios will look mostly the same when
  //saved)
  @Override
  public boolean save() throws IOException {
    FileIO.saveFlexPortfolio(stocks, this.toString(), this.name);
    String dir = System.getProperty("user.dir") + "/files/";
    File file = new File(dir + name + ".txt");
    return file.exists() && !file.isDirectory();
  }

  //This I think will be exactly the same?
  @Override
  public boolean load(String fileName) throws IOException {
    if (stocks.isEmpty()) {
      this.name = fileName;
      //this.stocks = FileIO.loadFlexPortfolio(fileName);
    } else {
      throw new IllegalArgumentException("Load cannot be run when portfolio contains "
              + "a usable list of Stocks");
    }
    return false;
  }

  //Will need to call this often in a few other methods in this implementation, but I don't
  //think this method itself will be any different.
  @Override
  public double examineValue(int year, int month, int day) throws IllegalArgumentException {
    //short-circuit if necessary
    double total = 0.0;
    String purchase = "";
    double shares = 0;
    String name = "";
    int purchaseMonth = 0;
    int purchaseDay = 0;
    int purchaseYear = 0;
    int purchaseEndMonth = 0;
    int purchaseEndDay = 0;
    int purchaseEndYear = 0;
    double percent = 0.0;
    double totalInvestment = 0.0;
    int interval = 0;
    String[] dateString;
    //run through string log itself
    for (String s : log) {
      //split off each purchase and take relevant info
      String[] components = s.split(",");
      if (s.charAt(0) == 'B' || s.charAt(0) == 'S') {
        String[] purchaseAndShares = components[0].split(" ");
        purchase = purchaseAndShares[0];
        shares = Double.parseDouble(purchaseAndShares[1]);
        name = components[1].replaceAll("\\s", "");
        dateString = components[2].split("/");
        //split date for validation
        purchaseMonth = Integer.parseInt(dateString[0].replaceAll("\\s", ""));
        purchaseDay = Integer.parseInt(dateString[1].replaceAll("\\s", ""));
        purchaseYear = Integer.parseInt(dateString[2].replaceAll("\\s", ""));
      } else if (s.charAt(0) == 'D') {
        //separate out pieces of log
        String[] purchaseAndShares = components[0].split(" ");
        percent = Double.parseDouble(purchaseAndShares[2].replaceAll("\\s", ""));
        totalInvestment = Double.parseDouble(components[1].replaceAll("\\s", ""));
        name = components[2].replaceAll("\\s", "");
        dateString = components[3].split("/");
        String[] finalDateString = components[4].split("/");
        purchaseMonth = Integer.parseInt(dateString[0].replaceAll("\\s", ""));
        purchaseDay = Integer.parseInt(dateString[1].replaceAll("\\s", ""));
        purchaseYear = Integer.parseInt(dateString[2].replaceAll("\\s", ""));
        purchaseEndMonth = Integer.parseInt(finalDateString[0]
                .replaceAll("\\s", ""));
        purchaseEndDay = Integer.parseInt(finalDateString[1].replaceAll("\\s", ""));
        purchaseEndYear = Integer.parseInt(finalDateString[2]
                .replaceAll("\\s", ""));
        String[] intervalString = components[5].split(" ");
        interval = Integer.parseInt(intervalString[1]);
        //if start date is earlier than date of interest we add to shares and print total (test)
        if (year > purchaseYear || year == purchaseYear && month > purchaseMonth
                || year == purchaseYear && month == purchaseMonth && day >= purchaseDay) {
          indices.getIndex(name);
          shares = (totalInvestment * percent) / indices
                  .getValue(name, purchaseYear, purchaseMonth, purchaseDay);
          total += indices.getValue(name, year, month, day) * shares;
        }
        //add interval to purchase day to simulate next purchase
        purchaseDay += interval;
        //reset purchase day if too big
        while (purchaseDay > 30) {
          purchaseDay -= 30;
          purchaseMonth += 1;
          if (purchaseMonth == 13) {
            purchaseMonth = 1;
            purchaseYear += 1;
          }
        }
        //while new purchase day is within bounds of last purchase day and day of interest
        while ((year > purchaseYear || year == purchaseYear && month > purchaseMonth
                || year == purchaseYear && month == purchaseMonth && day >= purchaseDay)
                && (purchaseEndYear > purchaseYear || purchaseEndYear == purchaseYear
                && purchaseEndMonth > purchaseMonth || purchaseEndYear == purchaseYear
                && purchaseEndMonth == purchaseMonth && purchaseEndDay >= purchaseDay)) {
          //do it again
          try {
            shares = (totalInvestment * percent) / indices
                    .getValue(name, purchaseYear, purchaseMonth, purchaseDay);
          } catch (Exception e) {
            purchaseDay += 1;
            while (purchaseDay > 30) {
              purchaseDay -= 30;
              purchaseMonth += 1;
              if (purchaseMonth == 13) {
                purchaseMonth = 1;
                purchaseYear += 1;
              }
            }
            continue;
          }
          total += indices.getValue(name, year, month, day) * shares;
          //increment more
          purchaseDay += interval;
          while (purchaseDay > 30) {
            purchaseDay -= 30;
            purchaseMonth += 1;
            if (purchaseMonth == 13) {
              purchaseMonth = 1;
              purchaseYear += 1;
            }
          }
        }
      }
      //if earlier in time than date of interest then we consider the price
      if (year > purchaseYear || year == purchaseYear && month > purchaseMonth
              || year == purchaseYear && month == purchaseMonth && day >= purchaseDay) {
        for (Stock stock : stocks) {
          if (stock.getTicker().equals(name) && purchase.equals("BUY:")) {
            total += indices.getValue(name, year, month, day) * shares;
          } else if (stock.getTicker().equals(name) && purchase.equals("SELL:")) {
            total -= indices.getValue(name, year, month, day) * shares;
          }

        }
      }
    }
    return total;
  }

  //Should be virtually identical
  @Override
  public void print() {
    System.out.print(this);
  }

  //We add stock and pretend like it's today, and then give them their date back -- this
  //allows us to not need to update the API every time
  @Override
  public Portfolio addStock(Stock stock, String date) {
    boolean isInStocks = false;
    for (Stock s : stocks) {
      if (s.getTicker().equals(stock.getTicker())) {
        Stock newstock = new StockImpl(s.getShares() + stock.getShares(),
                s.getTicker(), "");
        stocks.remove(s);
        stocks.add(newstock);
        isInStocks = true;
        break; //return new PortfolioImpl(this.name, stocks);
      }
    }
    if (!isInStocks) {
      this.stocks.add(stock);
    }

    indices.getIndex(stock.getTicker());
    try {
      String[] split = date.split("/");
      indices.getValue(stock.getTicker(), Integer.parseInt(split[2]), Integer.parseInt(split[0]),
              Integer.parseInt(split[1]));
      log.add("BUY: " + stock.print().split("\n")[0] + ", " + date + "\n");
    } catch (IllegalArgumentException e) {
      // the date was invalid and don't add it to the log
    }

    return new PortfolioImpl(this.name, stocks);
  }

  //Should be similar to old one except we need date in here
  @Override
  public Portfolio deleteStock(String ticker, double num, String date) {
    for (Stock stock : stocks) {
      if (ticker.equals(stock.getTicker())) {
        if (stock.getShares() - num < 0) {
          throw new IllegalArgumentException("cant sell more stock than this has");
        }
        Stock newStock = new StockImpl(stock.getShares() - num,
                ticker, stock.getIndex());
        this.stocks.remove(stock);
        this.stocks.add(newStock);

        break;
      }
    }
    try {
      String[] split = date.split("/");
      indices.getValue(ticker, Integer.parseInt(split[2]), Integer.parseInt(split[0]),
              Integer.parseInt(split[1]));
      log.add("SELL: " + num + ", " + ticker + ", " + date + "\n");
    } catch (IllegalArgumentException e) {
      // the date was invalid and don't add it to the log
    }
    return new PortfolioImpl(this.name, stocks);
  }

  //Mostly identical
  @Override
  public Object getStocks() {
    return null;
  }

  @Override
  public String toString() {
    String accumulator = "FLEX PORTFOLIO " + name + "\n" + brokerFee + "\n";
    for (String s : log) {
      accumulator += s;
    }
    //accumulator += log.toString().substring(1, log.toString().length() - 1);
    return accumulator;
  }

  @Override
  public double getCostBasis(int year, int month, int day) {
    double total = 0.0;
    //run through string log itself
    for (String s : log) {
      if (s.charAt(0) == 'D') {
        String purchase = "";
        double shares = 0;
        String name = "";
        int purchaseMonth = 0;
        int purchaseDay = 0;
        int purchaseYear = 0;
        int purchaseEndMonth = 0;
        int purchaseEndDay = 0;
        int purchaseEndYear = 0;
        double percent = 0.0;
        double totalInvestment = 0.0;
        int interval = 0;
        String[] dateString;
        //separate out pieces of log
        String[] components = s.split(",");
        String[] purchaseAndShares = components[0].split(" ");
        percent = Double.parseDouble(purchaseAndShares[2].replaceAll("\\s", ""));
        totalInvestment = Double.parseDouble(components[1].replaceAll("\\s", ""));
        name = components[2].replaceAll("\\s", "");
        dateString = components[3].split("/");
        String[] finalDateString = components[4].split("/");
        purchaseMonth = Integer.parseInt(dateString[0].replaceAll("\\s", ""));
        purchaseDay = Integer.parseInt(dateString[1].replaceAll("\\s", ""));
        purchaseYear = Integer.parseInt(dateString[2].replaceAll("\\s", ""));
        purchaseEndMonth = Integer.parseInt(finalDateString[0]
                .replaceAll("\\s", ""));
        purchaseEndDay = Integer.parseInt(finalDateString[1].replaceAll("\\s", ""));
        purchaseEndYear = Integer.parseInt(finalDateString[2]
                .replaceAll("\\s", ""));
        String[] intervalString = components[5].split(" ");
        interval = Integer.parseInt(intervalString[1]);
        //if start date is earlier than date of interest we add to shares and print total (test)
        if (year > purchaseYear || year == purchaseYear && month > purchaseMonth
                || year == purchaseYear && month == purchaseMonth && day >= purchaseDay) {
          total += totalInvestment * percent;
          total += brokerFee;
        }
        //add interval to purchase day to simulate next purchase
        purchaseDay += interval;
        //reset purchase day if too big
        while (purchaseDay > 30) {
          purchaseDay -= 30;
          purchaseMonth += 1;
          if (purchaseMonth == 13) {
            purchaseMonth = 1;
            purchaseYear += 1;
          }
        }
        //while new purchase day is within bounds of last purchase day and day of interest
        while ((year > purchaseYear || year == purchaseYear && month > purchaseMonth
                || year == purchaseYear && month == purchaseMonth && day >= purchaseDay)
                && (purchaseEndYear > purchaseYear || purchaseEndYear == purchaseYear
                && purchaseEndMonth > purchaseMonth || purchaseEndYear == purchaseYear
                && purchaseEndMonth == purchaseMonth && purchaseEndDay >= purchaseDay)) {
          //do it again
          total += totalInvestment * percent;
          total += brokerFee;
          //increment more
          purchaseDay += interval;
          while (purchaseDay > 30) {
            purchaseDay -= 30;
            purchaseMonth += 1;
            if (purchaseMonth == 13) {
              purchaseMonth = 1;
              purchaseYear += 1;
            }
          }
        }
      } else {
        String[] values = getLogValues(s);
        int purchaseYear = Integer.parseInt(values[5]);
        String name = values[2];
        //if earlier in time we consider the price
        if (year > purchaseYear || year == purchaseYear && month > Integer.parseInt(values[3])
                || year == purchaseYear && month == Integer.parseInt(values[3])
                && day >= Integer.parseInt(values[4])) {
          if (values[0].equals("BUY:")) {
            total += indices.getValue(name, purchaseYear, Integer.parseInt(values[3]),
                    Integer.parseInt(values[4])) * Integer.parseInt(values[1]);
            total += brokerFee;
          } else if (values[0].equals("SELL:")) {
            total += brokerFee;
          }
        }
      }
    }
    return total;
  }

  @Override
  public double[] printGraph(int day1, int day2, int month1, int month2, int year1, int year2) {
    //number of rows
    if (month1 == 0) {
      int yearDiff = year2 - year1 + 1;
      int duplicateTracker = 1;
      while (yearDiff > 30) {
        yearDiff /= 2;
        duplicateTracker++;
      }
      //initialize double array to get values with
      int[] setOfYears = new int[yearDiff];
      //evenly divide and set array values
      for (int i = 0; i < yearDiff; i++) {
        setOfYears[i] = year1 + (duplicateTracker - 1) + i * duplicateTracker;
      }
      //cycle through array and populate cost array
      double[] setOfValues = new double[yearDiff];
      for (int i = 0; i < yearDiff; ++i) {
        try {
          setOfValues[i] = examineValue(setOfYears[i], 12, 31);
        } catch (Exception e) {
          try {
            setOfValues[i] = examineValue(setOfYears[i], 12, 30);
          } catch (Exception e2) {
            setOfValues[i] = examineValue(setOfYears[i], 12, 29);
          }
        }
      }
      return setOfValues;
    }
    if (day1 == 0) {
      int monthDiff = month2 - month1 + 1;
      //initialize double array to get values with
      int[] setOfMonths = new int[monthDiff];
      //evenly divide and set array values
      for (int i = 0; i < monthDiff; i++) {
        setOfMonths[i] = month1 + i;
      }
      //cycle through array and populate cost array
      double[] setOfValues = new double[monthDiff];
      for (int i = 0; i < monthDiff; ++i) {
        try {
          setOfValues[i] = examineValue(year1, setOfMonths[i], 31);
        } catch (Exception e) {
          try {
            setOfValues[i] = examineValue(year1, setOfMonths[i], 30);
          } catch (Exception e2) {
            try {
              setOfValues[i] = examineValue(year1, setOfMonths[i], 29);
            } catch (Exception e3) {
              try {
                setOfValues[i] = examineValue(year1, setOfMonths[i], 28);
              } catch (Exception e4) {
                try {
                  setOfValues[i] = examineValue(year1, setOfMonths[i], 27);
                } catch (Exception e5) {
                  setOfValues[i] = examineValue(year1, setOfMonths[i], 26);
                }
              }
            }
          }
        }
      }
      return setOfValues;
    }
    int dayDiff = day2 - day1 + 1;
    //initialize double array to get values with
    int[] setOfDays = new int[dayDiff];
    //evenly divide and set array values
    for (int i = 0; i < dayDiff; i++) {
      setOfDays[i] = day1 + i;
    }
    //cycle through array and populate cost array
    double[] setOfValues = new double[dayDiff];
    for (int i = 0; i < dayDiff; ++i) {
      try {
        setOfValues[i] = examineValue(year1, month1, setOfDays[i]);
      } catch (Exception e) {
        //passes through without adding value
      }
    }
    return setOfValues;
  }

  @Override
  public void dollarCostProcessing(String startDate, String endDate, int intervalDays,
                                   double investmentTotal, String[] stockList,
                                   double[] percentList) {
    for (int i = 0; i < stockList.length; ++i) {
      log.add("DOLLAR COST: " + percentList[i] / 100 + ", "
              + investmentTotal + ", " + stockList[i]
              + ", " + startDate + ", " + endDate + ", " + intervalDays + " day interval:\n");
    }
  }

  @Override
  public Portfolio rebalancePortfolio(String date, double investmentTotal, double[] percentList) {
    Map<String, Double> newStockPriceMap = new HashMap<>();
    for (String s : log) {
      String[] split = date.split("/");
      String[] logValues = getLogValues(s);
      double value = indices.getValue(logValues[2], Integer.parseInt(split[2]),
              Integer.parseInt(split[0]), Integer.parseInt(split[1]));
      newStockPriceMap.put(logValues[2], value);
    }

    Map<String, Double> newStockCostMap = new HashMap<>();
    for (Map.Entry<String, Double> entry : newStockPriceMap.entrySet()) {
      for (Stock stock : stocks) {
        if (entry.getKey().equals(stock.getTicker())) {
          newStockCostMap.put(entry.getKey(), stock.getShares() * entry.getValue());
          break;
        }
      }
    }

    // check if new cost of all stocks exceeded investmentTotal
    double totalStockCost = 0;
    for (Map.Entry<String, Double> entry : newStockCostMap.entrySet()) {
      totalStockCost += entry.getValue();
    }

    // build percentage distribution
    double[] percentageDistribution = new double[percentList.length];
    int percentIndex = 0;
    for (double percent : percentList) {
      percentageDistribution[percentIndex] = totalStockCost * percent / 100;
      percentIndex++;
    }

    // decide whether to buy or sell stocks
    percentIndex = 0;
    for (Map.Entry<String, Double> entry : newStockCostMap.entrySet()) {
      Double stockCost = entry.getValue();
      double percentageBasedStockCost = percentageDistribution[percentIndex++];
      double pricePerShare = getPricePerShare(newStockPriceMap, entry);

      if (stockCost - percentageBasedStockCost < 0) {
        // buy stocks
        double totalSharesToBuy = Math.abs(stockCost - percentageBasedStockCost) / pricePerShare;
        addStock(new StockImpl(totalSharesToBuy, entry.getKey(),
                indices.getIndex(entry.getKey()).getIndex()), date);
      } else {
        // sell stocks
        double totalSharesToSell = (stockCost - percentageBasedStockCost) / pricePerShare;
        deleteStock(entry.getKey(), totalSharesToSell, date);
      }
    }

    return new PortfolioImpl(this.name, stocks);
  }

  private double getPricePerShare(Map<String, Double> newStockPriceMap,
                                  Map.Entry<String, Double> entry) {
    double pricePerShare = 0;
    for (Map.Entry<String, Double> entryPrice : newStockPriceMap.entrySet()) {
      if (entry.getKey().equals(entryPrice.getKey())) {
        pricePerShare = entryPrice.getValue();
        break;
      }
    }
    return pricePerShare;
  }

  // retrieves the 6 values form a log in the order they are displayed
  // ex."BUY: 10, GOOG, 10/17/2022"
  // 0 - BUY
  // 1 - 10
  // 2 - GOOG
  // 3 - 10
  // 4 - 17
  // 5 - 2022
  private String[] getLogValues(String log) {

    //split off each purchase and take relevant info
    String[] components = log.split(",");
    String[] purchaseAndShares = components[0].split(" ");
    String purchase = purchaseAndShares[0];
    String shares = purchaseAndShares[1];
    String name = components[1].replaceAll("\\s", "");
    String[] dateString = components[2].split("/");
    //split date for validation
    String purchaseMonth = dateString[0].replaceAll("\\s", "");
    String purchaseDay = dateString[1].replaceAll("\\s", "");
    String purchaseYear = dateString[2].replaceAll("\\s", "");

    return new String[]{purchase, shares, name, purchaseMonth, purchaseDay, purchaseYear};
  }

}
