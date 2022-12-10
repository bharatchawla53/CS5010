package stockhw7.modelviewcontroller.view;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * A Jpanel View Interface that implements methods
 * that the controller can call.
 */
public interface IGUIView {
  /**
   * make the view visible. it is called after
   * the view is created.
   */
  void makeVisible();

  /**
   * create a listener for different commands to.
   *
   * @param actionEvent thsi is the action listener.
   */
  void setButtonListener(ActionListener actionEvent);

  /**
   * Loads the load state for the view. should be
   * called by the controller.
   */
  void loadState();

  /**
   * Loads the new Flexible Portfolio creation state.
   * Should be called by the controller.
   */
  void newFlexibleState();

  /**
   * Loads the new Fractional Portfolio creation state
   * Should be called by the controller.
   */
  void newFractionalState();

  /**
   * Loads the flexible State when a flexible portfolio
   * is currently being manipulated.
   */
  void flexibleState();

  /**
   * This function will find the input of the submitted panel
   * and return it as a string.
   *
   * @param name the name of the panel that triggered the event
   * @return the String of inputs separated by space.
   */
  String findPanel(String name);

  /**
   * updates the log panel to show portfolio information.
   *
   * @param log the new displayed text.
   */
  void updateLog(String log);

  /**
   * gets the stock text for the controller to use.
   *
   * @return stock text
   */
  String getDCAPanelStocks();

  String getRebalanceStocks();

  /**
   * gets % text for the controller to use.
   *
   * @return % text
   */
  String getDCAPanelPercent();

  String getRebalancePanelPercent();

  /**
   * adds a given stock value to the list of stocks.
   *
   * @param stock the stock to be added
   */
  void addDcaStock(String stock);

  void addRebalanceStock(String stock, List<String> stocksList);

  /**
   * adds a given percentage value to the list of %.
   *
   * @param percent the given percent
   */
  void addPercent(String panel, Double percent);

  /**
   * clears the DCA inputs for percent and stocks.
   */
  void clearDCA();

  void clearRebalance();

  void newRebalanceState();
}
