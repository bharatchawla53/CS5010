package stockhw7.modelviewcontroller.pannel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stockhw7.modelviewcontroller.gui.ExtendedButton;


/**
 * This is a class for this class.
 */
public abstract class ModularPanel extends JPanel {
  protected final String panelName;
  protected List<JButton> buttons;
  protected List<JTextField> fields;

  /**
   * This is a class for this class.
   * @param name this is name
   * @param preferredSize this is size
   * @param mgr  this is mgr
   */
  public ModularPanel(String name, Dimension preferredSize, LayoutManager mgr) {
    this.setBorder(BorderFactory.createLineBorder(Color.black)); // set a boarder
    this.setPreferredSize(preferredSize);
    this.setLayout(mgr);
    this.setVisible(true);
    this.panelName = name;
    buttons = new ArrayList<JButton>();
    fields = new ArrayList<JTextField>();
  }

  /**
   * This is a class for this class.
   * @param actionEvent the action event.
   */
  public void setButtonListener(ActionListener actionEvent) {
    for (JButton jb : buttons) {
      jb.addActionListener(actionEvent);
    }
  }

  /**
   * This is a class for this class.
   * @return returns string that is the value
   */
  public String getTextFieldInput() {
    StringBuilder result = new StringBuilder();
    for (JTextField tf : fields) {
      result.append(" ").append(tf.getText());
    }
    return result.substring(1, result.length());
  }

  /**This is a class for this class.
   * @return string value
   */
  public String getPanelName() {
    return panelName;
  }

  protected JTextField uniformTextField(String name) {
    JTextField tf = new JTextField(12);
    tf.setName(name);
    fields.add(tf);
    return tf;
  }

  protected JTextField shortUniformTextField(String name) {
    JTextField tf = new JTextField(6);
    tf.setName(name);
    fields.add(tf);
    return tf;
  }

  protected JButton uniformButton(String text) {
    JButton button = new ExtendedButton(panelName, text);
    button.setPreferredSize(new Dimension(75, 20));
    buttons.add(button);
    return button;
  }
}
