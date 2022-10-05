package calculator;

/**
 * A simple calculator takes straightforward inputs to perform computation between given operands
 * and operator. The only valid operand characters are 0-9, and the only valid operators are +, -
 * and *.
 */
public class SimpleCalculator extends AbstractCalculator {

  /**
   * Constructs an empty SimpleCalculator constructor.
   */
  public SimpleCalculator() {
    super("", 0, '\0', false);
  }

  /**
   * Constructs an SimpleCalculator constructor with inputString, second operand, operator, and
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
  private SimpleCalculator(String inputString, int secondOperand,
                           char operator, boolean hasComputationPerformed) {
    super(inputString, secondOperand, operator, hasComputationPerformed);
  }

  /**
   * A correct sequence of inputs is the first operand, followed by an operator, followed by the
   * second operand. So, if the builder is empty and the first sequence of input is an operator, it
   * throws an exception.
   *
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @param argument an input received from the client.
   * @throws IllegalArgumentException for any incorrect inputs and sequences.
   */
  @Override
  protected Calculator handleBuilderEmptyAndAllowedToBeginWithAnOperator(StringBuilder builder,
                                                                         char argument) {
    throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, "
            + "followed by the operator, followed by the second operand, followed by \"=\"");
  }

  /**
   * It does not allow two consecutive operators.
   *
   * @param builder  a sequence of inputs appended in a StringBuilder.
   * @param argument an input received from the client.
   * @throws IllegalArgumentException if two consecutive operators are found.
   */
  @Override
  protected Calculator handleIfThereAreTwoConsecutiveOperators(StringBuilder builder,
                                                               char argument) {
    throw new IllegalArgumentException("Cannot have two consecutive operators.");
  }

  /**
   * It does not "infer" any missing inputs.
   *
   * @param builder a sequence of inputs appended in a StringBuilder.
   * @throws IllegalArgumentException if there are any missing inputs.
   */
  @Override
  protected Calculator handleIfSecondOperandMissing(StringBuilder builder) {
    throw new IllegalArgumentException("The calculator does not infer any missing inputs. "
            + "Please check your inputs.");
  }

  /**
   * @param builder
   * @return
   */
  @Override
  protected Calculator handleIfBuilderDoesNotContainOperators(StringBuilder builder) {
    if (this.hasComputationPerformed) {
      return new SimpleCalculator(builder.toString(), 0, '\0', true);
    }
    throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, "
            + "followed by the operator, followed by the second operand, followed by \"=\"");
  }

  /**
   * @param builder
   * @return
   */
  @Override
  protected Calculator handleIfBuilderContainOperators(StringBuilder builder) {
    return new SimpleCalculator(performArithmeticOperation(builder.toString()), 0, '\0', true);
  }

  /**
   * Creates a SimpleCalculator object with updated values that we received from the client thus
   * far.
   *
   * @param inputString             the inputs that has been entered thus far.
   * @param secondOperand           tracks the last operand used in computation before a new
   *                                operator or operand is given.
   * @param operator                tracks the last operator used in computation before a new
   *                                operator or operand is given.
   * @param hasComputationPerformed indicating if the computation has performed on given input
   *                                string or not.
   * @return a new SimpleCalculator object.
   */
  @Override
  protected Calculator calculatorFactory(String inputString, int secondOperand,
                                         char operator, boolean hasComputationPerformed) {
    return new SimpleCalculator(inputString, secondOperand, operator, hasComputationPerformed);
  }
}
