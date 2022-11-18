package stockhw5.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStockModelTest {

  protected abstract StockModel abstractStockModel();

  public static final class InflexibleStockModel extends AbstractStockModelTest {

    @Override
    protected StockModel abstractStockModel() {
      return new StockModelImpl();
    }

    @Test
    public void testValidTickerShareSequence() {
      String tickerShare = "AMZN,50";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertTrue(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareSequenceWithSpaces() {
      String tickerShare = "AMZN, 50";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareSequenceWithNoComma() {
      String tickerShare = "AMZN 50";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareReverseSequence() {
      String tickerShare = "50,AMZN";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testGetPortfoliosForUser() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isSaved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isSaved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);
      assertNotNull(result);
      assertEquals(1, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testGetZeroPortfoliosForUser() {
      User user = User.builder().userName("test2").build();
      StockModel stockModel = abstractStockModel();
      List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

      assertNotNull(portfoliosForUser);
      assertEquals(0, portfoliosForUser.size());
    }

    @Test
    public void testSaveStock() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isSaved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isSaved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);
      assertEquals(1, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testSaveMultipleStocks() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();

      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.saveStock(user, portfolioUUID, "DAL", "10");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.saveStock(user, portfolioUUID, "MRO", "30");
      assertTrue(isStock3Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(3, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testSaveStocksConsolidation() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();

      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "10");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "40");
      assertTrue(isStock3Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(1, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testGetPortfolioContents() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();

      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.saveStock(user, portfolioUUID, "DAL", "10");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.saveStock(user, portfolioUUID, "MRO", "30");
      assertTrue(isStock3Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(3, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testGetPortfolioContentsNoneExists() {
      User user = User.builder().userName("test1").build();
      StockModel stockModel = abstractStockModel();

      List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

      assertEquals(0, portfoliosForUser.size());

      List<String> portfolioContents = stockModel.getPortfolioContents(user, "ran123");

      assertEquals(0, portfolioContents.size());
    }

    @Test
    public void testCalculateTotalValueOfAPortfolioUnderApiLimit() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();

      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.saveStock(user, portfolioUUID, "AAPL", "20");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.saveStock(user, portfolioUUID, "DAL", "10");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.saveStock(user, portfolioUUID, "MRO", "30");
      assertTrue(isStock3Saved);


      Map<Integer, List<String>> result = stockModel
              .calculateTotalValueOfAPortfolio("2022-10-30", user, portfolioUUID);


      assertNotNull(result);
      for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
        Integer key = entry.getKey();
        int size = entry.getValue().size();
        assertEquals(String.valueOf(key), String.valueOf(size));
      }

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, false);
    }

    @Test
    public void testValidUserPortfolioExternalPathAndContentsStructure() {
      String userFilePath = "test/stockhw5/testUserLoadFile.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertTrue(validFilePath);
    }

    @Test
    public void testInvalidUserPortfolioExternalPath() {
      String userFilePath = "random.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertFalse(validFilePath);
    }

    @Test
    public void testInvalidUserPortfolioExternalContentsStructure() {
      String userFilePath = "test/stockhw5/testUserLoadInvalidFile.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertFalse(validFilePath);
    }

    @Test
    public void testSaveExternalUserPortfolio() {
      User user = User.builder().userName("test").build();
      String userFilePath = "test/stockhw5/testUserLoadFile.csv";
      StockModel stockModel = abstractStockModel();
      String portfolioIdSavedTo = stockModel
              .saveExternalUserPortfolio(userFilePath, user);

      assertNotNull(portfolioIdSavedTo);

      List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioIdSavedTo);
      assertNotNull(portfolioContents);
      assertEquals(4, portfolioContents.size());

      // only for testing this action needs to be performed
      deleteFileOnlyForTesting(portfolioIdSavedTo, user, false);
    }

    @Test
    public void testSaveExternalUserPortfolioInvalidStructure() {
      User user = User.builder().userName("test").build();
      String userFilePath = "test/stockhw5/testUserLoadInvalidFile.csv";
      StockModel stockModel = abstractStockModel();

      String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

      assertNull(portfolioIdSavedTo);
    }

    @Test
    public void testSaveExternalUserPortfolioInvalidFilePath() {
      User user = User.builder().userName("test").build();
      String userFilePath = "random.csv";
      StockModel stockModel = abstractStockModel();

      String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

      assertNull(portfolioIdSavedTo);
    }

    @Test
    public void testValidPortfolioUUID() {
      User user = User.builder().userName("test").build();
      StockModel stockModel = abstractStockModel();
      String userFilePath = "test/stockhw5/testUserLoadFile.csv";
      String portfolioIdSavedTo = stockModel
              .saveExternalUserPortfolio(userFilePath, user);

      assertNotNull(portfolioIdSavedTo);

      List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

      assertEquals(1, portfoliosForUser.size());

      String portfolioUuid = null;
      for (String portfolioId : portfoliosForUser) {
        portfolioUuid = portfolioId;
        break;

      }

      boolean validPortfolioId = stockModel.validatePortfolioUUID(portfolioUuid, user);

      assertTrue(validPortfolioId);

      // only for testing this action needs to be performed
      deleteFileOnlyForTesting(portfolioIdSavedTo, user, false);
    }

  }

  public static final class FlexibleStockModelClass extends AbstractStockModelTest {

    @Override
    protected StockModel abstractStockModel() {
      return new FlexibleStockModelImpl();
    }

    public FlexibleStockModel flexibleStockModel() {
      return new FlexibleStockModelImpl();
    }

    @Test
    public void testValidTickerShareSequence() {
      String tickerShare = "AMZN,50,2022-09-12";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertTrue(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareSequenceWithSpaces() {
      String tickerShare = "AMZN,50, 2022-08-12";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareSequenceWithNoComma() {
      String tickerShare = "AMZN 50 2022-09-12";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testInvalidTickerShareReverseSequence() {
      String tickerShare = "2022-05-12,50,AMZN";
      StockModel stockModel = abstractStockModel();
      boolean isValidSequence = stockModel.validateTickerShare(tickerShare);

      assertFalse(isValidSequence);
    }

    @Test
    public void testValidUserPortfolioExternalPathAndContentsStructure() {
      String userFilePath = "test/stockhw5/testFlexibleUserLoad.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertTrue(validFilePath);
    }

    @Test
    public void testInvalidUserPortfolioExternalPath() {
      String userFilePath = "random.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertFalse(validFilePath);
    }

    @Test
    public void testInvalidUserPortfolioExternalContentsStructure() {
      String userFilePath = "test/stockhw5/testFlexibleUserLoadInvalidFile.csv";
      StockModel stockModel = abstractStockModel();

      boolean validFilePath = stockModel
              .validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

      assertFalse(validFilePath);
    }

    @Test
    public void testBuyStock() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock3Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(3, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testBuyStockNoConsolidationOnSameDay() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "5", "2022-10-12");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock3Saved);
      boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock4Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, result.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testSellStock() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      // buy stocks
      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock3Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(3, result.size());

      // sell stocks
      boolean isSold = stockModel.sellStockOnSpecifiedDate(user, portfolioUUID, "AAPL", "10", "2022-11-01");
      assertTrue(isSold);

      List<String> updatedResult = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, updatedResult.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testCalculateCostBasis() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "5", "2022-08-12");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock3Saved);
      boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock4Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, result.size());

      String date = "2022-08-15";
      double expectedCostBasis = 0;

      for (String row : result) {
        String[] split = row.split(",");
        if (LocalDate.parse(split[3]).isBefore(LocalDate.parse(date))) {
          double totalShareValue = Double.parseDouble(split[1]) * Double.parseDouble(split[2]);
          double commission = totalShareValue * 0.10;
          expectedCostBasis += totalShareValue + commission;
        }
      }

      double costBasis = stockModel.calculateCostBasis(user, portfolioUUID, date);

      assertEquals(expectedCostBasis, costBasis, 0.2);

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testCalculateCostBasisWithNoMatchingRecords() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "5", "2022-08-12");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock3Saved);
      boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock4Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, result.size());

      String date = "2020-08-15";
      double expectedCostBasis = 0;

      for (String row : result) {
        String[] split = row.split(",");
        if (LocalDate.parse(split[3]).isBefore(LocalDate.parse(date))) {
          double totalShareValue = Double.parseDouble(split[1]) * Double.parseDouble(split[2]);
          double commission = totalShareValue * 0.10;
          expectedCostBasis += totalShareValue + commission;
        }
      }

      double costBasis = stockModel.calculateCostBasis(user, portfolioUUID, date);

      assertEquals(expectedCostBasis, costBasis, 0.2);

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testPortfolioCompositionFlexibleWithBuy() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "5", "2022-08-12");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock3Saved);
      boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock4Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, result.size());

      List<String> portfolioComposition = stockModel
              .portfolioCompositionFlexible(portfolioUUID, user, "2022-12-02");

      assertEquals(3, portfolioComposition.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testPortfolioCompositionFlexibleWithBuyAndSell() {
      User user = User.builder().userName("test").build();

      FlexibleStockModel stockModel = flexibleStockModel();
      String portfolioUUID = stockModel.generateUUID();

      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user, portfolioUUID, "AAPL", "8", "2022-11-12");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock3Saved);
      boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock4Saved);

      List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

      assertEquals(4, result.size());

      List<String> portfolioComposition = stockModel
              .portfolioCompositionFlexible(portfolioUUID, user, "2022-12-02");

      assertEquals(3, portfolioComposition.size());

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }

    @Test
    public void testGetPortfolioPerformance() {
      // TODO
    }

    @Test
    public void testCalculateTotalValueOfAPortfolioUnderApiLimit() {
      User user = User.builder().userName("test").build();
      FlexibleStockModel stockModel = flexibleStockModel();

      String portfolioUUID = stockModel.generateUUID();

      // buy stocks
      boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "AAPL", "20", "2022-10-12");
      assertTrue(isStock1Saved);
      boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "DAL", "10", "2021-12-22");
      assertTrue(isStock2Saved);
      boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user, portfolioUUID, "MRO", "30", "2022-01-31");
      assertTrue(isStock3Saved);

      Map<Integer, List<String>> result = stockModel
              .calculateTotalValueOfAPortfolio("2022-10-30", user, portfolioUUID);


      assertNotNull(result);
      for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
        Integer key = entry.getKey();
        int size = entry.getValue().size();
        assertEquals(String.valueOf(key), String.valueOf(size));
      }

      // only for testing
      deleteFileOnlyForTesting(portfolioUUID, user, true);
    }


    @Test
    public void testSaveExternalUserPortfolio() {
      User user = User.builder().userName("test").build();
      String userFilePath = "test/stockhw5/testFlexibleUserLoad.csv";
      StockModel stockModel = abstractStockModel();
      String portfolioIdSavedTo = stockModel
              .saveExternalUserPortfolio(userFilePath, user);

      assertNotNull(portfolioIdSavedTo);

      List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioIdSavedTo);
      assertNotNull(portfolioContents);
      assertEquals(4, portfolioContents.size());

      // only for testing this action needs to be performed
      deleteFileOnlyForTesting(portfolioIdSavedTo, user, true);
    }

    @Test
    public void testSaveExternalUserPortfolioInvalidStructure() {
      User user = User.builder().userName("test").build();
      String userFilePath = "test/stockhw5/testFlexibleUserLoadInvalidFile.csv";
      StockModel stockModel = abstractStockModel();

      String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

      assertNull(portfolioIdSavedTo);
    }

    @Test
    public void testSaveExternalUserPortfolioInvalidFilePath() {
      User user = User.builder().userName("test").build();
      String userFilePath = "random.csv";
      StockModel stockModel = abstractStockModel();

      String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

      assertNull(portfolioIdSavedTo);
    }

  }

  private static final String[] tickers = new String[]{
          "AAPL", "MSFT", "TSLA", "MRO", "DAL", "AMZN"};


  @Test
  public void testSaveUser() {
    User user = User.builder().userName("test").build();
    StockModel stockModel = abstractStockModel();
    User actualUser = stockModel.saveUser(user);

    assertNotNull(actualUser);
    assertEquals(user.getUserName(), actualUser.getUserName());
    assertNotNull(actualUser.getId());

    // delete user for testing
    removeUser(user);
  }

  @Test
  public void testSaveUserForExistingUser() {
    User user = User.builder().userName("test3").build();
    StockModel stockModel = abstractStockModel();
    User actualUser = stockModel.saveUser(user);

    assertNull(actualUser);
  }

  @Test
  public void testUserNameDoesExists() {
    StockModel stockModel = abstractStockModel();
    boolean userNameExists = stockModel.isUserNameExists("test1");

    assertTrue(userNameExists);
  }

  @Test
  public void testUserNameDoesNotExists() {
    StockModel stockModel = abstractStockModel();
    boolean userNameExists = stockModel.isUserNameExists("random");

    assertFalse(userNameExists);
  }

  @Test
  public void testValidTicker() {
    for (int i = 0; i < tickers.length; i++) {
      StockModel stockModel = abstractStockModel();
      boolean validTicker = stockModel.isValidTicker(tickers[i]);
      assertTrue(validTicker);
    }
  }

  @Test
  public void testInvalidTicker() {
    StockModel stockModel = abstractStockModel();
    boolean validTicker = stockModel.isValidTicker("random");
    assertFalse(validTicker);
  }

  @Test
  public void testGenerateUUID() {
    StockModel stockModel = abstractStockModel();
    String uuid = stockModel.generateUUID();

    assertNotNull(uuid);
    assertEquals(8, uuid.length());
  }


  @Test
  public void testInValidPortfolioUUID() {
    User user = User.builder().userName("test5").build();
    StockModel stockModel = abstractStockModel();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(0, portfoliosForUser.size());

    boolean validPortfolioId = stockModel.validatePortfolioUUID("ran2324", user);

    assertFalse(validPortfolioId);
  }

  @Test
  public void testGetUserFromUsername() {
    StockModel stockModel = abstractStockModel();
    User user = stockModel.getUserFromUsername("test1");

    assertNotNull(user);
    assertNotNull(user.getId());
    assertNotNull(user.getUserName());
  }

  @Test
  public void testGetUserFromInvalidUsername() {
    StockModel stockModel = abstractStockModel();
    User user = stockModel.getUserFromUsername("test54534");

    assertNull(user);
  }


  private void removeUser(User user) {
    String lineToRemove = user.getId() + "," + user.getUserName();
    try {
      File inFile = new File("users.csv");
      if (!inFile.isFile()) {
        System.out.println("Parameter is not an existing file");
        return;
      }

      //Construct the new file that will later be renamed to the original filename.
      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

      BufferedReader br = new BufferedReader(new FileReader("users.csv"));
      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

      String line = null;

      //Read from the original file and write to the new
      //unless content matches data to be removed.
      while ((line = br.readLine()) != null) {

        if (!line.trim().equals(lineToRemove)) {
          pw.println(line);
          pw.flush();
        }
      }
      pw.close();
      br.close();

      //Delete the original file
      if (!inFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }

      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(inFile)) {
        System.out.println("Could not rename file");
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  void deleteFileOnlyForTesting(String portfolioUuid, User user, boolean flexible) {
    String portfolioFileName = flexible
            ? user.getUserName() + "_" + portfolioUuid + "_fl_.csv"
            : user.getUserName() + "_" + portfolioUuid + ".csv";
    File f = new File(portfolioFileName);
    if (f.delete()) {
      return;
    }
  }
}
