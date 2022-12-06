package stockhw7.modelviewcontroller.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stockhw7.modelviewcontroller.gui.ExtendedButton;
import stockhw7.modelviewcontroller.pannel.AddAmountPanel;
import stockhw7.modelviewcontroller.pannel.BuySellPanel;
import stockhw7.modelviewcontroller.pannel.CoastBasisPanel;
import stockhw7.modelviewcontroller.pannel.CreateFlexPanel;
import stockhw7.modelviewcontroller.pannel.DCAPanel;
import stockhw7.modelviewcontroller.pannel.GraphPanel;
import stockhw7.modelviewcontroller.pannel.LoadPortPanel;
import stockhw7.modelviewcontroller.pannel.LogPanel;
import stockhw7.modelviewcontroller.pannel.ModularPanel;
import stockhw7.modelviewcontroller.pannel.ValuePanel;

/**
 * A GUI version of the Investment view class. this
 * is a more userFriendly way to manipulate portfolios.
 */
public class InvestmentGUIViewImpl extends JFrame implements IGUIView {
  private final LogPanel logPanel;
  private final JPanel westPanel;
  //sub panels
  private final ModularPanel loadPortPanel;
  private final ModularPanel createFlexPanel;
  private final DCAPanel dcaPanel;
  private final ModularPanel graphPanel;
  private final ModularPanel coastBasisPanel;
  private final ModularPanel valuePanel;
  private final ModularPanel buySellPanel;
  private final ModularPanel addAmountPanel;


  private final List<ModularPanel> westPanelList;
  private final List<JButton> topPanelList;

  /**
   * The constructor of the JFrame GUI, it sets the
   * prerequisites for the JFrame as well as adding specific
   * buttons and other components.
   */
  public InvestmentGUIViewImpl() {
    super();
    this.setTitle("Portfolio Application");
    this.setPreferredSize(new Dimension(1000, 800));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    logPanel = new LogPanel();
    logPanel.setLayout(new FlowLayout());

    JPanel topPanel = new JPanel();
    topPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // set a boarder
    topPanel.setLayout(new FlowLayout()); // try different layouts

    westPanel = new JPanel();
    westPanel.setLayout(new FlowLayout());
    westPanel.setPreferredSize(new Dimension(200, 800));
    westPanelList = new ArrayList<ModularPanel>();

    loadPortPanel = new LoadPortPanel();
    createFlexPanel = new CreateFlexPanel();
    dcaPanel = new DCAPanel();
    graphPanel = new GraphPanel();
    coastBasisPanel = new CoastBasisPanel();
    valuePanel = new ValuePanel();
    buySellPanel = new BuySellPanel();
    addAmountPanel = new AddAmountPanel();

    westPanelList.add(loadPortPanel);
    westPanelList.add(createFlexPanel);
    westPanelList.add(dcaPanel);
    westPanelList.add(graphPanel);
    westPanelList.add(coastBasisPanel);
    westPanelList.add(valuePanel);
    westPanelList.add(buySellPanel);
    westPanelList.add(addAmountPanel);


    topPanelList = new ArrayList<JButton>();
    topPanelList.add(new ExtendedButton("", "Save"));
    topPanelList.add(new ExtendedButton("", "Load"));
    topPanelList.add(new ExtendedButton("", "New Flex"));
    topPanelList.add(new ExtendedButton("", "New Frac"));

    for (JButton jb : topPanelList) {
      topPanel.add(jb);
    }

    JButton quit = new JButton("Quit");
    quit.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    topPanel.add(quit);

    this.add(logPanel, BorderLayout.EAST);
    this.add(topPanel, BorderLayout.NORTH);
    this.add(westPanel, BorderLayout.WEST);
    loadState();

    this.pack();
    //flexibleState();

  }

  @Override
  public void loadState() {
    westPanel.removeAll();
    westPanel.add(loadPortPanel);
    westPanel.revalidate();
    westPanel.repaint();
  }

  @Override
  public void newFlexibleState() {
    westPanel.removeAll();
    westPanel.add(createFlexPanel);
    westPanel.revalidate();
    westPanel.repaint();
  }

  //TODO: implement the newFractional state
  @Override
  public void newFractionalState() {
    westPanel.removeAll();
    westPanel.add(dcaPanel);
    westPanel.revalidate();
    westPanel.repaint();
  }

  //TODO: add a boolean to the input and check if it could be changed
  @Override
  public void flexibleState() {
    westPanel.removeAll();
    westPanel.add(graphPanel);
    westPanel.add(coastBasisPanel);
    westPanel.add(valuePanel);
    westPanel.add(buySellPanel);
    westPanel.revalidate();
    westPanel.repaint();
  }

  @Override
  public String findPanel(String name) {
    String result;
    for (ModularPanel mp : westPanelList) {
      if (name.equals(mp.getPanelName())) {
        return mp.getTextFieldInput();
      }
    }
    return null;
  }

  @Override
  public String getDCAPanelStocks() {
    return dcaPanel.getStockText();
  }


  @Override
  public String getDCAPanelPercent() {
    return dcaPanel.getPercentText();
  }

  @Override
  public void addStock(String stock) {
    dcaPanel.addStock(stock);
  }

  @Override
  public void addPercent(Double percent) {
    dcaPanel.addPercent(percent);
  }

  @Override
  public void clearDCA() {
    dcaPanel.clear();
  }

  @Override
  public void updateLog(String log) {
    logPanel.setText(log);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setButtonListener(ActionListener actionEvent) {
    for (JButton jb : topPanelList) {
      jb.addActionListener(actionEvent);
    }
    loadPortPanel.setButtonListener(actionEvent);
    createFlexPanel.setButtonListener(actionEvent);
    graphPanel.setButtonListener(actionEvent);
    coastBasisPanel.setButtonListener(actionEvent);
    valuePanel.setButtonListener(actionEvent);
    buySellPanel.setButtonListener(actionEvent);
    addAmountPanel.setButtonListener(actionEvent);
    dcaPanel.setButtonListener(actionEvent);

  }
}
