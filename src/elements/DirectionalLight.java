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
     *
     * @param p the point p
     * @return the intensity at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     *
     * @param p the Point p
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
