package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-1, -2, -3);
    Vector v3 = new Vector(0, 3, 2);
    Vector v4 = new Vector(0, 3, -2);

    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            new Vector(0,0,0);
            fail("add() for zero vector does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.add(v3);
        assertEquals("add() has wrong result",temp_vector ,new Vector(1,5,5));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.add(v2);
            fail("add() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.subtract(v3);
        assertEquals("subtract() has wrong result"+temp_vector,temp_vector ,new Vector(1,-1,1));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.add(v2);
            fail("subtract() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void scale() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.scale(2);
        assertEquals("scale() has wrong result",temp_vector ,new Vector(2,4,6));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.scale(0);
            fail("scale() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        double temp_result = v1.dotProduct(v2);
        assertTrue("dotProduct() has wrong result",isZero(temp_result +14.0));

    }

    @Test
    public void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.crossProduct(v4);
        assertEquals("crossProduct() has wrong result",temp_vector ,new Vector(-13,2,3));
        assertTrue("ERROR: crossProduct() wrong result length",
                isZero(temp_vector.length() - v1.length() * v4.length()));
        assertTrue("ERROR: crossProduct() result is not orthogonal to its operands",
                isZero(temp_vector.dotProduct(v1)) && isZero(temp_vector.dotProduct(v4)));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}
    }

    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
    }

    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
    }

    @Test
    public void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v1);
        Vector u = vCopy.normalize();
        assertTrue("ERROR: normalizate() function creates a new vector", u == vCopy);
    }

    @Test
    public void normalized() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v1);
        Vector vCopyNormalize = vCopy.normalized();
        assertFalse("ERROR: normalized() function does not create a new vector", vCopy == vCopyNormalize);
        assertTrue ("ERROR: normalized() result is not a unit vector",isZero(vCopyNormalize.length() - 1));
    }

}