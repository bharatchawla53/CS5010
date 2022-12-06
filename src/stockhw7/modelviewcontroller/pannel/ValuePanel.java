package stockhw7.modelviewcontroller.pannel;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * this is the panel for retrieving the value of
 * the given portfolio it should be okay for either.
 */
public class ValuePanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public ValuePanel() {
    super("ValuePanel", new Dimension(200, 100),
            new GridLayout(3, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Value");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel from = new JLabel("Date:");
    JTextField start = uniformTextField("start");

    JPanel row3 = new JPanel(new FlowLayout());
    JButton go = uniformButton("Go");

    row2.add(from);
    row2.add(start);
    row3.add(go);

    this.add(title);
    this.add(row2);
    this.add(row3);
  }
}
