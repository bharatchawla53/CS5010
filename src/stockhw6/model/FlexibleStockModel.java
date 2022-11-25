package stockhw6.model;

import java.util.List;

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
                                 String noOfShares, String date);

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
                                   String noOfShares, String date);

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

  boolean createPortfolioBasedOnPlan(User user, String portfolioUUID, List<String> tickers,
                                     String startDate, String endDate,
                                     int daySkip, int monthSkip, int yearSkip, int capital,
                                     List<Integer> weightList);

  boolean UpdatePortfolioBasedOnInvestment(User user, String portfolioUUID, List<String> tickerList,
                                   String startDate, int capital,List<Integer> weightList);
}
