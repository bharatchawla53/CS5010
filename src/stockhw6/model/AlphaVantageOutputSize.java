package stockhw6.model;

/**
 * This enum represents the values for output size accepted by third-party API.
 */
public enum AlphaVantageOutputSize {
  COMPACT("compact"),
  FULL("full");

  private final String input;

  public String getInput() {
    return input;
  }

  AlphaVantageOutputSize(String input) {
    this.input = input;
  }
}
