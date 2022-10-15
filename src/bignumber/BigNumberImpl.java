package bignumber;

import java.util.Objects;
import java.util.regex.Pattern;

public class BigNumberImpl implements BigNumber {

  private BigNumberListNode head;

  // set this BigNumber to 0 so, 0 -> EmptyNode
  public BigNumberImpl() {
    this.head = new BigNumberElementListNode(0, new BigNumberEmptyListNode());
  }

  public BigNumberImpl(String number) throws IllegalArgumentException {
    // write a Regex to eliminate unwanted chars
    if (!isNumeric(number) || number == null || number.equals("")) {
      // throw IAE
      throw new IllegalArgumentException("String passed to it does not represent a valid number.");
    }

    // initialize the head
    this.head = new BigNumberEmptyListNode();

    // add each digit to the back of the list
    for (int i = 0; i < number.length(); i++) {
      this.head = head.addBack(Integer.parseInt(String.valueOf(number.charAt(i))));
    }

  }

  private BigNumberImpl(BigNumberListNode newHead) {
    this.head = newHead;
  }

  @Override
  public int length() {
    return head.size();
  }

  @Override
  public void shiftLeft(int shiftsBy) {
    // case where initial value of this number is 0 and left shifting should always yield to 0.
    if (this.toString().equals("0")) {
      return;
    }

    // positive shift
    if (shiftsBy > 0) {
      for (int i = 0; i < shiftsBy; i++) {
        // means appending 0's to the back of the nodes
        head = head.addBack(0);
      }
    } else { // negative shift
      shiftRight(Math.abs(shiftsBy));
    }
  }

  @Override
  public void shiftRight(int shiftsBy) {
    // case where initial value of this number is 0 and right shifting should always yield to 0.
    if (this.toString().equals("0")) {
      return;
    }

    if (shiftsBy > 0) {
     for (int i = 0; i < shiftsBy; i++) {
       // means removing the last node
       head = head.remove(head.toString().length() - 1);
     }
    } else { // negative shift
      shiftLeft(Math.abs(shiftsBy));
    }
  }

  @Override
  public void addDigit(int digit) throws IllegalArgumentException {
    if (digit < 0) {
      throw new IllegalArgumentException("Digit passed is not a single non-negative digit");
    }

    // map digit to BigNumberListNode
    BigNumberListNode digitNode = new BigNumberEmptyListNode();
    digitNode = digitNode.addFront(digit);


    head = head.reverse();

    int shiftsBy = head.size() - digitNode.size();
    for (int i = 0; i < shiftsBy; i++) {
      // means appending 0's to the back of the nodes
      digitNode = digitNode.addBack(0);
    }

    head = head.sum(digitNode);
    head = head.reverse();
  }

  @Override
  public int getDigitAt(int position) throws IllegalArgumentException {
    if (position >= 0 && position < length()) {
      return head.get(position);
    } else {
      throw new IllegalArgumentException("Invalid position is passed");
    }
  }

  @Override
  public BigNumber copy() {
    BigNumberListNode independentCopy = head.copyOf();

    return new BigNumberImpl(independentCopy); // TODO research if we can use map here
  }

  @Override
  public BigNumber add(BigNumber other) {
    // map BigNumber to BigNumberListNode
    BigNumberListNode otherNode = new BigNumberEmptyListNode();

    for (int i = 0; i < other.toString().length(); i++) {
      otherNode = otherNode.addBack(other.getDigitAt(i));
    }

    // reverse first number
    BigNumberListNode reverseFirstBigNumber = head.reverse();

    // reverse second number
    BigNumberListNode reverseSecondBigNumber = otherNode.reverse();

    if (reverseFirstBigNumber.size() < reverseSecondBigNumber.size()) {
      int shiftsBy = reverseSecondBigNumber.size() - reverseFirstBigNumber.size();
      for (int i = 0; i < shiftsBy; i++) {
        // means appending 0's to the back of the nodes
        reverseFirstBigNumber = reverseFirstBigNumber.addBack(0);
      }
    } else if (reverseSecondBigNumber.size() < reverseFirstBigNumber.size()){
      int shiftsBy = reverseFirstBigNumber.size() - reverseSecondBigNumber.size();
      for (int i = 0; i < shiftsBy; i++) {
        // means appending 0's to the back of the nodes
        reverseSecondBigNumber = reverseSecondBigNumber.addBack(0);
      }
    }

    BigNumberListNode result = reverseFirstBigNumber
            .sum(reverseSecondBigNumber)
            .reverse();

    return new BigNumberImpl(result);
  }

  @Override
  public int compareTo(BigNumber other) { // TODO
    // based on mathematical ordering so no comparison using string
    return 0;
    //return this.head.toString().compareTo(other.toString());
  }

  @Override
  public int hashCode() {
    return 31 * Objects.hashCode(head);
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

    // checks if two numbers are identical
    return this.toString().equals(that.toString());
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
