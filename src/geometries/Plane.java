package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * define Plane as a geometry object
 */
public class Plane implements Geometry {
    protected Point3D _p;
    protected Vector _normal;

    /**
     * constructor of Plane
     * @param _p = point on the Plane
     * @param _normal = a normal vector come from the Plane
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    /**
     * constructor of Plane by three points
     * @param x = point number one
     * @param y = point number two
     * @param z = point number three
     */
    public Plane(Point3D x, Point3D y, Point3D z){
        _p = x;
        Vector v1 = y.subtract(x);
        Vector v2 = z.subtract(x);
        Vector temp_vector = v1.crossProduct(v2);
        _normal = temp_vector.normalized();
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector get_normal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        double numerator = alignZero(_normal.dotProduct(_p.subtract(ray.get_p00())));
        double denominator = alignZero(_normal.dotProduct(ray.get_direction()));
        if(denominator == 0 || numerator/denominator <= 0)
            return null;
        double t = alignZero(numerator/denominator);
        List<Point3D> result = new ArrayList<>();
        result.add(new Point3D(ray.getPoint(t)));
        return result;
    }
}