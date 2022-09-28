package calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class SimpleCalculatorTest {

  private Random random;

  @Before
  public void setup() {
    random = new Random();
  }

  @Test
  public void testGetResultIsEmptyBeforeEnteringInputs() {
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    String actualResult = simpleCalculator.getResult();

    assertNull(actualResult);
  }

  @Test
  public void testIsValidOperandCharacter() {
    for (int i = 0; i < 999; i++) {
      char input = (char) (random.nextInt(10) + '0');

      SimpleCalculator simpleCalculator = new SimpleCalculator();
      Calculator actualCalculator = simpleCalculator.input(input);

      String expectedResult = String.valueOf(input);

      assertEquals(expectedResult, actualCalculator.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperandCharacter() {
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    simpleCalculator.input('R');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperandCharacterForNegativeValues() {
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    simpleCalculator.input('-');
  }

  @Test
  public void testForNegativeResults() {
    int i1 = 5;
    int i2 = 8;

    char input1 = (char) (i1 + '0');
    char input2 = (char) (i2 + '0');
    char operator = '-';

    // no mutations
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    assertNull(simpleCalculator.getResult());

    Calculator calculator = simpleCalculator.input(input1);
    assertEquals(String.valueOf(input1), calculator.getResult());

    Calculator calculator1 = calculator.input(operator);
    assertEquals("" + input1 + operator, calculator1.getResult());

    Calculator calculator2 = calculator1.input(input2);
    assertEquals("" + input1 + operator + input2, calculator2.getResult());

    Calculator calculator3 = calculator2.input('=');
    assertEquals(String.valueOf(i1 - i2), calculator3.getResult());
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
      SimpleCalculator simpleCalculator = new SimpleCalculator();
      assertNull(simpleCalculator.getResult());

      Calculator calculator = simpleCalculator.input(input1);
      assertEquals(String.valueOf(input1), calculator.getResult());

      Calculator calculator1 = calculator.input(operator);
      assertEquals("" + input1 + operator, calculator1.getResult());

      Calculator calculator2 = calculator1.input(input2);
      assertEquals("" + input1 + operator + input2, calculator2.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsInvalidOperator() {
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    simpleCalculator.input('/');
  }

  @Test
  public void testCorrectBasicSequenceOfInputs() {

  }

  @Test
  public void testIncorrectBasicSequenceOfInputs() {

  }

  @Test
  public void testValidSequenceWithMultipleOperandsAndOperators() {

  }

  @Test
  public void testGetResultToShowWhatWasEnteredThusFar() {

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
      SimpleCalculator simpleCalculator = new SimpleCalculator();
      assertNull(simpleCalculator.getResult());

      Calculator calculator = simpleCalculator.input(input1);
      assertEquals(String.valueOf(input1), calculator.getResult());

      Calculator calculator1 = calculator.input(operator);
      assertEquals("" + input1 + operator, calculator1.getResult());

      Calculator calculator2 = calculator1.input(input2);
      Calculator calculator3 = calculator2.input('C');

      assertEquals("", calculator3.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingInputsWhereAnOperandIsMissing() {
    for (int i = 0; i < 999; i++) {
      int i1 = random.nextInt(10);

      char input1 = (char) (i1 + '0');
      char operator = getRandomOperator();

      // no mutations
      SimpleCalculator simpleCalculator = new SimpleCalculator();
      assertNull(simpleCalculator.getResult());

      Calculator calculator = simpleCalculator.input(input1);
      assertEquals(String.valueOf(input1), calculator.getResult());

      Calculator calculator1 = calculator.input(operator);
      assertEquals("" + input1 + operator, calculator1.getResult());

      calculator1.input('=');
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingInputsWhereAnOperatorIsGivenFirst() {
    char operator = getRandomOperator();

    // no mutations
    SimpleCalculator simpleCalculator = new SimpleCalculator();
    assertNull(simpleCalculator.getResult());

    Calculator calculator = simpleCalculator.input(operator);
    assertEquals(String.valueOf(operator), calculator.getResult());
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
      SimpleCalculator simpleCalculator = new SimpleCalculator();
      assertNull(simpleCalculator.getResult());

      Calculator calculator = simpleCalculator.input(input1);
      assertEquals(String.valueOf(input1), calculator.getResult());

      Calculator calculator1 = calculator.input(operator);
      assertEquals("" + input1 + operator, calculator1.getResult());

      Calculator calculator2 = calculator1.input(input2);
      assertEquals("" + input1 + operator + input2, calculator2.getResult());

      Calculator calculator3 = calculator2.input('=');
      assertEquals(computeValues(i1, i2, operator), calculator3.getResult());

      Calculator calculator4 = calculator3.input('=');
      assertEquals(computeValues(i1, i2, operator), calculator4.getResult());

      Calculator calculator5 = calculator3.input('=');
      assertEquals(computeValues(i1, i2, operator), calculator5.getResult());
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
      SimpleCalculator simpleCalculator = new SimpleCalculator();
      assertNull(simpleCalculator.getResult());

      Calculator calculator = simpleCalculator.input(input1);
      assertEquals(String.valueOf(input1), calculator.getResult());
      assertNotEquals(calculator.getResult(), simpleCalculator.getResult());

      Calculator calculator1 = calculator.input(operator);
      assertEquals("" + input1 + operator, calculator1.getResult());
      assertNotEquals(calculator1.getResult(), calculator.getResult());
    }
  }

  //TODO test overflow clarify which exception is being thrown??
  // Test to catch overflow exception and recall the object with a different
  // value

  /**
   * Generates a random math operator from the given array defined with allowed operators.
   *
   * @return a valid math operator.
   */
  private char getRandomOperator() {
    char[] operator = {'+', '-', '*'};
    int i = random.nextInt(operator.length);
    return operator[i];
  }

  /**
   *
   * @param i1
   * @param i2
   * @param operator
   * @return
   */
  private String computeValues(int i1, int i2, char operator) {
    switch (operator) {
      case '+':
        return String.valueOf(i1 + i2);
      case '-':
        return String.valueOf(i1 - i2);
      case '*':
        return String.valueOf(i1 * i2);
      default:
        return null;
    }
  }
}
