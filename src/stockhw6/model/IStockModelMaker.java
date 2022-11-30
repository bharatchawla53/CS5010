package stockhw6.model;

import java.util.List;
import java.util.Map;

/**
 * This interface represents all the features supported for models including flexible and inflexible
 * portfolios.
 */
public interface IStockModelMaker {

  /**
   * It returns a user based on a given username.
   *
   * @param username input received from the user.
   * @return an authenticated user.
   */
  User getUserFromUsername(String username);

  /**
   * Given the username, it checks against the user file if the user already exists.
   *
   * @param username input received from the user.
   * @return true if username already exists, false, otherwise.
   */
  boolean isUserNameExists(String username);

  /**
   * Generated a random UUID.
   *
   * @return a random generated UUID.
   */
  String generateUUID();

  /**
   * Given the combination provided by the user, it validates if it's a correct sequence. Expected
   * Sequence : Symbol,shares
   *
   * @param tickerShare input received from the user.
   * @return true if it's a valid sequence, false, otherwise.
   */
  boolean validateInflexibleTickerShare(String tickerShare);

  /**
   * Given the ticker provided by the user, it validates if it's a legitimate symbol.
   *
   * @param inputTicker input received from the user.
   * @return true if it's a valid ticker, false, otherwise.
   */
  boolean isValidTicker(String inputTicker);

  /**
   * Save the user stock to their portfolio.
   *
   * @param user          that is attempting to save it's stock to their portfolio.
   * @param portfolioUuid the unique ID of the portfolio.
   * @param ticker        the stock user wants to add to their portfolio.
   * @param noOfShares    the number of shares for the given ticker.
   * @return true if the stock was saved successfully, false, otherwise.
   */
  boolean saveInflexibleStock(User user, String portfolioUuid, String ticker, String noOfShares);

  /**
   * It returns a list of stocks that are saved in a user portfolio.
   *
   * @param user          that is requesting to load its portfolio.
   * @param portfolioUuid the unique ID of the portfolio.
   * @return the list of stocks.
   */
  List<String> getPortfolioContents(User user, String portfolioUuid);

  /**
   * Gets the composition of a flexible input portfolio.
   *
   * @param portfolioUUID the portfolio UUID.
   * @param user          the user that owns the portfolio.
   * @param date          the date on which you want to generate the composition of the portfolio.
   * @return the composition of the portfolio to be rendered on the view.
   */
  List<String> portfolioCompositionFlexible(String portfolioUUID, User user, String date);

  /**
   * It calculates total value of a portfolio on a certain date.
   *
   * @param certainDate   input received from the user on which they want to calculate total value.
   * @param user          that is requesting to determine total value.
   * @param portfolioUuid the unique ID of the portfolio.
   * @return map of expected contents of a portfolio with the list of how many are processed.
   */
  Map<Integer, List<String>> calculateTotalValueOfAInflexiblePortfolio(
          String certainDate, User user, String portfolioUuid);

  /**
   * Gets the list of all portfolios for a user.
   *
   * @param user that is attempting to request it's portfolio.
   * @return the list of portfolios for the requested user.
   */
  List<String> getPortfoliosForUser(User user);

  /**
   * It validates portfolio UUID entered by the user to access their portfolio.
   *
   * @param portfolioUuid the unique ID of the portfolio.
   * @param user          that is requesting to load its portfolio.
   * @return true if it's a valid UUID, false, otherwise.
   */
  boolean validatePortfolioUUID(String portfolioUuid, User user);

  /**
   * It validates if the filepath provided is correct and the contents of it are in right sequence.
   *
   * @param filepath input received from the user.
   * @return true if it's a valid path, false, otherwise.
   */
  boolean validateInflexibleUserPortfolioExternalPathAndContentsStructure(String filepath);

  /**
   * It saves a user provided portfolio file.
   *
   * @param filepath input received from the user.
   * @param user     that is requesting to save external provided portfolio file.
   * @return the name of the portfolio that was used to save it.
   */
  String saveExternalInflexibleUserPortfolio(String filepath, User user);

  /**
   * Given the user data, it saves the user details to a user file.
   *
   * @param user User to be added to the csv file.
   * @return User that was saved.
   */
  User saveUser(User user);

  /**
   * Validates the user input for ticker, number of bought shares, and the date of purchase.
   *
   * @param tickerShare input received from the user.
   * @return if validation is successful, return true, else return false.
   */
  boolean validateFlexibleTickerShare(String tickerShare);

  /**
   * Save the user stock to their portfolio.
   *
   * @param user          that is attempting to save it's stock to their portfolio.
   * @param portfolioUuid the unique ID of the portfolio.
   * @param ticker        the stock user wants to add to their portfolio.
   * @param noOfShares    the number of shares for the given ticker.
   * @return true if the stock was saved successfully, false, otherwise.
   */
  boolean saveFlexibleStock(User user, String portfolioUuid, String ticker,
                            String noOfShares, String date, int commissionFees);

