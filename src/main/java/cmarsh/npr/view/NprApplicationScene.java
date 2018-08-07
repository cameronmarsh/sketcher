package cmarsh.npr.view;

import cmarsh.npr.controllers.DrawingObserver;
import cmarsh.npr.model.SketchDrawer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class NprApplicationScene {
    private ImageView img;
    DrawingObserver observer;

    public NprApplicationScene() {
        img = new ImageView();
        observer = new DrawingObserver();
    }

    public Scene setScene() {
        //rendering info
        Text imgInfo = new Text("This is where the image info will be displayed");

        //toolbar
        HBox toolbar = new HBox(50);
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        Button greyScale = new Button("Grey");
        toolbar.setAlignment(Pos.CENTER);
        toolbar.getChildren().addAll(saveButton, loadButton, greyScale);

        //image view
        if (img.getImage() == null) {
            img.setFitHeight(500);
            img.setFitWidth(700);
        }

        //construct layout
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(imgInfo);
        borderPane.setCenter(img);
        borderPane.setTop(toolbar);

        //add button listeners
        loadButton.setOnAction(observer.loadImage(img));
        saveButton.setOnAction(observer.saveImage(img));
        greyScale.setOnAction(observer.greyImage(img));

        Scene scene = new Scene(borderPane);
        return scene;

    }
}