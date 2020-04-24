package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class allowing to test the different operations on a Cylinder
 */
public class CylinderTest {

    /**
     * we test the constructor of the Cylinder
     */
    @Test
    public void testConstructor() {
        Ray temp_ray = new Ray(new Point3D(1, 2, 3), new Vector(4, 5, 6));
        new Cylinder(3, temp_ray, 10);
    }
}