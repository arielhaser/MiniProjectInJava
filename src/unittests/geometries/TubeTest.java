package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * class allowing to test the different operations on a Tube
 */
public class TubeTest {
    Tube tu = new Tube(2.189111188, new Ray(new Point3D(1,2,3), new Vector(4,5,6).normalize()));

    /**
     * we test the obtaining of the normal to the tube
     */
    @Test
    public void getNormal() {
        Vector normal_vector = tu.getNormal(new Point3D(1,2,6));
        assertEquals("normal isn't right", normal_vector, new Vector(-0.42714364638469876,-0.5339295579808734,0.7297037292405272));
    }
}