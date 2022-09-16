package vehicle;

/**
 * This enum represents the values for each gear required for a manual transmission.
 */
public enum GearEnum {
  GEAR_1(1),
  GEAR_2(2),
  GEAR_3(3),
  GEAR_4(4),
  GEAR_5(5);

  private final int gear;

  public int getGearValue() {
    return gear;
  }

  GearEnum(int gear) {
    this.gear = gear;
  }
}

