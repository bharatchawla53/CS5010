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
    this.head = new BigNumberElementListNode(0, new BigNumberEmptyListNode());

    // add each digit to the back of the list
    for (int i = 0; i < number.length(); i++) {
      //this.head = head.addBack(Integer.parseInt(String.valueOf(number.charAt(i))));
      this.shiftLeft(1);
      //this.head = head.addFront(Integer.parseInt(String.valueOf(number.charAt(i))));
      this.addDigit(Integer.parseInt(String.valueOf(number.charAt(i))));
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
    if (head.isNodeEmpty() || shiftsBy == 0) {
      return;
    }
    // positive shift
    if (shiftsBy > 0) {
      head = head.shiftLeft(shiftsBy);
    } else { // negative shift
      shiftRight(Math.abs(shiftsBy));
    }
  }

  @Override
  public void shiftRight(int shiftsBy) {
    // case where initial value of this number is 0 and right shifting should always yield to 0.
    if (head.isNodeEmpty() || shiftsBy == 0) {
      return;
    }

    if (shiftsBy > 0) {
      head = head.shiftRight(shiftsBy);
    } else { // negative shift
      shiftLeft(Math.abs(shiftsBy));
    }
  }

  @Override
  public void addDigit(int digit) throws IllegalArgumentException {
    if (digit < 0 || digit > 9) {
      throw new IllegalArgumentException("Digit passed is not a single non-negative digit");
    }
    head = head.addDigit(digit, 0);
  }

  @Override
  public int getDigitAt(int position) throws IllegalArgumentException {
    int listLength = this.length();
    if (position < 0) {
      throw new IllegalArgumentException("Invalid position is passed");
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

  @Override
  public int compareTo(BigNumber other) {
    // based on mathematical ordering so no comparison using string
    if (this.length() > other.length()) {
      return 1;
    } else if (this.length() < other.length()) {
      return -1;
    } else {
      BigNumberImpl otherBigNum = new BigNumberImpl(other.toString());
      // length is same, but need to check individual numbers to find which one is bigger or are they equal
      return head.reverse().compare(otherBigNum.head.reverse());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(head);
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

  // using regex to find if string contains valid numbers and not unwanted chars.
  private boolean isNumeric(String number) {
    if (number == null) {
      return false;
    }
    Pattern pattern = Pattern.compile("^[0-9]*$");
    return pattern.matcher(number).matches();
  }
}
