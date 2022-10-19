package bignumber;

/**
 * This interface represents all operations for a node in a list of BigNumber implemented as an
 * ADT.
 */
public interface BigNumberListNode {

  /**
   * Return the number of digits in this list of BigNumber.
   *
   * @return length of this number.
   */
  int size();

  /**
   * Shifts the existing list of numbers by adding a new node with 0 to the front and return this
   * modified list.
   *
   * @param shiftBy a number to be used to left shift this number by.
   * @return the head of the resulting list.
   */
  BigNumberListNode shiftLeft(int shiftBy);

  /**
   * Shifts the existing list of numbers by removing the head node and return this modified list.
   *
   * @param shiftsBy a number to be used to right shift this number by.
   * @return the head of the resulting list.
   */
  BigNumberListNode shiftRight(int shiftsBy);

  /**
   * Add the given digit to the front of this list and return this modified list.
   *
   * @param digit the book to be added
   * @return the head of the resulting list.
   */
  BigNumberListNode addFront(int digit);

  /**
   * Get the digit at the specified index, with 0 meaning the first number in this list.
   *
   * @param index the specified index.
   * @return the digit at the specified index.
   * @throws IllegalArgumentException if an invalid index is passed.
   */
  int get(int index) throws IllegalArgumentException;

  /**
   * It gets the independent copy of this list.
   *
   * @return the head of the resulting new list.
   */
  BigNumberListNode copyOf();

  /**
   * Return an independent, but reversed version of this list.
   *
   * @return the reverse of this list.
   */
  BigNumberListNode reverse();

  /**
   * Returns a list of BigNumberListNode that is already reversed.
   *
   * @param accumulator the list that is already reversed.
   * @return an accumulated reverse list.
   */
  BigNumberListNode reverseAccumulator(BigNumberListNode accumulator);

  /**
   * Returns a new list with the result of sum of two lists.
   *
   * @param other     the list that needs to be added.
   * @param carryOver an int to track carryovers after two nodes values are added.
   * @return the head of the resulting new list.
   */
  BigNumberListNode addSum(BigNumberListNode other, int carryOver);

  /**
   * It compares each node of this BigNumberListNode to the other BigNumberListNode to determine
   * which number is bigger, smaller or equal to.
   *
   * @param other the number to be compared with.
   * @return 1 if the current node is greater than the other node, -1 if the current node is less
   *         than the other node, and 0 if two nodes are equal.
   */
  int compare(BigNumberListNode other);

  /**
   * It returns the rest of the head of the list.
   *
   * @return rest of the head of the list.
   */
  BigNumberListNode getRest();

  /**
   * Checks if the list is empty.
   *
   * @return true if the list is empty, false otherwise.
   */
  Boolean isNodeEmpty();

  /**
   * Returns a list with this new added digit to the number.
   *
   * @param digit     digit to be added.
   * @param carryOver an int to track carryovers after two nodes values are added.
   * @return the head of the resulting list.
   */
  BigNumberListNode addDigit(int digit, int carryOver);

  String toStringHelper(StringBuilder stringAcc);

  //<R> BigNumberListADTNode map(Function<BigNumberListADTNode,R> converter);
}
