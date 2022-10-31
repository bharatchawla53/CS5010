package stockHw4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * The StockControllerImpl class represents as a mediator between model and view.
 */
public class StockControllerImpl extends StockControllerAbstract {
  private final StockModel model;
  private final StockView view;
  private User user;
  private Boolean isUserOperationSuccessful;

  /**
   * Constructs a StockControllerImpl constructor and initializes model and view.
   *
   * @param model StockModel that controller talks to.
   * @param view StockView that controller talk to.
   */
  public StockControllerImpl(StockModel model, StockView view) {
    this.model = model;
    this.view = view;
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

      while (this.isUserOperationSuccessful) {
        keepPromotingUserForOptionsOnceAOperationIsDone(input);
      }
    } else {
      if (processNewUser()) {
        while (this.isUserOperationSuccessful) {
          keepPromotingUserForOptionsOnceAOperationIsDone(input);
        }
      }
    }
  }

  private String getLoginScreenInput() {
    boolean invalidInput = true;
    String input = null;

    view.getLoginScreenView();
    // validate the user login input
    while (invalidInput) {
      try {
        input = view.getUserInputView(System.in).toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid user input", "Enter Y/N : "));
      }
    }
    return input;
  }

  private String getValidUserNameInput(String input) {
    boolean invalidInput = true;

    view.getUsernameInputView();
    // validate the username input and check if it already exists in our records
    while (invalidInput) {
      input = view.getUserInputView(System.in);
      if (model.isUserNameExists(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Collections.singletonList("This username does not exist! Enter an existing username:"));
      }
    }
    return input;
  }

  private void keepPromotingUserForOptionsOnceAOperationIsDone(String input) {
    input = getUserOptionsInput(input);

    if (input.equals(UserInputOptions.ONE.getInput())) {
      processUserOptionOne(input);
    } else if (input.equals(UserInputOptions.TWO.getInput())) {
      processUserOptionTwo(input);
    } else if (input.equals(UserInputOptions.THREE.getInput())) {
      processUserOptionThree(input);
    } else if (input.equals(UserInputOptions.FOUR.getInput())) {
      processUserOptionFour(input);
    } else if (input.equals(UserInputOptions.FIVE.getInput())) {
      processUserOptionFive(input);
    } else if (input.equals(UserInputOptions.SIX.getInput())) {
      terminateApplication(input);
    }
  }

  private void terminateApplication(String input) {
    boolean invalidInput = true;
    view.terminateView();

    // validate the user input
    while (invalidInput) {
      try {
        input = view.getUserInputView(System.in).toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid input", "Enter Y/N : "));
      }
    }

    if (input.equals(UserInput.Y.name())) {
      this.isUserOperationSuccessful = false;
      System.exit(0);
    }
  }

  private String getUserOptionsInput(String input) {
    boolean invalidInput = true;

    view.getUserOptionsView();
    // validate the user options input
    while (invalidInput) {
      try {
        input = view.getUserInputView(System.in).toUpperCase(Locale.ROOT);

        String finalInput = input;
        Optional<UserInputOptions> userInputOption = Arrays.stream(UserInputOptions.values())
                .filter(u -> finalInput.equals(u.getInput()))
                .findFirst();

        if (userInputOption.isPresent()) {
          invalidInput = false;
        }

      } catch (IllegalArgumentException e) {
        view.getBuilderView(Arrays.asList("Invalid option!", "Please enter a valid option:"));
      }
    }
    return input;
  }

  // TODO restrict users from purchasing fractional shares
  // Fractional Shares dealt with in regex(regex looks for whole numbers only)
  // TODO allow user to create portfolio by providing a file
  private void processUserOptionOne(String input) {
    boolean invalidInput = true;

    view.getPortfolioCreatorView();
    String portfolioUuid = model.generateUUID();
    while (!input.equals("DONE")) {
      // validate the user provided ticker/share combination
      while (invalidInput) {
        input = view.getUserInputView(System.in).toUpperCase(Locale.ROOT);
        if (model.validateTickerShare(input) && model.isValidTicker(input.split(",")[0])) {
          invalidInput = false;
        } else {
          if (input.equals("DONE")) {
            invalidInput = false;
            // TODO add a view stating portfolio  is successfully created
          } else {
            view.getBuilderView(Arrays.asList("Invalid input!", "Please enter a valid ticker/share (no fractional shares) combination : "));
          }
        }
      }
      // received correct combination from user to add share to their portfolio

      // TODO find a way to avoid Index out of bounds exception
      if (!input.equals("DONE") && model.saveStock(this.user, portfolioUuid,
              input.split(",")[0], input.split(",")[1])) {
        // TODO add successful view as well and tell them to enter another stock or enter "DONE" to exit this process
        view.getBuilderView(Collections.singletonList("Ticker and number of shares added to portfolio! Enter DONE to exit " +
                "Portfolio Creation or enter another valid stock to continue"));
        invalidInput = true;
      }
    }
    view.getBuilderView(Collections.singletonList("Your portfolio has been created! You can find it at" + portfolioUuid));
  }

  private void processUserOptionTwo(String input) {
    input = getPortfolioIdInput(input);
    //TODO Display all tickershare given user and portfolio uuid
    // TODO add a generic table view

    List<String> columns = new ArrayList<String>();
    columns.add("Ticker");
    columns.add("Number of shares");
    columns.add("Share Price");
    view.getTableViewBuilder(model.getPortfolioContents(this.user, input), columns);
  }

  private void processUserOptionThree(String input) {
    boolean invalidInput = true;
    String portfolioId = getPortfolioIdInput(input);

    view.getTotalPortfolioValueView();

    // validate date is in correct format
    while (invalidInput) {
      input = view.getUserInputView(System.in);
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid date format entered!", "Please enter a valid date: "));
      }
    }

    // calculate total worth of a portfolio
    Map<Integer, List<String>> totalValueOfAPortfolio = model.calculateTotalValueOfAPortfolio(input, this.user, portfolioId);

    double totalPortfolioValueSum = 0.0;
    // check if list length matches with expected length returned by model to indicate if the entire portfolio has been processed
    for (Map.Entry<Integer, List<String>> entry : totalValueOfAPortfolio.entrySet()) {
      if (entry.getKey() != entry.getValue().size()) {
        
        for (int i = 0; i <= 100; i++) {
          if (i != 100) {
            // call the model again to fetch remaining ones
            totalValueOfAPortfolio = model.calculateTotalValueOfAPortfolio(input, this.user, portfolioId);
          }
          view.getProgressBarView(i);
          try {
            Thread.sleep( 600);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      }

      for (String row : totalValueOfAPortfolio.values().stream().findFirst().get()) {
        totalPortfolioValueSum += Double.parseDouble(row.split(" ")[2]);
      }

      List<String> columns = new ArrayList<String>();
      columns.add("Ticker");
      columns.add("Number of shares");
      columns.add("Total Share Value");
      view.getTableViewBuilder(totalValueOfAPortfolio.values().stream().findFirst().get(), columns);
      view.getBuilderView(Collections.singletonList("The total value of this portfolio is: " + totalPortfolioValueSum));
    }
  }

  // TODO add load the file option and test 4 completely
  // TODO add subviews whether to save it or load a file to create a portfolio
  private void processUserOptionFour(String input) {
    boolean invalidInput = true;
    view.getPortfolioFilePathHeaderView();

    List<String> portfolioUser = model.getPortfoliosForUser(this.user);

    view.getTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getPortfolioFilePathInputView();
    while (invalidInput) {
      input = view.getUserInputView(System.in);
      if (model.validatePortfolioUUID(input, this.user)) {
        invalidInput = false;
      } else {
        view.getBuilderView(Arrays.asList("Invalid UUID entered!", "Please enter a valid portfolio ID: "));
      }
    }

    // TODO make directory path dynamic
    String portfolioFilePath = "CS5010/" + this.user.getUserName() + "_" + input + ".csv";
    List<String> serializedPortfolioSuccess = new ArrayList<String>();
    serializedPortfolioSuccess.add("Your portfolio " + input + " has been serialized! You can find it at: " + portfolioFilePath);
    view.getBuilderView(serializedPortfolioSuccess);
  }

  private void processUserOptionFive(String input) {
    boolean invalidInput = true;
    view.getSavePortfolioFromUserView();
    String pUUID=null;
    view.getSavePortfolioFilePathInputView();
    while (invalidInput) {
      input = view.getUserInputView(System.in);
      if (model.validateUserPortfolioExternalPathAndContentsStructure(input)) {
        pUUID = model.saveExternalUserPortfolio(input, this.user);
        if(pUUID != null)
        {
          invalidInput = false;
        }
      }
       else {
         view.getBuilderView(Arrays.asList("Invalid File Path entered or Structure of File is malformed!", "Please enter a valid file path: "));
      }
    }
    if (pUUID != null) {
      view.getBuilderView(Collections.singletonList("The external portfolio file has been saved successfully. You can find it at :" + pUUID));
    }
  }


  private boolean processNewUser() {
    boolean invalidUserName = true;
    String input;
    User user1 = null;

    view.getNewUserView();
    while (invalidUserName) {
      input = view.getUserInputView(System.in);
      while (input.length() > 8) {
        view.getBuilderView(Arrays.asList("Username can't be longer than 8 characters", "Please enter a valid username: "));
        input = view.getUserInputView(System.in);

      }
      // get username and then attempt to save it
      user1 = model.saveUser(User
              .builder()
              .userName(input)
              .build());

      if (user1 != null) {
        invalidUserName = false;
      } else { // means invalid username meaning already exists
        view.getBuilderView(Arrays.asList("Username already exists", "Please try another valid username"));
      }
    }

    this.user = user1;
    return true;
  }


  private String getPortfolioIdInput(String input) {
    boolean invalidInput = true;
    view.getPortfolioHeaderView();

    List<String> portfolioUser = model.getPortfoliosForUser(this.user);

    view.getTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getPortfolioIdInputView();

    // validate correct portfolio ID is provided
    while (invalidInput) {

        input = view.getUserInputView(System.in);
        if (model.validatePortfolioUUID(input, this.user)) {
          invalidInput = false;
        }

       else {
         view.getBuilderView(Arrays.asList("Invalid UUID entered!", "Please enter a valid option:"));
      }
    }
    return input;
  }

  private boolean isValidDate(String date) {
    return LocalDate.parse(date) != null;
  }
}

