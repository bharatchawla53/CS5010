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

  @Rule
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
}
