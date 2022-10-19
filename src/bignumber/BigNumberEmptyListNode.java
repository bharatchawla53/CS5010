package bignumber;

/**
 * This class represents an empty node in the big number list adt implementation.
 */
class BigNumberEmptyListNode implements BigNumberListNode {

  @Override
  public int size() {
    return 0;
  }

  @Override
  public BigNumberListNode shift(int shiftsBy, int shiftOperation) {
    return getBigNumberElementListNode(0, this);
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return getBigNumberElementListNode(digit, this);
  }

  @Override
  public int get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public BigNumberListNode copyOf() {
    return getBigNumberEmptyListNode();
  }

  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(getBigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return accumulator;
  }

  @Override
  public BigNumberListNode addSum(BigNumberListNode other, int carryOver) {
    if (carryOver != 0) {
      return getBigNumberElementListNode(carryOver, getBigNumberEmptyListNode());
    } else {
      return getBigNumberEmptyListNode();
    }
  }

  @Override
  public int compare(BigNumberListNode other) {
    return 0;
  }

  @Override
  public BigNumberListNode getRest() {
    return this;
  }

  @Override
  public Boolean isNodeEmpty() {
    return true;
  }

  @Override
  public BigNumberListNode addDigit(int digit, int carryOver) {
    if (carryOver != 0) {
      return getBigNumberElementListNode(carryOver, getBigNumberEmptyListNode());
    } else {
      return addFront(digit);
    }
  }

  @Override
  public String toString() {
    return toStringHelper(new StringBuilder());
  }

  @Override
  public String toStringHelper(StringBuilder stringAcc) {
    return stringAcc.toString();
  }

  /**
   * It creates a BigNumberElementListNode object.
   *
   * @param digit                  number to be represented in node.
   * @param bigNumberEmptyListNode instance of bigNumberEmptyListNode object.
   * @return new instance of BigNumberElementListNode.
   */
  private BigNumberElementListNode getBigNumberElementListNode(int digit,
                 BigNumberEmptyListNode bigNumberEmptyListNode) {
    return new BigNumberElementListNode(digit, bigNumberEmptyListNode);
  }

  /**
   * It creates a BigNumberElementListNode object.
   *
   * @return new instance of BigNumberEmptyListNode.
   */
  private BigNumberEmptyListNode getBigNumberEmptyListNode() {
    return new BigNumberEmptyListNode();
  }

}
