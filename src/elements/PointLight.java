package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class representing the PointLight such as light bulb
 */
public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     * the Point light constructor with his intensity, his position, and the attenuation coefficients
     * @param _intensity intensity of the light
     * @param _position position of the light
     * @param _kC attenuation coefficient kC
     * @param _kL attenuation coefficient kL
     * @param _kQ attenuation coefficient kQ
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /**
     * function to receive the intensity of the color at this point
     * @param p the point of the geometry
     * @return the color intensity at the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        double ds = p.distanceSquared(_position);
        return _intensity.reduce(_kC+_kL*d+_kQ*ds);
    }

    /**
     * getter for the vector l : from the light to the point
     * @param p the point p
     * @return the vector normalized from the light source to the point
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     * function to receive the distance between the light source and the point
     * @param point the point of the geometry
     * @return the distance between the light source and the point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }
}
