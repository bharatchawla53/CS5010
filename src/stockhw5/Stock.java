package stockhw5;

import java.io.InputStreamReader;

import stockhw5.controller.StockController;
import stockhw5.controller.StockControllerImpl;
import stockhw5.model.FlexibleStockModelImpl;
import stockhw5.model.StockModel;
import stockhw5.view.StockView;
import stockhw5.view.StockViewImpl;

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
    StockModel model = new FlexibleStockModelImpl();
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
