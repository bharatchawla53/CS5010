package bignumber;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BigNumberTest {

  private BigNumber bigNumber;

  //@Rule
  public Timeout timeout = new Timeout(3000, TimeUnit.MILLISECONDS);

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
    bigNumber = new BigNumberImpl("34456678908765");

    assertEquals("34456678908765", bigNumber.toString());
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

  }

  @Test
  public void testWithLargeInputs() {

  }

  @Test
  public void testGetLength() {
    String num = "34456678908765";
    bigNumber = new BigNumberImpl(num);

    assertEquals(num.length(), bigNumber.length());
  }

  @Test
  public void testShiftLeftWithPositiveNumber() {
    bigNumber = new BigNumberImpl("34456678908765");
    bigNumber.shiftLeft(3);

    assertEquals("34456678908765000", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithPositiveNumberWithEmptyConstructor() {
    bigNumber = new BigNumberImpl();
    bigNumber.shiftLeft(3);

    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithNegativeNumber() {

  }

  @Test
  public void testShiftLeftWithNegativeNumberWithEmptyConstructor() {

  }

  @Test
  public void testRightShiftWithPositiveNumber() {
    bigNumber = new BigNumberImpl("34456678908765");
    bigNumber.shiftRight(3);

    assertEquals("34456678908", bigNumber.toString());
  }

  @Test
  public void testRightShiftWithNegativeNumber() {

  }

  @Test
  public void testRightShiftWithNumberZeroWhichYieldsSameResult() {

  }

  @Test
  public void testAddDigit() {

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNegativeDigit() {

  }

  @Test
  public void testGetDigitAtForValidPosition() {
    bigNumber = new BigNumberImpl("34456678908765");
    int digit = bigNumber.getDigitAt(5);


    assertEquals(6, digit);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDigitAtForInvalidPosition() {
    bigNumber = new BigNumberImpl();
    bigNumber.getDigitAt(-4);
  }

  @Test
  public void testCopy() {

  }

  @Test
  public void testAdd() {

  }

  @Test
  public void testCompareTwoBigNumbers() {

  }

  @Test
  public void testEqualsTwoBigNumbers() {
    bigNumber = new BigNumberImpl("345690");
    BigNumber bigNumber1 = new BigNumberImpl("345690");

    assertEquals(bigNumber, bigNumber1);
  }

  @Test
  public void testNotEqualsTwoBigNumbers() {
    bigNumber = new BigNumberImpl("345690");
    BigNumber bigNumber1 = new BigNumberImpl("345690435");

    assertNotEquals(bigNumber, bigNumber1);
  }

  @Test
  public void testNotEqualsTwoDifferentObjects() {
    bigNumber = new BigNumberImpl("345690");
    Object obj = "4322";

    assertNotEquals(bigNumber, obj);
  }

  // TODO test to create a number by left shifting as listed in the description
}
