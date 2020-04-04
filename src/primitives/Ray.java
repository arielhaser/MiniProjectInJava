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
     * @param _direction = vector with contain a direction
     * @throws IllegalArgumentException = when vector isn't with size 1
     */
    public Ray(Point3D _p00, Vector _direction) throws IllegalArgumentException {
        if (!isZero(_direction.length()-1))
            throw new IllegalArgumentException("vector not normalize");
        this._p00 = _p00;
        this._direction = _direction;
    }

    public Point3D get_p00() {
        return _p00;
    }

    public Vector get_direction() {
        return _direction;
    }

    public boolean isPointOnRay(Point3D other){
        return _p00.equals(other) || _direction.isSameVector(other.subtract(_p00));
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
