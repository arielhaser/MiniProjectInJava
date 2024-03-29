package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * class allowing to test the different operations on the sphere
 */
public class SphereTest {
    Sphere sp = new Sphere(3, new Point3D(1,2,3));

    /**
     * we test the obtaining of the normal to the sphere
     */
    @Test
    public void getNormalTest() {
        Vector normal_vector = sp.getNormal(new Point3D(1,2,6));
        assertEquals("normal isn't right", normal_vector, new Vector(0,0,1));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersectionsTest() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p1),
                new Intersectable.GeoPoint(sphere, p2)), result);

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p3 = new Point3D(1.9939572059906485 , 0.10976826799792186, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0),
                new Vector(4.5, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p3)), result);

        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(3, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Ray's line out of sphere", null, result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p3 = new Point3D(1.9939572059906485, 0.10976826799792186, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0),
                new Vector(4.5, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p3)), result);

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(4, 2, 0)));
        assertEquals("Ray's line out of sphere", null, result);

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(0.105572809, 0, 0.4472135955);
        p2 = new Point3D(1.894427191, 0, -0.4472135955);
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 1),
                new Vector(4, 0, -2)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p1),
                new Intersectable.GeoPoint(sphere, p2)), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        p3 = new Point3D(0.4, -0.8, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(-1, -3, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p3)), result);

        // TC15: Ray starts inside (1 points)
        p3 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0),
                new Vector(0, -1.5, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p3)), result);

        // TC16: Ray starts at the center (1 points)
        p3 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(0, -1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Intersectable.GeoPoint(sphere, p3)), result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 1),
                new Vector(0, -1, 0)));
        assertEquals("Ray's line out of sphere", null, result);

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(0, -1, 0)));
        assertEquals("Ray's line out of sphere", null, result);

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1, -1, 1),
                new Vector(0, -1, 0)));
        assertEquals("Ray's line out of sphere", null, result);

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 2),
                new Vector(1, 0, 0)));
        assertEquals("Ray's line out of sphere", null, result);

    }

}