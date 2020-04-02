package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * class allowing to test the different operations on a triangle
 */
public class TriangleTest {

    /**
     * we test the constructor of the triangle
     */
    @Test
    public void testConstructor() {
        new Triangle(new Point3D(1,2,3),new Point3D(4,5,6),new Point3D(8,8,8));
    }

    @Test
    public void findIntsersections() {
        Triangle tr = new Triangle(new Point3D(1,2,3),new Point3D(4,5,6),new Point3D(8,8,8));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects inside the triangle
        Point3D p1 = new Point3D(1.489009999, 2.1726473249, 2.8562846507);
        List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0,4,0),
                new Vector(1.489009999, -1.8273526751, 2.8562846507).normalize()));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses triangle", List.of(p1), result);

        //TC02: Ray intersects outside the triangle
        result = tr.findIntersections(new Ray(new Point3D(0, 4, 0),
                new Vector(0.0472780667, -2.7523275457, 2.448066842).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        // =============== Boundary Values Tests ==================
        //TC03: Ray intersects inside the triangle. Ray begin at the plane.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(1.4156298378, 2.1194441464, 2.823258455),
                new Vector(2.5843701622, -2.1194441464, -2.823258455).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC04: Ray intersects outside the triangle. Ray begin at the plane.
        result = tr.findIntersections(new Ray(new Point3D(2.2436369334, 1.7837534462, 1.323869959),
                new Vector(1.7563630666, -1.7837534462, -1.323869959).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC05: Ray intersects outside the triangle, but the ray of triangle intersected.
        result = tr.findIntersections(new Ray(new Point3D(-0.5,-1,-1.5),
                new Vector(4.5,1,1.5).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC06: Ray intersects the rib's triangle.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(0.5,1,1.5),
                new Vector(3.5,-1,-1.5).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC07: Ray intersects the vertex's triangle.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(1,2,3),
                new Vector(3,-2,-3).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

    }
}