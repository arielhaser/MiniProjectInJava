package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private  Vector _direction;

    /**
     * constructor of light intensity
     *
     * @param _intenstity the intensity of the light
     */
    public DirectionalLight(Color _intenstity, Vector _direction) {
        super(_intenstity);
        this._direction = _direction.normalized();

    }


    @Override
    public Color getIntensity(Point3D p) {
        return _intenstity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
