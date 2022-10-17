package bignumber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BigNumberTest {

  private BigNumber bigNumber;
  private Random random;
  private BigInteger bigInteger;

  @Rule
  public Timeout timeout = new Timeout(3000, TimeUnit.MILLISECONDS);

  @Before
  public void setup() {
    random = new Random();
  }

  @Test
  public void shouldNotTimeOut() throws InterruptedException {
    sleep(2000);
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
      bigInteger = generateBigNumber(20);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
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
      bigInteger = generateBigNumber(20);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testWithLargeInputs() {

  }

  @Test
  public void testGetLength() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigInteger.toString().length(), bigNumber.length());
    }
  }

  @Test
  public void testShiftLeftWithPositiveNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = getRandomDigit(false);
      bigNumber.shiftLeft(shiftBy);

      bigInteger = getBigInteger(bigInteger.toString() + "0".repeat(shiftBy));

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
                bigNumber.getDigitAt(j));
      }
      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testShiftLeftWithPositiveNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftLeft(getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testShiftLeftWithNegativeNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = -getRandomDigit(false);
      bigNumber.shiftLeft(shiftBy);

      for (int d = 0; d < Math.abs(shiftBy); d++) {
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      }

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
                bigNumber.getDigitAt(j));
      }
      assertEquals(bigInteger.toString(), bigNumber.toString());
    }
  }

  @Test
  public void testShiftLeftWithNegativeNumberWithEmptyConstructor() {
    for (int i = 0; i < 999; i++) {
      bigNumber = new BigNumberImpl();
      bigNumber.shiftLeft(-getRandomDigit(false));

      assertEquals("0", bigNumber.toString());
    }
  }

  @Test
  public void testRightShiftWithPositiveNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = getRandomDigit(false);
      bigNumber.shiftRight(shiftBy);

      for (int d = 0; d < shiftBy; d++) {
        bigInteger = bigInteger.divide(BigInteger.valueOf(10));
      }

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
                bigNumber.getDigitAt(j));
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
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int shiftBy = -getRandomDigit(false);
      bigNumber.shiftRight(shiftBy);

      bigInteger = getBigInteger(bigInteger.toString() + "0".repeat(Math.abs(shiftBy)));

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
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
  public void testAddDigit() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(10);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      int digit = getRandomDigit(true);

      bigNumber.addDigit(digit);

      bigInteger = bigInteger.add(BigInteger.valueOf(digit));

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
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

  @Test
  public void testGetDigitAtForValidPosition() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
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
  public void testCopy() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(15);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber copyBigNumber = bigNumber.copy();

      for (int j = 0; j < bigInteger.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(bigInteger.toString().charAt(j))),
                bigNumber.getDigitAt(j));
      }

      assertEquals(bigInteger.toString(), copyBigNumber.toString());
    }
  }

  @Test
  public void testAddWithFirstNumberBeTheCaller() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);
      BigInteger bigInteger1 = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString().charAt(j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithSecondNumberBeTheCaller() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);
      BigInteger bigInteger1 = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger1.add(bigInteger);

      BigNumber actualResult = bigNumber1.add(bigNumber);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString().charAt(j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithFirstNumberSizeGreaterThanSecondNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(20);
      BigInteger bigInteger1 = generateBigNumber(10);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString().charAt(j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testAddWithSecondNumberSizeGreaterThanFirstNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(10);
      BigInteger bigInteger1 = generateBigNumber(20);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1);

      for (int j = 0; j < expectedResult.toString().length(); j++) {
        assertEquals(Integer.parseInt(String.valueOf(expectedResult.toString().charAt(j))),
                actualResult.getDigitAt(j));
      }

      assertEquals(expectedResult.toString(), actualResult.toString());
    }
  }

  @Test
  public void testCompareTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(15);
      BigInteger bigInteger1 = generateBigNumber(15);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      int res = bigInteger.compareTo(bigInteger1);

      assertEquals(res, bigNumber.compareTo(bigNumber1));
    }
  }

  @Test
  public void testEqualsTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(15);
      BigInteger bigInteger1 = bigInteger;

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertEquals(bigNumber, bigNumber1);
    }
  }

  @Test
  public void testNotEqualsTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(15);
      BigInteger bigInteger1 = generateBigNumber(15);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertNotEquals(bigNumber, bigNumber1);
    }
  }

  @Test
  public void testNotEqualsTwoDifferentObjects() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber(15);

      bigNumber = new BigNumberImpl(bigInteger.toString());
      Object obj = "4322685*&^%%";

      assertNotEquals(bigNumber, obj);
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
