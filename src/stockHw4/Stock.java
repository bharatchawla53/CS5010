package stockHw4;

public class Stock {

  public static void main(String[] args) {
    System.out.println("Stock app started");

    StockModel model = new StockModelImpl();
    StockView view = new StockViewImpl();
    StockController controller = new StockControllerImpl(model, view);

    controller.start();

    System.out.println("Stock app ended");
  }

}
