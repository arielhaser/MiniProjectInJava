package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.awt.*;
import java.awt.Color;
import java.util.List;

public class Render {
    Scene _scene;
    ImageWriter _imageWriter;

    public Render(Scene _scene) {
        this._scene = _scene;
    }


    public Render(Scene _scene, ImageWriter _imageWriter) {
        this._scene = _scene;
        this._imageWriter = _imageWriter;
    }

    public Scene get_scene() {
        return _scene;
    }

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
                if (intersectionPoints.isEmpty()){
                    _imageWriter.writePixel(col, row, background);
                }
                else{
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(col, row, calcColor(closestPoint));

                }

            }
        }

    }

    private Color calcColor(Point3D point)
    {
     return _scene.getAmbientLight().getIntensity();
    }

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

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

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
