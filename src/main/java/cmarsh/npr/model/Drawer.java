package cmarsh.npr.model;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Drawer {
    private PixMap pixMap;
    private BufferedImage img;

    public Drawer() {
        pixMap = null;
        img = null;
    }

    public BufferedImage getImage() {
        return img;
    }

    /**
     * Load the file selected from the "Load" button in the view into a pixel map
     * @param file file to load the pixel map from
     */
    public void fromImage(File file) {
        try {
           img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pixMap = new PixMap(img);
    }

    /**
     * Return an image with the edges detected by the Canny edge detection algorithm
     * @param filename input image file path
     * @return image with detected edges shown against black background
     */
    public Image cannyize(String filename){
        //convert image chosen to opencv matrix
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat matrix = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

        //apply canny edge detection
        Mat edges = new Mat();
        Imgproc.Canny(matrix, edges, 40, 40*3);

        Mat subMat = new Mat(matrix.size(), matrix.type());
        subMat.setTo(new Scalar(255));

        Core.subtract(subMat, edges, edges);

        //convert matrix to image
        MatOfByte buf = new MatOfByte();
        Imgcodecs.imencode(".png", edges, buf);
        byte[] ba = buf.toArray();

        Image retImage = new Image(new ByteArrayInputStream(ba));

        return retImage;
    }
}
