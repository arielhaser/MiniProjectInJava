package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

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
        if(_center.equals(ray.get_p00())){
            List<Point3D> results = new ArrayList<>();
            results.add(new Point3D(ray.get_p00().add(ray.get_direction().scale(_radius))));
            return results;
        }
        Vector u = _center.subtract(ray.get_p00());
        double t1, t2;
        if(u.isSameVector(ray.get_direction())){
            t1 = alignZero(u.length()-_radius);
            t2 = alignZero(u.length()+_radius);
        }
        else{
            double tm = ray.get_direction().dotProduct(u);
            double d = Math.sqrt(u.lengthSquared() - tm * tm);
            if (d > _radius) {
                return null;
            }
            double th = Math.sqrt(_radius * _radius - d * d);
            t1 = alignZero(tm - th);
            t2 = alignZero(tm + th);
        }
        if((t1<=0 && t2<=0)||(t1==t2)){
            return null;
        }
        List<Point3D> results = new ArrayList<>();
        if(t1>0){
            results.add(new Point3D(ray.get_p00().add(ray.get_direction().scale(t1))));
        }
        if(t2>0){
            results.add(new Point3D(ray.get_p00().add(ray.get_direction().scale(t2))));
        }
        return results;
    }
}
