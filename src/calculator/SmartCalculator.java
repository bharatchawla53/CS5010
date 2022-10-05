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

  /**
   * A correct sequence of inputs is the first operand, followed by an operator, followed by the
   * second operand. But a smart calculator allows to have '+' operator as it has a mathematical
   * meaning when it comes before an operand as long as the builder is empty.
   *
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @param argument an input received from the client.
   * @return new SmartCalculator object with updated input received thus far.
   * @throws IllegalArgumentException for any incorrect inputs and sequences, and operators such as
   *                                  '-' && '*'.
   */
  @Override
  protected Calculator handleBuilderEmptyAndAllowedToBeginWithAnOperator(StringBuilder builder,
                                                                         char argument) {
    if (argument == '-' || argument == '*') {
      throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, "
              + "followed by the operator, followed by the second operand, followed by \"=\"");
    } else {
      //builder.append(argument);
      return new SmartCalculator(builder.toString(), 0, '\0', false);
    }
  }

  /**
   * It does allow two consecutive operators, and ignores the first operator, and uses the second
   * one for computation.
   *
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @param argument an input received from the client.
   * @return new SmartCalculator object with updated input received thus far and override the
   * previous operator.
   */
  @Override
  protected Calculator handleIfThereAreTwoConsecutiveOperators(StringBuilder builder,
                                                               char argument) {
    return new SmartCalculator(overrideOperator(builder.toString(), argument),
            0, '\0', false);
  }

  /**
   * If the second operand is missing, it uses the first operand to compute the sequence. For
   * example: 32 += 64, 32 +== 96, and so on.
   *
   * @param builder a sequence of inputs appended in a StringBuilder.
   * @return new SmartCalculator object with updated computed result.
   */
  @Override
  protected Calculator handleIfSecondOperandMissing(StringBuilder builder) {
    String firstOperand = builder.substring(0, builder.toString().length() - 1);
    String updatedResult = performArithmeticOperation(builder.append(firstOperand).toString());
    return new SmartCalculator(updatedResult, getLastOperand(), getLastOperator(), true);
  }

  /**
   * @param builder
   * @return
   */
  @Override // TODO verfiy this logic
  protected Calculator handleIfBuilderDoesNotContainOperators(StringBuilder builder) {
    if (getLastOperator() != '\0') {
      String updatedResult = performArithmeticOperation(builder.append(getLastOperator())
              .append(getLastOperand()).toString());
      return new SmartCalculator(updatedResult,
              getLastOperand(), getLastOperator(), true);
    }
    return new SmartCalculator(getResult(), getLastOperand(), getLastOperator(), false);
  }

  /**
   * @param builder
   * @return
   */
  @Override
  protected Calculator handleIfBuilderContainOperators(StringBuilder builder) {
    if (builder.charAt(0) == '+') {
      builder.deleteCharAt(0);
    }
    // case to commute value of arithmetic operation if sequence is valid
    return new SmartCalculator(performArithmeticOperation(builder.toString()),
            getLastOperand(), getLastOperator(), true);
  }

  /**
   * Creates a SmartCalculator object with updated values that we received from the client thus
   * far.
   *
   * @param inputString             the inputs that has been entered thus far.
   * @param secondOperand           tracks the last operand used in computation before a new
   *                                operator or operand is given.
   * @param operator                tracks the last operator used in computation before a new
   *                                operator or operand is given.
   * @param hasComputationPerformed indicating if the computation has performed on given input
   *                                string or not.
   * @return a new SmartCalculator object.
   */
  @Override
  protected Calculator calculatorFactory(String inputString, int secondOperand,
                                         char operator, boolean hasComputationPerformed) {
    return new SmartCalculator(inputString, secondOperand, operator, hasComputationPerformed);
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
