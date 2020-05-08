package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * define Plane as a geometry object
 */
public class Plane extends Geometry {
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

    /**
     * constructor of Plane
     * @param _emission = the color of object
     * @param _p = point on the Plane
     * @param _normal = a normal vector come from the Plane
     */
    public Plane(Color _emission, Point3D _p, Vector _normal) {
        this(_p, _normal);
        this._emission = _emission;
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

    /**
     * allows us to get the plane's normal
     * @param p: the point
     * @return the normal of the plane
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    /**
     * this function allows to find the intersections between the ray and the plane
     * @param ray: the ray
     * @return a list : result => if null: there isn't intersection and if not null,
     *         it includes all intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector v = ray.get_direction();
        Point3D p0 = ray.get_p00();
        double numerator = alignZero(_normal.dotProduct(_p.subtract(p0)));
        double denominator = alignZero(_normal.dotProduct(v));
        double t = alignZero(numerator/denominator);
        if(isZero(denominator) || t <= 0) // if denominator == 0: ray is parallel to the place => no intersection
            return null;
        List<GeoPoint> result = new ArrayList<>();
        result.add(new GeoPoint(this, ray.getPoint(t)));
        return result;
    }
}