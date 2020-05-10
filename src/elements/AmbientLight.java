package elements;

import primitives.Color;

/**
 * A class that represents ambient light of the scene
 */
public class AmbientLight extends Light {

    /**
     * the Ambient Light constructor using the constructor of the class Light
     * @param _intenstity intensity filling lighting
     */
    public AmbientLight(Color _intenstity) {
        super(_intenstity);
    }



}
