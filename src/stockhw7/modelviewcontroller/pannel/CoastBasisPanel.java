package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * This is the panel to display the coast basis over a
 * given date.
 */
public class CoastBasisPanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public CoastBasisPanel() {
    super("CoastBasisPanel", new Dimension(200, 100),
            new GridLayout(3, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Coast Basis");

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
