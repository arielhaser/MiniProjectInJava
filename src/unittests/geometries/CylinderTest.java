package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CylinderTest {

    @Test
    public void testConstructor() {
        Ray temp_ray = new Ray(new Point3D(1, 2, 3), new Vector(4, 5, 6).normalize());
        new Cylinder(3, temp_ray, 10);
    }
}