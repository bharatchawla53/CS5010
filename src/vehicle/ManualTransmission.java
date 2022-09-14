package vehicle;

/**
 * This interface represents a set of operations on any manual transmission. A manual transmission
 * is a transmission that requires the driver to change gears as they are changing the speed.
 */

public interface ManualTransmission {

  /**
   * Return the status of this transmission as a String without any additional parameters.
   *
   * @return the status of this transmission as a string.
   */
  String getStatus();

  /**
   * Get the current speed of the vehicle as a whole number.
   *
   * @return the current speed of the vehicle.
   */
  int getSpeed();

  /**
   * Get the current gear of the vehicle as a whole number.
   *
   * @return the current gear of the vehicle.
   */
  int getGear();

  /**
   * Increase the speed by a fixed amount without changing gears and return the resulting
   * transmission object. If the speed cannot be increased, then it should return an object with the
   * same speed as before. The speed change amount is up to the implementation and is not an
   * argument of this method.
   *
   * @return {@link ManualTransmission} object with updated or previous speed.
   */
  ManualTransmission increaseSpeed();

  /**
   * Decrease the speed by a fixed amount without changing gears and return the resulting
   * transmission object. If the speed cannot be decreased, then it should return an object with the
   * same speed as before. The speed change amount is up to the implementation and is not an
   * argument of this method.
   *
   * @return {@link ManualTransmission} object with updated or previous speed.
   */
  ManualTransmission decreaseSpeed();

  /**
   * Increase the gear by one without changing speed and return the resulting transmission object.
   * If the gear cannot be increased, then it should return an object with the same gear as before.
   *
   * @return {@link ManualTransmission} object with updated or previous gear.
   */
  ManualTransmission increaseGear();

  /**
   * Decrease the gear by one without changing speed and return the resulting transmission object.
   * If the gear cannot be decreased, then it should return an object with the same gear as before.
   *
   * @return {@link ManualTransmission} object with updated or previous gear.
   */
  ManualTransmission decreaseGear();
}
