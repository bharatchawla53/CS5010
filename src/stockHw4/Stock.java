package stockHw4;

import java.io.InputStreamReader;

/**
 * Demonstrates a command-line-based Stock application.
 */
public class Stock {

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
