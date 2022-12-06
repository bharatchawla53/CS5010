package stockhw7.portfolio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import stockhw7.resources.FileIO;


/**
 * PortfolioImpl implements the portfolio interface
 * it holds a name of the type of portfolio and a List of
 * stocks.
 */
public class PortfolioImpl implements Portfolio {

  private String name;
  private List<Stock> stocks;

  /**
   * Constructor sets the given string to the name and initializes
   * a new array list.
   *
   * @param s the given name of a portfolio
   */
  public PortfolioImpl(String s) {
    this.name = s;
    this.stocks = new ArrayList<>();
  }

  /**
   * Constructor takes a string and a list of string
   * to set the name and the given list in the object.
   *
   * @param name the name of the portfolio
   * @param list the list of stocks given to the constructor.
   */
  public PortfolioImpl(String name, List<Stock> list) {
    this.name = name;
    this.stocks = list;
  }

  @Override
  public boolean save() throws IOException {
    FileIO.savePortfolio(stocks, name);
    String dir = System.getProperty("user.dir") + "/files/";
    File file = new File(dir + name + ".txt");
    return file.exists() && !file.isDirectory();
  }

  @Override
  public boolean load(String fileName) throws IOException {
    if (stocks.isEmpty()) {
      this.name = fileName;
      this.stocks = FileIO.loadPortfolio(fileName);
    } else {
      throw new IllegalArgumentException("Load cannot be run when portfolio contains "
              + "a usable list of Stocks");
    }
    return false;
  }

  @Override
  public double examineValue(int year, int month, int day) throws IllegalArgumentException {

    //    Calendar startDate = new GregorianCalendar();
    //    Calendar given = new GregorianCalendar(year,month - 1, day - 1,
    //            0, 0,0);
    //    if(given.get(Calendar.YEAR) < 1792 || given.compareTo(startDate) >= 0 ||
    //            given.get(Calendar.DAY_OF_WEEK) > 5 || month < 1 || month > 12 ) {
    //      throw new IllegalArgumentException("invalid date input: the year given is either "
    //              + "too large or too small");
    //    }
    if (stocks.isEmpty()) {
      return 0.0;
    }
    double accumulator = 0;
    for (Stock stock : stocks) {
      accumulator += stock.examineValue(year, month, day);
    }
    return accumulator;
  }

  @Override
  public void print() {
    System.out.print(this);
  }

  @Override
  public Portfolio addStock(Stock stock, String date) {
    this.stocks.add(stock);
    return new PortfolioImpl(this.name, stocks);
  }

  @Override
  public Portfolio deleteStock(String ticker, int num, String date) {
    if (num == 0) {
      for (Stock stock : stocks) {
        if (ticker.equals(stock.getTicker())) {
          this.stocks.remove(stock);
          return new PortfolioImpl(this.name, stocks);
        }
      }
    }
    for (Stock stock : stocks) {
      if (ticker.equals(stock.getTicker())) {
        if (stock.getShares() - num < 0) {
          throw new IllegalArgumentException("cant sell more stock than this has");
        }
        Stock newStock = new StockImpl(stock.getShares() - num,
                stock.getTicker(), stock.getIndex());
        this.stocks.remove(stock);
        this.stocks.add(newStock);
      }
      break;
    }
    return new PortfolioImpl(this.name, stocks);
  }


  @Override
  public List<Stock> getStocks() {
    return stocks;
  }

  @Override
  public String toString() {
    String accumulator = "PORTFOLIO " + name + "\n";
    for (Stock stock : stocks) {
      accumulator += stock.print() + "\n";
    }
    return accumulator.trim();
  }
}
