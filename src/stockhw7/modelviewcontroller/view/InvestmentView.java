package stockhw7.modelviewcontroller.view;

import java.io.IOException;

/**
 * This interface represents the user-interface portion of our MVC.
 * This interface will print statements provided by the controller.
 */
public interface InvestmentView {

  /**
   * Print given string to view.
   *
   * @param s String to print
   * @throws IOException In case of I/O error.
   */
  void print(String s) throws IOException;

  /**
   * Print initial menu to select and create/load a portfolio of a specific type.
   *
   * @throws IOException In case of I/O error.
   */
  void printMainMenu1() throws IOException;

  /**
   * Print first menu with possible selections for portfolio of that type.
   *
   * @throws IOException In case of I/O error.
   */
  void printStaticMenu1() throws IOException;

  /**
   * Print secondary menu for locked static portfolios.
   *
   * @throws IOException In case of I/O error.
   */
  void printStaticMenu2() throws IOException;

  /**
   * Print flexible portfolio menu. Only one is necessary based on specifications.
   *
   * @throws IOException In case of I/O error.
   */
  void printFlexMenu1() throws IOException;

  /**
   * Prints graph of portfolio performance over a time period.
   *
   * @param valueArray Double set to be graphed out.
   * @throws IOException In case of I/O Error.
   */
  void printGraph(int firstDay, int secondDay, int firstMonth, int secondMonth,
                  int firstYear, int secondYear, double[] valueArray) throws IOException;
}
