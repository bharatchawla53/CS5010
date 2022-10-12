package bignumber;

import java.util.function.Function;

public interface ListADT<T> {
  /**
   * Return the number of digits in this list of BigNumber.
   *
   * @return length of this number.
   */
  int size();

  ListADT<T> addFront(int digit);

  ListADT<T> addBack(int digit);

  ListADT<T> remove(int index);

  /**
   * It takes a position, and returns the digit at that position.
   *
   * @param index the specified index.
   * @return the digit at the specified position.
   * @throws IllegalArgumentException if an invalid position is passed.
   */
  ListADT<T> get(int index) throws IllegalArgumentException;

  ListADT<T> copyOf();

  <R> ListADT<R> map(Function<T,R> converter);

  /**
   * It takes a single digit and add it to this number.
   *
   * @param digit a number to be added to this number.
   * @throws IllegalArgumentException if the digit passed to it is not a single non-negative digit.
   */
  ListADT<T> addDigit(int digit) throws IllegalArgumentException;

  /**
   * Given two BigNumber, it adds them and return the sum of it.
   *
   * @param other the number to be added.
   * @return the sum of two numbers.
   */
  ListADT<T> add(T other);


}
