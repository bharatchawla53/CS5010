package stockhw7.modelviewcontroller.gui;

import java.util.Objects;

import javax.swing.JButton;

/**
 * This Class Extends JButton to make output the text
 * of the button as a string for controller handling.
 */
public class ExtendedButton extends JButton {
  private final String panelName;

  /**
   * This constructor is the only type needed meant
   * to set the text of the button.
   *
   * @param text the text of the button
   */
  public ExtendedButton(String panelName, String text) {
    super(text);
    this.panelName = panelName;
  }

  @Override
  public String toString() {
    if (Objects.equals(panelName, "")) {
      return this.getText();
    }
    return this.panelName + "," + this.getText();
  }
}
