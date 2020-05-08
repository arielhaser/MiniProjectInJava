package geometries;

import primitives.Color;
import primitives.Ray;

/**
 * A kind of shape of Tube shape
 */
public class Cylinder extends Tube {
    protected double _height;

    /**
     * constructor of Cylinder
     * @param _radius = radius of the Cylinder
     * @param _axisRay = ray of the axis of the Cylinder
     * @param _height = the height of the Cylinder
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    /**
     * constructor of Cylinder
     * @param _emission = the color of object
     * @param _radius = radius of the Cylinder
     * @param _axisRay = ray of the axis of the Cylinder
     * @param _height = the height of the Cylinder
     */
    public Cylinder(Color _emission, double _radius, Ray _axisRay, double _height) {
        this(_radius, _axisRay, _height);
        this._emission = _emission;
    }

    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
