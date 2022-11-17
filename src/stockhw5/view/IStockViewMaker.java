package stockhw5.view;

import java.util.List;

public interface IStockViewMaker {
  void getLoginScreenView();

  void getBuilderView(List<String> values);

  void getUsernameInputView();

  void terminateView();

  void getInflexibleUserOptionsView();

  void getInflexiblePortfolioCreatorView();

  void getInflexibleTableViewBuilder(List<String> rows, List<String> cols);

  void getFlexibleTableViewBuilder(List<String> rows, List<String> cols);

  void getTotalPortfolioValueView();

  void getProgressBarView(int i);

  void getPortfolioFilePathHeaderView();

  void getPortfolioFilePathInputView();

  void getSavePortfolioFromUserView();

  void getSavePortfolioFilePathInputView();

  void getNewUserView();

  void getPortfolioHeaderView();

  void getPortfolioIdInputView();

  void getPortfolioTypeView();

  void getFlexibleUserOptionsView();

  void getFlexiblePortfolioCreatorView();

  void getFlexibleSellStockView();

  void getFlexibleCompositionView();

  void getCostBasisView();

  void getPortfolioPerformanceView();
}
