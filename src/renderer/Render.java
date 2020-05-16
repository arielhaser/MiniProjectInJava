package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import primitives.Color;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

//import java.awt.Color;
import java.util.List;

import static java.lang.Math.max;

/**
 * A class for rendering image by the scene with image writer element
 * to determine the properties of the image
 */
public class Render {
    Scene _scene;
    ImageWriter _imageWriter;

    /**
     * Render's constructor
     * @param _scene = list of 3D objects
     * @param _imageWriter = properties of the image
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._scene = _scene;
        this._imageWriter = _imageWriter;
    }

    /**
     * Draw any pixel in the view plane in specific color
     * which determine by the objects' color's and background
     * in the scene
     */
    public void renderImage()
    {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double distance = _scene.getDistance();
        int width = (int)_imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();
        Ray ray;
        for(int row = 0; row < nX;  row++){
            for(int col = 0; col < nY; col++){
                ray = camera.constructRayThroughPixel(nX, nY, col, row, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null){
                    _imageWriter.writePixel(col, row, background); // the pixel with the background color at this point
                }
                else{
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(col, row, calcColor(closestPoint));

                }
            }
        }

    }

    /**
     * Calculate the exact color intensity which the pixel should be draw
     * @param geo = the nearest point which insert the objects' by the ray
     * @return = the color of the pixel
     */
    private java.awt.Color calcColor(GeoPoint geo)
    {
        Color color = new Color(_scene.getAmbientLight().get_intensity());
        color = color.add(geo.geometry.get_emission());
        Vector v = geo.point.subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = geo.geometry.getNormal(geo.point);
        Material material = geo.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(geo.point);
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                Color lightIntensity = lightSource.getIntensity(geo.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color.getColor();
    }

    /**
     *
     * @param ks specular component coefficient
     * @param l the vector direction l from light to point
     * @param n the vector normal at the point
     * @param v direction from point of view to point
     * @param nShininess shininess level
     * @param lightIntensity light intensity at the point
     * @return specular component light effect at the point
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = (n.scale((l.dotProduct(n))*-2)).add(l);
        double factor = ks*(Math.pow(max((-1)*v.dotProduct(r),0), nShininess));
        return lightIntensity.scale(factor);
    }

    /**
     * Calculate Diffusive component of light reflection.
     * @param kd the coefficient kD
     * @param l the vector direction l from light to point
     * @param n the vector normal at the point
     * @param lightIntensity light intensity at the point
     * @return diffusive component of light reflection
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd*Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);
    }

    /**
     * check if number is positive or negative
     * @param num = number to check
     * @return = 'p' for positive, 'n' for negative
     */
    private char sign(double num) {
        if (num < 0)
            return 'n';
        return 'p';
    }

    /**
     * Find the closest point in the points on objects' which inserted
     * by the ray
     * @param intersectionPoints = list of all the interaction points
     * @return the closet 3D point
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints)
    {
        double minDistance = Double.MAX_VALUE;
        Point3D p0 = this._scene.getCamera().get_p0();
        GeoPoint minDistancePoint = null;

        for (GeoPoint geoPoint: intersectionPoints)
        {
         if(p0.distance(geoPoint.point) < minDistance){
            minDistance = p0.distance(geoPoint.point);
            minDistancePoint = new GeoPoint(geoPoint.geometry, geoPoint.point);}
        }
        return minDistancePoint;
    }

    /**
     * activate writeToImage function which find in ImageWriter class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * print a grid on the image utself, useful for debugging
     * @param interval = a number which decide how many times the line should be printed
     * @param colorsep = specific color of the grid
     */
    public void printGrid(int interval,java.awt.Color colorsep) { ;
        double rows = this._imageWriter.getNx();
        double cols = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, colorsep);
                }
            }
        }
    }
}
