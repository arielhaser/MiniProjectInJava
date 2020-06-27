package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class Mini1Test {
    /**
     * Test which preform all existing methods.
     */
    @Test
    public void essai() {
        long startTime = System.nanoTime();

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 8000, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

        scene.addGeometries(
                new Sphere(new Color(50, 140, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        300, new Point3D(300, 400, 370)),
                new Sphere(new Color(205, 0, 155), new Material(0.25, 0.25, 20, 0.5, 0), // )
                        170, new Point3D(280, 600, 600)),
                new Sphere(new Color(0, 255, 0), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        180, new Point3D(680, 350, 300)),
                new Sphere(new Color(180, 125, 14), new Material(0.25, 0.25, 20, 0.5, 0),
                        160, new Point3D(-80, 355, 600)),
                new Sphere(new Color(255, 0, 0), new Material(0.25, 0.25, 20, 0, 0.5),
                        160, new Point3D(-380, 600, 350)),
                new Sphere(new Color(200, 30, 40), new Material(0.25, 0.25, 20, 1, 0),
                        320, new Point3D(1200, 500, 350)),
                new Triangle(Color.BLACK, new Material(0.8, 0.2, 300),
                        new Point3D(2000, 500, -500), new Point3D(-1500, 500, -500), new Point3D(0, -1500, 1500)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),
                        new Point3D(2000, 500, -500), new Point3D(1500,100,1500), new Point3D(0, -1500, 1500)));

        scene.addLights(new SpotLight(new Color(300, 400, 400), //
                        new Point3D(-1100, 3100, 2000), new Vector(1100, -3100, -2000),  1, 4E-5, 2E-7, 200),
                new SpotLight(new Color(20, 40, 0), new Point3D(1100, 3100, 2000), new Vector(-1100, -3100, -2000),
                        1, 4E-5, 2E-7, 200),
                new SpotLight(new Color(302, 400, 400), new Point3D(2000, 0, 2000), new Vector(-2000, 0, -2000),
                        1, 4E-5, 2E-7, 200));

        ImageWriter imageWriter = new ImageWriter("essai", 500, 500, 1200, 1200);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        scene.getCamera().set_IMPROVE_PIXEL(true);
        render.set_SOFT_SHADOW(true);
        render.renderImage();
        render.writeToImage();

        long endTime   = System.nanoTime();
        double totalTime = (endTime - startTime)/Math.pow(10,9);
        System.out.println(totalTime);
    }
}
