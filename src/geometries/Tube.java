package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Define a shape of Tube - a kind of RadialGeometry
 */
public class Tube extends RadialGeometry implements Geometry {
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
     * Pay attention!
     * the calculation has made by the start point of the ray
     * that define as the point which the vector get out from her.
     * @param p = point on the tube WHICH found on the same plane as the start point
     *          we define in the description above
     * @return = normalized vector which normal to the tube
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector temp_vector = p.subtract(_axisRay.get_p00());
        return temp_vector.normalized();
    }
}
