package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        double ds = p.distanceSquared(_position);
        return _intensity.reduce(_kC+_kL*d+_kQ*ds);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position);
    }
}
