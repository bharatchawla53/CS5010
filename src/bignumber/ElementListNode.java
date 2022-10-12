package bignumber;

import java.util.function.Function;

class ElementListNode<T> implements ListADT<T> {

  private final T number;
  private ListADT<T>  rest;

  public ElementListNode(T number, ListADT<T>  rest) {
    this.number = number;
    this.rest = rest;
  }

  @Override
  public int size() {
    return 1 + this.rest.size();
  }

  @Override
  public ListADT<T> addDigit(int digit) throws IllegalArgumentException {
    return null;
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    if (index == 0) {
      return this.number;
    } else {
      return this.rest.get(index - 1);
    }
  }

  @Override
  public ListADT<T> copyOf() {
    return new ElementListNode(this.number, this.rest.copyOf());
  }

  @Override
  public <R> ListADT<R> map(Function<T, R> converter) {
    return new ElementListNode<R>(converter.apply(this.number), this.rest.map(converter));
  }

  @Override
  public ListADT<T> add(T other) {
    return null;
  }

  @Override
  public ListADT<T> addFront(int digit) {
    return new ElementListNode(digit, this);
  }

  @Override
  public ListADT<T> addBack(int digit) {
    this.rest = this.rest.addBack(digit);
    return this;
  }

  @Override
  public ListADT<T>  remove(int index) {
    // TODO fix this logic
    // build a new list and exclude the last element??

/*    if (this.number == value) {
      return this.rest;
    } else {
      this.rest = this.rest.remove(value);
      return this;
    }*/
    return null;
  }

  @Override
  public String toString() {
    return this.number + this.rest.toString();
  }
}
