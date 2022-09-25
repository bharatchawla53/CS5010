package calculator;

/**
 * This interface represents a single calculator that supports two operations: take user input and
 * returns current result of the calculator.
 */

public interface Calculator {

  /**
   * It takes a single character as its only argument.
   *
   * @param argument Single character to simulate button press functionality.
   * @return {@link Calculator} object as a result of processing the input.
   */
  Calculator input(char argument);

  /**
   * It does not take any arguments, but returns the current result of the calculator i.e. the
   * message that we would normally see on the screen.
   *
   * @return the current result of the calculator.
   */
  String getResult();
}
