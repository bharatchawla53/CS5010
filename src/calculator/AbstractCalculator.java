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
  private int secondOperand;
  private char operator;

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

      int firstOperand = Integer.parseInt(split[0]);
      secondOperand = Integer.parseInt(split[1]);
      operator = builder.charAt(operatorIndex);

      result = computeTwoOperands(firstOperand, secondOperand, operator);
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

  /**
   * It performs operation on builder for a valid input i.e. digits.
   *
   * @param argument                a valid digit to be appended to the string builder.
   * @param builder                 containing the sequence of inputs.
   * @param hasComputationPerformed boolean denoting if the computation has performed previously or
   *                                not to overwrite the builder and initiate a new sequence of
   *                                inputs.
   */
  protected void performValidOperandCharacterOperation(char argument, StringBuilder builder,
                                                       boolean hasComputationPerformed) {
    // override the string with fresh input value
    if ((hasComputationPerformed && !sbContainsOperators(builder.toString()))
            || (hasComputationPerformed && allowedArithmeticOperators(builder.toString().charAt(0))
            && !sbContainsOperators(builder.substring(1)))) {
      builder.delete(0, builder.length());
    }

    // build stringBuilder
    builder.append(argument);

    // determine which operand it belongs to
    if (!sbContainsOperators(builder.toString())) {
      // check if new value causes an overflow
      parseInt(builder.toString());
    } else {
      // split it at a Math operator and check if it causes an overflow
      parseInt(builder.toString().split(REGEX)[1]);
    }
  }

  /**
   * Checks if the last character in the sequence is an operator.
   *
   * @param builder containing the sequence of inputs.
   * @return true, if the last character is an operator, false otherwise.
   */
  protected boolean isLastCharAnOperator(StringBuilder builder) {
    return builder != null
            && allowedArithmeticOperators(builder.toString().charAt(builder.toString().length() - 1));
  }

  /**
   * Checks if the given string builder is empty or null.
   *
   * @param builder containing the sequence of inputs.
   * @return true, if the builder is empty, false, otherwise.
   */
  protected boolean isBuilderEmpty(StringBuilder builder) {
    return builder == null || builder.toString().equals("");
  }

  /**
   * It checks if builder contains an operator between two given operands before we allow them to
   * compute the results.
   *
   * @param builder contains the sequence of inputs.
   * @return true, if it contains a valid operator, false, otherwise.
   */
  protected boolean checkBuilderContainsOperator(StringBuilder builder) {
    return sbContainsOperators(builder.toString())
            && !allowedArithmeticOperators(builder.toString().charAt(0));
  }

  /**
   * If the argument is an operator, and the sequence is valid, it performs computation to evaluate
   * `just in time` and append the new operator once expression is evaluated.
   *
   * @param argument an operator to be appended after expression is evaluated.
   * @param builder  contains the sequence of inputs.
   * @return the computed result with the new operator.
   */
  protected String computeSequenceThusFar(char argument, StringBuilder builder) {
    String updatedResult = performArithmeticOperation(builder.toString());
    updatedResult = updatedResult + argument;
    return updatedResult;
  }

  /**
   * It clears calculator input sequence if argument `C` is provided.
   *
   * @param argument calculator input to clear the sequence.
   * @return true if argument matches, false, otherwise.
   */
  protected boolean clearCalculatorInputs(char argument) {
    return argument == 'C';
  }

  /**
   * Given the string builder it checks if the second operand is missing.
   *
   * @param builder builder containing the sequence of inputs captured thus far.
   * @return true if the second operand is missing, false, otherwise.
   */
  protected boolean isSecondOperandMissing(StringBuilder builder) {
    return !builder.toString().equals("")
            && allowedArithmeticOperators(builder.toString().charAt(builder.toString().length() - 1));
  }

  /**
   * Need to access this if we want to continue to evaluate expression if multiple `=` are given.
   *
   * @return the last operand used in previous computation.
   */
  protected int getLastOperand() {
    return this.secondOperand;
  }

  /**
   * Need to access this if we want to continue to evaluate expression if multiple `=` are given.
   *
   * @return the last operator used in previous computation.
   */
  protected char getLastOperator() {
    return this.operator;
  }
}
