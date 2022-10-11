package bignumber;

import java.util.regex.Pattern;

public class BigNumberImpl implements BigNumber {

  private BigNumberListNode head;

  // set this BigNumber to 0 so, 0 -> EmptyNode
  public BigNumberImpl() {
    this.head = new BigNumberElementNode(0, new BigNumberEmptyNode());
  }

  public BigNumberImpl(String number) throws IllegalArgumentException {
    // write a Regex to eliminate unwanted chars
    if (!isNumeric(number) || number == null || number.equals("")) {
      // throw IAE
      throw new IllegalArgumentException("String passed to it does not represent a valid number.");
    }

    // call empty constructor??
    // TODO revisit this logic
    this.head = new BigNumberElementNode(Integer.parseInt(String.valueOf(number.charAt(0))), new BigNumberEmptyNode());
    // else build the list with the numbers we got in the string
    for (int i = 1; i < number.length(); i++) {
      this.head = head.addBack(Integer.parseInt(String.valueOf(number.charAt(i))));
    }

  }

  @Override
  public int length() {
    return head.length();
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

  // using regex to find if string contains valid numbers and not unwanted chars.
  private boolean isNumeric(String number) {
    if (number == null) {
      return false;
    }
    Pattern pattern = Pattern.compile("^\\d+$");
    return pattern.matcher(number).matches();
  }
}
