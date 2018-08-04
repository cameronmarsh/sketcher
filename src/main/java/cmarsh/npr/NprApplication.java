package cmarsh.npr;

import cmarsh.npr.controllers.Drawer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NprApplication extends Application {
    private Image img;
    private Drawer drawer;

    public NprApplication() {

    }


    public void start(Stage stage) throws Exception {
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        Text imgInfo = new Text("This is where the image info will be displayed");
        HBox toolbar = new HBox(50);
        toolbar.setAlignment(Pos.CENTER);
        toolbar.getChildren().addAll(saveButton, loadButton);


        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(imgInfo);
        borderPane.setTop(toolbar);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

    }
}
