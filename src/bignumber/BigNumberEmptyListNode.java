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
  public BigNumberListNode shiftLeft(int shiftBy) {
    return new BigNumberElementListNode(0, this);
  }

  @Override
  public BigNumberListNode shiftRight(int shiftsBy) {
    return new BigNumberElementListNode(0, this);
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }

  @Override
  public int get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public BigNumberListNode copyOf() {
    return new BigNumberEmptyListNode();
  }

  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(new BigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return accumulator;
  }

  @Override
  public BigNumberListNode addSum(BigNumberListNode other, int carryOver) {
    if (carryOver != 0) {
      return new BigNumberElementListNode(carryOver, new BigNumberEmptyListNode());
    } else {
      return new BigNumberEmptyListNode();
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
      return new BigNumberElementListNode(carryOver, new BigNumberEmptyListNode());
    } else {
      return addFront(digit);
    }
  }


/*  @Override
  public <R> BigNumberListADTNode map(Function<BigNumberListADTNode, R> converter) {
    return new BigNumberEmptyListNode();
  }*/

  @Override
  public String toString() {
    return toStringHelper(new StringBuilder());
  }

  @Override
  public String toStringHelper(StringBuilder stringAcc) {
    return stringAcc.toString();
  }

}
