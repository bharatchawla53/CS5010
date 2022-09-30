package calculator;

/**
 * A simple calculator takes straightforward inputs, and due to limited processing power it cannot
 * work with whole numbers longer than 32 bits.
 */
public class SimpleCalculator extends AbstractCalculator {

  private String inputString;
  private boolean hasComputationPerformed = false;

  /**
   *
   */
  public SimpleCalculator() {
  }

  /**
   * @param inputString
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

    // check if its a valid digit before append it to sb
    if (isValidOperandCharacter(argument)) {

      // override the string with fresh input value
      if ((hasComputationPerformed && !sbContainsOperators(builder.toString()))
              || (hasComputationPerformed && allowedArithmeticOperators(builder.toString().charAt(0)) && !sbContainsOperators(builder.substring(1)))) {
        builder.delete(0, builder.length());
      }

      // build stringBuilder
      builder.append(argument);

      // determine which operand it belongs to
      if (!sbContainsOperators(builder.toString())) {
        // check if new value causes an overflow which is done in parseInt()
        parseInt(builder.toString());
      } else {
        // split it at a Math operator
        String operandString = builder.toString().split(REGEX)[1];
        // check if new value causes an overflow which is done in parseInt()
        parseInt(operandString);
      }
      return new SimpleCalculator(builder.toString(), false);
    } else if (allowedArithmeticOperators(argument)) { // check if its a valid operator before append it to sb
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
        return new SimpleCalculator(updatedResult, true);
      } else {
        builder.append(argument);
        return new SimpleCalculator(builder.toString(), false);
      }
    } else if (argument == 'C') { // Check for correct sequence of inputs
      return new SimpleCalculator("", false); // clear calculator inputs
    } else if (argument == '=') {
      // case for missing inputs
      if (!builder.toString().equals("")
              && allowedArithmeticOperators(builder.toString().charAt(builder.toString().length() - 1))) {
        throw new IllegalArgumentException("The calculator does not infer any missing inputs. Please check your inputs.");
      } else if (allowedArithmeticOperators(builder.toString().charAt(0))
      && (!sbContainsOperators(builder.substring(1)))) {
        // case for negative results where no further operation is given
        return new SimpleCalculator(this.inputString, false); // previously calculated result
      } else if (!allowedArithmeticOperators(builder.toString().charAt(0))
              && (!sbContainsOperators(builder.substring(1)))) {
        // case for negative result where we have a new operation to perform
        return new SimpleCalculator(this.inputString, false);
      }
      else if (sbContainsOperators(builder.toString())) {
        // case to commute value of arithmetic operation if sequence is valid
        String updatedResult = performArithmeticOperation(builder.toString());
        return new SimpleCalculator(updatedResult, true);
      }
    } else {
      throw new IllegalArgumentException("The only valid operand characters are 0-9 and operators are +, - and *");
    }

    // if it falls here, just return empty object
    return new SimpleCalculator();
  }

  @Override
  public String getResult() {
    return this.inputString;
  }

}
