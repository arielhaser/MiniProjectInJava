package elements;

import primitives.Color;

/**
 * A class that represents ambient light of the scene
 */
public class AmbientLight {
    Color _intensity;
    double _kA;

    /**
     * the AmbientLight constructor using _intensity and _kA parameters
     * @param _intensity intensity filling lighting
     * @param _kA Attenuation coefficient
     */
    public AmbientLight(Color _intensity, double _kA) {
        this._intensity = _intensity;
        //this._kA = _kA; _kA is always 1 for the Ambient Light so we don't use it
    }

    /**
     * a function allowing to  return the value of the Color ambient lighting intensity.
     * @return the value of the Color ambient lighting intensity.
     */
    public java.awt.Color getIntensity(){
        return _intensity.getColor();
    }
}
