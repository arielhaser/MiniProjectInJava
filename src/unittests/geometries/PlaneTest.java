package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * class allowing to test the different operations on a Plane
 */
public class PlaneTest {
    Plane pl1 = new Plane(new Point3D(0,0,0), new Point3D(1,2,3), new Point3D(4,5,6));

    /**
     * we test the obtaining of the normal to the plane
     */
    @Test
    public void getNormal() {
        Vector normal_vector = pl1.getNormal(null);
        assertEquals("normal isn't right", normal_vector, new Vector(-3,6,-3).normalize());
    }


    @Test
    public void findIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Point3D p1 = new Point3D(0.5, 0.375, 0.25);
        List<Point3D> result = pl1.findIntersections(new Ray(new Point3D(0, 0.5, 0),
                new Vector(2, -0.5, 1).normalize()));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(p1), result);

        //TC02: Ray does not intersect the plane
        result = pl1.findIntersections(new Ray(new Point3D(0, 0, 1),
                new Vector(2, 0, 0).normalize()));
        assertEquals("Ray's line out of plane", null, result);

        // =============== Boundary Values Tests ==================
        //TC03: the ray included in the plane
        result = pl1.findIntersections(new Ray(new Point3D(1, 2, 3),
                new Vector(-1, -2, -3).normalize()));
        assertEquals("Ray's line out of plane", null, result);

        //TC04: the ray not included in the plane
        result = pl1.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(1, 2, 3).normalize()));
        assertEquals("Ray's line out of plane", null, result);

        //TC05: Ray is orthogonal to the plane - before
        p1 = new Point3D(0.66666666666667, 0.66666666666667, 0.66666666666667);
        result = pl1.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(-1, 2, -1).normalize()));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(p1), result);

        //TC06: Ray is orthogonal to the plane - in
        result = pl1.findIntersections(new Ray(new Point3D(1, 1, 1),
                new Vector(-1, 2, -1).normalize()));
        assertEquals("Ray's line out of plane", null, result);

        //TC07: Ray is orthogonal to the plane - after
        result = pl1.findIntersections(new Ray(new Point3D(1, 2, 1),
                new Vector(-1, 2, -1).normalize()));
        assertEquals("Ray's line out of plane", null, result);

        //TC08: Ray is begins at the plane (𝑃0 is in the plane, but not the ray)
        result = pl1.findIntersections(new Ray(new Point3D(1, 1, 1),
                new Vector(-1, 1, -1).normalize()));
        assertEquals("Ray's line out of plane", null, result);

    }
}