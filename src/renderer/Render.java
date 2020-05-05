package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.awt.*;
import java.awt.Color;
import java.util.List;

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
    public Render(Scene _scene, ImageWriter _imageWriter) {
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
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null){
                    _imageWriter.writePixel(col, row, background);
                }
                else{
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(col, row, calcColor(closestPoint));

                }

            }
        }

    }

    /**
     * Calculate the exact color which the pixel should be draw
     * @param point = the nearest point which insert the objects' by the ray
     * @return = the color of the pixel
     */
    private Color calcColor(Point3D point)
    {
     return _scene.getAmbientLight().getIntensity();
    }

    /**
     * Find the closest point in the points on objects' which inserted
     * by the ray
     * @param intersectionPoints = list of the interaction points
     * @return the closet 3D point
     */
    private Point3D getClosestPoint(List<Point3D> intersectionPoints)
    {
        double minDistance = Double.MAX_VALUE;
        Point3D p0 = this._scene.getCamera().get_p0();
        Point3D minDistancePoint = null;

        for (Point3D point: intersectionPoints)
        {
         if(p0.distance(point) < minDistance){
            minDistance = p0.distance(point);
             minDistancePoint = new Point3D(point);}
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
    public void printGrid(int interval,java.awt.Color colorsep) {
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
