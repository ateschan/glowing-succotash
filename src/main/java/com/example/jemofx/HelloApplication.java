package com.example.jemofx;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
//FXML version must be <AnchorPane prefHeight="164.0" prefWidth="574.0" xmlns="http://javafx.com/javafx/17.0.12" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.example.jemofx.HelloController">
public class HelloApplication extends Application {
    private static final String MESH_FILENAME = "src/CylinderHead-ascii.stl";
    private static final double MODEL_SCALE_FACTOR = 2;

    //Square box resolution
    private static final int VIEWPORT_SIZE = 1080;
    private static final Color lightColor = Color.rgb(244, 255, 250);
    private static final Color modelColor = Color.rgb(56, 90, 222);

    private RotatableGroup group;
    private PointLight pointLight;



    //MODEL
    static MeshView[] loadStlMeshView() {
        File file = new File(MESH_FILENAME);
        StlMeshImporter importer = new StlMeshImporter();
        importer.read(file);
        Mesh mesh = importer.getImport();

        return new MeshView[] { new MeshView(mesh) };
    }

    //TODO: MODEL + CONTROLLER NEEDS TO BE SEPARATED
    private RotatableGroup buildScene() {
        //For our implementation, the meshViews is functionally equivalent to a javafx.scene.shape Shape due to it extending the class
        MeshView[] meshViews = loadStlMeshView();
        for (int i = 0; i < meshViews.length; i++) {
            meshViews[i].setTranslateX(0);
            meshViews[i].setTranslateY(0);
            meshViews[i].setTranslateZ(0);
            meshViews[i].setScaleX(MODEL_SCALE_FACTOR);
            meshViews[i].setScaleY(MODEL_SCALE_FACTOR);
            meshViews[i].setScaleZ(MODEL_SCALE_FACTOR);

            //Material (unused atm)
            PhongMaterial sample = new PhongMaterial(modelColor);
            sample.setSpecularColor(lightColor);
            sample.setSpecularPower(16);

            meshViews[i].setMaterial(sample);
            meshViews[i].getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(0, Rotate.X_AXIS));
        }

        //Lighting
        pointLight = new PointLight(lightColor);
        pointLight.setTranslateX(VIEWPORT_SIZE*3/4);
        pointLight.setTranslateY(VIEWPORT_SIZE/2);
        pointLight.setTranslateZ(VIEWPORT_SIZE/2);
        PointLight pointLight2 = new PointLight(lightColor);
        pointLight2.setTranslateX(VIEWPORT_SIZE*1/4);
        pointLight2.setTranslateY(VIEWPORT_SIZE*3/4);
        pointLight2.setTranslateZ(VIEWPORT_SIZE*3/4);
        PointLight pointLight3 = new PointLight(lightColor);
        pointLight3.setTranslateX(VIEWPORT_SIZE*5/8);
        pointLight3.setTranslateY(VIEWPORT_SIZE/2);
        pointLight3.setTranslateZ(0);
        Color ambientColor = Color.rgb(80, 80, 80, 0);
        AmbientLight ambient = new AmbientLight(ambientColor);

        group = new RotatableGroup();
        //defaults
        group.setScaleX(2);
        group.setScaleY(2);
        group.setScaleZ(2);
        group.setTranslateX(0);
        group.setTranslateY(0);
        group.getChildren().addAll(meshViews);
        group.getChildren().add(pointLight);
        group.getChildren().add(pointLight2);
        group.getChildren().add(pointLight3);
        group.getChildren().add(ambient);

        return group;
    }

    //VIEW
    private PerspectiveCamera addCamera(SubScene scene) {
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(30);
        camera.setTranslateX(-VIEWPORT_SIZE + 200);
        camera.setTranslateY(-VIEWPORT_SIZE);
        //perspectiveCamera.setNearClip(0.2);
        scene.setCamera(camera);
        return camera;
    }

    //VIEW Clearly this needs to be in the group class
    private void addMouseControl(RotatableGroup group, SubScene scene, Stage primaryStage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(group.angleX);
        yRotate.angleProperty().bind(group.angleY);

        scene.setOnMousePressed(event -> {
            group.anchorX = event.getSceneX();
            group.anchorY = event.getSceneY();
            group.anchorAngleX = group.angleX.get();
            group.anchorAngleY = group.angleY.get();
        });

        primaryStage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() - delta);
        });

        scene.setOnMouseDragged(event -> {
            group.angleX.set(group.anchorAngleX - (group.anchorY - event.getSceneY()));
            group.angleY.set(group.anchorAngleY + group.anchorX - event.getSceneX());
        });
    }

    //VIEW
    private void addKeyboardControl(RotatableGroup group, Stage primaryStage) {
        //Input handling right err
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
            switch (event.getCode()) {
                case S:
                    group.translateYProperty().set(group.getTranslateY() - 100);
                    break;
                case W:
                    group.translateYProperty().set(group.getTranslateY() + 100);
                    break;
                case A:
                    group.translateXProperty().set(group.getTranslateX() + 100);
                    break;
                case D:
                    group.translateXProperty().set(group.getTranslateX() - 100);
                    break;
            }
        });
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        //In this sense, group can refer to our single 3d object
        RotatableGroup group = buildScene();

        //Adding 3d scene and camera
        SubScene dscene = new SubScene(group, VIEWPORT_SIZE, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        dscene.setFill(Color.rgb(135, 135, 240));
        addMouseControl(group, dscene, primaryStage);
        addKeyboardControl(group, primaryStage);

        //Adding ui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        AnchorPane pane = loader.load();
        pane.getChildren().add(0, dscene);
        Scene scene = new Scene(pane);
        addCamera(dscene);
        primaryStage.setTitle("--> && ✒ 3D");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}