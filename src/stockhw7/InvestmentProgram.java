package stockhw7;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import stockhw7.modelviewcontroller.controller.GUIController;
import stockhw7.modelviewcontroller.controller.GUIControllerImpl;
import stockhw7.modelviewcontroller.controller.InvestmentControllerImpl;
import stockhw7.modelviewcontroller.model.InvestmentModelFlexImpl;
import stockhw7.modelviewcontroller.model.InvestmentModelImpl;
import stockhw7.modelviewcontroller.view.IGUIView;
import stockhw7.modelviewcontroller.view.InvestmentGUIViewImpl;
import stockhw7.modelviewcontroller.view.InvestmentViewImpl;


/**
 * The investment program is used to create portfolios
 * stocks and view the values of them.
 */
public class InvestmentProgram {
  /**
   * The investment program main class runs the
   * investment program using modelViewControllers
   * to create stocks and portfolios.
   *
   * @param args console arguments
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(new InputStreamReader(System.in));
    System.out.println("Would you like to use GUI if so type Y then enter");
    String input = sc.next();

    if (input.equals("Y")) {
      InvestmentModelFlexImpl model = new InvestmentModelFlexImpl();
      IGUIView view = new InvestmentGUIViewImpl();
      GUIController controller = new GUIControllerImpl(model, view);
      controller.run();
    } else {
      try {
        new InvestmentControllerImpl(new InputStreamReader(System.in), System.out)
                .runProgram(new InvestmentModelImpl(), new InvestmentViewImpl(System.out));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
