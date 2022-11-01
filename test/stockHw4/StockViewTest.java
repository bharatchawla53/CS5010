package stockHw4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import java.io.InputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;


/**
 * Test class for StockViewImpl
 */

public class StockViewTest {
  private static StockView stockView;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;


  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @BeforeClass
  public static void setUp() {
    stockView = new StockViewImpl();
  }


  private String ConsoleOpDialogCreator(List<String> consoleOp) {
    String consoleOpDialog = "";
    for (String line : consoleOp) {
      consoleOpDialog += line;
    }
    return consoleOpDialog;
  }

  @Test
  public void testGetLoginScreenView() {
    stockView.getLoginScreenView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Welcome to the portfolio manager\n");
    consoleOp.add("Are you a existing user or would you like to create a new user ?\n");
    consoleOp.add("Enter Y/N : \n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetUsernameInputView() {
    stockView.getUsernameInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Enter your username: \n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetPortfolioIdInputView() {
    stockView.getPortfolioIdInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter a Portfolio ID from the above: \n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetPortfolioFilePathHeaderView() {
    stockView.getPortfolioFilePathHeaderView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Here are your Portfolio's!\n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetPortfolioFilePathInputView() {
    stockView.getPortfolioFilePathInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Enter the Portfolio UUID of the Portfolio you wish to serialize: \n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetBuilderView() {
    //TODO fuzyy testing
    List<String> randomStrings = new ArrayList<String>();
    randomStrings.add("fmdslsaKLSKLAkslAKLSA");
    randomStrings.add("fmdsfdsikfdslAKLSA");
    randomStrings.add("fmdslsaKLSKfsuosfidAKLSA");


    stockView.getBuilderView(randomStrings);
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("fmdslsaKLSKLAkslAKLSA\n");
    consoleOp.add("fmdsfdsikfdslAKLSA\n");
    consoleOp.add("fmdslsaKLSKfsuosfidAKLSA\n");

    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testUserInputView() {
    //TODO fuzzy testing
    String userInput = "ABCDE";
    InputStream in = new ByteArrayInputStream(userInput.getBytes());
    String val = stockView.getUserInputView(in);
    assertEquals(val, userInput);

  }

  @Test
  public void testGetSavePortfolioFromUserView() {
    stockView.getSavePortfolioFromUserView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Let's save an external portfolio!\n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }


  @Test
  public void testGetSavePortfolioFilePathInputView() {
    stockView.getSavePortfolioFilePathInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter the filepath of the external portfolio:\n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetNewUserView() {
    stockView.getNewUserView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Get started with your portfolio account \n");
    consoleOp.add("Please enter an username and can't be longer than 8 characters: \n");


    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetUserOptionsView() {
    stockView.getUserOptionsView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("1. Would you like to create a portfolio with shares of one or more stock ? " +
            "Once done creating your portfolio, please enter \"DONE\" \n");
    consoleOp.add("2. Would you like to examine the composition of a portfolio ?\n");
    consoleOp.add("3. Would you like to determine the total value of a portfolio on a certain date ?\n");
    consoleOp.add("4. Would you like to persist a portfolio so that it can be saved ?\n");
    consoleOp.add("5. Would you like to load an external portfolio?\n");
    consoleOp.add("6. Would you like to exit the application ?\n");
    consoleOp.add("Enter your option: \n");


    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }


  @Test
  public void testGetPortfolioCreatorView() {
    stockView.getPortfolioCreatorView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Let's create your portfolio!\n");
    consoleOp.add("Enter your preferred ticker and no of shares you'd like "
            + "to invest in this ticker "
            + "(Expected Format: ABC123,number of shares): \n");
    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetPortfolioHeaderView() {
    stockView.getPortfolioHeaderView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Here are your Portfolios:\n");


    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetTotalPortfolioValueView() {
    stockView.getTotalPortfolioValueView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter a date for which the portfolio value will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): \n");

    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }


  @Test
  public void testTerminateView() {
    stockView.terminateView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Are you sure you want to exit the application ?\n");
    consoleOp.add("Enter Y/N : \n");

    assertEquals(outContent.toString(), ConsoleOpDialogCreator(consoleOp));
  }


  @Test
  public void testCommandLineTable() {
    String s = "+--------+------------------+-------------+\n" +
            "| Ticker | Number of shares | Share Price |\n" +
            "+--------+------------------+-------------+\n" +
            "| MSFT   | 45               | 235.87      |\n" +
            "| GOOG   | 10               | 96.58       |\n" +
            "| AAPL   | 15               | 155.74      |\n" +
            "| DAL    | 10               | 34.67       |\n" +
            "+--------+------------------+-------------+\n";

    List<String> columns = new ArrayList<String>();
    columns.add("Ticker");
    columns.add("Number of shares");
    columns.add("Share Price");
    List<String> rows = new ArrayList<String>();
    rows.add("MSFT 45 235.87");
    rows.add("GOOG 10 96.58");
    rows.add("AAPL 15 155.74");
    rows.add("DAL 10 34.67");
    stockView.getTableViewBuilder(rows, columns);
    assertEquals(outContent.toString(), s);
  }

  @Test
  public void testProgressBar() {
    char[] animationChars = new char[]{'|', '/', '-', '\\'};

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 100; i++) {
      stockView.getProgressBarView(i);
      if (i != 100) {
        sb.append("Calculating your portfolio worth: ")
                .append(i)
                .append("% ")
                .append(animationChars[i % 4])
                .append("\r");
      } else {
        sb.append("Processing: Done!" + "\n");
      }
      assertEquals(outContent.toString(), sb.toString());
    }
  }
}
