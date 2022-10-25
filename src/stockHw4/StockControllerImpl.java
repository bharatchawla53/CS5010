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
      view.getExistingUserView(); // TODO
    } else {
      view.getNewUserView(); // TODO
      // create new user at model level
    }

    // show options to the user
    view.getUserOptions();





  }
}
