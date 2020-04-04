package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {
    List<Intersectable> _geometries;

    public Geometries(){
        this._geometries = new ArrayList<Intersectable>();
    }

    public Geometries(List<Intersectable> _geometries) {
        this._geometries = _geometries;
    }

    public void add(List<Intersectable> _geometries) {
        this._geometries.add((Intersectable) _geometries);
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
