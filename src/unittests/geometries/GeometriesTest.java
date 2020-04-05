package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    @Test
    public void findIntersections() {
        Triangle tr = new Triangle(new Point3D(2,3,2),new Point3D(3,1,2),new Point3D(4,-4,4));
        Sphere sp = new Sphere(Math.sqrt(2), new Point3D(2,-1.5,2));
        Plane pl = new Plane(new Point3D(-1,0.5,4),new Point3D(2.1,3.5,7),new Point3D(6.1,6.5,9));
        ArrayList<Intersectable> temp_list = new ArrayList<>();
        temp_list.add(tr);
        temp_list.add(sp);
        temp_list.add(pl);
        Geometries ge = new Geometries(temp_list);
        Geometries ge_empty = new Geometries();

        // ============ Equivalence Partitions Tests ==============
        //TC01: some of shapes inserted (but not all of them) (sphere and plane == 3 points)
        List<Point3D> result = ge.findIntersections(new Ray(new Point3D(-1,0,0),
                new Vector(3.279177659930163, -2.886383725451189, 2).normalize()));
        assertEquals("Wrong number of points", 3, result.size());

        // =============== Boundary Values Tests ==================
        //TC02: empty body list (0 points)
        result = ge_empty.findIntersections(new Ray(new Point3D(-3,0,0),
                new Vector(4.0091500472, -2.8390318306, 2.5269172321).normalize()));
        assertEquals("Ray's line out of shape", null, result);

        //TC04: one shape inserted (plane == 1 points)
        result = ge.findIntersections(new Ray(new Point3D(0,3,0),
                new Vector(-1.5511254045, -3.4504801707, 1.6501650632).normalize()));
        assertEquals("Wrong number of points", 1, result.size());

        //TC03: not single shape has inserted (0 points)
        result = ge.findIntersections(new Ray(new Point3D(0,3,0),
                new Vector(3.1,3,3).normalize()));
        assertEquals("Ray's line out of shape", null, result);

        //TC05: all shapes inserted (4 points)
        result = ge.findIntersections(new Ray(new Point3D(-2,0,0),
                new Vector(5.174540608147899, -0.391852414985844, 2.695180799126696).normalize()));
        assertEquals("Wrong number of points", 4, result.size());
    }
}