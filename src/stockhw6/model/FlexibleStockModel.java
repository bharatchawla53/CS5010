package stockhw6.model;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a FlexibleStockModel that defines the operations listed below while
 * supporting StockModel features with it.
 */
public interface FlexibleStockModel extends StockModel {

  /**
   * Buys some shares of some ticker on a date and adds it to a given portfolio belonging to a
   * user.
   *
   * @param user          The user who owns the portfolio.
   * @param portfolioUUID the portfolio UUID.
   * @param ticker        the ticker that the user wants to buys shares of.
   * @param noOfShares    the no of shares that user wants to buy.
   * @param date          the date that the user purchases these shares.
   * @return if the shares were successfully added to the portfolio return true else false.
   */
  boolean buyStockOnSpecificDate(User user, String portfolioUUID, String ticker,
                                 String noOfShares, String date, int commissionRate);

  /**
   * Sells some shares of some ticker on a date and adds it to a portfolio belonging to a user.
   *
   * @param user          user The user who owns the portfolio.
   * @param portfolioUUID portfolioUUID the portfolio UUID.
   * @param ticker        the ticker that the user wants to buys shares of.
   * @param noOfShares    noOfShares the no of shares that user wants to buy.
   * @param date          the date that the user purchases these shares.
   * @return if negative shares were inserted into the portfolio return true else false.
   */
  boolean sellStockOnSpecifiedDate(User user, String portfolioUUID, String ticker,
                                   String noOfShares, String date, int commissionRate);

  /**
   * Calculates the cost basis on a date for a given portfolio, given the user.
   *
   * @param user          the owner of the portfolio.
   * @param portfolioUUID the portfolio UUID of the portfolio.
   * @param date          the date on which the cost basis needs to be calculated on
   * @return the cost basis of the portfolio
   */
  double calculateCostBasis(User user, String portfolioUUID, String date);

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
   * Generates a performance graph given a start and end date, a portfolio, and a user.
   *
   * @param date1         the start date for the graph.
   * @param date2         the end date for the graph.
   * @param portfolioUuid the portfolio uuid.
   * @param user          the user owning the portfolio.
   * @return a list of lines that make up the graph as a whole.
   */
  List<String> getPortfolioPerformance(String date1, String date2, String portfolioUuid, User user);

  /**
   * Create a dollar cost averaged portfolio from start to finish.
   * @param user current user of the app.
   * @param portfolioUUID the portfolio uuid of the portfolio that belongs to the above user.
   * @param tickers the list of tickers/stocks the user wants to invest in.
   * @param startDate the date on which the user wishes to begin investing in the portfolio.
   * @param endDate the date on which the user wishes to end investing in the portfolio.
   * @param daySkip skip these many number of days before investing in a given ticker again.
   * @param capital the total amount available to the user to invest in the portfolio.
   * @param weightList the percentage of capital to assign to each ticker in the ticker list.
   * @param commissionRate the commission to apply for each transaction generated by the.
   * dollarcostaveraged plan.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction.
   * failed in the generated list of transactions from the plan.
   */
  boolean createPortfolioBasedOnPlan(User user, String portfolioUUID, List<String> tickers,
                                     String startDate, String endDate,
                                     int daySkip, int capital,
                                     List<Integer> weightList, int commissionRate);

  /**
   * Given an existing portfolio, update the portfolio to have a list of transactions.
   * added for a specific date.
   * @param user the user of the application.
   * @param portfolioUUID the uuid of the portfolio belonging to the user.
   * @param tickerList  the list of tickers to add buy transactions in the portfolio.
   * @param startDate the date on which each buy transaction is performed.
   * @param capital the total amount available to the user, to distribute to each ticker on each date.
   * @param weightList the percentage of the capital that needs to be distributed to each ticker in the ticker list.
   * @param commissionRate the commission rate associated with each buy transaction.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction.
   * failed in the generated list of transactions from the plan.
   */
  boolean UpdatePortfolioBasedOnInvestment(User user, String portfolioUUID, List<String> tickerList,
                                   String startDate, int capital,List<Integer> weightList, int commissionRate);


  /**
   * Given an existing portfolio, perform a single buy transaction and add it to the portfolio.
   * @param user the user using the app.
   * @param portfolioUUID the uuid of the portfolio belonging ot that user. Must exist.
   * @param ticker the ticker that the user wants to buy some amount of.
   * @param noOfShares the number of shares of the ticker that the suer wants to buy of.
   * @param date the date on which the user wants to buy some amount of the specified ticker.
   * @param commissionRate the commission rate associated with the buy transaction.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction.
   * failed in the generated list of transactions from the plan.
   */
  boolean addBuyStockToPortfolio(User user, String portfolioUUID, String ticker,
                                 String noOfShares, String date, int commissionRate);


  /**
   * Given the contents of the performance graph, convert it to a hashmap that maps a.
   * date to the value of portfolio in integer based on some scale.
   * @param date1 the date on which the user wants to begin analyzing the portfolio.
   * @param date2 the date on which the user wants to end analyzing the portfolio.
   * @param portfolioUUID the uuid of the portfolio belonging to the user.
   * @param user the user using the app.
   * @return a hashmap containing the list of dates to be rendered as a key
   * and the number of stars based on some scale as a value.
   */
  Map<String, Integer> getBarChartContents(String date1,String date2, String portfolioUUID, User user);


}
