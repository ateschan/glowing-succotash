package _3d.memo.utsa;
import _3d.memo.utsa.model.BuilderGroup;
import _3d.memo.utsa.model.ModeledMeshView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import java.io.IOException;

import static _3d.memo.utsa.model.ModeledMeshView.LoadMeshView;


//FXML version must be <AnchorPane prefHeight="164.0" prefWidth="574.0" xmlns="http://javafx.com/javafx/17.0.12" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.example.jemofx.HelloController">
public class MainEntry extends Application {
    public static final int VIEWPORT_SIZE = 1080;

    @Override
    public void start(Stage primaryStage) throws IOException {
        MeshView[] m = ModeledMeshView.LoadMeshView("data/Tree1.obj");
        //In this sense, group can refer to our single 3d object
        BuilderGroup mg = new BuilderGroup(m);

        //Adding 3d scene and camera
        SubScene dscene = new SubScene(mg, VIEWPORT_SIZE, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        dscene.setFill(Color.rgb(135, 135, 240));

        //Adding ui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
        AnchorPane pane = loader.load();
        pane.getChildren().add(0, dscene);
        Scene scene = new Scene(pane);
        mg.addUserInput(primaryStage, dscene);
        primaryStage.setTitle("3D Memo");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}
