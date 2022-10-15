package bignumber;

class BigNumberEmptyListNode implements BigNumberListNode {

  @Override
  public int size() {
    return 0;
  }

  @Override
  public int get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public BigNumberListNode copyOf() {
    return new BigNumberEmptyListNode();
  }

/*  @Override
  public <R> BigNumberListADTNode map(Function<BigNumberListADTNode, R> converter) {
    return new BigNumberEmptyListNode();
  }*/



  // TODO trying out reverse approach and will check later if there is a better approach
  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(new BigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return accumulator;
  }

  @Override
  public BigNumberListNode sum(BigNumberListNode other) {
    return sumAccumulator(other, 0, 0);
  }

  @Override
  public BigNumberListNode sumAccumulator(BigNumberListNode other, int carryOver, int indexIncrementer) {
    BigNumberListNode resultNode = new BigNumberEmptyListNode();

    if (indexIncrementer == other.size()) {
      if (carryOver != 0) {
        resultNode = resultNode.addBack(carryOver);
      } else {
        return this;
      }
    } else {
      // we still need to add the values if they weren't same length
      while (indexIncrementer < other.size()) {
        resultNode = resultNode.addBack(other.get(indexIncrementer));
      }
    }
    return resultNode;
  }

  @Override
  public int compare(BigNumber other, int indexIncrementer) {
    return 0;
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }

  @Override
  public BigNumberListNode addBack(int digit) {
    return addFront(digit);
  }

  @Override
  public BigNumberListNode remove(int index) {
    return this; // nothing to remove from empty
  }

  @Override
  public String toString() {
    return "";
  }
}
