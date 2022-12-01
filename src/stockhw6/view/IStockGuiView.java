package stockhw6.view;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import stockhw6.controller.IStockGuiFeatures;

/**
 * This interface represents all the features supported for view GUI.
 */
public interface IStockGuiView {

  /**
   * It renders login screen view.
   */
  void getLoginScreenView();

  /**
   * It renders user options view for user logged in.
   *
   * @param userName user logged in.
   */
  void getUserOptionsView(String userName);

  /**
   * It renders existing user view.
   */
  void getExistingUserView();

  /**
   * It renders new user view.
   */
  void getNewUserView();

  /**
   * It renders error message for invalid inputs or failed to process any action.
   *
   * @param message message to display to the user.
   */
  void showErrorMessage(String message);

  /**
   * A callback function to call controller features based on user's actions.
   *
   * @param features supported by the controller.
   */
  void addGuiFeatures(IStockGuiFeatures features);

  /**
   * It renders flexible user option one to create a flexible portfolio.
   */
  void flexibleUserOptionOne();

  /**
   * It renders flexible user option two to allow them to sell stocks.
   */
  void flexibleUserOptionTwo();

  /**
   * It renders flexible user option three to allow them to calculate cost basis.
   */
  void flexibleUserOptionThree();

  /**
   * It renders flexible user option four to allow them to calculate total portfolio value.
   */
  void flexibleUserOptionFour();

  /**
   * It renders flexible user option five to display successful message.
   *
   * @param message successful response with portfolio id.
   */
  void flexibleUserOptionFiveAndSix(String message);

  /**
   * It renders dollar cost averaging view for existing portfolios.
   */
  void flexibleUserOptionEight();

  /**
   * It renders dollar cost averaging view for a new portfolio.
   */
  void flexibleUserOptionsNine();

  /**
   * It renders portfolio performance bar chart.
   */
  void flexibleUserOptionTen();

  /**
   * It renders successful message to the user.
   *
   * @param message successful response to display.
   * @param evt     Action event based on user input.
   */
  void showSuccessMessage(String message, ActionEvent evt);

  /**
   * It renders the portfolio ids available for a user.
   *
   * @param data    list of portfolio ids.
   * @param title   for the panel.
   * @param uOption option selected by the user.
   */
  void comboBoxBuilder(List<String> data, String title, String uOption);

  /**
   * It renders the total portfolio value.
   *
   * @param data                   list of stocks.
   * @param columns                list of columns.
   * @param totalPortfolioValueSum sum of total value of portfolio.
   */
  void portfolioValueTableView(List<String> data, List<String> columns,
                               double totalPortfolioValueSum);

  /**
   * It renders view to get external portfolio file path from user.
   */
  void getExternalPortfolioFilePath();

  /**
   * It renders portfolio performance bar chart.
   *
   * @param barChartContents the contents to be displayed on bar chart.
   */
  void showPerformanceGraph(Map<String, Integer> barChartContents);
}
