package stockhw6.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import stockhw6.controller.IStockGuiFeatures;

public class JFrameViewImpl extends JFrame implements IStockGuiView {

  private JPanel loginPanel, userOptionsPanel;
  private JLabel title, comboBoxDisplay;
  private JRadioButton r1, r2, u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
  private JTextField uName, symbol, noOfShares, date, commissionFees, capital, weightage, date2;
  private JButton uNameSubmitButton, buyStockButton, cancelBuyStockButton,
          saveBuyStockButton, sellStockButton, cancelSellStockButton, saveSellStockButton,
          costBasisButton, cancelCostBasisButton, portfolioValueButton, cancelPortfolioValueButton,
          fileOpenButton, saveUnameSubmitButton, addInvestmentStockButton, saveInvestmentStockButton,
          cancelInvestmentStockButton, generateGraphButton;
  private JFrame uOptionTwoFrame, portfolioValueFrame, barChartFrame;
  private ButtonGroup userOptionsBg;
  private JComboBox<String> portfolioIdCb;
  private JTable portfolioValueTable;
  private String uOptionNumber;
  private BarChartPanel chartPanel;

  public JFrameViewImpl() {
    super();
    setTitle("Welcome to flexible portfolio manager");
    setSize(770, 230);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // initial login screen panel
    loginPanel = new JPanel();
    //loginPanel.setLayout(new FlowLayout());
    //loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
    //loginPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

    // label for the panel
    title = new JLabel("Portfolio Manager");
    title.setFont(new Font("Arial", Font.PLAIN, 15));
    title.setHorizontalAlignment(JLabel.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);
    //title.setSize(300, 30);
    //title.setLocation(300, 100);
    loginPanel.add(title);
    this.add(loginPanel);

    // radio buttons for type of user
    r1 = new JRadioButton("Y");
    r2 = new JRadioButton("N");

    // text button to submit username
    uNameSubmitButton = new JButton("submit");
    uNameSubmitButton.setActionCommand("submit button");

    // radio buttons for flexible portfolio functionality
    u1 = new JRadioButton("Would you like to create a portfolio with shares of one or more stock on a specific date?");
    u1.setActionCommand("1");
    u2 = new JRadioButton("Would you like to sell a specific number of shares of a specific stock on a specified date from a given portfolio?");
    u2.setActionCommand("2");
    u3 = new JRadioButton("Would you like to determine the cost basis of a portfolio by a specific date?");
    u3.setActionCommand("3");
    u4 = new JRadioButton("Would you like to determine the total value of a portfolio on a certain date?");
    u4.setActionCommand("4");
    u5 = new JRadioButton("Would you like to persist a portfolio so that it can be saved?");
    u5.setActionCommand("5");
    u6 = new JRadioButton("Would you like to load an external portfolio?");
    u6.setActionCommand("6");
    u7 = new JRadioButton("Would you like to buy a specific number of shares of one or more stock on a specific date from a given portfolio?");
    u7.setActionCommand("7");
    u8 = new JRadioButton("Would you like to invest a specific amount by specifying the weights on a specific date from a given portfolio?");
    u8.setActionCommand("8");
    u9 = new JRadioButton("Would you like to create a portfolio using dollar-cost averaging?");
    u9.setActionCommand("9");
    u10 = new JRadioButton("Would you like to analyze how your portfolio has performed over time?");
    u10.setActionCommand("10");

    // text field to take buy stock inputs
    symbol = new JTextField(8);
    noOfShares = new JTextField(8);
    date = new JTextField(10);
    commissionFees = new JTextField(8);
    date2 = new JTextField(10);
    capital = new JTextField(10);
    weightage = new JTextField(5);

    // text button to buy stock
    buyStockButton = new JButton("Buy");
    buyStockButton.setActionCommand("buy");

    // text button to cancel buy stock frame
    cancelBuyStockButton = new JButton("Cancel");
    cancelBuyStockButton.setActionCommand("cancel buy");

    // text button to save portfolio
    saveBuyStockButton = new JButton("Save");
    saveBuyStockButton.setActionCommand("save buy");

    // combo box for portfolioIds
    portfolioIdCb = new JComboBox<>();

    // text button to sell stock
    sellStockButton = new JButton("Sell");
    sellStockButton.setActionCommand("sell");

    // text button to cancel sell stock frame
    cancelSellStockButton = new JButton("Cancel");
    cancelSellStockButton.setActionCommand("cancel sell");

    // text button to save sell stock portfolio
    saveSellStockButton = new JButton("Save");
    saveSellStockButton.setActionCommand("save sell");

    // text button to calculate cost basis
    costBasisButton = new JButton("Calculate");
    costBasisButton.setActionCommand("cost basis");

    // text button to cancel calculate cost basis
    cancelCostBasisButton = new JButton("Cancel");
    cancelCostBasisButton.setActionCommand("cancel cost basis");

    // text button to calculate portfolio value
    portfolioValueButton = new JButton("Calculate");
    portfolioValueButton.setActionCommand("portfolio value");

    // text button to cancel calculate cost basis
    cancelPortfolioValueButton = new JButton("Cancel");
    cancelPortfolioValueButton.setActionCommand("cancel portfolio value");

    // button to open a external portfolio file
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");

    // text button to submit username
    saveUnameSubmitButton = new JButton("Submit");
    saveUnameSubmitButton.setActionCommand("submit new user button");

    // text button to add stock and its weightage for investment
    addInvestmentStockButton = new JButton("Add");
    addInvestmentStockButton.setActionCommand("add investment");

    // text button to save all stock and its weightage for investment
    saveInvestmentStockButton = new JButton("Save");
    saveInvestmentStockButton.setActionCommand("save investment");

    // text button to cancel stock and its weightage for investment
    cancelInvestmentStockButton = new JButton("Cancel");
    cancelInvestmentStockButton.setActionCommand("cancel investment");

    // text button to generate graph
    generateGraphButton = new JButton("Generate Bar Chart");
    generateGraphButton.setActionCommand("generate");

    //pack();
    setVisible(true);
  }

