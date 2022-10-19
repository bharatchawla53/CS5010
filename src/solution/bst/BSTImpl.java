package solution.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a binary search tree. It implements the BSTADT
 * interface
 */
public class BSTImpl<T extends Comparable<T>>
        implements BST<T> {
  private BSTNode<T> root;

  public BSTImpl() {
    root = new BSTEmptyNode<T>(); //no tree
  }

  @Override
  public int size() {
    return root.count();
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
    return "["+root.toString()+"]";
  }

  @Override
  public List<T> preorder() {
    List<T> result = new ArrayList<T>();
    root.preorder(result);
    return result;
  }

  @Override
  public List<T> inorder() {
    List<T> result = new ArrayList<T>();
    root.inorder(result);
    return result;
  }

  @Override
  public List<T> postorder() {
    List<T> result = new ArrayList<T>();
    root.postorder(result);
    return result;
  }
}
