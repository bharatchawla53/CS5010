package stockhw5.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * This interface represents a StockApi that defines the operations listed below.
 */
public interface StockApi {

  /**
   * Given the output size and symbol, it returns a list of hashmap of ticker with it's historical
   * data.
   *
   * @param outputSize how far to go back to retrieve historical data.
   * @param symbol     ticker to fetch details for.
   * @return a list of hashmap of ticker with it's data.
   */
  List<HashMap<String, List<StockApiResponse>>> getStockTradedValue(String outputSize, String symbol);

  /**
   * Given a date, it parses to LocalDate.
   *
   * @param date date.
   * @return parsed LocalDate object.
   */
  LocalDate dateParser(String date);
}
