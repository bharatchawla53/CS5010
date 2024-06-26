package stockhw6.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for implementing a plan called dollarCostAveraging Plan.
 */
public class DollarCostAveragingPlan implements StockPlan {

  private List<String> tickerList;
  private List<Integer> weightList;
  private int capital;
  private int capitalCeiling;
  private String date1;
  private String date2;
  private int daySkip;
  private String investmentTiming;
  private FlexibleStockModelImpl model;

  /**
   * Constructor for defining a dollar cost averaging plan.
   *
   * @param tickerList       The list of tickers to create a plan out of.
   * @param weightList       the percentage of the capital to distribute to each transaction
   *                         generated by. the plan.
   * @param capital          the total amount that the user wants to invest across all tickers and
   *                         durations.
   * @param date1           the date on which the user wishes to invest some amount into an
   *                         portfolio.
   * @param date2           the date the user wishes to stop investing some amount into an
   *                         portfolio.
   * @param daySkip          Every daySkip number of days, perform a transaction and add to the
   *                         portfolio.
   * @param investmentTiming dictates how we move from one date to another.
   * @param capitalCeiling   the amount of capital that one can invest in a portfolio in one plan.
   *                         iteration.
   */
  public DollarCostAveragingPlan(List<String> tickerList, List<Integer> weightList, int capital,
                                 String date1,
                                 String date2, int daySkip,
                                 String investmentTiming, int capitalCeiling) {
    this.tickerList = tickerList;
    this.weightList = weightList;
    this.capital = capital;
    this.capitalCeiling = capitalCeiling;
    this.date1 = date1;
    this.date2 = date2;
    this.daySkip = daySkip;

    this.investmentTiming = investmentTiming;
    this.capitalCeiling = capitalCeiling;

    this.model = new FlexibleStockModelImpl();
  }

  @Override
  public boolean createPortfolio(String portfolioUUID, User user, int commissionRate) {

    try {

      if (this.capital > this.capitalCeiling) {
        return false;
      }
      int wSum = 0;
      for (int val : weightList) {
        wSum += val;
      }
      if (wSum != 100) {
        return false;
      }


      if (investmentTiming.equals("quantum")) {
        LocalDate localDate2 = LocalDate.parse(date2);
        LocalDate localDate1 = LocalDate.parse(date1);

        LocalDate cursorDate;
        boolean isSuccessful;
        if (localDate2.equals("")) {
          localDate2 = LocalDate.now();
        }

        if (localDate2.isAfter(LocalDate.now())) {
          localDate2 = LocalDate.now();
        }
        cursorDate = localDate1;
        int qCount = 0;
        while (cursorDate.isBefore(localDate2)) {
          qCount += 1;
          cursorDate = cursorDate.plusDays(daySkip);

        }

        for (int i = 0; i < tickerList.size(); i++) {
          cursorDate = localDate1;
          while (cursorDate.isBefore(localDate2)) {
            double stockVal = 0;
            double numShares = 0;
            if (cursorDate.isAfter(LocalDate.now()) || cursorDate.equals(LocalDate.now())) {
              stockVal = model.getStockPrice(tickerList.get(i), "1",
                      model.getCurrentDateSkippingWeekends(LocalDate.now()).toString());
              numShares = (double) (capital * (weightList.get(i)) / (100.00 * qCount)) / stockVal;
              isSuccessful = model.buyStockOnSpecificDate(user, portfolioUUID, tickerList.get(i),
                      String.valueOf(numShares),
                      model.getCurrentDateSkippingWeekends(LocalDate.now()).toString(),
                      commissionRate);
            } else {
              stockVal = model.getStockPrice(tickerList.get(i), "1",
                      model.getCurrentDateSkippingWeekends(cursorDate).toString());
              numShares = (double) (capital * (weightList.get(i)) / (100.00 * qCount)) / stockVal;
              isSuccessful = model.buyStockOnSpecificDate(user, portfolioUUID, tickerList.get(i),
                      String.valueOf(numShares),
                      model.getCurrentDateSkippingWeekends(cursorDate).toString(),
                      commissionRate);
            }

            cursorDate = cursorDate.plusDays(daySkip);

            if (!isSuccessful) {
              return isSuccessful;
            }
          }

        }
        isSuccessful = true;
        return isSuccessful;
      }

      return false;
    } catch (Exception e) {
      return false;
    }
  }
}
