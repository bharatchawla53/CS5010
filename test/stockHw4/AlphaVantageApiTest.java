package stockHw4;

import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for AlphaVantageApi.
 */
public class AlphaVantageApiTest {

  private static AlphaVantageApi alphaVantageApi;
  private static List<HashMap<String, List<AlphaVantageApi.AlphaDailyTimeSeries>>> hashMapList;
  private final static String[] tickers = new String[]{"AAPL", "MSFT", "TSLA", "MRO", "DAL", "AMZN"};

  @BeforeClass
  public static void setUp() {
    alphaVantageApi = new AlphaVantageApi();

    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[0]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[1]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[2]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[3]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.FULL.getInput(), tickers[4]);
    hashMapList = alphaVantageApi
            .getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[5]);
  }

  @Test
  public void getStockTradedValueWithCompactOutputSize() {
    assertNotNull(hashMapList);
    assertEquals(5, hashMapList.size());

    for(Map.Entry<String, List<AlphaVantageApi.AlphaDailyTimeSeries>> entry : hashMapList.get(0).entrySet()) {
      assertEquals(tickers[0], entry.getKey());
      assertEquals(1, entry.getValue().size());
      assertNotNull(entry.getValue());
    }
  }

  @Test
  public void getStockTradedValueWithFullOutputSize() {
    assertNotNull(hashMapList);
    assertEquals(5, hashMapList.size());

    for(Map.Entry<String, List<AlphaVantageApi.AlphaDailyTimeSeries>> entry : hashMapList.get(4).entrySet()) {
      assertEquals("DAL", entry.getKey());
      assertEquals(3902, entry.getValue().size());
      assertNotNull(entry.getValue());
    }
  }

  @Test
  public void getStockTradedValueMaxApiLimit() {
    assertNotNull(hashMapList);
    assertEquals(5, hashMapList.size());

    for (int i = 0; i < hashMapList.size(); i++) {
      for(Map.Entry<String, List<AlphaVantageApi.AlphaDailyTimeSeries>> entry : hashMapList.get(i).entrySet()) {
        assertEquals(tickers[i], entry.getKey());
        if (i == 4) {
          assertEquals(3902, entry.getValue().size());
        } else {
          assertEquals(1, entry.getValue().size());
        }
        assertNotNull(entry.getValue());
      }
    }

  }

  @Test
  public void dateParser() {
    LocalDate localDate = alphaVantageApi.dateParser("2022-10-31");
    assertNotNull(localDate);
  }

  @Test(expected = DateTimeParseException.class)
  public void dateParserInvalidDate() {
    alphaVantageApi.dateParser("2022-31-10");
  }
}
