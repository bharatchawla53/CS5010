package vehicle;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOrEqualToHx() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 30, 25, 15, 45, 30, 65, 45, 80);
    } catch (IllegalArgumentException iae) {
      String message = "For each gear x, lx cannot be greater than hx";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
    fail("For each gear x, lx cannot be greater than hx exception didn't throw");
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

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOfNextLx() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 20, 25, 15, 45, 30, 65, 45, 80);
    } catch (IllegalArgumentException iae) {
      String message = "The lower speed of x should be less than x+1";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
    fail("The lower speed of x should be less than x+1 exception didn't throw");
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

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereAdjacentGearDoesNotOverlap() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 12, 25, 28, 45, 30, 65, 45, 80);
    } catch (IllegalArgumentException iae) {
      String message = "Adjacent gear ranges cannot be overlapping";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
    fail("Adjacent gear ranges cannot be overlapping exception didn't throw");
  }

}