  @Override
  public void getLoginScreenView() {
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Login Panel"));
    //radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));
    radioPanel.setLayout(new FlowLayout());

    JLabel l = new JLabel("Are you a existing user or would you like to create a new user ?");

    ButtonGroup bg = new ButtonGroup();
    bg.add(r1);
    bg.add(r2);

    radioPanel.add(l);
    radioPanel.add(r1);
    radioPanel.add(r2);

    loginPanel.add(radioPanel);
    this.add(loginPanel);
    setVisible(true);

  }

  @Override
  public void getUserOptionsView(String userName) {
    // remove previous panel
    this.remove(loginPanel);
    // add new user options panel
    userOptionsPanel = new JPanel();
    userOptionsPanel.setLayout(new BoxLayout(userOptionsPanel, BoxLayout.PAGE_AXIS));

    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Welcome " + userName + " to Flexible Portfolio Panel"));
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JLabel l = new JLabel("Select one of the options from below");

    userOptionsBg = new ButtonGroup();
    userOptionsBg.add(u1);
    userOptionsBg.add(u7);
    userOptionsBg.add(u2);
    userOptionsBg.add(u3);
    userOptionsBg.add(u4);
    userOptionsBg.add(u5);
    userOptionsBg.add(u6);
    userOptionsBg.add(u8);
    userOptionsBg.add(u9);
    userOptionsBg.add(u10);

    radioPanel.add(l);
    radioPanel.add(u1);
    radioPanel.add(u7);
    radioPanel.add(u2);
    radioPanel.add(u3);
    radioPanel.add(u4);
    radioPanel.add(u5);
    radioPanel.add(u6);
    radioPanel.add(u8);
    radioPanel.add(u9);
    radioPanel.add(u10);

    userOptionsPanel.add(radioPanel);
    this.add(userOptionsPanel);
    this.setSize(770, 310);
    setVisible(true);
  }

