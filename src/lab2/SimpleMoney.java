package lab2;

/**
 * This class represents a SimpleMoney which implements Money interface.
 */
public class SimpleMoney implements Money {

  private final int dollar;
  private final int cent;

  /**
   * Constructs a money in terms of its dollar and cent values.
   *
   * @param dollar dollar value of the money
   * @param cent   cent value of the money
   * @throws IllegalArgumentException if a negative amount of money, or using a negative amount of
   *                                  dollars or cents
   */
  public SimpleMoney(int dollar, int cent) throws IllegalArgumentException {
    if (dollar < 0 || cent < 0) {
      throw new IllegalArgumentException("Negative amount of dollars or cents are not allowed.");
    }
    this.dollar = dollar;
    this.cent = cent;
  }

  @Override
  public Money add(Money other) {
    double decimalValueMoney1 = this.getDecimalValue();
    double decimalValueMoney2 = other.getDecimalValue();
    double result = decimalValueMoney1 + decimalValueMoney2;

    int intResultVal = (int) (Math.round(result * 100));
    int cents = intResultVal % 100;
    int dollar = intResultVal / 100;

    return new SimpleMoney(dollar, cents);
  }

  @Override
  public Money add(int dollars, int cents) throws IllegalArgumentException {
    if (dollars < 0 || cents < 0) {
      throw new IllegalArgumentException("Negative amount of dollars or cents are not allowed.");
    }

    double decimalValueMoney1 = this.getDecimalValue();
    double decimalValueMoney2 = dollars + (double) cents / 100;

    double result = decimalValueMoney1 + decimalValueMoney2;

    int intResultVal = (int) (Math.round(result * 100));
    int centsResult = intResultVal % 100;
    int dollarResult = intResultVal / 100;

    return new SimpleMoney(dollarResult, centsResult);
  }

  @Override
  public double getDecimalValue() {
    return dollar + (double) cent / 100;
  }

  /**
   * Overrides to return a string of the form "$xx.yy". There should be exactly two digits after the
   * decimal point, padded with leading zeroes if necessary. 7 + 22 => 7.22 or 7 + 2 => 7.02
   *
   * @return formatted string as "$xx.yy"
   */
  @Override
  public String toString() {
    double result = dollar + (double) cent / 100;
    return String.format("$%.2f", result);
  }
}
