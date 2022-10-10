package bignumber;

public class BigNumberEmptyNode implements BigNumberListNode {

  @Override
  public int length() {
    return 0;
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
  public String toString() {
    return "";
  }
}
