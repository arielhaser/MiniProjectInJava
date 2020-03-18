package primitives;

import java.util.Objects;

public class Vector {
    Point3D _head;

    public Vector(Point3D _head) throws IllegalArgumentException {
        if (_head.get_x().get() == 0.0 && _head.get_y().get() == 0.0 &&_head.get_z().get() == 0.0)
            throw new IllegalArgumentException("length vector is zero");
        this._head = _head;
    }

    public Vector(Vector vec)  {

    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) throws IllegalArgumentException {
        if (x.get() == 0.0 && y.get() == 0.0 && z.get() == 0.0)
            throw new IllegalArgumentException("length vector is zero");
    }

    public Vector(double x, double y, double z) throws IllegalArgumentException {
        if (x == 0.0 && y == 0.0 &&z == 0.0)
            throw new IllegalArgumentException("length vector is zero");
    }


    public Point3D get_head() {
        return _head;
    }

    public void set_head(Point3D _head) {
        this._head = _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(_head, vector._head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
