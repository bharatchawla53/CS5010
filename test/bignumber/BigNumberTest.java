package bignumber;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

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

  }

  @Test
  public void testShiftLeftWithNegativeNumber() {

  }

  @Test
  public void testRightShiftWithPositiveNumber() {

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

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDigitAtForInvalidPosition() {

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

  }
}
