package stockhw7.portfolio;

/**
 * Stock is a value that holds the information of a
 * given stock.
 */
public interface Stock {

  /**
   * prints this version of the stock.
   *
   * @return String of the stock printed to the console.
   */
  String print();

  /**
   * examines the value of a stock at a given date.
   *
   * @param year  input a year greater than 1792
   * @param month input a month between 1-12
   * @param day   input a day date between 1-31
   * @return returns the value at the given date
   * @throws IllegalArgumentException if dates are invalid
   */
  double examineValue(int year, int month, int day) throws IllegalArgumentException;

  /**
   * Gets the ticker the name of the stock.
   *
   * @return the name of the stock
   */
  String getTicker();

  /**
   * gets the number of shares in a stock.
   *
   * @return the number of stocks in this stock
   */
  int getShares();

  /**
   * gets the string value of the index.
   *
   * @return the string value of the index
   */
  String getIndex();
}
