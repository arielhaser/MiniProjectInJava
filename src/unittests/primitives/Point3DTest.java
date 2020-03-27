package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class Point3DTest {
    Point3D p1 = new Point3D(1, 2, 3);
    Point3D p2 = new Point3D(0, 3, -2);
    Point3D p3 = new Point3D(-1, -2, -3);

    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = p1.subtract(p2);
        assertEquals("subtract() has wrong result",temp_vector ,new Vector(1,-1,5));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            p1.subtract(p1);
            fail("subtract() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = p1.subtract(p2);
        Point3D temp_point = p1.add(temp_vector);
        assertEquals("add() has wrong result",temp_point ,new Point3D(2,1,8));
    }

    @Test
    public void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(p1.distanceSquared(p2) - 27.0));
    }

    @Test
    public void distance() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(p2.distance(p3) - Math.sqrt(27)));
    }
}