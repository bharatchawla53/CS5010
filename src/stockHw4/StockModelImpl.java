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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockModelImpl implements StockModel {


  String[] tickerList = {};



  public StockModelImpl()
  {
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

  private void loadTickerList()
  {
    try {
      BufferedReader in = new BufferedReader(new FileReader("path/of/text"));
      String str;

      List<String> list = new ArrayList<String>();
      while ((str = in.readLine()) != null) {
        list.add(str);
      }

      String[] stringArr = list.toArray(new String[0]);
      this.tickerList = stringArr;

    }
    catch(FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch(IOException e)
    {
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


  private boolean searchStringArray(String[] vals,String ele)
  {
    for (String val: vals)
    {
      if(val == ele.strip())
      {
        return true;
      }
    }
    return false;

  }
  public boolean validateTicker(String ticker)
  {

    if (searchStringArray(this.tickerList,ticker))
    {
      return true;
    }
   return false;
  }

  @Override
  public String generateUUID()
  {
    return  UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 8);
  }

  public boolean validateTickerShare(String tickerShare)
  {

    Pattern ticketShareValidationPattern = Pattern.compile("[A-Z]+[ ]\\d+");
    Matcher Validator = ticketShareValidationPattern.matcher(tickerShare);
    return Validator.matches();
  }

  public String[] getAllPortfoliosFromUser(User user)
  {
    String username = user.getUserName();
    File folder = new File("./");
    File[] listOfFiles = folder.listFiles();
    String[] vals = {};
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if(listOfFiles[i].getName().contains(username))
        {
          vals[i] = listOfFiles[i].getName();
        }

      }
    }
    return vals;

  }


  public boolean validatePortfolioUUID(String portfolioUUID,User user)
  {

    File folder = new File("./");
    File[] listOfFiles = folder.listFiles();
    String username = user.getUserName();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if(listOfFiles[i].getName().contains(portfolioUUID) && listOfFiles[i].
                getName().contains(username));
        {
          return true;
        }

      }
    }
    return false;

  }
  public void dumpTickerShare(User user,String portfolioUUID, String ticker, String shares) {

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
}
