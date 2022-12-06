package stockhw7.modelviewcontroller.controller;

import java.io.IOException;

import stockhw7.modelviewcontroller.model.InvestmentModel;
import stockhw7.modelviewcontroller.view.InvestmentView;


/**
 * This interface represents a controller that guides our MVC program.
 * All actions by this program act on objects in either the view or model classes.
 */
public interface InvestmentController {

  /**
   * Runs program.
   *
   * @param model portfolio model being utilized for this program run.
   * @param view  external interface that is printing readable statements to user.
   * @throws IOException given I/O errors.
   */
  void runProgram(InvestmentModel model, InvestmentView view) throws IOException;
}
