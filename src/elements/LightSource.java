package elements;
import primitives.*;
/**
 * an interface for all common actions of light sources
 */
public interface LightSource {

    /**
     * Obtain the intensity of the light source at the point reached
     *
     * @param p the lighted point
     * @return intensity at the point
     */
    public Color getIntensity(Point3D p);

    /**
     * Obtain normalized vector in the direction towards the lighted point from the light source
     * @param p the lighted point
     * @return light to point vector
     */
    public Vector getL(Point3D p);
}
