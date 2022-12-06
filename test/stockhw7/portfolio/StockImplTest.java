package stockhw7.portfolio;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import stockhw7.resources.AlphaVantageDemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A StockImpl test to make sure the implementation
 * of Stock is working properly with correct constructors
 * and dates added.
 */
public class StockImplTest {

  private static final String stockData = AlphaVantageDemo.getStockValues("GOOG");
  private final Boolean notSetup = false;
  private final String name = "GOOG";
  private final double[] index = initializeGoogValues();
  private Stock stock1;
  private Stock stock2;

  @Before
  public void setup() {
    stock1 = new StockImpl(1, name, stockData);
    stock2 = new StockImpl(2, name, stockData);
    System.out.println(stockData);
  }

  @Test
  public void basicGetterTest() {
    stock2.print();
    assertEquals(name, stock2.getTicker());
  }

  //----------Constructor--------------
  //negativeShareTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeShareTest() {
    stock1 = new StockImpl(-1, name, stockData);
  }

  //constructorTest
  @Test
  public void constructorTest() {
    stock1 = new StockImpl(5, name, stockData);
    assertEquals(name, stock1.getTicker());
  }

  //----------ExamineValue-------------
  //invalidYearTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidYearTest() {
    stock1.examineValue(1000, 12, 01);
  }

  //negativeYearTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeYearTest() {
    stock1.examineValue(-1000, 12, 01);
  }

  //invalidMonthTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidMonthTest() {
    stock1.examineValue(2000, 15, 01);
  }

  //negativeMonthTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeMonthTest() {
    stock1.examineValue(2000, -10, 01);
  }

  //invalidDayTest
  @Test(expected = IllegalArgumentException.class)
  public void invalidDayTest() {
    stock1.examineValue(2000, 10, 32);
  }

  //negativeDayTest
  @Test(expected = IllegalArgumentException.class)
  public void negativeDayTest() {
    stock1.examineValue(2000, 9, -26);
  }

  //invalidMothDayTest
  @Test
  public void invalidMothDayTest() {
    for (int i = 29; i < 32; i++) {
      try {
        stock1.examineValue(2000, 2, i);
        fail();
      } catch (IllegalArgumentException e) {
        assert (true);
      }
    }
    for (int i = 1; i < 13; i++) {
      if (i == 4 || i == 6 || i == 9 || i == 11) {
        try {
          stock1.examineValue(2000, i, 31);
          fail();
        } catch (IllegalArgumentException e) {
          assert (true);
        }
      }
    }
  }

  //fuzzyExamineValueTest
  @Test
  public void fuzzyExamineValueTest() {
    //with google
    Random rand = new Random();

    assertEquals(93.54, stock1.examineValue(2022, 10, 27), .01);

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
        if (year == date[0] && month == date[1] && day == date[2]) {
          iVal = j;
        }
      }
      if (iVal != -1) {
        double result = stock1.examineValue(Integer.valueOf(year),
                Integer.valueOf(month), Integer.valueOf(day));
        assertEquals(index[pos - 1], result, 2);
      }
    }
  }

  //-------------ToString--------------
  //toStringTest
  @Test
  public void toStringTest() {
    assertEquals("1, GOOG\n" + stockData, stock1.toString());
  }

  //--------------Print----------------
  //printTest
  @Test
  public void printTest() {
    assertEquals("1, GOOG\n", stock1.print());
  }

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
        int stop = 0;
      }


      output[i - 1] = noDecimal / 100;
    }
    return output;
  }
}