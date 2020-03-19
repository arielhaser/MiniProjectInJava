package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry implements Geometry {
    protected Point3D _center;

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
        return null;
    }
}
