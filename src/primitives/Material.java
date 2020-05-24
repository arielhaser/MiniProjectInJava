package primitives;

public class Material {
    double _kT;
    double _kR;
    double _kD;
    double _kS;
    int _nShininess;

    /**
     * the material constructor with the attenuation coefficients and the shininess
     * @param _kD attenuation coefficient kD
     * @param _kS attenuation coefficient ks
     * @param _nShininess the shininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kR = 0;
        this._kT = 0;
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kT = _kT;
        this._kR = _kR;
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * getter for the kD coefficient
     * @return the kD coefficient
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter for the kS coefficient
     * @return the kS coefficient
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter for the shininess
     * @return the shininess
     */
    public int get_nShininess() {
        return _nShininess;
    }

    public double get_kT() {
        return _kT;
    }

    public double get_kR() {
        return _kR;
    }
}
