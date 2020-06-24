package geometries;

import primitives.*;

import java.util.List;

/**
 * Define a shape of Tube - a kind of RadialGeometry
 */
public class Tube extends RadialGeometry {
    protected Ray _axisRay;

    /**
     * constructor of Tube
     * @param _radius = the radius of the Tube
     * @param _axisRay = the ray of the axis of the Tube
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = _axisRay;
    }

    /**
     * constructor of Tube
     * @param _emission = the color of object
     * @param _radius = the radius of the Tube
     * @param _axisRay = the ray of the axis of the Tube
     */
    public Tube(Color _emission, double _radius, Ray _axisRay) {
        this(_radius, _axisRay);
        this._emission = _emission;
    }

    public Tube(Color _emission, Material _material, double _radius, Ray _axisRay) {
        this(_emission, _radius, _axisRay);
        this._material = _material;
    }

    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * Calculate the normal of cylinder
     * @param p = point on the tube WHICH found on the same plane as the start point
     *          we define in the description above
     * @return = normalized vector which normal to the tube
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector temp_vector = p.subtract(_axisRay.get_p00());
        double t = _axisRay.get_direction().dotProduct(temp_vector);
        Point3D center = _axisRay.get_p00().add(_axisRay.get_direction().scale(t));
        Vector orthogonal = p.subtract(center);
        orthogonal.normalize();
        return orthogonal;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public Box buildBox() {
        return null;
    }

    @Override
    public Box get_box() {
        return null;
    }
}
