package primitives;

/**
 * Define vector object with direction and start point of (0,0,0)
 */
public class Vector {
    Point3D _head;

    /**
     * constructor of Vector
     * @param _head = 3D point of head's vector
     * @throws IllegalArgumentException = when vector is zero
     */
    public Vector(Point3D _head) throws IllegalArgumentException {
        if (Point3D.ZERO.equals(_head))
            throw new IllegalArgumentException("length vector is zero");
        this._head = _head;
    }

    /**
     * copy constructor of Vector
     * @param vec = vector object
     */
    public Vector(Vector vec)  {
        this._head = vec._head;
    }

    /**
     * constructor of Vector
     * @param x = coordinate of x axis.
     * @param y = coordinate of y axis.
     * @param z = coordinate of z axis.
     * @throws IllegalArgumentException = when vector is zero
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) throws IllegalArgumentException {
        if (Point3D.ZERO.equals(new Point3D(x,y,z)))
            throw new IllegalArgumentException("length vector is zero");
        this._head = new Point3D(x,y,z);
    }

    /**
     * constructor of Vector
     * @param x = coordinate of x axis.
     * @param y = coordinate of y axis.
     * @param z= = coordinate of z axis.
     * @throws IllegalArgumentException = when vector is zero
     */
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

    /**
     * concrete vector to another vector
     * @param other = vector object
     * @return = new vector
     */
    public Vector add(Vector other){
       double x= _head.get_x().get() + other.get_head().get_x().get();
       double y= _head.get_y().get() + other.get_head().get_y().get();
       double z= _head.get_z().get() + other.get_head().get_z().get();
       return new Vector(x,y,z);
    }

    /**
     * subtract vector with another vector
     * @param other = vector object
     * @return = new vector
     */
    public Vector subtract(Vector other){
        double x= _head.get_x().get() - other.get_head().get_x().get();
        double y= _head.get_y().get() - other.get_head().get_y().get();
        double z= _head.get_z().get() - other.get_head().get_z().get();
        return new Vector(x,y,z);
    }

    /**
     * multiple vector with scale number
     * @param mul = the scale number
     * @return = new vector
     */
    public Vector scale(double mul){
        double x= _head.get_x().get()*mul;
        double y= _head.get_y().get()*mul;
        double z= _head.get_z().get()*mul;
        return new Vector(x,y,z);
    }

    /**
     * execute dot product operation between two vectors
     * @param other = vector object
     * @return = new vector
     */
    public double dotProduct(Vector other){
        double x = _head.get_x().get()*other.get_head().get_x().get();
        double y = _head.get_y().get()*other.get_head().get_y().get();
        double z = _head.get_z().get()*other.get_head().get_z().get();
        return x+y+z;
    }

    /**
     * execute cross product operation between two vectors
     * @param other = vector object
     * @return = new vector
     */
    public Vector crossProduct(Vector other){
        double x = _head.get_x().get();
        double x_other = other._head.get_x().get();
        double y = _head.get_y().get();
        double y_other = other._head.get_y().get();
        double z = _head.get_z().get();
        double z_other = other._head.get_z().get();
        return new Vector(y*z_other-z*y_other, z*x_other-x*z_other, x*y_other-y*x_other);
    }

    /**
     * give the length of vector with square
     * @return = the size of the vector with square
     */
    public double lengthSquared(){
        double x= _head.get_x().get();
        double y= _head.get_y().get();
        double z= _head.get_z().get();
        return x*x+y*y+z*z;
    }

    /**
     * give the length of vector
     * @return = the size of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the current vector by changing his 3D point
     * @return the current vector with normalization
     */
    public Vector normalize(){
        double len_vec = length();
        _head.set_x(new Coordinate(_head.get_x().get()/len_vec));
        _head.set_y(new Coordinate(_head.get_y().get()/len_vec));
        _head.set_z(new Coordinate(_head.get_z().get()/len_vec));
        return this;
    }

    /**
     * give new normalized vector by the current vector by changing his 3D point
     * @return = new vector with normalization
     */
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
