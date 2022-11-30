package stockhw6.controller;

import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

import stockhw6.model.IStockModelMaker;
import stockhw6.model.StockModelMaker;
import stockhw6.view.IStockGuiView;
import stockhw6.view.IStockViewMaker;
import stockhw6.view.JFrameViewImpl;
import stockhw6.view.StockViewMaker;

/**
 * The ModelViewPairerControllerImpl class represents as a pairer to initialize concrete controller
 * and view classes based on user input.
 */
public class ModelViewPairerControllerImpl implements StockController {

  private final Scanner scanner;

  public ModelViewPairerControllerImpl(Readable in) {
    this.scanner = new Scanner(in);
  }


  @Override
  public void start() {
    System.out.println("What kind of view would you like?");
    System.out.println("1. Text-Based view");
    System.out.println("2. Graphical User Interface view");
    System.out.println("Please enter your selection: ");

    boolean invalidInput = true;
    while (invalidInput) {
      try {
        String input = getUserInputView().toUpperCase(Locale.ROOT);

        if (input.equals(UserInputOptions.ONE.getInput())) {
          // text-based view
          invalidInput = false;
          initializeTextBasedModelViewPair();
        } else if (input.equals(UserInputOptions.TWO.getInput())) {
          // GUI
          invalidInput = false;
          initializeGUIModelViewPair();
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid user input Enter 1/2 : ");
      }
    }

  }

  /**
   * It initializes the text based controller and view classes.
   */
  private void initializeTextBasedModelViewPair() {
    // create the model
    IStockModelMaker model = new StockModelMaker();
    // create the view
    IStockViewMaker view = new StockViewMaker(System.out);
    // create the controller
    StockController controller = new StockControllerImpl(model, view,
            new InputStreamReader(System.in));

    // hand over the control of the app to the controller
    controller.start();
  }

  /**
   * It initializes the GUI based controller and view classes.
   */
  private void initializeGUIModelViewPair() {
    // create the model
    IStockModelMaker model = new StockModelMaker();
    // create the view
    IStockGuiView view = new JFrameViewImpl();
    // create the controller
    StockControllerGuiImpl controller = new StockControllerGuiImpl(model);
    controller.setView(view);
  }

  /**
   * Gets user input from scanner.
   *
   * @return user input.
   */
  private String getUserInputView() {
    return scanner.next();
  }
}
