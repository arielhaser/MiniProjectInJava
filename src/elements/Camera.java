package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point3D _p0;
    private Vector _vUp;
    private Vector _vTo;
    private Vector _vRight;

    public Camera(Point3D _p0, Vector _vUp, Vector _vTo) {
        if (_vTo.dotProduct(_vUp) != 0)
            throw new IllegalArgumentException("Vectors aren't orthogonally each other");
        this._p0 = _p0;
        this._vUp = _vUp.normalize();
        this._vTo = _vTo.normalize();
        this._vRight = _vTo.crossProduct(_vUp);
    }

    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_vUp() {
        return _vUp;
    }

    public Vector get_vTo() {
        return _vTo;
    }

    public Vector get_vRight() {
        return _vRight;
    }

    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        double rX = screenWidth / nX;
        double rY = screenHeight / nY;
        double xJ = (j - nX /2d) * rX + rX/2d;
        double yI = (i - nY /2d) * rY + rY/2d;

        Point3D pIJ = _p0.add(_vTo.scale(screenDistance));
        if (!isZero(xJ)) pIJ = pIJ.add(_vRight.scale(xJ));
        if (!isZero(yI)) pIJ = pIJ.add(_vUp.scale(-yI));

        return new Ray(_p0, pIJ.subtract(_p0));

    }

}
