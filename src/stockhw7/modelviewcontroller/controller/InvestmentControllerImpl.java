package stockhw7.modelviewcontroller.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import stockhw7.modelviewcontroller.model.InvestmentModel;
import stockhw7.modelviewcontroller.model.InvestmentModelFlex;
import stockhw7.modelviewcontroller.model.InvestmentModelFlexImpl;
import stockhw7.modelviewcontroller.model.InvestmentModelImpl;
import stockhw7.modelviewcontroller.view.InvestmentView;
import stockhw7.resources.Indices;
import stockhw7.resources.IndicesImpl;


//TODO test out new section to make sure all logic gates to validate data work correctly
//TODO split up dollar cost function

/**
 * This class represents the implementation of the controller. This class functions
 * to run the program and orient the view and model to information as necessary.
 */
public class InvestmentControllerImpl implements InvestmentController {
  final Readable in;
  final Appendable out;
  private final Indices indices;
  protected double lastCoastBasis;
  protected double lastValue;

  /**
   * Creates Controller implementation with associated input and output source/sink.
   *
   * @param in  Readable object used for collecting user input.
   * @param out Appendable object used for displaying model results.
   */
  public InvestmentControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
    this.indices = new IndicesImpl();
  }

  @Override
  public void runProgram(InvestmentModel model, InvestmentView view) throws IOException {
    //make sure object works
    Objects.requireNonNull(model);
    //keeps track of portfolio's mutability
    boolean stillMutable = true;
    //keep track of which portfolio type we are using
    boolean flexible = false;
    //initiate scanner and welcome user
    Scanner sc = new Scanner(this.in);
    view.print("Welcome to the PDP Stockbroking Service!\n");
    //Set int track and begin first menu loop
    int track = 9;
    //Split by which portfolio one would like to work on
    while (track != 0) {
      view.print("Would you like to work on a static or flexible portfolio?\n");
      view.print("Select 1 for static, 2 for flexible, or 0 to quit\n\n");
      String s = sc.next();
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      }
      if (track == 0) {
        runStaticVersion(model, view, sc);
        track = 0;
      } else if (track == 1) {
        if (!(model instanceof InvestmentModel)) {
          model = new InvestmentModelImpl();
        }
        runStaticVersion((InvestmentModel) model, view, sc);
        track = 0;
      } else if (track == 2) {
        if (!(model instanceof InvestmentModelFlex)) {
          model = new InvestmentModelFlexImpl();
        }
        runFlexibleVersion((InvestmentModelFlex) model, view, sc);
        track = 0;
      }
      //      switch (track) {
      //        case 1:
      //          runStaticVersion(model, view, sc);
      //          track = 0;
      //          break;
      //        case 2:
      //          if (!(model instanceof InvestmentModelFlex)) {
      //            model = new InvestmentModelFlexImpl();
      //          }
      //          runFlexibleVersion((InvestmentModelFlex) model, view, sc);
      //          track = 0;
      //          break;
      //      }
    }
    //Option to add new portfolio if wanted
    try {
      view.print("Would you like to work on another portfolio? Press Y if so.\n");
      String s = sc.next();
      if (s.equals("Y")) {
        this.runProgram(new InvestmentModelImpl(), view);
        return;
      }
      view.print("Thank you for using the PDP Stockbroking Service. Goodbye!");
    } catch (IOException e) {
      throw new IOException();
    }
  }

  /**
   * Runs a sub-controller that functionality only includes static model manipulation.
   *
   * @param model flexible portfolio to be processed.
   * @param view  Output view for responses/error throwing.
   * @param sc    Scanner object.
   * @throws IOException In case of I/O Errors.
   */
  private void runStaticVersion(InvestmentModel model, InvestmentView view, Scanner sc)
          throws IOException {
    int track = 9;
    boolean stillMutable = true;
    while (track != 0) {
      view.printMainMenu1();
      String s = sc.next();
      //verify input
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      }
      switch (track) {
        case 0:
          return;
        case 1:
          createPortfolio(model, sc, view, false);
          track = 0;
          break;
        case 2:
          loadPortfolio(model, sc, view);
          track = 0;
          break;
        default:
          break;
      }
    }
    //reset track and run Main Menu
    track = 9;
    while (track != 0) {
      view.printStaticMenu1();
      String s = sc.next();
      //verify input
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      } else {
        track = 9;
      }
      switch (track) {
        //kicks us out of main menu
        case 1:
          valueStock(model, sc, view);
          break;
        case 2:
          savePortfolio(model, sc, view);
          break;
        case 3:
          view.print(model.printStocks() + "\n");
          break;
        case 4:
          addStock(model, sc, view, false);
          break;
        case 5:
          sellStock(model, sc, view, false);
          break;
        case 6:
          stillMutable = false;
          track = 0;
          break;
        default:
      }
    }
    //secondary menu initiation if necessary
    track = 9;
    while (track != 0 && !stillMutable) {
      view.printStaticMenu2();
      String s = sc.next();
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      }
      switch (track) {
        case 1:
          valueStock(model, sc, view);
          break;
        case 2:
          savePortfolio(model, sc, view);
          break;
        case 3:
          view.print(model.printStocks() + "\n");
          break;
        default:
      }
    }
  }

  /**
   * Runs a sub-controller that functionality only includes flexible model manipulation.
   *
   * @param model flexible portfolio to be processed.
   * @param view  Output view for responses/error throwing.
   * @param sc    Scanner object.
   * @throws IOException In case of I/O Errors.
   */
  protected void runFlexibleVersion(InvestmentModelFlex model, InvestmentView view, Scanner sc)
          throws IOException {
    int track = 9;
    while (track != 0) {
      view.printMainMenu1();
      String s = sc.next();
      //verify input
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      }
      switch (track) {
        case 1:
          createPortfolio(model, sc, view, true);
          track = 0;
          break;
        case 2:
          if (!loadPortfolio(model, sc, view)) {
            runFlexibleVersion(model, view, sc);
            return;
          }
          track = 0;
          break;
        default:
          break;
      }
    }
    track = 9;
    while (track != 0) {
      view.printFlexMenu1();
      String s = sc.next();
      //verify input
      if (verifyChoice(s, view)) {
        track = Integer.parseInt(s);
      }
      switch (track) {
        //kicks us out of main menu
        case 1:
          getValue(model, sc, view);
          break;
        case 2:
          savePortfolio(model, sc, view);
          break;
        case 3:
          view.print(model.printStocks() + "\n");
          break;
        case 4:
          addStock(model, sc, view, true);
          break;
        case 5:
          sellStock(model, sc, view, true);
          break;
        case 6:
          dollarCostAverage(model, sc, view);
          break;
        case 7:
          rebalancedPortfolio(model, sc, view);
          break;
        default:
          break;
      }
    }
  }

  /**
   * Navigates a delegates a variety of value functions, with both integer numbers and graphs.
   *
   * @param model FlexPortfolio model currently using.
   * @param sc    Scanner object.
   * @param view  Output object for doubles and graphs.
   * @throws IOException In case of I/O Error.
   */
  protected void getValue(InvestmentModelFlex model, Scanner sc, InvestmentView view)
          throws IOException {
    int track = 9;
    view.print("Which portfolio value would you like to see?\n");
    view.print("1. Cost Basis. 2. Value at Date. 3. Value Graph over Time.\n\n");
    String s = sc.next();
    if (verifyChoice(s, view)) {
      track = Integer.parseInt(s);
    } else {
      getValue(model, sc, view);
    }
    switch (track) {
      //Taking necessary info for cost basis
      case 1:
        int day;
        int month;
        int year;
        double totalValue = 0.0;
        view.print("Which day would you like to view your cost basis?"
                + "Please enter in MM/DD/YYYY form\n");
        String dateString = sc.next();
        try {
          String[] dateBlocked = dateString.split("/");
          day = Integer.parseInt(dateBlocked[1]);
          month = Integer.parseInt(dateBlocked[0]);
          year = Integer.parseInt(dateBlocked[2]);
        } catch (Exception e) {
          view.print("Invalid date format.\n");
          return;
        }
        try {
          totalValue = model.getCostBasis(year, month, day);
          lastCoastBasis = totalValue;
          view.print(totalValue + "\n\n");
          return;
        } catch (Exception e) {
          view.print("Error: Invalid date selected. Try again.\n\n");
          return;
        }
        //Value function -- straightforward call
      case 2:
        valueStock(model, sc, view);
        return;
      //Graph function -- differential calling based on level of granularity wanted.
      case 3:
        //necessary parameters for start and end date
        int firstYear;
        int secondYear;
        int firstMonth;
        int secondMonth;
        int firstDay;
        int secondDay;
        //year level
        view.print("Please select 'Y' to view the graph in the timespan of years, or any other"
                + "key to view the graph in a smaller timespan.\n");
        s = sc.next();
        //if we want year granularity grab starting and ending year,
        // get double array, print it, return.
        if (s.charAt(0) == 'Y') {
          view.print("Please choose your starting year of interest.\n");
          try {
            firstYear = sc.nextInt();
          } catch (Exception e) {
            view.print("This does not represent a valid year. Try again.\n");
            return;
          }
          view.print("Please choose your ending year of interest.\n");
          try {
            secondYear = sc.nextInt();
          } catch (Exception e) {
            view.print("This does not represent a valid year. Try again.\n");
            return;
          }
          if (secondYear - firstYear < 4) {
            view.print("Dates selected too close together. Please try again.\n");
            return;
          }
          view.printGraph(0, 0, 0, 0, firstYear, secondYear,
                  model.printGraph(0, 0, 0, 0, firstYear, secondYear));
          return;
        }
        //if not ask about interest year and then ask about months.
        view.print("Which year would you like to look deeper into?\n");
        try {
          firstYear = sc.nextInt();
        } catch (Exception e) {
          view.print("This does not represent a valid year. Try again.\n");
          return;
        }
        //set years equal for logic in later functions
        secondYear = firstYear;
        //same structure for month call
        view.print("Please select 'Y' to view the graph in the timespan of months, or any other "
                + "key to view the graph in days.\n");
        s = sc.next();
        if (s.charAt(0) == 'Y') {
          view.print("Please choose your starting month of interest as an "
                  + "integer between 1-12.\n");
          try {
            firstMonth = sc.nextInt();
            if (firstMonth > 12 || firstMonth < 0) {
              throw new IllegalArgumentException();
            }
          } catch (Exception e) {
            view.print("This does not represent a valid month. Try again.\n");
            return;
          }
          view.print("Please choose your ending month of interest.\n");
          try {
            secondMonth = sc.nextInt();
            if (secondMonth > 12 || secondMonth < 0) {
              throw new IllegalArgumentException();
            }
          } catch (Exception e) {
            view.print("This does not represent a valid month. Try again.\n");
            return;
          }
          if (secondMonth - firstMonth < 4) {
            view.print("Dates selected too close together. Please try again.\n");
            return;
          }
          view.printGraph(0, 0, firstMonth, secondMonth,
                  firstYear, secondYear, model.printGraph(0, 0, firstMonth,
                          secondMonth, firstYear, secondYear));
          return;
        }
        //days ask starting month
        //if not ask about interest year and then ask about months.
        view.print("Which month would you like to look deeper into?\n");
        try {
          firstMonth = sc.nextInt();
          //if (firstMonth > 12 || firstMonth < 1) ;
        } catch (Exception e) {
          view.print("This does not represent a valid month. Try again.\n");
          return;
        }
        secondMonth = firstMonth;
        view.print("Please choose your starting day of interest as an "
                + "integer between 1-31.\n");
        try {
          firstDay = sc.nextInt();
          if (firstDay > 31 || firstDay < 0) {
            throw new IllegalArgumentException();
          }
        } catch (Exception e) {
          view.print("This does not represent a valid day. Try again.\n");
          return;
        }
        view.print("Please choose your starting day of interest.\n");
        try {
          secondDay = sc.nextInt();
          if (secondDay > 31 || secondDay < 0) {
            throw new IllegalArgumentException();
          }
        } catch (Exception e) {
          view.print("This does not represent a valid day. Try again.\n");
          return;
        }
        if (secondDay - firstDay < 4) {
          view.print("Dates selected too close together. Please try again.\n");
          return;
        }
        view.printGraph(firstDay, secondDay, firstMonth, secondMonth,
                firstYear, secondYear, model.printGraph(firstDay, secondDay, firstMonth,
                        secondMonth, firstYear, secondYear));
        break;
      default:
        break;
    }
  }

  /**
   * Validates choice for menu selection.
   *
   * @param s    String to be validated.
   * @param view Output object to present findings of validation.
   * @return boolean representing validity of selection.
   * @throws IOException In case of I/O Error.
   */
  private boolean verifyChoice(String s, InvestmentView view) throws IOException {
    if (s.length() == 1) {
      if (Character.isDigit(s.charAt(0))) {
        return true;
      } else {
        view.print("Error: provided selection is out of "
                + "provided range. Please try again.\n\n");
        return false;
      }
    } else {
      view.print("Error: provided selection is too long. Please try again.\n\n");
      return false;
    }
  }

  /**
   * Creates portfolio.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  private void createPortfolio(InvestmentModel model, Scanner sc, InvestmentView view, boolean
          flexible)
          throws IOException {
    view.print("What would you like to name this portfolio?\n");
    String name = sc.next();
    if (flexible) {
      view.print("What would you like the brokerage fee to be? Please input answer "
              + "as an integer value \n");
      Double d;
      String s = sc.next();
      try {
        d = Double.parseDouble(s);
        model.createPortfolio(name, d);
      } catch (Exception e) {
        view.print("Impossible brokerage fee. Try again.");
        createPortfolio(model, sc, view, true);
      }
    } else {
      model.createPortfolio(name, 0.0);
    }
    view.print("New portfolio, " + name + ", created.\n\n");
  }

  /**
   * Saves portfolio.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  private void savePortfolio(InvestmentModel model, Scanner sc, InvestmentView view)
          throws IOException {
    model.savePortfolio();
    view.print("Portfolio saved.\n\n");
  }

  /**
   * Loads portfolio.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  protected boolean loadPortfolio(InvestmentModel model, Scanner sc, InvestmentView view)
          throws IOException {
    view.print("Which portfolio would you like to load?\n");
    String targetPort = sc.next();
    try {
      model.loadPortfolio(targetPort);
    } catch (Exception e) {
      view.print("Error, Portfolio non-empty or non-viable file added.\n\n");
      return false;
    }
    view.print("Portfolio loaded.\n\n");
    return true;
  }

  /**
   * Adds stock.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  protected void addStock(InvestmentModel model, Scanner sc, InvestmentView view, boolean
          flexible)
          throws IOException {
    int shares;
    String date;
    view.print("Which stock would you like to purchase?\n");
    String ticker = sc.next();
    view.print("How many shares of this stock would you like? \n");
    String s = sc.next();
    try {
      shares = Integer.parseInt(s);
    } catch (Exception e) {
      view.print("Cannot accept this number of shares. Try again. \n\n");
      return;
    }
    if (flexible) {
      view.print("What Date should the purchase be on? Please enter in MM/DD/YYYY form.\n");
      date = sc.next();
    } else {
      date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    try {
      model.addStock(shares, ticker, indices.getIndex(ticker).getIndex(), date);
      view.print(shares + " shares of " + ticker + " successfully purchased.\n\n");
      view.print(model.printStocks() + "\n");
    } catch (Exception e) {
      if (shares <= 0) {
        view.print("Error, shares chosen zero or negative.\n");
      } else {
        view.print("The indicated ticker is invalid. Please try again.\n");
      }
    }
  }

  /**
   * Sells stock.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  protected void sellStock(InvestmentModel model, Scanner sc, InvestmentView view, boolean flexible)
          throws IOException {
    view.print("Which stock would you like to sell?\n");
    String delTicker = sc.next();
    int shares = 0;
    String date = "";
    if (flexible) {
      view.print("How many of the stock would you like to sell\n");
      shares = sc.nextInt();
      view.print("What Date should the purchase be on? Please enter in MM/DD/YYYY form.\n");
      date = sc.next();
    }
    model.deleteStock(delTicker, shares, date);
    view.print("Stock successfully sold.\n\n");
  }

  /**
   * Generates total value of portfolio.
   *
   * @param model Given model implementation of Portfolio.
   * @param sc    Given scanner.
   * @param view  Given view implementation used in current run.
   * @throws IOException in case of I/O errors.
   */
  private void valueStock(InvestmentModel model, Scanner sc, InvestmentView view)
          throws IOException {
    int day;
    int month;
    int year;
    double totalValue = 0.0;
    view.print("Which day would you like to view your portfolio value?"
            + "Please enter in MM/DD/YYYY form\n");
    String dateString = sc.next();
    try {
      String[] dateBlocked = dateString.split("/");
      day = Integer.parseInt(dateBlocked[1]);
      month = Integer.parseInt(dateBlocked[0]);
      year = Integer.parseInt(dateBlocked[2]);
    } catch (Exception e) {
      view.print("Invalid date format.\n");
      return;
    }
    try {
      totalValue = model.getValuePortfolio(year, month, day);
      lastValue = totalValue;
    } catch (Exception e) {
      view.print("Error, date selected does not contain data for all stocks in portfolio.\n\n");
      return;
    }
    view.print("This is the current value of portfolio.\n");
    view.print(totalValue + "\n\n");
  }

  /**
   * Generates a dollar cost-average plan attached to the current working portfolio.
   *
   * @param model Current portfolio type using, only flexible permitted here.
   * @param sc    Scanner object currently using.
   * @param view  View object currently using.
   * @throws IOException In case of I/O Error.
   */
  protected void dollarCostAverage(InvestmentModelFlex model, Scanner sc, InvestmentView view)
          throws IOException {
    int year = 0;
    int month = 0;
    int day = 0;
    int endDay = 0;
    int endMonth = 0;
    int endYear = 0;
    view.print("How many stocks would you like to average? "
            + "Please enter as an integer.\n");
    int numStocks = 0;
    try {
      String s = sc.next();
      numStocks = Integer.parseInt(s);
      if (numStocks < 2) {
        throw new IllegalArgumentException("Insufficient number of stocks to average.\n");
      }
    } catch (Exception e) {
      view.print("Try again.\n");
      dollarCostAverage(model, sc, view);
      return;
    }
    //build stockList array based on number of stocks
    String[] stockList = new String[numStocks];
    //fill in each array cell in linear manner
    int stockCounter = 0;
    while (stockCounter < numStocks) {
      view.print("Please enter stock ticker " + (stockCounter + 1) + ".\n");
      String nextStock = sc.next();
      try {
        indices.getIndex(nextStock);
        stockList[stockCounter] = nextStock;
        ++stockCounter;
      } catch (Exception e) {
        view.print("Bad ticker. Try again.\n");
      }
    }
    double[] percentList = getWeightList(sc, view, numStocks, stockList);
    //get total money amount that will be split
    double d = getInvestmentMoney(sc, view);
    //ask for initial date
    boolean worked = false;
    while (!worked) {
      view.print("Which day would you like these purchases to begin?"
              + "Please enter in MM/DD/YYYY form\n");
      String dateString = sc.next();
      try {
        String[] dateBlocked = dateString.split("/");
        day = Integer.parseInt(dateBlocked[1]);
        month = Integer.parseInt(dateBlocked[0]);
        year = Integer.parseInt(dateBlocked[2]);
      } catch (Exception e) {
        view.print("Invalid date format.\n");
        break;
      }
      try {
        for (String s : stockList) {
          indices.getValue(s, year, month, day);
        }
        worked = true;
      } catch (Exception e) {
        view.print("Error, start date does not contain data for all stocks in portfolio. Try " +
                "again. \n\n");
      }
    }
    //ask for interval of days
    worked = false;
    int intervalDays = 0;
    while (!worked) {
      view.print("How many days between purchases would you like? Please enter "
              + "as an integer value.\n");
      try {
        String s = sc.next();
        intervalDays = Integer.parseInt(s);
        if (intervalDays <= 0) {
          throw new IllegalArgumentException("Insufficient number of days to average.\n");
        }
        worked = true;
      } catch (Exception e) {
        view.print("Try again.\n");
      }
    }
    worked = false;
    while (!worked) {
      view.print("Which day would you like these purchases to end?"
              + "Please enter in MM/DD/YYYY form. Type in 'never' for purchases to be ongoing.\n");
      String dateString = sc.next();
      if (dateString.equals("never")) {
        dateString = "12/31/3005";
        worked = true;
      }
      try {
        String[] dateBlocked = dateString.split("/");
        endDay = Integer.parseInt(dateBlocked[1]);
        endMonth = Integer.parseInt(dateBlocked[0]);
        endYear = Integer.parseInt(dateBlocked[2]);
        if (endDay < 1 || endDay > 31 || endMonth < 1 || endMonth > 12 || endYear < 1970) {
          throw new IllegalArgumentException("Invalid date format");
        }
      } catch (Exception e) {
        view.print("Invalid date format.\n");
        continue;
      }
      if (endYear < year || endYear == year && endMonth < month || endYear == year && endMonth
              == month && endDay <= day) {
        view.print("Start date occurs after end date. Please try again.");
      } else {
        worked = true;
      }
    }
    String startDate = "" + month + "/" + day + "/" + year;
    String endDate = "" + endMonth + "/" + endDay + "/" + endYear;
    view.print("Purchasing " + d + " worth of stocks below from " +
            startDate + " until " + endDate + " every " + intervalDays + " days in the " +
            "following ratios:\n");
    for (int i = 0; i < numStocks; ++i) {
      view.print(stockList[i] + " -- " + percentList[i] + "%\n");
    }
    model.dollarCostProcessing(startDate, endDate, intervalDays, d, stockList, percentList);
  }

  private void rebalancedPortfolio(InvestmentModelFlex model, Scanner sc, InvestmentView view)
          throws IOException {
    int year = 0;
    int month = 0;
    int day = 0;

    view.print("Let's rebalance your portfolio");
    //get total money amount that will be split
    double d = getInvestmentMoney(sc, view);

    //ask for the date to rebalance a portfolio on
    boolean worked = false;
    while (!worked) {
      view.print("Which day would you like this portfolio to be rebalanced?"
              + "Please enter in MM/DD/YYYY form\n");
      String dateString = sc.next();
      try {
        String[] dateBlocked = dateString.split("/");
        day = Integer.parseInt(dateBlocked[1]);
        month = Integer.parseInt(dateBlocked[0]);
        year = Integer.parseInt(dateBlocked[2]);
        worked = true;
      } catch (Exception e) {
        view.print("Invalid date format.\n");
        break;
      }
    }

    // get list of stocks that are in this portfolio
    String stocks = model.printStocks();
    String[] splitStocks = stocks.split("\n");
    List<String> stocksList = new ArrayList<>();
    for (String index : splitStocks) {
      if (index.contains(",")) {
        stocksList.add(index.split(",")[1]);
      }
    }

    // get weightage percent list from the user and the size should match with
    // number of stocks they have in their portfolio
    double[] percentList = getWeightList(sc, view, stocksList.size(), stocksList.toArray(String[]::new));

    String date = "" + month + "/" + day + "/" + year;
    view.print("Rebalancing " + d + " worth of stocks below from "
            + date + "with following ratios:\n");
    for (int i = 0; i < stocksList.size(); ++i) {
      view.print(stocksList.get(i) + " -- " + percentList[i] + "%\n");
    }

    model.rebalancePortfolio(date, d, percentList);
  }

  private double getInvestmentMoney(Scanner sc, InvestmentView view) throws IOException {
    //ask for total money amount that will be split
    double d = 0.0;
    while (d == 0.0) {
      view.print("How much money will you invest at each interval?\n");
      String moneyString = sc.next();
      try {
        double placeholder = Double.parseDouble(moneyString);
        if (placeholder <= 0) {
          throw new IllegalArgumentException("Bad money\n");
        }
        d = placeholder;
      } catch (Exception e) {
        view.print("Bad Money. Try again.\n");
      }
    }
    return d;
  }

  private double[] getWeightList(Scanner sc, InvestmentView view, int numStocks, String[] stockList)
          throws IOException {
    int stockCounter;
    //now do the same sort of thing with percentages
    double[] percentList = new double[numStocks];
    //fill in each array in linear manner
    stockCounter = 0;
    double totalPercent = 0.0;
    while (stockCounter < numStocks) {
      view.print("Please enter % weight for " + stockList[stockCounter] + ". You currently have " +
              String.format("%,.2f", 100 - totalPercent) + " percent remaining \n");
      String nextPercTry = sc.next();
      try {
        double nextPerc = Double.parseDouble(nextPercTry);
        if (nextPerc <= 0) {
          throw new IllegalArgumentException("Too low.");
        }
        if (totalPercent + nextPerc > 100) {
          throw new IllegalArgumentException("Too high.");
        }
        totalPercent += nextPerc;
        percentList[stockCounter] = nextPerc;
        ++stockCounter;
      } catch (Exception e) {
        view.print("Bad number. Try again.\n");
      }
      if (stockCounter == numStocks && totalPercent < 100) {
        view.print("Total percent does not total 100. Resetting percentages. Try again.\n");
        stockCounter = 0;
        totalPercent = 0;
      }
    }
    return percentList;
  }
}

