package vehicle;

/**
 * This class represents a RegularManualTransmission which implements ManualTransmission interface.
 */

public class RegularManualTransmission implements ManualTransmission {

  private final int[] speedRanges;
  private String currentStatus;
  private int currentSpeed;
  private int currentGear;

  /**
   * @param l1 low speed for gear 1.
   * @param h1 high speed for gear 1.
   * @param l2 low speed for gear 2.
   * @param h2 high speed for gear 2.
   * @param l3 low speed for gear 3.
   * @param h3 high speed for gear 3.
   * @param l4 low speed for gear 4.
   * @param h4 high speed for gear 4.
   * @param l5 low speed for gear 5.
   * @param h5 high speed for gear 5.
   */
  public RegularManualTransmission(int l1, int h1, int l2, int h2, int l3, int h3, int l4, int h4, int l5, int h5)
          throws IllegalArgumentException {

    if (!(l1 <= h1 && l2 <= h2 && l3 <= h3 && l4 <= h4 && l5 <= h5)) {
      throw new IllegalArgumentException("For each gear x, lx cannot be greater than hx");
    } else if (!(l1 < l2 && l2 < l3 && l3 < l4 && l4 < l5)) {
      throw new IllegalArgumentException("The lower speed of x should be less than x+1");
    } else if (l2 > h1 || l3 > h2 || l4 > h3 || l5 > h4) {
      throw new IllegalArgumentException("Adjacent gear ranges cannot be overlapping");
    }

    this.speedRanges = new int[]{l1, h1, l2, h2, l3, h3, l4, h4, l5, h5};
    // Initialize initial values for status, gear, and speed.
    this.currentGear = GearEnum.GEAR_1.getGearValue();
    this.currentSpeed = 0;
    this.currentStatus = "OK: everything is OK.";
  }

  /**
   * Constructs a RegularManualTransmission object with updated status, speed, gear, and speed
   * ranges.
   *
   * @param status Return the current status of this transmission as a String.
   * @param speed  Return the current speed of the vehicle.
   * @param gear   Return the current gear of the vehicle.
   */
  private RegularManualTransmission(String status, int speed, int gear, int[] speedRanges) {
    this.currentStatus = status;
    this.currentSpeed = speed;
    this.currentGear = gear;
    this.speedRanges = speedRanges;
  }

  @Override
  public String getStatus() {
    return currentStatus;
  }

  @Override
  public int getSpeed() {
    return currentSpeed;
  }

  @Override
  public int getGear() {
    return currentGear;
  }

  @Override
  public ManualTransmission increaseSpeed() {
    String message = null;
    int updatedSpeed = 0;

    for (int i = 0; i < speedRanges.length - 1; i += 2) {
      // checks if its in range between low and high
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]) {
        int newSpeed = currentSpeed + 1;
        if (newSpeed >= speedRanges[i + 2] && newSpeed <= speedRanges[i+1]) {
          //check if it overlaps with next one and  new speed is next range,
          // we need to let them know to change gear
          message = "OK: you may increase the gear.";
          updatedSpeed = newSpeed;
        } else if (newSpeed >= speedRanges[i + 1]) {
          // case need to increase the gear first
          message = "Cannot increase speed, increase gear first.";
          updatedSpeed = currentSpeed;
        } else {
          message = "OK: everything is OK.";
          updatedSpeed = newSpeed;
        }
        break;
      } else if (currentSpeed > speedRanges[speedRanges.length - 1]) {
        // reached last gear
        message = "Cannot increase speed. Reached maximum speed.";
        updatedSpeed = currentSpeed;
        break;
      }
    }

    return new RegularManualTransmission(message, updatedSpeed, currentGear,speedRanges);
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    String message = null;
    int updatedSpeed = 0;

    for (int i = 0; i < speedRanges.length - 1; i+=2) {
      // checks if its in range between low and high
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]) {
        int newSpeed = currentSpeed - 1;

        // TODO clarify logic for checking decreasing values
        if (i - 2 > 0) {
          if (newSpeed >= speedRanges[i-2] && newSpeed <= speedRanges[i-1]) {
            //check if it overlaps with next one and  new speed is next range,
            // we need to let them know to change gear
            message = "OK: you may decrease the gear.";
            updatedSpeed = newSpeed;
          } else if (newSpeed <= speedRanges[i-1]) {
            // case need to increase the gear first
            message = "Cannot decrease speed, decrease gear first.";
            updatedSpeed = currentSpeed;
          } else {
            message = "OK: everything is OK.";
            updatedSpeed = newSpeed;
          }
        } else if (newSpeed < speedRanges[0]){
          // special case ??
          // reached first gear
          message = "Cannot decrease speed. Reached minimum speed.";
          updatedSpeed = currentSpeed;
        } else {
          message = "OK: everything is OK.";
          updatedSpeed = newSpeed;
        }
        break;
      } else if (currentSpeed < speedRanges[0]) {
        // reached first gear
        message = "Cannot decrease speed. Reached minimum speed.";
        updatedSpeed = currentSpeed;
        break;
      }
    }

    return new RegularManualTransmission(message, updatedSpeed, currentGear, speedRanges);
  }

  @Override
  public ManualTransmission increaseGear() {
   /* if (isIncreaseOfGearAllowed()) {
      // cannot exceed gear 5
      if (!(gear + 1 > GearEnum.GEAR_5.getGearValue())) {
        //gear = gear + 1;
        // it means gear was changed successfully without changing speed.
        return new RegularManualTransmission("OK: everything is OK.", speed, gear + 1, speedRanges);
      }
    }*/


    return null;

  }

  @Override
  public ManualTransmission decreaseGear() {
    /*if (isDecreasedOfGearAllowed()) {
      // cannot exceed gear 5
      if (!(gear - 1 < GearEnum.GEAR_1.getGearValue())) {
        gear = gear - 1;
      }
    }*/
    return null;

  }

}
