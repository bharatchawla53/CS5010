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

  /**
   * Constructs an empty SmartCalculator constructor.
   */
  public SmartCalculator() {
    super("", 0, '\0', false);
  }

  /**
   * Constructs an SmartCalculator constructor with inputString, second operand, operator, and
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
  private SmartCalculator(String inputString, int secondOperand,
                          char operator, boolean hasComputationPerformed) {
    super(inputString, secondOperand, operator, hasComputationPerformed);
  }

  @Override
  public Calculator input(char argument) {
    // initialize with what has been already entered thus far
    StringBuilder builder =
            this.inputString != null
                    ? new StringBuilder(this.inputString)
                    : new StringBuilder();

    // check if its a valid digit before append it to sb
    if (isValidOperandCharacter(argument)) {
      performValidOperandCharacterOperation(argument, builder, hasComputationPerformed);
      return new SmartCalculator(builder.toString(), 0, '\0', false);
    } else if (allowedArithmeticOperators(argument)) {
      // ex: +23+1 invalid sequence
      if (isBuilderEmpty(builder)) {
        if (argument == '-' || argument == '*') {
          throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, "
                  + "followed by the operator, followed by the second operand, followed by \"=\"");
        } else {
          builder.append(argument);
          return new SmartCalculator(builder.toString(), 0, '\0', false);
        }
      } else if (isLastCharAnOperator(builder)) {
        //3 2 + - 2 4 = > 32-24 = 8
        return new SmartCalculator(overrideOperator(builder.toString(), argument),
                0, '\0', false);
      } else if (checkBuilderContainsOperator(builder)) {
        return new SmartCalculator(computeSequenceThusFar(argument, builder),
                getLastOperand(), getLastOperator(), true);
      } else {
        return new SmartCalculator(builder.append(argument).toString(), 0, '\0', false);
      }
    } else if (clearCalculatorInputs(argument)) {
      return new SmartCalculator("", 0, '\0', false);
    } else if (argument == '=') {
      if (isSecondOperandMissing(builder)) {
        // get the first operand to perform computation
        String firstOperand = builder.substring(0, builder.toString().length() - 1);
        String updatedResult = performArithmeticOperation(builder.append(firstOperand).toString());
        return new SmartCalculator(updatedResult, getLastOperand(), getLastOperator(), true);
      } else if (!sbContainsOperators(builder.toString())) {
        String updatedResult = performArithmeticOperation(builder.append(this.operator)
                .append(this.secondOperand).toString());
        return new SmartCalculator(updatedResult,
                getLastOperand(), getLastOperator(), true);
      } else if (sbContainsOperators(builder.toString())) {
        if (allowedArithmeticOperators(builder.toString().charAt(0))
                && (!sbContainsOperators(builder.substring(1)))) {
          String updatedResult = performArithmeticOperation(builder.append(this.operator)
                  .append(this.secondOperand).toString());
          return new SmartCalculator(updatedResult,
                  getLastOperand(), getLastOperator(), true);
        }
        if (builder.charAt(0) == '+') {
          builder.deleteCharAt(0);
        }
        // case to commute value of arithmetic operation if sequence is valid
        return new SmartCalculator(performArithmeticOperation(builder.toString())
                , getLastOperand(), getLastOperator(), true);
      }
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 "
              + "and operators are +, - and *");
    }

    // if it falls here, just return empty object
    return new SmartCalculator(this.inputString, this.secondOperand, this.operator, this.hasComputationPerformed);
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
}
