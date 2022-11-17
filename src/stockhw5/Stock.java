package stockhw5;

import java.io.InputStreamReader;

import stockhw5.controller.StockController;
import stockhw5.controller.StockControllerImpl;
import stockhw5.model.IStockModelMaker;
import stockhw5.model.StockModelMaker;
import stockhw5.view.IStockViewMaker;
import stockhw5.view.StockViewMaker;

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
    IStockModelMaker model = new StockModelMaker();
    // create the view
    IStockViewMaker view = new StockViewMaker(System.out);
    // create the controller
    StockController controller = new StockControllerImpl(model, view,
            new InputStreamReader(System.in));



    // hand over the control of the app to the controller
    controller.start();

    System.out.println("Stock app ended");
  }
}
