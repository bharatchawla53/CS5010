package stockhw7.modelviewcontroller.pannel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 * Shows the log of the current portfolio.
 */
public class LogPanel extends JPanel {
  private String text;
  private final JTextArea textArea;

  /**
   * This is a class for this class.
   */
  public LogPanel() {
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(200, 500));
    this.setVisible(true);
    this.setBorder(BorderFactory.createLineBorder(Color.black)); // set a boarder

    text = "";

    this.textArea = new JTextArea();
    textArea.setText(text);
    //textArea.setLayout(new );

    JScrollPane jScrollPane = new JScrollPane(textArea);
    this.setLayout(new BorderLayout());
    this.add(jScrollPane);


  }

  /**
   * This is a class for this class.
   * @param text this is for the text.
   */
  public void setText(String text) {
    this.text = text;
    textArea.setText(text);
    this.revalidate();
    this.repaint();
  }
}
