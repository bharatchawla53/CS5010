package vehicle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static vehicle.GearEnum.GEAR_1;
import static vehicle.GearEnum.GEAR_2;
import static vehicle.GearEnum.GEAR_5;

/**
 * A JUnit test class for the RegularManualTransmission class.
 */

public class RegularManualTransmissionTest {

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereLxShouldBeLessThanOrEqualToHx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertEquals(GEAR_1.getGearValue(), rmt.getGear());
    assertEquals(0, rmt.getSpeed());
    assertEquals("OK: everything is OK.", rmt.getStatus());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOrEqualToHx() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 30, 25, 15, 45, 30, 65, 45, 80);
      fail("For each gear x, lx cannot be greater than hx exception didn't throw");
    } catch (IllegalArgumentException iae) {
      String message = "For each gear x, lx cannot be greater than hx";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
  }

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereLxShouldBeLessThanOfNextLx() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertEquals(GEAR_1.getGearValue(), rmt.getGear());
    assertEquals(0, rmt.getSpeed());
    assertEquals("OK: everything is OK.", rmt.getStatus());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereLxIsNotLessThanOfNextLx() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 20, 25, 15, 45, 30, 65, 45, 80);
      fail("The lower speed of x should be less than x+1 exception didn't throw");
    } catch (IllegalArgumentException iae) {
      String message = "The lower speed of x should be less than x+1";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
  }

  @Test
  public void testRegularManualTransmissionConstructorCreationWhereAdjacentGearMayOverlap() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertEquals(GEAR_1.getGearValue(), rmt.getGear());
    assertEquals(0, rmt.getSpeed());
    assertEquals("OK: everything is OK.", rmt.getStatus());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegularManualTransmissionConstructorCreationWhereAdjacentGearDoesNotOverlap() {
    try {
      RegularManualTransmission rmt = new
              RegularManualTransmission(0, 10, 12, 25, 28, 45, 30, 65, 45, 80);
      fail("Adjacent gear ranges cannot be overlapping exception didn't throw");
    } catch (IllegalArgumentException iae) {
      String message = "Adjacent gear ranges cannot be overlapping";
      assertEquals(message, iae.getMessage());
      throw iae;
    }
  }

  @Test
  public void testGetters() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    assertEquals(0, rmt.getSpeed());
    assertEquals(GEAR_1.getGearValue(), rmt.getGear());
    assertEquals("OK: everything is OK.", rmt.getStatus());
  }

  @Test
  public void testIncreaseSpeedEverythingIsOk() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(1, mt.getSpeed());
    assertEquals("OK: everything is OK.", mt.getStatus());
  }

  @Test
  public void testIncreaseSpeedMayIncreaseTheGear() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 1, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(1, mt.getSpeed());
    assertEquals("OK: you may increase the gear.", mt.getStatus());
  }

  @Test
  public void testIncreaseSpeedIncreaseTheGearFirst() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);
    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 10; i++) {
      mt = mt.increaseSpeed();
    }

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(10, mt.getSpeed());
    assertEquals("Cannot increase speed, increase gear first.", mt.getStatus());
  }

  @Test
  public void testIncreaseSpeedReachedMaxSpeed() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 5, 3, 10, 8, 20, 16, 30, 22, 35);
    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 35; i++) {
      mt = mt.increaseSpeed();
      if (mt.getSpeed() == 5 || mt.getSpeed() == 10 || mt.getSpeed() == 20 || mt.getSpeed() == 30) {
        mt = mt.increaseGear();
      }
    }

    assertEquals(GEAR_5.getGearValue(), mt.getGear());
    assertEquals(35, mt.getSpeed());
    assertEquals("Cannot increase speed. Reached maximum speed.", mt.getStatus());
  }

  @Test
  public void testDecreaseSpeedEverythingIsOk() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 4; i++) {
      mt = mt.increaseSpeed();
    }

    mt = mt.decreaseSpeed();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(4, mt.getSpeed());
    assertEquals("OK: everything is OK.", mt.getStatus());
  }

  @Test
  public void testDecreaseSpeedMayDecreaseTheGear() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 10; i++) {
      mt = mt.increaseSpeed();
      if (mt.getSpeed() == 10) {
        mt = mt.increaseGear();
      }
    }

    mt = mt.decreaseSpeed();

    assertEquals(GEAR_2.getGearValue(), mt.getGear());
    assertEquals(10, mt.getSpeed());
    assertEquals("OK: you may decrease the gear.", mt.getStatus());
  }

  @Test
  public void testDecreaseSpeedMayDecreaseTheGear_2() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 15, 10, 25, 15, 35, 25, 50, 40, 65);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 49; i++) {
      mt = mt.increaseSpeed();
      if (mt.getSpeed() == 15 || mt.getSpeed() == 25 || mt.getSpeed() == 35
              || mt.getSpeed() == 50) {
        mt = mt.increaseGear();
      }
    }

    mt = mt.decreaseSpeed();
    //mt = mt.decreaseSpeed();

    assertEquals(GEAR_5.getGearValue(), mt.getGear());
    assertEquals(65, mt.getSpeed());
    assertEquals("OK: you may decrease the gear.", mt.getStatus());
  }

  @Test
  public void testDecreaseSpeedDecreaseTheGearFirst() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 10; i++) {
      mt = mt.increaseSpeed();
      if (mt.getSpeed() == 10) {
        mt = mt.increaseGear();
      }
    }

    for (int i = 0; i < 2; i++) {
      mt = mt.decreaseSpeed();
    }

    assertEquals(GEAR_2.getGearValue(), mt.getGear());
    assertEquals(10, mt.getSpeed());
    assertEquals("Cannot decrease speed, decrease gear first.", mt.getStatus());
  }

  @Test
  public void testDecreaseSpeedReachedMinSpeed() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 4; i++) {
      mt = mt.increaseSpeed();
    }

    for (int i = 0; i < 6; i++) {
      mt = mt.decreaseSpeed();
    }

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(0, mt.getSpeed());
    assertEquals("Cannot decrease speed. Reached minimum speed.", mt.getStatus());
  }

  @Test
  public void testIncreaseGearEverythingIsOk() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 10; i++) {
      mt = mt.increaseSpeed();
    }

    mt = mt.increaseGear();

    assertEquals(GEAR_2.getGearValue(), mt.getGear());
    assertEquals(10, mt.getSpeed());
    assertEquals("OK: everything is OK.", mt.getStatus());
  }

  @Test
  public void testIncreaseGearReachMaxGear() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 46, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 66; i++) {
      mt = mt.increaseSpeed();
      if (mt.getSpeed() == 10 || mt.getSpeed() == 25 || mt.getSpeed() == 45
              || mt.getSpeed() == 65) {
        mt = mt.increaseGear();
      }
    }

    mt = mt.increaseGear();

    assertEquals(GEAR_5.getGearValue(), mt.getGear());
    assertEquals(67, mt.getSpeed());
    assertEquals("Cannot increase gear. Reached maximum gear.", mt.getStatus());
  }

  @Test
  public void testIncreaseGearIncreaseSpeedFirst() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    mt = mt.increaseSpeed();
    mt = mt.increaseGear();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(2, mt.getSpeed());
    assertEquals("Cannot increase gear, increase speed first.", mt.getStatus());
  }

  @Test
  //Attempting to unsuccessfully increase gear from 2 at speed 10 but status is incorrect.
  // expected:<[Cannot increase gear, increase speed first].> but was:<[OK: everything is OK].

  //
  public void testIncreaseGearIncreaseSpeedFirst_2() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 12, 10, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 7; i++) {
      mt = mt.increaseSpeed();
    }

    mt = mt.increaseGear();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(8, mt.getSpeed());
    assertEquals("Cannot increase gear, increase speed first.", mt.getStatus());
  }

  @Test
  public void testDecreaseGearEverythingIsOk() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 5; i++) {
      mt = mt.increaseSpeed();
      if (i == 4) {
        mt = mt.increaseGear();
      }
    }
    mt = mt.decreaseGear();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(6, mt.getSpeed());
    assertEquals("OK: everything is OK.", mt.getStatus());
  }

  @Test
  public void testDecreaseGearDecreaseSpeedFirst() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 10; i++) {
      mt = mt.increaseSpeed();
    }

    mt = mt.increaseGear();
    mt = mt.increaseSpeed();
    mt = mt.decreaseGear();

    assertEquals(GEAR_2.getGearValue(), mt.getGear());
    assertEquals(11, mt.getSpeed());
    assertEquals("Cannot decrease gear, decrease speed first.", mt.getStatus());
  }

  @Test
  public void testDecreaseGearReachMinGear() {
    RegularManualTransmission rmt = new
            RegularManualTransmission(0, 10, 3, 25, 15, 45, 30, 65, 45, 80);

    ManualTransmission mt = rmt.increaseSpeed();
    for (int i = 0; i < 5; i++) {
      mt = mt.increaseSpeed();
      if (i == 4) {
        mt = mt.increaseGear();
        mt = mt.decreaseGear();
      }
    }
    mt = mt.decreaseGear();

    assertEquals(GEAR_1.getGearValue(), mt.getGear());
    assertEquals(6, mt.getSpeed());
    assertEquals("Cannot decrease gear. Reached minimum gear.", mt.getStatus());
  }

}