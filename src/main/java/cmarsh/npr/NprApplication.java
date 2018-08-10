package cmarsh.npr;

import cmarsh.npr.view.NprApplicationScene;
import javafx.application.Application;
import javafx.stage.Stage;


public class NprApplication extends Application {
    private final NprApplicationScene nprApplicationScene;

    public NprApplication() {
        nprApplicationScene = new NprApplicationScene();
    }

    public void start(Stage stage) {
        stage.setScene(nprApplicationScene.setScene());
        stage.sizeToScene();
        stage.show();
    }
}
