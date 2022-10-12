package bignumber;

public interface BigNumberListNode {
  /**
   * Return the number of digits in this list of BigNumber.
   *
   * @return length of this number.
   */
  int size();

  BigNumberListNode addFront(int digit);

  BigNumberListNode addBack(int digit);

  BigNumberListNode remove(int index);

  /**
   * It takes a position, and returns the digit at that position.
   *
   * @param index the specified index.
   * @return the digit at the specified position.
   * @throws IllegalArgumentException if an invalid position is passed.
   */
  int get(int index) throws IllegalArgumentException;

  BigNumberListNode copyOf();

  //<R> BigNumberListADTNode map(Function<BigNumberListADTNode,R> converter);

  /**
   * It takes a single digit and add it to this number.
   *
   * @param digit a number to be added to this number.
   * @throws IllegalArgumentException if the digit passed to it is not a single non-negative digit.
   */
  BigNumberListNode sum(int digit) throws IllegalArgumentException;

  BigNumberListNode sumAccumulator(int digit, int carryOver);

  /**
   * Given two BigNumber, it adds them and return the sum of it.
   *
   * @param other the number to be added.
   * @return the sum of two numbers.
   */
  //BigNumberListADTNode add(T other);

  BigNumberListNode reverse();

  BigNumberListNode reverseAccumulator(BigNumberListNode accumulator);

}
