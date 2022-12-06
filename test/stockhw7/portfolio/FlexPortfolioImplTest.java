package stockhw7.portfolio;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import stockhw7.resources.AlphaVantageDemo;
import stockhw7.resources.IndicesImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This Class test the Flexible Portfolio implementation.
 */
public class FlexPortfolioImplTest {
  private static final String stockData = AlphaVantageDemo.getStockValues("GOOG");
  private final double[] index = initializeGoogValues();
  private Portfolio p1;
  private Portfolio p2;
  private Portfolio p3;

  @Before
  public void setup() {
    p1 = new FlexPortfolioImpl("test", 200);
    p2 = new FlexPortfolioImpl("test", 200);
    p3 = new FlexPortfolioImpl("test", 200);

    p2.addStock(new StockImpl(1, "GOOG", stockData), "11/14/2022");
    p3.addStock(new StockImpl(6, "GOOG", stockData), "11/16/2022");
    p3.addStock(new StockImpl(3, "IBM", stockData), "10/27/2021");
    p3.addStock(new StockImpl(10, "TSCO", stockData), "09/01/2020");
  }

  //----------Constructor--------------
  @Test
  public void Constructor() {
    p1.addStock(new StockImpl(1, "GOOG", stockData), "11/14/2022");
    p1.examineValue(2005, 12, 1);
    assertEquals(p1.toString(), p2.toString());
  }

  @Test
  public void ConstructorTwo() {
    Portfolio p4;
    p4 = new FlexPortfolioImpl("test", 200, new ArrayList<>(),
            new ArrayList<>(), new IndicesImpl());
    //p1.addStock(new StockImpl(1, "GOOG", stockData), "11/14/2022");
    //p1.examineValue(2005, 12, 1);
    assertEquals(p4.toString(), p1.toString());
  }

  //---------------Save----------------
  //saveTest
  @Test
  public void saveTest() throws IOException {
    assertTrue(p2.save());
  }

  //----------ExamineValue-------------

  //fuzzyExamineValueTest
  @Test
  public void fuzzyExamineValueTest() {
    //with google
    Random rand = new Random();

    assertEquals(99, p2.examineValue(2022, 11, 16), .01);

    for (int i = 0; i < 1000; i++) {
      String[] newLineData = stockData.split("\n");

      int pos = rand.nextInt(newLineData.length / 10 - 2) + 1;
      String[] posDate = newLineData[pos].split(",");

      String year = posDate[0].split("-")[0];
      String month = posDate[0].split("-")[1];
      String day = posDate[0].split("-")[2];

      int iVal = -1;

      for (int j = 1; j < newLineData.length; j++) {
        String[] date = newLineData[j].split(",")[0].split("-");
        if (Objects.equals(year, date[0]) && Objects.equals(month, date[1])
                && Objects.equals(day, date[2])) {
          iVal = j;
        }
      }
      if (iVal != -1) {
        double result = p2.examineValue(Integer.parseInt(year),
                Integer.parseInt(month), Integer.parseInt(day));
        assertEquals(99, p2.examineValue(2022, 11, 16), .01);
      }
    }
  }

  //-------------ToString--------------
  //toStringTest
  @Test
  public void toStringTest() {
    assertEquals("FLEX PORTFOLIO test\n200.0\n" + "BUY: 1, GOOG, 11/14/2022\n", p2.toString());
  }

  //--------------Print----------------
  //printTest
  //printBasePortfolioTest
  //-------------AddStock--------------
  //addBadStockTest
  @Test
  public void addStockTest() {

    p1.addStock(new StockImpl(6, "GOOG", stockData), "11/16/2022");
    p1.addStock(new StockImpl(3, "IBM", stockData), "10/27/2021");
    p1.addStock(new StockImpl(10, "TSCO", stockData), "09/01/2020");
    assertEquals(p3.toString(), p1.toString());
  }

  //-----------DeleteStock-------------
  //deleteStockTest
  @Test
  public void deleteStockTest() {

    p2.deleteStock("GOOG", 1, "11/16/2022");
    assertEquals("FLEX PORTFOLIO test\n200.0\n"
            + "BUY: 1, GOOG, 11/14/2022\n"
            + "SELL: 1, GOOG, 11/16/2022\n", p2.toString());
  }
  //deleteBadStockTest

  private double[] initializeGoogValues() {
    String[] newLineData = stockData.split("\n");
    double[] output = new double[newLineData.length - 1];

    for (int i = 1; i < newLineData.length - 1; i++) {
      String[] commaResult = newLineData[i].split(",");
      double noDecimal = 0;
      try {
        noDecimal = (Math.round(((Double.parseDouble(commaResult[2])
                + Double.parseDouble(commaResult[3])) / 2) * 100));
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new ArrayIndexOutOfBoundsException("out of bounds");
      }


      output[i - 1] = noDecimal / 100;
    }
    return output;
  }
}