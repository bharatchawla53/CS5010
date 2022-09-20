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
   * This constructor that takes the speed ranges for the 5 gears as two integral numbers each: low
   * and high. The transmission supports exactly 5 gears, numbered 1 through 5. Gear 1 has the
   * lowest speed range.
   *
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
   * @throws IllegalArgumentException if for each gear x, lx is greater than hx, the lower speed of
   *                                  x is greater than x+1, and adjacent gears ranges are
   *                                  non-overlapping.
   */
  public RegularManualTransmission(int l1, int h1, int l2, int h2, int l3, int h3, int l4, int h4,
                                   int l5, int h5) throws IllegalArgumentException {

    if (!(l1 <= h1 && l2 <= h2 && l3 <= h3 && l4 <= h4 && l5 <= h5)) {
      throw new IllegalArgumentException("For each gear x, lx cannot be greater than hx");
    } else if (!(l1 < l2 && l2 < l3 && l3 < l4 && l4 < l5)) {
      throw new IllegalArgumentException("The lower speed of x should be less than x+1");
    } else if (l2 > h1 || l3 > h2 || l4 > h3 || l5 > h4) {
      throw new IllegalArgumentException("Adjacent gear ranges cannot be non-overlapping");
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
      //checks which range the current speed lies in
      //and if i also matches the currentGear since currentSpeed could overlap
      //if they are adjacent
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]
              && i == 2 * (currentGear - 1)) {
        int newSpeed = currentSpeed + 1;
        //this check to prevent out of bounds exception
        if (i < 8) {
          //checks if newSpeed is in next gear interval
          if (newSpeed >= speedRanges[i + 2] && newSpeed <= speedRanges[i + 1]) {
            message = "OK: you may increase the gear.";
            updatedSpeed = newSpeed;
          } else if (newSpeed >= speedRanges[i + 1]) { //reached maxSpeed for the currentGear
            message = "Cannot increase speed, increase gear first.";
            updatedSpeed = currentSpeed;
          } else { //speed was changed successfully
            message = "OK: everything is OK.";
            updatedSpeed = newSpeed;
          }
          break;
        } else if (newSpeed <= speedRanges[i + 1]) { //case for Gear 5 and check if its less than h5
          message = "OK: everything is OK.";
          updatedSpeed = newSpeed;
        }
      } else if (currentSpeed >= speedRanges[speedRanges.length - 1]) {
        //reached last gear max speed
        message = "Cannot increase speed. Reached maximum speed.";
        updatedSpeed = currentSpeed;
        break;
      }
    }

    return new RegularManualTransmission(message, updatedSpeed, currentGear, speedRanges);
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    String message = null;
    int updatedSpeed = 0;

    for (int i = 0; i < speedRanges.length - 1; i += 2) {
      //checks which range the current speed lies in
      //and if i also matches the currentGear since currentSpeed could overlap
      //if they are adjacent
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]
              && i == 2 * (currentGear - 1)) {
        int newSpeed = currentSpeed - 1;

        //this check to prevent out of bounds exception
        if (i - 2 >= 0) {
          if (newSpeed < speedRanges[i]) { //case need to decrease the gear first
            message = "Cannot decrease speed, decrease gear first.";
            updatedSpeed = currentSpeed;
          } else if (newSpeed >= speedRanges[i - 2] && newSpeed <= speedRanges[i - 1]) {
            //checks if newSpeed is in previous gear interval
            message = "OK: you may decrease the gear.";
            updatedSpeed = newSpeed;
          } else { //speed was changed successfully
            message = "OK: everything is OK.";
            updatedSpeed = newSpeed;
          }
        } else if (newSpeed < speedRanges[0]) { // reached first gear min speed
          message = "Cannot decrease speed. Reached minimum speed.";
          updatedSpeed = currentSpeed;
        } else { // speed was changed successfully for gear 1
          message = "OK: everything is OK.";
          updatedSpeed = newSpeed;
        }
        break;
      }
    }

    return new RegularManualTransmission(message, updatedSpeed, currentGear, speedRanges);
  }

  @Override
  public ManualTransmission increaseGear() {
    String message = null;
    int updatedGear = 0;

    for (int i = 0; i < speedRanges.length - 1; i += 2) {
      //checks which range the current speed lies in
      //and if i also matches the currentGear since currentSpeed could overlap
      //if they are adjacent
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]
              && i == 2 * (currentGear - 1)) {
        int newGear = currentGear + 1;

        if (newGear > GearEnum.GEAR_5.getGearValue()) { // case newGear exceeds the maxGear
          message = "Cannot increase gear. Reached maximum gear.";
          updatedGear = currentGear;
        } else if (currentSpeed < speedRanges[i + 1] && currentSpeed < speedRanges[i + 2]) {
          //check if current speed is less than hx speed for the currentGear interval
          //and currentSpeed is less than adjacent gear lx
          message = "Cannot increase gear, increase speed first.";
          updatedGear = currentGear;
        } else { // gear was successfully changed
          message = "OK: everything is OK.";
          updatedGear = newGear;
        }
        break;
      }
    }

    return new RegularManualTransmission(message, currentSpeed, updatedGear, speedRanges);
  }

  @Override
  public ManualTransmission decreaseGear() {
    String message = null;
    int updatedGear = 0;

    for (int i = 0; i < speedRanges.length - 1; i += 2) {
      //checks which range the current speed lies in
      //and if i also matches the currentGear since currentSpeed could overlap
      //if they are adjacent
      if (currentSpeed >= speedRanges[i] && currentSpeed <= speedRanges[i + 1]
              && i == 2 * (currentGear - 1)) {
        int newGear = currentGear - 1;

        // this check to prevent out of bounds
        if (i == 0) {
          if (newGear < GearEnum.GEAR_1.getGearValue()) { // newGear is less than minGear
            message = "Cannot decrease gear. Reached minimum gear.";
            updatedGear = currentGear;
          }
        } else {
          if (currentSpeed > speedRanges[i - 1] && currentSpeed > speedRanges[i - 2]) {
            //check if current speed is greater than hx speed for the gear
            //and currentSpeed is greater than adjacent gear lx
            message = "Cannot decrease gear, decrease speed first.";
            updatedGear = currentGear;
          } else { //gear was successfully changed
            message = "OK: everything is OK.";
            updatedGear = newGear;
          }
        }
        break;
      }
    }

    return new RegularManualTransmission(message, currentSpeed, updatedGear, speedRanges);
  }

}
