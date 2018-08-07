package cmarsh.npr.controllers;

import cmarsh.npr.model.SketchDrawer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class DrawingObserver {
    private SketchDrawer drawerModel;
    public JFileChooser fc;

    public DrawingObserver() {
        drawerModel = new SketchDrawer();
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

    public EventHandler greyImage(ImageView imageView) {
        return event -> {
            System.out.println("I currently do nothing!");
        };

    }

    public EventHandler saveImage(ImageView imageView) {
        return event -> {
            fc.showSaveDialog(null);
            File outFile = new File(fc.getSelectedFile().getName());
            try {
                ImageIO.write(drawerModel.getImage(), "png", outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
