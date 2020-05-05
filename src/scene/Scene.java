package scene;

import elements.AmbientLight;
import elements.Camera;
//import elements.DirectionalLight;
//import elements.Light;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contain the all elements that a scene should be included
 * which is: background color, the color of the light, the geometries,
 * the passion of the camera and the distance between the camera and
 * the view plane
 */
public class Scene {
    private  final String _name;
    private  Color _background;
    private  AmbientLight _ambientLight;
    private  Geometries _geometries;
    private  Camera _camera;
    private  double _distance;

    /**
     * Scene's constructor
     * @param _name = the name of the scene
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries = new Geometries(new ArrayList<Intersectable>());
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistance() {
        return _distance;
    }

    /**
     * function  allowing to add shapes to the to the 3D geometric model
     * @param geometries undefined number of geometric shapes
     */
    public void addGeometries(Intersectable... geometries)
    {
        for (Intersectable intersectable :geometries)
        {
         _geometries.add(intersectable);
        }
    }

    public void setBackground(Color _background) {
        this._background = _background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }
}
