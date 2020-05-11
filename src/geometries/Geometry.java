package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * An interface for all geometry shapes
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

    public Geometry() {
        this(Color.BLACK);
    }

    public Geometry(Color _emission) {
        this._emission = _emission;
        this._material = new Material(0,0,0);
    }

    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    public abstract Vector getNormal(Point3D p);

    public Color get_emission() {
        return _emission;
    }

    public Material get_material() {
        return _material;
    }
}
