package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * objects with radius parameter
 */
public abstract class RadialGeometry extends Geometry {
    protected double _radius;

    /**
     * constructor of RadialGeometry
     * @param _radius = radius of the object
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * copy constructor of RadialGeometry
     * @param other = RadialGeometry object
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other.get_radius();
    }

    /**
     * constructor of RadialGeometry
     * @param _emission = color of the object
     * @param _radius = radius of the object
     */
    public RadialGeometry(Color _emission, double _radius) {
        this(_radius);
        this._emission = _emission;
    }

    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
