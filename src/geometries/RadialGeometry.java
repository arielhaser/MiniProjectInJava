package geometries;

public class RadialGeometry {
    protected double _radius;

    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

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
