package bignumber;

public interface BigNumberListNode {
  /**
   * Return the number of digits in this number.
   *
   * @return length of this number.
   */
  int length();

  /**
   * Given the argument, it shifts this number to the left by that number. If a negative number of
   * left-shifts is given, it will correspond to those many right shifts.
   *
   * @param shiftsBy a number to be used to shift this number by.
   */
  BigNumberListNode shiftLeft(int shiftsBy);

  /**
   * Given the argument, it shifts this number to the right by that number. If the number is 0, it
   * can be right-shifted any positive n times, yielding the same number 0. But a negative number of
   * right-shifts will correspond to those many left-shifts.
   *
   * @param shiftsBy a number to be used to shift this number by.
   */
  BigNumberListNode shiftRight(int shiftsBy);

  /**
   * It takes a single digit and add it to this number.
   *
   * @param digit a number to be added to this number.
   * @throws IllegalArgumentException if the digit passed to it is not a single non-negative digit.
   */
  BigNumberListNode addDigit(int digit) throws IllegalArgumentException;

  /**
   * It takes a position, and returns the digit at that position.
   *
   * @param position the specified index.
   * @return the digit at the specified position.
   * @throws IllegalArgumentException if an invalid position is passed.
   */
  int getDigitAt(int position) throws IllegalArgumentException;

  /**
   * Return an identical and independent copy of this number.
   *
   * @return the copy of this number.
   */
  BigNumber copy();

  /**
   * Given two BigNumber, it adds them and return the sum of it.
   *
   * @param other the number to be added.
   * @return the sum of two numbers.
   */
  BigNumber add(BigNumber other);

  BigNumberListNode addFront(int digit);

  BigNumberListNode addBack(int digit);
}
