package stockhw6.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;


/**
 * A class to render bar chart for portfolio performance over time.
 */
public class BarChartPanel extends JPanel {
  public static final int TOP_BUFFER = 30; // where additional text is drawn
  public static final int AXIS_OFFSET = 20;
  private final Map<String, Integer> barChartContents;
  private final Map<Integer, Integer> barCount = new LinkedHashMap<>();
  private int chartWidth;
  private int chartHeight;
  private int chartX;
  private int chartY;
  private String xLabel;
  private String yLabel;

  /**
   * Constructs a BarChartPanel constructor.
   *
   * @param barChartContents map containing dates, number of stars for each date.
   * @param xl               label for x axis.
   * @param yl               label for y axis.
   */
  public BarChartPanel(Map<String, Integer> barChartContents, String xl, String yl) {
    super();
    this.barChartContents = barChartContents;

    xLabel = xl;
    yLabel = yl;
  }

  /**
   * Component to process bar chart and it's axes.
   *
   * @param g Graphics object
   */
  public void paintComponent(Graphics g) {
    setupNumberOfBars();
    computeSize();

    Graphics2D g2 = (Graphics2D) g;
    drawBars(g2);
    drawAxes(g2);
  }

  private void drawBars(Graphics2D g2) {
    Color original = g2.getColor();

    double numBars = barCount.keySet().size();
    int barWidth = (int) (chartWidth / numBars);

    double max = 0.;
    for (Integer wrapper : barCount.values()) {
      if (max < wrapper) {
        max = wrapper;
      }
    }

    int value;
    int height;
    int xLeft;
    int yTopLeft;
    int counter = 0;
    for (Integer bar : barCount.keySet()) {
      value = barCount.get(bar);

      double height2 = (value / max) * chartHeight;
      height = (int) height2;

      xLeft = AXIS_OFFSET + counter * barWidth;
      yTopLeft = chartY - height;
      Rectangle rec = new Rectangle(xLeft, yTopLeft, barWidth, height);

      g2.setColor(getRandomColor());
      g2.fill(rec);

      counter++;
    }

    g2.setColor(original);
  }

  private void drawAxes(Graphics2D g2) {
    int rightX = chartX + chartWidth;
    int topY = chartY - chartHeight;

    g2.drawLine(chartX, chartY, rightX, chartY);
    g2.drawLine(chartX, chartY, chartX, topY);

    g2.drawString(xLabel, chartX + chartWidth / 2, chartY + AXIS_OFFSET / 2 + 3);

    // draw vertical string
    AffineTransform affineTransform = new AffineTransform();
    affineTransform.rotate(Math.toRadians(-90), 0, 0);
    g2.setFont(g2.getFont().deriveFont(affineTransform));
    g2.drawString(yLabel, AXIS_OFFSET / 2 + 3, chartY - chartHeight / 2);
  }

  private Color getRandomColor() {
    Random rand = new Random();

    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();

    return new Color(r, g, b);
  }

  private void setupNumberOfBars() {
    barCount.clear();
    int i = 0;
    for (Map.Entry<String, Integer> entry : barChartContents.entrySet()) {
      int stars = entry.getValue();
      barCount.put(i++, stars);
    }
  }

  private void computeSize() {

    int width = this.getWidth();
    int height = this.getHeight();

    // chart area size
    chartWidth = width - 2 * AXIS_OFFSET;
    chartHeight = height - 2 * AXIS_OFFSET - TOP_BUFFER;

    // Chart origin co-ordinates
    chartX = AXIS_OFFSET;
    chartY = height - AXIS_OFFSET;

  }
}
