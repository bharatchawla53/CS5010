package stockhw7.resources;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Index is meant to hold a string of the index
 * of a stock, for ease of calling the index.
 */
public class IndexImpl implements Index {

  private final String name;
  private final String[][] separated;
  Calendar lastUpdated;
  private String index;

  /**
   * Constructor that initializes the given name
   * and calls the api. for the index.
   *
   * @param name the name of the stock index.
   */
  public IndexImpl(String name) {
    this.name = name;
    this.index = AlphaVantageDemo.getStockValues(name);
    this.lastUpdated = new GregorianCalendar();
    this.separated = setSeparated(this.index);
  }

  /**
   * creates the index with a given name and a given
   * index already grabbed.
   *
   * @param name  the name of the index.
   * @param index the values of the index.
   */
  public IndexImpl(String name, String index) {
    this.name = name;
    this.index = index;
    this.separated = setSeparated(this.index);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public String getIndex() {
    return index;
  }

  @Override
  public void update() {
    this.index = AlphaVantageDemo.getStockValues(name);
    this.lastUpdated = new GregorianCalendar();
  }

  @Override
  public Calendar getLastUpdate() {
    return lastUpdated;
  }

  @Override
  public double getValue(int year, int month, int day) throws IllegalArgumentException {
    String[] firstDate = separated[1][0].split("-");
    Calendar given;
    int iVal = -1;

    for (int i = 1; i < separated.length; i++) {
      String[] date = separated[i][0].split("-");
      try {
        int test = Integer.valueOf(date[2]);
      } catch (ArrayIndexOutOfBoundsException e) {
        int stop = 0;
      }
      if (year == Integer.valueOf(date[0]) && month == Integer.valueOf(date[1])
              && day == Integer.valueOf(date[2])) {
        iVal = i;
        break;
      }
    }


    //must adjust values so that it starts with 0 can possibly change so inputs have to be correct
    given = new GregorianCalendar(year, month - 1, day - 1,
            0, 0, 0);

    //set the date as the first date in the list.
    Calendar startDate = new GregorianCalendar(Integer.valueOf(firstDate[0]),
            Integer.valueOf(firstDate[1]) - 1, Integer.valueOf(firstDate[2]) - 1,
            0, 0, 0);

    if (given.get(Calendar.YEAR) < 1792 || given.compareTo(startDate) > 0
            || given.get(Calendar.DAY_OF_WEEK) > 5) {
      throw new IllegalArgumentException("invalid date input: the year given is either "
              + "too large or too small");
    }

    if (iVal == -1) {
      throw new IllegalArgumentException("invalid date input");
    }

    double output = (Math.round((Double.parseDouble(separated[iVal][2])
            + Double.parseDouble(separated[iVal][3])) / 2) * 100);
    return output / 100;
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public int compareTo(Index o) {
    return this.toString().compareTo(index);
  }

  private String[][] setSeparated(String index) {
    String[] newline = index.split("\n");
    String[][] result = new String[newline.length][6];
    if (index.length() > 0) {
      for (int i = 0; i < newline.length; i++) {
        System.arraycopy(newline[i].split(","), 0, result[i], 0, 6);
      }
    }
    return result;
  }
}
