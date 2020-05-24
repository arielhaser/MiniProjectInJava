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
import static primitives.Util.alignZero;

/**
 * A class for rendering image by the scene with image writer element
 * to determine the properties of the image
 */
public class Render {
    Scene _scene;
    ImageWriter _imageWriter;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        Color background = _scene.getBackground();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double distance = _scene.getDistance();
        int width = (int)_imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();
        Ray ray;
        for(int row = 0; row < nX;  row++){
            for(int col = 0; col < nY; col++){
                ray = camera.constructRayThroughPixel(nX, nY, col, row, distance, width, height);
                GeoPoint closestPoint = findClosestIntersection(ray);
                if (closestPoint == null){
                    _imageWriter.writePixel(col, row, background); // the pixel with the background color at this point
                }
                else{
                    _imageWriter.writePixel(col, row, calcColor(closestPoint, ray));
                }
            }
        }

    }

    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().get_intensity());
    }

    /**
     * Calculate the exact color intensity which the pixel should be draw
     * @param geo = the nearest point which insert the objects' by the ray
     * @return = the color of the pixel
     */
    private Color calcColor(GeoPoint geo, Ray inRay, int level, double k)
    {
        Color color = new Color (geo.geometry.get_emission());

        Vector v = geo.point.subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = geo.geometry.getNormal(geo.point);
        Material material = geo.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(geo.point);
            if (n.dotProduct(l)*n.dotProduct(v) > 0) {
                double ktr = transparency(lightSource, l, n, geo);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geo.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        if (level == 1) return Color.BLACK;
        double kr = geo.geometry.get_material().get_kR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geo.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kr));
        }
        double kt = geo.geometry.get_material().get_kT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geo.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));
        }
        return color;
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

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint geo : intersections) {
            if (alignZero(geo.point.distance(gp.point) - lightDistance) <= 0
            && gp.geometry.get_material().get_kT() == 0)
                return false;
        }
        return true;
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = light.getDistance(geopoint.point);
        double ktr = 1.0;
        for (GeoPoint geo : intersections) {
            if (alignZero(geo.point.distance(geopoint.point) - lightDistance) <= 0){
                ktr *= geo.geometry.get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    /**
     * print a grid on the image utself, useful for debugging
     * @param interval = a number which decide how many times the line should be printed
     * @param colorsep = specific color of the grid
     */
    public void printGrid(int interval,Color colorsep) {
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

    private GeoPoint findClosestIntersection(Ray ray){

        Intersectable geometries = _scene.getGeometries();
        List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
        if (intersectionPoints == null)
            return null;

        double minDistance = Double.MAX_VALUE;
        Point3D p0 = ray.get_p00();
        GeoPoint minDistancePoint = null;
        for (GeoPoint geoPoint: intersectionPoints)
        {
            if(p0.distance(geoPoint.point) < minDistance){
                minDistance = p0.distance(geoPoint.point);
                minDistancePoint = new GeoPoint(geoPoint.geometry, geoPoint.point);}
        }
        return minDistancePoint;

    }

    public Ray constructReflectedRay(Vector n, Point3D point, Ray ray){
        Vector v = ray.get_direction();
        Vector r = (n.scale((v.dotProduct(n))*-2)).add(v);
        return new Ray(point, r, n);
    }

    public Ray constructRefractedRay(Vector n, Point3D point, Ray ray){
        return new Ray(point, ray.get_direction(), n);
    }

}

