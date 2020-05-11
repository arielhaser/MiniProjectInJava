package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static sun.swing.MenuItemLayoutHelper.max;

public class SpotLight extends PointLight {
    private Vector _direction;

    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = _direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(max(0, (int) _direction.dotProduct(getL(p))));
    }

}
