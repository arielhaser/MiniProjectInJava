package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A composite class that include a collection of composite geometries
 */

public class Geometries implements Intersectable {
    List<Intersectable> _geometries;
    boolean IMPROVE_TIME = false;
    private Box _box;

    /**
     * constructor of Geometries
     */
    public Geometries(){
        this._geometries = new ArrayList<Intersectable>();
        this._box = null;
    }

    /**
     * Geometries constructor that allow to add geometries and the "box"
     * @param _geometries to add to the collection of geometries
     */
    public Geometries(List<Intersectable> _geometries) {
        this._geometries = _geometries;
        this._box = buildBox();
    }

    /**
     * this function allows to add geometries to the composite geometry
     * @param _geometry to add to the collection of geometries
     */
    public void add(Intersectable _geometry) {
        this._geometries.add(_geometry);
    }

    /**
     *
     * this function allows you to find and get the intersections if there are any
     * between the ray and the composite geometries
     * @param ray :the ray
     * @return a list : result => if null: there isn't intersection and if not null,
        it includes all intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Box box = buildBox();
        List<GeoPoint> result = new ArrayList<>();
        if(!IMPROVE_TIME || isIntersected(box,ray)) {
            for (Intersectable geometry : _geometries) {
                if (!IMPROVE_TIME || isIntersected(geometry.get_box(), ray)) {
                    List<GeoPoint> temp_result = geometry.findIntersections(ray);
                    if (temp_result != null)
                        result.addAll(temp_result);
                }
            }
        }
        if (result.isEmpty()) // there isn't intersection
            return null;
        return result;
    }

    /**
     * function that allows us to build the box finding the min and the max Points.
     * @return the box
     */
    public Box buildBox() {
        if (_box != null) return _box;
        Point3D min, max;
        double minX, minY, minZ, maxX, maxY, maxZ;
        minX = minY = minZ = Double.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Double.NEGATIVE_INFINITY;
        for(Intersectable intersectable : _geometries) {
            Box box = intersectable.buildBox(); // make recursive call in case intersectable is set of geometries
            minX = min(minX, box._min.get_x().get());
            maxX = max(maxX, box._max.get_x().get());
            minY = min(minY, box._min.get_y().get());
            maxY = max(maxY, box._max.get_y().get());
            minZ = min(minZ, box._min.get_z().get());
            maxZ = max(maxZ, box._max.get_z().get());
        }
        min = new Point3D(minX, minY, minZ);
        max = new Point3D(maxX, maxY, maxZ);
        _box = new Box(min, max);
        return _box;
    }

    /**
     * getter of the box
     * @return the box
     */
    public Box get_box() {
        return _box;
    }

    /**
     * the function checks if there is intersection between the ray and the box
     * @param box the box
     * @param ray the ray to the scene
     * @return True if there is intersection between the ray and the box
     */
    private boolean isIntersected(Box box, Ray ray) {
        double tmin = Double.NEGATIVE_INFINITY, tmax = Double.POSITIVE_INFINITY;
        Point3D headVector = ray.get_direction().get_head();
        Point3D p00 = ray.get_p00();
        if (headVector.get_x().get() != 0.0) {
            double tx1 = (box._min.get_x().get() - p00.get_x().get())/headVector.get_x().get();
            double tx2 = (box._max.get_x().get() - p00.get_x().get())/headVector.get_x().get();

            tmin = max(tmin, min(tx1, tx2));
            tmax = min(tmax, max(tx1, tx2));
        }

        if (headVector.get_y().get() != 0.0) {
            double ty1 = (box._min.get_y().get() - p00.get_y().get())/headVector.get_y().get();
            double ty2 = (box._max.get_y().get() - p00.get_y().get())/headVector.get_y().get();

            tmin = max(tmin, min(ty1, ty2));
            tmax = min(tmax, max(ty1, ty2));
        }

        if (headVector.get_z().get() != 0.0) {
            double tz1 = (box._min.get_z().get() - p00.get_z().get())/headVector.get_z().get();
            double tz2 = (box._max.get_z().get() - p00.get_z().get())/headVector.get_z().get();

            tmin = max(tmin, min(tz1, tz2));
            tmax = min(tmax, max(tz1, tz2));
        }

        return tmax >= tmin;
    }

    public void autoBVHConstruction(){
        while(_geometries.size() > 2){
            Intersectable savedGeometryFirst=null, savedGeometrySecond=null;
            double best = Double.POSITIVE_INFINITY;
            for(Intersectable geometryFirst : _geometries){
                for (Intersectable geometrySecond : _geometries){
                    Box boxA = geometryFirst.buildBox();
                    Box boxB = geometrySecond.buildBox();
                    double boxesDistance = boxesDistance(boxA, boxB);
                    if (boxesDistance != 0 && boxesDistance < best){
                        best = boxesDistance;
                        savedGeometryFirst = geometryFirst;
                        savedGeometrySecond = geometrySecond;
                    }
                }
            }
            Geometries newPair = new Geometries(List.of(savedGeometryFirst, savedGeometrySecond));
            _geometries.remove(savedGeometryFirst);
            _geometries.remove(savedGeometrySecond);
            _geometries.add(newPair);
        }
    }

    /**
     * the function return the distance between boxes
     * @param boxA one box
     * @param boxB the other box
     * @return the distance between the boxes A and B
     */
    double boxesDistance(Box boxA, Box boxB){
        double minA=0, minB=0;
        if (!boxA._min.equals(boxB._min) && !boxA._min.equals(boxB._max))
            minA = min(boxA._min.distance(boxB._min), boxA._min.distance(boxB._max));
        if (!boxA._max.equals(boxB._min) && !boxA._max.equals(boxB._max))
            minB = min(boxA._max.distance(boxB._min), boxA._max.distance(boxB._max));
        return min(minA, minB);
    }


}

