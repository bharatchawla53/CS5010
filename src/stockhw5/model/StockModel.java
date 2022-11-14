package stockhw5.model;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a StockModel that defines the operations listed below.
 */

public interface StockModel {

  /**
   * Given the user data, it saves the user details to a user file.
   *
   * @param user User to be added to the csv file.
   * @return User that was saved.
   */
  User saveUser(User user);

  /**
   * Given the username, it checks against the user file if the user already exists.
   *
   * @param userName input received from the user.
   * @return true if username already exists, false, otherwise.
   */
  boolean isUserNameExists(String userName);

  /**
   * Given the ticker provided by the user, it validates if it's a legitimate symbol.
   *
   * @param inputTicker input received from the user.
   * @return true if it's a valid ticker, false, otherwise.
   */
  boolean isValidTicker(String inputTicker);

  /**
   * Given the combination provided by the user, it validates if it's a correct sequence. Expected
   * Sequence : Symbol,shares
   *
   * @param tickerShare input received from the user.
   * @return true if it's a valid sequence, false, otherwise.
   */
  boolean validateTickerShare(String tickerShare);

  /**
   * Gets the list of all portfolios for a user.
   *
   * @param user that is attempting to request it's portfolio.
   * @return the list of portfolios for the requested user.
   */
  List<String> getPortfoliosForUser(User user);

  /**
   * Save the user stock to their portfolio.
   *
   * @param user          that is attempting to save it's stock to their portfolio.
   * @param portfolioUUID the unique ID of the portfolio.
   * @param ticker        the stock user wants to add to their portfolio.
   * @param noOfShares    the number of shares for the given ticker.
   * @return true if the stock was saved successfully, false, otherwise.
   */
  boolean saveStock(User user, String portfolioUUID, String ticker, String noOfShares);

  /**
   * Generated a random UUID.
   *
   * @return a random generated UUID.
   */
  String generateUUID();

  /**
   * It validates portfolio UUID entered by the user to access their portfolio.
   *
   * @param portfolioUUID the unique ID of the portfolio.
   * @param user          that is requesting to load its portfolio.
   * @return true if it's a valid UUID, false, otherwise.
   */
  boolean validatePortfolioUUID(String portfolioUUID, User user);

  /**
   * It returns a list of stocks that are saved in a user portfolio.
   *
   * @param user          that is requesting to load its portfolio.
   * @param portfolioUUID the unique ID of the portfolio.
   * @return the list of stocks.
   */
  List<String> getPortfolioContents(User user, String portfolioUUID);

  /**
   * It returns a user based on a given username.
   *
   * @param username input received from the user.
   * @return an authenticated user.
   */
  User getUserFromUsername(String username);

  /**
   * It calculates total value of a portfolio on a certain date.
   *
   * @param certainDate   input received from the user on which they want to calculate total value.
   * @param user          that is requesting to determine total value.
   * @param portfolioUUID the unique ID of the portfolio.
   * @return map of expected contents of a portfolio with the list of how many are processed.
   */
  Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate,
                                                             User user, String portfolioUUID);

  /**
   * It validates if the filepath provided is correct and the contents of it are in right sequence.
   *
   * @param filePath input received from the user.
   * @return true if it's a valid path, false, otherwise.
   */
  boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath);

  /**
   * It saves a user provided portfolio file.
   *
   * @param filePath input received from the user.
   * @param user     that is requesting to save external provided portfolio file.
   * @return the name of the portfolio that was used to save it.
   */
  String saveExternalUserPortfolio(String filePath, User user);




}
