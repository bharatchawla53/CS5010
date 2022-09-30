package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple calculator takes straightforward inputs, and due to limited processing power it cannot
 * work with whole numbers longer than 32 bits.
 */
public class SimpleCalculator extends AbstractCalculator {

  // to keep track of individual input values
  // can help to support currentState??
  // TODO use stacks??? queue??
  private String inputString;

  //private Queue<Character> inputQueue = new LinkedList<>();

  // new approach to compute
  private int firstOperand;
  private int secondOperand;

  private final static String REGEX = "[+\\-*]";

  /**
   *
   */
  public SimpleCalculator() {
  }

  /**
   * @param inputString
   */
  private SimpleCalculator(String inputString) {
    this.inputString = inputString;
  }

  @Override
  public Calculator input(char argument) throws RuntimeException {
    // initialize with what has been already entered thus far
    StringBuilder builder =
            inputString != null
                    ? new StringBuilder(this.inputString)
                    : new StringBuilder();

    // check if its a valid digit before append it to sb
    if (isValidOperandCharacter(argument)) {
      // build stringBuilder
      builder.append(argument);

      // determine which operand it belongs to
      if (!sbContainsOperators(builder.toString())) {
        // check if new value causes an overflow which is done in parseInt()
        // TODO test overflow does not cause to lose previous value
        firstOperand = parseInt(builder.toString());
      } else {
        // split it at a Math operator
        String operandString = builder.toString().split(REGEX)[1];
        // check if new value causes an overflow which is done in parseInt()
        // TODO test overflow does not cause to lose previous value
        secondOperand = parseInt(operandString);
      }
      return new SimpleCalculator(builder.toString());
    } else if (isValidArithmeticOperator(argument)) { // check if its a valid operator before append it to sb
      // ex: +23+1 invalid sequence
      if (builder == null || builder.toString().equals("")) {
        throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, followed by the operator, followed by the second operand, followed by \"=\"");
      } else if (builder != null
              && allowedArithmeticOperators(builder.toString().charAt(builder.toString().length() - 1))) {
        // check if last index value is also an operator
        throw new IllegalArgumentException("Cannot have two consecutive operators.");
      } else if (sbContainsOperators(builder.toString())
        && !allowedArithmeticOperators(builder.toString().charAt(0))) {
        // check first if previously we appended any operator
        String updatedResult = performArithmeticOperation(builder.toString());
        updatedResult = updatedResult + argument;
        return new SimpleCalculator(updatedResult);
      } else {
        builder.append(argument);
        return new SimpleCalculator(builder.toString());
      }
    } else if (argument == 'C') { // Check for correct sequence of inputs
      return new SimpleCalculator(""); // clear calculator inputs
    } else if (argument == '=') {
      // case for missing inputs
      if (!builder.toString().equals("")
              && allowedArithmeticOperators(builder.toString().charAt(builder.toString().length() - 1))) {
        throw new IllegalArgumentException("The calculator does not infer any missing inputs. Please check your inputs.");
      } else if (allowedArithmeticOperators(builder.toString().charAt(0))
      && (!sbContainsOperators(builder.substring(1)))) {
        // case for negative results where no further operation is given
        return new SimpleCalculator(this.inputString); // previously calculated result
      } else if (!allowedArithmeticOperators(builder.toString().charAt(0))
              && (!sbContainsOperators(builder.substring(1)))) {
        // case for negative result where we have a new operation to perform
        return new SimpleCalculator(this.inputString);
      }
      else if (sbContainsOperators(builder.toString())) {
        // case to commute value of arithmetic operation if sequence is valid
        String updatedResult = performArithmeticOperation(builder.toString());
        return new SimpleCalculator(updatedResult);
      } /*else { // TODO test this if we need this code at all
        // case to handle multiple "="
        if (inputQueue.size() == 1) {

        }
      }*/
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 and operators are +, - and *");
    }

    // if it falls here, just return empty object
    return new SimpleCalculator();
  }

  @Override
  public String getResult() {
    return this.inputString;
    //TODO test and should show what has been entered so far
    // before entering any inputs, the result should be a blank string
  }

  // TODO check which exception is thrown if multi value digits are passed
  // Test for negative inputs to see how it reacts
  private boolean isValidOperandCharacter(char argument) {
      return Character.isDigit(argument);
  }

  private boolean isValidArithmeticOperator(char argument) {
    return allowedArithmeticOperators(argument);
  }

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

  private boolean sbContainsOperators(String builder) {
    return builder.contains("+")
            || builder.contains("-")
            || builder.contains("*");
  }

  private int parseInt(String value) {
    if (Long.parseLong(value) > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("A valid input causes an operand to integer overflow.");
    }
    return Integer.parseInt(value);
  }

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

  private String performArithmeticOperation(String builder) {
    String result = null;
    boolean foundNegativeOperand = false;
    // TODO test this if it works

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

}
