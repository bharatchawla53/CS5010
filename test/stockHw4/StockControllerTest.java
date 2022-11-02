package stockHw4;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class StockControllerTest {
  /**
   * The StockModelImpl class represents user portfolio's and features supported for it.
   */
  public class MockStockModelImpl implements StockModel {
    private StringBuilder log;

    /**
     * Constructs an empty MockStockModelImpl constructor which initializes StringBuilder.
     */
    public MockStockModelImpl() {
      this.log = new StringBuilder();
    }

    @Override
    public User saveUser(User user) {
      if (user.getUserName() != null) {
        log.append(user.getUserName());
      }
      return null;
    }

    @Override
    public boolean isUserNameExists(String userName) {
      if (userName != null) {
        log.append(userName);
      }
      return false;
    }

    @Override
    public boolean isValidTicker(String ticker) {
      if (ticker != null) {
        log.append(ticker);
      }
      return false;
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
      if (tickerShare != null) {
        log.append(tickerShare);
      }
      return false;
    }

    @Override
    public List<String> getPortfoliosForUser(User user) {
      if (user.getUserName() != null) {
        log.append(user.getUserName());
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public boolean validatePortfolioUUID(String portfolioUUID, User user) {
      if (portfolioUUID != null && user != null) {
        log.append(portfolioUUID);
        log.append(user.getUserName());
      }
      return false;
    }

    @Override
    public User getUserFromUsername(String username) {
      if (username != null) {
        log.append(User.builder().userName(username).build());
      }
      return null;
    }

    @Override
    public boolean saveStock(User user, String portfolioUUID, String ticker, String noOfShares) {
      if (user != null && portfolioUUID != null && ticker != null && noOfShares != null) {
        log.append(user.getUserName());
        log.append(portfolioUUID);
        log.append(ticker);
        log.append(noOfShares);
        return true;
      }
      return false;
    }

    @Override
    public List<String> getPortfolioContents(User user, String portfolioUUID) {
      if (user != null && portfolioUUID != null) {
        log.append(user.getUserName());
        log.append(portfolioUUID);
        return new ArrayList<>();
      }
      return null;
    }

    @Override
    public Map<Integer, List<String>> calculateTotalValueOfAPortfolio(String certainDate, User user,
                                                                      String portfolioUUID) {
      if (certainDate != null && user != null && portfolioUUID != null) {
        log.append(certainDate);
        log.append(user);
        log.append(portfolioUUID);
        return new HashMap<>() {{
          put(1, Collections.singletonList("0.00"));
        }};
      }
      return null;
    }

    @Override
    public boolean validateUserPortfolioExternalPathAndContentsStructure(String filePath) {
      if (filePath != null) {
        log.append(filePath);
      }
      return false;
    }

    @Override
    public String saveExternalUserPortfolio(String filePath, User user) {
      if (filePath != null &&  user != null) {
        log.append(filePath);
        log.append(user.getUserName());
      }
      return null;
    }
  }

  private StockController controller;
  private StockModel model;
  private StockView view;
  private Reader in;
  private StringBuffer out;

  @Before
  public void setup() {
    out = new StringBuffer();
    model = new MockStockModelImpl();
    view = new StockViewImpl(out);
  }

  @Test
  public void testStart() {
    // login screen input
    //out = getLoginScreenInput();

    in = new StringReader("jj");

    // initialize
    controller = new StockControllerImpl(model, view, in);

    controller.start();

    //out = getUserNameInput();

    //controller = new StockControllerImpl(model, view, in, out);


    assertEquals("Invalid user input\nEnter Y/N :", out.toString());
    //assertEquals(view.getLoginScreenView(), new String(bytes.toByteArray()));
  }


}
