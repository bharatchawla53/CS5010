package stockhw6.controller;

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

import stockhw5.controller.StockController;
import stockhw5.controller.StockControllerImpl;
import stockhw5.model.FlexibleStockModel;
import stockhw5.model.IStockModelMaker;
import stockhw5.model.StockModel;
import stockhw5.model.User;
import stockhw5.view.FlexibleStockView;
import stockhw5.view.IStockViewMaker;
import stockhw5.view.StockView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for stock controller.
 */
public class StockControllerTest {
  /**
   * The MockStockModelImpl class represents user inflexible portfolio's and features supported for
   * it.
   */
  public static class MockStockModelImpl extends AbstractStockModel {
    private StringBuilder log;

    /**
     * Constructs an empty MockStockModelImpl constructor which initializes StringBuilder.
     */
    public MockStockModelImpl(StringBuilder sb) {
      super(sb);
      this.log = sb;
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

  }

  /**
   * The AbstractStockModel represents the common stock functionality between flexible and
   * inflexible.
   */
  public abstract static class AbstractStockModel implements StockModel {
    private StringBuilder log;

    public AbstractStockModel(StringBuilder sb) {
      log = sb;
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
      if (filePath != null && user != null) {
        return "saved ";
      }
      return null;
    }
  }

  /**
   * The MockFlexibleModelImpl represents user flexible portfolio's and features supported for it.
   */
  public static class MockFlexibleModelImpl extends AbstractStockModel
          implements FlexibleStockModel {

    private StringBuilder log;

    /**
     * Constructs an empty MockStockModelImpl constructor which initializes StringBuilder.
     *
     * @param sb string builder
     */
    public MockFlexibleModelImpl(StringBuilder sb) {
      super(sb);
      this.log = sb;
    }

    @Override
    public boolean buyStockOnSpecificDate(User user, String portfolioUUID,
                  String ticker, String noOfShares, String date) {
      return user != null && portfolioUUID != null && ticker != null
              && noOfShares != null && date != null;
    }

    @Override
    public boolean sellStockOnSpecifiedDate(User user, String portfolioUUID,
                   String ticker, String noOfShares, String date) {
      return user != null && portfolioUUID != null && ticker != null
              && noOfShares != null && date != null;
    }

    @Override
    public double calculateCostBasis(User user, String portfolioUUID, String date) {
      return 0;
    }

    @Override
    public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
      if (portfolioUUID != null && user != null && date != null) {
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public List<String> getPortfolioPerformance(String date1, String date2,
                       String portfolioUuid, User user) {
      if (date1 != null && date2 != null && portfolioUuid != null && user != null) {
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public boolean validateTickerShare(String tickerShare) {
      if (tickerShare != null) {
        Pattern ticketShareValidationPattern = Pattern
                .compile("([A-Z]+[,]\\d+[,]+\\d{4}-\\d{2}-\\d{2})");
        Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
        if (validator.matches()) {
          log.append(tickerShare).append(" ");
          return true;
        }
      }
      return false;
    }

  }

  /**
   * MockStockModelMaker represents both flexible and inflexible model interfaces.
   */
  public static class MockStockModelMaker implements IStockModelMaker {

    private final StockModel inflexibleModel;
    private final FlexibleStockModel flexibleModel;

    public MockStockModelMaker(StringBuilder sb) {
      inflexibleModel = new MockStockModelImpl(sb);
      flexibleModel = new MockFlexibleModelImpl(sb);
    }

    @Override
    public User getUserFromUsername(String username) {
      return inflexibleModel.getUserFromUsername(username);
    }

    @Override
    public boolean isUserNameExists(String username) {
      return inflexibleModel.isUserNameExists(username);
    }

    @Override
    public String generateUUID() {
      return inflexibleModel.generateUUID();
    }

    @Override
    public boolean validateInflexibleTickerShare(String tickerShare) {
      return inflexibleModel.validateTickerShare(tickerShare);
    }

    @Override
    public boolean isValidTicker(String inputTicker) {
      return inflexibleModel.isValidTicker(inputTicker);
    }

    @Override
    public boolean saveInflexibleStock(User user, String portfolioUuid,
                  String ticker, String noOfShares) {
      return inflexibleModel.saveStock(user, portfolioUuid, ticker, noOfShares);
    }

    @Override
    public List<String> getPortfolioContents(User user, String portfolioUuid) {
      return inflexibleModel.getPortfolioContents(user, portfolioUuid);
    }

    @Override
    public List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date) {
      return flexibleModel.portfolioCompositionFlexible(portfolioUUID, user, date);
    }

    @Override
    public Map<Integer, List<String>> calculateTotalValueOfAInflexiblePortfolio(
            String certainDate, User user, String portfolioUuid) {
      return inflexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
    }

    @Override
    public List<String> getPortfoliosForUser(User user) {
      return inflexibleModel.getPortfoliosForUser(user);
    }

    @Override
    public boolean validatePortfolioUUID(String portfolioUuid, User user) {
      return inflexibleModel.validatePortfolioUUID(portfolioUuid, user);
    }

    @Override
    public boolean validateInflexibleUserPortfolioExternalPathAndContentsStructure(
            String filepath) {
      return inflexibleModel.validateUserPortfolioExternalPathAndContentsStructure(filepath);
    }

    @Override
    public String saveExternalInflexibleUserPortfolio(String filepath, User user) {
      return inflexibleModel.saveExternalUserPortfolio(filepath, user);
    }

    @Override
    public User saveUser(User user) {
      return inflexibleModel.saveUser(user);
    }

    @Override
    public boolean validateFlexibleTickerShare(String tickerShare) {
      return flexibleModel.validateTickerShare(tickerShare);
    }

    @Override
    public boolean saveFlexibleStock(User user, String portfolioUuid, String ticker,
                           String noOfShares, String date) {
      return flexibleModel.buyStockOnSpecificDate(user, portfolioUuid, ticker, noOfShares, date);
    }

    @Override
    public boolean sellFlexibleStock(User user, String portfolioUuid, String ticker,
                            String noOfShares, String date) {
      return flexibleModel.sellStockOnSpecifiedDate(user, portfolioUuid, ticker, noOfShares, date);
    }

    @Override
    public Map<Integer, List<String>> calculateTotalValueOfAFlexiblePortfolio(String certainDate,
                                           User user, String portfolioUuid) {
      return flexibleModel.calculateTotalValueOfAPortfolio(certainDate, user, portfolioUuid);
    }

    @Override
    public double calculateCostBasis(User user, String portfolioUuid, String date) {
      return flexibleModel.calculateCostBasis(user, portfolioUuid, date);
    }

    @Override
    public boolean validateFlexibleUserPortfolioExternalPathAndContentsStructure(String filepath) {
      return flexibleModel.validateUserPortfolioExternalPathAndContentsStructure(filepath);
    }

    @Override
    public String saveExternalFlexibleUserPortfolio(String filepath, User user) {
      return flexibleModel.saveExternalUserPortfolio(filepath, user);
    }

    @Override
    public List<String> getFlexiblePortfolioPerformance(String date1, String date2,
                            String portfolioUuid, User user) {
      return flexibleModel.getPortfolioPerformance(date1, date2, portfolioUuid, user);
    }
  }

  /**
   * The MockStockViewImpl class represents user portfolio's view.
   */
  public static class MockStockViewImpl extends AbstractStockView {

    private final StringBuffer out;

    public MockStockViewImpl(StringBuffer out) {
      super(out);
      this.out = out;
    }

    @Override
    public void getUserOptionsView() {
      this.out.append("inflexible user options ");
    }

    @Override
    public void getPortfolioCreatorView() {
      this.out.append("inflexible portfolio creator ");
    }

    @Override
    public void getTableViewBuilder(List<String> rows, List<String> columns) {
      this.out.append("inflexible table builder ");
    }

  }

  /**
   * The AbstractStockView represents the common stock functionality between flexible and inflexible
   * views.
   */
  public abstract static class AbstractStockView implements StockView {

    private StringBuffer out;

    public AbstractStockView(StringBuffer out) {
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

  }

  /**
   * The MockFlexibleViewImpl represents user flexible portfolio's views.
   */
  public static class MockFlexibleViewImpl extends AbstractStockView implements FlexibleStockView {

    private final StringBuffer out;

    public MockFlexibleViewImpl(StringBuffer out) {
      super(out);
      this.out = out;
    }

    @Override
    public void getPortfolioTypeView() {
      this.out.append("portfolio types ");
    }

    @Override
    public void getFlexibleSellStockView() {
      this.out.append("flexible sell stock view ");
    }

    @Override
    public void getFlexibleCompositionView() {
      this.out.append("flexible composition view ");
    }

    @Override
    public void getCostBasisView() {
      this.out.append("flexible cost basis view ");
    }

    @Override
    public void getFlexiblePerformanceView() {
      this.out.append("flexible performance view ");
    }

    @Override
    public void getUserOptionsView() {
      this.out.append("flexible user options ");
    }

    @Override
    public void getPortfolioCreatorView() {
      this.out.append("flexible portfolio creator ");
    }

    @Override
    public void getTableViewBuilder(List<String> rows, List<String> columns) {
      this.out.append("flexible table builder ");
    }
  }

  /**
   * MockStockViewMaker represents both flexible and inflexible views interfaces.
   */
  public static class MockStockViewMaker implements IStockViewMaker {
    private final StockView inflexibleView;
    private final FlexibleStockView flexibleView;

    public MockStockViewMaker(StringBuffer out) {
      this.inflexibleView = new MockStockViewImpl(out);
      this.flexibleView = new MockFlexibleViewImpl(out);
    }

    @Override
    public void getLoginScreenView() {
      inflexibleView.getLoginScreenView();
    }

    @Override
    public void getBuilderView(List<String> values) {
      inflexibleView.getBuilderView(values);
    }

    @Override
    public void getUsernameInputView() {
      inflexibleView.getUsernameInputView();
    }

    @Override
    public void terminateView() {
      inflexibleView.terminateView();
    }

    @Override
    public void getInflexibleUserOptionsView() {
      inflexibleView.getUserOptionsView();
    }

    @Override
    public void getInflexiblePortfolioCreatorView() {
      inflexibleView.getPortfolioCreatorView();
    }

    @Override
    public void getInflexibleTableViewBuilder(List<String> rows, List<String> cols) {
      inflexibleView.getTableViewBuilder(rows, cols);
    }

    @Override
    public void getFlexibleTableViewBuilder(List<String> rows, List<String> cols) {
      flexibleView.getTableViewBuilder(rows, cols);
    }

    @Override
    public void getTotalPortfolioValueView() {
      inflexibleView.getTotalPortfolioValueView();
    }

    @Override
    public void getProgressBarView(int i) {
      inflexibleView.getProgressBarView(i);
    }

    @Override
    public void getPortfolioFilePathHeaderView() {
      inflexibleView.getPortfolioFilePathHeaderView();
    }

    @Override
    public void getPortfolioFilePathInputView() {
      inflexibleView.getPortfolioFilePathInputView();
    }

    @Override
    public void getSavePortfolioFromUserView() {
      inflexibleView.getSavePortfolioFromUserView();
    }

    @Override
    public void getSavePortfolioFilePathInputView() {
      inflexibleView.getSavePortfolioFilePathInputView();
    }

    @Override
    public void getNewUserView() {
      inflexibleView.getNewUserView();
    }

    @Override
    public void getPortfolioHeaderView() {
      inflexibleView.getPortfolioHeaderView();
    }

    @Override
    public void getPortfolioIdInputView() {
      inflexibleView.getPortfolioIdInputView();
    }

    @Override
    public void getPortfolioTypeView() {
      flexibleView.getPortfolioTypeView();
    }

    @Override
    public void getFlexibleUserOptionsView() {
      flexibleView.getUserOptionsView();
    }

    @Override
    public void getFlexiblePortfolioCreatorView() {
      flexibleView.getPortfolioCreatorView();
    }

    @Override
    public void getFlexibleSellStockView() {
      flexibleView.getFlexibleSellStockView();
    }

    @Override
    public void getFlexibleCompositionView() {
      flexibleView.getFlexibleCompositionView();
    }

    @Override
    public void getCostBasisView() {
      flexibleView.getCostBasisView();
    }

    @Override
    public void getPortfolioPerformanceView() {
      flexibleView.getFlexiblePerformanceView();
    }
  }


  private StockController controller;
  private IStockModelMaker model;
  private IStockViewMaker view;
  private Reader in;
  private StringBuffer out;
  private StringBuilder sb;

  @Before
  public void setup() {
    out = new StringBuffer();
    sb = new StringBuilder();

    model = new MockStockModelMaker(sb);
    view = new MockStockViewMaker(out);

  }

  @Test
  public void testInvalidLoginScreenInputWithValidUsernameAndUserOption() {
    in = new StringReader("jj y bharath 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("bharath", sb.toString());
    assertEquals("login screen builder view username input portfolio types inflexible "
                    + "user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testInvalidUserNameInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("y tony 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony", sb.toString());
    assertEquals("login screen username input portfolio types inflexible user "
                    + "options terminate builder view ",
            out.toString());
  }

  @Test
  public void testInvalidUserNameLongerThan8InputWithValidLoginScreenAndUserOption() {
    in = new StringReader("n tonytesttest tony 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user builder view portfolio types inflexible "
                    + "user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testNewUserNameAlreadyExistsInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("n test tony 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user builder view portfolio types inflexible "
                    + "user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistedUserNameInputWithValidLoginScreenAndUserOption() {
    in = new StringReader("y test tony 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony", sb.toString());
    assertEquals("login screen username input builder view portfolio types inflexible "
                    + "user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testNewUserNameInputWithValidLoginScreenForNewUserAndUserOption() {
    in = new StringReader("n tony 1 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user "
                    + "options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithValidSequence() {
    in = new StringReader("n tony 1 1 DAL,10 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony DAL,10 AMZN,15 ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options inflexible "
                    + "portfolio creator builder view builder view builder view inflexible user "
                    + "options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithInValidSequenceOfFractionalShares() {
    in = new StringReader("n tony 1 1 DAL,10.5 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony AMZN,15 ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options "
                    + "inflexible portfolio creator builder view builder view builder "
                    + "view inflexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionOneWithInValidSequence() {
    in = new StringReader("n tony 1 1 DAL10 AMZN,15 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony AMZN,15 ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options "
                    + "inflexible portfolio creator builder view builder view builder "
                    + "view inflexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionTwoWithValidSequence() {
    in = new StringReader("n tony 1 2 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio "
                    + "header inflexible table builder portfolio id input inflexible table builder "
                    + "inflexible user options builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionTwoWithInValidSequence() {
    in = new StringReader("n tony 1 3 jg57 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f 2022-10-20 ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio "
                    + "header inflexible table builder portfolio id input inflexible table builder "
                    + "builder view total portfolio value inflexible table builder builder "
                    + "view inflexible user options builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionThreeWithValidSequence() {
    in = new StringReader("n tony 1 3 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f 2022-10-20 ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio "
                    + "header inflexible table builder portfolio id input total "
                    + "portfolio value inflexible "
                    + "table builder builder view inflexible user options builder "
                    + "view terminate builder view ",
            out.toString());
  }

  @Test(expected = DateTimeParseException.class)
  public void testExistingUserForOptionThreeWithInValidSequence() {
    in = new StringReader("n tony 1 3 23f 10-20-2022 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();
  }

  @Test
  public void testExistingUserForOptionThreeWithMaxApiLimit() {
    in = new StringReader("n tony 1 3 546fg 2022-10-20 23f 2022-10-20 DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 546fg 2022-10-20 executed once ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio "
                    + "header inflexible table builder portfolio id input "
                    + "total portfolio value progress "
                    + "bar inflexible table builder builder view inflexible user "
                    + "options builder view builder "
                    + "view builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFourWithValidSequence() {
    in = new StringReader("n tony 1 4 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio file "
                    + "path header inflexible table builder portfolio file path "
                    + "input builder view inflexible "
                    + "user options builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFourWithInValidSequence() {
    in = new StringReader("n tony 1 4 43gf 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options portfolio "
                    + "file path header inflexible table builder portfolio file "
                    + "path input builder view "
                    + "builder view inflexible user options builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFiveWithValidSequence() {
    in = new StringReader("n tony 1 5 /test.csv 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony /test.csv ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options save "
                    + "portfolio save portfolio file path builder view inflexible user options "
                    + "builder view builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionFiveWithInValidSequence() {
    in = new StringReader("n tony 1 5 /records.csv /test.csv 23f DONE 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony /test.csv ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options save portfolio "
                    + "save portfolio file path builder view builder view "
                    + "inflexible user options builder "
                    + "view builder view terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionSixWithValidSequence() {
    in = new StringReader("n tony 1 6 n 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options "
                    + "terminate inflexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForOptionSixWithInValidSequence() {
    in = new StringReader("n tony 1 6 fjsdhf 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options "
                    + "terminate builder view builder view builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForInvalidOptionWithValidOption() {
    in = new StringReader("n tony 1 8 6 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony ", sb.toString());
    assertEquals("login screen new user portfolio types inflexible user options "
                    + "inflexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionOneWithValidSequence() {
    in = new StringReader("n tony 2 1 DAL,10,2022-08-12 AMZN,15,2022-10-10 DONE 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony DAL,10,2022-08-12 AMZN,15,2022-10-10 ", sb.toString());
    assertEquals("login screen new user portfolio types flexible user options flexible portfolio "
                    + "creator builder view builder view builder view flexible user "
                    + "options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionTwoWithValidSequence() {
    in = new StringReader("n tony 2 2 23f DAL,10,2022-08-12 DONE 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f DAL,10,2022-08-12 ", sb.toString());
    assertEquals("login screen new user portfolio types flexible user options portfolio header "
                    + "inflexible table builder portfolio id input flexible "
                    + "sell stock view builder view "
                    + "builder view flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionThreeWithValidSequence() {
    in = new StringReader("n tony 2 3 23f 2022-08-12 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types flexible user options portfolio header "
                    + "inflexible table builder portfolio id input flexible "
                    + "composition view flexible table "
                    + "builder flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionFourWithValidSequence() {
    in = new StringReader("n tony 2 4 23f 2022-08-12 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f 2022-08-12 ", sb.toString());
    assertEquals("login screen new user portfolio types flexible user options "
                    + "portfolio header inflexible "
                    + "table builder portfolio id input total portfolio value "
                    + "inflexible table builder builder view "
                    + "flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionFiveWithValidSequence() {
    in = new StringReader("n tony 2 5 23f 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types flexible user"
                    + " options portfolio file path header "
                    + "inflexible table builder portfolio file path input builder "
                    + "view flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionSixWithValidSequence() {
    in = new StringReader("n tony 2 6 /test.csv 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony /test.csv ", sb.toString());
    assertEquals("login screen new user portfolio types flexible "
                    + "user options save portfolio save portfolio "
                    + "file path builder view flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionSevenWithValidSequence() {
    in = new StringReader("n tony 2 7 23f 2022-08-12 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types flexible "
                    + "user options portfolio header inflexible table "
                    + "builder portfolio id input flexible cost basis view builder "
                    + "view flexible user options terminate builder view ",
            out.toString());
  }

  @Test
  public void testExistingUserForFlexibleOptionEightWithValidSequence() {
    in = new StringReader("n tony 2 8 23f 2022-08-12 2022-10-12 9 y");
    controller = new StockControllerImpl(model, view, in);
    controller.start();

    assertEquals("tony 23f ", sb.toString());
    assertEquals("login screen new user portfolio types flexible "
                    + "user options portfolio header inflexible table "
                    + "builder portfolio id input flexible performance view "
                    + "builder view builder view flexible user "
                    + "options terminate builder view ",
            out.toString());
  }

}
