package stockhw5.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface StockApi {

  List<HashMap<String, List<StockApiResponse>>> getStockTradedValue(String outputSize, String symbol);

  LocalDate dateParser(String date);
}
