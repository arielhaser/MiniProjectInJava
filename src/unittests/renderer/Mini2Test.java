package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class Mini2Test {
    /**
     * A test to recognise the performance of the building image with BVH method.
     */
    @Test
    public void bizua() {
        long startTime = System.nanoTime();

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 8000, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

        for (int j=0; j<10; j++){
            Geometries new_geo = new Geometries();
            for(int i=0; i<25; i++){
                int r = (int)(256*Math.random());
                int g = (int)(256*Math.random());
                int b = (int)(256*Math.random());
                new_geo.add(
                        new Sphere(new Color(r, g, b), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                100, new Point3D(-2000+200*i, 400, -2000+500*j))
                );
            }
            scene.addGeometries(new_geo);
        }

        //scene.getGeometries().autoBVHConstruction();

        scene.addLights(new SpotLight(new Color(300, 400, 400), //
                        new Point3D(-1100, 3100, 2000), new Vector(1100, -3100, -2000),  1, 4E-5, 2E-7, 200),
                new SpotLight(new Color(20, 40, 0), new Point3D(1100, 3100, 2000), new Vector(-1100, -3100, -2000),
                        1, 4E-5, 2E-7, 200),
                new SpotLight(new Color(302, 400, 400), new Point3D(2000, 0, 2000), new Vector(-2000, 0, -2000),
                        1, 4E-5, 2E-7, 200));


        ImageWriter imageWriter = new ImageWriter("essai2", 500, 500, 1200, 1200);
        Render render = new Render(imageWriter, scene).setMultithreading(1).setDebugPrint();

        render.renderImage();
        render.writeToImage();

        long endTime   = System.nanoTime();
        double totalTime = (endTime - startTime)/Math.pow(10,9);
        System.out.println(totalTime);
    }
}
