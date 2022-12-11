package stockhw7.resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stockhw7.portfolio.FlexPortfolioImpl;
import stockhw7.portfolio.Portfolio;
import stockhw7.portfolio.Stock;
import stockhw7.portfolio.StockImpl;


/**
 * Saves and loads given files to a designated position.
 * there are a couple methods that work with the Investment
 * Program.
 */
public class FileIO {

  private static final String stockDelimiter = "<NewStock>";
  private static final String logDelimiter = "<Log>\n";
  private static final String flexPortfolio = "<FlexPortfolio>\n";
  private static final String dir = System.getProperty("user.dir") + "\\files\\";
  //private static final String dir = "../../"; //System.getProperty("user.dir") + "/files/";

  /**
   * Save a flexible Portfolio saves a given portfolio to a file
   * by the given name.
   *
   * @param stocks   takes a list of stocks to save to a file
   * @param portInfo the portfolio information that holds broker fee and log
   * @param fileName the file name of the portfolio.
   * @throws IOException when the portfolio cannot be saved
   */
  public static void saveFlexPortfolio(List<Stock> stocks, String portInfo, String fileName)
          throws IOException {
    String[] illegalInputs = new String[]{"#", "%", "&", "{", "}", "\\", "<", ">",
        "*", "?", "/", "$", "!", "'", "\"", ":", "@", "+", "`", "|", "="};
    for (String s : illegalInputs) {
      if (fileName.contains(s)) {
        throw new IllegalArgumentException("the file name contains a invalid character");
      }
    }

    FileWriter fileWriter = new FileWriter(dir + fileName + ".txt");
    fileWriter.append(flexPortfolio);     //<FlexPortfolio>
    fileWriter.append(logDelimiter);      //<Log>
    fileWriter.append(portInfo);          // port info
    fileWriter.append(logDelimiter);      //<Log>
    fileWriter.append(stockDelimiter);    //<NewStock>
    for (Stock s : stocks) {
      fileWriter.append(s.print());       //5, GOOG
      fileWriter.append(stockDelimiter);  //<NewStock>
    }

    fileWriter.close();
  }

  /**
   * loads a flexible Portfolio form a given portfolio file
   * by the given name.
   *
   * @param fileName the file name of the portfolio.
   * @return a list of stock for a Portfolio to load.
   * @throws IOException when the portfolio cannot be loaded
   */
  public static Portfolio loadFlexPortfolio(String fileName) throws IOException {


    File file = new File(dir + fileName + ".txt");
    if (!file.exists()) {
      throw new IllegalArgumentException("the file name given does cannot be found");
    }
    Scanner scanner = new Scanner(file);

    //checks weather it is flexible or not
    String flexibleTitle = scanner.nextLine() + "\n";
    if (!flexibleTitle.equals(flexPortfolio)) {
      throw new IllegalArgumentException("This file is not of a flexible type");
    }

    //brokerFee
    //List of String log
    List<String> log = new ArrayList<>();
    scanner.useDelimiter(logDelimiter);
    String logToken = scanner.next();
    double brokerFee = 0;
    int brokerFeeStart = 1;
    try {
      brokerFee = Double.valueOf(logToken.split("\n")[brokerFeeStart]);
    } catch (Exception e) {
      brokerFeeStart = 2;
      brokerFee = Double.valueOf(logToken.split("\n")[brokerFeeStart]);
    }

    String[] logArray = logToken.split("\n");
    for (int i = brokerFeeStart + 1; i < logArray.length; i++) {
      log.add(logArray[i] + "\n");
      String string = "";
      try {
        string = logArray[i + 1].substring(1,4);
      } catch (Exception e) {
        string = "";
      }
      if (string.equals("Log")) {
        i = logArray.length;
      }
    }


    //List of stock
    scanner = new Scanner(file);
    scanner.useDelimiter(stockDelimiter);
    List<Stock> stocks = new ArrayList<Stock>();

    if (scanner.hasNext()) {
      scanner.next();
    }

    while (scanner.hasNext()) {
      String token = scanner.next();

      String firstLine = token.split("\n")[0];
      String num = firstLine.split(",")[0];
      String name = firstLine.split(",")[1].trim();

      stocks.add(new StockImpl(Double.parseDouble(num), name, ""));
    }
    // indices
    Indices indices = new IndicesImpl();
    for (Stock s : stocks) {
      try {
        indices.getIndex(s.getTicker());
      } catch (IllegalArgumentException e) {
        //not all indices loaded
        break;
      }
    }


    //StringBuilder output = new StringBuilder();
    return new FlexPortfolioImpl(fileName, brokerFee, stocks, log, indices);
  }

