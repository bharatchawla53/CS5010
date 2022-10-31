package stockHw4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for StockModelImpl.
 */
public class StockModelTest {

  private static StockModel stockModel;
  private final static String[] tickers = new String[]{"AAPL", "MSFT", "TSLA", "MRO", "DAL", "AMZN"};

  @BeforeClass
  public static void setUp() {
    stockModel = new StockModelImpl();
  }

  @Test
  public void testSaveUser() {
    User user = User.builder().userName("test").build();
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
    User actualUser = stockModel.saveUser(user);

    assertNull(actualUser);
  }

  @Test
  public void testUserNameDoesExists() {
    boolean userNameExists = stockModel.isUserNameExists("test1");

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
    User user = User.builder().userName("test").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertNotNull(portfoliosForUser);
    assertEquals(2, portfoliosForUser.size());
  }

  @Test
  public void testGetZeroPortfoliosForUser() {
    User user = User.builder().userName("test2").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertNotNull(portfoliosForUser);
    assertEquals(0, portfoliosForUser.size());
  }

  @Test
  public void testValidPortfolioUUID() {
    User user = User.builder().userName("test").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(2, portfoliosForUser.size());

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
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(0, portfoliosForUser.size());

    boolean validPortfolioId = stockModel.validatePortfolioUUID("ran2324", user);

    assertFalse(validPortfolioId);
  }

  @Test
  public void testGetUserFromUsername() {
    User user = stockModel.getUserFromUsername("test1");

    assertNotNull(user);
    assertNotNull(user.getId());
    assertNotNull(user.getUserName());
  }

  @Test
  public void testGetUserFromInvalidUsername() {
    User user = stockModel.getUserFromUsername("test5");

    assertNull(user);
  }

  @Test
  public void testSaveStock() {
    User user = User.builder().userName("test").build();
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
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(2, portfoliosForUser.size());

    String portfolioUuid = null;
    for (String portfolioId : portfoliosForUser) {
      if (!portfolioId.equals("3cf39b8a")) {
        portfolioUuid = portfolioId;
        break;
      }
    }

    List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioUuid);

    assertNotNull(portfolioContents);
    assertEquals(2, portfolioContents.size());
  }

  @Test
  public void testGetPortfolioContentsNoneExists() {
    User user = User.builder().userName("test1").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(0, portfoliosForUser.size());

    List<String> portfolioContents = stockModel.getPortfolioContents(user, "ran123");

    assertEquals(0, portfolioContents.size());
  }

  @Test
  public void testCalculateTotalValueOfAPortfolioUnderApiLimit() {
    User user = User.builder().userName("test").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(2, portfoliosForUser.size());

    String portfolioUuid = null;
    for (String portfolioId : portfoliosForUser) {
      if (!portfolioId.equals("3cf39b8a")) {
        portfolioUuid = portfolioId;
        break;
      }
    }

    Map<Integer, List<String>> result = stockModel
            .calculateTotalValueOfAPortfolio("2022-10-30", user, portfolioUuid);


    assertNotNull(result);
    for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
      Integer key = entry.getKey();
      int size = entry.getValue().size();
      assertEquals(String.valueOf(key), String.valueOf(size));
    }
  }

  @Test
  public void testCalculateTotalValueOfAPortfolioOverApiLimit() {
    User user = User.builder().userName("test").build();
    List<String> portfoliosForUser = stockModel.getPortfoliosForUser(user);

    assertEquals(2, portfoliosForUser.size());

    String portfolioUuid = null;
    for (String portfolioId : portfoliosForUser) {
      if (portfolioId.equals("3cf39b8a")) {
        portfolioUuid = portfolioId;
        break;
      }
    }

    Map<Integer, List<String>> result = stockModel
            .calculateTotalValueOfAPortfolio("2022-10-30", user, portfolioUuid);


    assertNotNull(result);

    for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
      Integer key = entry.getKey();
      int size = entry.getValue().size();
      assertNotEquals(String.valueOf(key), String.valueOf(size));
    }

  }

  @Test
  public void testValidUserPortfolioExternalPathAndContentsStructure() {
    String userFilePath = "testUserLoadFile.csv";
    boolean validFilePath = stockModel.validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

    assertTrue(validFilePath);
  }

  @Test
  public void testInvalidUserPortfolioExternalPath() {
    String userFilePath = "random.csv";
    boolean validFilePath = stockModel.validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

    assertFalse(validFilePath);
  }

  @Test
  public void testInvalidUserPortfolioExternalContentsStructure() {
    String userFilePath = "testUserLoadInvalidFile.csv";
    boolean validFilePath = stockModel.validateUserPortfolioExternalPathAndContentsStructure(userFilePath);

    assertFalse(validFilePath);
  }

  @Test
  public void testSaveExternalUserPortfolio() {
    User user = User.builder().userName("test").build();
    String userFilePath = "testUserLoadFile.csv";
    String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

    assertNotNull(portfolioIdSavedTo);

    List<String> portfolioContents = stockModel.getPortfolioContents(user, portfolioIdSavedTo);
    assertNotNull(portfolioContents);
    assertNotEquals(0, portfolioContents.size());

    // only for testing this action needs to be performed
    deleteFileOnlyForTesting(portfolioIdSavedTo, user);
  }

  @Test
  public void testSaveExternalUserPortfolioInvalidStructure() {
    User user = User.builder().userName("test").build();
    String userFilePath = "testUserLoadInvalidFile.csv";
    String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

    assertNull(portfolioIdSavedTo);
  }

  @Test
  public void testSaveExternalUserPortfolioInvalidFilePath() {
    User user = User.builder().userName("test").build();
    String userFilePath = "random.csv";
    String portfolioIdSavedTo = stockModel.saveExternalUserPortfolio(userFilePath, user);

    assertNull(portfolioIdSavedTo);
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
      if (!tempFile.renameTo(inFile))
        System.out.println("Could not rename file");

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void deleteFileOnlyForTesting(String portfolioUuid, User user) {
    String portfolioFileName = user.getUserName() + "_" + portfolioUuid + ".csv";
    File f = new File(portfolioFileName);
    if (f.delete()) {
      return;
    }
  }
}
