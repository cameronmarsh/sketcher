package cmarsh.npr.model;

import cmarsh.npr.model.PixMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SketchDrawer {
    private PixMap pixMap;
    private BufferedImage img;

    public SketchDrawer() {
        pixMap = null;
        img = null;
    }

    public BufferedImage getImage() {
        return img;
    }

    /**
     * Load the file selected from the "Load" button in the view into a pixel map
     * @param file
     */
    public void fromImage(File file) {
        try {
           img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pixMap = new PixMap(img);
    }
}
