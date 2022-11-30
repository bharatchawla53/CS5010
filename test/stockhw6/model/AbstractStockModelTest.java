package stockhw6.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link StockModel}s.
 */
public abstract class AbstractStockModelTest {

  protected abstract StockModel abstractStockModel();

  /**
   * The InflexibleStockModel class represents user inflexible portfolio's features.
   */
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

  /**
   * The FlexibleStockModelClass class represents user flexible portfolio's features.
   */
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
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }


      @Test
      public void testBuyStockFractional() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20.68668", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10.101", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "5", "2022-10-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock4Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(4, result.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }


      @Test
      public void testBuyStockOnFutureDay() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-12-12",commissionRate);
        assertFalse(isStock1Saved);
        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);


        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }


      @Test
      public void testBuyStockWithNegativeShares() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "-20", "2022-12-12",commissionRate);
        assertFalse(isStock1Saved);


        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }

      @Test
      public void testBuyStockOnInvalidDateFormat() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "202-12-12",commissionRate);
        assertFalse(isStock1Saved);
        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);


        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }

      @Test
      public void testBuyStockOnHoliday()
      {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20.68668", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10.101", "2021-09-05",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }



      @Test
      public void testBuyStockExistingPortfolio()
      {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20.68668", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);

        List<String> result1 = stockModel.getPortfolioContents(user, portfolioUUID);

        boolean isStock2Saved = stockModel.addBuyStockToPortfolio(user,
                portfolioUUID, "DAL", "10.101", "2021-09-05",commissionRate);
        assertTrue(isStock2Saved);




        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(2, result.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }


      @Test
      public void testSellStockOnFutureDay()
      {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "20", "2022-12-12",commissionRate);
        assertFalse(isStock1Saved);
        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);



        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }


      @Test
      public void testSellStockFractional()
      {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();

        boolean isStock1Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "20.595959", "2022-12-12",commissionRate);
        assertTrue(isStock1Saved);
        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);



        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }

      @Test
      public void testSellStockWithNegativeShares()
      {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();

        boolean isStock1Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "-20", "2022-12-12",commissionRate);
        assertFalse(isStock1Saved);




        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }

      @Test
      public void testSellStockOnInvalidDateFormat()
      {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();

        boolean isStock1Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "20", "202-12-12",commissionRate);
        assertFalse(isStock1Saved);
        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);



        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }






      @Test
      public void testSellStock() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        // buy stocks
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // sell stocks
        boolean isSold = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "10", "2022-11-01",commissionRate);
        assertTrue(isSold);

        List<String> updatedResult = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(4, updatedResult.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testSellStockAbsentTicker() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        // buy stocks
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // sell stocks
        boolean isSold = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "MSFT", "10", "2022-11-01",commissionRate);
        assertFalse(isSold);


        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testSellStockGreaterThanAvailable() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        // buy stocks
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // sell stocks
        boolean isSold = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "40", "2022-11-01",commissionRate);
        assertFalse(isSold);

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }



      @Test
      public void testSellOnHoliday()
      {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        // buy stocks
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2020-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-10-05",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock3Saved);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(3, result.size());

        // sell stocks
        boolean isSold = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "10", "2022-09-05",commissionRate);
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
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "5", "2022-08-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
      public void testCalculateCostBasisOfPlannedPortfolio() {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        List<String> tickerList = new ArrayList<String>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        tickerList.add("TSLA");
        String startDate="2021-12-27";
        String endDate = "2022-12-27";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;

        int capital = 1000;
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(25);
        weightList.add(25);
        weightList.add(25);
        boolean isSuccessful = stockModel.createPortfolioBasedOnPlan(user,portfolioUUID,tickerList,startDate,
                endDate,daySkip,capital,weightList,commissionRate);

        assertTrue(isSuccessful);

        List<String> result = stockModel.getPortfolioContents(user, portfolioUUID);

        assertEquals(48, result.size());

        String date = "2022-12-27";
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

        assertEquals(expectedCostBasis, costBasis, 0.4);

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testCalculateCostBasisWithNoMatchingRecords() {
        User user = User.builder().userName("test").build();

        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "5", "2022-08-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "5", "2022-08-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
        int commissionRate = 2;
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "8", "2022-11-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
      public void testGetPortfolioPerformanceBetweenDays() {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2021-06-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "8", "2021-11-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2021-01-31",commissionRate);
        assertTrue(isStock4Saved);
        String date1 = "2021-10-10";
        String date2 = "2021-10-15";

        List<String> gContents = stockModel.getPortfolioPerformance(date1, date2,
                portfolioUUID, user);
        assertEquals(7, gContents.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);

      }

      @Test
      public void testGetPortfolioPerformanceBetweenMonths() {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "8", "2022-11-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock4Saved);
        String date1 = "2021-12-10";
        String date2 = "2022-06-15";

        List<String> gContents = stockModel.getPortfolioPerformance(date1, date2,
                portfolioUUID, user);

        assertEquals(8, gContents.size());
        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testGetPortfolioPerformanceBetweenYears() {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        String date1 = "2016-12-10";
        String date2 = "2022-06-15";
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "8", "2022-11-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock4Saved);

        List<String> gContents = stockModel.getPortfolioPerformance(date1, date2,
                portfolioUUID, user);

        assertEquals(7, gContents.size());

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testGetPortfolioPerformanceOverPastMonths() {
        User user = User.builder().userName("test").build();
        int commissionRate = 2;
        FlexibleStockModel stockModel = flexibleStockModel();
        String portfolioUUID = stockModel.generateUUID();
        String date1 = "2021-03-10";
        String date2 = "2021-11-15";
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.sellStockOnSpecifiedDate(user,
                portfolioUUID, "AAPL", "8", "2022-11-12",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock3Saved);
        boolean isStock4Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
        assertTrue(isStock4Saved);

        List<String> gContents = flexibleStockModel().getPortfolioPerformance(date1,
                date2, portfolioUUID, user);
        gContents = gContents.subList(0, gContents.size() - 2);

        for (int i = 0; i < gContents.size() - 1; i++) {
          int numStarsBefore = 0;
          int numStarsAfter = 0;
          if (gContents.get(i).split(":").length > 1) {
            numStarsBefore = gContents.get(i).split(":")[1].length();
          }
          if (gContents.get(i + 1).split(":").length > 1) {
            numStarsBefore = gContents.get(i + 1).split(":")[1].length();
          }

          assertTrue(numStarsAfter >= numStarsBefore);
        }

        // only for testing
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }


      //Test for portfolio creation, how to test for new portfolio created
      @Test
      public void testCreatePortfolioFromPlan()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        int commissionRate = 2;
        String endDate = "2021-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertTrue(isPlannedPortfolioCreated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(9, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }


      @Test
      public void testCreatePortfolioFromPlanRecurring()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2021-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        int commissionRate = 2;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertTrue(isPlannedPortfolioCreated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(9, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }

      @Test
      public void testCreatePortfolioFromPlanRecurringPast()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2020-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        int commissionRate = 2;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertFalse(isPlannedPortfolioCreated);


      }


      @Test
      public void testCreatePortfolioFromPlanPositiveCapital()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2021-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        int commissionRate = 2;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertTrue(isPlannedPortfolioCreated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(9, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }



      @Test
      public void testCreatePortfolioFromPlanNegativeCapital()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2021-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int commissionRate = 2;
        int capital = -3000;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertFalse(isPlannedPortfolioCreated);



      }

      @Test
      public void testCreatePortfolioFromPlanIncompleteWeights()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        int w1 = 25;
        int w2 = 30;
        int w3 = 40;
        weightList.add(w1);
        weightList.add(w2);
        weightList.add(w3);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2021-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        int commissionRate = 2;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);

        assertFalse(isPlannedPortfolioCreated);
        deleteFileOnlyForTesting(portfolioUUID, user, true);


      }



      @Test
      public void testCreatePortfolioFromPlanIntoFuture()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        int w1 = 25;
        int w2 = 30;
        int w3 = 45;
        weightList.add(w1);
        weightList.add(w2);
        weightList.add(w3);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2023-11-12";
        int daySkip = 0;
        int monthSkip = 1;
        int commissionRate = 2;
        int yearSkip = 0;
        int capital = 3000;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);

        assertTrue(isPlannedPortfolioCreated);
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }




      @Test
      public void testUpdatePortfolioBasedOnInvestment()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        int capital = 4000;
        int commissionRate = 2;
        boolean isPortfolioUpdated = stockModel.UpdatePortfolioBasedOnInvestment(user, portfolioUUID,
                tickerList, startDate, capital, weightList,commissionRate);
        assertTrue(isPortfolioUpdated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(3, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }



      @Test
      public void testUpdatePortfolioBasedOnInvestmentAbsentPortfolio()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();

        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        int capital = 4000;
        int commissionRate = 2;
        boolean isPortfolioUpdated = stockModel.UpdatePortfolioBasedOnInvestment(user, portfolioUUID,
                tickerList, startDate, capital, weightList,commissionRate);
        assertFalse(isPortfolioUpdated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(9, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testUpdatePortfolioBasedOnInvestmentPresentPortfolio()
      {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();
        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);
        String portfolioUUID = stockModel.generateUUID();

        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        int capital = 4000;
        int commissionRate = 2;
        boolean isPortfolioUpdated = stockModel.UpdatePortfolioBasedOnInvestment(user, portfolioUUID,
                tickerList, startDate, capital, weightList,commissionRate);
        assertTrue(isPortfolioUpdated);
        List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUUID);
        assertNotNull(portfolioContents);
        assertEquals(3, portfolioContents.size());
        deleteFileOnlyForTesting(portfolioUUID, user, true);
      }

      @Test
      public void testCalculateTotalValueOfAPortfolioUnderApiLimit() {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();

        String portfolioUUID = stockModel.generateUUID();
        int commissionRate = 2;
        // buy stocks
        boolean isStock1Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "AAPL", "20", "2022-10-12",commissionRate);
        assertTrue(isStock1Saved);
        boolean isStock2Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "DAL", "10", "2021-12-22",commissionRate);
        assertTrue(isStock2Saved);
        boolean isStock3Saved = stockModel.buyStockOnSpecificDate(user,
                portfolioUUID, "MRO", "30", "2022-01-31",commissionRate);
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
      public void testCalculateTotalValueOfAPlannedPortfolio() {
        User user = User.builder().userName("test").build();
        FlexibleStockModel stockModel = flexibleStockModel();

        String portfolioUUID = stockModel.generateUUID();



        List<Integer> weightList = new ArrayList<>();
        weightList.add(25);
        weightList.add(50);
        weightList.add(25);

        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("MRO");
        tickerList.add("DAL");
        String startDate = "2021-08-12";
        String endDate = "2021-11-12";
        int daySkip = 30;
        int monthSkip = 1;
        int yearSkip = 0;
        int capital = 3000;
        int commissionRate = 2;
        boolean isPlannedPortfolioCreated = stockModel.createPortfolioBasedOnPlan(user, portfolioUUID,
                tickerList, startDate,endDate,daySkip,capital,weightList,commissionRate);
        assertTrue(isPlannedPortfolioCreated);

        Map<Integer, List<String>> result = stockModel
                .calculateTotalValueOfAPortfolio("2021-10-30", user, portfolioUUID);


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
