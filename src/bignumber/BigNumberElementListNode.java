package bignumber;

/**
 * This class represents an element node in the big number list adt implementation.
 */
class BigNumberElementListNode implements BigNumberListNode {

  private final int number;
  private BigNumberListNode rest;

  public BigNumberElementListNode(int number, BigNumberListNode rest) {
    this.number = number;
    this.rest = rest;
  }

  @Override
  public int size() {
    return 1 + this.rest.size();
  }

  @Override
  public BigNumberListNode shiftLeft(int shiftBy) {
    if (shiftBy == 0) {
      return this;
    } else {
      return new BigNumberElementListNode(0, this).shiftLeft(shiftBy - 1);
    }
  }

  @Override
  public BigNumberListNode shiftRight(int shiftsBy) {
    if (shiftsBy == 0) {
      return this;
    } else {
      this.rest = this.rest.shiftRight(shiftsBy - 1);
      return this.rest;
    }
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }


  @Override
  public int get(int index) throws IllegalArgumentException {
    if (index == 0) {
      return this.number;
    } else {
      return this.rest.get(index - 1);
    }
  }

  @Override
  public BigNumberListNode copyOf() {
    return new BigNumberElementListNode(this.number, this.rest.copyOf());
  }

  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(new BigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return this.rest.reverseAccumulator(new BigNumberElementListNode(this.number, accumulator));
  }

  @Override
  public BigNumberListNode addSum(BigNumberListNode other, int carryOver) {
    int result = 0;
    // if other is instanceof empty list node, the result should just contain
    // the sum of number and carryover.
    if (other instanceof BigNumberEmptyListNode) {
      result = this.number + carryOver;
    } else {
      // if the current element node value at index is 0, that means, the result should just
      // contain the sum of number and carryover.
      if (other.get(0) == 0) {
        result = this.number + carryOver;
      }
      // else perform sum of two nodes number with carryover.
      result = this.number + other.get(0) + carryOver;
    }

    int sum = result % 10;
    carryOver = result / 10;

    return new BigNumberElementListNode(sum, this.rest.addSum(other.getRest(), carryOver));
  }

  @Override
  public int compare(BigNumberListNode other) {
    if (this.number > other.get(0)) {
      return 1;
    } else if (this.number < other.get(0)) {
      return -1;
    } else {
      return this.rest.compare(other.getRest());
    }
  }

  @Override
  public BigNumberListNode getRest() {
    return this.rest;
  }

  @Override
  public Boolean isNodeEmpty() {
    if (this.rest instanceof BigNumberEmptyListNode) {
      if (this.number == 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public BigNumberListNode addDigit(int digit, int carryOver) {
    int result = this.number + digit + carryOver;
    int sum = result % 10;
    carryOver = result / 10;

    if (carryOver != 0) {
      return new BigNumberElementListNode(sum, this.rest.addDigit(0, carryOver));
    } else {
      return new BigNumberElementListNode(sum, this.rest);
    }
  }

  /*  @Override
  public <R> BigNumberListADTNode map(Function<BigNumberListADTNode, R> converter) {
    return new BigNumberElementListNode(converter.apply(this.number), this.rest.map(converter));
  }*/

  @Override
  public String toString() {
    return toStringHelper(new StringBuilder());
  }

  @Override
  public String toStringHelper(StringBuilder stringAcc) {
    return stringAcc.append(this.rest.toString()).append(this.number).toString();
  }
}
