package stockhw6;

import java.io.InputStreamReader;

import stockhw6.controller.ModelViewPairerControllerImpl;
import stockhw6.controller.StockController;

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

    StockController controller = new ModelViewPairerControllerImpl(
            new InputStreamReader(System.in));

    // hand over the control of the app to the controller
    controller.start();

    System.out.println("Stock app ended");
  }
}
