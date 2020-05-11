package primitives;

public class Material {
    double _kD;
    double _kS;
    int _nShininess;

    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    public double get_kD() {
        return _kD;
    }

    public double get_kS() {
        return _kS;
    }

    public int get_nShininess() {
        return _nShininess;
    }
}
