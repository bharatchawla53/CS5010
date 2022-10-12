package bignumber;

class BigNumberEmptyListNode implements BigNumberListADTNode {

  @Override
  public int size() {
    return 0;
  }

  @Override
  public BigNumberListADTNode addDigit(int digit) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public BigNumberListADTNode copyOf() {
    return new BigNumberEmptyListNode();
  }

/*  @Override
  public <R> BigNumberListADTNode<R> map(Function<T, R> converter) {
    return new EmptyBigNumberListNodeNode<R>();
  }*/
/*
  @Override
  public BigNumberListADTNode<T> add(T other) {
    return null;
  }*/

  @Override
  public BigNumberListADTNode addFront(int digit) {
    return new BigNumberElementListNode(digit, this);
  }

  @Override
  public BigNumberListADTNode addBack(int digit) {
    return addFront(digit);
  }

  @Override
  public BigNumberListADTNode remove(int index) {
    return this; // nothing to remove from empty
  }

  @Override
  public String toString() {
    return "";
  }
}
