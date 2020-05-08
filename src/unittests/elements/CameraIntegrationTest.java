package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;
import static org.junit.Assert.*;

/**
 * class allowing to test the intersections between the ray from the camera and the different geometric shapes
 */
public class CameraIntegrationTest {
    Camera cma1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cma2 = new Camera(new Point3D(0,0,-0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

    /**
     * test with a sphere with which we get 2 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithSphere1() {
        Sphere sphere = new Sphere(1, new Point3D(0,0,3));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = sphere.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 2);
    }

    /**
     * test with a sphere with which we get 18 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithSphere2() {
        Sphere sphere = new Sphere(2.5, new Point3D(0,0,2.5));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = sphere.findIntersections(cma2.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 18);
    }

    /**
     * test with a sphere with which we get 10 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithSphere3() {
        Sphere sphere = new Sphere(2, new Point3D(0,0,2));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = sphere.findIntersections(cma2.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 10);
    }

    /**
     * test with a sphere with which we get 9 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithSphere4() {
        Sphere sphere = new Sphere(4, new Point3D(0,0,1));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = sphere.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 9);
    }

    /**
     * test with a sphere with which we get no intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithSphere5() {
        Sphere sphere = new Sphere(0.5, new Point3D(0,0,-1));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = sphere.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 0);
    }

    /**
     * test with a plane with which we get 9 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithPlane1() {
        Plane plane = new Plane(new Point3D(1,1,5), new Point3D(1,2,5),
                new Point3D(0, 0, 5));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = plane.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 9);
    }

    /**
     * test with a plane with which we get 9 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithPlane2() {
        Plane plane = new Plane(new Point3D(2,3,0), new Point3D(2,0,1),
                new Point3D(3,-4,2));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = plane.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 9);
    }

    /**
     * test with a plane with which we get 6 intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithPlane3() {
        Plane plane = new Plane(new Point3D(0,-5,0), new Point3D(1,2,7),
                new Point3D(2,2,7));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = plane.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 6);
    }

    /**
     * test with a triangle with which we get just one intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithTriangle1() {
        Triangle triangle = new Triangle(new Point3D(0,-1,2), new Point3D(1,1,2),
                new Point3D(-1,1,2));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = triangle.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 1);
    }

    /**
     * test with a triangle with which we get no intersection points
     */
    @Test
    public void testConstructRayThroughPixelWithTriangle2() {
        Triangle triangle = new Triangle(new Point3D(0,-20,2), new Point3D(1,1,2),
                new Point3D(-1,1,2));

        List<Intersectable.GeoPoint> results;
        int counter = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                results = triangle.findIntersections(cma1.constructRayThroughPixel(3,3,j,i,1,
                        3,3));
                if (results!=null)
                    counter += results.size();
            }
        }
        assertEquals("wrong intersections number", counter, 2);
    }

}
