package stockhw7.modelviewcontroller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import stockhw7.modelviewcontroller.model.InvestmentModelFlex;
import stockhw7.modelviewcontroller.model.InvestmentModelFlexImpl;
import stockhw7.resources.AlphaVantageDemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * test the Investment Model Flex  implementation
 * that extends Investment Model.
 */
public class InvestmentModelFlexTest {


  private static final String stockData = AlphaVantageDemo.getStockValues("GOOG");
  private static final String stockData2 = AlphaVantageDemo.getStockValues("IBM");
  private InvestmentModelFlex model;

  //---------------Basic Tests

  //Constructor
  @Before
  public void setUp() {
    model = new InvestmentModelFlexImpl();
    model.createPortfolio("name", 10.0);
  }
  //Bad Constructor not possible since no constructor exists with arguments

  //Print empty Port
  @Test
  public void printModelTest() {
    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\n", s);
  }

  //Print filled Port
  @Test
  public void addStockAndprintFilledModelTest() {
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/15/2022\n", s);
  }

  //Adding negative shares returns error at model level -- handled in controller
  @Test
  public void addBadSharesToModelTest() throws IllegalArgumentException {
    try {
      model.addStock(-1, "GOOG", stockData, "");
      assertEquals("PORTFOLIO", model.printStocks());
      fail();
    } catch (Exception e) {
      //passes
    }
  }

  //Add bad Stock.
  //This will show up at the model level as adding a stock with no index.
  //Controller captures this error thrown at model level.
  @Test
  public void addBadStockToModelTest() throws IllegalArgumentException {
    try {
      model.addStock(1, "GOOG", "stockData", "");
      assertEquals("PORTFOLIO", model.printStocks());
      fail();
    } catch (Exception e) {
      //passes
    }
  }

