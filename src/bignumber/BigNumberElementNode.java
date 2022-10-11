package bignumber;

public class BigNumberElementNode implements BigNumberListNode {

  private int number;
  private BigNumberListNode rest;

  public BigNumberElementNode(int number, BigNumberListNode rest) {
    this.number = number;
    this.rest = rest;
  }

  @Override
  public int length() {
    return 1 + this.rest.length();
  }

  @Override
  public BigNumberListNode shiftLeft(int shiftsBy) {
    return null;
  }

  @Override
  public BigNumberListNode shiftRight(int shiftsBy) {
    return null;
  }

  @Override
  public BigNumberListNode addDigit(int digit) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int getDigitAt(int position) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public BigNumber copy() {
    return null;
  }

  @Override
  public BigNumber add(BigNumber other) {
    return null;
  }

  @Override
  public BigNumberListNode addFront(int digit) {
    return new BigNumberElementNode(digit, this);
  }

  @Override
  public BigNumberListNode addBack(int digit) {
    this.rest = this.rest.addBack(digit);
    return this;
  }

  @Override
  public String toString() {
    return this.number + this.rest.toString();
  }
}
