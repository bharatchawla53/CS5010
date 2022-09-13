package lab1;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a SimpleProjectile. A SimpleProjectile has a xPosition, yPosition,
 * xVelocity, and yVelocity.
 */
public class SimpleProjectile implements Particle {

  private float xPosition;
  private float yPosition;
  private float xVelocity;
  private float yVelocity;
  private final float gAcceleration = 9.81f;
  //private final float groundTime = 2.0387f;

  /**
   * Construct a Book object that has the provided title, author and  price.
   *
   * @param xPosition x coordinate of its initial position
   * @param yPosition y coordinate of its initial position
   * @param xVelocity x component of its initial velocity
   * @param yVelocity y component of its initial velocity
   */
  public SimpleProjectile(float xPosition, float yPosition, float xVelocity, float yVelocity) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  /**
   * Return the displacements of the x and y coordinates s = ut + 1/2 * at^2.
   *
   * @return the Map of x and y displacements
   */
  public Map<String, Float> getDisplacement(float time) {
    Map<String, Float> displacements = new HashMap<>();
    float xDisplacement;
    float yDisplacement;

    // For any negative time, it stays at its initial position.
    if (time <= 0) {
      displacements.put("x", xPosition);
      displacements.put("y", yPosition);
      return displacements;
    }

    // For yVelocity = 0, acceleration * time will be equal to 0, thus
    // only returning y's original position.
    if (yVelocity == 0) {
      xDisplacement = xPosition + xVelocity * time;
      yDisplacement = yPosition;
    } else {
      float groundTime = 2 * yVelocity / gAcceleration;
      if (time < groundTime) {
        xDisplacement = xPosition + xVelocity * time;
        yDisplacement = (float) (yPosition +
                yVelocity * time + 0.5 * (-1) * gAcceleration * Math.pow(time, 2));
      } else {
        xDisplacement = xPosition + xVelocity * groundTime;
        yDisplacement = yPosition;
      }
    }

    displacements.put("x", xDisplacement);
    displacements.put("y", yDisplacement);

    return displacements;
  }

  @Override
  public String getState(float time) {
    Map<String, Float> displacements = getDisplacement(time);
    return String.format("At time %.2f: position is (%.2f,%.2f)", time, displacements.get("x"),
            displacements.get("y"));
  }
}
