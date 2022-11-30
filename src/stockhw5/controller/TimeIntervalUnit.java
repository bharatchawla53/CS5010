package stockhw5.controller;

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
