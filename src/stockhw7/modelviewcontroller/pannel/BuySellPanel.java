package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * This is the panel for Buying and selling a flexible
 * portfolio stock.
 */
public class BuySellPanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public BuySellPanel() {
    super("BuySellPanel", new Dimension(200, 150),
            new GridLayout(5, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Buy/Sell");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel stock = new JLabel("Stock:");
    JTextField stockInput = uniformTextField("stockInput");

    JPanel row3 = new JPanel(new FlowLayout());
    JLabel count = new JLabel("Count:");
    JTextField countInput = uniformTextField("countInput");

    JPanel row4 = new JPanel(new FlowLayout());
    JLabel date = new JLabel("Date:");
    JTextField dateInput = uniformTextField("dateInput");

    JPanel row5 = new JPanel();
    JButton buy = uniformButton("Buy");
    JButton sell = uniformButton("Sell");

    row2.add(stock);
    row2.add(stockInput);
    row3.add(count);
    row3.add(countInput);
    row4.add(date);
    row4.add(dateInput);
    row5.add(buy);
    row5.add(sell);

    this.add(title);
    this.add(row2);
    this.add(row3);
    this.add(row4);
    this.add(row5);
  }
}
