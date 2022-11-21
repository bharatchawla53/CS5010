package stockhw6.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import stockhw6.model.IStockModelMaker;
import stockhw6.model.User;
import stockhw6.view.IStockViewMaker;

/**
 * The StockControllerImpl class represents as a mediator between model and view.
 */
public class StockControllerImpl implements StockController {
  private final IStockModelMaker model;
  private final IStockViewMaker view;
  private User user;
  private final Scanner scanner;
  private Boolean isUserOperationSuccessful;

  /**
   * Constructs a StockControllerImpl constructor and initializes model and view.
   *
   * @param model StockModel that controller talks to.
   * @param view  StockView that controller talk to.
   */
  public StockControllerImpl(IStockModelMaker model, IStockViewMaker view, Readable in) {
    this.model = model;
    this.view = view;
    this.scanner = new Scanner(in);
    this.isUserOperationSuccessful = true;
  }

  @Override
  public void start() {
    String input = getLoginScreenInput();

    // existing user flow
    if (input.equals(UserInput.Y.name())) {
      //input = null;
      input = getValidUserNameInput(input);

      // get the user object from the model once validated
      this.user = model.getUserFromUsername(input);

      getPortfolioTypeInput(input);

    } else {
      if (processNewUser()) {
        getPortfolioTypeInput(input);
      }
    }
  }

  private void getPortfolioTypeInput(String input) {
    boolean invalidInput = true;

    view.getPortfolioTypeView();
    // validate the user portfolio type input
    while (invalidInput) {
      try {
        input = getUserInputView().toUpperCase(Locale.ROOT);

        if (input.equals(UserInputOptions.ONE.getInput())) {
          // inflexible view
          while (this.isUserOperationSuccessful) {
            keepPromptingInflexibleUserOptions(input);
          }
          invalidInput = false;
        } else if (input.equals(UserInputOptions.TWO.getInput())) {
          // flexible view
          while (this.isUserOperationSuccessful) {
            keepPromptingFlexibleUserOptions(input);
          }
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid user input", "Enter Y/N : "));
      }
    }

  }

  /**
   * It gets the login screen input from the user whether they are existing user or not, and it
   * keeps prompting the user until valid input is received.
   *
   * @return input received from the user.
   */
  private String getLoginScreenInput() {
    boolean invalidInput = true;
    String input = null;

    view.getLoginScreenView();
    // validate the user login input
    while (invalidInput) {
      try {
        input = getUserInputView().toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid user input", "Enter Y/N : "));
      }
    }
    return input;
  }

  /**
   * Get's the valid username input for the existing users. It keeps prompting the user until valid
   * input is received.
   *
   * @param input input received from the user.
   * @return input received from the user.
   */
  private String getValidUserNameInput(String input) {
    boolean invalidInput = true;

    view.getUsernameInputView();
    // validate the username input and check if it already exists in our records
    while (invalidInput) {
      input = getUserInputView();
      if (model.isUserNameExists(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Collections.singletonList("This username does not exist! "
                + "Enter an existing username:"));
      }
    }
    return input;
  }

  /**
   * It keeps prompting the user options once the selected operation was successful.
   *
   * @param input input received from the user.
   */
  private void keepPromptingInflexibleUserOptions(String input) {
    input = getInflexibleUserOptionsInput(input);

    if (input.equals(UserInputOptions.ONE.getInput())) {
      processInflexibleUserOptionOne(input);
    } else if (input.equals(UserInputOptions.TWO.getInput())) {
      processInflexibleUserOptionTwo(input);
    } else if (input.equals(UserInputOptions.THREE.getInput())) {
      processInflexibleUserOptionThree(input);
    } else if (input.equals(UserInputOptions.FOUR.getInput())) {
      processUserOptionFour(input);
    } else if (input.equals(UserInputOptions.FIVE.getInput())) {
      processUserOptionFive(input);
    } else if (input.equals(UserInputOptions.SIX.getInput())) {
      terminateApplication(input);
    }
  }

  private void keepPromptingFlexibleUserOptions(String input) {
    input = getFlexibleUserOptionsInput(input);

    if (input.equals(UserInputOptions.ONE.getInput())) {
      processFlexibleUserOptionOne(input);
    } else if (input.equals(UserInputOptions.TWO.getInput())) {
      processFlexibleUserOptionTwo(input);
    } else if (input.equals(UserInputOptions.THREE.getInput())) {
      processFlexibleUserOptionThree(input);
    } else if (input.equals(UserInputOptions.FOUR.getInput())) {
      processFlexibleUserOptionFour(input);
    } else if (input.equals(UserInputOptions.FIVE.getInput())) {
      processUserOptionFour(input);
    } else if (input.equals(UserInputOptions.SIX.getInput())) {
      processFlexibleUserOptionSix(input);
    } else if (input.equals(UserInputOptions.SEVEN.getInput())) {
      processFlexibleUserOptionSeven(input);
    } else if (input.equals(UserInputOptions.EIGHT.getInput())) {
      processFlexibleUserOptionEight(input);
    } else if (input.equals(UserInputOptions.NINE.getInput())) {
      terminateApplication(input);
    }
  }


  /**
   * It terminates the application if valid input is provided.
   *
   * @param input input received from the user.
   */
  private void terminateApplication(String input) {
    boolean invalidInput = true;
    view.terminateView();

    // validate the user input
    while (invalidInput) {
      try {
        input = getUserInputView().toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid input", "Enter Y/N : "));
      }
    }

    if (input.equals(UserInput.Y.name())) {
      this.isUserOperationSuccessful = false;
      view.getBuilderView(Collections.singletonList("You have exited the "
              + "application successfully."));
    }
  }

  /**
   * It validates the user options input and keeps prompting until successful input is received.
   *
   * @param input input received from the user.
   * @return input received from the user.
   */
  private String getInflexibleUserOptionsInput(String input) {
    boolean invalidInput = true;

    view.getInflexibleUserOptionsView();
    // validate the user options input
    while (invalidInput) {
      input = getUserInputView().toUpperCase(Locale.ROOT);

      String finalInput = input;
      Optional<UserInputOptions> userInputOption = Arrays.stream(UserInputOptions.values())
              .filter(u -> finalInput.equals(u.getInput()))
              .findFirst();

      if (userInputOption.isPresent()) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid option!", "Please enter a valid option:"));
      }

    }
    return input;
  }

