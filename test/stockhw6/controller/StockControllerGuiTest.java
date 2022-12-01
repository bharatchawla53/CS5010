package stockhw6.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import stockhw6.model.IStockModelMaker;
import stockhw6.model.User;
import stockhw6.view.IStockGuiView;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for StockControllerGuiImpl.
 */
@RunWith(MockitoJUnitRunner.class)
public class StockControllerGuiTest {

  @InjectMocks
  private StockControllerGuiImpl controller;

  @Mock
  private IStockModelMaker model;

  @Mock
  private IStockGuiView view;

  private final int uniqueId = 12;

  @Before
  public void setup() {
    controller.setView(view);
  }

  @Test
  public void testGetLoginScreenExistingUserView() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInput.Y.name());
    controller.getLoginScreenView(evt);

    verify(view, times(1)).getExistingUserView();
  }

  @Test
  public void testGetLoginScreenNewUserView() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInput.N.name());
    controller.getLoginScreenView(evt);

    verify(view, times(1)).getNewUserView();
  }

  @Test
  public void testGetExistingUserName() {
    when(model.isUserNameExists(any())).thenReturn(Boolean.TRUE);
    when(model.getUserFromUsername(any())).thenReturn(User.builder().build());

    controller.getUserName(any());

    verify(model, times(1)).isUserNameExists(any());
    verify(model, times(1)).getUserFromUsername(any());
    verify(view, times(1)).getUserOptionsView(any());
  }

  @Test
  public void testGetExistingUserNameNotExists() {
    when(model.isUserNameExists(any())).thenReturn(Boolean.FALSE);

    controller.getUserName(any());

    verify(model, times(1)).isUserNameExists(any());
    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testValidSaveNewUser() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInput.N.name());
    User user = User.builder().userName("test").build();
    when(model.saveUser(any())).thenReturn(user);

    controller.saveNewUser("test", evt);

    verify(model, times(1)).saveUser(any());
    verify(view, times(1)).showSuccessMessage(anyString(), any());
    verify(view, times(1)).getUserOptionsView(any());
  }

  @Test
  public void testUserNameExistsSaveNewUser() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInput.N.name());
    when(model.saveUser(any())).thenReturn(null);

    controller.saveNewUser("test", evt);

    verify(model, times(1)).saveUser(any());
    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testUserNameLengthGreaterThanEightExistsSaveNewUser() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInput.N.name());
    when(model.saveUser(any())).thenReturn(null);

    controller.saveNewUser("test12345", evt);

    verify(model, times(1)).saveUser(any());
    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testGetUserOptionsViewOne() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.ONE.getInput());
    when(model.generateUUID()).thenReturn("1234");

    controller.getUserOptionsView(evt);

    verify(view, times(1)).flexibleUserOptionOne();
    verify(model, times(1)).generateUUID();
  }

  @Test
  public void testGetUserOptionsViewTwo() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.TWO.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Sell Stocks", UserInputOptions.TWO.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewThree() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.THREE.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Cost Basis", UserInputOptions.THREE.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewFour() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.FOUR.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Portfolio Value",
                    UserInputOptions.FOUR.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewFive() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.FIVE.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Save Portfolio", UserInputOptions.FIVE.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewSix() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.SIX.getInput());

    controller.getUserOptionsView(evt);

    verify(view, times(1)).getExternalPortfolioFilePath();
  }


  @Test
  public void testGetUserOptionsViewSeven() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.SEVEN.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Buy Stocks", UserInputOptions.SEVEN.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewEight() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.EIGHT.getInput());
    //when(model.getPortfoliosForUser(any())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Existing Portfolio Investment",
                    UserInputOptions.EIGHT.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testGetUserOptionsViewNine() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.NINE.getInput());

    controller.getUserOptionsView(evt);

    verify(view, times(1)).flexibleUserOptionsNine();
  }

  @Test
  public void testGetUserOptionsViewTen() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.TEN.getInput());
    //when(model.getPortfoliosForUser(User.builder().build())).thenReturn(new ArrayList<>());

    controller.getUserOptionsView(evt);

    verify(view, times(1))
            .comboBoxBuilder(new ArrayList<>(), "Portfolio Performance",
                    UserInputOptions.TEN.getInput());
    verify(model, times(1)).getPortfoliosForUser(any());
  }

  @Test
  public void testProcessFlexibleOptionOneBuy() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "buy");
    String input = "TSLA,10,2022-11-10,150";

    when(model.validateFlexibleTickerShare(input)).thenReturn(Boolean.TRUE);
    when(model.isValidTicker(input.split(",")[0])).thenReturn(Boolean.TRUE);
    when(model.saveFlexibleStock(null, null, input.split(",")[0],
            input.split(",")[1], input.split(",")[2], 150))
            .thenReturn(Boolean.TRUE);

    controller.processFlexibleOptionOne(input, evt);

    verify(view, times(1)).showSuccessMessage(any(), any());
    verify(model, times(1)).validateFlexibleTickerShare(anyString());
    verify(model, times(1)).isValidTicker(any());
    verify(model, times(1)).saveFlexibleStock(null, null,
            input.split(",")[0], input.split(",")[1],
            input.split(",")[2], 150);
  }

  @Test
  public void testProcessFlexibleOptionOneBuyFails() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "buy");
    String input = "TSLA,10,2022-11-10,150";

    when(model.validateFlexibleTickerShare(input)).thenReturn(Boolean.FALSE);
    //when(model.isValidTicker(input.split(",")[0])).thenReturn(Boolean.FALSE);

    controller.processFlexibleOptionOne(input, evt);

    verify(view, times(1)).showErrorMessage(any());
    verify(model, times(1)).validateFlexibleTickerShare(any());
  }

  @Test
  public void testProcessFlexibleOptionOneBuySave() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "save buy");
    String input = "TSLA,10,2022-11-10,150";

    controller.processFlexibleOptionOne(input, evt);

    verify(view, times(1)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionTwoSell() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "sell");
    String input = "TSLA,10,2022-11-10,150";

    when(model.validateFlexibleTickerShare(input)).thenReturn(Boolean.TRUE);
    when(model.isValidTicker(input.split(",")[0])).thenReturn(Boolean.TRUE);
    when(model.sellFlexibleStock(null, null, input.split(",")[0],
            input.split(",")[1], input.split(",")[2], 150))
            .thenReturn(Boolean.TRUE);

    controller.processFlexibleOptionTwo(input, evt);

    verify(view, times(1)).showSuccessMessage(any(), any());
    verify(model, times(1)).validateFlexibleTickerShare(anyString());
    verify(model, times(1)).isValidTicker(any());
    verify(model, times(1)).sellFlexibleStock(null, null,
            input.split(",")[0], input.split(",")[1],
            input.split(",")[2], 150);
  }

  @Test
  public void testProcessFlexibleOptionTwoSellFails() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "sell");
    String input = "TSLA,10,2022-11-10,150";

    when(model.validateFlexibleTickerShare(input)).thenReturn(Boolean.FALSE);

    controller.processFlexibleOptionTwo(input, evt);

    verify(view, times(1)).showErrorMessage(any());
    verify(model, times(1)).validateFlexibleTickerShare(any());
  }

  @Test
  public void testProcessFlexibleOptionTwoSellSave() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "save sell");
    String input = "TSLA,10,2022-11-10,150";

    controller.processFlexibleOptionTwo(input, evt);

    verify(view, times(1)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionThree() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "cost basis");
    when(model.calculateCostBasis(null, null, "2022-11-10")).thenReturn(100.00);

    controller.processFlexibleOptionThree("2022-11-10", evt);

    verify(view, times(1)).showSuccessMessage(any(), any());
    verify(model, times(1)).calculateCostBasis(null, null, "2022-11-10");
  }

  @Test
  public void testProcessFlexibleOptionThreeFails() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "cost basis");

    controller.processFlexibleOptionThree(null, evt);

    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testProcessFlexibleOptionFour() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "portfolio value");
    Map<Integer, List<String>> map = new HashMap<>();
    List<String> res = new ArrayList<>();
    res.add("TSLA,10,250");
    map.put(1, res);

    when(model.calculateTotalValueOfAFlexiblePortfolio("2022-11-10", null, null))
            .thenReturn(map);

    controller.processFlexibleOptionFour("2022-11-10", evt);

    verify(model, times(1))
            .calculateTotalValueOfAFlexiblePortfolio("2022-11-10", null, null);
    verify(view, times(1))
            .portfolioValueTableView(anyList(), anyList(), anyDouble());

  }

  @Test
  public void testProcessFlexibleOptionFourFails() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "portfolio value");

    controller.processFlexibleOptionFour(null, evt);

    verify(view, times(1)).showErrorMessage(any());

  }

  @Test
  public void testProcessFlexibleOptionEightAdd() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.EIGHT.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "add investment");
    when(model.isValidTicker("TSLA")).thenReturn(Boolean.TRUE);
    when(model.isValidTicker("MSFT")).thenReturn(Boolean.TRUE);

    controller.processFlexibleOptionEight("2022-11-10", "1000",
            "TSLA", "25", "10", evt1);

    controller.processFlexibleOptionEight("2022-11-12", "1000",
            "MSFT", "25", "20", evt1);

    verify(model, times(2)).isValidTicker(anyString());
    verify(view, times(2)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionEightAddInvalidSymbol() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.EIGHT.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "add investment");
    when(model.isValidTicker("vfvdfbdf")).thenReturn(Boolean.FALSE);

    controller.processFlexibleOptionEight("2022-11-10", "1000",
            "vfvdfbdf", "25", "10", evt1);

    verify(model, times(1)).isValidTicker(anyString());
    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testProcessFlexibleOptionEightAddSave() {
    ActionEvent evtU = new ActionEvent(this, uniqueId, UserInput.N.name());
    User user = User.builder().userName("test").build();
    when(model.saveUser(any())).thenReturn(user);

    controller.saveNewUser("test", evtU);

    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.EIGHT.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "add investment");
    when(model.isValidTicker("TSLA")).thenReturn(Boolean.TRUE);
    when(model.isValidTicker("MSFT")).thenReturn(Boolean.TRUE);
    /*when(model.updatePortfolioBasedOnInvestment(null, null,
            Arrays.asList("TSLA", "MSFT"), "2022-11-10", 1000,
            Arrays.asList(25, 25), 0))
            .thenReturn(Boolean.TRUE);*/

    controller.processFlexibleOptionEight("2022-11-10", "1000",
            "TSLA", "25", "10", evt1);

    controller.processFlexibleOptionEight("2022-11-12", "1000",
            "MSFT", "25", "20", evt1);

    ActionEvent evt2 = new ActionEvent(this, uniqueId, "save investment");
    controller.processFlexibleOptionEight("2022-11-10", "1000", "",
            "", "", evt2);

    verify(model, times(2)).isValidTicker(anyString());
    verify(view, times(3)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionEightNoInput() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "add investment");

    controller.processFlexibleOptionEight("", "",
            "", "", "", evt);

    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testProcessFlexibleOptionNineAdd() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.NINE.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "new investment");
    when(model.isValidTicker("TSLA")).thenReturn(Boolean.TRUE);
    when(model.isValidTicker("MSFT")).thenReturn(Boolean.TRUE);

    controller.processFlexibleOptionNine("1000", "TSLA", "25",
            "2022-11-10", "2023-11-10", "25", "days",
            "10", evt1);

    controller.processFlexibleOptionNine("1000", "MSFT", "25",
            "2022-11-10", "2023-11-10", "25", "days",
            "10", evt1);

    verify(model, times(2)).isValidTicker(anyString());
    verify(view, times(2)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionNineAddInvalidSymbol() {
    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.NINE.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "add investment");
    when(model.isValidTicker("vfvdfbdf")).thenReturn(Boolean.FALSE);


    controller.processFlexibleOptionNine("1000", "vfvdfbdf", "25",
            "2022-11-10", "2023-11-10", "25", "days",
            "10", evt1);

    verify(model, times(1)).isValidTicker(anyString());
    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testProcessFlexibleOptionNineAddSave() {
    ActionEvent evtU = new ActionEvent(this, uniqueId, UserInput.N.name());
    User user = User.builder().userName("test").build();
    when(model.saveUser(any())).thenReturn(user);

    controller.saveNewUser("test", evtU);

    ActionEvent evt = new ActionEvent(this, uniqueId, UserInputOptions.NINE.getInput());
    controller.getUserOptionsView(evt);

    ActionEvent evt1 = new ActionEvent(this, uniqueId, "new investment");
    when(model.isValidTicker("TSLA")).thenReturn(Boolean.TRUE);
    when(model.isValidTicker("MSFT")).thenReturn(Boolean.TRUE);

    controller.processFlexibleOptionNine("1000", "TSLA", "25",
            "2022-11-10", "2023-11-10", "25", "days",
            "10", evt1);

    controller.processFlexibleOptionNine("1000", "MSFT", "25",
            "2022-11-10", "2023-11-10", "25", "days",
            "10", evt1);

    ActionEvent evt2 = new ActionEvent(this, uniqueId, "save investment");

    controller.processFlexibleOptionNine("1000", "", "",
            "", "", "", "", "", evt1);

    verify(model, times(2)).isValidTicker(anyString());
    verify(view, times(3)).showSuccessMessage(any(), any());
  }

  @Test
  public void testProcessFlexibleOptionNineNoInput() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "new investment");

    controller.processFlexibleOptionNine("", "", "", "", "",
            "", "", "", evt);

    verify(view, times(1)).showErrorMessage(any());
  }

  @Test
  public void testProcessFlexibleOptionTen() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "generate");

    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("2022-11-10", 8);
    when(model.getBarChartContents("2022-11-10", "2022-11-30", null, null))
            .thenReturn(map);

    controller.processFlexibleOptionTen("2022-11-10", "2022-11-30", evt);

    verify(model, times(1)).getBarChartContents("2022-11-10",
            "2022-11-30", null, null);
    verify(view, times(1)).showPerformanceGraph(anyMap());
  }

  @Test
  public void testProcessFlexibleOptionTenFail() {
    ActionEvent evt = new ActionEvent(this, uniqueId, "generate");

    when(model.getBarChartContents("2022-11-10", "2022-11-30", null, null))
            .thenReturn(new LinkedHashMap<>());

    controller.processFlexibleOptionTen("2022-11-10", "2022-11-30", evt);

    verify(model, times(1)).getBarChartContents("2022-11-10",
            "2022-11-30", null, null);
    verify(view, times(1)).showSuccessMessage(any(), any());
  }

  @Test
  public void testGetPortfolioId() {
    String portfolioId = "test";

    ActionEvent evtU = new ActionEvent(this, uniqueId, UserInput.N.name());
    User user = User.builder().userName("test").build();
    when(model.saveUser(any())).thenReturn(user);

    controller.saveNewUser("test", evtU);

    when(model.validatePortfolioUUID(portfolioId, user)).thenReturn(Boolean.TRUE);

    controller.getPortfolioIdInputForOptionTwo(portfolioId);
    controller.getPortfolioIdInputForOptionThree(portfolioId);
    controller.getPortfolioIdInputForOptionFour(portfolioId);
    controller.getPortfolioIdInputForOptionFive(portfolioId);
    controller.getPortfolioIdInputForOptionSeven(portfolioId);
    controller.getPortfolioIdInputForOptionEight(portfolioId);
    controller.getPortfolioIdInputForOptionTen(portfolioId);


    verify(view).flexibleUserOptionOne();
    verify(view).flexibleUserOptionTwo();
    verify(view).flexibleUserOptionThree();
    verify(view).flexibleUserOptionFour();
    verify(view).flexibleUserOptionFiveAndSix(any());
    verify(view).flexibleUserOptionEight();
    verify(view).flexibleUserOptionTen();
  }

  @Test
  public void testGetFilePathForOptionSix() {
    ActionEvent evtU = new ActionEvent(this, uniqueId, UserInput.N.name());
    User user = User.builder().userName("test").build();
    when(model.saveUser(any())).thenReturn(user);

    controller.saveNewUser("test", evtU);

    String filePath = "test.csv";
    when(model.validateFlexibleUserPortfolioExternalPathAndContentsStructure(filePath))
            .thenReturn(Boolean.TRUE);
    when(model.saveExternalFlexibleUserPortfolio(filePath, user))
            .thenReturn("random");

    controller.getFilePathForOptionSix(filePath);

    verify(model).saveUser(any());
    verify(model).validateFlexibleUserPortfolioExternalPathAndContentsStructure(anyString());
    verify(model).saveExternalFlexibleUserPortfolio(anyString(), any());
    verify(view).flexibleUserOptionFiveAndSix(any());
  }

  @Test
  public void testGetFilePathForOptionSixFail() {
    controller.getFilePathForOptionSix(null);

    verify(view).showErrorMessage(any());
  }
}
