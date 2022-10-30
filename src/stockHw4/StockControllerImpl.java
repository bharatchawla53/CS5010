package stockHw4;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class StockControllerImpl extends StockControllerAbstract {
  private final StockModel model;
  private final StockView view;
  private User user;
  private Boolean isUserOperationSuccessful;

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
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.throwErrorMessage("Invalid user input");
        view.getErrorMessageView("Enter Y/N : ");
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
      }
    }
    return input;
  }

  private String getValidUserNameInput(String input) {
    boolean invalidInput = true;

    view.getUsernameFromUser();
    // validate the username input and check if it already exists in our records
    while (invalidInput) {
      input = view.getUserInput(System.in);
      if (model.isUserNameExists(input)) {
        invalidInput = false;
      } else {
        view.throwErrorMessage("This username does not exist! Enter an existing username:");
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
      processUserOptionSix(input);
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
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
        if (UserInput.valueOf(input).name().equals(input)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.throwErrorMessage("Invalid input");
        view.getErrorMessageView("Enter Y/N : ");
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
      }
    }

    if (input.equals(UserInput.Y.name())) {
      this.isUserOperationSuccessful = false;
      System.exit(0);
    }
  }

  private String getUserOptionsInput(String input) {
    boolean invalidInput = true;

    view.getExistingUserOptions();
    // validate the user options input
    while (invalidInput) {
      try {
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);

        String finalInput = input;
        Optional<UserInputOptions> userInputOption = Arrays.stream(UserInputOptions.values())
                .filter(u -> finalInput.equals(u.getInput()))
                .findFirst();

        if (userInputOption.isPresent()) {
          invalidInput = false;
        }

      } catch (IllegalArgumentException e) {
        view.throwErrorMessage("Invalid option!");
        view.getErrorMessageView("Please enter a valid option:");
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
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
        input = view.getUserInput(System.in).toUpperCase(Locale.ROOT);
        model.validateTickerShare(input);
        if (model.isValidTicker(input.split(",")[0])) {
          invalidInput = false;
        } else {
          if (input.equals("DONE")) {
            invalidInput = false;
            // TODO add a view stating portfolio  is successfully created
          } else {
            view.throwErrorMessage("Invalid input!");
            view.getErrorMessageView("Please enter a valid ticker/share combination: ");
          }
        }
      }
      // received correct combination from user to add share to their portfolio

      // TODO find a way to avoid Index out of bounds exception
      if (!input.equals("DONE") && model.dumpTickerShare(this.user, portfolioUuid,
              input.split(",")[0], input.split(",")[1])) {
        // TODO add successful view as well and tell them to enter another stock or enter "DONE" to exit this process
        view.getDisplaySuccessfulTickerShareDump(portfolioUuid);
        invalidInput = true;
      }
    }
    view.getDisplayFinishedDumpingPortfolio(portfolioUuid);
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
    // present the list of portfolios
    // get the portfolio id
    // then get date
    // then compute
    boolean invalidInput = true;
    String portfolioId = getPortfolioIdInput(input);

    view.getDisplayTotalPortfolioValue();

    // validate date is in correct format
    while (invalidInput) {
      input = view.getUserInput(System.in);
      if (isValidDate(input)) {
        invalidInput = false;
      } else {
        view.throwErrorMessage("Invalid date format entered!");
        view.getErrorMessageView("Please enter a valid date:");
        input = view.getUserInput(System.in);
      }
    }

    // calculate total worth of a portfolio
    List<String> totalValueOfAPortfolio = model.calculateTotalValueOfAPortfolio(input, this.user, portfolioId);
    double totalPortfolioValueSum = 0.0;
    for(String row: totalValueOfAPortfolio)
    {
      System.out.println(row);
      totalPortfolioValueSum+= Double.parseDouble(row.split(" ")[2]);
    }

    // render it to view
    List<String> columns = new ArrayList<String>();
    List<String> totalPortfolioValue = new ArrayList<String>();

    totalPortfolioValue.add("The total value of this portfolio is:"+String.valueOf(totalPortfolioValueSum));
    columns.add("Ticker");
    columns.add("Number of shares");
    columns.add("Total Share Value");
    view.getTableViewBuilder(totalValueOfAPortfolio, columns);
    view.getViewBuilder(totalPortfolioValue);
  }

  // TODO add load the file option and test 4 completely
  // TODO add subviews whether to save it or load a file to create a portfolio
  private void processUserOptionFour(String input) {
    boolean invalidInput = true;
    view.getDisplayPortfolioFilePathHeader();

    List<String> portfolioUser = model.getAllPortfoliosFromUser(this.user);

    view.getTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getDisplayPortfolioFilePathInput();
    while (invalidInput) {
      input = view.getUserInput(System.in);
      if (model.validatePortfolioUUID(input, this.user)) {
        invalidInput = false;
      } else {
        view.throwErrorMessage("Invalid UUID entered!");
        view.getErrorMessageView("Please enter a valid option:");
      }
    }

    // TODO make directory path dynamic
    String portfolioFilePath = "CS5010/" + this.user.getUserName() + "_" + input + ".csv";
    List<String> serializedPortfolioSuccess = new ArrayList<String>();
    serializedPortfolioSuccess.add("Your portfolio " + input + " has been serialized! You can find it at: " + portfolioFilePath);
    view.getViewBuilder(serializedPortfolioSuccess);
  }

  private void processUserOptionSix(String input) {
    boolean invalidInput = true;
    view.getDisplaySavePortfolioFromUser();

    view.getDisplaySavePortfolioFilePathInput();
    while (invalidInput) {
      input = view.getUserInput(System.in);
      if (model.validateUserPortfolioExternal(input, this.user)) {
        invalidInput = false;
      } else {
        view.throwErrorMessage("Invalid File Path entered or Structure of File is malformed!");
        view.getErrorMessageView("Please enter a valid file path:");
      }
    }
    String pUUID = model.saveExternalUserPortfolio(input, this.user);
    if (pUUID != null) {
      view.getDisplaySuccessfullPortfolioSave(pUUID);
    }
  }


  private boolean processNewUser() {
    boolean invalidUserName = true;
    String input;
    User user1 = null;

    view.getNewUserView();
    while (invalidUserName) {
      input = view.getUserInput(System.in);
      while (input.length() > 8) {
        view.throwErrorMessage("Username can't be longer than 8 characters");
        view.getErrorMessageView("Please enter a valid username");
        input = view.getUserInput(System.in);

      }
      // get username and then attempt to save it
      user1 = model.saveUser(User
              .builder()
              .userName(input)
              .build());

      if (user1 != null) {
        invalidUserName = false;
      } else { // means invalid username meaning already exists
        view.throwErrorMessage("Username already exists");
        view.getErrorMessageView("Please try another valid username");
      }
    }

    this.user = user1;
    return true;
  }


  private String getPortfolioIdInput(String input) {
    boolean invalidInput = true;
    view.getDisplayPortfolioHeader();

    List<String> portfolioUser = model.getAllPortfoliosFromUser(this.user);

    view.getTableViewBuilder(portfolioUser, Collections.singletonList("Portfolio ID"));
    //view.getViewBuilder(portfolioUser);
    view.getDisplayPortfolioInput();

    // validate correct portfolio ID is provided
    while (invalidInput) {
      try {
        input = view.getUserInput(System.in);
        if (model.validatePortfolioUUID(input, this.user)) {
          invalidInput = false;
        }
      } catch (IllegalArgumentException e) {
        view.throwErrorMessage("Invalid UUID entered!");
        view.getErrorMessageView("Please enter a valid option:");
        input = view.getUserInput(System.in);
      }
    }
    return input;
  }

  private boolean isValidDate(String date) {
    return LocalDate.parse(date) != null;
  }
}