  /**
   * Sells some shares of some ticker on a date and adds it to a portfolio belonging to a user.
   *
   * @param user          user The user who owns the portfolio.
   * @param portfolioUuid portfolioUUID the portfolio UUID.
   * @param ticker        the ticker that the user wants to buys shares of.
   * @param noOfShares    noOfShares the no of shares that user wants to buy.
   * @param date          the date that the user purchases these shares.
   * @return if negative shares were inserted into the portfolio return true else false.
   */
  boolean sellFlexibleStock(User user, String portfolioUuid, String ticker,
                            String noOfShares, String date, int commissionFees);

  /**
   * It calculates total value of a portfolio on a certain date.
   *
   * @param certainDate   input received from the user on which they want to calculate total value.
   * @param user          that is requesting to determine total value.
   * @param portfolioUuid the unique ID of the portfolio.
   * @return map of expected contents of a portfolio with the list of how many are processed.
   */
  Map<Integer, List<String>> calculateTotalValueOfAFlexiblePortfolio(
          String certainDate, User user, String portfolioUuid);

  /**
   * Calculates the cost basis on a date for a given portfolio, given the user.
   *
   * @param user          the owner of the portfolio.
   * @param portfolioUuid the portfolio UUID of the portfolio.
   * @param date          the date on which the cost basis needs to be calculated on
   * @return the cost basis of the portfolio
   */
  double calculateCostBasis(User user, String portfolioUuid, String date);

  /**
   * It validates if the filepath provided is correct and the contents of it are in right sequence.
   *
   * @param filepath input received from the user.
   * @return true if it's a valid path, false, otherwise.
   */
  boolean validateFlexibleUserPortfolioExternalPathAndContentsStructure(String filepath);

  /**
   * It saves a user provided portfolio file.
   *
   * @param filepath input received from the user.
   * @param user     that is requesting to save external provided portfolio file.
   * @return the name of the portfolio that was used to save it.
   */
  String saveExternalFlexibleUserPortfolio(String filepath, User user);

  /**
   * Generates a performance graph given a start and end date, a portfolio, and a user.
   *
   * @param date1         the start date for the graph.
   * @param date2         the end date for the graph.
   * @param portfolioUuid the portfolio uuid.
   * @param user          the user owning the portfolio.
   * @return a list of lines that make up the graph as a whole.
   */
  List<String> getFlexiblePortfolioPerformance(String date1, String date2,
                                               String portfolioUuid, User user);

  /**
   * Given an existing portfolio, update the portfolio to have a list of transactions. added for a
   * specific date.
   *
   * @param user           the user of the application.
   * @param portfolioUUID  the uuid of the portfolio belonging to the user.
   * @param tickerList     the list of tickers to add buy transactions in the portfolio.
   * @param startDate      the date on which each buy transaction is performed.
   * @param capital        the total amount available to the user, to distribute to each ticker on
   *                       each date.
   * @param weightList     the percentage of the capital that needs to be distributed to each ticker
   *                       in the ticker list.
   * @param commissionFees the commission rate associated with each buy transaction.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction.
   * failed in the generated list of transactions from the plan.
   */
  boolean updatePortfolioBasedOnInvestment(User user, String portfolioUUID, List<String> tickerList,
                                           String startDate, int capital, List<Integer> weightList, int commissionFees);

  /**
   * Given the contents of the performance graph, convert it to a hashmap that maps a. date to the
   * value of portfolio in integer based on some scale.
   *
   * @param date1         the date on which the user wants to begin analyzing the portfolio.
   * @param date2         the date on which the user wants to end analyzing the portfolio.
   * @param portfolioUUID the uuid of the portfolio belonging to the user.
   * @param user          the user using the app.
   * @return a hashmap containing the list of dates to be rendered as a key and the number of stars
   * based on some scale as a value.
   */
  Map<String, Integer> getBarChartContents(String date1, String date2, String portfolioUUID, User user);

  /**
   * Create a dollar cost averaged portfolio from start to finish.
   *
   * @param user           current user of the app.
   * @param portfolioUUID  the portfolio uuid of the portfolio that belongs to the above user.
   * @param tickers        the list of tickers/stocks the user wants to invest in.
   * @param startDate      the date on which the user wishes to begin investing in the portfolio.
   * @param endDate        the date on which the user wishes to end investing in the portfolio.
   * @param daySkip        skip these many number of days before investing in a given ticker again.
   * @param capital        the total amount available to the user to invest in the portfolio.
   * @param weightList     the percentage of capital to assign to each ticker in the ticker list.
   * @param commissionRate the commission to apply for each transaction generated by the.
   *                       dollarcostaveraged plan.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction.
   * failed in the generated list of transactions from the plan.
   */
  boolean createPortfolioBasedOnPlan(User user, String portfolioUUID, List<String> tickers,
                                     String startDate, String endDate,
                                     int daySkip, int capital,
                                     List<Integer> weightList, int commissionRate);
}
