package stockhw6.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stockhw6.model.IStockModelMaker;
import stockhw6.model.User;
import stockhw6.view.IStockGuiView;

/**
 * The StockControllerImpl class represents as a mediator between model and GUI view.
 */
public class StockControllerGuiImpl implements IStockGuiFeatures {

  private final IStockModelMaker model;
  private IStockGuiView view;
  private User user;
  private String portfolioUuid;
  private List<String> tickerList;
  private List<Integer> weights;
  private String investmentDate;
  private String investmentDate2;
  private int investmentCapital;

  public StockControllerGuiImpl(IStockModelMaker model) {
    this.model = model;
  }

  public void setView(IStockGuiView view) {
    this.view = view;
    this.view.getLoginScreenView();
    this.view.addGuiFeatures(this);
  }

  @Override
  public void getLoginScreenView(ActionEvent evt) {
    String actionCommand = evt.getActionCommand();
    // existing user flow
    if (actionCommand.equals(UserInput.Y.name())) {
      processExistingUser();
    } else {
      processNewUser();
    }
  }

  @Override
  public void getUserName(String userName) {
    if (model.isUserNameExists(userName)) {
      this.user = model.getUserFromUsername(userName);
      if (this.user != null) {
        this.view.getUserOptionsView(this.user.getUserName());
      }
    } else {
      this.view.showErrorMessage("Invalid username, please enter again!");
    }
  }

  @Override
  public void saveNewUser(String userName, ActionEvent evt) {
    if (userName != null) {
      User user1 = model.saveUser(User
              .builder()
              .userName(userName)
              .build());
      if (user1 != null) {
        this.user = user1;
        this.view.showSuccessMessage("User created successfully", evt);
        this.view.getUserOptionsView(this.user.getUserName());
      } else {
        this.view.showErrorMessage("Username already exists, Please "
                + "try another valid username.");
      }
    } else if (userName.length() > 8) {
      this.view.showErrorMessage("Username can't be longer than 8 characters, Please "
              + "enter a valid username.");
    }
  }

  private void processNewUser() {
    this.view.getNewUserView();
  }

  private void processExistingUser() {
    this.view.getExistingUserView();
  }

