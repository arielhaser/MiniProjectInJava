package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static primitives.Util.isZero;

/**
 * The class that allows to represent Camera
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp, _vTo, _vRight;
    private boolean IMPROVE_PIXEL = false;
    private int NUM_OF_RAYS = 50;

    /**
     * the constructor for Camera: his position and his orientation
     * @param _p0 the location of the camera
     * @param _vTo the Towards direction of the camera
     * @param _vUp the up direction of the camera
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if (_vTo.dotProduct(_vUp) != 0)
            throw new IllegalArgumentException("Vectors aren't orthogonally each other");
        this._p0 = _p0;
        this._vUp = _vUp.normalize();
        this._vTo = _vTo.normalize();
        this._vRight = _vTo.crossProduct(_vUp);
    }
    /**
     * the getter for _pO: camera location
     * @return the location
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * the getter for up direction vector
     * @return the up direction vector
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     * the getter for toward direction vector
     * @return the toward  direction vector
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     * the getter for right direction vector
     * @return the right direction vector
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     * This function allows to construct a ray from Camera location throw the center
     * of a pixel (i, j) in the view plane
     * @param nX the number of pixel in a row of the view plane
     * @param nY the number of pixel in a column of the view plane
     * @param j the column index of the pixel
     * @param i the row index of the pixel
     * @param screenDistance the distance between the view plane and the camera(_p0)
     * @param screenWidth the width of the view plane
     * @param screenHeight the height of the view plane
     * @return the ray from the camera (_p0) through pixel's center
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        double rX = screenWidth / nX; // the width of one pixel
        double rY = screenHeight / nY; // the height of one pixel
        double xJ = (j - nX /2d) * rX + rX/2d;
        double yI = (i - nY /2d) * rY + rY/2d;

        Point3D pIJ = _p0.add(_vTo.scale(screenDistance));
        if (!isZero(xJ)) pIJ = pIJ.add(_vRight.scale(xJ));
        if (!isZero(yI)) pIJ = pIJ.add(_vUp.scale(-yI));

        return new Ray(_p0, pIJ.subtract(_p0));
    }

    /**
     * the function which will allow us to choose the placement of the rays at random in the virtual circle
     * @param rangeMin the minimum limit
     * @param rangeMax the maximum limit
     * @return the random result in the domain
     */
    public double random(double rangeMin, double rangeMax){
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    /**
     *
     * @param nX the number of pixel in a row of the view plane
     * @param nY the number of pixel in a column of the view plane
     * @param col the column index of the pixel
     * @param row the row index of the pixel
     * @param screenDistance the distance between the view plane and the camera(_p0)
     * @param screenWidth the width of the view plane
     * @param screenHeight the height of the view plane
     * @return the beam: the list that contains all the rays from the camera
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int col, int row,
                                               double screenDistance, double screenWidth, double screenHeight){
        Ray center_ray = constructRayThroughPixel (nX, nY, col, row, screenDistance, screenWidth, screenHeight);
        List<Ray> beam = new ArrayList<>();
        beam.add(center_ray);
        if (!IMPROVE_PIXEL) return beam;
        Point3D p0 = center_ray.get_p00();
        Vector v = center_ray.get_direction();
        Vector vx = _vRight;
        Vector vy = _vUp;
        double r = min(screenWidth/nX, screenHeight/nY)/2;
        for (int i=0; i<NUM_OF_RAYS-1; i++){
            // create random polar system coordinates of a point in circle of radius r
            double cosTeta = random(-1,1);
            double sinTeta = sqrt(1 - cosTeta*cosTeta);
            double d = random (-r,r);
            // Convert polar coordinates to Cartesian ones
            double x = d*cosTeta;
            double y = d*sinTeta;
            // pC - center of the circle
            // p0 - start of central ray, v - its direction, distance - from p0 to pC
            Point3D pC = p0.add(v.scale(screenDistance));
            Point3D point = pC;
            if (!isZero(x)) point = point.add(vx.scale(x));
            if (!isZero(y)) point = point.add(vy.scale(y));
            beam.add(new Ray(p0, point.subtract(p0))); // normalized inside Ray constructor
        }
        return beam;
    }

    /**
     * the setter of Improve Pixel
     * @param IMPROVE_PIXEL the boolean
     */
    public void set_IMPROVE_PIXEL(boolean IMPROVE_PIXEL) {
        this.IMPROVE_PIXEL = IMPROVE_PIXEL;
    }

    /**
     * the setter of number of rays
     * @param NUM_OF_RAYS the integer: number of rays
     */
    public void set_NUM_OF_RAYS(int NUM_OF_RAYS) {
        this.NUM_OF_RAYS = NUM_OF_RAYS;
    }
}
