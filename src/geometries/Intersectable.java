package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * An interface for all the intersections
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}

