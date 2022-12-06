package stockhw7.resources;

import java.util.Calendar;

/**
 * Index is the individual index value of each stock element.
 * It should contain the string of the value of the index, and
 * the name of the string.
 */

public interface Index extends Comparable<Index> {

  /**
   * Simply retrieves the index value of the stock in string
   * format with \n values in the string.
   *
   * @return the value of the stock as a String
   */
  String getIndex();

  // might have to override compare to and hashmap
  // try to just use toString for now

  /**
   * updates the string index value.
   */
  void update();

  /**
   * gets the last updated date.
   *
   * @return returns the last updated date
   */
  Calendar getLastUpdate();

  /**
   * grabs the value of one stock on that day.
   *
   * @param year  the year of the day
   * @param month the given month
   * @param day   the given day
   * @return the value of the astock on that day
   * @throws IllegalArgumentException throws exceptions if the date given is not allowed
   */
  double getValue(int year, int month, int day) throws IllegalArgumentException;
}
