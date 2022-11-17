package stockhw5.view;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStockViewTest {

  private static Appendable out;

  protected AbstractStockViewTest() {
    out = new StringBuffer();
  }

  protected abstract StockView abstractStockView();

  public static final class InflexibleStockViewClass extends AbstractStockViewTest {

    @Override
    protected StockView abstractStockView() {
      return new StockViewImpl(out);
    }

    @Test
    public void testGetUserOptionsView() {
      StockView stockView = abstractStockView();
      stockView.getUserOptionsView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("1. Would you like to create a portfolio with shares of one or more stock ? "
              + "Once done creating your portfolio, please enter \"DONE\" \n");
      consoleOp.add("2. Would you like to examine the composition of a portfolio ?\n");
      consoleOp.add("3. Would you like to determine the total value of a "
              + "portfolio on a certain date ?\n");
      consoleOp.add("4. Would you like to persist a portfolio so that it can be saved ?\n");
      consoleOp.add("5. Would you like to load an external portfolio?\n");
      consoleOp.add("6. Would you like to exit the application ?\n");
      consoleOp.add("Enter your option: \n");


      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetPortfolioCreatorView() {
      StockView stockView = abstractStockView();
      stockView.getPortfolioCreatorView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Let's create your portfolio!\n");
      consoleOp.add("Enter your preferred ticker and no of shares you'd like "
              + "to invest in this ticker "
              + "(Expected Format: ABC123,number of shares): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testCommandLineTable() {
      String s = "+--------+------------------+-------------+\n"
              + "| Ticker | Number of shares | Share Price |\n"
              + "+--------+------------------+-------------+\n"
              + "| MSFT   | 45               | 235.87      |\n"
              + "| GOOG   | 10               | 96.58       |\n"
              + "| AAPL   | 15               | 155.74      |\n"
              + "| DAL    | 10               | 34.67       |\n"
              + "+--------+------------------+-------------+\n";

      List<String> columns = new ArrayList<String>();
      columns.add("Ticker");
      columns.add("Number of shares");
      columns.add("Share Price");
      List<String> rows = new ArrayList<String>();
      rows.add("MSFT,45,235.87");
      rows.add("GOOG,10,96.58");
      rows.add("AAPL,15,155.74");
      rows.add("DAL,10,34.67");

      StockView stockView = abstractStockView();
      stockView.getTableViewBuilder(rows, columns);
      assertEquals(out.toString(), s);
    }


  }

  public static final class FlexibleStockViewClass extends AbstractStockViewTest {

    @Override
    protected StockView abstractStockView() {
      return new FlexibleStockViewImpl(out);
    }

    public FlexibleStockView flexibleStockView() {
      return new FlexibleStockViewImpl(out);
    }

    @Test
    public void testGetUserOptionsView() {
      StockView stockView = abstractStockView();
      stockView.getUserOptionsView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("1. Would you like to create a portfolio with shares of one or more stock on "
              + "a specific date ? "
              + "Once done creating your portfolio, please enter \"DONE\" \n");
      consoleOp.add("2. Would you like to sell a specific number of shares of a specific stock "
              + "on a specified date from a given portfolio ?\n");
      consoleOp.add("3. Would you like to examine the composition of a portfolio on a specific date ?\n");
      consoleOp.add("4. Would you like to determine the total value of a portfolio on a "
              + "certain date ?\n");
      consoleOp.add("5. Would you like to persist a portfolio so that it can be saved ?\n");
      consoleOp.add("6. Would you like to load an external portfolio?\n");
      consoleOp.add("7. Would you like to determine the cost basis of a portfolio by a specific date ?\n");
      consoleOp.add("8. Would you like to analyze your specific portfolio over time on a specified time range ?\n");
      consoleOp.add("9. Would you like to exit the application ?\n");
      consoleOp.add("Enter your option: \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetPortfolioCreatorView() {
      StockView stockView = abstractStockView();
      stockView.getPortfolioCreatorView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Let's create your portfolio!\n");
      consoleOp.add("Enter your preferred ticker and no of shares you'd like "
              + "to invest in this ticker "
              + "(Expected Format: ABC123,number of shares,date): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testCommandLineTable() {
      String s = "+--------+------------------+-------------+------------+\n"
              + "| Ticker | Number of shares | Share Price | Date       |\n"
              + "+--------+------------------+-------------+------------+\n"
              + "| GOOG   | 1                | 1519.67     | 2020-02-18 |\n"
              + "| AMZN   | 5                | 3467.42     | 2021-05-01 |\n"
              + "+--------+------------------+-------------+------------+\n";

      List<String> columns = new ArrayList<String>();
      columns.add("Ticker");
      columns.add("Number of shares");
      columns.add("Share Price");
      columns.add("Date");
      List<String> rows = new ArrayList<String>();
      rows.add("GOOG,1,1519.67,2020-02-18");
      rows.add("AMZN,5,3467.42,2021-05-01");

      StockView stockView = abstractStockView();
      stockView.getTableViewBuilder(rows, columns);
      assertEquals(out.toString(), s);
    }

    @Test
    public void testGetPortfolioTypeView() {
      FlexibleStockView stockView = flexibleStockView();
      stockView.getPortfolioTypeView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Which type of portfolio would you like to create: \n");
      consoleOp.add("1. Inflexible \n");
      consoleOp.add("2. Flexible \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetFlexibleSellStockView() {
      FlexibleStockView stockView = flexibleStockView();
      stockView.getFlexibleSellStockView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Enter your preferred ticker and no of shares you'd like "
              + "to sell on a specific date "
              + "(Expected Format: ABC123,number of shares,date): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetFlexibleCompositionView() {
      FlexibleStockView stockView = flexibleStockView();
      stockView.getFlexibleCompositionView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Please enter a date for which the portfolio composition will be calculated on "
              + "(Expected Date Format: yyyy-MM-dd): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetCostBasisView() {
      FlexibleStockView stockView = flexibleStockView();
      stockView.getCostBasisView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Please enter a date for which the portfolio cost basis will be calculated on "
              + "(Expected Date Format: yyyy-MM-dd): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

    @Test
    public void testGetFlexiblePerformanceView() {
      FlexibleStockView stockView = flexibleStockView();
      stockView.getFlexiblePerformanceView();
      List<String> consoleOp = new ArrayList<String>();
      consoleOp.add("\n");
      consoleOp.add("Please enter a specific date range to analyze how your portfolio has performed over time:  "
              + "(Expected Date Format: Enter date1 hit enter, and then enter date2 and hit enter again (yyyy-MM-dd)): \n");
      assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
    }

  }

  @Test
  public void testGetLoginScreenView() {
    StockView stockView = abstractStockView();
    stockView.getLoginScreenView();

    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Welcome to the portfolio manager\n");
    consoleOp.add("Are you a existing user or would you like to create a new user ?\n");
    consoleOp.add("Enter Y/N : \n");

    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetUsernameInputView() {
    StockView stockView = abstractStockView();
    stockView.getUsernameInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Enter your username: \n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetPortfolioIdInputView() {
    StockView stockView = abstractStockView();
    stockView.getPortfolioIdInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter a Portfolio ID from the above: \n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetPortfolioFilePathHeaderView() {
    StockView stockView = abstractStockView();
    stockView.getPortfolioFilePathHeaderView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Here are your Portfolio's!\n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetPortfolioFilePathInputView() {
    StockView stockView = abstractStockView();
    stockView.getPortfolioFilePathInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Enter the Portfolio UUID of the Portfolio you wish to serialize: \n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetBuilderView() {
    List<String> randomStrings = new ArrayList<String>();
    randomStrings.add("fmdslsaKLSKLAkslAKLSA");
    randomStrings.add("fmdsfdsikfdslAKLSA");
    randomStrings.add("fmdslsaKLSKfsuosfidAKLSA");

    StockView stockView = abstractStockView();
    stockView.getBuilderView(randomStrings);
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("fmdslsaKLSKLAkslAKLSA\n");
    consoleOp.add("fmdsfdsikfdslAKLSA\n");
    consoleOp.add("fmdslsaKLSKfsuosfidAKLSA\n");

    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetSavePortfolioFromUserView() {
    StockView stockView = abstractStockView();
    stockView.getSavePortfolioFromUserView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Let's save an external portfolio!\n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetSavePortfolioFilePathInputView() {
    StockView stockView = abstractStockView();
    stockView.getSavePortfolioFilePathInputView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter the filepath of the external portfolio:\n");
    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));

  }

  @Test
  public void testGetNewUserView() {
    StockView stockView = abstractStockView();
    stockView.getNewUserView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Get started with your portfolio account \n");
    consoleOp.add("Please enter an username and can't be longer than 8 characters: \n");

    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetPortfolioHeaderView() {
    StockView stockView = abstractStockView();
    stockView.getPortfolioHeaderView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Here are your Portfolios:\n");


    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testGetTotalPortfolioValueView() {
    StockView stockView = abstractStockView();
    stockView.getTotalPortfolioValueView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Please enter a date for which the portfolio value will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): \n");

    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testTerminateView() {
    StockView stockView = abstractStockView();
    stockView.terminateView();
    List<String> consoleOp = new ArrayList<String>();
    consoleOp.add("\n");
    consoleOp.add("Are you sure you want to exit the application ?\n");
    consoleOp.add("Enter Y/N : \n");

    assertEquals(out.toString(), consoleOpDialogCreator(consoleOp));
  }

  @Test
  public void testProgressBar() {
    char[] animationChars = new char[]{'|', '/', '-', '\\'};

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 100; i++) {
      StockView stockView = abstractStockView();
      stockView.getProgressBarView(i);
      if (i != 100) {
        sb.append("Calculating your portfolio worth: ")
                .append(i)
                .append("% ")
                .append(animationChars[i % 4])
                .append("\r");
      } else {
        sb.append("Processing: Done!");
      }
      assertEquals(out.toString(), sb.toString());
    }
  }


  protected String consoleOpDialogCreator(List<String> consoleOp) {
    String consoleOpDialog = "";
    for (String line : consoleOp) {
      consoleOpDialog += line;
    }
    return consoleOpDialog;
  }

}
