package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface representing all the light sources
 */
public interface LightSource {
    /**
     * function to receive the intensity of the color at this point
     * @param p the point of the geometry
     * @return the color intensity at the point
     */
    public Color getIntensity(Point3D p);

    /**
     * function to receive the vector from the light source to the point p of the geometry
     * @param p the point of the geometry
     * @return the vector direction  the light source to the point
     */
    public Vector getL(Point3D p);

    /**
     * function to receive the distance between the light source and the point
     * @param point the point of the geometry
     * @return the distance between the light source and the point
     */
    double getDistance(Point3D point);

    /**
     * function to receive the radius of the source light
     * @return the radius of the source light
     */
    public double getRadius();
}
