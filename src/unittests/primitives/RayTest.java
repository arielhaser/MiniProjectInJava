package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * class allowing to test the Ray
 */
public class RayTest {

    /**
     * we test the constructor of the Ray
     */
    @Test
    public void testConstructor() {
        try {
            Ray temp_ray = new Ray(new Point3D(1, 2, 3), new Vector(4, 5, 6));
        }
        catch (IllegalArgumentException ignored){
            fail("failed to build ray");
        }
    }
}