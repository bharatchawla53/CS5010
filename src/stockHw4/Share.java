package stockHw4;

import java.sql.Timestamp;

public class Share {
  private final int noOfShares;
  private final double shareValue;
  private final String ticker;
  private final Timestamp date;

  public static ShareBuilder builder() {
    return new ShareBuilder();
  }

  public Share(String ticker, Timestamp date, int noOfShares, double shareValue) {
    this.ticker = ticker;
    this.date = date;
    this.noOfShares = noOfShares;
    this.shareValue = shareValue;
  }

  public String getTicker() {
    return this.ticker;
  }

  public Timestamp getDate() {
    return this.date;
  }

  public double getShareValue() {
    return this.shareValue;
  }

  public int getNoOfShares() {
    return this.noOfShares;
  }

  public static class ShareBuilder {
    private String ticker;
    private Timestamp date;
    private int noOfShares;
    private double shareValue;

    public ShareBuilder() {
    }

    public ShareBuilder ticker(String ticker) {
      this.ticker = ticker;
      return this;
    }

    public ShareBuilder noOfShares(int noOfShares) {
      this.noOfShares = noOfShares;
      return this;
    }

    public ShareBuilder date(Timestamp date) {
      this.date = date;
      return this;
    }

    public ShareBuilder shareValue(double shareValue) {
      this.shareValue = shareValue;
      return this;
    }

    public Share build() {
      return new Share(ticker, date, noOfShares, shareValue);
    }
  }
}