  @Override
  public void getUserOptionsView(ActionEvent evt) {
    String input = evt.getActionCommand();

    // initialize investment variables
    this.tickerList = new ArrayList<>();
    this.weights = new ArrayList<>();
    this.investmentDate = null;
    this.investmentCapital = 0;

    if (input.equals(UserInputOptions.ONE.getInput())) {
      portfolioUuid = model.generateUUID();
      this.view.flexibleUserOptionOne();
    } else if (input.equals(UserInputOptions.TWO.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Sell Stocks", input);
    } else if (input.equals(UserInputOptions.THREE.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Cost Basis", input);
    } else if (input.equals(UserInputOptions.FOUR.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Portfolio Value", input);
    } else if (input.equals(UserInputOptions.FIVE.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Save Portfolio", input);
    } else if (input.equals(UserInputOptions.SIX.getInput())) {
      this.view.getExternalPortfolioFilePath();
    } else if (input.equals(UserInputOptions.SEVEN.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Buy Stocks", input);
    } else if (input.equals(UserInputOptions.EIGHT.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Existing Portfolio Investment", input);
    } else if (input.equals(UserInputOptions.NINE.getInput())) {
      this.view.flexibleUserOptionsNine();
    } else if (input.equals(UserInputOptions.TEN.getInput())) {
      List<String> portfolioIds = model.getPortfoliosForUser(this.user);
      this.view.comboBoxBuilder(portfolioIds, "Portfolio Performance", input);
    }
  }

  @Override
  public void processFlexibleOptionOne(String input, ActionEvent evt) {
    if (!evt.getActionCommand().equals("cancel buy")
            && !evt.getActionCommand().equals("save buy")) {
      if (model.validateFlexibleTickerShare(input) && model.isValidTicker(input.split(",")[0])) {
        if (model.saveFlexibleStock(this.user, this.portfolioUuid,
                input.split(",")[0], input.split(",")[1],
                input.split(",")[2], getCommissionFees(input))) {
          // successful message
          this.view.showSuccessMessage("Ticker and number of shares added "
                  + "to portfolio! "
                  + "Click save to exit "
                  + "Portfolio Creation or enter another valid stock to continue.", evt);
        }
      } else {
        // error message
        this.view.showErrorMessage("Invalid input!, Please enter a "
                + "valid ticker/share/date combination : ");
      }
    } else if (!evt.getActionCommand().equals("buy")
            && !evt.getActionCommand().equals("cancel buy")) {
      // close the frame message
      this.view.showSuccessMessage("Your portfolio has been created! "
              + "You can find it at : " + portfolioUuid, evt);
    }
  }

  @Override
  public void processFlexibleOptionTwo(String input, ActionEvent evt) {
    if (!evt.getActionCommand().equals("cancel sell")
            && !evt.getActionCommand().equals("save sell")) {
      if (model.validateFlexibleTickerShare(input) && model.isValidTicker(input.split(",")[0])) {
        if (model.sellFlexibleStock(this.user, this.portfolioUuid,
                input.split(",")[0], input.split(",")[1],
                input.split(",")[2], getCommissionFees(input))) {
          // successful message
          this.view.showSuccessMessage("Ticker and number of shares sold "
                  + "from your portfolio! "
                  + "Click save to exit "
                  + "Portfolio Creation or enter another valid stock to continue selling.", evt);
        }
      } else {
        // error message
        this.view.showErrorMessage("Invalid input!, Please enter a "
                + "valid ticker/share/date combination : ");
      }
    } else if (!evt.getActionCommand().equals("sell")
            && !evt.getActionCommand().equals("cancel sell")) {
      // close the frame message
      this.view.showSuccessMessage("Your portfolio has been updated! "
              + "You can find it at : " + portfolioUuid, evt);
    }
  }

  @Override
  public void processFlexibleOptionThree(String input, ActionEvent evt) {
    if (!evt.getActionCommand().equals("cancel cost basis")) {
      if (input != null && !input.equals("")) {
        double costBasis = model.calculateCostBasis(user, portfolioUuid, input);
        // successful message
        this.view.showSuccessMessage("The total amount of money invested in "
                + "a portfolio : $" + costBasis, evt);
      } else {
        // error message
        this.view.showErrorMessage("Invalid input!, Please enter a "
                + "valid date. ");
      }
    }
  }

  @Override
  public void processFlexibleOptionFour(String input, ActionEvent evt) {
    if (!evt.getActionCommand().equals("cancel portfolio value")) {
      if (input != null && !input.equals("")) {
        // calculate total worth of a portfolio
        Map<Integer, List<String>> totalValueOfAPortfolio =
                model.calculateTotalValueOfAFlexiblePortfolio(input, this.user, this.portfolioUuid);

        double totalPortfolioValueSum = 0.0;
        // check if list length matches with expected length returned by model to
        // indicate if the entire portfolio has been processed
        for (Map.Entry<Integer, List<String>> entry : totalValueOfAPortfolio.entrySet()) {
          if (entry.getKey() != entry.getValue().size()) {
            try {
              Thread.sleep(600);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            // call the model again to fetch remaining ones
            totalValueOfAPortfolio = model.calculateTotalValueOfAFlexiblePortfolio(input, this.user,
                    this.portfolioUuid);
          }

          for (String row : totalValueOfAPortfolio.values().stream().findFirst().get()) {
            totalPortfolioValueSum += Double.parseDouble(row.split(",")[2]);
          }

          List<String> columns = new ArrayList<>();
          columns.add("Ticker");
          columns.add("Number of shares");
          columns.add("Total Share Value");
          this.view.portfolioValueTableView(totalValueOfAPortfolio
                  .values().stream().findFirst().get(), columns, totalPortfolioValueSum);
        }
      } else {
        // error message
        this.view.showErrorMessage("Invalid input!, Please enter a "
                + "valid date. ");
      }
    }
  }

  @Override
  public void processFlexibleOptionEight(String date, String capital, String symbol,
                                         String weightage, String commissionFees, ActionEvent evt) {

    if (!date.equals("") && !capital.equals("") && !symbol.equals("") && !weightage.equals("")) {
      if (!evt.getActionCommand().equals("cancel investment")
              && !evt.getActionCommand().equals("save investment")) {
        // set date and capital once
        if (this.investmentDate == null && this.investmentCapital == 0) {
          this.investmentDate = date;
          this.investmentCapital = Integer.parseInt(capital);
        }

        if (model.isValidTicker(symbol)) {
          this.tickerList.add(symbol);
          this.weights.add(Integer.valueOf(weightage));
          this.view.showSuccessMessage("Investment added", evt);
        } else {
          this.view.showErrorMessage("Invalid symbol, enter again!");
        }
      }
    } else if (!evt.getActionCommand().equals("add investment")
            && !evt.getActionCommand().equals("cancel investment")) {
      if (this.user != null && this.portfolioUuid != null && this.investmentDate != null
              && this.investmentCapital != 0 && this.tickerList.size() != 0
              && this.weights.size() != 0) {
        int cFees = !commissionFees.equals("") ? Integer.parseInt(commissionFees) : 0;
        if (this.model.updatePortfolioBasedOnInvestment(this.user, this.portfolioUuid,
                this.tickerList, this.investmentDate, this.investmentCapital,
                this.weights, cFees)) {
          this.view.showSuccessMessage("Your investment has been successfully "
                  + "added to your portfolio", evt);
        } else {
          this.view.showErrorMessage("Couldn't save the investment to your portfolio");
        }
      }
    } else {
      this.view.showErrorMessage("Nothing to save");
    }
  }

  @Override
  public void processFlexibleOptionNine(String capital, String symbol, String weightage,
                                        String date, String date2, String timeIntervalUnit,
                                        String timeFrequency,
                                        String commissionFees, ActionEvent evt) {
    if (!capital.equals("") && !symbol.equals("") && !weightage.equals("") && !date.equals("")
            && !date2.equals("") && !timeIntervalUnit.equals("")) {
      if (!evt.getActionCommand().equals("cancel investment")
              && !evt.getActionCommand().equals("save investment")) {
        // set date and capital once
        if (this.investmentDate == null && this.investmentCapital == 0) {
          this.investmentDate = date;
          this.investmentDate2 = date2;
          this.investmentCapital = Integer.parseInt(capital);
        }

        if (model.isValidTicker(symbol)) {
          this.tickerList.add(symbol);
          this.weights.add(Integer.valueOf(weightage));
          this.view.showSuccessMessage("Investment added, keeping adding or click save", evt);
        } else {
          this.view.showErrorMessage("Invalid symbol, enter again!");
        }
      }
    } else if (!evt.getActionCommand().equals("new investment")
            && !evt.getActionCommand().equals("cancel investment")) {
      if (this.user != null && this.investmentDate != null
              && this.investmentCapital != 0 && this.tickerList.size() != 0
              && this.weights.size() != 0) {
        int cFees = !commissionFees.equals("") ? Integer.parseInt(commissionFees) : 0;
        if (this.model.createPortfolioBasedOnPlan(this.user, model.generateUUID(),
                this.tickerList, this.investmentDate, this.investmentDate2,
                getDaysSkip(timeFrequency, timeIntervalUnit),
                this.investmentCapital, this.weights, cFees)) {
          this.view.showSuccessMessage("Your strategy has been successfully "
                  + "created", evt);
        } else {
          this.view.showErrorMessage("Couldn't save the strategy");
        }
      }
    } else {
      this.view.showErrorMessage("Nothing to save");
    }
  }


  @Override
  public void processFlexibleOptionTen(String date1, String date2, ActionEvent evt) {
    if (evt.getActionCommand().equals("generate")) {
      if (date1 != null && date2 != null) {
        Map<String, Integer> barChartContents = this.model
                .getBarChartContents(date1, date2, this.portfolioUuid, this.user);
        if (barChartContents.size() != 0) {
          this.view.showPerformanceGraph(barChartContents);
        } else {
          this.view.showSuccessMessage("No data to show", evt);
        }
      }
    }
  }

  @Override
  public void getPortfolioIdInputForOptionTwo(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionTwo();
    }
  }

  @Override
  public void getPortfolioIdInputForOptionThree(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionThree();
    }
  }

  @Override
  public void getPortfolioIdInputForOptionFour(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionFour();
    }
  }

  @Override
  public void getPortfolioIdInputForOptionFive(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      if (model.validatePortfolioUUID(input, this.user)) {
        String portfolioFilePath = this.user.getUserName() + "_" + input + ".csv";
        String message = "Your portfolio has been serialized! You can find it at: "
                + portfolioFilePath;
        this.view.flexibleUserOptionFiveAndSix(message);
      }
    }
  }

  @Override
  public void getFilePathForOptionSix(String input) {
    if (input != null && model
            .validateFlexibleUserPortfolioExternalPathAndContentsStructure(input)) {
      String pUUID = model.saveExternalFlexibleUserPortfolio(input, this.user);
      if (pUUID != null) {
        String message = "The external portfolio file has "
                + "been saved successfully. "
                + "You can find it at : " + this.user.getUserName() + "_" + pUUID + ".csv";
        this.view.flexibleUserOptionFiveAndSix(message);
      }
    } else {
      // error message
      this.view.showErrorMessage("Invalid File Path entered or Structure of " +
              "File is malformed! Please enter a valid file path ");
    }
  }

  @Override
  public void getPortfolioIdInputForOptionSeven(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionOne();
    }
  }

  @Override
  public void getPortfolioIdInputForOptionEight(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionEight();
    }
  }

  @Override
  public void getPortfolioIdInputForOptionTen(String input) {
    this.portfolioUuid = input;
    if (portfolioUuid != null) {
      this.view.flexibleUserOptionTen();
    }
  }

  /**
   * It parses the commission fees to int.
   *
   * @param input commission fees.
   * @return int commission fees.
   */
  private int getCommissionFees(String input) {
    return !input.split(",")[3].equals("")
            ? Integer.parseInt(input.split(",")[3])
            : 0;
  }

  /**
   * It gets day skips based on user input.
   *
   * @param timeFrequency    how often the investment should occur.
   * @param timeIntervalUnit unit selected by the user.
   * @return converted timeFrequency to days.
   */
  private int getDaysSkip(String timeFrequency, String timeIntervalUnit) {
    if (TimeIntervalUnit.DAYS.getInput().equals(timeIntervalUnit)) {
      return Integer.parseInt(timeFrequency);
    } else if (TimeIntervalUnit.MONTH.getInput().equals(timeIntervalUnit)) {
      return Integer.parseInt(timeFrequency) * 30;
    } else if (TimeIntervalUnit.YEAR.getInput().equals(timeIntervalUnit)) {
      return Integer.parseInt(timeFrequency) * 365;
    }
    return 0;
  }
}
