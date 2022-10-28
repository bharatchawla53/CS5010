package stockHw4;

public enum AlphaVantageOutputSize {
  COMPACT("compact"),
  FULL("full");

  private final String input;

  public String getInput() {
    return input;
  }

  AlphaVantageOutputSize (String input) {
    this.input = input;
  }
}
