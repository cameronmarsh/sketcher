package cmarsh.npr.model;


import com.jhlabs.composite.ColorDodgeComposite;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static javafx.embed.swing.SwingFXUtils.fromFXImage;
import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class Drawer {
    private BufferedImage img;


    public Drawer() {
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
        img = matToImage(edges);
        Image retImage = toFXImage(img, null);

        return retImage;
    }


    /**
     * Convert the OpenCV matrix to a Buffered Image
     * @param matrix Matrix object to covert
     * @return Buffered Image containing the information of the OpenCV matrix
     */
    public BufferedImage matToImage(Mat matrix){
        //convert matrix to image
        MatOfByte buf = new MatOfByte();
        Imgcodecs.imencode(".png", matrix, buf);
        byte[] ba = buf.toArray();

        return  fromFXImage(new Image(new ByteArrayInputStream(ba)), null);
    }

    /**
     * Perform a color dodge blending method to two images
     * @param greyscale top layer
     * @param neg bottom layer
     * @return Blended image
     */
    public BufferedImage blend(BufferedImage greyscale, BufferedImage neg){
        ColorDodgeComposite cdComposite = new ColorDodgeComposite(1);
        CompositeContext compContext = cdComposite.createContext(neg.getColorModel(), greyscale.getColorModel(), null);
        Raster negRaster = neg.getRaster();
        Raster greyRaster = greyscale.getRaster();

        BufferedImage composite = new BufferedImage(greyscale.getWidth(), greyscale.getHeight(), greyscale.getType());
        WritableRaster colorDodgeRaster = composite.getRaster();
        compContext.compose(negRaster, greyRaster, colorDodgeRaster);

        return composite;
    }

    /**
     * Return an image that appears like a shaded picture
     * @param filename file containing the source image
     * @return the shaded version of the source image
     */
    public Image shade(String filename){
        //read in image as greyscale
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat matrix = Imgcodecs.imread(filename, Imgcodecs.CV_IMWRITE_PAM_FORMAT_GRAYSCALE);

        //negate the greyscale image
        Mat subMat = new Mat(matrix.size(), matrix.type());
        subMat.setTo(new Scalar(255));

        Core.subtract(subMat, matrix, subMat);

        //apply gaussian blurring
        Imgproc.GaussianBlur(subMat, subMat, new Size(45, 45), 0);

        //apply color dodge of blurred image and source
        BufferedImage imageGreyscale = matToImage(matrix);
        BufferedImage imageNegBlurred = matToImage(subMat);
        BufferedImage shaded = blend(imageGreyscale, imageNegBlurred);

        img = shaded;

        return toFXImage(shaded, null);
    }
}
