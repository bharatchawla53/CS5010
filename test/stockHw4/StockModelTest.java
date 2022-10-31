package stockHw4;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import jdk.jfr.StackTrace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for StockModelImpl.
 */
public class StockModelTest {

  //private static AlphaVantageApi alphaVantageApi;
  private static StockModel stockModel;
  //private static List<HashMap<String, List<AlphaVantageApi.AlphaDailyTimeSeries>>> hashMapList;
  private final static String[] tickers = new String[]{"AAPL", "MSFT", "TSLA", "MRO", "DAL", "AMZN"};

  @BeforeClass
  public static void setUp() {
    //alphaVantageApi = new AlphaVantageApi();
    stockModel = new StockModelImpl();

/*    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[0]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[1]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[2]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[3]);
    alphaVantageApi.getStockTradedValue(AlphaVantageOutputSize.FULL.getInput(), tickers[4]);
    hashMapList = alphaVantageApi
            .getStockTradedValue(AlphaVantageOutputSize.COMPACT.getInput(), tickers[5]);*/
  }

  @Test
  public void testSaveUser() {
    User user = User.builder().userName("test").build();
    User actualUser = stockModel.saveUser(user);

    assertNotNull(actualUser);
    assertEquals(user.getUserName(), actualUser.getUserName());
    assertNotNull(actualUser.getId());
  }

  @Test
  public void testSaveUserForExistingUser() {
    User user = User.builder().userName("test").build();
    User actualUser = stockModel.saveUser(user);

    assertNull(actualUser);
  }

  @Test
  public void testUserNameDoesExists() {
    boolean userNameExists = stockModel.isUserNameExists("test");

    assertTrue(userNameExists);
  }

  @Test
  public void testUserNameDoesNotExists() {
    boolean userNameExists = stockModel.isUserNameExists("random");

    assertFalse(userNameExists);
  }

  @Test
  public void testValidTicker() {
    for (int i = 0; i < tickers.length; i++) {
      boolean validTicker = stockModel.isValidTicker(tickers[i]);
      assertTrue(validTicker);
    }
  }

  @Test
  public void testInvalidTicker() {
    boolean validTicker = stockModel.isValidTicker("random");
    assertFalse(validTicker);
  }

  @Test
  public void testGenerateUUID() {
    String uuid = stockModel.generateUUID();

    assertNotNull(uuid);
    assertEquals(8, uuid.length());
  }

  @Test
  public void testValidTickerShareSequence() {
    String tickerShare = "AMZN,50";
    boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

    assertTrue(isValidSequence);
  }

  @Test
  public void testInvalidTickerShareSequenceWithSpaces() {
    String tickerShare = "AMZN, 50";
    boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

    assertFalse(isValidSequence);
  }

  @Test
  public void testInvalidTickerShareSequenceWithNoComma() {
    String tickerShare = "AMZN 50";
    boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

    assertFalse(isValidSequence);
  }

  @Test
  public void testInvalidTickerShareReverseSequence() {
    String tickerShare = "50,AMZN";
    boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

    assertFalse(isValidSequence);
  }

  @Test
  public void testGetPortfoliosForUser() {

  }

  @Test
  public void testValidatePortfolioUUID() {

  }

  @Test
  public void testGetUserFromUsername() {

  }

  @Test
  public void testSaveStock() {

  }

  @Test
  public void testGetPortfolioContents() {

  }

  @Test
  public void testCalculateTotalValueOfAPortfolio() {

  }

  @Test
  public void testValidateUserPortfolioExternalPath() {

  }

  @Test
  public void testSaveExternalUserPortfolio() {

  }
}
