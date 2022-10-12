package bignumber;

import java.util.function.Function;

class EmptyListNode<T> implements ListADT<T> {

  @Override
  public int size() {
    return 0;
  }

  @Override
  public ListADT<T> addDigit(int digit) throws IllegalArgumentException {
    return null;
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public ListADT<T> copyOf() {
    return new EmptyListNode();
  }

  @Override
  public <R> ListADT<R> map(Function<T, R> converter) {
    return new EmptyListNode<R>();
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
    return addFront(digit);
  }

  @Override
  public ListADT<T> remove(int index) {
    return this; // nothing to remove from empty
  }

  @Override
  public String toString() {
    return "";
  }
}
