package calculator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple calculator takes straightforward inputs, and due to limited processing power it cannot
 * work with whole numbers longer than 32 bits.
 */
public class SimpleCalculator extends AbstractCalculator {

  // to keep track of individual input values
  // can help to support currentState??
  // TODO use stacks??? queue??
  private StringBuilder sb = new StringBuilder();
  private Queue<Character> inputQueue = new LinkedList<>();

  // result to keep track of arithmetic expressions as they are provided to
  // us to support just in time functionality
  private int expressionResult = 0;

  @Override
  public Calculator input(char argument) throws RuntimeException {

    if (!isValidOperandCharacter(argument)) {
      throw new IllegalArgumentException("The only valid operand characters are 0-9.");
    }

    if (!isValidArithmeticOperator(argument)) {
      throw new IllegalArgumentException("The only valid operators are +, - and *");
    }

    // Check for correct sequence of inputs

    // ex: +23+1 invalid sequence
    if (inputQueue.isEmpty() && isValidArithmeticOperator(argument)) {
      throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, followed by the operator, followed by the second operand, followed by \"=\"");
    }

    if (isValidOperandCharacter(argument)) {
      inputQueue.add(argument);
    } else if (isValidArithmeticOperator(argument)) {
      // cannot have two consecutive operators
      if (!inputQueue.isEmpty() && allowedArithmeticOperators(inputQueue.peek())) {
        throw new IllegalArgumentException("Cannot have two consecutive operators.");
      }

      // check first if previously we added any operator
      if (queueContainsOperators()) {
        StringBuilder operand1 = new StringBuilder();
        StringBuilder operand2 = new StringBuilder();
        char operator = 0;
        while (!inputQueue.isEmpty()) {
          if (!allowedArithmeticOperators(inputQueue.peek())) {
            operand1.append(inputQueue.remove());
          } else if (allowedArithmeticOperators(inputQueue.peek())){
            operator = inputQueue.remove();
          } else {
            operand2.append(inputQueue.remove());
          }
        }

        int firstOperand = parseInt(operand1.toString());
        int secondOperand = parseInt(operand2.toString());
        
        inputQueue.add(computeTwoOperands(firstOperand, secondOperand, operator));
      } else {
        inputQueue.add(argument);
      }
    } else if (argument == 'C') {
      inputQueue.clear(); // clear calculator inputs
    } else if (argument == '=') {
      // TODO not infer missing
      // TODO also commute value of arithmetic operation if sequence is valid
      // Handle multiple "="
    }

    return new SimpleCalculator();
  }

  @Override
  public String getResult() {
    return inputQueue.toString(); //TODO test and should show what has been entered so far
  }

  // TODO check which exception is thrown if multi value digits are passed
  // Test for negative inputs to see how it reacts
  private boolean isValidOperandCharacter(char argument) {
    try {
      return Character.isDigit(argument);
    } catch (Exception e) {
      return false;
    }
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

  private boolean queueContainsOperators() {
    return inputQueue.contains('+')
            || inputQueue.contains('-')
            || inputQueue.contains('*');
  }

  private int parseInt(String value) {
    if (Long.parseLong(value) > Integer.MAX_VALUE) {
      throw new RuntimeException("A valid input causes an operand to integer overflow.");
    }
    return Integer.parseInt(value);
  }

  private Character computeTwoOperands(int firstOperand, int secondOperand, char operator) {
    int result = 0;
    if (operator == '+') {
      try {
        result = Math.addExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
        throw new RuntimeException("The arithmetic of the operand causes an integer overflow.");
      }
    } else if (operator == '-') {
      try {
        result = Math.subtractExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
        throw new RuntimeException("The arithmetic of the operand causes an integer overflow.");
      }
    } else if (operator == '*') {
      try {
        result = Math.multiplyExact(firstOperand, secondOperand);
      } catch (ArithmeticException ae) {
        result = 0;
        throw new RuntimeException("The arithmetic of the operand causes an integer overflow.");
      }
    }

    // TODO test this if casting works as intended
    return (char) result;
  }

}
