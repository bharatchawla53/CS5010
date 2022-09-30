package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract base class for implementations of {@link Calculator}. This class contains all the method
 * definitions that are common to the concrete implementations of the {@link Calculator} interface.
 * A new implementation of the interface has the option of extending this class, or directly
 * implementing all the methods.
 */

public abstract class AbstractCalculator implements Calculator {

  protected final static String REGEX = "[+\\-*]";

  /**
   * Checks if operand characters are withing range [0-9].
   *
   * @param argument Single character to check if its a valid value.
   * @return true if the character is a digit, false, otherwise.
   */
  protected boolean isValidOperandCharacter(char argument) {
    return Character.isDigit(argument);
  }

  /**
   * Checks if given operator is valid. Allowed operators are +,-,*.
   *
   * @param argument Single character to check if its a valid value.
   * @return true if the character is a valid arithmetic operator, false, otherwise.
   */
  protected boolean allowedArithmeticOperators(char argument) {
    switch (argument) {
      case '+':
      case '-':
      case '*':
        return true;
      default:
        return false;
    }
  }

  /**
   * Parses the string argument as an integer value.
   *
   * @param value String containing the int representation to be parsed.
   * @return the integer value of parsed string
   * @throws IllegalArgumentException - if the parsed int value overflows.
   */
  protected int parseInt(String value) {
    if (Long.parseLong(value) > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("A valid input causes an operand to integer overflow.");
    }
    return Integer.parseInt(value);
  }

  /**
   * Checks if the given string has any of the allowed arithmetic operators.
   *
   * @param builder a string is a sequence with an possible operator in it.
   * @return true if an operator is found, false, otherwise.
   */
  protected boolean sbContainsOperators(String builder) {
    return builder.contains("+")
            || builder.contains("-")
            || builder.contains("*");
  }

  /**
   * Takes in a valid string sequence and splits two operands by their operator to parse them to
   * individual integer values.
   *
   * @param builder a string which needs to be computed.
   * @return the computed result as a string.
   */
  protected String performArithmeticOperation(String builder) {
    String result = null;
    boolean foundNegativeOperand = false;

    // check if first char is a negative number since we allow negative result
    if (allowedArithmeticOperators(builder.charAt(0))) {
      builder = builder.substring(1);
      foundNegativeOperand = true;
    }

    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(builder);
    int operatorIndex = 0;
    if (matcher.find()) {
      operatorIndex = matcher.start();
    }

    String[] split = builder.split(REGEX);

    if (split[0] != null && split[1] != null) {

      split[0] = foundNegativeOperand ? '-' + split[0] : split[0];

      result = computeTwoOperands(Integer.parseInt(split[0]),
              Integer.parseInt(split[1]),
              builder.charAt(operatorIndex));
    }
    return result;
  }

  /**
   * It compute two operands based on the operator provided. It also checks for arithmetic
   * overflows, and set the result to 0 in that case.
   *
   * @param firstOperand  first integer operand.
   * @param secondOperand second integer operand.
   * @param operator      operator to use to perform action on above two given operands.
   * @return the computed result as a string.
   */
  private String computeTwoOperands(int firstOperand, int secondOperand, char operator) {
    int result = 0;
    if (operator == '+') {
      try {
        result = Math.addExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
      }
    } else if (operator == '-') {
      try {
        result = Math.subtractExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
      }
    } else if (operator == '*') {
      try {
        result = Math.multiplyExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
      }
    }

    return String.valueOf(result);
  }
}
