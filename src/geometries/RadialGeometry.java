package geometries;

/**
 * objects with radius parameter
 */
public class RadialGeometry {
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
