package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * An abstract class for  all geometry shapes
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

    /**
     *Geometry constructor with default color: BLACK
     */
    public Geometry() {
        this(Color.BLACK);
    }

    /**
     * Geometry constructor that receive the emission Color
     * @param _emission the Color of the geometry
     */
    public Geometry(Color _emission) {
        this._emission = _emission;
        this._material = new Material(0,0,0);
    }

    /**
     * Geometry constructor that receive the emission Color and the material
     * @param _emission the emission color of the geometry
     * @param _material the material of the geometry
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    /**
     * getter for the normal
     * @param p Point p
     * @return the vector normal
     */
    public abstract Vector getNormal(Point3D p);

    /**
     * getter for the emission Color
     * @return th emission color
     */
    public Color get_emission() {
        return _emission;
    }

    /**
     * getter for the material
     * @return the material
     */
    public Material get_material() {
        return _material;
    }
}
