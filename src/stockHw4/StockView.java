package stockHw4;

import java.io.InputStream;

public interface StockView {

  void getLoginScreenView();

  String getUserInput(InputStream in);

  void throwErrorMessage(String message);

  void getExistingUserView();

  void getErrorMessageView(String message);

  void getNewUserView();

  void getExistingUserOptions();

  void getNewUserOptions();
}
