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
    private Drawer drawerModel;
    public JFileChooser fc;

    public DrawingObserver() {
        drawerModel = new Drawer();
        fc = new JFileChooser();
    }

    public EventHandler loadImage(ImageView imageView){
        return event -> {
            fc.showOpenDialog(null);
            imageView.setImage(new Image(fc.getSelectedFile().toURI().toString()));
            imageView.setPreserveRatio(true);
            drawerModel.fromImage(fc.getSelectedFile());
        };
    }

    public EventHandler cannyImage(final ImageView imageView) {
        return event -> {
            Image cannyImage = drawerModel.cannyize(fc.getSelectedFile().getPath());
            imageView.setImage(cannyImage);
        };

    }

    public EventHandler saveImage(ImageView imageView) {
        return event -> {
            fc.showSaveDialog(null);
            File outFile = new File(fc.getSelectedFile().getPath());
            try {
                ImageIO.write(drawerModel.getImage(), "png", outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
