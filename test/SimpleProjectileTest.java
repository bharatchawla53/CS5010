import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A JUnit test class for the SimpleProjectile class.
 */
public class SimpleProjectileTest {

  private SimpleProjectile simpleProjectile;
  private final float time = 2.0387f;

  @Before
  public void setUp() {
    simpleProjectile = new SimpleProjectile(0, 0, 0, 10);
  }

  @Test
  public void testGetDisplacements() {
    Map<String, Float> displacement = simpleProjectile.getDisplacement(time);
    assertNotNull(displacement);
    assertNotNull(displacement.get("x"));
    assertNotNull(displacement.get("y"));
  }

  @Test
  public void testGetState() {
    Map<String, Float> displacement = simpleProjectile.getDisplacement(time);
    String state = simpleProjectile.getState(time);
    assertEquals(String.format("At time %.2f: position is (%.2f,%.2f)", time,
            displacement.get("x"), displacement.get("y")), state);
  }

}