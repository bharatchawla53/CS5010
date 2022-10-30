package stockHw4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// displays cmd GUI and relevant texts, and validation is done here
// TODO see if we can dispatch using New User vs Existing User
public class StockViewImpl implements StockView {

  public StockViewImpl() {
  }

  private String formatOutput(List<String> outputStrings) {
    StringBuilder sb = new StringBuilder();
    sb.append("///");
    for (String e : outputStrings) {
      sb.append("\n");
      sb.append(e);
    }
    return sb.toString();
  }

  @Override
  public void getLoginScreenView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Welcome to the portfolio manager");
    opItems.add("Are you a existing user or would you like to create a new user ?");
    opItems.add("Enter Y/N : ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public String getUserInput(InputStream in) {
    Scanner scanner = new Scanner(in);
    return scanner.next();
  }

  @Override
  public void throwErrorMessage(String message) {
    System.out.println(message);
  }

  @Override
  public void getExistingUserView() {

  }

  @Override
  public void getErrorMessageView(String message) {
    System.out.println(message);
  }

  @Override
  public void getNewUserView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Get started with your portfolio account ");
    opItems.add("Please enter an username and can't be longer than 8 characters: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getUsernameFromUser() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter your username:");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


  @Override
  public void getRepeatUsername() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Looks like this user name does not exist, please enter an existing user name:");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getPortfolioCreatorView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's create your portfolio!");
    opItems.add("Enter your preferred ticker and no of shares you'd like " +
            "to invest in this ticker" +
            "(Expected Format: ABC123,number of shares):");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


  @Override
  public void getViewBuilder(List<String> values) {
    String displayString = formatOutput(values);
    System.out.println(displayString);
  }


  @Override
  public void getDisplayPortfolioHeader() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Here are your Portfolios:");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);


  }

  @Override
  public void getDisplayPortfolioInput() {
    List<String> opItems = new ArrayList<>();

    opItems.add("Please enter a Portfolio ID from the above:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplayTotalPortfolioValue() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a date for which the portfolio value will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): ");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void terminateView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Are you sure you want to exit the application ?");
    opItems.add("Enter Y/N : ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplaySuccessfulTickerShareDump() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Ticker and Number of Shares added to portfolio! Enter DONE to exit " +
            "Portfolio Creation or Enter another valid stock to continue");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplayFinishedDumpingPortfolio() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Your portfolio has been created!");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getTableViewBuilder(List<String> rows, List<String> columns) {
    CommandLineTable commandLineTable = new CommandLineTable();
    commandLineTable.setShowVerticalLines(true);
    commandLineTable.setHeaders(columns);

    for(String row : rows) {
      String[] splitRow = row.split(" ");

      if (splitRow.length == 1) {
        commandLineTable.addRow(splitRow[0]);
      } else {
        commandLineTable.addRow(splitRow[0], splitRow[1], splitRow[2]);
      }
    }

    commandLineTable.print();
  }


  @Override
  public void getExistingUserOptions() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio with shares of one or more stock ? " +
            "Once done creating your portfolio, please enter \"DONE\" ");
    opItems.add("2. Would you like to examine the composition of a portfolio ?");
    opItems.add("3. Would you like to determine the total value of a portfolio on a certain date ?");
    opItems.add("4. Would you like to persist a portfolio so that it can be saved ?");
    opItems.add("5. Would you like to load an external portfolio?");
    opItems.add("6. Would you like to exit the application ?");

    opItems.add("Enter your option: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


  @Override

  public void getDisplayPortfolioFilePathInput() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter the Portfolio UUID of the Portfolio you wish to serialize:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplayPortfolioFilePathHeader() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Here are your Portfolio's!");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getNewUserOptions() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio ?");
    opItems.add("Enter your option: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplaySavePortfolioFromUser()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's save an external portfolio!");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplaySavePortfolioFilePathInput()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter the filepath of the external portfolio:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplaySuccessfullPortfolioSave(String pUUID)
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("The external portfolio file has been saved successfully.You can find it at "+pUUID);
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
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
