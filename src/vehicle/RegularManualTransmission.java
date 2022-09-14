package vehicle;

/**
 * This class represents a RegularManualTransmission which implements ManualTransmission interface.
 */

public class RegularManualTransmission implements ManualTransmission {

  public RegularManualTransmission() {

  }

  @Override
  public String getStatus() {
    return null;
  }

  @Override
  public int getSpeed() {
    return 0;
  }

  @Override
  public int getGear() {
    return 0;
  }

  @Override
  public ManualTransmission increaseSpeed() {
    return null;
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    return null;
  }

  @Override
  public ManualTransmission increaseGear() {
    return null;
  }

  @Override
  public ManualTransmission decreaseGear() {
    return null;
  }
}
