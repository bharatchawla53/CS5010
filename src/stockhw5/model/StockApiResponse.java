package stockhw5.model;

import java.time.LocalDate;

/**
 * The StockApiResponse class represents the object to map the fields received from third-party API
 * for stock data.
 */
public class StockApiResponse {

  private final LocalDate date;
  private final String openVal;
  private final String closeVal;

  /**
   * Creates an AlphaDailyTimeSeries constructor.
   *
   * @param date     stock data for that particular day.
   * @param openVal  open value of stock for the given date.
   * @param closeVal close value of stock for the given date.
   */
  public StockApiResponse(LocalDate date, String openVal, String closeVal) {
    this.date = date;
    this.openVal = openVal;
    this.closeVal = closeVal;
  }

  /**
   * Returns the date.
   *
   * @return date.
   */
  public LocalDate getDate() {
    return this.date;
  }

  /**
   * Returns the open value of stock.
   *
   * @return openVal.
   */
  public String getOpenVal() {
    return this.openVal;
  }

  /**
   * Returns the close value of stock.
   *
   * @return closeVal.
   */
  public String getCloseVal() {
    return this.closeVal;
  }

}
