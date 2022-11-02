package stockhw4.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stockhw4.model.StockModel;
import stockhw4.model.User;
import stockhw4.view.StockView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for stock controller.
 */
public class StockControllerTest {
  /**
   * The MockStockModelImpl class represents user portfolio's and features supported for it.
   */
  public static class MockStockModelImpl implements StockModel {
    private StringBuilder log;

    /**
     * Constructs an empty MockStockModelImpl constructor which initializes StringBuilder.
     */
    public MockStockModelImpl(StringBuilder sb) {
      this.log = sb;
    }

    @Override
    public User saveUser(User user) {
      if (user.getUserName() != null && user.getUserName().equals("tony")) {
        log.append(user.getUserName()).append(" ");
        return user;
      }
      return null;
    }

    @Override
    public boolean isUserNameExists(String userName) {
      if (userName != null && !userName.equals("test")) {
        log.append(userName);
        return true;
      }
      return false;
    }

    @Override
    public boolean isValidTicker(String ticker) {
      return ticker != null;
    }

    @Override
    public String generateUUID() {
      return UUID
              .randomUUID()
              .toString()
              .replace("-", "")
              .substring(0, 8);
    }

    @Override
    public boolean validateTickerShare(String tickerShare) {
      if (tickerShare != null) {
        Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+");
        Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
        if (validator.matches()) {
          log.append(tickerShare).append(" ");
          return true;
        }
      }
      return false;
    }

