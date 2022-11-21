package stockhw6.controller;

/**
 * This enum represents the values for allowed user input options.
 */
public enum UserInputOptions {
  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9");
  private final String input;

  public String getInput() {
    return input;
  }

  UserInputOptions(String input) {
    this.input = input;
  }

}
