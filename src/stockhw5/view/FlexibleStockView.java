package stockhw5.view;

/**
 * This interface represents a FlexibleStockView that defines the view operations for flexible
 * portfolio listed below for user interface.
 */
public interface FlexibleStockView extends StockView {

  /**
   * Method for loading portfolio types view.
   */
  void getPortfolioTypeView();

  /**
   * Method for loading sell portfolio view.
   */
  void getFlexibleSellStockView();

  /**
   * Method for loading flexible portfolio composition view.
   */
  void getFlexibleCompositionView();

  /**
   * Method for loading portfolio cost basis view.
   */
  void getCostBasisView();

  /**
   * Method for loading portfolio performance view.
   */
  void getFlexiblePerformanceView();
}
