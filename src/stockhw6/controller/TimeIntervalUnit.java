package stockhw6.controller;

/**
 * This enum represents the values for allowed time interval unit.
 */
public enum TimeIntervalUnit {
  DAYS("Days"),
  MONTH("Month"),
  YEAR("Year");

  private final String input;

  public String getInput() {
    return input;
  }

  TimeIntervalUnit(String input) {
    this.input = input;
  }
}
