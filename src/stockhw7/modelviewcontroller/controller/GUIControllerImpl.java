package stockhw7.modelviewcontroller.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import stockhw7.modelviewcontroller.model.InvestmentModelFlexImpl;
import stockhw7.modelviewcontroller.view.IGUIView;
import stockhw7.modelviewcontroller.view.InvestmentView;
import stockhw7.modelviewcontroller.view.InvestmentViewImpl;


/**
 * this is the implementation of the GUI Controller. this will delegate the correct tasks to the
 * model, as given by the GUI view.
 */
public class GUIControllerImpl extends InvestmentControllerImpl
        implements GUIController, ActionListener {

  private final InvestmentModelFlexImpl model;
  private final IGUIView view;
  private final List<String> stocksList;

  /**
   * GUIControllerImpl is the constructor that takes in a GUI View and a model.
   *
   * @param model this model is the Flexible version
   * @param view  the GUI version of a view
   */
  public GUIControllerImpl(InvestmentModelFlexImpl model, IGUIView view) {
    super(new InputStreamReader(System.in), System.out);
    this.model = model;
    this.view = view;
    this.stocksList = new ArrayList<>();
  }

  @Override
  public void run() {
    this.view.setButtonListener(this);
    this.view.makeVisible();

  }

  @Override
  public String processInput(String input) {
    return null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String test0 = "";
    String input = e.getSource().toString().split(",")[0];
    switch (input) {
      case "Save":
        //TODO: this is dumby code should be corrected
        try {
          model.savePortfolio();
          view.updateLog("SAVED\n" + model.printStocks());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Load":
        view.loadState();
        break;
      case "New Flex":
        view.newFlexibleState();
        break;
      case "New Frac":
        view.newFractionalState();
        break;
      case "Rebalance Portfolio":
        view.newRebalanceState();
        break;
      case "ValuePanel":
        valuePanel(input);
        break;
      case "LoadPortPanel":
        loadPortPanel(input);
        break;
      case "GraphPanel":
        graphPanel(input);
        break;
      case "CreateFlexPanel":
        createFlexPanel(input);
        break;
      case "DCAPanel":
        dcaPanel(e, input);
        break;
      case "CoastBasisPanel":
        coastBasisPanel(input);
        break;
      case "BuySellPanel":
        buySellPanel(e, input);
        break;
      case "RebalancePanel":
        updateStocksList();
        rebalancePanel(e, input);
      case "AddAmountPanel":
        test0 = view.findPanel(input);
        //TODO: Deprecate
        break;
      default:

        String test = e.getSource().toString();
        int test1 = 0;
        //this is where you track down the correct subPanel
    }
  }

  private void valuePanel(String input) {
    Reader in = new StringReader("2 " + view.findPanel(input));
    InvestmentView iv = new InvestmentViewImpl(out);

    try {
      getValue(model, new Scanner(in), iv);
      view.updateLog(model.printStocks());
      view.updateLog(Double.toString(lastValue));

      //determine if it is flex or frac;
      view.flexibleState();
    } catch (Exception ex) {
      view.updateLog(ex.toString());
    }
  }

  private void loadPortPanel(String input) {
    Reader in = new StringReader(view.findPanel(input) + " 3");
    InvestmentView iv = new InvestmentViewImpl(out);
    try {
      loadPortfolio(model, new Scanner(in), iv);
      view.updateLog(model.printStocks());
      //determine if it is flex or frac;
      view.flexibleState();
    } catch (Exception ex) {
      view.updateLog(ex.toString());
    }
  }

  private void graphPanel(String input) {
    Reader in = new StringReader(view.findPanel(input) + " 3");
    InvestmentView iv = new InvestmentViewImpl(out);

  }

  private void createFlexPanel(String input) {
    //figure out the input order
    Reader in = new StringReader("1 " + view.findPanel(input));
    InvestmentView iv = new InvestmentViewImpl(out);
    //run try catch
    try {
      runFlexibleVersion(model, iv, new Scanner(in));
      view.updateLog(model.printStocks());
      //determine if it is flex or frac;
      view.flexibleState();
    } catch (NoSuchElementException ex) {
      view.updateLog(model.printStocks());
      //determine if it is flex or frac;
      view.flexibleState();
    } catch (Exception ex) {
      view.updateLog(ex.toString());
    }
  }

  private void dcaPanel(ActionEvent e, String input) {

    if (e.getSource().toString().split(",")[1].equals("Add S")) {
      view.addDcaStock(view.findPanel(input).split(" ")[0]);

    } else if (e.getSource().toString().split(",")[1].equals("Add %")) {
      view.addPercent("dca", Double.valueOf(view.findPanel(input).split(" ")[1]));

    } else if (e.getSource().toString().split(",")[1].equals("Clear")) {
      view.clearDCA();
    } else {
      String[] splitInput = view.findPanel(input).split(" ");
      String[] stocks = view.getDCAPanelStocks().split(",");
      String[] percent = view.getDCAPanelPercent().split(",");
      String count = Integer.toString(stocks.length - 1);

      String output = "1 " + splitInput[2] + " 0 6 " + count + " ";
      for (String s : stocks) {
        output += s;
      }
      for (String p : percent) {
        output += p;
      }
      output += " " + splitInput[6] + " ";
      output += splitInput[3] + " ";
      output += splitInput[5] + " ";
      output += splitInput[4] + " ";

      Reader in = new StringReader(output);
      InvestmentView iv = new InvestmentViewImpl(out);

      try {
        runFlexibleVersion(model, iv, new Scanner(in));
        view.updateLog(model.printStocks());
        //determine if it is flex or frac;
        view.flexibleState();
      } catch (NoSuchElementException ex) {
        view.updateLog(model.printStocks());
        //determine if it is flex or frac;
        view.flexibleState();
      } catch (Exception ex) {
        view.updateLog(ex.toString());
      }

    }
  }

  private void coastBasisPanel(String input) {

    Reader in = new StringReader("1 " + view.findPanel(input));
    InvestmentView iv = new InvestmentViewImpl(out);

    try {
      getValue(model, new Scanner(in), iv);

      view.updateLog(model.printStocks());
      view.updateLog(Double.toString(lastCoastBasis));
      view.flexibleState(); //determine if it is flex or frac;

    } catch (Exception ex) {
      view.updateLog(ex.toString());
    }
  }

  private void buySellPanel(ActionEvent e, String input) {

    Reader in = new StringReader(view.findPanel(input));
    InvestmentView iv = new InvestmentViewImpl(out);

    if (e.getSource().toString().split(",")[1].equals("Buy")) {
      try {
        addStock(model, new Scanner(in), iv, true);

        view.updateLog(model.printStocks());
        view.flexibleState();//determine if it is flex or frac;

      } catch (Exception ex) {
        view.updateLog(ex.toString());
      }
    } else {
      try {
        sellStock(model, new Scanner(in), iv, true);

        view.updateLog(model.printStocks());
        view.flexibleState();//determine if it is flex or frac;

      } catch (Exception ex) {
        view.updateLog(ex.toString());
      }
    }
  }

  private void rebalancePanel(ActionEvent e, String input) {
    if (e.getSource().toString().split(",")[1].equals("Add S")) {
      view.addRebalanceStock(view.findPanel(input).split(" ")[0], stocksList);
    } else if (e.getSource().toString().split(",")[1].equals("Add %")) {
      view.addPercent("rebalance", Double.valueOf(view.findPanel(input).split(" ")[1]));
    } else if (e.getSource().toString().split(",")[1].equals("Clear")) {
      view.clearRebalance();
    } else {
      String[] splitInput = view.findPanel(input).split(" ");
      //String[] stocks = view.getRebalanceStocks().split(",");
      String[] percent = view.getRebalancePanelPercent().split(",");
      double[] weights = new double[percent.length];
      int index = 0;
      for (String weight : percent) {
        weights[index++] = Double.parseDouble(weight);
      }

      model.rebalancePortfolio(splitInput[2], Double.parseDouble(splitInput[3]), weights);
      view.updateLog(model.printStocks());
      view.flexibleState();

    }
  }

  private void updateStocksList() {
    // get list of stocks that are in this portfolio
    if (stocksList.size() == 0) {
      String[] splitStocks = model.printStocks().split("\n");
      for (String index : splitStocks) {
        if (index.contains(",")) {
          this.stocksList.add(index.split(",")[1]);
        }
      }
    }
  }
}
