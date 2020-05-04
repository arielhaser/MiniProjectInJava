package renderer;
import static org.junit.Assert.*;

import org.junit.Test;

import renderer.ImageWriter;
import primitives.*;

import java.awt.Color;

public class ImageWriterTest {
    @Test
    public void writeToImageTest() {
        String imageName = "our image";
        int width = 1600;
        int height = 1000;
        int nx = 800;
        int ny = 500;
        ImageWriter imageWriter = new ImageWriter(imageName, width, height, nx, ny);
        for (int col = 0; col < ny; col++) {
            for (int row = 0; row < nx; row++) {
                if (col % 50 == 0 || row % 50 == 0) {
                    imageWriter.writePixel(row, col, Color.WHITE);
                }
            }
        }
        imageWriter.writeToImage();
    }
}

