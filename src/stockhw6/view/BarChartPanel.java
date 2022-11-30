package stockhw6.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class BarChartPanel extends JPanel {
  public static final int TOP_BUFFER = 30; // where additional text is drawn
  public static final int AXIS_OFFSET = 20;
  private final Map<String, Integer> barChartContents;
  private Map<Integer, Integer> counts = new LinkedHashMap<>();
  private int chartWidth, chartHeight, chartX, chartY;
  private String xLabel, yLabel;

  public BarChartPanel(Map<String, Integer> barChartContents, String xl, String yl) {
    super();
    this.barChartContents = barChartContents;

    xLabel = xl;
    yLabel = yl;
  }

  public void paintComponent(Graphics g) {
    setupCounts();
    computeSize();

    Graphics2D g2 = (Graphics2D) g;
    drawBars(g2);
    drawAxes(g2);
  }

  private void drawBars(Graphics2D g2) {
    Color original = g2.getColor();

    double numBars = counts.keySet().size();
    double max = 0.;

    for (Integer wrapper : counts.values()) {
      if (max < wrapper)
        max = wrapper;
    }

    int barWidth = (int) (chartWidth /numBars);

    int value, height, xLeft, yTopLeft;
    int counter = 0;
    for (Integer bar : counts.keySet()) {
      value = counts.get(bar);

      double height2 = (value/max)* chartHeight;
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
    g2.drawString(yLabel,AXIS_OFFSET / 2 + 3, chartY - chartHeight / 2);
  }

/*  private void drawText(Graphics2D g2) {

    int size = counts.keySet().size();

    g2.drawString("Number of classes: " + size, AXIS_OFFSET +10, 15) ;

    g2.drawString("Number of counts: " + list.size(), AXIS_OFFSET +10, 30) ;
  }*/

  private Color getRandomColor() {
    Random rand = new Random();

    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();

    return new Color(r, g, b);
  }

  private void setupCounts() {
    counts.clear();
    int i = 0;
    for (Map.Entry<String, Integer> entry : barChartContents.entrySet()) {
      int stars = entry.getValue();
      counts.put(i++, stars);
    }
  }

  private void computeSize() {

    int width = this.getWidth();
    int height = this.getHeight();

    // chart area size
    chartWidth = width - 2*AXIS_OFFSET;
    chartHeight = height - 2*AXIS_OFFSET - TOP_BUFFER;

    // Chart origin coords
    chartX = AXIS_OFFSET;
    chartY = height - AXIS_OFFSET;

  }
}
