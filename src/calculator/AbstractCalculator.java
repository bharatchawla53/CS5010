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

  protected static final String REGEX = "[+\\-*]";

  protected String inputString;
  protected int secondOperand;
  protected char operator;
  protected boolean hasComputationPerformed;

  /**
   * Constructs an AbstractCalculator constructor with inputString, second operand, operator, and
   * hasComputationPerformed.
   *
   * @param inputString             the inputs that has been entered thus far.
   * @param secondOperand           tracks the last operand used in computation before a new
   *                                operator or operand is given.
   * @param operator                tracks the last operator used in computation before a new
   *                                operator or operand is given.
   * @param hasComputationPerformed indicating if the computation has performed on given input
   *                                string or not.
   */
  public AbstractCalculator(String inputString, int secondOperand,
                            char operator, boolean hasComputationPerformed) {
    this.inputString = inputString;
    this.secondOperand = secondOperand;
    this.operator = operator;
    this.hasComputationPerformed = hasComputationPerformed;
  }

  @Override
  public Calculator input(char argument) throws IllegalArgumentException {
    // initialize with what has been already entered thus far
    StringBuilder builder =
            inputString != null
                    ? new StringBuilder(this.inputString)
                    : new StringBuilder();

    if (isValidOperandCharacter(argument)) {
      return processValidOperandCharacter(argument, builder);
    } else if (allowedArithmeticOperators(argument)) {
      return processValidOperator(argument, builder);
    } else if (clearCalculatorInputs(argument)) {
      return clearCalculatorState();
    } else if (argument == '=') {
      return processEqualCharacter(builder);
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 "
              + "and operators are +, - and *");
    }
  }

  @Override
  public String getResult() {
    return this.inputString;
  }

  protected abstract Calculator handleBuilderEmptyAndAllowedToBeginWithAnOperator(
          StringBuilder builder, char argument);

  protected abstract Calculator handleIfThereAreTwoConsecutiveOperators(StringBuilder builder,
                                                                        char argument);

  protected abstract Calculator handleIfSecondOperandMissing(StringBuilder builder);

  protected abstract Calculator handleIfBuilderDoesNotContainOperators(StringBuilder builder);

  protected abstract Calculator handleIfBuilderContainOperators(StringBuilder builder);

  protected abstract Calculator calculatorFactory(String inputString, int secondOperand,
                                                  char operator, boolean hasComputationPerformed);

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

  /**
   * Need to access this if we want to continue to evaluate expression if multiple `=` are given.
   *
   * @return the last state if there was any computation done.
   */
  protected boolean getHasComputationPerformed() {
    return this.hasComputationPerformed;
  }

  /**
   * It processes the valid operand character and updates the state of inputString stored thus far.
   *
   * @param argument valid digit that needs to be processed.
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @return the state of this calculator using factory pattern.
   */
  private Calculator processValidOperandCharacter(char argument, StringBuilder builder) {
    performValidOperandCharacterOperation(argument, builder, hasComputationPerformed);
    return calculatorFactory(builder.toString(), 0, '\0', false);
  }

  /**
   * It processes the valid operator character and updates the state of inputString stored thus
   * far.
   *
   * @param argument valid digit that needs to be processed.
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @return the state of this calculator using factory pattern.
   */
  private Calculator processValidOperator(char argument, StringBuilder builder) {
    if (isBuilderEmpty(builder)) {
      return handleBuilderEmptyAndAllowedToBeginWithAnOperator(builder, argument);
    } else if (isLastCharAnOperator(builder)) {
      return handleIfThereAreTwoConsecutiveOperators(builder, argument);
    } else if (checkBuilderContainsOperator(builder)) {
      return calculatorFactory(computeSequenceThusFar(argument, builder),
              getLastOperand(), getLastOperator(), true);
    } else {
      return calculatorFactory(builder.append(argument).toString(),
              0, '\0', false);
    }
  }

  /**
   * It clears the calculator input string stored thus far.
   *
   * @return the state of this calculator using factory pattern with cleared state.
   */
  private Calculator clearCalculatorState() {
    return calculatorFactory("", 0, '\0', false);
  }

  /**
   * It processes the '=' character and updates the state of inputString stored thus far and peforms
   * the computation only a valid sequence.
   *
   * @param builder builder a sequence of inputs appended in a StringBuilder.
   * @return the state of this calculator using factory pattern after the sequence has been
   *         processed.
   */
  private Calculator processEqualCharacter(StringBuilder builder) {
    if (isBuilderEmpty(builder)) {
      throw new IllegalArgumentException("A correct basic sequence of inputs is the first "
              + "operand, followed by the operator, followed by the second operand, "
              + "followed by \"=\"");
    } else if (!sbContainsOperators(builder.toString())) {
      return handleIfBuilderDoesNotContainOperators(builder);
    } else if (isSecondOperandMissing(builder)) {
      return handleIfSecondOperandMissing(builder);
    } else if (sbContainsOperators(builder.toString())) {
      // case where resulted computation resulted in a negative result
      if (allowedArithmeticOperators(builder.toString().charAt(0))
              && (!sbContainsOperators(builder.substring(1)))) {
        return handleIfBuilderDoesNotContainOperators(builder);
      }
      // case to commute value of arithmetic operation if sequence is valid
      return handleIfBuilderContainOperators(builder);
    } else {
      return calculatorFactory(this.inputString, this.secondOperand,
              this.operator, this.hasComputationPerformed);
    }
  }

  /**
   * Checks if operand characters are withing range [0-9].
   *
   * @param argument Single character to check if its a valid value.
   * @return true if the character is a digit, false, otherwise.
   */
  private boolean isValidOperandCharacter(char argument) {
    return Character.isDigit(argument);
  }

  /**
   * Checks if given operator is valid. Allowed operators are +,-,*.
   *
   * @param argument Single character to check if its a valid value.
   * @return true if the character is a valid arithmetic operator, false, otherwise.
   */
  private boolean allowedArithmeticOperators(char argument) {
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
  private int parseInt(String value) {
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
  private boolean sbContainsOperators(String builder) {
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
    boolean foundNegativeOperand2 = false;

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

    // check if there is another operator which denotes negative result from previous calculations.
    if (allowedArithmeticOperators(builder.charAt(operatorIndex + 1))) {
      String substring = builder.substring(0, operatorIndex + 1);
      String substring1 = builder.substring(operatorIndex + 2);

      builder = substring + substring1;
      foundNegativeOperand2 = true;
    }
    String[] split = builder.split(REGEX);

    if (split[0] != null && split[1] != null) {

      split[0] = foundNegativeOperand ? '-' + split[0] : split[0];
      split[1] = foundNegativeOperand2 ? '-' + split[1] : split[1];

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
  private void performValidOperandCharacterOperation(char argument, StringBuilder builder,
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
  private boolean isLastCharAnOperator(StringBuilder builder) {
    return builder != null
            && allowedArithmeticOperators(builder.toString()
            .charAt(builder.toString().length() - 1));
  }

  /**
   * Checks if the given string builder is empty or null.
   *
   * @param builder containing the sequence of inputs.
   * @return true, if the builder is empty, false, otherwise.
   */
  private boolean isBuilderEmpty(StringBuilder builder) {
    return builder == null || builder.toString().equals("");
  }

  /**
   * It checks if builder contains an operator between two given operands before we allow them to
   * compute the results.
   *
   * @param builder contains the sequence of inputs.
   * @return true, if it contains a valid operator, false, otherwise.
   */
  private boolean checkBuilderContainsOperator(StringBuilder builder) {
    if (allowedArithmeticOperators(builder.charAt(0))
            && sbContainsOperators(builder.substring(1))) {
      return true;
    } else if (!allowedArithmeticOperators(builder.charAt(0))
            && sbContainsOperators(builder.toString())) {
      return true;
    }
    return false;
  }

  /**
   * If the argument is an operator, and the sequence is valid, it performs computation to evaluate
   * `just in time` and append the new operator once expression is evaluated.
   *
   * @param argument an operator to be appended after expression is evaluated.
   * @param builder  contains the sequence of inputs.
   * @return the computed result with the new operator.
   */
  private String computeSequenceThusFar(char argument, StringBuilder builder) {
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
  private boolean clearCalculatorInputs(char argument) {
    return argument == 'C';
  }

  /**
   * Given the string builder it checks if the second operand is missing. It checks if the last
   * character is an operator
   *
   * @param builder builder containing the sequence of inputs captured thus far.
   * @return true if the second operand is missing, false, otherwise.
   */
  private boolean isSecondOperandMissing(StringBuilder builder) {
    return !builder.toString().equals("")
            && allowedArithmeticOperators(builder.charAt(builder.length() - 1));
  }

}
