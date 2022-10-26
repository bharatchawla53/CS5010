package stockHw4;

import java.util.Locale;

public class StockControllerImpl extends StockControllerAbstract {
  private StockModel model;
  private StockView view;

  @Override
  public void start(StockModel model, StockView view) {
    view.getLoginScreenView();

    String input = null;
    boolean invalidInput = true;
    boolean invalidUserName = true;

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

    if (input.equals(UserInput.Y.name())) {
      view.getExistingUserOptions(); // TODO
      input = view.getUserInput(System.in);
    } else {
      view.getNewUserView();
      while (invalidUserName) {
        input = view.getUserInput(System.in);
        while (input.length() > 8) {
          view.throwErrorMessage("Username can't be longer than 8 characters");
          view.getErrorMessageView("Please enter a valid username");
          input = view.getUserInput(System.in);
        }
        // get username and then attempt to save it
        User user = model.saveUser(User
                .builder()
                .userName(input)
                .build());
        if (user != null) {
          invalidUserName = false;
        } else { // means invalid username meaning already exists
          view.throwErrorMessage("Username already exists");
          view.getErrorMessageView("Please try another valid username");
        }
      }
      // if successful proceed to show options
      view.getNewUserOptions(); // TODO
      input = view.getUserInput(System.in);
    }

  }
}
