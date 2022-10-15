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

  BigNumberListNode reverse();

  BigNumberListNode reverseAccumulator(BigNumberListNode accumulator);

  BigNumberListNode sum(BigNumberListNode other);

  BigNumberListNode sumAccumulator(BigNumberListNode other, int carryOver, int indexIncrementer);

  int compare(BigNumber other, int indexIncrementer);
}
