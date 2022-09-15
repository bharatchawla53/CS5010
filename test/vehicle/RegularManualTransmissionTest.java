package vehicle;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RegularManualTransmissionTest {

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereLxShouldBeLessThanOrEqualToHx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertTrue(0 <= 10);
    assertTrue(3 <= 25);
    assertTrue(15 <= 45);
    assertTrue(30 <= 65);
    assertTrue(45 <= 80);
  }

  @Ignore // TODO after impl
  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOrEqualToHx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 30, 25, 15, 45, 30, 65, 45, 80);

  }

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereLxShouldBeLessThanOfNextLx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertTrue(0 < 3);
    assertTrue(3 < 15);
    assertTrue(15 < 30);
    assertTrue(30 < 45);
  }

  @Ignore // TODO after impl
  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOfNextLx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);
  }

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereAdjacentGearMayOverlap() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertTrue(3 > 0 && 3 < 10);
    assertTrue(15 > 3 && 15 < 25);
    assertTrue(30 > 15 && 30 < 45);
    assertTrue(45 > 30 && 45 < 65);

  }

  @Ignore  // TODO after impl
  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereAdjacentGearDoesNotOverlap() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 12, 25, 28, 45, 46, 65, 45, 80);

  }

}