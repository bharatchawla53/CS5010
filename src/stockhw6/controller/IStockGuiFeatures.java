package stockhw6.controller;

import java.awt.event.ActionEvent;

public interface IStockGuiFeatures {
  void getLoginScreenView(ActionEvent evt);
  void getUserName(String userName);
  void saveNewUser(String userName, ActionEvent evt);
  void getUserOptionsView(ActionEvent evt);
  void processFlexibleOptionOne(String input, ActionEvent evt);
  void processFlexibleOptionTwo(String input, ActionEvent evt);
  void processFlexibleOptionThree(String input, ActionEvent evt);
  void processFlexibleOptionFour(String input, ActionEvent evt);
  void processFlexibleOptionEight(String date, String capital, String symbol, String weightage,
                                  String commissionFees, ActionEvent evt);

  void processFlexibleOptionNine(String capital, String symbol, String weightage,
                                 String date, String date2, String timeIntervalUnit,
                                 String timeFrequency, String commissionFees, ActionEvent evt);

  void processFlexibleOptionTen(String date1, String date2, ActionEvent evt);
  void getPortfolioIdInputForOptionTwo(String input);
  void getPortfolioIdInputForOptionThree(String input);
  void getPortfolioIdInputForOptionFour(String input);
  void getPortfolioIdInputForOptionFive(String input);
  void getFilePathForOptionSix(String input);
  void getPortfolioIdInputForOptionSeven(String input);
  void getPortfolioIdInputForOptionEight(String input);
  void getPortfolioIdInputForOptionTen(String input);
}
