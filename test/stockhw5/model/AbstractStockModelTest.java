package stockhw5.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioUUID, user);
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
      deleteFileOnlyForTesting(portfolioIdSavedTo, user);
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

  }

  public static final class FlexibleStockModel extends AbstractStockModelTest {

    @Override
    protected StockModel abstractStockModel() {
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
  public void testValidPortfolioUUID() {
    User user = User.builder().userName("test").build();
    StockModel stockModel = abstractStockModel();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(1, portfoliosForUser.size());

    String portfolioUuid = null;
    for (String portfolioId : portfoliosForUser) {
      if (!portfolioId.equals("3cf39b8a")) {
        portfolioUuid = portfolioId;
        break;
      }
    }

    boolean validPortfolioId = stockModel.validatePortfolioUUID(portfolioUuid, user);

    assertTrue(validPortfolioId);
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

  void deleteFileOnlyForTesting(String portfolioUuid, User user) {
    String portfolioFileName = user.getUserName() + "_" + portfolioUuid + ".csv";
    File f = new File(portfolioFileName);
    if (f.delete()) {
      return;
    }
  }
}
