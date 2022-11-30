package stockhw6.controller;

/**
 * This enum represents the values for allowed time interval unit.
 */
public enum TimeIntervalUnit {
  DAYS("days"),
  MONTH("month"),
  YEAR("year");

  private final String input;

  public String getInput() {
    return input;
  }

  TimeIntervalUnit(String input) {
    this.input = input;
  }
}
