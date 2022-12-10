package stockhw7.modelviewcontroller.view;

import java.io.IOException;

/**
 * This class represents the implementation of a view Object. This class will
 * function to display results and prompts to the user.
 */
public class InvestmentViewImpl implements InvestmentView {

  final Appendable out;

  /**
   * Creates view object that will print to Appendable Object.
   *
   * @param out Appendable object being used as the user console.
   */
  public InvestmentViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void print(String s) throws IOException {
    this.out.append(s);
  }

  @Override
  public void printMainMenu1() throws IOException {
    this.out.append("Please make a selection from the following choices, "
            + "or press 0 to quit:\n");
    this.out.append("1. Begin Creating Portfolio\n");
    this.out.append("2. Load Portfolio\n");
  }

  @Override
  public void printStaticMenu1() throws IOException {
    this.out.append("Please make a selection from the following choices, "
            + "or press 0 to quit:\n");
    this.out.append("1. View Value of Portfolio\n");
    this.out.append("2. Save Portfolio\n");
    this.out.append("3. Print portfolio\n");
    this.out.append("4. Purchase Stock\n");
    this.out.append("5. Sell Stock\n");
    this.out.append("6. Complete Creating Portfolio\n");
  }

  @Override
  public void printStaticMenu2() throws IOException {
    this.out.append("Please make a selection from the following choices, "
            + "or press 0 to quit:\n");
    this.out.append("1. View Value of Portfolio\n");
    this.out.append("2. Save Portfolio\n");
    this.out.append("3. Print portfolio\n");
  }

  @Override
  public void printFlexMenu1() throws IOException {
    this.out.append("Please make a selection from the following choices, "
            + "or press 0 to quit:\n");
    this.out.append("1. View Values of Portfolio\n");
    this.out.append("2. Save Portfolio\n");
    this.out.append("3. Print portfolio\n");
    this.out.append("4. Purchase Stock\n");
    this.out.append("5. Sell Stock\n");
    this.out.append("6. Dollar Cost-Average\n");
    this.out.append("7: Rebalanced Portfolio\n");
  }

  @Override
  public void printGraph(int firstDay, int secondDay, int firstMonth, int secondMonth,
                         int firstYear, int secondYear, double[] valueArray) throws IOException {
    //make date array and string version of it
    int[] dateArray = new int[valueArray.length];
    String[] dateStringArray = new String[valueArray.length];
    //print out title based on equality of dates
    if (firstYear != secondYear) {
      //special title header
      print("Performance of Portfolio from " + firstYear + " to " + secondYear + "\n");
      //regenerate date array
      int yearDiff = secondYear - firstYear + 1;
      int duplicateTracker = 1;
      while (yearDiff > 30) {
        yearDiff /= 2;
        duplicateTracker++;
      }
      //initialize double array to get values with
      dateArray = new int[yearDiff];
      //evenly divide and set array values
      for (int i = 0; i < yearDiff; i++) {
        dateStringArray[i] = Integer.toString(firstYear + (duplicateTracker - 1)
                + i * duplicateTracker);
      }
    }
    //do the same thing for months and a fixed pre-specified year
    else if (firstMonth != secondMonth) {
      print("Performance of Portfolio from " + firstMonth + "/" + firstYear
              + " to " + secondMonth + "/" + firstYear + "\n");
      int monthDiff = secondMonth - firstMonth + 1;
      //initialize double array to get values with
      dateArray = new int[monthDiff];
      //evenly divide and set array values
      String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
          "AUG", "SEP", "OCT", "NOV", "DEC"};
      for (int i = 0; i < monthDiff; i++) {
        dateArray[i] = firstMonth + i;
        dateStringArray[i] = monthNames[dateArray[i] - 1];
      }
    }
    //do the same thing for days and a fixed pre-specified year/month
    else {
      print("Performance of Portfolio from " + firstMonth + "/" + firstDay + "/" + firstYear
              + " to " + secondMonth + "/" + secondDay + "/" + firstYear + "\n");
      int dayDiff = secondDay - firstDay + 1;
      //evenly divide and set array values
      for (int i = 0; i < dayDiff; i++) {
        String dateString = Integer.toString(firstDay + i);
        if (dateString.length() == 1) {
          dateString = "0" + dateString;
        }
        dateStringArray[i] = dateString;
      }
    }
    //find minimum and maximum to figure out range we're dealing with
    double min = 100000000;
    double max = 0;
    int scale = 1;
    for (int i = 0; i < valueArray.length; ++i) {
      if (valueArray[i] > max) {
        max = valueArray[i];
      }
      if (valueArray[i] < min && valueArray[i] > 1.0) {
        min = valueArray[i];
      }
    }
    double minHolder = min;
    while (minHolder > 10) {
      scale *= 10;
      minHolder /= 10;
    }
    //TODO deal with relative scaling
    for (int i = 0; i < valueArray.length; ++i) {
      this.out.append(dateStringArray[i] + ":");
      int numStars = (int) valueArray[i] / scale;
      for (int j = 0; j < numStars; ++j) {
        this.out.append('*');
      }
      this.out.append('\n');
    }
    this.out.append("Scale: * = $" + scale + "\n");
  }
}
