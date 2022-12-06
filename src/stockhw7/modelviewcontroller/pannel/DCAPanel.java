package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * This is the panel for Createing a Dolar Cost Average flexible
 * portfolio stock.
 */
public class DCAPanel extends ModularPanel {

  private final JTextArea stockTextArea;
  private final JTextArea percentTextArea;

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public DCAPanel() {
    super("DCAPanel", new Dimension(200, 450),
            new GridLayout(12, 1, 5, 5));
    // 12 rows
    JLabel title = new JLabel();
    title.setText("     New Dollar Coast-Avrg (Frac)");

    JPanel row2 = new JPanel(new FlowLayout());
    JButton clearStocks = uniformButton("Clear");

    JPanel row3 = new JPanel(new FlowLayout());
    JLabel stock = new JLabel("Stock:");
    JTextField stockInput = shortUniformTextField("stockInput");
    JButton stockAdd = uniformButton("Add S");

    stockTextArea = new JTextArea();
    stockTextArea.setText("");

    JPanel row5 = new JPanel(new FlowLayout());
    JLabel percent = new JLabel("%:");
    JTextField percentInput = shortUniformTextField("percentInput");
    JButton percentAdd = uniformButton("Add %");

    percentTextArea = new JTextArea();
    percentTextArea.setText("");

    JPanel row7 = new JPanel(new FlowLayout());
    JLabel name = new JLabel("name:");
    JTextField nameInput = uniformTextField("nameInput");

    JPanel row8 = new JPanel(new FlowLayout());
    JLabel start = new JLabel("Start:");
    JTextField startInput = uniformTextField("startInput");

    JPanel row9 = new JPanel(new FlowLayout());
    JLabel end = new JLabel("End:");
    JTextField endInput = uniformTextField("endInput");

    JPanel row10 = new JPanel(new FlowLayout());
    JLabel interval = new JLabel("Interval:");
    JTextField intervalInput = uniformTextField("intervalInput");

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
    row7.add(name);
    row7.add(nameInput);
    row8.add(start);
    row8.add(startInput);
    row9.add(end);
    row9.add(endInput);
    row10.add(interval);
    row10.add(intervalInput);
    row11.add(amount);
    row11.add(amountInput);
    row12.add(go);


    this.add(title);
    this.add(row2);
    this.add(row3);
    this.add(stockTextArea);
    this.add(row5);
    this.add(percentTextArea);
    this.add(row7);
    this.add(row8);
    this.add(row9);
    this.add(row10);
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
   * @param stock the stock to be added
   */
  public void addStock(String stock) {
    String result = stockTextArea.getText() + ", " + stock;
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
    String result = percentTextArea.getText() + ", " + percent;
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
