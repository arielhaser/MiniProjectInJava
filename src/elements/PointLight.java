package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


public class PointLight extends Light implements LightSource {
     protected Point3D _position;
    double _kC, _kL, _kQ;


    public PointLight(Color _intenstity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intenstity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d = _position.distance(p);
        double dSquared = _position.distanceSquared(p);

        Color IL = _intenstity.reduce(_kC + _kL*d + _kQ*dSquared);
        return IL;
    }


    @Override
    public Vector getL(Point3D p) {

        if (_position.equals(p)){
            return null;}
        else{
            return p.subtract(_position).normalize();

        }
    }
}
