package lab2;

/**
 * This interface represents US money and implement some functionality for it.
 */
public interface Money {

  /**
   * A method to add two money amounts.
   *
   * @param other Money to be added to another money object
   * @return sum of two money objects.
   */
  Money add(Money other);

  /**
   * A method to add a money amount with another given as a separate dollar and cent value.
   *
   * @param dollars dollar value of money
   * @param cents   cent value of money
   * @return sum of two money objects.
   */
  Money add(int dollars, int cents);

  /**
   * A method that returns the decimal value of the money in the format "xx.yy". The cent value
   * should be padded with leading zeroes if necessary (i.e. 8 dollars and 2 cents should be 8.02).
   *
   * @return a decimal value of the money
   */
  double getDecimalValue();
}
