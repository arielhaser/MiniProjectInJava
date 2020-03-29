package geometries;
import static java.lang.System.out;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {
    Tube tu = new Tube(3, new Ray(new Point3D(1,2,3), new Vector(4,5,6).normalize()));

    @Test
    public void getNormal() {
        Vector normal_vector = tu.getNormal(new Point3D(1,2,6));
        out.println(normal_vector);
        assertEquals("normal isn't right", normal_vector, new Vector(0,0,1));
    }
}