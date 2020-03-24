package primitives;

/**
 * define a 3D point in space
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    final static Point3D ZERO = new Point3D(0.0,0.0,0.0);

    /**
     * constructor of 3D point
     * @param _x = coordinate of x axis
     * @param _y = coordinate of y axis
     * @param _z = coordinate of z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        super();
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * constructor of 3D point by real numbers
     * @param _x = number of x axis
     * @param _y = number of y axis
     * @param _z = number of z axis
     */
    public Point3D(double _x, double _y, double _z){
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * copy constructor
     * @param other = Point3D object
     */
    public Point3D(Point3D other){
        this._x = other._x;
        this._y = other._y;
        this._z = other._z;
    }

    public Coordinate get_x() {
        return _x;
    }

    public void set_x(Coordinate _x) {
        this._x = _x;
    }

    public Coordinate get_y() {
        return _y;
    }

    public void set_y(Coordinate _y) {
        this._y = _y;
    }

    public Coordinate get_z() {
        return _z;
    }

    public void set_z(Coordinate _z) {
        this._z = _z;
    }

    /**
     * subtract to points to create vector
     * @param other = 3D point
     * @return = new vector
     */
    public Vector subtract(Point3D other){
        double x = _x.get()-other.get_x().get();
        double y = _y.get()-other.get_y().get();
        double z = _z.get()-other.get_z().get();
        Point3D temp = new Point3D(x, y, z);
        return new Vector(temp);
    }

    /**
     * To concrete vector and point
     * @param other = vector object
     * @return = 3D point which created
     */
    public Point3D add(Vector other){
        double x = _x.get()+other.get_head().get_x().get();
        double y = _y.get()+other.get_head().get_y().get();
        double z = _z.get()+other.get_head().get_z().get();
        return new Point3D(x, y, z);
    }

    /**
     * calculate the distance of two points with squared
     * @param other = 3D point
     * @return = double number
     */
    public double distanceSquared(Point3D other){
        Vector temp = subtract(other);
        double x= temp.get_head().get_x().get();
        double y= temp.get_head().get_y().get();
        double z= temp.get_head().get_z().get();
        return x*x+y*y+z*z;
    }

    /**
     * calculate the distance of two points
     * @param other = 3D point
     * @return = double number
     */
    public double distance(Point3D other){
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }
}
