package elements;

import primitives.*;


public class SpotLight extends PointLight {
    Vector _direction;


    public SpotLight(Color _intenstity, Point3D _position, double _kC, double _kL, double _kQ, Vector _direction) {
        super(_intenstity, _position, _kC, _kL, _kQ);
        this._direction = _direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {

        double project = _direction.dotProduct(getL(p));
        Color pointLightIntens = super.getIntensity(p);
        Color IL = pointLightIntens.scale(Math.max(0, project));
        return IL;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

}