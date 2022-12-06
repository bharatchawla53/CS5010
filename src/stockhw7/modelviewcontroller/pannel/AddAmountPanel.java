package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is the panel for Adding an amount to a Fractional Portfolio.
 */
public class AddAmountPanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public AddAmountPanel() {
    super("AddAmountPanel", new Dimension(200, 100),
            new GridLayout(3, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Add Amount");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel amount = new JLabel("Amount:");
    JTextField amountInput = uniformTextField("amountInput");

    JPanel row3 = new JPanel(new FlowLayout());
    JButton add = uniformButton("Add");

    row2.add(amount);
    row2.add(amountInput);
    row3.add(add);

    this.add(title);
    this.add(row2);
    this.add(row3);
  }
}
