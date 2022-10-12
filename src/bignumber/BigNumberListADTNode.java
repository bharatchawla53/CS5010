package bignumber;

import java.util.function.Function;

public interface BigNumberListADTNode {
  /**
   * Return the number of digits in this list of BigNumber.
   *
   * @return length of this number.
   */
  int size();

  BigNumberListADTNode addFront(int digit);

  BigNumberListADTNode addBack(int digit);

  BigNumberListADTNode remove(int index);

  /**
   * It takes a position, and returns the digit at that position.
   *
   * @param index the specified index.
   * @return the digit at the specified position.
   * @throws IllegalArgumentException if an invalid position is passed.
   */
  int get(int index) throws IllegalArgumentException;

  BigNumberListADTNode copyOf();

  //<R> BigNumberListADTNode<R> map(Function<T,R> converter);

  /**
   * It takes a single digit and add it to this number.
   *
   * @param digit a number to be added to this number.
   * @throws IllegalArgumentException if the digit passed to it is not a single non-negative digit.
   */
  BigNumberListADTNode addDigit(int digit) throws IllegalArgumentException;

  /**
   * Given two BigNumber, it adds them and return the sum of it.
   *
   * @param other the number to be added.
   * @return the sum of two numbers.
   */
  //BigNumberListADTNode add(T other);


}
