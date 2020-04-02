package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * define Sphere as a shape of RadialGeometry
 */
public class Sphere extends RadialGeometry implements Geometry {
    protected Point3D _center;

    /**
     * constructor of Sphere
     * @param _radius = radius of Sphere
     * @param _center = the point of the center of the Sphere
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = _center;
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p) {
        Vector temp_vector = p.subtract(_center);
        return temp_vector.normalized();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
