package stockhw6.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stockhw5.model.AlphaVantageApi;
import stockhw5.model.AlphaVantageOutputSize;
import stockhw5.model.StockApi;
import stockhw5.model.StockApiResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for AlphaVantageApi.
 */
public class AlphaVantageApiTest {

  private static StockApi alphaVantageApi;
  private static List<HashMap<String, List<StockApiResponse>>> hashMapList;
  private static final String[] tickers = new String[]{
      "AAPL", "MSFT", "TSLA", "MRO", "DAL", "AMZN"};

  /**
   * It caches the API result for testing purposes.
   */
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
    assertEquals(6, hashMapList.size());

    for (Map.Entry<String, List<StockApiResponse>> entry
            : hashMapList.get(0).entrySet()) {
      assertEquals(tickers[0], entry.getKey());
      assertNotNull(entry.getValue());
    }
  }

  @Test
  public void getStockTradedValueWithFullOutputSize() {
    assertNotNull(hashMapList);
    assertEquals(6, hashMapList.size());

    for (Map.Entry<String, List<StockApiResponse>> entry
            : hashMapList.get(4).entrySet()) {
      assertEquals("DAL", entry.getKey());
      assertNotNull(entry.getValue());
    }
  }

  @Test
  public void getStockTradedValueMaxApiLimit() {
    assertNotNull(hashMapList);
    assertEquals(6, hashMapList.size());

    for (int i = 0; i < hashMapList.size(); i++) {
      for (Map.Entry<String, List<StockApiResponse>> entry
              : hashMapList.get(i).entrySet()) {
        assertEquals(tickers[i], entry.getKey());
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
