package stockHw4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// displays cmd GUI and relevant texts, and validation is done here
public class StockViewImpl implements StockView {

  public StockViewImpl() {
  }

  private String formatOutput(List<String> outputStrings) {
    StringBuilder sb = new StringBuilder();
    sb.append("///");
    for (String e : outputStrings) {
      sb.append("\n");
      sb.append(e);
    }
    return sb.toString();
  }

  @Override
  public void getLoginScreenView() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Welcome to the portfolio manager");
    opItems.add("Are you a existing user or would you like to create a new user ?");
    opItems.add("Enter Y/N : ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public String getUserInput(InputStream in) {
    Scanner scanner = new Scanner(in);
    return scanner.next();
  }

  @Override
  public void throwErrorMessage(String message) {
    System.out.println(message);
  }

  @Override
  public void getExistingUserView() {

  }

  @Override
  public void getErrorMessageView(String message) {
    System.out.println(message);
  }

  @Override
  public void getNewUserView() {

  }

  @Override
  public void getUserOptions() {
    List<String> opItems = new ArrayList<>();
    opItems.add("Welcome to the portfolio manager");
    opItems.add("Are you a existing user or would you like to create a new user ?");
    opItems.add("Enter Y/N : ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


}
