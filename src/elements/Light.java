package elements;

import primitives.Color;

/**
 * abstract class representing the light of the scene
 */
public abstract class Light {
    protected Color _intenstity;

    /**
     * constructor of light intensity
     * @param _intenstity the intensity of the light
     */
    public Light(Color _intenstity) {
        this._intenstity = _intenstity;
    }

    /**
     * getter og light intensity
     * @return the light intensity
     */
    public Color getIntenstity() {
        return _intenstity;
    }
}
