package solution.bst;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents a binary search tree. It implements the BSTADT interface
 */
public class BSTImpl<T extends Comparable<T>>
        implements BST<T> {
  private BSTNode<T> root;

  public BSTImpl() {
    root = new BSTEmptyNode<T>(); //no tree
  }

  @Override
  public int size() {
    Deque<Pair<String, BSTNode<T>>> ts = new LinkedList<>();
    Deque<Integer> rs = new LinkedList<>();

    ts.push(new Pair<>("recur", root)); //CHANGE 1: start with the root
    while (!ts.isEmpty()) {
      Pair<String, BSTNode<T>> command = ts.pop();
      if (command.field1.equals("recur")) {
        if (command.field2 instanceof BSTEmptyNode) {
          //simply process this node, no recursive call
          ts.push(new Pair<>("process", command.field2));
        }
        if (command.field2 instanceof BSTElementNode) {
          //first make the recursive call to left, then to right, and
          //finally process this node (add 1 to the results)
          //added in reverse order so that they come out of stack in correct order
          //CHANGE 2
          ts.push(new Pair<>("process", command.field2));
          ts.push(new Pair<>("recur", ((BSTElementNode<T>) command.field2).getRight()));
          ts.push(new Pair<>("recur", ((BSTElementNode<T>) command.field2).getLeft()));
        }
      }
      if (command.field1.equals("process")) {
        if (command.field2 instanceof BSTEmptyNode) {
          //an empty node simply returns 0, as per above implementation
          rs.push(0);
        }
        if (command.field2 instanceof BSTElementNode) {
          //add 1 to the previous two results, as per above implementation
          //CHANGE 3
          int num1 = rs.pop();
          int num2 = rs.pop();
          rs.push(1 + num1 + num2);
        }
      }
    }
    return rs.pop(); //the last thing remaining is the final result
    //return root.count();
  }

  @Override
  public void insert(T data) {
    root = root.insert(data);
  }

  @Override
  public boolean present(T data) {
    return root.contains(data);
  }

  @Override
  public T minimum() {
    return root.minimum();
  }

  @Override
  public T maximum() {
    return root.maximum();
  }

  public String toString() {
    return "[" + root.toString() + "]";
  }

  @Override
  public List<T> preorder() {
    List<T> result = new ArrayList<T>();
    Stack<BSTNode<T>> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      BSTNode<T> curr = stack.pop();

      if (curr instanceof BSTElementNode) {
        result.add(((BSTElementNode<T>) curr).getData());

        if (((BSTElementNode<T>) curr).getRight() != null) {
          stack.push(((BSTElementNode<T>) curr).getRight());
        }

        if (((BSTElementNode<T>) curr).getLeft() != null) {
          stack.push(((BSTElementNode<T>) curr).getLeft());
        }
      }
    }
    return result;
  }

  @Override
  public List<T> inorder() {
    List<T> result = new ArrayList<T>();
    Stack<BSTNode<T>> stack = new Stack<>();
    BSTNode<T> curr = this.root;

    while (!stack.isEmpty() || !curr.toString().equals("")) {
      if (curr instanceof BSTElementNode) {
        if (((BSTElementNode<T>) curr).getData() != null) {
          stack.push(curr);
          curr = ((BSTElementNode<T>) curr).getLeft();
        }
      } else {
        curr = stack.pop();
        result.add(((BSTElementNode<T>) curr).getData());
        curr = ((BSTElementNode<T>) curr).getRight();
      }
    }

    return result;
  }

  @Override
  public List<T> postorder() {
    List<T> result = new ArrayList<T>();
    Stack<BSTNode<T>> stack = new Stack<>();
    Stack<T> out = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      BSTNode<T> curr = stack.pop();

      if (curr instanceof BSTElementNode) {
        //result.add(((BSTElementNode<T>) curr).getData());
        out.push(((BSTElementNode<T>) curr).getData());
        if (((BSTElementNode<T>) curr).getLeft() != null) {
          stack.push(((BSTElementNode<T>) curr).getLeft());
        }

        if (((BSTElementNode<T>) curr).getRight() != null) {
          stack.push(((BSTElementNode<T>) curr).getRight());
        }
      }
    }

    while (!out.isEmpty()) {
      result.add(out.pop());
    }
    return result;
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
