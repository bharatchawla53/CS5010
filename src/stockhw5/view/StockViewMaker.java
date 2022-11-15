package stockhw5.view;

import java.util.List;

public class StockViewMaker {
  private final StockView inflexibleView;
  private final FlexibleStockView flexibleView;

  public StockViewMaker(Appendable out) {
    this.inflexibleView = new StockViewImpl(out);
    this.flexibleView = new FlexibleStockViewImpl(out);
  }

  public void getLoginScreenView() {
    inflexibleView.getLoginScreenView();
  }

  public void getBuilderView(List<String> values) {
    inflexibleView.getBuilderView(values);
  }

  public void getUsernameInputView() {
    inflexibleView.getUsernameInputView();
  }

  public void terminateView() {
    inflexibleView.terminateView();
  }

  public void getInflexibleUserOptionsView() {
    inflexibleView.getUserOptionsView();
  }

  public void getInflexiblePortfolioCreatorView() {
    inflexibleView.getPortfolioCreatorView();
  }

  public void getInflexibleTableViewBuilder(List<String> rows, List<String> cols) {
    inflexibleView.getTableViewBuilder(rows, cols);
  }

  public void getFlexibleTableViewBuilder(List<String> rows, List<String> cols) {
    flexibleView.getTableViewBuilder(rows, cols);
  }

  public void getTotalPortfolioValueView() {
    inflexibleView.getTotalPortfolioValueView();
  }

  public void getProgressBarView(int i) {
    inflexibleView.getProgressBarView(i);
  }

  public void getPortfolioFilePathHeaderView() {
    inflexibleView.getPortfolioFilePathHeaderView();
  }

  public void getPortfolioFilePathInputView() {
    inflexibleView.getPortfolioFilePathInputView();
  }

  public void getSavePortfolioFromUserView() {
    inflexibleView.getSavePortfolioFromUserView();
  }

  public void getSavePortfolioFilePathInputView() {
    inflexibleView.getSavePortfolioFilePathInputView();
  }

  public void getNewUserView() {
    inflexibleView.getNewUserView();
  }

  public void getPortfolioHeaderView() {
    inflexibleView.getPortfolioHeaderView();
  }

  public void getPortfolioIdInputView() {
    inflexibleView.getPortfolioIdInputView();
  }

  public void getPortfolioTypeView() {
    flexibleView.getPortfolioTypeView();
  }

  public void getFlexibleUserOptionsView() {
    flexibleView.getUserOptionsView();
  }

  public void getFlexiblePortfolioCreatorView() {
    flexibleView.getPortfolioCreatorView();
  }

  public void getFlexibleSellStockView() {
    flexibleView.getFlexibleSellStockView();
  }

  public void getFlexibleCompositionView() {
    flexibleView.getFlexibleCompositionView();
  }

  public void getCostBasisView() {
    flexibleView.getCostBasisView();
  }

  public void getPortfolioPerformanceView() {
    flexibleView.getFlexiblePerformanceView();
  }
}
