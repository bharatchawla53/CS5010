package stockhw7.modelviewcontroller.controller;

/**
 * This is the interface of the GUI Controller.
 * It should contain methods of run/go and other
 * controller methods.
 */
public interface GUIController {
  /**
   * this method begins the controller and view in an ongoing
   * loop to begin the program.
   */
  void run();

  /**
   * processes a given input and determines
   * if the input is a valid one and delegates to
   * the proper workflow.
   *
   * @param input the given string input (11/20/2022 or GOOG)
   * @return the string of the status or an error;
   */
  String processInput(String input);
}
