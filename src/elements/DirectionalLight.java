package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * the class DirectionLight representing the light such as the sun
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * he Directional light constructor with his intensity and direction
     * @param _intensity intensity of the light
     * @param _direction vector direction
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalized();
    }

    /**
     * function to receive the intensity of the color at this point
     * @param p the point p
     * @return the color intensity at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * function to receive the vector from the light source to the point p of the geometry
     * @param p the Point p
     * @return the vector direction from the light source to the point
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * function to receive the distance between the light source and the point here: POSITIVE_INFINITY
     * @param point the point of the geometry
     * @return the distance between the light source and the point
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
