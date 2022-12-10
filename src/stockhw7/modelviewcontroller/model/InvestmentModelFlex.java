package stockhw7.modelviewcontroller.model;

/**
 * Is an Investment Model that uses the flexible
 * portfolio to preform different tasks.
 */
public interface InvestmentModelFlex extends InvestmentModel {

  /**
   * gets the coast Basis of a portfolio including the broker fees
   * on sell.
   *
   * @param year  the year the coast Basis should end on
   * @param month the month the coast Basis should end on
   * @param day   the day the coast Basis should end on
   * @return the value of the Coast Basis result
   */
  double getCostBasis(int year, int month, int day);

  /**
   * prints a given graph from a given time period to another
   * given time period.
   *
   * @param day1   the start day
   * @param day2   the end day
   * @param month1 the start month
   * @param month2 the end month
   * @param year1  the start year
   * @param year2  the end year
   * @return returns a graph with the given values
   */
  double[] printGraph(int day1, int day2, int month1, int month2, int year1, int year2);

  void dollarCostProcessing(String startDate, String endDate, int intervalDays,
                            double investmentTotal, String[] stockList,
                            double[] percentList);

  void rebalancePortfolio(String date, double investmentTotal, double[] percentList);
}