  //Delete Stock (need to add one first)
  @Test
  public void deleteToModelTest() {
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/15/2022\n",
            model.printStocks());
    model.deleteStock("GOOG", 1, "date");
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/15/2022\n"
            + "SELL: 1, GOOG, date\n", model.printStocks());
  }

  //Delete stock not in list (does nothing ideally)
  @Test
  public void deleteNotInModelTest() {
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/15/2022\n",
            model.printStocks());
    model.deleteStock("BOOG", 0, "");
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/15/2022\n",
            model.printStocks());
  }

  //Load portfolio
  @Test
  public void loadPortfolioTest() throws IOException {
    model.loadPortfolio("testtest");
    assertEquals("FLEX PORTFOLIO testtest\n0.0\nBUY: 1, GOOG, 11/15/2022\n",
            model.printStocks());
  }

  //Load port that isn't there
  @Test
  public void loadBadPortfolioTest() throws IOException {
    try {
      model.loadPortfolio("test1");
      fail();
    } catch (Exception e) {
      //passes
    }
  }

  //Save portfolio
  @Test
  public void saveTest() throws IOException {
    model = new InvestmentModelFlexImpl();
    model.createPortfolio("testtest", 0);
    model.savePortfolio();
    model = new InvestmentModelFlexImpl();
    model.loadPortfolio("testtest");
    assertEquals("PORTFOLIO testtest", model.printStocks());
  }

  //Allow overwriting
  @Test
  public void saveOverwriteTest() throws IOException {
    model.createPortfolio("testtest", 0.0);
    model.savePortfolio();
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    model.savePortfolio();
    model = new InvestmentModelFlexImpl();
    model.loadPortfolio("testtest");
    assertEquals("PORTFOLIO testtest\n1, GOOG", model.printStocks());
  }

  //Put it all together
  @Test
  public void allOperationsTest() throws IOException {
    model.createPortfolio("test2", 0.0);
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    model.addStock(2, "IBM", stockData, "11/15/2022");
    model.deleteStock("GOOG", 1, "11/15/2022");
    model.savePortfolio();
    model = new InvestmentModelFlexImpl();
    model.loadPortfolio("test2");
    assertEquals("PORTFOLIO test2\n2, BOOG", model.printStocks());
  }

  //getValue of empty portfolio should return 0.0
  @Test
  public void noValueValueTest() {
    assertEquals("0.0",
            Double.toString(model.getValuePortfolio(2022, 11, 02)));
  }

  //getValue with good date
  @Test
  public void basicValueTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    assertEquals("89.0",
            Double.toString(model.getValuePortfolio(2022, 11, 02)));
  }

  //getValue with date too early -- should throw error
  @Test
  public void tooEarlyValueTest() throws IOException {
    try {
      model.addStock(1, "GOOG", stockData, "");
      assertEquals("89.16",
              Double.toString(model.getValuePortfolio(1022, 11, 02)));
    } catch (Exception e) {
      //passes
    }
  }

  //getValue with holiday -- should throw error
  @Test
  public void holidayValueTest() throws IOException {
    try {
      model.addStock(1, "GOOG", stockData, "");
      assertEquals("89.16",
              Double.toString(model.getValuePortfolio(2021, 12, 25)));
    } catch (Exception e) {
      //passes
    }
  }

  //getValue with weekend date -- should throw error
  @Test
  public void weekendValueTest() throws IOException {
    try {
      model.addStock(1, "GOOG", stockData, "");
      assertEquals("89.16",
              Double.toString(model.getValuePortfolio(2022, 10, 30)));
      fail();
    } catch (Exception e) {
      assert (true);
      //passes
    }
  }

  //Adding already existing stock on weekend
  @Test
  public void addingExistingStockTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/01/2022\n", s);
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    model.addStock(1, "GOOG", stockData, "11/05/2022");

    //shows up as repeat in portfolio
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
    //deals with value accurately
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));

  }


  //Adding already existing stock on holiday
  @Test
  public void addingExistingStockHolidayTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/01/2022\n", s);
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    model.addStock(1, "GOOG", stockData, "07/04/2022");

    //shows up as repeat in portfolio
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
    //deals with value accurately
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));

  }

  //Adding already existing stock in absolute future (ex. 1 year from today)
  @Test
  public void addingExistingStockFutureTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/01/2022\n", s);
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    model.addStock(1, "GOOG", stockData, "07/06/2023");

    //shows up as repeat in portfolio
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
    //deals with value accurately
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));

  }

  //Adding already existing stock in relative future (Later than last purchase dt, in actual past)
  @Test
  public void addingExistingStockRFutureTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/01/2022\n", s);
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    model.addStock(1, "GOOG", stockData, "11/15/2022");

    //shows up as repeat in portfolio
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/15/2022\n", model.printStocks());
    //deals with value accurately
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    assertEquals("198.0", Double.toString(
            model.getValuePortfolio(2022, 11, 16)));

  }

  //Adding stock that was just fully sold (does index hold?)
  @Test
  public void addingExistingStockFullSellTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    String s = model.printStocks();
    assertEquals("FLEX PORTFOLIO name\n10.0\nBUY: 1, GOOG, 11/01/2022\n", s);
    assertEquals("89.0", Double.toString(
            model.getValuePortfolio(2022, 11, 02)));
    model.deleteStock("GOOG", 1, "11/09/2022");

    //shows up as repeat in portfolio
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "SELL: 1, GOOG, 11/09/2022\n", model.printStocks());
    //deals with value accurately
    assertEquals("0.0", Double.toString(
            model.getValuePortfolio(2022, 11, 14)));
    model.addStock(1, "GOOG", stockData, "11/15/2022");
    assertEquals("99.0", Double.toString(
            model.getValuePortfolio(2022, 11, 16)));

  }

  //Error for adding fractional/negative stock
  @Test(expected = IllegalArgumentException.class)
  public void addingErrorTest() throws IOException {
    model.addStock(-1, "GOOG", stockData, "11/01/2022");
    assertEquals("FLEX PORTFOLIO name\n10.0\n", model.printStocks());

  }

  //Date checker for just added stock
  @Test
  public void addingExistingStockJustAddedTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.addStock(1, "GOOG", stockData, "10/30/2022");
    model.addStock(1, "GOOG", stockData, "11/01/2022");

    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
  }

  //Date checker for multiple stock addings
  @Test
  public void addingExistingStockMultiAddedTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.addStock(1, "GOOG", stockData, "10/30/2022");
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.addStock(1, "GOOG", stockData, "10/29/2022");
    model.addStock(1, "GOOG", stockData, "11/02/2022");


    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/02/2022\n", model.printStocks());
  }

  //Date checker for multiple stock addings/sellings
  @Test
  public void addingExistingStockSellAddedTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.addStock(1, "GOOG", stockData, "10/30/2022");
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.deleteStock("GOOG", 1, "11/02/2022");
    model.addStock(1, "GOOG", stockData, "10/29/2022");
    model.addStock(1, "GOOG", stockData, "11/02/2022");


    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "SELL: 1, GOOG, 11/02/2022\n"
            + "BUY: 1, GOOG, 11/02/2022\n", model.printStocks());
  }

  //Error for selling too much stock error capturing
  @Test(expected = IllegalArgumentException.class)
  public void SellingExistingStockTooMuchTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.deleteStock("GOOG", 10, "11/02/2022");

    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
  }

  //Error for selling fractional/negative stock (did I do this?)
  @Test(expected = IllegalArgumentException.class)
  public void sellingExistingStockNegativeTest() throws IOException {
    model.addStock(-1, "GOOG", stockData, "11/01/2022");
    model.deleteStock("GOOG", 10, "11/02/2022");

    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
  }

  //Error for selling too early for purchase date (relative past)
  //TODO: not sure how to do this one
  //Error for selling in absolute future (e.g. next year)
  @Test(expected = IllegalArgumentException.class)
  public void sellingExistingStockFutureTest() throws IOException {
    model.addStock(-1, "GOOG", stockData, "11/01/2022");
    model.deleteStock("GOOG", 10, "11/02/2022");

    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n", model.printStocks());
  }

  //Successfully selling stock and printing out new thing
  @Test
  public void SellingExistingStockSellAddedTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.addStock(1, "GOOG", stockData, "10/30/2022");
    model.addStock(1, "GOOG", stockData, "11/01/2022");
    model.deleteStock("GOOG", 1, "11/02/2022");
    model.addStock(1, "GOOG", stockData, "10/29/2022");
    model.addStock(1, "GOOG", stockData, "11/02/2022");


    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "BUY: 1, GOOG, 11/01/2022\n"
            + "SELL: 1, GOOG, 11/02/2022\n"
            + "BUY: 1, GOOG, 11/02/2022\n", model.printStocks());
  }

  //Cost Basis Tests
  @Test
  public void coastBasisTest() throws IOException {
    model.addStock(2, "GOOG", stockData, "11/01/2022");
    model.addStock(2, "GOOG", stockData, "11/03/2022");
    model.deleteStock("GOOG", 1, "11/07/2022");
    model.addStock(2, "GOOG", stockData, "11/09/2022");
    model.deleteStock("GOOG", 1, "11/14/2022");
    model.deleteStock("GOOG", 1, "11/16/2022");


    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 2, GOOG, 11/01/2022\n"
            + "BUY: 2, GOOG, 11/03/2022\n"
            + "SELL: 1, GOOG, 11/07/2022\n"
            + "BUY: 2, GOOG, 11/09/2022\n"
            + "SELL: 1, GOOG, 11/14/2022\n"
            + "SELL: 1, GOOG, 11/16/2022\n", model.printStocks());
    assertEquals(0.0, model.getCostBasis(2022, 10, 27), .01);
    assertEquals(196.0, model.getCostBasis(2022, 11, 2), .01);
    assertEquals(376.0, model.getCostBasis(2022, 11, 4), .01);
    assertEquals(386.0, model.getCostBasis(2022, 11, 8), .01);
    assertEquals(572.0, model.getCostBasis(2022, 11, 10), .01);
    assertEquals(582.0, model.getCostBasis(2022, 11, 15), .01);
    assertEquals(592.0, model.getCostBasis(2022, 11, 16), .01);
  }

  @Test
  public void coastBasisOtherStockTest() throws IOException {
    model.addStock(2, "GOOG", stockData, "11/01/2022");
    model.addStock(2, "GOOG", stockData, "11/03/2022");
    model.deleteStock("GOOG", 1, "11/07/2022");
    model.addStock(2, "IBM", stockData2, "11/07/2022");
    model.addStock(2, "GOOG", stockData, "11/09/2022");
    model.deleteStock("GOOG", 1, "11/14/2022");
    model.addStock(2, "IBM", stockData2, "11/14/2022");
    model.deleteStock("GOOG", 1, "11/16/2022");


    //shows up as repeat in portfolio skips invalid
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
            + "BUY: 2, GOOG, 11/01/2022\n"
            + "BUY: 2, GOOG, 11/03/2022\n"
            + "SELL: 1, GOOG, 11/07/2022\n"
            + "BUY: 2, IBM, 11/07/2022\n"
            + "BUY: 2, GOOG, 11/09/2022\n"
            + "SELL: 1, GOOG, 11/14/2022\n"
            + "BUY: 2, IBM, 11/14/2022\n"
            + "SELL: 1, GOOG, 11/16/2022\n", model.printStocks());
    assertEquals(0.0, model.getCostBasis(2022, 10, 27), .01);
    assertEquals(196.0, model.getCostBasis(2022, 11, 2), .01);
    assertEquals(376.0, model.getCostBasis(2022, 11, 4), .01);
    assertEquals(672.0, model.getCostBasis(2022, 11, 8), .01);
    assertEquals(858.0, model.getCostBasis(2022, 11, 10), .01);
    assertEquals(1166.0, model.getCostBasis(2022, 11, 15), .01);
    assertEquals(1176.0, model.getCostBasis(2022, 11, 16), .01);
  }

  //test dollar cost works and creates values correctly
  @Test
  public void dollarCostTest() throws IOException {
    String[] stockList = {"AAPL", "GOOG"};
    double[] percentList = {50, 50};
    model.dollarCostProcessing("1/3/2022", "12/1/2022", 30, 100,
            stockList, percentList);
    //ensure print works
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
                    + "DOLLAR COST: 0.5, 100.0, AAPL, 1/3/2022, 12/1/2022, 30 day interval:\n"
                    + "DOLLAR COST: 0.5, 100.0, GOOG, 1/3/2022, 12/1/2022, 30 day interval:\n",
            model.printStocks());
    //ensure value and cost basis are what they should be
    assertEquals(120.00, model.getCostBasis(2022, 1, 4), 0.01);
    assertEquals(100.00, model.getValuePortfolio(2022, 1, 3), 0.01);
  }

  //test dollar cost can recur successfully a few times
  @Test
  public void dollarCostRepeatTest() throws IOException {
    String[] stockList = {"AAPL", "GOOG"};
    double[] percentList = {50, 50};
    model.dollarCostProcessing("1/3/2022", "12/1/2022", 30, 100,
            stockList, percentList);
    //ensure print works
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
                    + "DOLLAR COST: 0.5, 100.0, AAPL, 1/3/2022, 12/1/2022, 30 day interval:\n"
                    + "DOLLAR COST: 0.5, 100.0, GOOG, 1/3/2022, 12/1/2022, 30 day interval:\n",
            model.printStocks());
    //ensure value and cost basis are what they should be
    assertEquals(240.00, model.getCostBasis(2022, 2, 4), 0.01);
    assertEquals(195.20585157919487, model.getValuePortfolio(2022, 2, 4), 0.01);
    //one more cycle
    assertEquals(360.00, model.getCostBasis(2022, 3, 4), 0.01);
    assertEquals(281.94545495720575, model.getValuePortfolio(2022, 3, 4), 0.01);
  }

  //test dollar cost ends correct with end date
  @Test
  public void dollarCostEndsTest() throws IOException {
    String[] stockList = {"AAPL", "GOOG"};
    double[] percentList = {50, 50};
    model.dollarCostProcessing("1/3/2022", "2/1/2022", 30, 100,
            stockList, percentList);
    //ensure print works
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
                    + "DOLLAR COST: 0.5, 100.0, AAPL, 1/3/2022, 2/1/2022, 30 day interval:\n"
                    + "DOLLAR COST: 0.5, 100.0, GOOG, 1/3/2022, 2/1/2022, 30 day interval:\n",
            model.printStocks());
    //ensure value and cost basis are what they should be
    assertEquals(120.00, model.getCostBasis(2022, 2, 4), 0.01);
    assertEquals(96.96491025788846, model.getValuePortfolio(2022, 2, 4), 0.01);
  }

  //test dollar cost works correctly with no end date
  @Test
  public void dollarCostNoEndTest() throws IOException {
    String[] stockList = {"AAPL", "GOOG"};
    double[] percentList = {50, 50};
    model.dollarCostProcessing("1/3/2022", "12/1/3005", 30, 100,
            stockList, percentList);
    //ensure print works
    assertEquals("FLEX PORTFOLIO name\n10.0\n"
                    + "DOLLAR COST: 0.5, 100.0, AAPL, 1/3/2022, 12/1/3005, 30 day interval:\n"
                    + "DOLLAR COST: 0.5, 100.0, GOOG, 1/3/2022, 12/1/3005, 30 day interval:\n",
            model.printStocks());
    //ensure value and cost basis are what they should be
    assertEquals(120.00, model.getCostBasis(2022, 1, 4), 0.01);
    assertEquals(100.50261347476844, model.getValuePortfolio(2022, 1, 4), 0.01);
  }


}
