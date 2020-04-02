package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * class allowing to test the Ray
 */
public class RayTest {

    /**
     * we test the constructor of the Ray
     * @throws IllegalArgumentException = ignored: if accept vector which not normalize
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // test not normalize vector
        try {
            Ray temp_ray = new Ray(new Point3D(1, 2, 3), new Vector(4, 5, 6));
            fail("Ray accept vector which not normalize");
        }
        catch (IllegalArgumentException ignored){}
    }
}