package primitives;

import java.util.Objects;

public class Vector {
    Point3D _head;

    public Vector(Point3D _head) throws IllegalArgumentException {
        if (Point3D.ZERO.equals(_head))
            throw new IllegalArgumentException("length vector is zero");
        this._head = _head;
    }

    public Vector(Vector vec)  {
        this._head = vec._head;
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) throws IllegalArgumentException {
        if (Point3D.ZERO.equals(new Point3D(x,y,z)))
            throw new IllegalArgumentException("length vector is zero");
        this._head = new Point3D(x,y,z);
    }

    public Vector(double x, double y, double z) throws IllegalArgumentException {
        if (Point3D.ZERO.equals(new Point3D(x,y,z)))
            throw new IllegalArgumentException("length vector is zero");
        this._head = new Point3D(x,y,z);
    }

    public Point3D get_head() {
        return _head;
    }

    public void set_head(Point3D _head) {
        this._head = _head;
    }

    public Vector add(Vector other){
       double x= _head.get_x().get() + other.get_head().get_x().get();
       double y= _head.get_y().get() + other.get_head().get_y().get();
       double z= _head.get_z().get() + other.get_head().get_z().get();
       return new Vector(x,y,z);
    }

    public Vector subtract(Vector other){
        double x= _head.get_x().get() - other.get_head().get_x().get();
        double y= _head.get_y().get() - other.get_head().get_y().get();
        double z= _head.get_z().get() - other.get_head().get_z().get();
        return new Vector(x,y,z);
    }

    public Vector scale(double mul){
        double x= _head.get_x().get()*mul;
        double y= _head.get_y().get()*mul;
        double z= _head.get_z().get()*mul;
        return new Vector(x,y,z);
    }

    public double dotProduct(Vector other){
        double x = _head.get_x().get()*other.get_head().get_x().get();
        double y = _head.get_y().get()*other.get_head().get_y().get();
        double z = _head.get_z().get()*other.get_head().get_z().get();
        return x+y+z;
    }

    public Vector crossProduct(Vector other){
        double x = _head.get_x().get();
        double x_other = other._head.get_x().get();
        double y = _head.get_y().get();
        double y_other = other._head.get_y().get();
        double z = _head.get_z().get();
        double z_other = other._head.get_z().get();
        return new Vector(y*z_other-z*y_other, z*x_other-x*z_other, x*y_other-y*x_other);
    }

    public double lengthSquared(){
        double x= _head.get_x().get();
        double y= _head.get_y().get();
        double z= _head.get_z().get();
        return x*x+y*y+z*z;
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        double len_vec = length();
        _head.set_x(new Coordinate(_head.get_x().get()/len_vec));
        _head.set_y(new Coordinate(_head.get_y().get()/len_vec));
        _head.set_z(new Coordinate(_head.get_z().get()/len_vec));
        return this;
    }

    public Vector normalized(){
        return new Vector(_head).normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
