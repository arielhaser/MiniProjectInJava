package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

public class RayTest {
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // test not normalize vector
        try {
            Ray temp_ray = new Ray(new Point3D(1, 2, 3), new Vector(4, 5, 6));
            fail("Ray accept vector which not normalize");
        }
        catch (Exception ignored){}
    }
}