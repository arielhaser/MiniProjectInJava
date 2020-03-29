package geometries;

import org.junit.Test;
import primitives.Point3D;


public class TriangleTest {

    @Test
    public void testConstructor() {
        new Triangle(new Point3D(1,2,3),new Point3D(4,5,6),new Point3D(8,8,8));
    }

}