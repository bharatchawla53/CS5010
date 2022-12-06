package stockhw7.portfolio;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * StockImpl is the implementation of the stock interface
 * it holds the shares name index and string index for info
 * on the stock.
 */
public class StockImpl implements Stock {

  private final int shares;
  private final String name;
  //separated by new line character
  private final String[][] index;

  private final String stringIndex;

  /**
   * Makes a new stock and initializes the 2d array.
   *
   * @param shares integer representing number of shares.
   * @param index  the index of all the stock information.
   * @param name   string representing ticker number of stock.
   */

  public StockImpl(int shares, String name, String index) {
    if (shares < 0) {
      throw new IllegalArgumentException("Shares must be a positive value");
    }
    this.shares = shares;
    this.name = name;
    this.stringIndex = index;

    String[] newline = index.split("\n");
    String[][] result = new String[newline.length][6];
    if (index.length() > 0) {
      for (int i = 0; i < newline.length; i++) {
        System.arraycopy(newline[i].split(","), 0, result[i], 0, 6);
      }
    }


    this.index = result;
  }

  @Override
  public String print() {
    return shares + ", " + name + "\n";
  }

  @Override
  public double examineValue(int year, int month, int day) throws IllegalArgumentException {

    //    String[] commaResult = index[1].split(",");
    String[] firstDate = index[1][0].split("-");
    Calendar given;
    int iVal = -1;

    for (int i = 1; i < index.length; i++) {
      String[] date = index[i][0].split("-");
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

    double output = (Math.round(shares * ((Double.parseDouble(index[iVal][4]) * 100))));
    return output / 100;
  }

  @Override

  public String toString() {
    return shares + ", " + name + "\n" + stringIndex;
  }

  @Override
  public String getTicker() {
    return name;
  }

  @Override
  public int getShares() {
    return this.shares;
  }

  @Override
  public String getIndex() {
    return stringIndex;
  }

}