  private String getFlexibleUserOptionsInput(String input) {
    boolean invalidInput = true;

    view.getFlexibleUserOptionsView();
    // validate the user options input
    while (invalidInput) {
      input = getUserInputView().toUpperCase(Locale.ROOT);

      String finalInput = input;
      Optional<UserInputOptions> userInputOption = Arrays.stream(UserInputOptions.values())
              .filter(u -> finalInput.equals(u.getInput()))
              .findFirst();

      if (userInputOption.isPresent()) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid option!", "Please enter a valid option:"));
      }

    }
    return input;
  }

  /**
   * It processes the UserOptions.ONE and allows them to create a portfolio with one or more stocks,
   * and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processInflexibleUserOptionOne(String input) {
    boolean invalidInput = true;

    view.getInflexiblePortfolioCreatorView();
    String portfolioUuid = model.generateUUID();
    while (!input.equals("DONE")) {
      // validate the user provided ticker/share combination
      while (invalidInput) {
        input = getUserInputView().toUpperCase(Locale.ROOT);
        if (model.validateInflexibleTickerShare(input)
                && model.isValidTicker(input.split(",")[0])) {
          invalidInput = false;
        } else {
          if (input.equals("DONE")) {
            invalidInput = false;
          } else {
            view.getBuilderView(Arrays.asList("Invalid input!", "Please enter a "
                    + "valid ticker/share (no fractional shares) combination : "));
          }
        }
      }
      // received correct combination from user to add share to their portfolio

      if (!input.equals("DONE") && model.saveInflexibleStock(this.user, portfolioUuid,
              input.split(",")[0], input.split(",")[1])) {
        view.getBuilderView(Collections.singletonList("Ticker and number of shares added "
                + "to portfolio! "
                + "Enter DONE to exit "
                + "Portfolio Creation or enter another valid stock to continue"));
        invalidInput = true;
      }
    }
    view.getBuilderView(Collections.singletonList("---Your portfolio has been created! "
            + "You can find it at : " + portfolioUuid + "---"));
  }

  /**
   * It processes the flexible UserOptions.ONE and allows them to create a portfolio with one or
   * more stocks, on a specific date and keeps prompting until successful sequence of input is
   * received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionOne(String input) {
    boolean invalidInput = true;

    view.getFlexiblePortfolioCreatorView();
    String portfolioUuid = model.generateUUID();
    while (!input.equals("DONE")) {
      // validate the user provided ticker/share/date combination
      while (invalidInput) {
        input = getUserInputView().toUpperCase(Locale.ROOT);
        if (model.validateFlexibleTickerShare(input) && model.isValidTicker(input.split(",")[0])
                && isValidDate(input.split(",")[2])) {
          invalidInput = false;
        } else {
          if (input.equals("DONE")) {
            invalidInput = false;
          } else {
            view.getBuilderView(Arrays.asList("Invalid input!", "Please enter a "
                    + "valid ticker/share/date (no fractional shares) combination : "));
          }
        }
      }
      // received correct combination from user to add share to their portfolio

      if (!input.equals("DONE") && model.saveFlexibleStock(this.user, portfolioUuid,
              input.split(",")[0], input.split(",")[1], input.split(",")[2])) {
        view.getBuilderView(Collections.singletonList("Ticker and number of shares added "
                + "to portfolio! "
                + "Enter DONE to exit "
                + "Portfolio Creation or enter another valid stock to continue"));
        invalidInput = true;
      }
    }
    view.getBuilderView(Collections.singletonList("---Your portfolio has been created! "
            + "You can find it at : " + portfolioUuid + "---"));
  }

  /**
   * It processes the UserOptions.TWO and allows them to examine the composition of a portfolio, and
   * keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processInflexibleUserOptionTwo(String input) {
    input = getPortfolioIdInput(input);

    List<String> columns = new ArrayList<String>();
    columns.add("Ticker");
    columns.add("Number of shares");
    columns.add("Share Price");
    view.getInflexibleTableViewBuilder(model.getPortfolioContents(this.user, input), columns);
  }

  /**
   * It processes the flexible UserOptions.SECOND and allows them to sell one or more stocks of a
   * portfolio, and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionTwo(String input) {
    boolean invalidInput = true;

    String portfolioUuid = getPortfolioIdInput(input);

    view.getFlexibleSellStockView();
    while (!input.equals("DONE")) {
      // validate the user provided ticker/share/date combination
      while (invalidInput) {
        input = getUserInputView().toUpperCase(Locale.ROOT);
        if (model.validateFlexibleTickerShare(input) && model.isValidTicker(input.split(",")[0])
                && isValidDate(input.split(",")[2])) {
          invalidInput = false;
        } else {
          if (input.equals("DONE")) {
            invalidInput = false;
          } else {
            view.getBuilderView(Arrays.asList("Invalid input!", "Please enter a "
                    + "valid ticker/share/date (no fractional shares) combination : "));
          }
        }
      }
      // received correct combination from user to add share to their portfolio

      if (!input.equals("DONE") && model.sellFlexibleStock(this.user, portfolioUuid,
              input.split(",")[0], input.split(",")[1], input.split(",")[2])) {
        view.getBuilderView(Collections.singletonList("Ticker and number of shares sold "
                + "from your portfolio! "
                + "Enter DONE to exit "
                + "Portfolio or enter another valid stock to continue selling "));
        invalidInput = true;
      }
    }
    view.getBuilderView(Collections.singletonList("---Your portfolio has been updated! "
            + "You can find it at : " + portfolioUuid + "---"));
  }

  /**
   * It processes the UserOptions.THREE and allows them to determine the total value of a portfolio,
   * and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processInflexibleUserOptionThree(String input) {
    boolean invalidInput = true;
    String portfolioId = getPortfolioIdInput(input);

    view.getTotalPortfolioValueView();

    // validate date is in correct format
    while (invalidInput) {
      input = getUserInputView();
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid "
                + "date format entered!", "Please enter a valid date: "));
      }
    }

    // calculate total worth of a portfolio
    Map<Integer, List<String>> totalValueOfAPortfolio =
            model.calculateTotalValueOfAInflexiblePortfolio(input, this.user, portfolioId);

    double totalPortfolioValueSum = 0.0;
    // check if list length matches with expected length returned by model to
    // indicate if the entire portfolio has been processed
    for (Map.Entry<Integer, List<String>> entry : totalValueOfAPortfolio.entrySet()) {
      if (entry.getKey() != entry.getValue().size()) {

        for (int i = 0; i <= 100; i++) {
          if (i != 100) {
            // call the model again to fetch remaining ones
            totalValueOfAPortfolio = model
                    .calculateTotalValueOfAInflexiblePortfolio(input, this.user,
                    portfolioId);
          }
          view.getProgressBarView(i);
          try {
            Thread.sleep(600);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      }

      for (String row : totalValueOfAPortfolio.values().stream().findFirst().get()) {
        totalPortfolioValueSum += Double.parseDouble(row.split(",")[2]);
      }

      List<String> columns = new ArrayList<String>();
      columns.add("Ticker");
      columns.add("Number of shares");
      columns.add("Total Share Value");
      view.getInflexibleTableViewBuilder(totalValueOfAPortfolio
              .values().stream().findFirst().get(), columns);
      view.getBuilderView(Collections.singletonList("---The total value of this portfolio is: $"
              + totalPortfolioValueSum + "---"));
    }
  }

  /**
   * It processes the flexible UserOptions.THREE and allows them to see composition of their
   * portfolio, and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionThree(String input) {
    boolean invalidInput = true;

    String portfolioInput = getPortfolioIdInput(input);
    view.getFlexibleCompositionView();

    // validate date is in correct format
    while (invalidInput) {
      input = getUserInputView();
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid "
                + "date format entered!", "Please enter a valid date: "));
      }
    }

    List<String> columns = new ArrayList<String>();
    columns.add("Ticker");
    columns.add("Number of shares");
    columns.add("Share Price");
    columns.add("Date");
    view.getFlexibleTableViewBuilder(model
            .portfolioCompositionFlexible(portfolioInput, user, input), columns);
  }

  /**
   * It processes the UserOptions.FOUR and allows them to save a portfolio, and keeps prompting
   * until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processUserOptionFour(String input) {
    boolean invalidInput = true;
    view.getPortfolioFilePathHeaderView();

    List<String> portfolioUser = model.getPortfoliosForUser(this.user);

    view.getInflexibleTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getPortfolioFilePathInputView();
    while (invalidInput) {
      input = getUserInputView();
      if (model.validatePortfolioUUID(input, this.user)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid UUID entered!", "Please "
                + "enter a valid portfolio ID: "));
      }
    }

    String portfolioFilePath = this.user.getUserName() + "_" + input + ".csv";
    view.getBuilderView(Collections.singletonList("---Your portfolio " + input + " has been "
            + "serialized! You can find it at: " + portfolioFilePath + "---"));
  }

  /**
   * It processes the flexible UserOptions.FOUR and provided them the total value of a portfolio,
   * and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionFour(String input) {
    boolean invalidInput = true;
    String portfolioId = getPortfolioIdInput(input);

    view.getTotalPortfolioValueView();

    // validate date is in correct format
    while (invalidInput) {
      input = getUserInputView();
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid "
                + "date format entered!", "Please enter a valid date: "));
      }
    }

    // calculate total worth of a portfolio
    Map<Integer, List<String>> totalValueOfAPortfolio =
            model.calculateTotalValueOfAFlexiblePortfolio(input, this.user, portfolioId);

    double totalPortfolioValueSum = 0.0;
    // check if list length matches with expected length returned by model to
    // indicate if the entire portfolio has been processed
    for (Map.Entry<Integer, List<String>> entry : totalValueOfAPortfolio.entrySet()) {
      if (entry.getKey() != entry.getValue().size()) {

        for (int i = 0; i <= 100; i++) {
          if (i != 100) {
            // call the model again to fetch remaining ones
            totalValueOfAPortfolio = model.calculateTotalValueOfAFlexiblePortfolio(input, this.user,
                    portfolioId);
          }
          view.getProgressBarView(i);
          try {
            Thread.sleep(600);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      }

      for (String row : totalValueOfAPortfolio.values().stream().findFirst().get()) {
        totalPortfolioValueSum += Double.parseDouble(row.split(",")[2]);
      }

      List<String> columns = new ArrayList<>();
      columns.add("Ticker");
      columns.add("Number of shares");
      columns.add("Total Share Value");
      view.getInflexibleTableViewBuilder(totalValueOfAPortfolio
              .values().stream().findFirst().get(), columns);
      view.getBuilderView(Collections.singletonList("---The total value of this portfolio is: $"
              + totalPortfolioValueSum + "---"));
    }
  }

  /**
   * It processes the UserOptions.FIVE and allows them to load an external portfolio, and keeps
   * prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processUserOptionFive(String input) {
    boolean invalidInput = true;
    view.getSavePortfolioFromUserView();
    String pUUID = null;
    view.getSavePortfolioFilePathInputView();
    while (invalidInput) {
      input = getUserInputView();
      if (model.validateInflexibleUserPortfolioExternalPathAndContentsStructure(input)) {
        pUUID = model.saveExternalInflexibleUserPortfolio(input, this.user);
        if (pUUID != null) {
          invalidInput = false;
        }
      } else {
        view.getBuilderView(Arrays.asList("Invalid File Path entered or Structure of "
                + "File is malformed!", "Please enter a valid file path: "));
      }
    }
    view.getBuilderView(Collections.singletonList("---The external portfolio file has "
            + "been saved successfully. "
            + "You can find it at : " + this.user.getUserName() + "_" + pUUID + ".csv---"));
  }

  /**
   * It processes the flexible UserOptions.SIX and allows them to load an external portfolio, and
   * keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionSix(String input) {
    boolean invalidInput = true;
    view.getSavePortfolioFromUserView();
    String pUUID = null;
    view.getSavePortfolioFilePathInputView();
    while (invalidInput) {
      input = getUserInputView();
      if (model.validateFlexibleUserPortfolioExternalPathAndContentsStructure(input)) {
        pUUID = model.saveExternalFlexibleUserPortfolio(input, this.user);
        if (pUUID != null) {
          invalidInput = false;
        }
      } else {
        view.getBuilderView(Arrays.asList("Invalid File Path entered or Structure of "
                + "File is malformed!", "Please enter a valid file path: "));
      }
    }
    view.getBuilderView(Collections.singletonList("---The external portfolio file has "
            + "been saved successfully. "
            + "You can find it at : " + this.user.getUserName() + "_" + pUUID + ".csv---"));
  }

  /**
   * It processes the flexible UserOptions.SEVEN and allows them to determine the cost basis by a
   * specific date and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionSeven(String input) {
    boolean invalidInput = true;
    String portfolioUuid = getPortfolioIdInput(input);

    view.getCostBasisView();

    // validate date is in correct format
    while (invalidInput) {
      input = getUserInputView();
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid "
                + "date format entered!", "Please enter a valid date: "));
      }
    }

    double costBasisValue = model.calculateCostBasis(user, portfolioUuid, input);
    view.getBuilderView(Collections.singletonList("The total amount of money invested in "
            + "a portfolio : $" + costBasisValue));


  }

  /**
   * It processes the flexible UserOptions.EIGHT and displays them the portfolio performance over
   * time, and keeps prompting until successful sequence of input is received.
   *
   * @param input input received from the user.
   */
  private void processFlexibleUserOptionEight(String input) {
    boolean invalidInput = true;
    String portfolioUuid = getPortfolioIdInput(input);
    String date1 = null;
    String date2 = null;

    view.getPortfolioPerformanceView();

    // validate date is in correct format
    while (invalidInput) {
      date1 = getUserInputView();
      date2 = getUserInputView();
      if (isValidDate(date1) && isValidDate(date2)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid "
                + "date format entered!", "Please enter a valid date: "));
      }
    }

    List<String> result = model.getFlexiblePortfolioPerformance(date1, date2, portfolioUuid, user);
    view.getBuilderView(Collections.singletonList("Performance of portfolio " + portfolioUuid
            + "from " + date1 + " to " + date2));

    view.getBuilderView(result);
  }

  /**
   * It processes the view for new user creation and validates username and other inputs for a
   * successful response.
   *
   * @return true if process was successful, false, otherwise.
   */
  private boolean processNewUser() {
    boolean invalidUserName = true;
    String input;
    User user1 = null;

    view.getNewUserView();
    while (invalidUserName) {
      input = getUserInputView();
      while (input.length() > 8) {
        view.getBuilderView(Arrays.asList("Username can't be longer than 8 characters", "Please "
                + "enter a valid username: "));
        input = getUserInputView();

      }
      // get username and then attempt to save it
      user1 = model.saveUser(User
              .builder()
              .userName(input)
              .build());

      if (user1 != null) {
        invalidUserName = false;
      } else { // means invalid username meaning already exists
        view.getBuilderView(Arrays.asList("Username already exists", "Please "
                + "try another valid username"));
      }
    }

    this.user = user1;
    return true;
  }

  /**
   * It validates the portfolio ID recieved from the user, and keeps prompting until successful
   * input is received.
   *
   * @param input input received from the user.
   * @return input received from the user.
   */
  private String getPortfolioIdInput(String input) {
    boolean invalidInput = true;
    view.getPortfolioHeaderView();

    List<String> portfolioUser = model.getPortfoliosForUser(this.user);

    view.getInflexibleTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getPortfolioIdInputView();

    // validate correct portfolio ID is provided
    while (invalidInput) {

      input = getUserInputView();
      if (model.validatePortfolioUUID(input, this.user)) {
        invalidInput = false;
      } else {
        view.getInflexibleTableViewBuilder(portfolioUser,
                Collections.singletonList("Portfolio ID"));
        view.getBuilderView(Arrays.asList("Invalid UUID entered!", "Please enter a valid option:"));
      }
    }
    return input;
  }

  /**
   * It validates if the date provided by the user is a valid sequence.
   *
   * @param date String date input received from the user.
   * @return true if it's a valid date, false, otherwise.
   */
  private boolean isValidDate(String date) {
    LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate inputDate = LocalDate.parse(date, dtf);

    return !inputDate.isAfter(localDate);
  }

  /**
   * Gets user input from scanner.
   *
   * @return user input.
   */
  private String getUserInputView() {
    return scanner.next();
  }
}

