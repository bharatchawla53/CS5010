package stockhw5;

import java.io.InputStreamReader;

import stockhw4.controller.StockController;
import stockhw4.controller.StockControllerImpl;
import stockhw4.model.StockModel;
import stockhw4.model.StockModelImpl;
import stockhw4.view.StockView;
import stockhw4.view.StockViewImpl;

/**
 * Demonstrates a command-line-based Stock application.
 */
public class Stock {

  /**
   * Application initializer.
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    System.out.println("Stock app started");

    // create the model
    StockModel model = new StockModelImpl();
    // create the view
    StockView view = new StockViewImpl(System.out);
    // create the controller
    StockController controller = new StockControllerImpl(model, view,
            new InputStreamReader(System.in));
    // hand over the control of the app to the controller
    controller.start();

    System.out.println("Stock app ended");
  }
}
