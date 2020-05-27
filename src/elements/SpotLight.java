package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class representing the spotlight such as flash light
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * the Spot light constructor with his intensity, his position, his direction and the attenuation coefficients
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = _direction.normalized();
    }

    /**
     * function to receive the intensity of the color at this point
     * @param p the point of the geometry
     * @return the color intensity at the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(getL(p))));
    }

}
