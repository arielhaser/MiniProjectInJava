package geometries;

import primitives.*;
import primitives.Color;

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
    private Box _box;

    /**
     * constructor of Plane
     * @param _p = point on the Plane
     * @param _normal = a normal vector come from the Plane
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
        this._box = buildBox();
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
        this._box = buildBox();
    }
    public Plane (Color _emission, Material _material,Point3D x, Point3D y, Point3D z)
    {
        this._emission = _emission;
        this._material = _material;
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
        this._box = buildBox();
    }

    public Plane(Color _emission, Material _material, Point3D _p, Vector _normal) {
        this(_emission, _p, _normal);
        this._material = _material;
        this._box = buildBox();
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
     * @return a list of GeoPoint: result => if null: there isn't intersection and if not null,
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

    @Override
    public Box buildBox() {
        if (_box != null) return _box;
        Vector vx = new Vector(-_normal.get_head().get_y().get(), _normal.get_head().get_x().get(), 0);
        Vector vy = vx.crossProduct(_normal);
        double minX=-1000, minY=-1000, minZ=-1000, maxX=1000, maxY=1000, maxZ=1000;
        if(vy.get_head().get_x().get() == 0){
            minX = -1;
            maxX = 1;
        }
        if(vy.get_head().get_y().get() == 0){
            minY = -1;
            maxY = 1;
        }
        if(vy.get_head().get_z().get() == 0){
            minZ = -1;
            maxZ = 1;
        }
        Point3D min = new Point3D(minX,minY,minZ);
        Point3D max = new Point3D(maxX,maxY,maxZ);
        _box = new Box(min, max);
        return _box;
    }

    @Override
    public Box get_box() {
        return _box;
    }
}