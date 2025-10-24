package edu.utsa.cs3443;
import edu.utsa.cs3443.FX3D.BuilderGroup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

//FXML version must be <AnchorPane prefHeight="164.0" prefWidth="574.0" xmlns="http://javafx.com/javafx/17.0.12" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.example.jemofx.HelloController">
public class MainEntry extends Application {
    public static final int VIEWPORT_SIZE = 1080;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //In this sense, group can refer to our single 3d object
        BuilderGroup mg = new BuilderGroup();
        mg.setModelFromFileName("data/20mm_cube.stl");

        //Adding 3d scene and camera
        SubScene dscene = new SubScene(mg, VIEWPORT_SIZE, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        dscene.setFill(Color.rgb(135, 135, 240));

        //Adding ui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
        AnchorPane pane = loader.load();
        pane.getChildren().add(0, dscene);
        Scene scene = new Scene(pane);
        mg.addUserInputControls(primaryStage, dscene);

        MainScreenController mainScreenController = loader.getController();
        mainScreenController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("3D Memo");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        mainScreenController.attachNewModelToPrimaryStage("data/CylinderHead-ascii.stl");
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}
