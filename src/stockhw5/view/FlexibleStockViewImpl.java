package stockhw5.view;

import java.util.ArrayList;
import java.util.List;

public class FlexibleStockViewImpl extends AbstractStockView implements FlexibleStockView {

  private final Appendable out;

  /**
   * Creates a constructor that initializes Appendable.
   *
   * @param out takes an appendable object.
   */
  public FlexibleStockViewImpl(Appendable out) {
    super(out);
    this.out = out;
  }

  @Override
  public void getPortfolioTypeView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Which type of portfolio would you like to create: ");
    opItems.add("1. Inflexible ");
    opItems.add("2. Flexible ");

    print(formatOutput(opItems));
  }

  @Override
  public void getUserOptionsView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio with shares of one or more stock on "
            + "a specific date ? "
            + "Once done creating your portfolio, please enter \"DONE\" ");
    opItems.add("2. Would you like to sell a specific number of shares of a specific stock "
            + "on a specified date from a given portfolio ?");
    opItems.add("3. Would you like to examine the composition of a portfolio on a specific date ?");
    opItems.add("4. Would you like to determine the total value of a portfolio on a "
            + "certain date ?");
    opItems.add("5. Would you like to persist a portfolio so that it can be saved ?");
    opItems.add("6. Would you like to load an external portfolio?");
    opItems.add("7. Would you like to determine the cost basis of a portfolio by a specific date ?");
    opItems.add("8. Would you like to analyze your specific portfolio over time on a specified time range ?");
    opItems.add("9. Would you like to exit the application ?");
    opItems.add("Enter your option: ");

    print(formatOutput(opItems));
  }

  @Override
  public void getPortfolioCreatorView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's create your portfolio!");
    opItems.add("Enter your preferred ticker and no of shares you'd like "
            + "to invest in this ticker "
            + "(Expected Format: ABC123,number of shares,date): ");

    print(formatOutput(opItems));
  }

  @Override
  public void getFlexibleSellStockView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter your preferred ticker and no of shares you'd like "
            + "to sell on a specific date "
            + "(Expected Format: ABC123,number of shares,date): ");

    print(formatOutput(opItems));
  }

  @Override
  public void getFlexibleCompositionView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a date for which the portfolio composition will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): ");

    print(formatOutput(opItems));
  }

  @Override
  public void getCostBasisView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a date for which the portfolio cost basis will be calculated on "
            + "(Expected Date Format: yyyy-MM-dd): ");

    print(formatOutput(opItems));
  }

  @Override
  public void getFlexiblePerformanceView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a specific date range to analyze how your portfolio has performed over time:  "
            + "(Expected Date Format: (minDate-maxDate) (yyyy-MM-dd)): ");

    print(formatOutput(opItems));
  }
}
