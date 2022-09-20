package lab2;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link SimpleMoney}s.
 */
public class SimpleMoneyTest {

  private static int dollar = 12;
  private static int cent = 23;

  private static int dollar2 = 25;
  private static int cent2 = 12;

  private Money createSimpleMoney(int dollar, int cent) {
    return new SimpleMoney(dollar, cent);
  }

  @Test
  public void testConstructorValidMoney() {
    Money simpleMoney = createSimpleMoney(6387, 60);

    assertEquals("$6387.60", simpleMoney.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidMoney() {
    createSimpleMoney(542, -21);
  }

  @Test
  public void testAddMoney() {
    Money money1 = createSimpleMoney(dollar, cent);
    Money money2 = createSimpleMoney(dollar2, cent2);

    assertEquals("$37.35", money1.add(money2).toString());
  }

  @Test
  public void testAddMoneyFuzzyTesting() {
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      int dollar1 = random.nextInt(99999);
      int cent1 = random.nextInt(99999);
      int dollar2 = random.nextInt(99999);
      int cent2 = random.nextInt(99999);

      Money money1 = createSimpleMoney(dollar1, cent1);
      Money money2 = createSimpleMoney(dollar2, cent2);

      double expectedResult = dollar1 + (double) cent1 / 100 + dollar2 + (double) cent2 / 100;
      double actualResult = money1.add(money2).getDecimalValue();
      assertEquals(expectedResult, actualResult, 2);
    }
  }

  @Test
  public void testSecondAddMoney() {
    Money money1 = createSimpleMoney(dollar, cent);

    assertEquals("$37.35", money1.add(dollar2, cent2).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondAddMoneyInvalidMoney() {
    Money money1 = createSimpleMoney(dollar, cent);
    money1.add(42, -21);
  }
}
