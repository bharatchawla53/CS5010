package bignumber;

class BigNumberElementListNode implements BigNumberListNode {

  private final int number;
  private BigNumberListNode rest;
  //private int carry;

  public BigNumberElementListNode(int number, BigNumberListNode rest) {
    this.number = number;
    this.rest = rest;
  }

/*  private BigNumberElementListNode(int sum, int carryOver, BigNumberListNode rest) {
    this.number = sum;
    this.carry = carryOver;
    this.rest = rest;
  }*/

  @Override
  public int size() {
    return 1 + this.rest.size();
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

/*  @Override
  public <R> BigNumberListADTNode map(Function<BigNumberListADTNode, R> converter) {
    return new BigNumberElementListNode(converter.apply(this.number), this.rest.map(converter));
  }*/


  // TODO trying out reverse approach and will check later if there is a better approach
  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(new BigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return this.rest.reverseAccumulator(new BigNumberElementListNode(this.number, accumulator));
  }

  // get the rightmost node first
  // add that.node.number + digit
  // handle carry and update other nodes as needed
  // assuming we are getting reverse list here
  @Override
  public BigNumberListNode sum(BigNumberListNode other) {
    // perform addition on each digit and reverse the result before returning it
    return this.sumAccumulator(other, 0, 0);

  }

  @Override
  public BigNumberListNode sumAccumulator(BigNumberListNode other, int carryOver, int indexIncrementer) {
    int result = this.number + other.get(indexIncrementer) + carryOver;

    int sum = result % 10;
    carryOver = result / 10;

    return new BigNumberElementListNode(sum, this.rest.sumAccumulator(other, carryOver, ++indexIncrementer));
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }

  @Override
  public BigNumberListNode addBack(int digit) {
    this.rest = this.rest.addBack(digit);
    return this;
  }

  @Override
  public BigNumberListNode remove(int index) {
    if (index == 0) {
      return this.rest;
    } else {
      this.rest = this.rest.remove(index - 1);
      return this;
    }
  }

  @Override
  public String toString() {
    return this.number + this.rest.toString();
  }
}
