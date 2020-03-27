package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class SphereTest {
    Sphere sp = new Sphere(3, new Point3D(1,2,3));

    @Test
    public void getNormal() {
        Vector normal_vector = sp.getNormal(new Point3D(1,2,6));
        assertEquals("normal isn't right", normal_vector, new Vector(0,0,1));
    }
}