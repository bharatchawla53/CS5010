package solution.listadt;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * This is the implementation of a generic list. Specifically it implements the listadt.ListADT
 * interface
 */
public class ListADTImpl<T> implements ListADT<T> {
  private GenericListADTNode<T> head;

  public ListADTImpl() {
    head = new GenericEmptyNode();
  }

  //a private constructor that is used internally (see map)
  private ListADTImpl(GenericListADTNode<T> head) {
    this.head = head;
  }

  @Override
  public void addFront(T b) {
    head = head.addFront(b);
  }

  @Override
  public void addBack(T b) {
    head = head.addBack(b);
  }

  @Override
  public void add(int index, T b) {
    head = head.add(index, b);
  }

  @Override
  public int getSize() {
    //step 1: variables used: acc, this

    /*    //steps 2, 3
    int acc = 0; //the initial value of the accumulator
    GenericListADTNode<T> current = head; //the initial value of this, the node on
    which count is called first

    //step 4
    while (current instanceof GenericElementNode) { //step 5: this is when recursion goes on
      acc +=1;
      current = ((GenericElementNode<T>)current).getRest();
    }
    //step 6
    return acc;*/
    //return head.count();
    Deque<Pair<String, GenericListADTNode<T>>> ts = new LinkedList<>();
    Deque<Integer> rs = new LinkedList<>();

    ts.push(new Pair<>("recur", head));  //start with the head
    while (!ts.isEmpty()) {
      Pair<String, GenericListADTNode<T>> command = ts.pop();

      if (command.field1.equals("recur")) {
        if (command.field2 instanceof GenericEmptyNode) {
          //simply process this node, no recursive call
          ts.push(new Pair<>("process", command.field2));
        }
        if (command.field2 instanceof GenericElementNode) {
          //first make the recursive call, then process this node (add 1 to result)
          //added in reverse order so that they come out of stack in correct order
          ts.push(new Pair<>("process", command.field2));
          ts.push(new Pair<>("recur", command.field2.getRest()));
        }
      }
      if (command.field1.equals("process")) {
        if (command.field2 instanceof GenericEmptyNode) {
          //an empty node simply returns 0, as per above implementation
          rs.push(0);
        }
        if (command.field2 instanceof GenericElementNode) {
          //add 1 to the "last result", as per above implementation
          int num = rs.pop();
          rs.push(1 + num);
        }
      }
    }
    return rs.pop(); //the last thing remaining is the final result
  }

  @Override
  public void remove(T b) {
    head = head.remove(b);
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    if ((index >= 0) && (index < getSize())) {
      return head.get(index);
    } else {
      throw new IllegalArgumentException("Invalid index");
    }

  }

  @Override
  public <R> ListADT<R> map(Function<T, R> converter) {
    GenericListADTNode<T> current = head;
    ListADT<R> object = new ListADTImpl<>();
    //step 4
    while (current instanceof GenericElementNode) { //step 5: this is when recursion goes on
      object.addBack(converter.apply(((GenericElementNode<T>) current).getData()));
      current = ((GenericElementNode<T>) current).getRest();
    }
    //step 6
    return object;
    //return new ListADTImpl(head.map(converter));
  }

  @Override
  public String toString() {
    return "(" + head.toString() + ")";
  }

  class Pair<S, T> {
    S field1;
    T field2;

    public Pair(S field1, T field2) {
      this.field1 = field1;
      this.field2 = field2;
    }
  }
}
