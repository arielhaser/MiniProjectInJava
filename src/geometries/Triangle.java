package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {
    public Triangle(Point3D x, Point3D y, Point3D z) {
        super(x, y, z);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "_vertices=" + _vertices +
                ", _plane=" + _plane +
                '}';
    }
}
