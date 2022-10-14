package bignumber;

import org.junit.Before;
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

  //@Rule
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
      bigInteger = generateBigNumber();
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
      bigInteger = generateBigNumber();
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
      bigInteger = generateBigNumber();
      bigNumber = new BigNumberImpl(bigInteger.toString());

      assertEquals(bigInteger.toString().length(), bigNumber.length());
    }
  }

  @Test
  public void testShiftLeftWithPositiveNumber() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();

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
      bigInteger = generateBigNumber();

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
      bigInteger = generateBigNumber();

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
      bigInteger = generateBigNumber();

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
      bigInteger = generateBigNumber();

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
    bigInteger = generateBigNumber();

    bigNumber = new BigNumberImpl(bigInteger.toString());
    int digit = -getRandomDigit(true);

    bigNumber.addDigit(digit);
  }

  @Test
  public void testGetDigitAtForValidPosition() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();

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
    bigInteger = generateBigNumber();
    bigNumber = new BigNumberImpl(bigInteger.toString());
    bigNumber.getDigitAt(-4);
  }

  @Test
  public void testCopy() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();

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
  public void testAdd() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();
      BigInteger bigInteger1 = generateBigNumber();

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      BigInteger expectedResult = bigInteger.add(bigInteger1);

      BigNumber actualResult = bigNumber.add(bigNumber1); // TODO test for other way around for calling the object

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
      bigInteger = generateBigNumber();
      BigInteger bigInteger1 = generateBigNumber();

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertEquals(1, bigNumber.compareTo(bigNumber1));
    }
  }

  @Test
  public void testEqualsTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();
      BigInteger bigInteger1 = bigInteger;

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertEquals(bigNumber, bigNumber1);
    }
  }

  @Test
  public void testNotEqualsTwoBigNumbers() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();
      BigInteger bigInteger1 = generateBigNumber();

      bigNumber = new BigNumberImpl(bigInteger.toString());
      BigNumber bigNumber1 = new BigNumberImpl(bigInteger1.toString());

      assertNotEquals(bigNumber, bigNumber1);
    }
  }

  @Test
  public void testNotEqualsTwoDifferentObjects() {
    for (int i = 0; i < 999; i++) {
      bigInteger = generateBigNumber();

      bigNumber = new BigNumberImpl(bigInteger.toString());
      Object obj = "4322685*&^%%";

      assertNotEquals(bigNumber, obj);
    }
  }

  // TODO test to create a number by left shifting as listed in the description

  private BigInteger generateBigNumber() {
    StringBuilder bigInteger = new StringBuilder();

    for (int i = 0; i < 20; i++) {
      bigInteger.append(getRandomDigit(true));
    }

    return getBigInteger(bigInteger.toString());
  }

  private int getRandomDigit(boolean includeZero) {
    return includeZero ? random.nextInt(10) : random.nextInt(10) + 1;
  }

  private BigInteger getBigInteger(String num) {
    return new BigInteger(num);
  }
}
