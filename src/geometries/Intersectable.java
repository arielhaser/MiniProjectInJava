package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * An interface for all the intersections
 */
public interface Intersectable {

    List<GeoPoint> findIntersections(Ray ray); // a list of GeoPoint
    Box buildBox();
    Box get_box();

    /**
     * class static representing GeoPoint: Point of geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * the GeoPoint constructor
         * @param geometry a geometry
         * @param point a point of the geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) &&
                    point.equals(geoPoint.point);
        }

    }

    /**
     * class for the box
     */
    class Box {
        Point3D _min, _max;

        /**
         * constructor of the Box
         * @param _min the point minimum of the box
         * @param _max the point maximum of the box
         */
        public Box(Point3D _min, Point3D _max) {
            this._min = _min;
            this._max = _max;
        }
    }

}

