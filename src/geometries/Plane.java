package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    protected Point3D _p;
    protected Vector _normal;

    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    public Plane(Point3D x, Point3D y, Point3D z){
        _p = x;
        _normal = null;
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
        return null;
    }
}