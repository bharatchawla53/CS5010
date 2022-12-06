package stockhw7.modelviewcontroller.model;

/**
 * This class represents a mock model. This class is used exclusively for testing purposes,
 * and will not be available to the user.
 */
public class InvestmentModelMock implements InvestmentModel {

  private final StringBuilder log;
  private final String uniqueCode;

  /**
   * Creates a mock investment model.
   *
   * @param log        String representing a collection of each method called thus far.
   * @param uniqueCode String representing a unique identifier for proper controller testing.
   */
  public InvestmentModelMock(StringBuilder log, String uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void addStock(int shares, String name, String index, String date) {
    log.append("ADD STOCK ");
    log.append(name + " " + ((Integer.parseInt(uniqueCode)) + shares) + " ");
  }

  @Override
  public void deleteStock(String ticker, int num, String date) {
    log.append("DELETE STOCK ");
    log.append(ticker + " " + (Integer.parseInt(uniqueCode)) + " ");
  }

  @Override
  public void createPortfolio(String name, double brokerageFee) {
    log.append("ADD PORT ");
    log.append(name + " " + (Integer.parseInt(uniqueCode)) + " ");
  }

  @Override
  public void savePortfolio() {
    log.append("SAVE PORT ");
    //log.append(filename + " ");
  }

  @Override
  public void loadPortfolio(String filename) {
    log.append("LOAD PORT ");
    log.append(filename + " ");
  }

  @Override
  public double getValuePortfolio(int day, int month, int year) {
    log.append("VALUE STOCK ");
    return 999.999;
  }

  @Override
  public String printStocks() {
    log.append("PRINT STOCK ");
    return "MOCK LIST\n";
  }

}
