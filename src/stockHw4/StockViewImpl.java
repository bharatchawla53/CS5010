package stockHw4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// displays cmd GUI and relevant texts, and validation is done here
// TODO see if we can dispatch using New User vs Existing User
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
    List<String> opItems = new ArrayList<>();
    opItems.add("Get started with your portfolio account ");
    opItems.add("Please enter an username and can't be longer than 8 characters: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getUsernameFromUser()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter your username:");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


  @Override
  public void getRepeatUsername()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Looks like this user name does not exist, please enter an existing user name:");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getPortfolioCreatorView()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Let's create your portfolio!");
    opItems.add("Enter your preferred ticker and no of shares you'd like " +
            "to invest in this ticker" +
            "(Expected Format: (ABC123) (number of shares) ):");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }





  @Override
  public void getViewBuilder(String[] values)
  {
    List<String> opItems = new ArrayList<>();
    for(String v: values)
    {
      opItems.add(v);
    }
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }



  @Override
  public void getDisplayPortfolioHeader()
  {
    List<String> opItems = new ArrayList<>();

    opItems.add("Here are your Portfolios!");


    String displayString = formatOutput(opItems);
    System.out.println(displayString);


  }

  @Override
  public void getDisplayPortfolioInput()
  {
    List<String> opItems = new ArrayList<>();

    opItems.add("Please enter a Portfolio ID from the above:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplayTotalPortfolioValueFromDate()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Please enter a date from which the portfolio value will be calculated:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }



  @Override
  public void getExistingUserOptions() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio ?");
    opItems.add("2. Would you like to examine the composition of a portfolio ?");
    opItems.add("3. Would you like to determine the total value of a portfolio on a certain date ?");
    opItems.add("4. Would you like to persist a portfolio so that it can be saved and loaded ?");

    opItems.add("Enter your option: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }


  @Override

  public void getDisplayPortfolioFilePathInput()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Enter the Portfolio UUID of the Portfolio you wish to serialize:");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getDisplayPortfolioFilePathHeader()
  {
    List<String> opItems = new ArrayList<>();
    opItems.add("Here are your Portfolio's!");
    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }

  @Override
  public void getNewUserOptions() {
    List<String> opItems = new ArrayList<>();
    opItems.add("1. Would you like to create a portfolio ?");
    opItems.add("Enter your option: ");

    String displayString = formatOutput(opItems);
    System.out.println(displayString);
  }





}
