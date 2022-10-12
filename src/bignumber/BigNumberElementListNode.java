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

  @Override
  public BigNumberListNode sum(int digit) {
    // get the rightmost node first
    // add that.node.number + digit
    // handle carry and update other nodes as needed
    // assuming we are getting reverse list here
    return sumAccumulator(digit, 0);
  }

  @Override
  public BigNumberElementListNode sumAccumulator(int digit, int carryOver) {
    int result = this.number + digit + carryOver;

    int sum = result % 10;
    carryOver = result / 10;


    if (carryOver != 0) {
      return new BigNumberElementListNode(sum, this.rest.sumAccumulator(0, carryOver));
    } else {
      return new BigNumberElementListNode(sum, this.rest); // no further calculations so we can just return remaining rest
    }
  }

  // TODO trying out reverse approach and will check later if there is a better approach
  @Override
  public BigNumberListNode reverse() {
    return reverseAccumulator(new BigNumberEmptyListNode());
  }

  @Override
  public BigNumberListNode reverseAccumulator(BigNumberListNode accumulator) {
    return this.rest.reverseAccumulator(new BigNumberElementListNode(this.number, accumulator));
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
