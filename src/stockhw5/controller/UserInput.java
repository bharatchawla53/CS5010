package stockhw5.controller;

/**
 * This enum represents the values for allowed user input.
 */
public enum UserInput {
  Y("YES"),
  N("NO");

  private final String input;

  public String getInput() {
    return input;
  }

  UserInput(String input) {
    this.input = input;
  }
}
