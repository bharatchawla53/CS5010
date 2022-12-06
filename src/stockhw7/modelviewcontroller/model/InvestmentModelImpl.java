package stockhw7.modelviewcontroller.model;

import java.io.IOException;
import java.util.List;

import stockhw7.portfolio.Portfolio;
import stockhw7.portfolio.PortfolioImpl;
import stockhw7.portfolio.Stock;
import stockhw7.portfolio.StockImpl;

/**
 * This class implements a portfolio object and contains operations to manipulate, save, load,
 * and view the portfolio.
 */
public class InvestmentModelImpl implements InvestmentModel {

  private Portfolio portfolio;

  /**
   * Creates new investment model with empty portfolio ready to be populated.
   */
  public InvestmentModelImpl() {
    this.portfolio = new PortfolioImpl("");
  }

  @Override
  public void addStock(int shares, String name, String index, String date) {
    this.portfolio.addStock(new StockImpl(shares, name, index), "");
  }

  @Override
  public void deleteStock(String ticker, int num, String date) {
    this.portfolio.deleteStock(ticker, 0, "");
  }

  @Override
  public void createPortfolio(String name, double brokerageFee) {
    this.portfolio = new PortfolioImpl(name, (List<Stock>) this.portfolio.getStocks());
  }

  @Override
  public void savePortfolio() throws IOException {
    this.portfolio.save();
  }

  @Override
  public void loadPortfolio(String filename) throws IOException {
    this.portfolio.load(filename);
  }

  @Override
  public double getValuePortfolio(int year, int month, int day) {
    return this.portfolio.examineValue(year, month, day);
  }

  @Override
  public String printStocks() {
    return this.portfolio.toString();
  }
}
