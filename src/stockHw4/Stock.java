package stockHw4;



/**
 * Demonstrates a command-line-based Stock application.
 */
public class Stock {

  public static void main(String[] args) {
    System.out.println("Stock app started");

    // create the model
    StockModel model = new StockModelImpl();
    // create the view
    StockView view = new StockViewImpl();
    // create the controller
    StockController controller = new StockControllerImpl(model, view,
            System.in, System.out);
    // hand over the control of the app to the controller
    controller.start();

    System.out.println("Stock app ended");
  }
}
