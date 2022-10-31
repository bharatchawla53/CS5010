package stockHw4;

import java.io.InputStream;
import java.util.List;

//TODO Look into sub views
public interface StockView {

  void getLoginScreenView();

  void getUsernameFromUser();

  void getRepeatUsername();

  void getDisplayPortfolioInput();

  void getDisplayPortfolioFilePathHeader();

  void getDisplayPortfolioFilePathInput();

  void getViewBuilder(List<String> values);

  String getUserInput(InputStream in);

  void throwErrorMessage(String message);

  void getDisplaySavePortfolioFromUser();

  void getDisplaySavePortfolioFilePathInput();

  void getDisplaySuccessfullPortfolioSave(String pUUID);

  void progressBar(int index);

  void getErrorMessageView(String message);

  void getNewUserView();

  void getExistingUserOptions();

  void getPortfolioCreatorView();

  void getDisplayPortfolioHeader();

  void getDisplayTotalPortfolioValue();

  void terminateView();

  void getDisplaySuccessfulTickerShareDump(String uuid);

  void getDisplayFinishedDumpingPortfolio(String uuid);

  void getTableViewBuilder(List<String> rows, List<String> columns);

}
