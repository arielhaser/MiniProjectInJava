package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that contain the all elements that a scene should be included
 * which is: background color, the color of the light, the geometries,
 * the passion of the camera, the distance between the camera and
 * the view plane and a list that contains the light sources
 */
public class Scene {
    private  final String _name;
    private  Color _background;
    private  AmbientLight _ambientLight;
    private  Geometries _geometries;
    private  Camera _camera;
    private  double _distance;
    List<LightSource> _lights = new LinkedList<LightSource>();


    /**
     * Scene's constructor
     * @param _name = the name of the scene
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries = new Geometries(new ArrayList<Intersectable>());
    }

    /**
     * getter for the name's scene
     * @return the names's scene
     */
    public String getName() {
        return _name;
    }

    /**
     * getter for the background color
     * @return the background color
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * getter for the AmbientLight
     * @return the AmbientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * getter for the Geometries
     * @return the Geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * getter for the camera
     * @return the camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * getter for the distance between the camera and the view plane
     * @return the distance between the camera and the view plane
     */
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

    /**
     * getter for the list of LightSources
     * @return the list of LightSources
     */
    public List<LightSource> get_lights() {
        return _lights;
    }
    /**
     * setter of the background
     * @param _background a background color
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * setter of the ambientLight
     * @param _ambientLight an ambientLight
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * setter of the camera
     * @param _camera a camera
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * setter of the distance
     * @param _distance a distance between the camera and the view plane
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }


    /**
     * function allowing to add the lights to the light source list
     * @param lights
     */
    public void addLights(LightSource... lights) {
        for (LightSource light : lights) {
            _lights.add(light);
        }
    }
}
