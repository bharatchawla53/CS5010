package stockhw7.resources;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Indices holds a list of all the needed stock indices.
 * this helps with retrieving a specific index once per
 * execution. only has one method to get index weather
 * in or not yet in the indices.
 */

public interface Indices {
  /**
   * Retrieves the index based on the given name. If the index
   * is not present in the list, it will retrieve it from the
   * API and add it to the list.
   *
   * @param name the name of the Index the client would like to retrieve
   * @return return the String value of the index with \n values
   * @throws IllegalArgumentException called when index tries to retrieve from api,
   *                                  but it does not work.
   */
  Index getIndex(String name) throws IllegalArgumentException;

  /**
   * Saves the indices to a file for data to persist.
   *
   * @throws IOException when the file cannot be saved.
   */
  void save() throws IOException;

  /**
   * Loads previously used data from txt file.
   *
   * @throws FileNotFoundException when the file name given can't be found.
   */
  void load() throws IOException;

  /**
   * updates a specified index with a given name.
   *
   * @param name the name of the index in the list to be updated
   */
  void updateIndex(String name);

  /**
   * updates all the stocks in the list to the current date.
   */
  void updateAll();

  /**
   * gets the time it will take to update.
   *
   * @return the time it will take to update
   */
  String timeToUpdate();

  /**
   * gets the value of a specific stock.
   *
   * @param name  the name of the stock
   * @param year  the year of the date
   * @param month the month of the date
   * @param day   the day of the date
   * @return the value of the given stock on the given date
   */
  double getValue(String name, int year, int month, int day);

}
