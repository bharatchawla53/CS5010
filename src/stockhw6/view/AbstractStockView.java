package stockhw6.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for implementations of {@link StockView}. This class contains all the method
 * definitions that are common to the concrete implementations of the {@link StockView} interface. A
 * new implementation of the interface has the option of extending this class, or directly
 * implementing all the methods.
 */
public abstract class AbstractStockView implements StockView {

  private final Appendable out;

  /**
   * Creates a constructor that initializes Appendable.
   *
   * @param out takes an appendable object.
   */
  public AbstractStockView(Appendable out) {
    this.out = out;
  }

  @Override
  public void getLoginScreenView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Welcome to the portfolio manager");
    opItems.add("Are you a existing user or would you like to create a new user ?");
    opItems.add("Enter Y/N : ");

    print(formatOutput(opItems));
  }

  @Override
  public void getUsernameInputView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter your username: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioIdInputView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a Portfolio ID from the above: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioFilePathHeaderView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Here are your Portfolio's!");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioFilePathInputView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter the Portfolio UUID of the Portfolio you wish to serialize: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getBuilderView(List<String> values) {
    print(formatOutput(values));
  }

  @Override
  public void getSavePortfolioFromUserView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's save an external portfolio!");

    print(formatOutput(opItems));
  }

  @Override
  public void getSavePortfolioFilePathInputView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter the filepath of the external portfolio:");

    print(formatOutput(opItems));
  }

  @Override
  public void getProgressBarView(int index) {
    char[] animationChars = new char[]{'|', '/', '-', '\\'};

    if (index != 100) {
      try {
        out.append("Calculating your portfolio worth: "
                + index + "% " + animationChars[index % 4] + "\r");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        out.append("Processing: Done!");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void getNewUserView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Get started with your portfolio account ");
    opItems.add("Please enter an username and can't be longer than 8 characters: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioHeaderView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Here are your Portfolios:");

    print(formatOutput(opItems));
  }

  @Override
  public void getTotalPortfolioValueView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a date for which the portfolio value will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): ");

    print(formatOutput(opItems));
  }

  @Override
  public void terminateView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Are you sure you want to exit the application ?");
    opItems.add("Enter Y/N : ");

    print(formatOutput(opItems));
  }

  protected String formatOutput(List<String> outputStrings) {
    StringBuilder sb = new StringBuilder();
    for (String e : outputStrings) {
      sb.append("\n");
      sb.append(e);
    }
    return sb.toString();
  }

  /**
   * A helper method to print lines to command line.
   *
   * @param output string to print.
   */
  protected void print(String output) {
    try {
      this.out.append(String.format("%s\n", output));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The CommandLineTable class represents a table view builder for command line interface.
   */
  protected class CommandLineTable {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private List<String> headers;
    private List<String[]> rows = new ArrayList<>();
    private boolean rightAlign;

    /**
     * Creates an empty constructor.
     */
    public CommandLineTable() {
      setShowVerticalLines(false);
    }

    /**
     * Sets right align for table view.
     *
     * @param rightAlign true to set right align, false, otherwise.
     */
    public void setRightAlign(boolean rightAlign) {
      this.rightAlign = rightAlign;
    }

    /**
     * shows vertical lines for table view.
     *
     * @param showVerticalLines true to show vertical lines, false, otherwise.
     */
    public void setShowVerticalLines(boolean showVerticalLines) {
      verticalSep = showVerticalLines ? "|" : "";
      joinSep = showVerticalLines ? "+" : " ";
    }

    /**
     * Sets a list of headers for table view.
     *
     * @param headers takes a list of headers.
     */
    public void setHeaders(List<String> headers) {
      this.headers = headers;
    }

    /**
     * Adds rows for table view.
     *
     * @param cells rows needed for tables.
     */
    public void addRow(String... cells) {
      rows.add(cells);
    }

    /**
     * It prints the data in table view.
     */
    public void print() {
      int[] maxWidths = headers != null
              ? headers.stream().mapToInt(String::length).toArray() : null;

      for (String[] cells : rows) {
        if (maxWidths == null) {
          maxWidths = new int[cells.length];
        }
        if (cells.length != maxWidths.length) {
          throw new IllegalArgumentException("Number of row-cells and headers should "
                  + "be consistent");
        }
        for (int i = 0; i < cells.length; i++) {
          maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
        }
      }

      if (headers != null) {
        printLine(maxWidths);
        printRow(headers, maxWidths);
        printLine(maxWidths);
      }
      for (String[] cells : rows) {
        printRow(Arrays.asList(cells), maxWidths);
      }
      if (headers != null) {
        printLine(maxWidths);
      }
    }

    /**
     * It prints column for the given column widths.
     *
     * @param columnWidths an array of widths of the column.
     */
    private void printLine(int[] columnWidths) {
      for (int i = 0; i < columnWidths.length; i++) {
        String line = String.join("", Collections.nCopies(columnWidths[i]
                + verticalSep.length() + 1, HORIZONTAL_SEP));
        try {
          out.append(joinSep).append(line).append(i == columnWidths.length - 1 ? joinSep : "");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        out.append("\n");
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    /**
     * Given cells and max widths, it prints row for table view.
     *
     * @param cells     rows needed for tables.
     * @param maxWidths an array of widths of the rows.
     */
    private void printRow(List<String> cells, int[] maxWidths) {
      for (int i = 0; i < cells.size(); i++) {
        String s = cells.get(i);
        String verStrTemp = i == cells.size() - 1 ? verticalSep : "";
        if (rightAlign) {
          String sfmt = String.format("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
          try {
            out.append(sfmt);
          } catch (IOException e) {
            e.printStackTrace();
          }

        } else {
          String sfmt = String.format("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
          try {
            out.append(sfmt);
          } catch (IOException e) {
            e.printStackTrace();
          }

        }
      }
      try {
        out.append("\n");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
