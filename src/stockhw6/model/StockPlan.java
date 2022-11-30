package stockhw6.model;

/**
 * An interface to create plans.
 */
public interface StockPlan {


  /**
   * Creates a portfolio using some plan, with variables and rules being defined in the
   * implementation class.
   *
   * @param portfolioUUID  the uuid of the portfolio belonging to the user. This is randomly
   *                       generated and does not exist.
   * @param user           the user using the app.
   * @param commissionRate the commission rate that the user wants to apply to each transaction.
   * @return true if the transaction was successfully performed, false if atmost 1 transaction
   * failed in the generated list of transactions from the plan.
   */
  boolean createPortfolio(String portfolioUUID, User user, int commissionRate);


}
