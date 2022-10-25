package stockHw4;

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
