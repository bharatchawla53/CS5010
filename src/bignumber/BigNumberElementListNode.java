package bignumber;

class BigNumberElementListNode implements BigNumberListADTNode {

  private final int number;
  private BigNumberListADTNode rest;

  public BigNumberElementListNode(int number, BigNumberListADTNode rest) {
    this.number = number;
    this.rest = rest;
  }

  @Override
  public int size() {
    return 1 + this.rest.size();
  }

  @Override
  public BigNumberListADTNode addDigit(int digit) throws IllegalArgumentException {
    return null;
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
  public BigNumberListADTNode copyOf() {
    return new BigNumberElementListNode(this.number, this.rest.copyOf());
  }

/*  @Override
  public <R> BigNumberListADTNode<R> map(Function<T, R> converter) {
    return new ElementBigNumberListNodeNode<R>(converter.apply(this.number), this.rest.map(converter));
  }*/

/*
  @Override
  public BigNumberListADTNode add(T other) {
    return null;
  }
*/

  @Override
  public BigNumberListADTNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }

  @Override
  public BigNumberListADTNode addBack(int digit) {
    this.rest = this.rest.addBack(digit);
    return this;
  }

  @Override
  public BigNumberListADTNode remove(int index) {
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