    @Override
    public List<String> getPortfoliosForUser(User user) {
      if (user.getUserName() != null) {
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public boolean validatePortfolioUUID(String portfolioUUID, User user) {
      if (portfolioUUID != null && user != null && (portfolioUUID.equals("23f")
              || portfolioUUID.equals("546fg"))) {
        log.append(portfolioUUID).append(" ");
        return true;
      }
      return false;
    }

    @Override
    public User getUserFromUsername(String username) {
      if (username != null) {
        return User.builder().userName(username).build();
      }
      return null;
    }

    @Override
    public boolean saveStock(User user, String portfolioUUID, String ticker, String noOfShares) {
      return user != null && portfolioUUID != null && ticker != null && noOfShares != null;
    }

    @Override
    public List<String> getPortfolioContents(User user, String portfolioUUID) {
      if (user != null && portfolioUUID != null) {
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate, User user,
                                                                      String portfolioUUID) {
      if (certainDate != null && user != null && portfolioUUID != null) {
        if (portfolioUUID.equals("23f") || log.toString().contains("executed once ")) {
          if (!log.toString().contains("2022-10-20")) {
            log.append(certainDate).append(" ");
          }
          return new HashMap<>() {
            {
              put(1, Collections.singletonList("AMZN,10,0.00 "));
            }
          };
        } else {
          log.append(certainDate).append(" ").append("executed once ");
          return new HashMap<>() {
            {
              put(2, Collections.singletonList("AMZN,10,0.00 "));
            }
          };
        }

      }
      return null;
    }

    @Override
    public boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath) {
      if (filePath != null && filePath.equals("/test.csv")) {
        log.append(filePath).append(" ");
        return true;
      }
      return false;
    }

    @Override
    public String saveExternalUserPortfolio(String filePath, User user) {
      if (filePath != null &&  user != null) {
        return "saved ";
      }
      return null;
    }
  }

  /**
   * The MockStockViewImpl class represents user portfolio's view.
   */
  public static class MockStockViewImpl implements StockView {

    private final StringBuffer out;

    public MockStockViewImpl(StringBuffer out) {
      this.out = out;
    }

    @Override
    public void getLoginScreenView() {
      this.out.append("login screen ");
    }

    @Override
    public void getUsernameInputView() {
      this.out.append("username input ");
    }

    @Override
    public void getPortfolioIdInputView() {
      this.out.append("portfolio id input ");
    }

    @Override
    public void getPortfolioFilePathHeaderView() {
      this.out.append("portfolio file path header ");
    }

    @Override
    public void getPortfolioFilePathInputView() {
      this.out.append("portfolio file path input ");
    }

    @Override
    public void getBuilderView(List<String> values) {
      this.out.append("builder view ");
    }

    @Override
    public void getSavePortfolioFromUserView() {
      this.out.append("save portfolio ");
    }

    @Override
    public void getSavePortfolioFilePathInputView() {
      this.out.append("save portfolio file path ");
    }

    @Override
    public void getProgressBarView(int index) {
      if (!this.out.toString().contains("progress bar ")) {
        this.out.append("progress bar ");
      }
    }

    @Override
    public void getNewUserView() {
      this.out.append("new user ");
    }

    @Override
    public void getUserOptionsView() {
      this.out.append("user options ");
    }

    @Override
    public void getPortfolioCreatorView() {
      this.out.append("portfolio creator ");
    }

    @Override
    public void getPortfolioHeaderView() {
      this.out.append("portfolio header ");
    }

    @Override
    public void getTotalPortfolioValueView() {
      this.out.append("total portfolio value ");
    }

    @Override
    public void terminateView() {
      this.out.append("terminate ");
    }

    @Override
    public void getTableViewBuilder(List<String> rows, List<String> columns) {
      this.out.append("table builder ");
    }
  }

  private StockController controller;
  private StockModel model;
  private StockView view;
  private Reader in;
  private StringBuffer out;
  private StringBuilder sb;

  @Before
  public void setup() {
    out = new StringBuffer();
    sb = new StringBuilder();

    model = new MockStockModelImpl(sb);
    view = new MockStockViewImpl(out);
  }

  @Test
  public void testInvalidLoginScreenInputWithValidUsernameAndUserOption() {
    in = new StringReader("jj y bharath 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("bharath", sb.toString());
    assertEquals("login screen builder view username input user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testInvalidUserNameInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("y tony 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony", sb.toString());
    assertEquals("login screen username input user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testInvalidUserNameLongerThan8InputWithValidLoginScreenAndUserOption() {
    in = new StringReader("n tonytesttest tony 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testNewUserNameAlreadyExistsInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("n test tony 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistedUserNameInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("y test tony 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony", sb.toString());
    assertEquals("login screen username input builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testNewUserNameInputWithValidLoginScreenForNewUserAndUserOption() {
    in = new StringReader("n tony 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithValidSequence() {
    in = new StringReader("n tony 1 DAL,10 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony DAL,10 AMZN,15 ", sb.toString());
    assertEquals("login screen new user user options portfolio creator builder view "
                    + "builder view builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithInValidSequenceOfFractionalShares() {
    in = new StringReader("n tony 1 DAL,10.5 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony AMZN,15 ", sb.toString());
    assertEquals("login screen new user user options portfolio creator builder view "
                    + "builder view builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithInValidSequence() {
    in = new StringReader("n tony 1 DAL10 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony AMZN,15 ", sb.toString());
    assertEquals("login screen new user user options portfolio creator builder view "
                    + "builder view builder view user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionTwoWithValidSequence() {
    in = new StringReader("n tony 2 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user user options portfolio header table builder "
                    + "portfolio id input table builder user options builder view "
                    + "terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionTwoWithInValidSequence() {
    in = new StringReader("n tony 3 jg57 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f 2022-10-20 ", sb.toString());
    assertEquals("login screen new user user options portfolio header table builder portfolio "
                    + "id input table builder builder view total portfolio value "
                    + "table builder builder "
                    + "view user options builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionThreeWithValidSequence() {
    in = new StringReader("n tony 3 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f 2022-10-20 ", sb.toString());
    assertEquals("login screen new user user options portfolio header table "
                    + "builder portfolio id input total portfolio value table builder builder view "
                    + "user options builder view terminate builder view ",
            out.toString());
  }

  @Test(expected = DateTimeParseException.class)
  public void testExistingUserForOptionThreeWithInValidSequence() {
    in = new StringReader("n tony 3 23f 10-20-2022 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();
  }

  @Test
  public void testExistingUserForOptionThreeWithMaxApiLimmit() {
    in = new StringReader("n tony 3 546fg 2022-10-20 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 546fg 2022-10-20 executed once ", sb.toString());
    assertEquals("login screen new user user options portfolio header table builder portfolio "
                    + "id input total portfolio value progress bar table builder "
                    + "builder view user options "
                    + "builder view builder view builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFourWithValidSequence() {
    in = new StringReader("n tony 4 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user user options portfolio file path header table "
                    + "builder portfolio file path input builder view user options builder view "
                    + "terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFourWithInValidSequence() {
    in = new StringReader("n tony 4 43gf 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user user options portfolio file path header table "
                    + "builder portfolio file path input builder view builder view user options "
                    + "builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFiveWithValidSequence() {
    in = new StringReader("n tony 5 /test.csv 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony /test.csv ", sb.toString());
    assertEquals("login screen new user user options save portfolio save portfolio "
                    + "file path builder view user options builder view builder view "
                    + "terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFiveWithInValidSequence() {
    in = new StringReader("n tony 5 /records.csv /test.csv 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony /test.csv ", sb.toString());
    assertEquals("login screen new user user options save portfolio save portfolio file path "
                    + "builder view builder view user options builder view builder view "
                    + "terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionSixWithValidSequence() {
    in = new StringReader("n tony 6 n 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user user options terminate user "
                    + "options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionSixWithInValidSequence() {
    in = new StringReader("n tony 6 fjsdhf 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user user options terminate builder view builder "
                    + "view builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForInvalidOptionWithValidOption() {
    in = new StringReader("n tony 8 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user user options builder view terminate builder view ",
            out.toString());
  }

}
