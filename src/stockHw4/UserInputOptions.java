package stockHw4;

public enum UserInputOptions {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4);
  private final int input;

  public int getInput() {
    return input;
  }

  UserInputOptions(int input) {
    this.input = input;
  }

}
