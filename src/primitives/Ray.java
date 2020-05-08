package primitives;

import static primitives.Util.*;

/**
 * define Ray - an object with direction and size of one
 */
public class Ray  {
    private Point3D _p00;
    private Vector _direction;

    /**
     * constructor of Ray
     * @param _p00 = start 3D point of the ray
     * @param _direction = vector normal with contain a direction
     */
    public Ray(Point3D _p00, Vector _direction) {
        this._p00 = _p00;
        this._direction = _direction.normalize();
    }

    public Point3D get_p00() {
        return _p00;
    }

    public Vector get_direction() {
        return _direction;
    }

    /**
     * Get the inserted point with an object
     * @param t = the scale number which multiple the direction's vector
     * @return = the inserted 3D point
     */
    public Point3D getPoint(double t){
        return _p00.add(_direction.scale(t));
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
