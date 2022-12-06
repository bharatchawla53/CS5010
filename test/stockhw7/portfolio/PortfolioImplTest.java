package stockhw7.portfolio;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import stockhw7.resources.AlphaVantageDemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A PortfolioImpl test to make sure the implimentation
 * of portfolio is working properly with correct constructors
 * and dates added.
 */
public class PortfolioImplTest {
  private static final String stockData = AlphaVantageDemo.getStockValues("GOOG");
  private final double[] index = initializeGoogValues();
  private Portfolio p1;
  private Portfolio p2;
  private Portfolio p3;

  @Before
  public void setup() {
    p1 = new PortfolioImpl("test");
    p2 = new PortfolioImpl("test");
    p3 = new PortfolioImpl("test");

    p2.addStock(new StockImpl(1, "GOOG", stockData), "");
    p3.addStock(new StockImpl(6, "GOOG", stockData), "");
    p3.addStock(new StockImpl(3, "IBM", stockData), "");
    p3.addStock(new StockImpl(10, "TSCO", stockData), "");
  }

  //----------Constructor--------------
  @Test(expected = IllegalArgumentException.class)
  public void Constructor() {
    p1.addStock(new StockImpl(1, "GOOG", stockData), "");
    p1.examineValue(2005, 12, 1);
    //assertEquals(p1.examineValue());
  }

  //---------------Save----------------
  //saveTest
  @Test
  public void saveTest() throws IOException {
    assertTrue(p2.save());
  }

  //---------------Load----------------
  //loadTest
  //changes the name if different name
  @Test
  public void loadTest() throws IOException {
    p2.save();
    p1.load("test");
    assertEquals(p2.toString(), p1.toString());
  }

  //failLoadTest
  @Test(expected = IllegalArgumentException.class)
  public void failLoadTest() throws IOException {
    p1.load("asdgasji");
  }

  //----------ExamineValue-------------
  //zeros?????????????
  //invalidYearTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidYearTest() {
    p2.examineValue(1000, 12, 1);
  }

  //negativeYearTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeYearTest() {
    p2.examineValue(-1000, 12, 1);
  }

  //invalidMonthTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidMonthTest() {
    p2.examineValue(2000, 15, 1);
  }

  //negativeMonthTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeMonthTest() {
    p2.examineValue(2000, -10, 1);
  }

  //invalidDayTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidDayTest() {
    p2.examineValue(2000, 10, 32);
  }

  //negativeDayTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeDayTest() {
    p2.examineValue(2000, 9, -26);
  }

  //invalidMothDayTest
  @Test
  public void invalidMothDayTest() {
    for (int i = 29; i < 32; i++) {
      try {
        p2.examineValue(2000, 2, i);
        fail();
      } catch (IllegalArgumentException e) {
        assert (true);
      }
    }
    for (int i = 1; i < 13; i++) {
      if (i == 4 || i == 6 || i == 9 || i == 11) {
        try {
          p2.examineValue(2000, i, 31);
          fail();
        } catch (IllegalArgumentException e) {
          assert (true);
        }
      }
    }

    p1.examineValue(2000, 2, 30);
  }

  //fuzzyExamineValueTest
  @Test
  public void fuzzyExamineValueTest() {
    //with google
    Random rand = new Random();

    assertEquals(93.54, p2.examineValue(2022, 10, 27), .01);

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
        assertEquals(index[pos - 1], result, 2);
      }
    }
  }

  //-------------ToString--------------
  //toStringTest
  @Test
  public void toStringTest() {
    assertEquals("PORTFOLIO test\n" + "1, GOOG", p2.toString());
  }

  //--------------Print----------------
  //printTest
  //printBasePortfolioTest
  //-------------AddStock--------------
  //addStockTest
  @Test
  public void addStockTest() {

    p1.addStock(new StockImpl(6, "GOOG", stockData), "");
    p1.addStock(new StockImpl(3, "IBM", stockData), "");
    p1.addStock(new StockImpl(10, "TSCO", stockData), "");
    assertEquals(p3.toString(), p1.toString());
  }
  //addBadStockTest
  //-----------DeleteStock-------------
  //deleteStockTest
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