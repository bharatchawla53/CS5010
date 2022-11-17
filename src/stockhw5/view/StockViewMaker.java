package stockhw5.view;

import java.util.List;

public class StockViewMaker implements IStockViewMaker{
  private final StockView inflexibleView;
  private final FlexibleStockView flexibleView;

  public StockViewMaker(Appendable out) {
    this.inflexibleView = new StockViewImpl(out);
    this.flexibleView = new FlexibleStockViewImpl(out);
  }

  @Override
  public void getLoginScreenView() {
    inflexibleView.getLoginScreenView();
  }

  @Override
  public void getBuilderView(List<String> values) {
    inflexibleView.getBuilderView(values);
  }

  @Override
  public void getUsernameInputView() {
    inflexibleView.getUsernameInputView();
  }

  @Override
  public void terminateView() {
    inflexibleView.terminateView();
  }

  @Override
  public void getInflexibleUserOptionsView() {
    inflexibleView.getUserOptionsView();
  }

  @Override
  public void getInflexiblePortfolioCreatorView() {
    inflexibleView.getPortfolioCreatorView();
  }

  @Override
  public void getInflexibleTableViewBuilder(List<String> rows, List<String> cols) {
    inflexibleView.getTableViewBuilder(rows, cols);
  }

  @Override
  public void getFlexibleTableViewBuilder(List<String> rows, List<String> cols) {
    flexibleView.getTableViewBuilder(rows, cols);
  }

  @Override
  public void getTotalPortfolioValueView() {
    inflexibleView.getTotalPortfolioValueView();
  }

  @Override
  public void getProgressBarView(int i) {
    inflexibleView.getProgressBarView(i);
  }

  @Override
  public void getPortfolioFilePathHeaderView() {
    inflexibleView.getPortfolioFilePathHeaderView();
  }

  @Override
  public void getPortfolioFilePathInputView() {
    inflexibleView.getPortfolioFilePathInputView();
  }

  @Override
  public void getSavePortfolioFromUserView() {
    inflexibleView.getSavePortfolioFromUserView();
  }

  @Override
  public void getSavePortfolioFilePathInputView() {
    inflexibleView.getSavePortfolioFilePathInputView();
  }

  @Override
  public void getNewUserView() {
    inflexibleView.getNewUserView();
  }

  @Override
  public void getPortfolioHeaderView() {
    inflexibleView.getPortfolioHeaderView();
  }

  @Override
  public void getPortfolioIdInputView() {
    inflexibleView.getPortfolioIdInputView();
  }

  @Override
  public void getPortfolioTypeView() {
    flexibleView.getPortfolioTypeView();
  }

  @Override
  public void getFlexibleUserOptionsView() {
    flexibleView.getUserOptionsView();
  }

  @Override
  public void getFlexiblePortfolioCreatorView() {
    flexibleView.getPortfolioCreatorView();
  }

  @Override
  public void getFlexibleSellStockView() {
    flexibleView.getFlexibleSellStockView();
  }

  @Override
  public void getFlexibleCompositionView() {
    flexibleView.getFlexibleCompositionView();
  }

  @Override
  public void getCostBasisView() {
    flexibleView.getCostBasisView();
  }

  @Override
  public void getPortfolioPerformanceView() {
    flexibleView.getFlexiblePerformanceView();
  }
}
