package stockHw4;

import java.util.List;

/**
 * This interface represents a StockView that defines the view operations listed below for user
 * interface.
 */
public interface StockView {

  /**
   * Method for loading a login screen.
   */
  void getLoginScreenView();

  /**
   * Method for displaying prompt to get username from the user.
   */
  void getUsernameInputView();

  /**
   * Method for displaying prompt to get portfolio UUID from the user.
   */
  void getPortfolioIdInputView();


  /**
   * Method for displaying greeting prompt when user wants to load an external portfolio.
   */
  void getPortfolioFilePathHeaderView();

  /**
   * Method for displaying prompt to get file path input from user.
   */
  void getPortfolioFilePathInputView();

  /**
   * Method to build a view given a list of strings to be displayed, one on each line.
   *
   * @param values the List of values to be displayed, is of type string.
   */
  void getBuilderView(List<String> values);

  /**
   * Method to load greeting prompt when user wants to a portfolio.
   */
  void getSavePortfolioFromUserView();

  /**
   * Method to prompt user to enter file path when dumping an external portfolio.
   */
  void getSavePortfolioFilePathInputView();

  /**
   * Build a progress bar, showing progress of some input percent.
   *
   * @param index input percent to rendered as a progress bar.
   */
  void getProgressBarView(int index);

  /**
   * Method to load greeting and load prompt for inputting username from user.
   */
  void getNewUserView();

  /**
   * Method to prompt an existing user to select one of 6 options. The 1st option loads a view to
   * create a portfolio. The 2nd option loads a view to let the user view the contents of a specific
   * portfolio. The 3rd option loads a view to calculate the total value of a portfolio. The 4th
   * option loads a view to allow a user to dump an existing portfolio. The 5th option loads a view
   * to allow a user to load an external portfolio. The 6th option terminates the running of the
   * application.
   */
  void getUserOptionsView();

  /**
   * Method for loading view to build a portfolio, one ticker at a time.
   */
  void getPortfolioCreatorView();

  /**
   * Method for displaying greeting before showing all portfolios to user.
   */
  void getPortfolioHeaderView();


  /**
   * Method to display total value of a portfolio belonging to a specific user.
   */
  void getTotalPortfolioValueView();

  /**
   * Method to load a view that confirms if a user wishes to terminate the application.
   */
  void terminateView();

  /**
   * Method to build a view that renders a table given a List of rows, and List of columns
   *
   * @param rows    the rows the table must render apart from the header row.
   * @param columns the columns that the table must render.
   */
  void getTableViewBuilder(List<String> rows, List<String> columns);
}
