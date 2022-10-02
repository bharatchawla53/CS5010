package calculator;

/**
 * A smart calculator accepts inputs like simple calculator, and is backwards compatible with it.
 * There are certain smart operations it can perform such as - 1. Input '=' entered multiple times
 * produces the result of the previous operands plus the last operand and operator it used to
 * compute it. 2. Skipping second operand, it produces the result with it's own first operand. 3.
 * Given two consecutive operators, it ignores the first one. 4. Begin with '+' operator in the
 * sequence, it ignores it, and use the next operator given between two operands. It does not allow
 * any other operators to be at the first of the sequence.
 */
public class SmartCalculator extends AbstractCalculator {

  private String inputString;
  private int lastOperand;
  private char lastOperator;
  private boolean hasComputationPerformed = false;

  /**
   * Constructs an empty SmartCalculator constructor.
   */
  public SmartCalculator() {
  }

  /**
   * Constructs an SimpleCalculator constructor with inputString, hasComputationPerformed,
   * lastOperand, and lastOperator.
   *
   * @param inputString             the inputs that has been entered thus far.
   * @param hasComputationPerformed indicating if the computation has performed on given input
   *                                string or not.
   * @param lastOperand             the last operand used in previous computation.
   * @param lastOperator            the last operator used in previous computation.
   */
  private SmartCalculator(String inputString, boolean hasComputationPerformed, int lastOperand, char lastOperator) {
    this.inputString = inputString;
    this.hasComputationPerformed = hasComputationPerformed;
    this.lastOperand = lastOperand;
    this.lastOperator = lastOperator;
  }

  @Override
  public Calculator input(char argument) {
    // initialize with what has been already entered thus far
    StringBuilder builder =
            inputString != null
                    ? new StringBuilder(this.inputString)
                    : new StringBuilder();

    // check if its a valid digit before append it to sb
    if (isValidOperandCharacter(argument)) {
      performValidOperandCharacterOperation(argument, builder, hasComputationPerformed);
      return new SmartCalculator(builder.toString(), false, 0, '\0');
    } else if (allowedArithmeticOperators(argument)) { // check if its a valid operator before append it to sb
      // ex: +23+1 invalid sequence
      if (isBuilderEmpty(builder)) {
        if (argument == '-' || argument == '*') {
          throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, followed by the operator, followed by the second operand, followed by \"=\"");
        } else {
          builder.append(argument);
          return new SmartCalculator(builder.toString(), false, 0, '\0');
        }
      } else if (isLastCharAnOperator(builder)) {
        //3 2 + - 2 4 = > 32-24 = 8
        return new SmartCalculator(overrideOperator(builder.toString(), argument), false, 0, '\0');
      } else if (checkBuilderContainsOperator(builder)) {
        return new SmartCalculator(computeSequenceThusFar(argument, builder), true, getLastOperand(), getLastOperator());
      } else {
        return new SmartCalculator(builder.append(argument).toString(), false, 0, '\0');
      }
    } else if (clearCalculatorInputs(argument)) {
      return new SmartCalculator("", false, 0, '\0');
    } else if (argument == '=') {
      if (isSecondOperandMissing(builder)) {
        // get the first operand to perform computation
        String firstOperand = builder.substring(0, builder.toString().length() - 1);
        String updatedResult = performArithmeticOperation(builder.append(firstOperand).toString());
        return new SmartCalculator(updatedResult, true, getLastOperand(), getLastOperator());
      } else if (!sbContainsOperators(builder.toString())) {
        String updatedResult = performArithmeticOperation(builder.append(this.lastOperator).append(this.lastOperand).toString());
        return new SmartCalculator(updatedResult, true, getLastOperand(), getLastOperator()); // previously calculated result
      } else if (sbContainsOperators(builder.toString())) {
        if (allowedArithmeticOperators(builder.toString().charAt(0))
                && (!sbContainsOperators(builder.substring(1)))) {
          String updatedResult = performArithmeticOperation(builder.append(this.lastOperator).append(this.lastOperand).toString());
          return new SmartCalculator(updatedResult, true, getLastOperand(), getLastOperator());
        }
        if (builder.charAt(0) == '+') {
          builder.deleteCharAt(0);
        }
        // case to commute value of arithmetic operation if sequence is valid
        return new SmartCalculator(performArithmeticOperation(builder.toString()), true, getLastOperand(), getLastOperator());
      }
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 and operators are +, - and *");
    }

    // if it falls here, just return empty object
    return new SmartCalculator();
  }

  /**
   * When two consecutive operators are provided, it should ignore the first operator. This method
   * just overrides that and replace it with the last operator given.
   *
   * @param builder  the string - where the operator needs to be overridden.
   * @param operator the operator it needs to override with it.
   * @return the updates string with new operator.
   */
  private String overrideOperator(String builder, char operator) {
    return builder.replaceAll(REGEX, String.valueOf(operator));
  }

  @Override
  public String getResult() {
    return this.inputString;
  }
}
