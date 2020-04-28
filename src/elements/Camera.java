package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * The class that allows to represent Camera
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp, _vTo, _vRight;

    /**
     * the constructor for Camera: his position and his orientation
     * @param _p0 the location of the camera
     * @param _vTo the Towards direction of the camera
     * @param _vUp the up direction of the camera
     */
    public Camera(Point3D _p0, Vector _vUp, Vector _vTo) {
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

}
