package geometries;

public class Cylinder extends Tube {
    protected double _height;

    public Cylinder(double _height) {
        this._height = _height;
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
