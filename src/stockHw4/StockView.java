package stockHw4;

import java.io.InputStream;
import java.util.List;

/**
 * This interface represents a StockView that defines the view operations listed below for user interface.
 */

public interface StockView {

  void getLoginScreenView();

  void getUsernameInputView();

  void getPortfolioIdInputView();

  void getPortfolioFilePathHeaderView();

  void getPortfolioFilePathInputView();

  void getBuilderView(List<String> values);

  String getUserInputView(InputStream in);

  void getSavePortfolioFromUserView();

  void getSavePortfolioFilePathInputView();

  void getProgressBarView(int index);

  void getNewUserView();

  void getUserOptionsView();

  void getPortfolioCreatorView();

  void getPortfolioHeaderView();

  void getTotalPortfolioValueView();

  void terminateView();

  void getTableViewBuilder(List<String> rows, List<String> columns);
}
