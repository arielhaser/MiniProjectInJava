package scene;

import elements.AmbientLight;
import elements.Camera;
//import elements.DirectionalLight;
//import elements.Light;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private  final String _name;
    private  Color _background;
    private  AmbientLight _ambientLight;
    private  Geometries _geometries;
    private  Camera _camera;
    private  double _distance;


    public Scene(String _name) {
        this._name = _name;

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
