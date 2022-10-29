package stockHw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
      BufferedReader in = new BufferedReader(new FileReader("path/of/text"));
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
      if (val.equals(ticker.strip())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean validateTicker(String ticker) {
    return searchStringArray(ticker);
  }

  @Override
  public String generateUUID() {
    return UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 2);
  }

  @Override // TODO why we need this??
  public boolean validateTickerShare(String tickerShare) {
    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[ ]\\d+");
    Matcher validator = ticketShareValidationPattern.matcher(tickerShare);
    return validator.matches();
  }

  @Override
  public List<String> getAllPortfoliosFromUser(User user) {
    List<String> userPortfolios = new ArrayList<>();
    File folder = new File("./");

    if (folder.listFiles() != null || folder.listFiles().length == 0) {
      return userPortfolios;
    }

    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().contains(user.getUserName())) {
          userPortfolios.add(listOfFiles[i].getName());
        }
      }
    }
    return userPortfolios;
  }

  @Override
  public boolean validatePortfolioUUID(String portfolioUUID, User user) {
    File folder = new File("./");
    File[] listOfFiles = folder.listFiles();

    if (folder.listFiles() != null || folder.listFiles().length == 0) {
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

  @Override
  public void dumpTickerShare(User user, String portfolioUUID, String ticker, String shares) {
    //TODO Integrate API and store with Share Value
    String username = user.getUserName();
    String portfolioFileName = username + "_" + portfolioUUID + ".csv";
    File f = new File(portfolioFileName);
    if (f.exists() && !f.isDirectory()) {
      try {
        FileWriter fw = new FileWriter(portfolioFileName, true);
        fw.write(ticker + "," + shares + "\n");
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    } else {
      try {
        if (f.createNewFile()) {
          try {
            FileWriter fw = new FileWriter(portfolioFileName, true);
            fw.write(ticker + "," + shares + "\n");
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
  }

  @Override
  public List<String> getPortfolioContents(User user, String uuid) {
    String username = user.getUserName();
    String portfolioFileName = username + "_" + uuid + ".csv";
    List<String> portfolioContents = new ArrayList<>();
    File f = new File(portfolioFileName);

    try {
      BufferedReader fr = new BufferedReader(new FileReader(portfolioFileName));

      String line = fr.readLine();
      int i = 0;
      while (line != null) {

        line = fr.readLine();
        String ticker = line.split(",")[0];
        String noOfShares = line.split(",")[1];
        String tickerNoOfShares = ticker + " " + noOfShares;
        portfolioContents.add(tickerNoOfShares);
        i += 1;
      }
      return portfolioContents;
    } catch (IOException e) {
      return portfolioContents;
    }
  }

  // TODO add method determine the total value of a portfolio on a certain date
  @Override
  public List<String> calculateTotalValueOfAPortfolio(Date certainDate, User user, String portfolioUUID) {
    List<String> totalValueOfPortfolio = new ArrayList<>();

    List<String> portfolioContents = this.getPortfolioContents(user, portfolioUUID);

    // check if symbol exists in hash map list else call the api to fetch it

    return totalValueOfPortfolio;
  }

  private void getStockDataFromApi(String outputSize, String symbol) {
    stockHashMapList = alphaVantageApi.getStockTradedValue(outputSize, symbol);
  }
}
