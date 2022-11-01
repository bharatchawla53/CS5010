package stockHw4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The StockViewImpl class represents user portfolio's features user interface.
 */
public class StockViewImpl implements StockView {

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
  public String getUserInputView(InputStream in) {
    Scanner scanner = new Scanner(in);
    return scanner.next();
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
      System.out.print("Calculating your portfolio worth: " + index + "% " + animationChars[index % 4] + "\r");
    } else {
      print("Processing: Done!");
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
  public void getUserOptionsView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio with shares of one or more stock ? " +
            "Once done creating your portfolio, please enter \"DONE\" ");
    opItems.add("2. Would you like to examine the composition of a portfolio ?");
    opItems.add("3. Would you like to determine the total value of a portfolio on a certain date ?");
    opItems.add("4. Would you like to persist a portfolio so that it can be saved ?");
    opItems.add("5. Would you like to load an external portfolio?");
    opItems.add("6. Would you like to exit the application ?");
    opItems.add("Enter your option: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioCreatorView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's create your portfolio!");
    opItems.add("Enter your preferred ticker and no of shares you'd like "
            + "to invest in this ticker "
            + "(Expected Format: ABC123,number of shares): ");


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

  @Override
  public void getTableViewBuilder(List<String> rows, List<String> columns) {
    CommandLineTable commandLineTable = new CommandLineTable();
    commandLineTable.setShowVerticalLines(true);
    commandLineTable.setHeaders(columns);

    for (String row : rows) {
      String[] splitRow = row.split(" ");

      if (splitRow.length == 1) {
        commandLineTable.addRow(splitRow[0]);
      } else {
        commandLineTable.addRow(splitRow[0], splitRow[1], splitRow[2]);
      }
    }

    commandLineTable.print();
  }

  private String formatOutput(List<String> outputStrings) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (String e : outputStrings) {
      sb.append("\n");
      sb.append(e);
    }
    return sb.toString();
  }

  private void print(String output) {
    System.out.println(output);
  }

  private class CommandLineTable {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private List<String> headers;
    private List<String[]> rows = new ArrayList<>();
    private boolean rightAlign;

    public CommandLineTable() {
      setShowVerticalLines(false);
    }

    public void setRightAlign(boolean rightAlign) {
      this.rightAlign = rightAlign;
    }

    public void setShowVerticalLines(boolean showVerticalLines) {
      verticalSep = showVerticalLines ? "|" : "";
      joinSep = showVerticalLines ? "+" : " ";
    }

    public void setHeaders(List<String> headers) {
      this.headers = headers;
    }

    public void addRow(String... cells) {
      rows.add(cells);
    }

    public void print() {
      int[] maxWidths = headers != null ?
              headers.stream().mapToInt(String::length).toArray() : null;

      for (String[] cells : rows) {
        if (maxWidths == null) {
          maxWidths = new int[cells.length];
        }
        if (cells.length != maxWidths.length) {
          throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
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

    private void printLine(int[] columnWidths) {
      for (int i = 0; i < columnWidths.length; i++) {
        String line = String.join("", Collections.nCopies(columnWidths[i] +
                verticalSep.length() + 1, HORIZONTAL_SEP));
        System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
      }
      System.out.println();
    }

    private void printRow(List<String> cells, int[] maxWidths) {
      for (int i = 0; i < cells.size(); i++) {
        String s = cells.get(i);
        String verStrTemp = i == cells.size() - 1 ? verticalSep : "";
        if (rightAlign) {
          System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
        } else {
          System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
        }
      }
      System.out.println();
    }
  }
}
