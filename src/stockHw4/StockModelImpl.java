package stockHw4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class StockModelImpl implements StockModel {

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
}
