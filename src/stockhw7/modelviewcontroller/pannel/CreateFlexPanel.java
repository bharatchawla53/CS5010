package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * This is the panel for creating new Flexible portfolios.
 */
public class CreateFlexPanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public CreateFlexPanel() {
    super("CreateFlexPanel", new Dimension(200, 125),
            new GridLayout(4, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Create Flexible Portfolio");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel name = new JLabel("Name:");
    JTextField nameInput = uniformTextField("nameInput");

    JPanel row3 = new JPanel(new FlowLayout());
    JLabel fee = new JLabel("Fee:");
    JTextField feeInput = uniformTextField("feeInput");

    JPanel row4 = new JPanel(new FlowLayout());
    JButton create = uniformButton("Create");

    row2.add(name);
    row2.add(nameInput);
    row3.add(fee);
    row3.add(feeInput);
    row4.add(create);

    this.add(title);
    this.add(row2);
    this.add(row3);
    this.add(row4);
  }
}
