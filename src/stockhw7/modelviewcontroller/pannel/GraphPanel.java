package stockhw7.modelviewcontroller.pannel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * this Class is a JPanel that displays the input GUI
 * for a graph function.
 */
public class GraphPanel extends ModularPanel {

  /**
   * initializes the Graph panel with all the necessary information.
   * adds GUI elements to the panel.
   */
  public GraphPanel() {
    super("GraphPanel", new Dimension(200, 125),
            new GridLayout(4, 1, 5, 5));

    JLabel title = new JLabel();
    title.setText("     Graph");

    JPanel row2 = new JPanel(new FlowLayout());
    JLabel from = new JLabel("From:");
    JTextField start = uniformTextField("start");

    JPanel row3 = new JPanel(new FlowLayout());
    JLabel to = new JLabel("To:");
    JTextField end = uniformTextField("end");

    JPanel row4 = new JPanel(new FlowLayout());
    JButton go = uniformButton("Go");

    row2.add(from);
    row2.add(start);
    row3.add(to);
    row3.add(end);
    row4.add(go);

    this.add(title);
    this.add(row2);
    this.add(row3);
    this.add(row4);
  }
}
