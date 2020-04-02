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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
