package stockhw6.view;

import java.util.List;

/**
 * This interface represents all the features supported for views including flexible and inflexible
 * portfolios.
 */
public interface IStockViewMaker {
  /**
   * Method for loading a login screen.
   */
  void getLoginScreenView();

  /**
   * Method to build a view given a list of strings to be displayed, one on each line.
   *
   * @param values the List of values to be displayed, is of type string.
   */
  void getBuilderView(List<String> values);

  /**
   * Method for displaying prompt to get username from the user.
   */
  void getUsernameInputView();

  /**
   * Method to load a view that confirms if a user wishes to terminate the application.
   */
  void terminateView();

  /**
   * Method to prompt an existing user to select one of 6 inflexible options.
   */
  void getInflexibleUserOptionsView();

  /**
   * Method for loading view to build a inflexible portfolio, one ticker at a time.
   */
  void getInflexiblePortfolioCreatorView();

  /**
   * Method to build a view that renders a table given a List of rows, and List of columns.
   *
   * @param rows the rows the table must render apart from the header row.
   * @param cols the columns that the table must render.
   */
  void getInflexibleTableViewBuilder(List<String> rows, List<String> cols);

  /**
   * Method to build a view that renders a table given a List of rows, and List of columns.
   *
   * @param rows the rows the table must render apart from the header row.
   * @param cols the columns that the table must render.
   */
  void getFlexibleTableViewBuilder(List<String> rows, List<String> cols);

  /**
   * Method to display total value of a portfolio belonging to a specific user.
   */
  void getTotalPortfolioValueView();

  /**
   * Build a progress bar, showing progress of some input percent.
   *
   * @param i input percent to rendered as a progress bar.
   */
  void getProgressBarView(int i);

  /**
   * Method for displaying greeting prompt when user wants to load an external portfolio.
   */
  void getPortfolioFilePathHeaderView();

  /**
   * Method for displaying prompt to get file path input from user.
   */
  void getPortfolioFilePathInputView();

  /**
   * Method to load greeting prompt when user wants to save a portfolio.
   */
  void getSavePortfolioFromUserView();

  /**
   * Method to prompt user to enter file path when dumping an external portfolio.
   */
  void getSavePortfolioFilePathInputView();

  /**
   * Method to load greeting and load prompt for inputting username from user.
   */
  void getNewUserView();

  /**
   * Method for displaying greeting before showing all portfolios to user.
   */
  void getPortfolioHeaderView();

  /**
   * Method for displaying prompt to get portfolio UUID from the user.
   */
  void getPortfolioIdInputView();

  /**
   * Method for loading portfolio types view.
   */
  void getPortfolioTypeView();

  /**
   * Method to prompt an existing user to select one of 9 flexible options.
   */
  void getFlexibleUserOptionsView();

  /**
   * Method for loading view to build a flexible portfolio, one ticker at a time.
   */
  void getFlexiblePortfolioCreatorView();

  /**
   * Method for loading sell portfolio view.
   */
  void getFlexibleSellStockView();

  /**
   * Method for loading flexible portfolio composition view.
   */
  void getFlexibleCompositionView();

  /**
   * Method for loading portfolio cost basis view.
   */
  void getCostBasisView();

  /**
   * Method for loading portfolio performance view.
   */
  void getPortfolioPerformanceView();
}
