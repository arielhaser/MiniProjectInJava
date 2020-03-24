package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * A interface for all geometry shapes
 */
public interface Geometry {
    public Vector getNormal(Point3D p);
}
