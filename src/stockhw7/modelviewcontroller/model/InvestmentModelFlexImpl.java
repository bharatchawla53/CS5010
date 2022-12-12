package stockhw7.modelviewcontroller.model;

import java.io.IOException;

import stockhw7.portfolio.FlexPortfolio;
import stockhw7.portfolio.FlexPortfolioImpl;
import stockhw7.portfolio.StockImpl;
import stockhw7.resources.FileIO;

/**
 * The implementation of the Investment Model Flex interface
 * which implements the investment model.
 */
public class InvestmentModelFlexImpl implements InvestmentModelFlex {

  private FlexPortfolio portfolio;

  /**
   * the constructor initializes the portfolio.
   */

  public InvestmentModelFlexImpl() {
    this.portfolio = new FlexPortfolioImpl();
  }

  public InvestmentModelFlexImpl(String s, double brokerFee) {
    this.portfolio = new FlexPortfolioImpl(s, brokerFee);
  }

  @Override
  public void addStock(int shares, String name, String index, String date) {
    this.portfolio.addStock(new StockImpl(Double.parseDouble(String.valueOf(shares)),
            name, index), date);
  }

  @Override
  public void deleteStock(String ticker, int num, String date) {
    this.portfolio.deleteStock(ticker, num, date);
  }


  @Override
  public void createPortfolio(String name, double brokerageFee) {
    this.portfolio = new FlexPortfolioImpl(name, brokerageFee);
  }

  @Override
  public void savePortfolio() throws IOException {
    this.portfolio.save();
  }

  @Override
  public void loadPortfolio(String filename) throws IOException {
    this.portfolio = (FlexPortfolio) FileIO.loadFlexPortfolio(filename);
  }

  @Override
  public double getValuePortfolio(int year, int month, int day) {
    return this.portfolio.examineValue(year, month, day);
  }

  @Override
  public String printStocks() {
    return this.portfolio.toString();
  }

  @Override
  public double getCostBasis(int year, int month, int day) {
    return portfolio.getCostBasis(year, month, day);
  }

  @Override
  public double[] printGraph(int day1, int day2, int month1, int month2, int year1, int year2) {
    return portfolio.printGraph(day1, day2, month1, month2, year1, year2);
  }

  @Override
  public void dollarCostProcessing(String startDate, String endDate, int intervalDays,
                                   double investmentTotal, String[] stockList,
                                   double[] percentList) {
    portfolio.dollarCostProcessing(startDate, endDate, intervalDays, investmentTotal,
            stockList, percentList);
  }

  @Override
  public void rebalancePortfolio(String date, double investmentTotal, double[] percentList) {
    this.portfolio.rebalancePortfolio(date, investmentTotal, percentList);
  }

}
