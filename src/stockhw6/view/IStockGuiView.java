package stockhw6.view;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import stockhw6.controller.IStockGuiFeatures;

public interface IStockGuiView {

  void getLoginScreenView();
  void getUserOptionsView(String userName);
  void getExistingUserView();
  void getNewUserView();
  void showErrorMessage(String message);
  void addGuiFeatures(IStockGuiFeatures features);
  void flexibleUserOptionOne();
  void flexibleUserOptionTwo();
  void flexibleUserOptionThree();
  void flexibleUserOptionFour();
  void flexibleUserOptionFiveAndSix(String message);
  void flexibleUserOptionEight();
  void flexibleUserOptionsNine();
  void flexibleUserOptionTen();
  void showSuccessMessage(String message, ActionEvent evt);
  void comboBoxBuilder(List<String> data, String title, String uOption);
  void portfolioValueTableView(List<String> data, List<String> columns, double totalPortfolioValueSum);
  void getExternalPortfolioFilePath();
  void showPerformanceGraph(Map<String, Integer> barChartContents);
}
