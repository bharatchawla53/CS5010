package bignumber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for {@link BigNumber}s.
 */
public class BigNumberTest {

  private BigNumber bigNumber;
  private Random random;
  private BigInteger bigInteger;

  @Rule
  public Timeout timeout = new Timeout(4000, TimeUnit.MILLISECONDS);

  @Before
  public void setup() {
    random = new Random();
  }

  @Test
  public void testEmptyConstructor() {
    // call to empty constructor
    bigNumber = new BigNumberImpl();

    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testConstructorWithValidParameter() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }
      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullParameter() {
    bigNumber = new BigNumberImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyStringParameter() {
    bigNumber = new BigNumberImpl("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidParameter() {
    bigNumber = new BigNumberImpl("-3456");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidParameterOtherThanNumbers() {
    bigNumber = new BigNumberImpl("gsvgisv!$%^u*");
  }

  @Test
  public void testToString() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testGetLength() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigInteger.toString().length(), bigNumber.length());
    }
  }

  @Test
  public void testLeftShiftWithPositiveNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = getRandomDigit(false);
      bigNumber.shiftLeft(shiftBy);

      bigInteger = getBigInteger(bigInteger.toString() + "0".repeat(shiftBy));

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }
      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testLeftShiftYieldResultToZero() {
    for (int i = 0; i < 999; i++) {
      bigInteger = new BigInteger("0");

      bigNumber = new BigNumberImpl(bigInteger.toString());
      bigNumber.shiftLeft(20);

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testLeftShiftWithPositiveNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftLeft(getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testLeftShiftWithNegativeNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = -getRandomDigit(false);
      bigNumber.shiftLeft(shiftBy);

      for (int d = 0; d < Math.abs(shiftBy); d++) {
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      }

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }
      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testLeftShiftWithNegativeNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftLeft(-getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithPositiveNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = getRandomDigit(false);
      bigNumber.shiftRight(shiftBy);

      for (int d = 0; d < shiftBy; d++) {
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      }

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithYieldResultToZero() {
    for (int i = 0; i < 999; i++) {
      bigInteger = new BigInteger("234745454543543543545454");

      bigNumber = new BigNumberImpl(bigInteger.toString());
      bigNumber.shiftRight(40);

      for (int d = 0; d < 30; d++) {
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      }

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithPositiveNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftRight(getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithNegativeNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = -getRandomDigit(false);
      bigNumber.shiftRight(shiftBy);

      bigInteger = getBigInteger(bigInteger.toString() + "0".repeat(Math.abs(shiftBy)));

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithNegativeNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftRight(-getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testBothPositiveShifts() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int l = 0; l < 100; l++) {
        int digit = getRandomDigit(true);

        // left shift
        bigNumber.shiftLeft(1);
        bigInteger = getBigInteger(bigInteger.toString() + "0");
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());

        // right shift
        bigNumber.shiftRight(1);
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());
      }
    }
  }

  @Test
  public void testBothNegativeShifts() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int l = 0; l < 100; l++) {
        int digit = getRandomDigit(true);

        // negative left shift
        bigNumber.shiftLeft(-1);
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());

        // negative right shift
        bigNumber.shiftRight(-1);
        bigInteger = getBigInteger(bigInteger.toString() + "0");
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());
      }
    }
  }

  @Test
  public void testBothShiftsWithAddDigit() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(50);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int l = 0; l < 50; l++) {
        int digit = getRandomDigit(true);

        // left shift
        bigNumber.shiftLeft(1);
        bigInteger = getBigInteger(bigInteger.toString() + "0");
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());

        // right shift
        bigNumber.shiftRight(1);
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());

        // negative left shift
        bigNumber.shiftLeft(-1);
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());

        // negative right shift
        bigNumber.shiftRight(-1);
        bigInteger = getBigInteger(bigInteger.toString() + "0");
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));

        assertEquals(bigInteger.toString(), bigNumber.toString());
      }
    }
  }

  @Test
  public void testShiftByZero() {
    bigInteger = generateBigNumber(100);
    bigNumber = new BigNumberImpl(bigInteger.toString());

    // left shift
    bigNumber.shiftLeft(0);
    assertEquals(bigInteger.toString(), bigNumber.toString());

    // right shift
    bigNumber.shiftRight(0);
    assertEquals(bigInteger.toString(), bigNumber.toString());
  }

  @Test
  public void testAddDigit() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(10);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int digit = getRandomDigit(true);

      for (int a = 0; a < 50; a++) {
        bigNumber.addDigit(digit);
        bigInteger = bigInteger.add(BigInteger.valueOf(digit));
      }

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNegativeDigit() {
    bigInteger = generateBigNumber(10);

    bigNumber = new BigNumberImpl(bigInteger.toString());
    int digit = -getRandomDigit(false);

    bigNumber.addDigit(digit);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTwoDecimalDigits() {
    bigInteger = generateBigNumber(10);

    bigNumber = new BigNumberImpl(bigInteger.toString());

    bigNumber.addDigit(15);
  }


  @Test
  public void testGetDigitAtForValidPosition() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDigitAtForInvalidPosition() {
    bigInteger = generateBigNumber(10);
    bigNumber = new BigNumberImpl(bigInteger.toString());
    bigNumber.getDigitAt(-4);
  }

  @Test
  public void testGetDigitAtForPositionGreaterThanLength() {
    bigInteger = generateBigNumber(10);
    bigNumber = new BigNumberImpl(bigInteger.toString());
    int actualResult = bigNumber.getDigitAt(15);

    assertEquals(0, actualResult);
  }

  @Test
  public void testCopy() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());

      BigNumber copyBigNumber = bigNumber.copy();

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), copyBigNumber.toString());
    }
  }

  @Test
  public void testCopyWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      BigNumber copyBigNumber = bigNumber.copy();

      assertEquals("0", copyBigNumber.toString());
    }
  }

  @Test
  public void testAddWithFirstNumberBeTheCaller() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);
      BigInteger bigInteger1 = generateBigNumber(100);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString()
                        .charAt(expectedResult.toString().length() - 1 - j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithSecondNumberBeTheCaller() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      BigInteger bigInteger1 = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger1.add(bigInteger);

      BigNumber actualResult = bigNumber1.add(bigNumber);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString()
                        .charAt(expectedResult.toString().length() - 1 - j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithFirstNumberSizeGreaterThanSecondNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      BigInteger bigInteger1 = generateBigNumber(100);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString()
                        .charAt(bigNumber.length() - 1 - j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithSecondNumberSizeGreaterThanFirstNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(100);
      BigInteger bigInteger1 = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString()
                        .charAt(expectedResult.toString().length() - 1 - j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testCompareTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      BigInteger bigInteger1 = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      int res = bigInteger.compareTo(bigInteger1);

      assertEquals(res, bigNumber.compareTo(bigNumber1));
    }
  }

  @Test
  public void testEqualsSameObject() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigNumber, bigNumber);
      assertEquals(bigNumber.hashCode(), bigNumber.hashCode());
    }
  }

  @Test
  public void testEqualsTwoBigNumbersAndHashCode() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      BigInteger bigInteger1 = bigInteger;

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertEquals(bigNumber, bigNumber1);
      assertEquals(bigNumber1, bigNumber);
      assertEquals(bigNumber.hashCode(), bigNumber1.hashCode());
    }
  }

  @Test
  public void testNotEqualsTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);
      BigInteger bigInteger1 = generateBigNumber(15);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertNotEquals(bigNumber, bigNumber1);
      assertNotEquals(bigNumber.hashCode(), bigNumber1.hashCode());
    }
  }

  @Test
  public void testNotEqualsTwoDifferentObjects() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(200);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      Object obj = "4322685*&^%%";

      assertNotEquals(bigNumber, obj);
      assertNotEquals(bigNumber.hashCode(), obj.hashCode());
    }
  }

  @Test
  public void testCreateNumberUsingLeftShift() {
    for (int i = 0; i < 999; i++) {
      StringBuilder expectedResult = new StringBuilder();

      bigNumber = new BigNumberImpl();

      bigNumber.shiftLeft(1);
      int digit1 = getRandomDigit(false);
      expectedResult.append(digit1);
      bigNumber.addDigit(digit1);

      assertEquals(expectedResult.toString(), bigNumber.toString());

      bigNumber.shiftLeft(1);
      int digit2 = getRandomDigit(false);
      expectedResult.append(digit2);
      bigNumber.addDigit(digit2);

      assertEquals(expectedResult.toString(), bigNumber.toString());

      bigNumber.shiftLeft(1);
      int digit3 = getRandomDigit(false);
      expectedResult.append(digit3);
      bigNumber.addDigit(digit3);

      assertEquals(expectedResult.toString(), bigNumber.toString());

      bigNumber.shiftLeft(1);
      int digit4 = getRandomDigit(false);
      expectedResult.append(digit4);
      bigNumber.addDigit(digit4);

      assertEquals(expectedResult.toString(), bigNumber.toString());

      bigNumber.shiftLeft(1);
      int digit5 = getRandomDigit(false);
      expectedResult.append(digit5);
      bigNumber.addDigit(digit5);

      assertEquals(expectedResult.toString(), bigNumber.toString());

      assertEquals(expectedResult.toString().length(), bigNumber.length());
    }
  }

  @Test
  public void testCreateNumberUsingLeftShiftAndCreateACopyEachTime() {
    for (int i = 0; i < 999; i++) {
      StringBuilder expectedResult = new StringBuilder();

      bigNumber = new BigNumberImpl();

      bigNumber.shiftLeft(1);
      int digit1 = getRandomDigit(false);
      expectedResult.append(digit1);
      bigNumber.addDigit(digit1);
      BigNumber newCopy1 = bigNumber.copy();

      assertEquals(expectedResult.toString(), bigNumber.toString());
      assertEquals(expectedResult.toString(), newCopy1.toString());

      bigNumber.shiftLeft(1);
      int digit2 = getRandomDigit(false);
      expectedResult.append(digit2);
      bigNumber.addDigit(digit2);
      BigNumber newCopy2 = bigNumber.copy();

      assertEquals(expectedResult.toString(), bigNumber.toString());
      assertEquals(expectedResult.toString(), newCopy2.toString());

      bigNumber.shiftLeft(1);
      int digit3 = getRandomDigit(false);
      expectedResult.append(digit3);
      bigNumber.addDigit(digit3);
      BigNumber newCopy3 = bigNumber.copy();

      assertEquals(expectedResult.toString(), bigNumber.toString());
      assertEquals(expectedResult.toString(), newCopy3.toString());

      bigNumber.shiftLeft(1);
      int digit4 = getRandomDigit(false);
      expectedResult.append(digit4);
      bigNumber.addDigit(digit4);
      BigNumber newCopy4 = bigNumber.copy();

      assertEquals(expectedResult.toString(), bigNumber.toString());
      assertEquals(expectedResult.toString(), newCopy4.toString());

      bigNumber.shiftLeft(1);
      int digit5 = getRandomDigit(false);
      expectedResult.append(digit5);
      bigNumber.addDigit(digit5);
      BigNumber newCopy5 = bigNumber.copy();

      assertEquals(expectedResult.toString(), bigNumber.toString());
      assertEquals(expectedResult.toString(), newCopy5.toString());

      assertEquals(expectedResult.toString().length(), bigNumber.length());
    }
  }

  @Test
  public void testAllOperations() {
    bigInteger = generateBigNumber(100);
    bigNumber = new BigNumberImpl(bigInteger.toString());

    for (int i = 0; i < 1000; i++) {
      // left shift
      bigNumber.shiftLeft(1);
      bigInteger = getBigInteger(bigInteger.toString() + "0");
      int digit1 = getRandomDigit(false);

      // add digit
      bigNumber.addDigit(digit1);
      bigInteger = bigInteger.add(BigInteger.valueOf(digit1));
      assertEquals(bigInteger.toString(), bigNumber.toString());

      // right shift
      bigNumber.shiftRight(1);
      bigInteger = bigInteger.divide(BigInteger.valueOf(10));

      int digit2 = getRandomDigit(false);
      // add digit
      bigNumber.addDigit(digit2);

      bigInteger = bigInteger.add(BigInteger.valueOf(digit2));
      assertEquals(bigInteger.toString(), bigNumber.toString());

      // negative left shift
      bigNumber.shiftLeft(-1);
      bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      int digit3 = getRandomDigit(false);

      // add digit
      bigNumber.addDigit(digit3);
      bigInteger = bigInteger.add(BigInteger.valueOf(digit3));
      assertEquals(bigInteger.toString(), bigNumber.toString());

      // negative right shift
      bigNumber.shiftRight(-1);
      bigInteger = getBigInteger(bigInteger.toString() + "0");
      int digit4 = getRandomDigit(false);

      // add digit
      bigNumber.addDigit(digit4);
      bigInteger = bigInteger.add(BigInteger.valueOf(digit4));
      assertEquals(bigInteger.toString(), bigNumber.toString());

      // copy the list
      BigNumber newCopy = bigNumber.copy();
      assertEquals(bigInteger.toString(), newCopy.toString());

      // getDigitAt
      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString()
                        .charAt(bigInteger.toString().length() - 1 - j))),
                bigNumber.getDigitAt(j));
      }

      // add two big numbers
      BigInteger bigInteger1 = generateBigNumber(200);
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());
      BigNumber addResult = bigNumber.add(bigNumber1);

      BigInteger actualResult = bigInteger.add(bigInteger1);

      assertEquals(actualResult.toString(), addResult.toString());

      // compare numbers
      int expectedCompareResult = bigInteger.compareTo(bigInteger1);
      int actualCompareResult = bigNumber.compareTo(bigNumber1);

      assertEquals(expectedCompareResult, actualCompareResult);

    }
  }

  private BigInteger generateBigNumber(int size) {
    StringBuilder bigInteger = new StringBuilder();

    for (int i = 0; i < size; i++) {
      bigInteger.append(getRandomDigit(true));
    }

    return getBigInteger(bigInteger.toString());
  }

  private int getRandomDigit(boolean includeZero) {
    return includeZero
            ? random.nextInt(10)
            : random.nextInt((10 - 1)) + 1;
  }

  private BigInteger getBigInteger(String num) {
    return new BigInteger(num);
  }
}
