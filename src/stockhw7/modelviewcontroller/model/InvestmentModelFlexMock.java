package stockhw7.modelviewcontroller.model;

import java.io.IOException;

/**
 * This is the mock of the investment model Flex
 * so that testing can resume without the controller or view.
 */
public class InvestmentModelFlexMock implements InvestmentModelFlex {

  private final StringBuilder log;
  private final String uniqueCode;


  /**
   * Creates a mock flex investment model.
   *
   * @param log        String representing a collection of each method called thus far.
   * @param uniqueCode String representing a unique identifier for proper controller testing.
   */
  public InvestmentModelFlexMock(StringBuilder log, String uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void addStock(int shares, String name, String index, String date) {
    log.append("ADD STOCK " + date + " ");
    log.append(name + " " + ((Integer.parseInt(uniqueCode)) + shares) + " ");
  }

  @Override
  public void deleteStock(String ticker, int num, String date) {
    log.append("DELETE STOCK " + date + " ");
    log.append(ticker + " " + (Integer.parseInt(uniqueCode) + num) + " ");
  }

  @Override
  public void createPortfolio(String name, double brokerageFee) {
    log.append("ADD PORT ");
    log.append(name + " " + brokerageFee + " " + (Integer.parseInt(uniqueCode)) + " ");
  }

  @Override
  public void savePortfolio() throws IOException {
    log.append("SAVE PORT ");
  }

  @Override
  public void loadPortfolio(String filename) throws IOException {
    log.append("LOAD PORT ");
    log.append(filename + " ");
  }

  @Override
  public double getValuePortfolio(int day, int month, int year) {
    log.append("VALUE STOCK " + year + " " + month + " " + day);
    return 999.999;
  }

  @Override
  public String printStocks() {
    log.append("PRINT STOCK ");
    return "MOCK LIST\n";
  }

  @Override
  public double getCostBasis(int year, int month, int day) {
    log.append("COST BASIS " + year + " " + month + " " + day);
    return 999.998;
  }

  @Override
  public double[] printGraph(int day1, int day2, int month1, int month2, int year1, int year2) {
    log.append("PRINT GRAPH " + year1 + " " + year2 + " " + month1 + " "
            + month2 + " " + day1 + " " + day2);
    if (year1 != year2) {
      return new double[year2 - year1 + 1];
    } else if (month1 != month2) {
      return new double[month2 - month1 + 1];
    }
    return new double[day2 - day1 + 1];
  }


  @Override
  public void dollarCostProcessing(String startDate, String endDate, int intervalDays,
                                   double investmentTotal, String[] stockList,
                                   double[] percentList) {
    log.append("DOLLAR COST " + intervalDays + " " + investmentTotal);
  }

}
