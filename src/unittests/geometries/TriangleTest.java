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
        new Triangle(new Point3D(1,2,4),new Point3D(4,5,7),new Point3D(8,8,9));
    }

    @Test
    public void findIntsersections() {
        Triangle tr = new Triangle(new Point3D(1,2,4),new Point3D(4,5,7),new Point3D(8,8,9));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects inside the triangle
        Point3D p1 = new Point3D(4.730966470652567, 5.418179791415758, 7.105393112178949);
        List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0,4,0),
                new Vector(4.7309664706, 1.4181797914, 7.1053931121).normalize()));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses triangle", List.of(p1), result);

        //TC02: Ray intersects outside the triangle
        result = tr.findIntersections(new Ray(new Point3D(0, 4, 0),
                new Vector(0.4368217588, -1.6132638767, 5.3366504877).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        // =============== Boundary Values Tests ==================
        //TC03: Ray intersects inside the triangle. Ray begin at the plane.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(3.8441072472450846, 4.620134912376855, 6.396162577508626),
                new Vector(-3.8441072473, -8.6201349125, -6.3961625776).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC04: Ray intersects outside the triangle. Ray begin at the plane.
        result = tr.findIntersections(new Ray(new Point3D(0.2296738415, 2.1141864639, 4.9986990863),
                new Vector(-0.2296738415, -6.1141864639, -4.9986990863).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC05: Ray intersects outside the triangle, but the ray of triangle intersected.
        result = tr.findIntersections(new Ray(new Point3D(0,-4,0),
                new Vector(5.3013781937, 10.3013781937, 8.3013791937).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC06: Ray intersects the rib's triangle.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(0,-4,0),
                new Vector(2.2505820353999986, 7.2505820353999955, 5.250582035399996).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

        //TC07: Ray intersects the vertex's triangle.
        //insurance: point interaction- start point
        result = tr.findIntersections(new Ray(new Point3D(0,-4,0),
                new Vector(1,6,4).normalize()));
        assertEquals("Ray's line out of triangle", null, result);

    }
}