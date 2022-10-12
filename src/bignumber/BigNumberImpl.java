package bignumber;

import java.util.Objects;
import java.util.regex.Pattern;

public class BigNumberImpl implements BigNumber {

  private BigNumberListADTNode head;

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
    BigNumberListADTNode independentCopy = head.copyOf();
  // Big Number node
    //independentCopy.map();
    return null;
    //return independentCopy; // use map??
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
