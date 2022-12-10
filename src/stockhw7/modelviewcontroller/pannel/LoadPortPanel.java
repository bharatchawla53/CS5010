package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * This Modular Panel is for loading a portfolio
 * ideally of either type.
 */
public class LoadPortPanel extends ModularPanel {

  /**
   * this is the constructor that calls super for the style
   * and initializes the rest of the JComponents.
   */
  public LoadPortPanel() {
    super("LoadPortPanel", new Dimension(300, 100),
            new GridLayout(3, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Load Portfolio");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel name = new JLabel("Name:");
    JTextField nameInput = uniformTextField("nameInput");

    JPanel row3 = new JPanel(new FlowLayout());
    JButton load = uniformButton("Load");

    row2.add(name);
    row2.add(nameInput);
    row3.add(load);

    this.add(title);
    this.add(row2);
    this.add(row3);
  }
}
