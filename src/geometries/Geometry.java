package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * An interface for all geometry shapes
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;

    public Geometry() {
        this(Color.BLACK);
    }

    public Geometry(Color _emission) {
        this._emission = _emission;
    }

    public abstract Vector getNormal(Point3D p);

    public Color get_emission() {
        return _emission;
    }
}
