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

    public void add(Intersectable _geometry) {
        this._geometries.add(_geometry);
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = new ArrayList<>();
        for (Intersectable geometry : _geometries) {
            List<Point3D> temp_result = geometry.findIntersections(ray);
            if(temp_result != null)
                result.addAll(temp_result);
        }
        if (result.isEmpty())
            return null;
        return result;
    }
}
