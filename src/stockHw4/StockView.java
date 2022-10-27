package stockHw4;

import java.io.InputStream;
import java.util.Date;

//TODO Look into sub views
public interface StockView {

  void getLoginScreenView();





  void getDisplayPortfolioInput();

  void getViewBuilder(String[] values);

  String getUserInput(InputStream in);

  void throwErrorMessage(String message);

  void getExistingUserView();

  void getErrorMessageView(String message);

  void getNewUserView();

  void getExistingUserOptions();

  void getNewUserOptions();


  void getPortfolioCreatorView();

  void getDisplayPortfolioHeader();

  void getDisplayTotalPortfolioValueFromDate();









}
