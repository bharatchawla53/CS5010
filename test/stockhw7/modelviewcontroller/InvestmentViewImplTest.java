package stockhw7.modelviewcontroller;

import org.junit.Test;

import java.io.IOException;

import stockhw7.modelviewcontroller.view.InvestmentView;
import stockhw7.modelviewcontroller.view.InvestmentViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This tests the implementation of our view.
 */
public class InvestmentViewImplTest {

  InvestmentView view;

  //Print mainMenu works
  @Test
  public void mainMenuTest() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.printMainMenu1();
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[0]);
    assertEquals("1. Begin Creating Portfolio", lines[1]);
    assertEquals("2. Load Portfolio", lines[2]);
  }

  //print staticMenu1 works
  @Test
  public void staticMenu1Test() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.printStaticMenu1();
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[0]);
    assertEquals("1. View Value of Portfolio", lines[1]);
    assertEquals("2. Save Portfolio", lines[2]);
    assertEquals("3. Print portfolio", lines[3]);
    assertEquals("4. Purchase Stock", lines[4]);
    assertEquals("5. Sell Stock", lines[5]);
    assertEquals("6. Complete Creating Portfolio", lines[6]);
  }

  //print staticMenu2 works
  @Test
  public void staticMenu2Test() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.printStaticMenu2();
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[0]);
    assertEquals("1. View Value of Portfolio", lines[1]);
    assertEquals("2. Save Portfolio", lines[2]);
    assertEquals("3. Print portfolio", lines[3]);
  }

  //print flex menu works
  @Test
  public void flexMenuTest() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.printFlexMenu1();
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[0]);
    assertEquals("1. View Values of Portfolio", lines[1]);
    assertEquals("2. Save Portfolio", lines[2]);
    assertEquals("3. Print portfolio", lines[3]);
    assertEquals("4. Purchase Stock", lines[4]);
    assertEquals("5. Sell Stock", lines[5]);
    assertEquals("6. Dollar Cost-Average", lines[6]);
  }

  //print general empty String
  @Test
  public void emptyStringTest() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.print("");
    assertEquals("", out.toString());
  }

  //print general filled String
  @Test
  public void filledStringTest() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.print("test");
    assertEquals("test", out.toString());
  }

  //Verify can do all three of these in any order without issue
  @Test
  public void allAtOnceTest() throws IOException {
    StringBuffer out = new StringBuffer();
    view = new InvestmentViewImpl(out);
    view.printMainMenu1();
    view.print("\n");
    view.printStaticMenu1();
    view.print("\n");
    view.printStaticMenu2();
    view.print("\n");
    view.printFlexMenu1();
    view.print("test");
    String[] lines = out.toString().split("\n");
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[0]);
    assertEquals("1. Begin Creating Portfolio", lines[1]);
    assertEquals("2. Load Portfolio", lines[2]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[4]);
    assertEquals("1. View Value of Portfolio", lines[5]);
    assertEquals("2. Save Portfolio", lines[6]);
    assertEquals("3. Print portfolio", lines[7]);
    assertEquals("4. Purchase Stock", lines[8]);
    assertEquals("5. Sell Stock", lines[9]);
    assertEquals("6. Complete Creating Portfolio", lines[10]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[12]);
    assertEquals("1. View Value of Portfolio", lines[13]);
    assertEquals("2. Save Portfolio", lines[14]);
    assertEquals("3. Print portfolio", lines[15]);
    assertEquals("Please make a selection from the following choices, "
            + "or press 0 to quit:", lines[17]);
    assertEquals("1. View Values of Portfolio", lines[18]);
    assertEquals("2. Save Portfolio", lines[19]);
    assertEquals("3. Print portfolio", lines[20]);
    assertEquals("4. Purchase Stock", lines[21]);
    assertEquals("5. Sell Stock", lines[22]);
    assertEquals("6. Dollar Cost-Average", lines[23]);
    assertEquals("test", lines[24]);
  }

  //Verify graph works with years as basis.
  @Test
  public void basicGraphTest() throws IOException {
    StringBuffer out = new StringBuffer();
    double[] valueSet = {37.4, 46.95, 55.81, 62.51, 85.78, 101.42, 157.11, 221.34, 337.6};
    view = new InvestmentViewImpl(out);
    view.printGraph(0, 0, 0, 0,
            2013, 2021, valueSet);
    String[] lines = out.toString().split("\n");
    assertEquals("Performance of Portfolio from 2013 to 2021", lines[0]);
    assertEquals("2013:***", lines[1]);
    assertEquals("2014:****", lines[2]);
    assertEquals("2015:*****", lines[3]);
    assertEquals("2016:******", lines[4]);
    assertEquals("2017:********", lines[5]);
    assertEquals("2018:**********", lines[6]);
    assertEquals("2019:***************", lines[7]);
    assertEquals("2020:**********************", lines[8]);
    assertEquals("2021:*********************************", lines[9]);
  }

  //Verify graph works with months as basis.
  @Test
  public void basicMonthGraphTest() throws IOException {
    StringBuffer out = new StringBuffer();
    double[] valueSet = {37.4, 46.95, 55.81, 62.51, 85.78, 101.42, 157.11, 221.34, 337.6};
    view = new InvestmentViewImpl(out);
    view.printGraph(0, 0, 3, 11, 2021,
            2021, valueSet);
    String[] lines = out.toString().split("\n");
    assertEquals("Performance of Portfolio from 3/2021 to 11/2021", lines[0]);
    assertEquals("MAR:***", lines[1]);
    assertEquals("APR:****", lines[2]);
    assertEquals("MAY:*****", lines[3]);
    assertEquals("JUN:******", lines[4]);
    assertEquals("JUL:********", lines[5]);
    assertEquals("AUG:**********", lines[6]);
    assertEquals("SEP:***************", lines[7]);
    assertEquals("OCT:**********************", lines[8]);
    assertEquals("NOV:*********************************", lines[9]);


  }

  //Verify using days passes for making a graph
  @Test
  public void basicDayGraphTest() throws IOException {
    StringBuffer out = new StringBuffer();
    double[] valueSet = {37.4, 46.95, 55.81, 62.51, 85.78, 101.42, 157.11, 221.34, 337.6};
    view = new InvestmentViewImpl(out);
    view.printGraph(3, 11, 6, 6, 2021,
            2021, valueSet);
    String[] lines = out.toString().split("\n");
    assertEquals("Performance of Portfolio from 6/3/2021 to 6/11/2021", lines[0]);
    assertEquals("03:***", lines[1]);
    assertEquals("04:****", lines[2]);
    assertEquals("05:*****", lines[3]);
    assertEquals("06:******", lines[4]);
    assertEquals("07:********", lines[5]);
    assertEquals("08:**********", lines[6]);
    assertEquals("09:***************", lines[7]);
    assertEquals("10:**********************", lines[8]);
    assertEquals("11:*********************************", lines[9]);
  }

  //Test for ratio scaling for stars
  @Test
  public void ratioScalingGraphTest() throws IOException {
    StringBuffer out = new StringBuffer();
    double[] valueSet = {37.4, 46.95, 55.81, 62.51, 85.78, 101.42, 157.11, 221.34, 3333337.6};
    view = new InvestmentViewImpl(out);
    view.printGraph(3, 11, 6, 6, 2021,
            2021, valueSet);
    String[] lines = out.toString().split("\n");
    assertEquals("Performance of Portfolio from 6/3/2021 to 6/11/2021", lines[0]);
    assertEquals("03:***", lines[1]);
    assertEquals("04:****", lines[2]);
    assertEquals("05:*****", lines[3]);
    assertEquals("06:******", lines[4]);
    assertEquals("07:********", lines[5]);
    assertEquals("08:**********", lines[6]);
    assertEquals("09:***************", lines[7]);
    assertEquals("10:**********************", lines[8]);
    assertEquals("11:*********************************", lines[9]);
  }

  //Test for star contraction when necessary
  @Test
  public void relativeScalingGraphTest() throws IOException {
    StringBuffer out = new StringBuffer();
    double[] valueSet = {3337.4, 3346.95, 3355.81, 3362.51, 3385.78, 4101.42, 5157.11,
        6221.34, 7337.6};
    view = new InvestmentViewImpl(out);
    view.printGraph(3, 11, 6, 6, 2021,
            2021, valueSet);
    String[] lines = out.toString().split("\n");
    assertEquals("Performance of Portfolio from 6/3/2021 to 6/11/2021", lines[0]);
    assertEquals("03:***", lines[1]);
    assertEquals("04:***", lines[2]);
    assertEquals("05:***", lines[3]);
    assertEquals("06:***", lines[4]);
    assertEquals("07:***", lines[5]);
    assertEquals("08:****", lines[6]);
    assertEquals("09:*****", lines[7]);
    assertEquals("10:******", lines[8]);
    assertEquals("11:*******", lines[9]);
    assertEquals("Scale: * = $1000", lines[10]);
  }
}

