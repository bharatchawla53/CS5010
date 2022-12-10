package stockhw7.modelviewcontroller.pannel;

import java.awt.*;
import java.util.List;

import javax.swing.*;

public class RebalancePanel extends ModularPanel {

  private final JTextArea stockTextArea;
  private final JTextArea percentTextArea;
  private final JTextField stockInput;
  private final JTextField percentInput;

  /**
   * this is the constructor that calls super for the style and initializes the rest of the
   * JComponents.
   */
  public RebalancePanel() {
    super("RebalancePanel", new Dimension(250, 500),
            new GridLayout(12, 4, 5, 5));
    // 12 rows
    JLabel title = new JLabel();
    title.setText("     Rebalance Portfolio");

    JPanel row2 = new JPanel(new FlowLayout());
    JButton clearStocks = uniformButton("Clear");

    JPanel row3 = new JPanel(new FlowLayout());
    JLabel stock = new JLabel("Stock:");
    stockInput = shortUniformTextField("stockInput");
    JButton stockAdd = uniformButton("Add S");

    stockTextArea = new JTextArea();
    stockTextArea.setText("");

    JPanel row5 = new JPanel(new FlowLayout());
    JLabel percent = new JLabel("%:");
    percentInput = shortUniformTextField("percentInput");
    JButton percentAdd = uniformButton("Add %");

    percentTextArea = new JTextArea();
    percentTextArea.setText("");

    JPanel row8 = new JPanel(new FlowLayout());
    JLabel start = new JLabel("Date:");
    JTextField startInput = uniformTextField("startInput");

    JPanel row11 = new JPanel(new FlowLayout());
    JLabel amount = new JLabel("Amount:");
    JTextField amountInput = uniformTextField("amountInput");

    JPanel row12 = new JPanel(new FlowLayout());
    JButton go = uniformButton("Go");


    row2.add(clearStocks);
    row3.add(stock);
    row3.add(stockInput);
    row3.add(stockAdd);
    row5.add(percent);
    row5.add(percentInput);
    row5.add(percentAdd);
    row8.add(start);
    row8.add(startInput);
    row11.add(amount);
    row11.add(amountInput);
    row12.add(go);


    this.add(title);
    this.add(row2);
    this.add(row3);
    this.add(stockTextArea);
    this.add(row5);
    this.add(percentTextArea);
    this.add(row8);
    this.add(row11);
    this.add(row12);

  }

  /**
   * gets the stock text for the controller to use.
   *
   * @return stock text
   */

  public String getStockText() {
    return stockTextArea.getText();
  }

  /**
   * gets % text for the controller to use.
   *
   * @return % text
   */
  public String getPercentText() {
    return percentTextArea.getText();
  }

  /**
   * adds a given stock value to the list of stocks.
   *
   * @param stock      the stock to be added
   * @param stocksList
   */
  public void addStock(String stock, List<String> stocksList) {
    if (!stockTextArea.getText().equals("")) {
      String[] split = stockTextArea.getText().split(",");
      if (stocksList.size() == split.length) {
        JOptionPane.showMessageDialog(null, "Can't add new shares to the existing portfolio");
        stockInput.setEnabled(false);
        stockTextArea.setEnabled(false);
        return;
      }
    }

    String result = stockTextArea.getText().equals("")
            ? stockTextArea.getText() + stock
            : stockTextArea.getText() + ", " + stock;
    stockTextArea.setText(result);
    this.revalidate();
    this.repaint();
  }

  /**
   * adds a given percentage value to the list of %.
   *
   * @param percent the given percent
   */
  public void addPercent(Double percent) {
    String result = percentTextArea.getText().equals("")
            ? percentTextArea.getText() + percent
            : percentTextArea.getText() + ", " + percent;

    if (!result.equals("")) {
      String[] split = result.split(",");
      double totalPercent = 0.0;

      for (String weight : split) {
        totalPercent += Double.parseDouble(weight);
      }


      if (totalPercent <= 0) {
        JOptionPane.showMessageDialog(null, "Tow low");
      }
      if (totalPercent > 100) {
        result = result.substring(0, result.length() - String.valueOf(percent).length() - 2);
        percentInput.setEnabled(false);
        percentTextArea.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Tow high");
      }
    }

    percentTextArea.setText(result);
    this.revalidate();
    this.repaint();
  }

  /**
   * clears the text areas so that the user can make changes.
   */
  public void clear() {
    stockTextArea.setText("");
    percentTextArea.setText("");
    this.revalidate();
    this.repaint();
  }
}
