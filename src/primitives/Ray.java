package primitives;

import java.util.Objects;

public class Ray {
    Point3D _p00;
    Vector _direction;

    public Ray(Point3D _p00, Vector _direction) throws IllegalArgumentException {
        if (is_legal(_p00, _direction))
            throw new IllegalArgumentException("vector not normalize");
        this._p00 = _p00;
        this._direction = _direction;
    }

    public Point3D get_p00() {
        return _p00;
    }

    public void set_p00(Point3D _p00) {
        this._p00 = _p00;
    }

    public Vector get_direction() {
        return _direction;
    }

    public void set_direction(Vector _direction) {
        this._direction = _direction;
    }

    public boolean is_legal(Point3D _p00, Vector _direction){
        double _x = _direction.get_head().get_x().get() - _p00.get_x().get();
        double _y = _direction.get_head().get_y().get() - _p00.get_y().get();
        double _z = _direction.get_head().get_z().get() - _p00.get_z().get();
        return Math.sqrt(_x*_x+_y*_y+_z*_z) == 1.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p00.equals(ray._p00) &&
                _direction.equals(ray._direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p00=" + _p00 +
                ", _direction=" + _direction +
                '}';
    }
}
