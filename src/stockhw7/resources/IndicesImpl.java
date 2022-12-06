package stockhw7.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * IndicesImpl implements indices to hold a list
 * of index for ease of data return.
 */
public class IndicesImpl implements Indices {

  private List<Index> indices;

  /**
   * a simple constructor that initializes the list of index.
   */
  public IndicesImpl() {
    this.indices = new ArrayList<Index>();
  }

  @Override
  public Index getIndex(String name) throws IllegalArgumentException {

    for (Index index : indices) {
      if (Objects.equals(index.toString(), name)) {
        return index;
      }
    }

    Index output = new IndexImpl(name);
    indices.add(output);
    return output;
  }

  @Override
  public void save() throws IOException {
    FileIO.saveIndices(indices);
  }

  @Override
  public void load() throws IOException {
    if (indices.isEmpty()) {
      indices = FileIO.loadIndices();
    } else {
      throw new IllegalArgumentException("Load cannot be run when indices contains "
              + "a usable list of indexes");
    }
  }

  @Override
  public void updateIndex(String name) {
    for (Index i : indices) {
      if (name.equals(i.toString())) {
        i.update();
        return;
      }
    }
  }

  @Override
  public void updateAll() {
    Calendar today = new GregorianCalendar();
    for (Index i : indices) {
      if (i.getLastUpdate().get(Calendar.YEAR) != today.get(Calendar.YEAR)
              || i.getLastUpdate().get(Calendar.MONTH) != today.get(Calendar.MONTH)
              || i.getLastUpdate().get(Calendar.DAY_OF_MONTH) != today.get(Calendar.DAY_OF_MONTH)) {
        i.update();
      }
    }
  }

  @Override
  public String timeToUpdate() {
    return Math.ceil(indices.size() / 5) + " Minutes";
  }

  @Override
  public double getValue(String name, int year, int month, int day) {
    for (Index i : indices) {
      if (name.equals(i.toString())) {
        return i.getValue(year, month, day);
      }
    }
    throw new IllegalArgumentException("the name given is not in idecies");
  }


}
