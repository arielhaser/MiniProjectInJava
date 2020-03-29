package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class PlaneTest {
    Plane pl1 = new Plane(new Point3D(0,0,0), new Point3D(1,2,3), new Point3D(4,5,6));

    @Test
    public void getNormal() {
        Vector normal_vector = pl1.getNormal(null);
        assertEquals("normal isn't right", normal_vector, new Vector(-3,6,-3).normalize());
    }


}