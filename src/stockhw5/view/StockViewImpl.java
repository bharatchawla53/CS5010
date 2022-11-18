package stockhw5.view;

import java.util.ArrayList;
import java.util.List;

/**
 * The StockViewImpl class represents user portfolio's features user interface.
 */
public class StockViewImpl extends AbstractStockView {

  private final Appendable out;

  /**
   * Creates a constructor that initializes Appendable.
   *
   * @param out takes an appendable object.
   */
  public StockViewImpl(Appendable out) {
    super(out);
    this.out = out;
  }

  @Override
  public void getUserOptionsView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio with shares of one or more stock ? "
            + "Once done creating your portfolio, please enter \"DONE\" ");
    opItems.add("2. Would you like to examine the composition of a portfolio ?");
    opItems.add("3. Would you like to determine the total value of a portfolio on a "
            + "certain date ?");
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
  public void getTableViewBuilder(List<String> rows, List<String> columns) {
    CommandLineTable commandLineTable = new CommandLineTable();
    commandLineTable.setShowVerticalLines(true);
    commandLineTable.setHeaders(columns);

    for (String row : rows) {
      String[] splitRow = row.split(",");

      if (splitRow.length == 1) {
        commandLineTable.addRow(splitRow[0]);
      } else {
        commandLineTable.addRow(splitRow[0], splitRow[1], splitRow[2]);
      }
    }

    commandLineTable.print();
  }

}
