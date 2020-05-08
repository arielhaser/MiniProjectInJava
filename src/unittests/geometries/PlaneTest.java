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
    Plane pl1 = new Plane(new Point3D(1,4,8), new Point3D(1,2,4), new Point3D(4,5,7));

    /**
     * we test the obtaining of the normal to the plane
     */
    @Test
    public void getNormalTest() {
        Vector normal_vector = pl1.getNormal(null);
        assertEquals("normal isn't right", normal_vector, new Vector(3,-6,3).normalize());
    }

    /**
     * test of the function findIntersection for the plane
     */
    @Test
    public void findIntersectionsTest() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Point3D p1 = new Point3D(0.5000000000000002, 0.37499999999999994, 1.25);
        List<Intersectable.GeoPoint> result = pl1.findIntersections(new Ray(new Point3D(0, 0.5, 1),
                new Vector(2, -0.5, 1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(new Intersectable.GeoPoint(pl1, p1)), result);

        //TC02: Ray does not intersect the plane
        result = pl1.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(1, 0, 0)));
        assertEquals("Ray's line out of plane", null, result);

        // =============== Boundary Values Tests ==================
        //TC03: the ray included in the plane
        result = pl1.findIntersections(new Ray(new Point3D(1, 2, 4),
                new Vector(0, 2, 4)));
        assertEquals("Ray's line out of plane", null, result);

        //TC04: the ray not included in the plane
        result = pl1.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(0, 2, 4)));
        assertEquals("Ray's line out of plane", null, result);

        //TC05: Ray is orthogonal to the plane - before
        p1 = new Point3D(-0.8333333333333333, -0.33333333333333337, 1.1666666666666667);
        result = pl1.findIntersections(new Ray(new Point3D(-1, 0, 1),
                new Vector(1,-2,1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(new Intersectable.GeoPoint(pl1, p1)), result);

        //TC06: Ray is orthogonal to the plane - in
        result = pl1.findIntersections(new Ray(new Point3D(0, 0, 1),
                new Vector(1, -2, 1)));
        assertEquals("Ray's line out of plane", null, result);

        //TC07: Ray is orthogonal to the plane - after
        result = pl1.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(1, -2, 1)));
        assertEquals("Ray's line out of plane", null, result);

        //TC08: Ray is begins at the plane (𝑃0 is in the plane, but not the ray)
        result = pl1.findIntersections(new Ray(new Point3D(0, 0, 1),
                new Vector(3, -2, 4)));
        assertEquals("Ray's line out of plane", null, result);

    }
}