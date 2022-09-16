package vehicle;

/**
 * This class represents a RegularManualTransmission which implements ManualTransmission interface.
 */

public class RegularManualTransmission implements ManualTransmission {

  private final int l1;
  private final int h1;
  private final int l2;
  private final int h2;
  private final int l3;
  private final int h3;
  private final int l4;
  private final int h4;
  private final int l5;
  private final int h5;
  private ManualTransmission manualTransmission;
  private String status;
  private int speed;
  private int gear;

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

    this.l1 = l1;
    this.h1 = h1;
    this.l2 = l2;
    this.h2 = h2;
    this.l3 = l3;
    this.h3 = h3;
    this.l4 = l4;
    this.h4 = h4;
    this.l5 = l5;
    this.h5 = h5;

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
