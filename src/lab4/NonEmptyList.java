package lab4;

/**
 * This represents a non-empty node of the list. It contains a piece of data along with the rest of
 * the list.
 */
public class NonEmptyList implements ListOfString {
  private String str;
  private ListOfString rest;

  /**
   * Creates a constructor with given string, and rest of listOfString.
   * @param s string with current element.
   * @param rest listOfString containing all nodes.
   */
  public NonEmptyList(String s, ListOfString rest) {
    this.str = s;
    this.rest = rest;
  }

  @Override
  public int size() {
    return 1 + this.rest.size();
  }

  @Override
  public ListOfString addFront(String s) {
    return new NonEmptyList(s, this);
  }

  @Override
  public ListOfString addBack(String s) {
    this.rest = this.rest.addBack(s);
    return this;
  }

  @Override
  public ListOfString add(int index, String s) {
    if (index == 0) {
      return addFront(s);
    } else {
      return new NonEmptyList(this.str, this.rest.add(index - 1, s));
    }
  }


  @Override
  public String get(int index) throws IllegalArgumentException {
    if (index == 0) {
      return this.str;
    }
    return this.rest.get(index - 1);
  }

  @Override
  public ListOfString reverse() {
    return reverseAccumulator(new EmptyList());

  }

  @Override
  public ListOfString reverseAccumulator(ListOfString accumulator) {
    return this.rest.reverseAccumulator(new NonEmptyList(this.str, accumulator));
  }

  @Override
  public ListOfString interleave(ListOfString other) {
    // A=(1,3,5) and B=(2,4,6,8,10)
    // A.interleave(B) == (1,2,3,4,5,6,8,10)

    return new NonEmptyList(this.str, other.interleave(this.rest));
  }

  @Override
  public String toString() {
    if (this.rest.size() == 0) {
      return this.str + this.rest.toString();
    } else {
      return this.str + "," + this.rest.toString();
    }
  }

}
