package calculator;

/**
 * A simple calculator takes straightforward inputs to perform computation between given operands
 * and operator. The only valid operand characters are 0-9, and the only valid operators are +, -
 * and *.
 */
public class SimpleCalculator extends AbstractCalculator {

  private String inputString = "";
  private boolean hasComputationPerformed = false;

  /**
   * Constructs an empty SimpleCalculator constructor.
   */
  public SimpleCalculator() {
  }

  /**
   * Constructs an SimpleCalculator constructor with inputString and hasComputationPerformed.
   *
   * @param inputString             the inputs that has been entered thus far.
   * @param hasComputationPerformed indicating if the computation has performed on given input
   *                                string or not.
   */
  private SimpleCalculator(String inputString, boolean hasComputationPerformed) {
    this.inputString = inputString;
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
      performValidOperandCharacterOperation(argument, builder, hasComputationPerformed);
      return new SimpleCalculator(builder.toString(), false);
    } else if (allowedArithmeticOperators(argument)) {
      // ex: +23+1 invalid sequence
      if (isBuilderEmpty(builder)) {
        throw new IllegalArgumentException("A correct basic sequence of inputs is the first operand, "
                + "followed by the operator, followed by the second operand, followed by \"=\"");
      } else if (isLastCharAnOperator(builder)) {
        throw new IllegalArgumentException("Cannot have two consecutive operators.");
      } else if (checkBuilderContainsOperator(builder)) {
        return new SimpleCalculator(computeSequenceThusFar(argument, builder), true);
      } else {
        return new SimpleCalculator(builder.append(argument).toString(), false);
      }
    } else if (clearCalculatorInputs(argument)) {
      return new SimpleCalculator("", false);
    } else if (argument == '=') {
      if (isSecondOperandMissing(builder)) {
        throw new IllegalArgumentException("The calculator does not infer any missing inputs. "
                + "Please check your inputs.");
      } else if (!sbContainsOperators(builder.toString())) {
        return new SimpleCalculator(builder.toString(), false);
      } else if (sbContainsOperators(builder.toString())) {
        // case where resulted computation resulted in a negative result
        if (allowedArithmeticOperators(builder.toString().charAt(0))
                && (!sbContainsOperators(builder.substring(1)))) {
          return new SimpleCalculator(builder.toString(), false);
        }
        // case to commute value of arithmetic operation if sequence is valid
        return new SimpleCalculator(performArithmeticOperation(builder.toString()), true);
      }
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 "
              + "and operators are +, - and *");
    }
    // if it falls here, just return empty object
    return new SimpleCalculator();
  }

  @Override
  public String getResult() {
    return this.inputString;
  }

}
