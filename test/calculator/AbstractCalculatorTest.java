package calculator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for {@link Calculator}s.
 */
public abstract class AbstractCalculatorTest {
    /*
    Leave this section alone: It contains abstract methods to
    create Calculator, and concrete implementations of this testing class
    will supply particular implementations of Calculator to be used within
    these tests.
   */

  /**
   * Constructs an instance of the class under test representing AbstractCalculator object.
   *
   * @return an instance of the class under test
   */
  protected abstract Calculator ac();

  /**
   * This class tests simple calculator.
   */
  public static final class SimpleCalculatorTest extends AbstractCalculatorTest {

    @Override
    protected Calculator ac() {
      return new SimpleCalculator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingInputsWhereAnSecondOperandIsMissing() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        calculator2.input('=');
      }
    }

    @Test
    public void testIncorrectSequenceOfInputsAndCatchTheException() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());
        Calculator calculator3;
        try {
          calculator3 = calculator2.input('=');
        } catch (IllegalArgumentException e) {
          calculator3 = calculator2.input(input2);
          assertEquals("" + input1 + operator + input2, calculator3.getResult());
        }

        Calculator calculator4 = calculator3.input('=');
        assertEquals(computeValues(i1, i2, operator), calculator4.getResult());
      }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingInputsWhereAnOperandIsMissingAndTwoOperatorsAreGiven() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        calculator2.input(getRandomOperator());
      }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingInputsWhereAnOperatorIsGivenFirst() {
      char operator = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      calculator.input(operator);
    }

    @Test
    public void testMissingInputsWhereAnOperatorIsGivenFirstAndCatchTheException() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1;
        try {
          calculator1 = calculator.input(operator);
        } catch (IllegalArgumentException e) {
          calculator1 = calculator.input(input1);
          assertEquals(String.valueOf(input1), calculator1.getResult());

          Calculator calculator2 = calculator1.input(operator);
          assertEquals("" + input1 + operator, calculator2.getResult());

          Calculator calculator3 = calculator2.input(input2);
          assertEquals("" + input1 + operator + input2, calculator3.getResult());

          Calculator calculator4 = calculator3.input('=');
          assertEquals(computeValues(i1, i2, operator), calculator4.getResult());
        }
      }
    }

    @Test
    public void testResultInputEnteredMultipleTimes() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        Calculator calculator3 = calculator2.input(input2);
        assertEquals("" + input1 + operator + input2, calculator3.getResult());

        Calculator calculator4 = calculator3.input('=');
        assertEquals(computeValues(i1, i2, operator), calculator4.getResult());

        Calculator calculator5 = calculator4.input('=');
        assertEquals(computeValues(i1, i2, operator), calculator5.getResult());

        Calculator calculator6 = calculator5.input('=');
        assertEquals(computeValues(i1, i2, operator), calculator6.getResult());
      }
    }


  }

  /**
   * This class tests smart calculator.
   */
  public static final class SmartCalculatorTest extends AbstractCalculatorTest {

    @Override
    protected Calculator ac() {
      return new SmartCalculator();
    }

    @Test
    public void testMissingInputsWhereAnSecondOperandIsMissing() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        Calculator calculator3 = calculator2.input('=');
        assertEquals(computeValues(i1, i1, operator), calculator3.getResult());

        Calculator calculator4 = calculator3.input('=');
        assertEquals(computeValues(Integer.parseInt(computeValues(i1, i1, operator)), i1, operator),
                calculator4.getResult());
      }
    }

    @Test
    public void testTwoConsecutiveOperatorsIgnoresFirstOne() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = getRandomOperator();
        char operator1 = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        Calculator calculator3 = calculator2.input(operator1);
        assertEquals("" + input1 + operator1, calculator3.getResult());

        Calculator calculator4 = calculator3.input(input2);
        assertEquals("" + input1 + operator1 + input2, calculator4.getResult());

        Calculator calculator5 = calculator4.input('=');
        String expectedFirstComputation = computeValues(i1, i2, operator1);
        assertEquals(expectedFirstComputation, calculator5.getResult());
      }
    }

    @Test
    public void testBeginWithAdditionOperator() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = '+';
        char operator1 = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(operator);
        assertEquals(String.valueOf(operator), calculator1.getResult());

        Calculator calculator2 = calculator1.input(input1);
        assertEquals("" + operator + input1, calculator2.getResult());

        Calculator calculator3 = calculator2.input(operator1);
        assertEquals("" + operator + input1 + operator1, calculator3.getResult());

        Calculator calculator4 = calculator3.input(input2);
        assertEquals("" + operator + input1 + operator1 + input2, calculator4.getResult());

        Calculator calculator5 = calculator4.input('=');
        String expectedFirstComputation = computeValues(i1, i2, operator1);
        assertEquals(expectedFirstComputation, calculator5.getResult());
      }
    }

    @Test
    public void testResultInputEnteredMultipleTimes() {
      for (int i = 0; i < 999; i++) {
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);

        char input1 = (char) (i1 + '0');
        char input2 = (char) (i2 + '0');
        char operator = getRandomOperator();

        // no mutations
        Calculator calculator = ac();
        assertNull(calculator.getResult());

        Calculator calculator1 = calculator.input(input1);
        assertEquals(String.valueOf(input1), calculator1.getResult());

        Calculator calculator2 = calculator1.input(operator);
        assertEquals("" + input1 + operator, calculator2.getResult());

        Calculator calculator3 = calculator2.input(input2);
        assertEquals("" + input1 + operator + input2, calculator3.getResult());

        Calculator calculator4 = calculator3.input('=');
        String expectedFirstComputation = computeValues(i1, i2, operator);
        assertEquals(expectedFirstComputation, calculator4.getResult());

        Calculator calculator5 = calculator4.input('=');
        String expectedSecondComputation = computeValues(Integer.parseInt(expectedFirstComputation), i2, operator);
        assertEquals(expectedSecondComputation, calculator5.getResult());

        Calculator calculator6 = calculator5.input('=');
        String expectedThirdComputation = computeValues(Integer.parseInt(expectedSecondComputation), i2, operator);
        assertEquals(expectedThirdComputation, calculator6.getResult());
      }
    }

  }

  protected Random random;

  @Before
  public void setup() {
    random = new Random();
  }

  @Test
  public void testGetResultIsEmptyBeforeEnteringInputs() {
    Calculator calculator = ac();
    String actualResult = calculator.getResult();

    assertNull(actualResult);
  }

  @Test
  public void testIsValidOperandCharacter() {
    for (int i = 0; i < 999; i++) {
      char input = (char) (random.nextInt(10) + '0');

      Calculator calculator = ac();
      Calculator actualCalculator = calculator.input(input);

      String expectedResult = String.valueOf(input);

      assertEquals(expectedResult, actualCalculator.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperandCharacter() {
    Calculator calculator = ac();
    calculator.input('R');
  }

  @Test
  public void testIsInvalidOperandCharacterAndCatchTheExceptionToTestWithNewValidInput() {
    for (int i = 0; i < 999; i++) {
      char input = (char) (random.nextInt(10) + '0');

      Calculator calculator = ac();
      Calculator calculator1;

      try {
        calculator1 = calculator.input('R');
      } catch (IllegalArgumentException e) {
        calculator1 = calculator.input(input);
        String expectedResult = String.valueOf(input);

        assertEquals(expectedResult, calculator1.getResult());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperandCharacterForNegativeValues() {
    Calculator calculator = ac();
    calculator.input('-');
  }

  @Test
  public void testIsInvalidOperandCharacterForNegativeValuesAndCatchTheExceptionToTestWithNewValidInput() {
    Calculator calculator = ac();
    try {
      calculator.input('-');
    } catch (IllegalArgumentException e) {
      Calculator calculator1 = calculator.input('5');
      assertEquals(String.valueOf('5'), calculator1.getResult());
    }
  }

  @Test
  public void testIsValidOperator() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);
      int i2 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char input2 = (char) (i2 + '0');
      char operator = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());

      Calculator calculator3 = calculator2.input(input2);
      assertEquals("" + input1 + operator + input2, calculator3.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperator() {
    Calculator calculator = ac();
    calculator.input('/');
  }

  @Test
  public void testIsInvalidOperatorAndCatchTheExceptionToTestWithNewValidInput() {
    Calculator calculator = ac();
    try {
      calculator.input('/');
    } catch (IllegalArgumentException e) {
      int i1 = random.nextInt(10);
      char input1 = (char) (i1 + '0');
      char operator = getRandomOperator();

      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());
    }
  }

  @Test
  public void testCorrectBasicSequenceOfInputs() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);
      int i2 = random.nextInt(10);
      int i3 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char input2 = (char) (i2 + '0');
      char input3 = (char) (i3 + '0');
      char operator = getRandomOperator();
      char operator1 = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());

      Calculator calculator3 = calculator2.input(input2);
      assertEquals("" + input1 + operator + input2, calculator3.getResult());

      Calculator calculator4 = calculator3.input(operator1);
      assertEquals(computeValues(i1, i2, operator) + operator1, calculator4.getResult());

      Calculator calculator5 = calculator4.input(input3);
      assertEquals(computeValues(i1, i2, operator) + operator1 + input3, calculator5.getResult());

      Calculator calculator6 = calculator5.input('=');
      assertEquals(computeValues(Integer.parseInt(computeValues(i1, i2, operator)), i3, operator1), calculator6.getResult());
    }
  }

  @Test
  public void testForNegativeResults() {
    int i1 = 5;
    int i2 = 8;

    char input1 = (char) (i1 + '0');
    char input2 = (char) (i2 + '0');
    char operator = '-';

    // no mutations
    Calculator calculator = ac();
    assertNull(calculator.getResult());

    Calculator calculator1 = calculator.input(input1);
    assertEquals(String.valueOf(input1), calculator1.getResult());

    Calculator calculator2 = calculator1.input(operator);
    assertEquals("" + input1 + operator, calculator2.getResult());

    Calculator calculator3 = calculator2.input(input2);
    assertEquals("" + input1 + operator + input2, calculator3.getResult());

    Calculator calculator4 = calculator3.input('=');
    assertEquals(String.valueOf(i1 - i2), calculator4.getResult());
  }

  @Test
  public void testClearCalculatorInput() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);
      int i2 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char input2 = (char) (i2 + '0');
      char operator = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());

      Calculator calculator3 = calculator2.input(input2);
      assertEquals("" + input1 + operator + input2, calculator3.getResult());

      Calculator calculator4 = calculator3.input('C');
      assertEquals("", calculator4.getResult());
    }
  }

  @Test
  public void testNoChangesToCallingObject() {
    // input method is not expected to change the calling object.
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char operator = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());
      assertNotEquals(calculator1.getResult(), calculator.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());
      assertNotEquals(calculator2.getResult(), calculator1.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperandOverflow() {
    char input = (char) (9 + '0');

    Calculator calculator9 = getOperandOverflowObject(input);
    calculator9.input(input);
  }

  @Test
  public void testOperandOverflowButTheValueBeforeIsRetained() {
    Calculator calculator9 = null;
    char input = 0;
    try {
      int i1 = random.nextInt(10);
      input = (char) (i1 + '0');

      calculator9 = getOperandOverflowObject(input);
      Calculator calculator10 = calculator9.input(input);
      calculator9.input(input);
    } catch (IllegalArgumentException e) {
      String inputString = "" + input;
      String expectedResult = inputString.repeat(9);
      assertEquals(expectedResult, calculator9.getResult());
    }
  }

  @Test
  public void testAdditionOverflow() {
    //input 1 '2055786000'
    Calculator calculator = ac();
    assertNull(calculator.getResult());

    Calculator calculator27 = operand1(calculator);
    assertEquals("2055786000", calculator27.getResult());

    //operator '+'
    Calculator calculator9 = calculator27.input('+');
    assertEquals("2055786000+", calculator9.getResult());

    //input 2 '93552000'
    Calculator calculator17 = operand2(calculator9);

    //operator '-'
    Calculator calculator18 = calculator17.input('-');

    assertEquals("0-", calculator18.getResult());

    // input 3 2589000
    Calculator calculator25 = operand3(calculator18);

    assertEquals("0-2589000", calculator25.getResult());

    Calculator calculator26 = calculator25.input('=');

    assertEquals("-2589000", calculator26.getResult());
  }

  @Test
  public void testMultiplicationOverflow() {
    //input 1 '2055786000'
    Calculator calculator = ac();
    assertNull(calculator.getResult());

    Calculator calculator27 = operand1(calculator);
    assertEquals("2055786000", calculator27.getResult());

    //operator '+'
    Calculator calculator9 = calculator27.input('*');
    assertEquals("2055786000*", calculator9.getResult());

    //input 2 '93552000'
    Calculator calculator17 = operand2(calculator9);

    //operator '-'
    Calculator calculator18 = calculator17.input('+');

    assertEquals("0+", calculator18.getResult());

    // input 3 2589000
    Calculator calculator25 = operand3(calculator18);

    assertEquals("0+2589000", calculator25.getResult());

    Calculator calculator26 = calculator25.input('=');

    assertEquals("2589000", calculator26.getResult());
  }

  @Ignore // TODO fix this
  @Test
  public void testSubtractionOverflow() {
    //input 1 '2055786000'
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    assertNull(simpleCalculator.getResult());

    Calculator calculator27 = operand1(simpleCalculator);
    assertEquals("2055786000", calculator27.getResult());

    //operator '+'
    Calculator calculator9 = calculator27.input('-');
    assertEquals("2055786000-", calculator9.getResult());

    //input 2 '93552000'
    Calculator calculator17 = operand2(calculator9);

    //operator '-'
    Calculator calculator18 = calculator17.input('-');

    assertEquals("0-", calculator18.getResult());

    // input 3 2589000
    Calculator calculator25 = operand3(calculator18);

    assertEquals("0-2589000", calculator25.getResult());

    Calculator calculator26 = calculator25.input('=');

    assertEquals("-2589000", calculator26.getResult());
  }

  @Test
  public void testMultipleOperatorsAndResultInputEnteredMultipleTimes() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);
      int i2 = random.nextInt(10);
      int i3 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char input2 = (char) (i2 + '0');
      char input3 = (char) (i3 + '0');
      char operator = getRandomOperator();
      char operator1 = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());

      Calculator calculator3 = calculator2.input(input2);
      assertEquals("" + input1 + operator + input2, calculator3.getResult());

      Calculator calculator4 = calculator3.input('=');
      assertEquals(computeValues(i1, i2, operator), calculator4.getResult());

      Calculator calculator5 = calculator4.input(operator1);
      assertEquals(computeValues(i1, i2, operator) + operator1, calculator5.getResult());

      Calculator calculator6 = calculator5.input(input3);
      assertEquals(computeValues(i1, i2, operator) + operator1 + input3, calculator6.getResult());

      Calculator calculator7 = calculator6.input('=');
      assertEquals(computeValues(Integer.parseInt(computeValues(i1, i2, operator)), i3, operator1),
              calculator7.getResult());
    }
  }

  @Test
  public void testOverrideComputedResultIfAnOperandIsProvidedInsteadOfAnOperator() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);
      int i2 = random.nextInt(10);
      int i3 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char input2 = (char) (i2 + '0');
      char input3 = (char) (i3 + '0');
      char operator = getRandomOperator();

      // no mutations
      Calculator calculator = ac();
      assertNull(calculator.getResult());

      Calculator calculator1 = calculator.input(input1);
      assertEquals(String.valueOf(input1), calculator1.getResult());

      Calculator calculator2 = calculator1.input(operator);
      assertEquals("" + input1 + operator, calculator2.getResult());

      Calculator calculator3 = calculator2.input(input2);
      assertEquals("" + input1 + operator + input2, calculator3.getResult());

      Calculator calculator4 = calculator3.input('=');
      assertEquals(computeValues(i1, i2, operator), calculator4.getResult());

      Calculator calculator5 = calculator4.input(input3);
      assertEquals(String.valueOf(input3), calculator5.getResult());
    }
  }

  /**
   * Generates a random math operator from the given array defined with allowed operators.
   *
   * @return a valid math operator.
   */
  protected char getRandomOperator() {
    char[] operator = {'+', '-', '*'};
    int i = random.nextInt(operator.length);
    return operator[i];
  }

  /**
   * Compute mathematics operation based on the operator given.
   *
   * @param i1       operand 1.
   * @param i2       operand 2.
   * @param operator operator
   * @return returns the computed result.
   */
  protected String computeValues(int i1, int i2, char operator) {
    switch (operator) {
      case '+':
        return String.valueOf(i1 + i2);
      case '-':
        return String.valueOf(i1 - i2);
      case '*':
        return String.valueOf(i1 * i2);
    }
    return null;
  }

  private Calculator getOperandOverflowObject(char input) {
    Calculator calculator = ac();
    Calculator calculator1 = calculator.input(input);
    Calculator calculator2 = calculator1.input(input);
    Calculator calculator3 = calculator2.input(input);
    Calculator calculator4 = calculator3.input(input);
    Calculator calculator5 = calculator4.input(input);
    Calculator calculator6 = calculator5.input(input);
    Calculator calculator7 = calculator6.input(input);
    Calculator calculator8 = calculator7.input(input);
    return calculator8.input(input);
  }

  private Calculator operand3(Calculator calculator18) {
    Calculator calculator19 = calculator18.input('2');
    Calculator calculator20 = calculator19.input('5');
    Calculator calculator21 = calculator20.input('8');
    Calculator calculator22 = calculator21.input('9');
    Calculator calculator23 = calculator22.input('0');
    Calculator calculator24 = calculator23.input('0');
    return calculator24.input('0');
  }

  private Calculator operand2(Calculator calculator9) {
    Calculator calculator10 = calculator9.input('9');
    Calculator calculator11 = calculator10.input('3');
    Calculator calculator12 = calculator11.input('5');
    Calculator calculator13 = calculator12.input('5');
    Calculator calculator14 = calculator13.input('2');
    Calculator calculator15 = calculator14.input('0');
    Calculator calculator16 = calculator15.input('0');
    return calculator16.input('0');
  }

  private Calculator operand1(Calculator cal) {
    Calculator calculator = cal.input('2');
    Calculator calculator1 = calculator.input('0');
    Calculator calculator2 = calculator1.input('5');
    Calculator calculator3 = calculator2.input('5');
    Calculator calculator4 = calculator3.input('7');
    Calculator calculator5 = calculator4.input('8');
    Calculator calculator6 = calculator5.input('6');
    Calculator calculator7 = calculator6.input('0');
    Calculator calculator8 = calculator7.input('0');
    Calculator calculator27 = calculator8.input('0');
    return calculator27;
  }
}