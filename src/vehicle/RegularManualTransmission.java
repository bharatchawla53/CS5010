package vehicle;

/**
 * This class represents a RegularManualTransmission which implements ManualTransmission interface.
 */

public class RegularManualTransmission implements ManualTransmission {

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
  public RegularManualTransmission(int l1, int h1, int l2, int h2, int l3, int h3, int l4, int h4, int l5, int h5) throws IllegalArgumentException {

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
