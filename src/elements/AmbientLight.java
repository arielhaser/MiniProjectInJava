package elements;

import primitives.Color;

/**
 * A class that represents ambient light of the scene
 */
public class AmbientLight {
    Color _intensity;

    /**
     * the AmbientLight constructor using _intensity and _kA parameters
     * @param _intensity intensity filling lighting
     */
    public AmbientLight(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * a function allowing to  return the value of the Color ambient lighting intensity.
     * @return the value of the Color ambient lighting intensity.
     */
    public java.awt.Color getIntensity(){
        return _intensity.getColor();
    }
}
