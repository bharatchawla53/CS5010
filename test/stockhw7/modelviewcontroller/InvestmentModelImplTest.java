package stockhw7.modelviewcontroller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import stockhw7.modelviewcontroller.model.InvestmentModel;
import stockhw7.modelviewcontroller.model.InvestmentModelImpl;
import stockhw7.resources.AlphaVantageDemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This tests the implementation of our model.
 */
public class InvestmentModelImplTest {

  private static final String stockData = AlphaVantageDemo.getStockValues("GOOG");
  private InvestmentModel model;

  //---------------Basic Tests

  //Constructor
  @Before
  public void setUp() {
    model = new InvestmentModelImpl();
    assertEquals("PORTFOLIO", model.printStocks());
  }

  //Bad Constructor not possible since no constructor exists with arguments

  //Print empty Port
  @Test
  public void printModelTest() {
    String s = model.printStocks();
    assertEquals("PORTFOLIO", s);
  }

  //Print filled Port
  @Test
  public void printFilledModelTest() {
    String s = model.printStocks();
    assertEquals("PORTFOLIO", s);
  }

  //Add stock
  @Test
  public void addStockToModelTest() {
    model.addStock(1, "GOOG", stockData, "");
    assertEquals("PORTFOLIO \n1, GOOG", model.printStocks());
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
    model.addStock(1, "GOOG", stockData, "");
    assertEquals("PORTFOLIO \n1, GOOG", model.printStocks());
    model.deleteStock("GOOG", 0, "date");
    assertEquals("PORTFOLIO", model.printStocks());
  }

  //Delete stock not in list (does nothing ideally)
  @Test
  public void deleteNotInModelTest() {
    model.addStock(1, "GOOG", stockData, "");
    assertEquals("PORTFOLIO \n1, GOOG", model.printStocks());
    model.deleteStock("BOOG", 0, "");
    assertEquals("PORTFOLIO \n1, GOOG", model.printStocks());
  }

  //Create portfolio
  @Test
  public void createPortfolioTest() {
    model.createPortfolio("test", 0.0);
    assertEquals("PORTFOLIO test", model.printStocks());
  }

  //Create portfolio when portfolio is not empty
  @Test
  public void createFilledPortfolioTest() throws IOException {
    model.addStock(1, "GOOG", stockData, "");
    model.createPortfolio("test", 0.0);
    model.savePortfolio();
    assertEquals("PORTFOLIO test\n1, GOOG", model.printStocks());
  }

  //Load portfolio
  @Test
  public void loadPortfolioTest() throws IOException {
    model.loadPortfolio("test");
    assertEquals("PORTFOLIO test\n1, GOOG", model.printStocks());
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
    model = new InvestmentModelImpl();
    model.createPortfolio("testtest", 0);
    model.savePortfolio();
    model = new InvestmentModelImpl();
    model.loadPortfolio("testtest");
    assertEquals("PORTFOLIO testtest", model.printStocks());
  }

  //Allow overwriting
  @Test
  public void saveOverwriteTest() throws IOException {
    model.createPortfolio("testtest", 0.0);
    model.savePortfolio();
    model.addStock(1, "GOOG", stockData, "");
    model.savePortfolio();
    model = new InvestmentModelImpl();
    model.loadPortfolio("testtest");
    assertEquals("PORTFOLIO testtest\n1, GOOG", model.printStocks());
  }

  //Put it all together
  @Test
  public void allOperationsTest() throws IOException {
    model.createPortfolio("test2", 0.0);
    model.addStock(1, "GOOG", stockData, "");
    model.addStock(2, "BOOG", stockData, "");
    model.deleteStock("GOOG", 0, "");
    model.savePortfolio();
    model = new InvestmentModelImpl();
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
    model.addStock(1, "GOOG", stockData, "");
    assertEquals("87.07",
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
    } catch (Exception e) {
      //passes
    }
  }


}