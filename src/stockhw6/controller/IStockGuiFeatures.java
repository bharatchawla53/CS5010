package stockhw6.controller;

import java.awt.event.ActionEvent;

/**
 * This interface represents all the features supported for controller-view callback GUI.
 */
public interface IStockGuiFeatures {
  /**
   * Based on user input event, it renders existing or new user view.
   *
   * @param evt ActionEvent to determine user's input.
   */
  void getLoginScreenView(ActionEvent evt);

  /**
   * It validates the existing username input from user.
   *
   * @param userName input provided from client.
   */
  void getUserName(String userName);

  /**
   * It saves a new user if username meets requirements.
   *
   * @param userName input provided from client.
   * @param evt      ActionEvent to determine correct success message for view.
   */
  void saveNewUser(String userName, ActionEvent evt);

  /**
   * Based on user selection, it renders correct user input option vew.
   *
   * @param evt ActionEvent to determine user's input.
   */
  void getUserOptionsView(ActionEvent evt);

  /**
   * It process a user option to create a portfolio.
   *
   * @param input consists of symbol,share,date, and commission fees.
   * @param evt   ActionEvent to determine user's input.
   */
  void processFlexibleOptionOne(String input, ActionEvent evt);

  /**
   * It process a user option to allow to sell stocks for existing portfolio.
   *
   * @param input consists of symbol,share,date, and commission fees.
   * @param evt   ActionEvent to determine user's input.
   */
  void processFlexibleOptionTwo(String input, ActionEvent evt);

  /**
   * It process a user option to allow them to calculate cost basis of a portfolio.
   *
   * @param input date to when to calculate cost basis on.
   * @param evt   ActionEvent to determine user's input.
   */
  void processFlexibleOptionThree(String input, ActionEvent evt);

  /**
   * It process a user option to allow to calculate total value of a portfolio.
   *
   * @param input date to when to calculate portfolio value on.
   * @param evt   ActionEvent to determine user's input.
   */
  void processFlexibleOptionFour(String input, ActionEvent evt);

  /**
   * It process a user option to allow to create a dollar cost averaging for existing portfolio.
   *
   * @param date           when to start investing.
   * @param capital        the total amount to invest.
   * @param symbol         the symbol that needs to be added to the portfolio.
   * @param weightage      the weightage for each symbol.
   * @param commissionFees the fees for each transaction.
   * @param evt            ActionEvent to determine user's input.
   */
  void processFlexibleOptionEight(String date, String capital, String symbol, String weightage,
                                  String commissionFees, ActionEvent evt);

  /**
   * It process a user option to allow to create a dollar cost averaging for a new portfolio.
   *
   * @param capital          the total amount to invest.
   * @param symbol           the symbol that needs to be added to the portfolio.
   * @param weightage        he weightage for each symbol.
   * @param date             when to start investing.
   * @param date2            when to end investing.
   * @param timeIntervalUnit unit to how frequently the investment should be made.
   * @param timeFrequency    input for how often the investment is done.
   * @param commissionFees   the fees for each transaction.
   * @param evt              ActionEvent to determine user's input.
   */
  void processFlexibleOptionNine(String capital, String symbol, String weightage,
                                 String date, String date2, String timeIntervalUnit,
                                 String timeFrequency, String commissionFees, ActionEvent evt);

  /**
   * It process a user option to generate a graph bar chart.
   *
   * @param date1 start date
   * @param date2 end date
   * @param evt   ActionEvent to determine user's input.
   */
  void processFlexibleOptionTen(String date1, String date2, ActionEvent evt);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionTwo(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionThree(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionFour(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionFive(String input);

  /**
   * It processes user selected filepath to load an external portfolio.
   *
   * @param input filepath
   */
  void getFilePathForOptionSix(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionSeven(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionEight(String input);

  /**
   * It processes user selected portfolio id input and renders view accordingly.
   *
   * @param input portfolioId
   */
  void getPortfolioIdInputForOptionTen(String input);
}