  @Override
  public void getExistingUserView() {
    r1.setEnabled(false);
    r2.setEnabled(false);

    JLabel l = new JLabel("Enter your username: ");
    uName = new JTextField(8);

    JPanel panel = new JPanel();
    panel.add(l);
    panel.add(uName);
    panel.add(uNameSubmitButton);

    loginPanel.add(panel);
    this.add(loginPanel);
    setVisible(true);
  }

  @Override
  public void getNewUserView() {
    r1.setEnabled(false);
    r2.setEnabled(false);

    JLabel l = new JLabel("Please enter an username and can't be longer than 8 characters: ");
    uName = new JTextField(8);

    JPanel panel = new JPanel();
    panel.add(l);
    panel.add(uName);
    panel.add(saveUnameSubmitButton);

    loginPanel.add(panel);
    this.add(loginPanel);
    setVisible(true);
  }

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(loginPanel, message, "Portfolio Manager", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addGuiFeatures(IStockGuiFeatures features) {
    r1.addActionListener(features::getLoginScreenView);
    r2.addActionListener(features::getLoginScreenView);
    uNameSubmitButton.addActionListener(evt -> features.getUserName(uName.getText()));
    saveUnameSubmitButton.addActionListener(evt -> features.saveNewUser(uName.getText(), evt));
    u1.addActionListener(evt -> {
      features.getUserOptionsView(evt);
      //uOptionTwoFrame.dispose();
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
    });
    u2.addActionListener(features::getUserOptionsView);
    u3.addActionListener(features::getUserOptionsView);
    u4.addActionListener(features::getUserOptionsView);
    u5.addActionListener(features::getUserOptionsView);
    u6.addActionListener(features::getUserOptionsView);
    u7.addActionListener(features::getUserOptionsView);
    u8.addActionListener(features::getUserOptionsView);
    u9.addActionListener(features::getUserOptionsView);
    u10.addActionListener(features::getUserOptionsView);
    buyStockButton.addActionListener(evt -> features.processFlexibleOptionOne(
            symbol.getText() + "," + noOfShares.getText() + ","
                    + date.getText() + "," + commissionFees.getText(), evt
    ));
    saveBuyStockButton.addActionListener(evt -> features.processFlexibleOptionOne(null, evt));
    cancelBuyStockButton.addActionListener(evt -> {
      uOptionTwoFrame.dispose();
      symbol.setText(null);
      noOfShares.setText(null);
      date.setText(null);
      commissionFees.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    portfolioIdCb.addItemListener(evt -> {
      Object item = evt.getItem();
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        portfolioIdCb.setEnabled(false);
        switch (uOptionNumber) {
          case "2" :
            features.getPortfolioIdInputForOptionTwo(String.valueOf(item));
            break;
          case "3":
            features.getPortfolioIdInputForOptionThree(String.valueOf(item));
            break;
          case "4":
            features.getPortfolioIdInputForOptionFour(String.valueOf(item));
            break;
          case "5":
            features.getPortfolioIdInputForOptionFive(String.valueOf(item));
            break;
          case "7":
            features.getPortfolioIdInputForOptionSeven(String.valueOf(item));
            break;
          case "8":
            features.getPortfolioIdInputForOptionEight(String.valueOf(item));
            break;
          case "10":
            features.getPortfolioIdInputForOptionTen(String.valueOf(item));
        }
      }
    });
    sellStockButton.addActionListener(evt -> features.processFlexibleOptionTwo(
            symbol.getText() + "," + noOfShares.getText() + ","
                    + date.getText() + "," + commissionFees.getText(), evt
    ));
    saveSellStockButton.addActionListener(evt -> features.processFlexibleOptionTwo(null, evt));
    cancelSellStockButton.addActionListener(evt -> {
      uOptionTwoFrame.dispose();
      symbol.setText(null);
      noOfShares.setText(null);
      date.setText(null);
      commissionFees.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    costBasisButton.addActionListener(evt -> features.processFlexibleOptionThree(date.getText(), evt));
    cancelCostBasisButton.addActionListener(evt -> {
      uOptionTwoFrame.dispose();
      date.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    portfolioValueButton.addActionListener(evt -> {
      features.processFlexibleOptionFour(date.getText(), evt);
      uOptionTwoFrame.dispose();
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
      date.setText(null);
    });
    cancelPortfolioValueButton.addActionListener(evt -> {
      uOptionTwoFrame.dispose();
      date.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    fileOpenButton.addActionListener(evt -> {
      final JFileChooser fChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
      fChooser.setFileFilter(filter);
      int retValue = fChooser.showOpenDialog(uOptionTwoFrame);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = fChooser.getSelectedFile();
        //fileOpenDisplay.setText(f.getAbsolutePath());
        features.getFilePathForOptionSix(f.getAbsolutePath());
      } else if (retValue == JFileChooser.CANCEL_OPTION) {
        uOptionTwoFrame.dispose();
        userOptionsBg.clearSelection();
      }
    });
    addInvestmentStockButton.addActionListener(evt -> {
      features.processFlexibleOptionEight(date.getText(), capital.getText(),
              symbol.getText(), weightage.getText(), commissionFees.getText(), evt);
      date.setEnabled(false);
      capital.setEnabled(false);
      symbol.setText(null);
      weightage.setText(null);
    });
    saveInvestmentStockButton.addActionListener(evt -> {
      features.processFlexibleOptionEight(date.getText(), date.getText(), capital.getText(),
              symbol.getText(), weightage.getText(), evt);
      uOptionTwoFrame.dispose();
      date.setText(null);
      capital.setText(null);
      symbol.setText(null);
      weightage.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    cancelInvestmentStockButton.addActionListener(evt -> {
      uOptionTwoFrame.dispose();
      date.setText(null);
      capital.setText(null);
      symbol.setText(null);
      weightage.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
    generateGraphButton.addActionListener(evt -> {
      features.processFlexibleOptionTen(date.getText(), date2.getText(), evt);
      uOptionTwoFrame.dispose();
      date.setText(null);
      date2.setText(null);
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    });
  }

  @Override
  public void flexibleUserOptionOne() {
    /*uOptionOneFrame = new JFrame("Buy Stocks");
    uOptionOneFrame.setSize(350,150);
    uOptionOneFrame.setLocation(225, 225);
    uOptionOneFrame.setVisible(true);*/

    //if (uOptionTwoFrame == null) {
      setUpFrame("Buy Stocks");
    //}

    JPanel panel = getPanelForBuyAndSell();
    panel.add(buyStockButton);
    panel.add(saveBuyStockButton);
    panel.add(cancelBuyStockButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void flexibleUserOptionTwo() {
    JPanel panel = getPanelForBuyAndSell();
    panel.add(sellStockButton);
    panel.add(saveSellStockButton);
    panel.add(cancelSellStockButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setSize(350,240);
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void flexibleUserOptionThree() {
    JLabel l = new JLabel("Enter date (yyyy-MM-dd): ");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 2));
    panel.add(l);
    panel.add(date);
    panel.add(costBasisButton);
    panel.add(cancelCostBasisButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void flexibleUserOptionFour() {
    JLabel l = new JLabel("Enter date (yyyy-MM-dd): ");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 2));
    panel.add(l);
    panel.add(date);
    panel.add(portfolioValueButton);
    panel.add(cancelPortfolioValueButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void flexibleUserOptionFiveAndSix(String message) {
    int input = JOptionPane.showConfirmDialog(uOptionTwoFrame,
            message, "Portfolio Saved!", JOptionPane.DEFAULT_OPTION);
    // 0=ok
    if (input == JOptionPane.OK_OPTION) {
      uOptionTwoFrame.dispose();
      portfolioIdCb.removeAllItems();
      portfolioIdCb.setEnabled(true);
      userOptionsBg.clearSelection();
    }
  }

  @Override
  public void flexibleUserOptionEight() {
    setUpFrame("Dollar-Cost Averaging");
    JLabel l1 = new JLabel("Enter date (yyyy-MM-dd): ");
    JLabel l2 = new JLabel("Enter the amount for investment: ");
    JLabel l3 = new JLabel("Enter stock symbol: ");
    JLabel l4 = new JLabel("Enter stock weightage: ");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(7, 3));
    panel.add(l1);
    panel.add(date);
    panel.add(l2);
    panel.add(capital);
    panel.add(l3);
    panel.add(symbol);
    panel.add(l4);
    panel.add(weightage);
    panel.add(addInvestmentStockButton);
    panel.add(saveInvestmentStockButton);
    panel.add(cancelInvestmentStockButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setSize(new Dimension(450, 250));
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void flexibleUserOptionTen() {
    setUpFrame("Portfolio Performance");
    JLabel l1 = new JLabel("Enter date1 (yyyy-MM-dd): ");
    JLabel l2 = new JLabel("Enter date2 (yyyy-MM-dd): ");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 3));
    panel.add(l1);
    panel.add(date);
    panel.add(l2);
    panel.add(date2);
    panel.add(generateGraphButton);

    uOptionTwoFrame.add(panel);
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void showSuccessMessage(String message, ActionEvent evt) {
    switch (evt.getActionCommand()) {
      case "buy":
      case "sell":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        symbol.setText(null);
        noOfShares.setText(null);
        date.setText(null);
        commissionFees.setText(null);
        break;
      case "save buy":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        symbol.setText(null);
        noOfShares.setText(null);
        date.setText(null);
        commissionFees.setText(null);
        uOptionTwoFrame.setVisible(false);
        userOptionsBg.clearSelection();
        break;
      case "save sell":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        symbol.setText(null);
        noOfShares.setText(null);
        date.setText(null);
        commissionFees.setText(null);
        uOptionTwoFrame.setVisible(false);
        portfolioIdCb.removeAllItems();
        portfolioIdCb.setEnabled(true);
        userOptionsBg.clearSelection();
        break;
      case "cost basis":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        date.setText(null);
        uOptionTwoFrame.setVisible(false);
        portfolioIdCb.removeAllItems();
        portfolioIdCb.setEnabled(true);
        userOptionsBg.clearSelection();
        break;
      case "submit new user button":
        JOptionPane.showMessageDialog(this, message);
        break;
      case "generate":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        date.setText(null);
        date2.setText(null);
        uOptionTwoFrame.setVisible(false);
        portfolioIdCb.removeAllItems();
        portfolioIdCb.setEnabled(true);
        userOptionsBg.clearSelection();
        break;
      case "add investment":
        JOptionPane.showMessageDialog(uOptionTwoFrame, message);
        break;
    }
  }

  @Override
  public void comboBoxBuilder(List<String> data, String title, String uOption) {
    this.uOptionNumber = uOption;
    setUpFrame(title);

    comboBoxDisplay = new JLabel();
    comboBoxDisplay.setText("Select a portfolio ID");
    comboBoxDisplay.setHorizontalAlignment(JLabel.LEFT);
    comboBoxDisplay.setSize(400, 100);

    portfolioIdCb.addItem(null);
    data.forEach(item -> portfolioIdCb.addItem(item));
    portfolioIdCb.setBounds(100, 100, 200, 20);

    uOptionTwoFrame.add(comboBoxDisplay);
    uOptionTwoFrame.add(portfolioIdCb);

    uOptionTwoFrame.setVisible(true);
  }

  private void setUpFrame(String title) {
    if (uOptionTwoFrame != null) {
      uOptionTwoFrame.dispose();
    }
    uOptionTwoFrame = new JFrame(title);
    uOptionTwoFrame.setSize(350,210);
    uOptionTwoFrame.setLocation(225, 225);
    uOptionTwoFrame.setLayout(new FlowLayout());
    uOptionTwoFrame.setVisible(true);
  }

  @Override
  public void portfolioValueTableView(List<String> data, List<String> columns, double totalPortfolioValueSum) {
    Object[][] rows = new Object[data.size()][data.size() * 3];
    for (int i = 0; i < data.size(); i++) {
      String[] split = data.get(i).split(",");
      for (int j = 0; j < split.length; j++) {
        rows[i][j] = split[j];
      }
    }

    portfolioValueFrame = new JFrame();
    portfolioValueFrame.setSize(800,500);
    portfolioValueFrame.setLocation(225, 225);
    portfolioValueFrame.setLayout(new BorderLayout());
    portfolioValueFrame.setVisible(true);

    portfolioValueTable = new JTable(rows, columns.toArray());
    portfolioValueTable.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(portfolioValueTable);

    JLabel sumLabel = new JLabel();
    sumLabel.setText("Total value : ");
    sumLabel.setFont(new Font("Arial", Font.PLAIN, 20));

    sumLabel.setHorizontalAlignment(JLabel.CENTER);

    JTextField textField = new JTextField();
    textField.setText("$" + totalPortfolioValueSum);
    textField.setEditable(false);

    portfolioValueFrame.add(scrollPane, BorderLayout.SOUTH);
    portfolioValueFrame.add(sumLabel, BorderLayout.NORTH);
    portfolioValueFrame.add(textField, BorderLayout.EAST);
    portfolioValueFrame.setVisible(true);
  }

  @Override
  public void getExternalPortfolioFilePath() {
    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));

    // frame
    uOptionTwoFrame = new JFrame();
    uOptionTwoFrame.setSize(350,210);
    uOptionTwoFrame.setLocation(225, 225);
    uOptionTwoFrame.setLayout(new FlowLayout());
    uOptionTwoFrame.setVisible(true);
    uOptionTwoFrame.add(dialogBoxesPanel);

    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    fileOpenPanel.add(fileOpenButton);
    uOptionTwoFrame.setVisible(true);
  }

  @Override // TODO add scale to bar chart
  public void showPerformanceGraph(Map<String, Integer> barChartContents) {
    this.barChartFrame = new JFrame();
    this.barChartFrame.setSize(800, 500);
    this.barChartFrame.setTitle("BarChart");

    this.chartPanel = new BarChartPanel(barChartContents, "Dates", "Stock Price");

    JLabel title = new JLabel("Portfolio Performance Overview");
    title.setFont(new Font("Arial", Font.BOLD, 25));
    title.setHorizontalAlignment(JLabel.CENTER);

    this.barChartFrame.setLayout(new BorderLayout(2, 2));
    this.barChartFrame.add(title, BorderLayout.NORTH);
    this.barChartFrame.add(chartPanel, BorderLayout.CENTER);
    this.barChartFrame.setVisible(true);

  }

  private JPanel getPanelForBuyAndSell() {
    JLabel l1 = new JLabel("Enter stock symbol: ");
    JLabel l2 = new JLabel("Enter number of shares: ");
    JLabel l3 = new JLabel("Enter date (yyyy-MM-dd): ");
    JLabel l4 = new JLabel("Enter commission fees: ");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(7, 2));
    panel.add(l1);
    panel.add(symbol);
    panel.add(l2);
    panel.add(noOfShares);
    panel.add(l3);
    panel.add(date);
    panel.add(l4);
    panel.add(commissionFees);
    return panel;
  }
}
