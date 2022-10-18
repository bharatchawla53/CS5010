package bignumber;

public interface BigNumberListNode {
  /**
   * Return the number of digits in this list of BigNumber.
   *
   * @return length of this number.
   */
  int size();

  BigNumberListNode shiftLeft(int shiftBy);

  BigNumberListNode shiftRight(int shiftsBy);

  BigNumberListNode addFront(int digit);

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

  BigNumberListNode reverse();

  BigNumberListNode reverseAccumulator(BigNumberListNode accumulator);

  BigNumberListNode addSum(BigNumberListNode other, int carryOver);

  int compare(BigNumberListNode other);

  BigNumberListNode getRest();

  Boolean isNodeEmpty();

  BigNumberListNode addDigit(int digit, int carryOver);
}
