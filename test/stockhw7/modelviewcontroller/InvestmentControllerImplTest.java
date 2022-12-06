package stockhw7.modelviewcontroller;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import stockhw7.modelviewcontroller.controller.InvestmentController;
import stockhw7.modelviewcontroller.controller.InvestmentControllerImpl;
import stockhw7.modelviewcontroller.model.InvestmentModelFlexMock;
import stockhw7.modelviewcontroller.model.InvestmentModelImpl;
import stockhw7.modelviewcontroller.model.InvestmentModelMock;
import stockhw7.modelviewcontroller.view.InvestmentViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This tests the implementation of our controller.
 */
public class InvestmentControllerImplTest {


  //--------BASIC OPERATION TESTS------------------------
  //--COVERS INITIALIZATION

  @Test
  public void startUpTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    ctrl.runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //first line will be intro message
    assertEquals("Welcome to the PDP Stockbroking Service!", lines[0]);
    //next seven lines are recurring menu
    assertEquals("Would you like to work on a static or flexible portfolio?", lines[1]);
    assertEquals("Select 1 for static, 2 for flexible, or 0 to quit", lines[2]);
    //exit lines
    assertEquals("Would you like to work on another portfolio? Press Y if so.", lines[4]);
    assertEquals("Thank you for using the PDP Stockbroking Service. "
            + "Goodbye!", lines[5]);
  }

  //-----------BASIC STATIC PORTFOLIO OPERATION TESTS________________________
  //--COVERS BASIC STATIC METHODS AND CALLING OF SINGLE OPERATIONS

  //Test if add (with printing) works at controller level.
  @Test
  public void addTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 name 4 GOOG 5 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT name 123 ADD STOCK GOOG 128 PRINT STOCK ", log.toString());
    assertEquals("5 shares of GOOG successfully purchased.", lines[19]);
    assertEquals("", lines[20]);
    assertEquals("MOCK LIST", lines[21]);
  }

  //Test if delete works at controller level.
  @Test
  public void deleteTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 name 5 MOCK 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT name 123 DELETE STOCK MOCK 123 ", log.toString());
  }

  //Test if value functioning works at controller level.
  @Test
  public void valueTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 name 1 1/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT name 123 VALUE STOCK ", log.toString());
    assertEquals("999.999", lines[19]);
  }

  //Test if print works at controller level.
  @Test
  public void printTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 name 3 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT name 123 PRINT STOCK ", log.toString());
    assertEquals("MOCK LIST", lines[17]);
  }

  //Test if save works at controller level. Needs load/create to access.
  @Test
  public void saveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 2 test 2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("LOAD PORT test SAVE PORT ", log.toString());
    assertEquals("Portfolio saved.", lines[17]);
  }

  //Test if load works at controller level with associated menu shift.
  @Test
  public void loadTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 2 test 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("LOAD PORT test ", log.toString());
    assertEquals("Portfolio loaded.", lines[8]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[10]);
    assertEquals("1. View Value of Portfolio", lines[11]);
    assertEquals("2. Save Portfolio", lines[12]);
    assertEquals("3. Print portfolio", lines[13]);

  }

  //Testing portfolio generation at controller level with associated menu shift.
  @Test
  public void buildPortfolioTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 MOCK 6 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 123 ", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[17]);
    assertEquals("1. View Value of Portfolio", lines[18]);
    assertEquals("2. Save Portfolio", lines[19]);
    assertEquals("3. Print portfolio", lines[20]);
  }

  //Testing portfolio generation at controller level with associated menu shift.
  @Test
  public void dollarCostTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 2 GOOG AAPL 50 50 100 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[27]);
    assertEquals("GOOG -- 50.0%", lines[28]);
    assertEquals("AAPL -- 50.0%", lines[29]);
  }

  //--------BASIC FLEXIBLE PORTFOLIO TESTS------------------
  //--COVERS BASIC STATIC METHODS AND CALLING OF SINGLE OPERATIONS


  //--------COMPOUND OPERATIONS TESTS--------------------------
  //--MAKING SURE CONTROLLER WILL RESET AFTER EACH METHOD CALL
  //--INCLUDES INSTANCES OF BAD INPUTS RESETTING MENU

  //Test if menu replays after a sample operation
  @Test
  public void menuCycleTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 MOCK 5 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //certifying menu occurs after run
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[20]);
    assertEquals("1. View Value of Portfolio", lines[21]);
    assertEquals("2. Save Portfolio", lines[22]);
    assertEquals("3. Print portfolio", lines[23]);
    assertEquals("4. Purchase Stock", lines[24]);
    assertEquals("5. Sell Stock", lines[25]);
    assertEquals("6. Complete Creating Portfolio", lines[26]);
    assertEquals("Thank you for using the PDP Stockbroking Service. "
            + "Goodbye!", lines[28]);
  }

  //Test if selected multiple operations work.
  @Test
  public void addDeleteTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 MSFT 3 5 MOCK2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT test 123 ADD STOCK MSFT 126 "
            + "PRINT STOCK DELETE STOCK MOCK2 123 ", log.toString());
  }

  //Test if we can run the gamut of add assess delete assess
  @Test
  public void addValueDeleteValueTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 GOOG 3 1 1/1/2001 5 MOCK2 1 1/1/2001 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 123 ADD STOCK GOOG 126 PRINT STOCK VALUE STOCK"
            + " DELETE STOCK MOCK2 123 VALUE STOCK ", log.toString());
    assertEquals("999.999", lines[32]);
    assertEquals("999.999", lines[53]);
  }

  //Test if both types of invalid inputs correctly send back to original menu.
  @Test
  public void invalidInputThenQuitTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("15 q 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //ensuring menu starts
    assertEquals("Would you like to work on a static or flexible portfolio?", lines[1]);
    //reports bad input
    assertEquals("Error: provided selection is too long. Please try again.", lines[4]);
    //right back to menu
    assertEquals("Would you like to work on a static or flexible portfolio?", lines[6]);
    //another bad input
    assertEquals("Error: provided selection is out of "
            + "provided range. Please try again.", lines[9]);
    //back to menu
    assertEquals("Would you like to work on a static or flexible portfolio?", lines[11]);

  }

  //Test if invalid input works in second layer menu
  @Test
  public void invalidInputSecondMenuThenQuitTet() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 boo 15 q 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //ensuring menu starts
    assertEquals("Would you like to work on a static or flexible portfolio?", lines[1]);
    //second menu
    assertEquals("Please make a selection from the following choices, or press 0 to quit:",
            lines[4]);
    //reports bad input
    assertEquals("Error: provided selection is too long. Please try again.", lines[7]);
    //right back to menu
    assertEquals("Please make a selection from the following choices, or press 0 to quit:",
            lines[9]);
    //another bad input
    assertEquals("Error: provided selection is out of provided range. Please try again.",
            lines[17]);
    //back to menu
    assertEquals("Please make a selection from the following choices, or press 0 to quit:",
            lines[19]);
  }

  //Test if create can be followed by save in secondary menu.
  @Test
  public void createSaveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 MOCK 2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT MOCK 123 SAVE PORT ", log.toString());
    assertEquals("Portfolio saved.", lines[17]);
  }

  //Test if we can build a portfolio, create it, and save it.
  @Test
  public void AddCreateSaveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 MOCK 4 GOOG 1 2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT MOCK 123 ADD STOCK GOOG 124 PRINT STOCK SAVE PORT ",
            log.toString());
    assertEquals("Portfolio saved.", lines[30]);
  }

  //Test if we can do all relevant operations at once.
  @Test
  public void AddValueDeleteValueCreateSaveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 TEST 4 GOOG 3 1 1/1/2001 5 MOCK2 2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT TEST 123 ADD STOCK GOOG 126 PRINT STOCK VALUE "
                    + "STOCK DELETE STOCK MOCK2 123 SAVE PORT ",
            log.toString());
    assertEquals("Portfolio saved.", lines[51]);
  }

  //Test if we can do all relevant operations in the second menu specifically.
  @Test
  public void loadPrintValueSaveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 2 MOCK 1 1/1/2001 3 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("LOAD PORT MOCK VALUE STOCK PRINT STOCK ", log.toString());
  }

  //Test if invalid inputs interspersed are continued through
  @Test
  public void multipleInvalidInputsWithRegularInputTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test q 4 GOOG 3 q 1 1/1/2001 q 5 MOCK2 q 1 1/1/2001 q q 2 "
            + " 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 123 ADD STOCK GOOG 126 PRINT STOCK VALUE STOCK"
                    + " DELETE STOCK MOCK2 123 VALUE STOCK SAVE PORT ",
            log.toString());
    assertEquals("Portfolio saved.", lines[116]);
  }


  //----------ERROR TESTS------------------------
  //--COVERS LOADING AND IO ERRORS

  //Test if IO errors are appropriately captured (NON-MOCK TEST).
  @Test
  public void forcedIOError() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 2 MOCK 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    ctrl.runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //Error captured
    assertEquals("Error, Portfolio non-empty or non-viable file added.", lines[8]);
    //Menu reset
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[10]);
  }


  //Test if bad input for shares of stocks processes appropriately (NON-MOCK TEST).
  //Input for non-integer input as well as negative and fractional shares
  @Test
  public void badStockNumTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 MOCK 1/2 4 MOCK -1 4 MOCK p 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    ctrl.runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("Cannot accept this number of shares. Try again. ", lines[19]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[21]);
    assertEquals("Error, shares chosen zero or negative.", lines[30]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[31]);
    assertEquals("Cannot accept this number of shares. Try again. ", lines[40]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[42]);
  }

  //Test if bad input for BadStock processes appropriately (NON-MOCK TEST).
  @Test
  public void badStockNameTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 MOCK 1 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    ctrl.runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("The indicated ticker is invalid. Please try again.", lines[19]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[20]);
  }

  //Test if bad input for date processes appropriately.
  @Test
  public void badDateTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 1 fakedate 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 123 ", log.toString());
    //error captured
    assertEquals("Invalid date format.", lines[18]);
    //menu reset
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[19]);
  }

  //Test is valid date (holiday, weekend, etc.) that no stock information
  //exists for processes appropriately (NON-MOCK TEST).
  @Test
  public void goodDateIncompatibleTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 1 test 4 GOOG 1 1 12/25/2021 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    ctrl.runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    //Error captured
    assertEquals("Error, date selected does not contain data for all stocks in portfolio.",
            lines[31]);
    //Menu reset
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[33]);
  }

  //Tests specific for Flex functionalities

  //Test for costbasis to navigate correctly
  @Test
  public void costBasisBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 1 11/15/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 COST BASIS 2022 11 15",
            log.toString());
  }

  //Test for value to navigate correctly
  @Test
  public void valueBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 2 11/15/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 VALUE STOCK 15 11 2022",
            log.toString());
  }

  //Test for graph function to navigate to years correctly
  @Test
  public void graphBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 3 Y 2013 2021 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 PRINT GRAPH 2013 2021 0 0 0 0",
            log.toString());
  }

  //Test for graph handling month correctly
  @Test
  public void graphMonthBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 3 f 2013 Y 2 9 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 PRINT GRAPH 2013 2013 2 9 0 0",
            log.toString());
  }

  //Test for graph handling day correctly
  @Test
  public void graphDayBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 3 f 2013 f 6 2 9 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 PRINT GRAPH 2013 2013 6 6 2 9",
            log.toString());
  }

  //Test for multiple of these in a row
  @Test
  public void allViewsTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 1 1 5/5/2021 1 2 5/5/2021 1 3 f 2013 f 6 2 9 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 COST BASIS 2021 5 5VALUE STOCK "
                    + "5 5 2021PRINT GRAPH 2013 2013 6 6 2 9",
            log.toString());
  }

  //Tests specific for things Flex does differently

  //Test for separate creation criteria
  @Test
  public void creationBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 ",
            log.toString());
  }

  //Test for separate adding criteria
  @Test
  public void addingBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 4 GOOG 1 11/15/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 ADD STOCK 11/15/2022 GOOG 124 PRINT STOCK ",
            log.toString());
  }

  //Test for separate selling criteria
  @Test
  public void sellingBasicTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 5 GOOG 1 11/15/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 DELETE STOCK 11/15/2022 GOOG 124 ",
            log.toString());
  }

  //Test that a combination of all of these work as well
  @Test
  public void createBuyCostBasisGraphMonthSaveTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 test 10 4 GOOG 1 5/5/2016 1 1 5/10/2016 1 3 f 2013 Y 4"
            + " 10 2 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    String[] lines = out.toString().split("\n");
    assertEquals("ADD PORT test 10.0 123 ADD STOCK 5/5/2016 GOOG 124 PRINT "
                    + "STOCK COST BASIS 2016 5 10PRINT GRAPH 2013 2013 4 10 0 0SAVE PORT ",
            log.toString());
  }

  //Testing for validation of dollar cost info

  //Testing for incorrect number of stocks
  @Test
  public void dollarCostbadNumStockTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 -5 " +
            "2 GOOG AAPL 50 50 100 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[29]);
    assertEquals("GOOG -- 50.0%", lines[30]);
    assertEquals("AAPL -- 50.0%", lines[31]);
  }

  //Testing for incorrect name of stock
  @Test
  public void dollarCostbadStockTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 BOOG GOOG AAPL 50 50 100 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[29]);
    assertEquals("GOOG -- 50.0%", lines[30]);
    assertEquals("AAPL -- 50.0%", lines[31]);
  }

  //Testing for incorrect percentages -- too high
  @Test
  public void dollarCostbadPercentTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 150 50 50 100 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[29]);
    assertEquals("GOOG -- 50.0%", lines[30]);
    assertEquals("AAPL -- 50.0%", lines[31]);
  }

  //Testing for incorrect percentages -- sum to <100
  @Test
  public void dollarCostTooLowPercentTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 40 40 50 50 100 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[30]);
    assertEquals("GOOG -- 50.0%", lines[31]);
    assertEquals("AAPL -- 50.0%", lines[32]);
  }

  //Testing for incorrect start date
  @Test
  public void dollarCostBadStartTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 100 1/3/3005 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[30]);
    assertEquals("GOOG -- 50.0%", lines[31]);
    assertEquals("AAPL -- 50.0%", lines[32]);
  }

  //Testing for correct start date with no stock information (ex. holiday)
  @Test
  public void dollarCostGoodStartUnluckyDateTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 100 12/25/2021 1/3/2022 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[30]);
    assertEquals("GOOG -- 50.0%", lines[31]);
    assertEquals("AAPL -- 50.0%", lines[32]);
  }

  //Testing for incorrect end date
  @Test
  public void dollarCostBadEndTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 100 1/3/2022 30 1/2/2022 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[28]);
    assertEquals("GOOG -- 50.0%", lines[29]);
    assertEquals("AAPL -- 50.0%", lines[30]);
  }

  //Testing for no end date
  @Test
  public void dollarCostNoEndTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 100 1/3/2022 30 never 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/31/3005 every 30 days in the following ratios:", lines[27]);
    assertEquals("GOOG -- 50.0%", lines[28]);
    assertEquals("AAPL -- 50.0%", lines[29]);
  }

  //Testing for bad interval number
  @Test
  public void dollarCostBadIntervalTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 100 1/3/2022 -30 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[29]);
    assertEquals("GOOG -- 50.0%", lines[30]);
    assertEquals("AAPL -- 50.0%", lines[31]);
  }

  //Testing for bad money amount
  @Test
  public void dollarCostBadMoneyTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 1 MOCK 10 6 " +
            "2 GOOG AAPL 50 50 -100 100 1/3/2022 -30 30 12/1/2022 0 0");
    InvestmentController ctrl = new InvestmentControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    ctrl.runProgram(new InvestmentModelFlexMock(log, "123"), new InvestmentViewImpl(out));
    assertEquals("ADD PORT MOCK 10.0 123 DOLLAR COST 30 100.0", log.toString());
    String[] lines = out.toString().split("\n");
    assertEquals("Purchasing 100.0 worth of stocks below from 1/3/2022 " +
            "until 12/1/2022 every 30 days in the following ratios:", lines[31]);
    assertEquals("GOOG -- 50.0%", lines[32]);
    assertEquals("AAPL -- 50.0%", lines[33]);
  }
}

