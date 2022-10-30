package stockHw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockModelImpl implements StockModel {

  private static List<String> tickerList;
  private final AlphaVantageApi alphaVantageApi;
  private static List<HashMap<String, List<AlphaVantageApi.AlphaDailyTimeSeries>>> stockHashMapList;

  public StockModelImpl() {
    alphaVantageApi = new AlphaVantageApi();
    stockHashMapList = new ArrayList<>();
    tickerList = new ArrayList<>();
    loadTickerList();
  }

  @Override
  public User saveUser(User user) {
    // If its valid, persist it to the user file
    // else return a signal back to controller its invalid
    if (!isUserNameExists(user.getUserName())) {
      persistUser(user);
      return user;
    }
    return null; // indicates user never persisted
  }

  @Override
  public boolean isUserNameExists(String userName) {
    Set<User> userSet = getUsers();
    for (User u : userSet) {
      if (u.getUserName().equals(userName)) {
        return true;
      }
    }
    return false;
  }

  private void loadTickerList() {
    try {
      BufferedReader in = new BufferedReader(new FileReader("tickers.csv"));
      String str;

      List<String> list = new ArrayList<>();
      while ((str = in.readLine()) != null) {
        list.add(str);
      }
      tickerList = list;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void persistUser(User user) {
    // get existing user for re-persisting with the new user
    Set<User> userSet = getUsers();

    // write to text file comma separated
    try {
      FileWriter fileWriter = new FileWriter("users.csv");
      // add the existing users
      for (User u : userSet) {
        fileWriter.write(u.getId() + "," + u.getUserName() + "\n");
      }
      // add the new user
      fileWriter.write(user.getId() + "," + user.getUserName() + "\n");
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Set<User> getUsers() {
    Set<User> userSet = new HashSet<>();

    // Open the file
    FileInputStream fstream = null;
    try {
      fstream = new FileInputStream("users.csv");
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

      String strLine;

      //Read File Line By Line
      while ((strLine = br.readLine()) != null) {
        String[] userDetails = strLine.split(",");
        // add user to the list
        userSet.add(new User(userDetails[0], userDetails[1]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //Close the input stream
      try {
        fstream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return userSet;
  }

  private boolean searchStringArray(String ticker) {
    for (String val : tickerList) {
      if (val.split(",")[0].equals(ticker)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isValidTicker(String ticker) {
    return searchStringArray(ticker);
  }

  @Override
  public String generateUUID() {
    return UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 8);
  }

  @Override
  public boolean validateTickerShare(String tickerShare) {
    //TODO check for fractional and negative shares
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[ ]\\d+");
    Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
    return validator.matches();
  }

  @Override
  public List<String> getAllPortfoliosFromUser(User user) {
    List<String> userPortfolios = new ArrayList<>();
    File folder = new File("./");

    if (folder.listFiles() == null || folder.listFiles().length == 0) {
      return userPortfolios;
    }

    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().contains(user.getUserName())) {
          userPortfolios.add(listOfFiles[i].getName().split("_")[1].split("\\.")[0]);
        }
      }
    }
    return userPortfolios;
  }

  @Override
  public boolean validatePortfolioUUID(String portfolioUUID, User user) {
    File folder = new File("./");
    File[] listOfFiles = folder.listFiles();

    if (folder.listFiles() == null || folder.listFiles().length == 0) {
      return false;
    }

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().contains(portfolioUUID) &&
                listOfFiles[i].getName().contains(user.getUserName())) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public User getUserFromUsername(String username) {
    Set<User> userSet = getUsers();
    for (User u : userSet) {
      if (u.getUserName().equals(username)) {
        return u;
      }
    }
    return null;
  }
  private LocalDate getCurrentDateSkippingWeekends() {
    LocalDate now = LocalDate.now();

    DayOfWeek dayOfWeek = DayOfWeek.of(now.get(ChronoField.DAY_OF_WEEK));
    if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
      now = now.minusDays(1);
    } else if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
      now = now.minusDays(2);
    }
    return now;
  }


  private LocalDate getCurrentDateSkippingWeekends(LocalDate date) {
    LocalDate now = date;

    DayOfWeek dayOfWeek = DayOfWeek.of(now.get(ChronoField.DAY_OF_WEEK));
    if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
      now = now.minusDays(1);
    } else if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
      now = now.minusDays(2);
    }
    return now;
  }




  @Override // TODO consolidate stocks of same name during portfolio creation
  public boolean dumpTickerShare(User user, String portfolioUUID, String ticker, String noOfShares) {
    boolean isSuccessful = false;
    String username = user.getUserName();
    String portfolioFileName = username + "_" + portfolioUUID + ".csv";
    File f = new File(portfolioFileName);
    Double stockPrice = getStockPrice(new String[]{ticker, noOfShares}, getCurrentDateSkippingWeekends());
    boolean isOverwritten = false;
    if (stockPrice == null) {
      // call the API
      getStockDataFromApi(AlphaVantageOutputSize.COMPACT.name(), ticker);
      stockPrice = getStockPrice(new String[]{ticker, noOfShares}, getCurrentDateSkippingWeekends());
    }

    if (f.exists() && !f.isDirectory()) {

        List<String> portfolioContents = getPortfolioContents(user,portfolioUUID);
        for (int i=0;i<portfolioContents.size();i++)
        {
          if(portfolioContents.get(i).split(" ")[0].equals(ticker))
          {
            String newRow = ticker+","+
                    String.valueOf(Integer.parseInt(portfolioContents.get(i).split(" ")[1])
                    +Integer.parseInt(noOfShares))+","+portfolioContents.get(i).split(" ")[2];
            portfolioContents.set(i,newRow);
            isOverwritten = true;

          }
        }
        if(isOverwritten == true) {
          try {
            FileWriter fw = new FileWriter(portfolioFileName,false);
            for (String row : portfolioContents) {
              fw.write(row + "\n");

            }
            isSuccessful = true;
            fw.close();


          }
          catch(IOException e)
          {
            e.printStackTrace();
          }
        }

        else
        {
          try {
            FileWriter fw = new FileWriter(portfolioFileName, true);
            fw.write(ticker + "," + noOfShares + "," + stockPrice + "\n");
            isSuccessful = true;
            fw.close();
          }
          catch(IOException e) {
            e.printStackTrace();
          }
        }





      }

     else {
      try {
        if (f.createNewFile()) {
          try {
            FileWriter fw = new FileWriter(portfolioFileName, true);
            fw.write(ticker + "," + noOfShares + "," + stockPrice + "\n");
            isSuccessful = true;
            fw.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      //if portfolio uuid is not there in the folder, then create userid_portfolio.txt
      //with open("filename.txt","r") as f load all text append at the bottom and then save back to
      //filename.txt
      //format of filename.txt - filename is userid_portfolio.txt, with columns ticker and noofshares
    }
    return isSuccessful;
  }





  @Override
  public List<String> getPortfolioContents(User user, String uuid) {
    String username = user.getUserName();
    String portfolioFileName = username + "_" + uuid + ".csv";
    List<String> portfolioContents = new ArrayList<>();
    File f = new File(portfolioFileName);

    try {
      BufferedReader fr = new BufferedReader(new FileReader(portfolioFileName));

      String strLine;

      while ((strLine = fr.readLine()) != null) {
        String ticker = strLine.split(",")[0];
        String noOfShares = strLine.split(",")[1];
        String stockPrice = strLine.split(",")[2];
        String tickerNoOfShares = ticker + " " + noOfShares + " " + stockPrice;
        portfolioContents.add(tickerNoOfShares);
      }
      return portfolioContents;
    } catch (IOException e) {
      return portfolioContents;
    }
  }

  // TODO need to change code for API limits
  @Override
  public List<String> calculateTotalValueOfAPortfolio(String certainDate, User user, String portfolioUUID) {
    List<String> totalValueOfPortfolio = new ArrayList<>();

    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);

    for (String content : portfolioContents) {
      String[] shareDetail = content.split(" ");
      Double stockPrice = getStockPrice(shareDetail, getCurrentDateSkippingWeekends(dateParser(certainDate)));

      if (stockPrice == null) {
        // call the API
        getStockDataFromApi(AlphaVantageOutputSize.FULL.getInput(), shareDetail[0]);
        stockPrice = getStockPrice(shareDetail, getCurrentDateSkippingWeekends(dateParser(certainDate)));
      }

      String symbolCached = calculateTotalStockWorth(shareDetail, stockPrice);
      totalValueOfPortfolio.add(symbolCached);
    }
    return totalValueOfPortfolio;
  }

  private LocalDate dateParser(String certainDate) {
    return alphaVantageApi.dateParser(certainDate);
  }

  private void getStockDataFromApi(String outputSize, String symbol) {
    stockHashMapList = alphaVantageApi.getStockTradedValue(outputSize, symbol);
  }

  private boolean isCurrentTimeBeforeNoon() {
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    String formattedDate = formatter.format(now);
    boolean isTimeBeforeNoon = false;
    try {
      if (formatter.parse(formattedDate).before(formatter.parse("12:00"))) {
        isTimeBeforeNoon = true;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return isTimeBeforeNoon;
  }

  private Double getStockPrice(String[] shareDetail, LocalDate certainDate) {
    Double stockPrice = null;

    for (HashMap<String, List<AlphaVantageApi.AlphaDailyTimeSeries>> symbolMap : stockHashMapList) {
      if (symbolMap.containsKey(shareDetail[0])) {
        // iterate to find the stock value on a certain date
        for (AlphaVantageApi.AlphaDailyTimeSeries timeSeries : symbolMap.get(shareDetail[0])) {
          if (timeSeries.getDate().equals(certainDate)) {
            stockPrice = isCurrentTimeBeforeNoon()
                    ? Double.parseDouble(timeSeries.getOpenVal())
                    : Double.parseDouble(timeSeries.getCloseVal());
            break;
          }
        }
      }
    }
    return stockPrice;

  }

  private String calculateTotalStockWorth(String[] shareDetail, Double stockPrice) {
    return shareDetail[0] + " " + shareDetail[1] + " " + stockPrice * Double.parseDouble(shareDetail[1]);
  }


  private boolean validatePortfolioRow(String row)
  {
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[,]\\d+[,](\\d|\\.)+");
    Matcher validator = ticketShareValidationPattern.matcher(row);
    boolean isValid = validator.matches();
    return isValid;
  }

  @Override
  public boolean validateUserPortfolioExternal(String filePath, User user) {
    File f = new File(filePath);



    try {
      BufferedReader fr = new BufferedReader(new FileReader(filePath));
      String strLine;

      while ((strLine = fr.readLine()) != null) {

        if(!validatePortfolioRow(strLine))
        {
          return false;
        }
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public String saveExternalUserPortfolio(String filePath, User user) {
    String username = user.getUserName();
    String uuid = generateUUID();
    String portfolioFileName = username + "_" + uuid + ".csv";
    List<String> portfolioContents = new ArrayList<>();
    File f = new File(portfolioFileName);
    boolean isSuccessful = false;
    try {
      BufferedReader fr = new BufferedReader(new FileReader(filePath));

      String strLine;

      while ((strLine = fr.readLine()) != null) {
        //TODO add check for obe while splitting on ,
        if(validatePortfolioRow(strLine)) {
            String ticker = strLine.split(",")[0];
            String noOfShares = strLine.split(",")[1];
            String stockPrice = strLine.split(",")[2];
            String tickerNoOfShares = ticker + " " + noOfShares + " " + stockPrice;
            portfolioContents.add(tickerNoOfShares);
        }
        else
        {
          return null;
        }
      }
      if (f.createNewFile()) {
        try {
          FileWriter fw = new FileWriter(portfolioFileName, true);
          for (String s : portfolioContents) {
            fw.write(s.split(" ")[0] + "," + s.split(" ")[1] + "," + s.split(" ")[2]+"\n");
          }
          isSuccessful = true;

          fw.close();
          return uuid;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    } catch (IOException e) {
      return null;
    }
    return null;
  }
}
