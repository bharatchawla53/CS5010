package bignumber;

public class BigNumberImpl implements BigNumber {

  private BigNumberListNode head;

  // set this BigNumber to 0 so, 0 -> EmptyNode
  public BigNumberImpl() {
    head = new BigNumberElementNode(0, new BigNumberEmptyNode());
  }

  public BigNumberImpl(String number) throws IllegalArgumentException {

  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public void shiftLeft(int shiftsBy) {

  }

  @Override
  public void shiftRight(int shiftsBy) {

  }

  @Override
  public void addDigit(int digit) throws IllegalArgumentException {

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
  public int compareTo(BigNumber o) {
    return 0;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return head.toString();
  }
}
