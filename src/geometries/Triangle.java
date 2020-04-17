package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Define a shape of Triangle, kind of Polygon - flat object
 */
public class Triangle extends Polygon {
    public Triangle(Point3D x, Point3D y, Point3D z) {
        super(x, y, z);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "_vertices=" + _vertices +
                ", _plane=" + _plane +
                '}';
    }

    /**
     * this function allows to find the intersections between the ray and the triangle
     * @param ray: the ray
     * @return a list : result => if null: there isn't intersection and if not null,
     *      *         it includes all intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.get_p00();

        Vector v1 = _vertices.get(0).subtract(P0);
        Vector v2 = _vertices.get(1).subtract(P0);
        Vector v3 = _vertices.get(2).subtract(P0);

        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();

        double result1 = ray.get_direction().dotProduct(N1);
        double result2 = ray.get_direction().dotProduct(N2);
        double result3 = ray.get_direction().dotProduct(N3);
        if ((result1>0 && result2>0 && result3>0) || (result1<0 && result2<0 && result3<0)) {
            List<Point3D> result = _plane.findIntersections(ray); //list of intersections
            if (result != null && !ray.isPointOnRay(result.get(0)))
                return result;
        }
        return null; // there isn't intersection
    }
}
