package stockhw7.portfolio;

/**
 * this Flex port folio interface contains methods that are
 * specific to a Flexible portfolio but not a general portfolio.
 */
public interface FlexPortfolio extends Portfolio {

  /**
   * get cost basis gets the total value of a portfolio
   * on a given day  after x amount of time has passed since
   * stocks were purchased.
   *
   * @param year  the year given
   * @param month the month given
   * @param day   the day given
   * @return the value of the coast basis
   */
  double getCostBasis(int year, int month, int day);

  /**
   * returns the values for a graph to display the cost improvement over time.
   *
   * @param day1   the start day
   * @param day2   the end day
   * @param month1 the start month
   * @param month2 the end month
   * @param year1  the start year
   * @param year2  the end year
   * @return returns a list of the information contained in the dates given
   */
  double[] printGraph(int day1, int day2, int month1, int month2, int year1, int year2);

  /**
   * gets the dollar coas avarage of the protfolio.
   * @param startDate the start day
   * @param endDate the end day
   * @param intervalDays the intervals
   * @param investmentTotal the investment total
   * @param stockList the stock list
   * @param percentList the percent list
   */
  void dollarCostProcessing(String startDate, String endDate, int intervalDays,
                            double investmentTotal, String[] stockList,
                            double[] percentList);

  /**
   * Rebalanced an existing portfolio.
   * @param date when to rebalanced to.
   * @param investmentTotal amount invested in a portfolio.
   * @param percentList weights for each stock in a portfolio.
   */
  Portfolio rebalancePortfolio(String date, double investmentTotal, double[] percentList);
}