  /**
   * Save Portfolio saves a given portfolio to a file
   * by the given name.
   *
   * @param stocks   takes a list of stocks to save to a file
   * @param fileName the file name of the portfolio.
   * @throws IOException when the portfolio cannot be saved
   */
  public static void savePortfolio(List<Stock> stocks, String fileName)
          throws IOException {
    String[] illegalInputs = new String[]{"#", "%", "&", "{", "}", "\\", "<", ">",
        "*", "?", "/", "$", "!", "'", "\"", ":", "@", "+", "`", "|", "="};
    for (String s : illegalInputs) {
      if (fileName.contains(s)) {
        throw new IllegalArgumentException("the file name contains a invalid character");
      }
    }
    //attempt 3
    //    String dir3 = dir + "files/";
    //    System.out.println(originalDir);
    //
    //    String[] isProjectFolder = originalDir.split("\\\\");
    //    System.out.println(isProjectFolder[isProjectFolder.length - 2]);
    //
    //    FileWriter fileWriter;
    //
    //    if (Objects.equals(isProjectFolder[isProjectFolder.length - 2], "PDPAndreouGaziano")) {
    //      System.out.println("In Project");
    //      fileWriter = new FileWriter(originalDir + fileName + ".txt");
    //    } else {
    //      System.out.println("In Jar");
    //    }
    //    if (!Files.exists(Paths.get(dir3))) {
    //      new File(dir3);
    //    }
    //    //attempt 2
    //    //String path = new File(FileIO.class.getProtectionDomain().getCodeSource().getLocation()
    //            //.toURI()).getPath();
    //    //attempt 1
    //    File file = new File("../../../");
    //    String newDir = file.getAbsolutePath();

    FileWriter fileWriter = new FileWriter(dir + fileName + ".txt");

    fileWriter.append(stockDelimiter);
    for (Stock s : stocks) {
      fileWriter.append(s.toString());
      fileWriter.append(stockDelimiter);
    }

    fileWriter.close();
  }

  /**
   * loads Portfolio form a given portfolio file
   * by the given name.
   *
   * @param fileName the file name of the portfolio.
   * @return a list of stock for a Portfolio to load.
   * @throws IOException when the portfolio cannot be loaded
   */
  public static List<Stock> loadPortfolio(String fileName) throws IOException {
    List<Stock> output = new ArrayList<Stock>();
    File file = new File(dir + fileName + ".txt");
    if (!file.exists()) {
      throw new IllegalArgumentException("the file name given does cannot be found");
    }
    Scanner scanner = new Scanner(file);

    scanner.useDelimiter(stockDelimiter);

    while (scanner.hasNext()) {
      String token = scanner.next();
      String firstLine = token.split("\n")[0];
      String num = firstLine.split(",")[0];
      String name = firstLine.split(",")[1].trim();

      output.add(new StockImpl(Integer.valueOf(num), name,
              token.substring(firstLine.length() + 1)));
    }

    //StringBuilder output = new StringBuilder();
    return output;
  }

  /**
   * Save Indices saves a given list of indices to a predetermined file.
   *
   * @param indices takes a list of index to save to a file
   * @throws IOException when the Indices cannot be saved
   */
  public static void saveIndices(List<Index> indices) throws IOException {
    FileWriter fileWriter = new FileWriter(dir + "savedIndices.txt");

    fileWriter.append(stockDelimiter);
    for (Index i : indices) {
      fileWriter.append(i.toString() + "\n");
      fileWriter.append(i.getIndex());
      fileWriter.append(stockDelimiter);
    }

    fileWriter.close();
  }

  /**
   * loads indices form a given portfolio file
   * by the given name.
   *
   * @return a list of index for the indices to use
   * @throws IOException when the indices cannot be loaded
   */
  public static List<Index> loadIndices() throws IOException {
    List<Index> output = new ArrayList<Index>();
    File file = new File(dir + "savedIndices.txt");
    Scanner scanner = new Scanner(file);

    scanner.useDelimiter(stockDelimiter);

    while (scanner.hasNext()) {
      String token = scanner.next();
      String name = token.split("\n")[0];
      output.add(new IndexImpl(name, token.substring(name.length() + 1)));
    }

    //StringBuilder output = new StringBuilder();
    return output;
  }

  //  public static void saveFile(String fileName, String input) throws IOException {
  //    String[] illegalInputs = new String[]{"#", "%", "&", "{", "}", "\\", "<", ">", "*",
  //    "?", "/",
  //            "$", "!", "'", "\"", ":", "@", "+", "`", "|", "="};
  //    for (String s : illegalInputs)
  //      if (fileName.contains(s)) {
  //        throw new IllegalArgumentException("the file name contains a invalid character");
  //      }
  //
  //    FileWriter fileWriter = new FileWriter(dir + fileName + ".txt");
  //    //fileWriter.append(input);
  //    fileWriter.write(input);
  //    fileWriter.close();
  //  }
  //
  //  public static String loadFile(String fileName) throws FileNotFoundException {
  //    File file = new File(dir + fileName + ".txt");
  //    Scanner scanner = new Scanner(file);
  //
  //    StringBuilder output = new StringBuilder();
  //
  //    while (scanner.hasNextLine()) {
  //      output.append(scanner.nextLine() + "\n");
  //    }
  //    //scanner.useDelimiter(stockDelimiter);
  //    //String output = scanner.next();
  //
  //    System.out.println(output.toString().trim());
  //
  //    return output.toString().trim();
  //  }
}
