package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * class allowing to test the different operations on the vectors
 */
public class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-1, -2, -3);
    Vector v3 = new Vector(0, 3, 2);
    Vector v4 = new Vector(0, 3, -2);

	/**
     * we test the constructor: if creation of zero vector
     * @throws IllegalArgumentException = igonred: if creation of zero vector
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            new Vector(0,0,0);
            fail("add() for zero vector does not throw an exception");
        } catch (IllegalArgumentException ignored) {}
    }

	/**
     * we test the addition between two vectors
     * @throws IllegalArgumentException = ignored: if addition of two opposite vectors
     */
    @Test
    public void addTest() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.add(v3);
        assertEquals("add() has wrong result",temp_vector ,new Vector(1,5,5));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.add(v2);
            fail("add() for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * we test the subtraction between two vectors
     * @throws IllegalArgumentException = ignored: if we subtract two same vectors
     */
    @Test
    public void subtractTest() {
        // ============ Equivalence Partitions Tests ==============
        Vector temp_vector = v1.subtract(v3);
        assertEquals("subtract() has wrong result"+temp_vector,temp_vector ,new Vector(1,-1,1));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.subtract(v1);
            fail("subtract() for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException ignored) {}
    }

	/**
     * we test the multiplication of a vector by a scalar
     * @throws IllegalArgumentException = ignored: if the scalar is zero
     */
    @Test
    public void scaleTest() {
        // ============ Equivalence Partitions Tests ==============
        //positive scale
        Vector temp_vector = v1.scale(2);
        assertEquals("scale() has wrong result",temp_vector ,new Vector(2,4,6));
        //negative scale
        Vector temp_vector2 = v1.scale(-2);
        assertEquals("scale() has wrong result",temp_vector2 ,new Vector(-2,-4,-6));
        // =============== Boundary Values Tests ==================
        // test zero vector
        try {
            v1.scale(0);
            fail("scale() with scalar = 0 does not throw an exception");
        } catch (IllegalArgumentException ignored) {}
    }

	/**
     * we test the product between two vectors
     */
    @Test
    public void dotProductTest() {
        // ============ Equivalence Partitions Tests ==============
        double temp_result = v1.dotProduct(v2);
        assertTrue("dotProduct() has wrong result",isZero(temp_result +14.0));

    }

	/**
     * we test the crossProduct between two vectors
     * @throws IllegalArgumentException = ignored: if the vectors are collinear
     */
    @Test
    public void crossProductTest() {
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
        } catch (IllegalArgumentException ignored) {}
    }

	 /**
     * we test the calcul of the lengthSquared
     */
    @Test
    public void lengthSquaredTest() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
    }

	/**
     * we test the calculog the lenght
     */
    @Test
    public void lengthTest() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: lengthSquared() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
    }

	/**
     * we test the action of normalizing a vector
     */
    @Test
    public void normalizeTest() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v1);
        Vector u = vCopy.normalize();
        assertTrue("ERROR: normalize() function creates a new vector", u == vCopy);
    }

	/**
     * we test the creation of a new vector normalized.
     */
    @Test
    public void normalizedTest() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v1);
        Vector vCopyNormalize = vCopy.normalized();
        assertFalse("ERROR: normalized() function does not create a new vector", vCopy == vCopyNormalize);
        assertTrue ("ERROR: normalized() result is not a unit vector",isZero(vCopyNormalize.length() - 1));
    }

}