package cmarsh.npr.controllers;

import cmarsh.npr.model.Drawer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class DrawingObserver {
    private Drawer drawer;
    public JFileChooser fc;

    public DrawingObserver() {
        drawer = new Drawer();
        fc = new JFileChooser();
    }

    /**
     * Load the image selected in the gui file directory into the image view for processing
     * @param imageView the view to set the image into
     * @return Event handler expression when the "Load" button is selected
     */
    public EventHandler loadImage(ImageView imageView){
        return event -> {
            fc.showOpenDialog(null);
            imageView.setImage(new Image(fc.getSelectedFile().toURI().toString()));
            imageView.setPreserveRatio(true);
            drawer.fromImage(fc.getSelectedFile());
        };
    }

    /**
     * Add Canny Edge detection edges to the image view
     * @param imageView image onto which edges are added
     * @return Event handler expression to execute when the "Canny" button is selected
     */
    public EventHandler cannyImage(final ImageView imageView) {
        return event -> {
            //TODO: change the image passed in --> does it need to be a filename?
            Image cannyImage = drawer.cannyize(fc.getSelectedFile().getPath());
            imageView.setImage(cannyImage);
        };

    }

    /**
     * Saves the current image to the file and location selected
     * @return Event handler expression to execute when the "Save" button is selected
     */
    public EventHandler saveImage() {
        return event -> {
            fc.showSaveDialog(null);
            File outFile = new File(fc.getSelectedFile().getPath());
            try {
                ImageIO.write(drawer.getImage(), "png", outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * Adds shading to the image displayed
     * @param imageView image onto which shading is added
     * @return event handler expression that executes when the "Shade" button is selected
     */
    public EventHandler shadeImage(ImageView imageView){
        return event -> {
            Image shadedImage = drawer.shade(fc.getSelectedFile().getPath());
            imageView.setImage(shadedImage);
        };
    }
}
