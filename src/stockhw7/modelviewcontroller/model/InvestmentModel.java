package stockhw7.modelviewcontroller.model;

import java.io.IOException;

/**
 * This interface represents a model portfolio utilized by the controller in this MVC program.
 * The program contains a portfolio with a list of stocks that are part of the portfolio.
 * All operations at this level are of the portfolio.
 */


public interface InvestmentModel {

  /**
   * Add a given stock to an editable portfolio.
   *
   * @param shares - number of shares user chooses to add.
   * @param name   - ticker of Stock.
   * @param index  - Representation of all stock information in last 10 years.
   * @param date   - represents the date of a stock
   */
  void addStock(int shares, String name, String index, String date);

  /**
   * Deletes a stock within an editable portfolio.
   *
   * @param ticker - ticker of Stock.
   */
  void deleteStock(String ticker, int num, String date);

  /**
   * Creates a locked portfolio for editing purposes.
   *
   * @param name         user-selected name of portfolio.
   * @param brokerageFee user-selected brokerage fee
   */
  void createPortfolio(String name, double brokerageFee);

  /**
   * Save locked or loaded portfolio into file system.
   *
   * @throws IOException if file cannot be saved correctly due to I/O issue.
   */
  void savePortfolio() throws IOException;

  /**
   * Load portfolio from established file system.
   *
   * @param filename filename of portfolio to be loaded.
   * @throws IOException if load cannot occur due to I/O issue.
   */
  void loadPortfolio(String filename) throws IOException;

  /**
   * Get value of portfolio on certain date.
   *
   * @param day   user-selected date of value request.
   * @param month user-selected month of value request.
   * @param year  user-selected year of value request.
   * @return double referring to value of stock.
   */
  double getValuePortfolio(int day, int month, int year);

  /**
   * Prints portfolio as is with number of shares and stocks.
   *
   * @return a String representing the whole portfolio.
   */
  String printStocks();
}
