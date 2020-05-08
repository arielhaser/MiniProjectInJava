package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite class that include a collection of composite geometries
 */

public class Geometries implements Intersectable {
    List<Intersectable> _geometries;

    /**
     * constructor of Geometries
     */
    public Geometries(){
        this._geometries = new ArrayList<Intersectable>();
    }

    /**
     * Geometries constructor that allow to add geometries
     * @param _geometries to add to the collection of geometries
     */
    public Geometries(List<Intersectable> _geometries) {
        this._geometries = _geometries;
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
        List<GeoPoint> result = new ArrayList<>();
        for (Intersectable geometry : _geometries) {
            List<GeoPoint> temp_result = geometry.findIntersections(ray);
            if(temp_result != null)
                result.addAll(temp_result);
        }
        if (result.isEmpty()) // there isn't intersection
            return null;
        return result;
    }
}
