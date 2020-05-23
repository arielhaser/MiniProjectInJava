package elements;

import primitives.*;

/**
 * abstract class representing the lights
 */
abstract class Light {
    protected Color _intensity;

    /**
     * Light constructor
     * @param _intensity the intensity of the light
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * getter intensity of the light
     * @return the light intensity
     */
    public Color get_intensity() {
        return _intensity;
    }
}
