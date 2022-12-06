package stockhw7.portfolio;

import java.io.IOException;

/**
 * Portfolio holds a list of stocks each of different sizes.
 * the portfolio should be able to retrieve its own value.
 */
public interface Portfolio {
  /**
   * Saves this portfolio to a given predetermined file.
   *
   * @return a boolean weather the file was successfully saved
   * @throws IOException if the file cannot be found.
   */
  boolean save() throws IOException;

  /**
   * Loads a portfolio form a predetermined file using the given
   * portfolio filename.
   *
   * @param fileName the retrieval of the fileName
   * @return a boolean weather the file was successfully loaded
   * @throws IOException if the file cannot be found.
   */
  boolean load(String fileName) throws IOException;

  /**
   * takes in three ints in the format year month day and returns the value
   * of a stock at the given date.
   *
   * @param year  input a year greater than 1792
   * @param month input a month between 1-12
   * @param day   input a day date between 1-31
   * @return returns value of portfolio at given date
   * @throws IllegalArgumentException if dates are invalid
   */
  double examineValue(int year, int month, int day) throws IllegalArgumentException;

  /**
   * Prints this version of the portfolio.
   */
  void print();

  /**
   * Adds a new stock to the portfolio.
   *
   * @param stock is the given stock being added to the portfolio
   * @param date  the date the stock was sold
   * @return returns a portfolio with the new stock added
   */
  Portfolio addStock(Stock stock, String date);

  /**
   * Deletes the given stock form the portfolio.
   *
   * @param ticker the ticker of the given stock to be deleted
   * @param num    the number of stocks to be deleted
   * @return the new portfolio with the stock removed
   */
  Portfolio deleteStock(String ticker, int num, String date);

  /**
   * Gets stock information.
   *
   * @return All information about all relevant stocks.
   */
  Object getStocks();
}
