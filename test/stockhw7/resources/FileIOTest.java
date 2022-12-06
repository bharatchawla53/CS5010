package stockhw7.resources;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import stockhw7.portfolio.Portfolio;
import stockhw7.portfolio.PortfolioImpl;

import static org.junit.Assert.assertEquals;

/**
 * A FileIOTest that contains several static methods
 * to retrieve and save files in the program.
 */
public class FileIOTest {

  private final Indices indices = new IndicesImpl();
  private final Portfolio portfolio = new PortfolioImpl("test");

  /**
   * this constructor initializes the indices and other stocks
   * once so there are not many api calls.
   *
   * @throws IOException if the file cannot be loaded
   */
  public FileIOTest() throws IOException {
    //    indices.getIndex("GOOG");
    //    indices.getIndex("IBM");
    //    indices.getIndex("TSCO");
    //    indices.getIndex("SHOP");

    indices.load();
    //    portfolio.addStock(new StockImpl(2,"GOOG", indices.getIndex("GOOG")));
    //    portfolio.addStock(new StockImpl(3,"TSCO", indices.getIndex("TSCO")));

  }

  //  private FileIO fileIO;
  //
  //  @Before
  //  public void setup() {
  //    fileIO =
  //  }

  //  @Test
  //  public void loadFileSimpleTest() throws FileNotFoundException {
  //    FileIO.loadFile("test");
  //  }
  //
  //  @Test
  //  public void saveFileSimpleTest() throws IOException {
  //    FileIO.saveFile("test1", "this is a test456");
  //  }

  @Test
  public void saveIndicesSimpleTest() throws IOException {
    indices.save();
    assertEquals("PORTFOLIO", portfolio.getStocks());
  }

  @Test
  public void loadIndicesSimpleTest() throws IOException {
    indices.load();
    int test = 0;
    assertEquals("PORTFOLIO", portfolio.getStocks());
  }

  @Test
  public void savePortfolioSimpleTest() throws IOException, URISyntaxException {
    portfolio.save();
    int test = 0;
    assertEquals("PORTFOLIO", portfolio.getStocks());
  }

  @Test
  public void loadPortfolioSimpleTest() throws IOException {
    portfolio.load("test");
    int test = 0;
    assertEquals("PORTFOLIO", portfolio.getStocks());
  }
}