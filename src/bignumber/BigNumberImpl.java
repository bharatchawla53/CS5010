package bignumber;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The BigNumberImpl class represents a non-negative number of arbitrary lengths.
 */
public class BigNumberImpl implements BigNumber {

  private BigNumberListNode head;

  /**
   * Constructs an empty BigNumberImpl constructor which initializes this number to 0.
   */
  public BigNumberImpl() {
    initializeHead();
  }

  /**
   * Constructs an BigNumberImpl constructor that takes a string number and represents it.
   *
   * @param number a valid string number.
   * @throws IllegalArgumentException if the string passed to it does not represent a valid number.
   */
  public BigNumberImpl(String number) throws IllegalArgumentException {
    if (!isNumeric(number) || number == null || number.equals("")) {
      throw new IllegalArgumentException("String passed to it does not represent a valid number.");
    }

    initializeHead();

    // it performs shift left operation and then add the digit to the list nodes.
    for (int i = 0; i < number.length(); i++) {
      this.shiftLeft(1);
      this.addDigit(Integer.parseInt(String.valueOf(number.charAt(i))));
    }
  }

  /**
   * Constructs an BigNumberImpl that takes a number represented in BigNumberListNode and assign it
   * to current head.
   *
   * @param newHead takes a BigNumberListNode.
   */
  private BigNumberImpl(BigNumberListNode newHead) {
    this.head = newHead;
  }

  @Override
  public int length() {
    return head.size();
  }

  @Override
  public void shiftLeft(int shiftsBy) {
    shiftHelper(shiftsBy, 0);
  }

  @Override
  public void shiftRight(int shiftsBy) {
    shiftHelper(shiftsBy, 1);
  }

  @Override
  public void addDigit(int digit) throws IllegalArgumentException {
    if (digit < 0 || digit > 9) {
      throw new IllegalArgumentException("Digit passed is not a single non-negative digit.");
    }
    head = head.addDigit(digit, 0);
  }

  @Override
  public int getDigitAt(int position) throws IllegalArgumentException {
    int listLength = this.length();
    if (position < 0) {
      throw new IllegalArgumentException("Invalid position is passed.");
    } else if (position >= 0 && position < listLength) {
      return head.get(position);
    } else {
      return 0;
    }
  }

  @Override
  public BigNumber copy() {
    BigNumberListNode independentCopy = head.copyOf();
    return new BigNumberImpl(independentCopy); // TODO research if we can use map here
  }

  @Override
  public BigNumber add(BigNumber other) {
    // map BigNumber to BigNumberImpl
    BigNumberImpl otherBigNum = new BigNumberImpl(other.toString());

    if (this.length() > otherBigNum.length()) {
      return new BigNumberImpl(this.head.addSum(otherBigNum.head, 0));
    } else {
      return new BigNumberImpl(otherBigNum.head.addSum(this.head, 0));
    }
  }

  /**
   * It compares two BigNumbers based on mathematical ordering.
   *
   * @param other takes in a BigNumber
   * @return 1 if the current number is greater than the other, -1 if the current number is less
   *         than the other the other, and 0 if they are equal.
   */
  @Override
  public int compareTo(BigNumber other) {
    if (this.length() > other.length()) {
      return 1;
    } else if (this.length() < other.length()) {
      return -1;
    } else {
      BigNumberImpl otherBigNum = new BigNumberImpl(other.toString());
      // length is same, but need to compare individual nodes.
      return head.reverse().compare(otherBigNum.head.reverse());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(head.toString());
  }

  @Override
  public boolean equals(Object obj) {
    // backwards compatibility with default equals.
    if (this == obj) {
      return true;
    }

    // Check if obj is a BigNumber object
    if (!(obj instanceof BigNumber)) {
      return false;
    }

    // The successful instanceOf check means our cast will succeed.
    BigNumber that = (BigNumber) obj;

    // checks if two numbers are identical using compareTo
    return this.compareTo(that) == 0;
  }

  @Override
  public String toString() {
    return head.toString();
  }

  /**
   * It takes a string and validate against regex to check if the input is a valid number.
   *
   * @param number takes a string of number.
   * @return true if the string is a valid number, false, otherwise.
   */
  private boolean isNumeric(String number) {
    if (number == null) {
      return false;
    }
    Pattern pattern = Pattern.compile("^[0-9]*$");
    return pattern.matcher(number).matches();
  }

  private void initializeHead() {
    this.head = new BigNumberElementListNode(0, new BigNumberEmptyListNode());
  }

  /**
   * Perform left or right shift based off the arguments provided.
   *
   * @param shiftsBy a number to be used to shift this number by.
   * @param shiftOperation an int representing shift operations.
   *                       0 represents left shift and 1 represents right shift.
   * @return the head of the resulting shifted list.
   */
  private BigNumberListNode shiftHelper(int shiftsBy, int shiftOperation) {
    // case where initial value of this number is 0 or shiftBy is 0
    // and right shifting should always yield to 0.
    if (head.isNodeEmpty() || shiftsBy == 0) {
      return this.head;
    }

    // positive shift
    if (shiftsBy > 0) {
      head = shiftOperation == 0
              ? head.shiftLeft(shiftsBy)
              : head.shiftRight(shiftsBy);
    } else { // negative shift
      head = shiftOperation == 0
              ? head.shiftRight(Math.abs(shiftsBy))
              : head.shiftLeft(Math.abs(shiftsBy));
    }
    return head;
  }
}
