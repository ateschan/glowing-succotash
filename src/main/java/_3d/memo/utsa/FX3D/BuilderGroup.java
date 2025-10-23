package _3d.memo.utsa.FX3D;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;

import static _3d.memo.utsa.MainEntry.VIEWPORT_SIZE;

// Builds a javafx group that holds all 3D objects as children
// Entire BuilderGroup can be rotated around an anchor point in a 3D scene
public class BuilderGroup extends Group {

    //Next three track moused drag and object angle.
    //Tracks drag starting point for x and y
    public double anchorX, anchorY;

    //Keep track of current angle for x and y
    public double anchorAngleX = -1;
    public double anchorAngleY = -1;

    //We will update these after drag. Using JavaFX property to bind with object
    public final DoubleProperty angleX = new SimpleDoubleProperty(-1);
    public final DoubleProperty angleY = new SimpleDoubleProperty(-1);

    //Model Color
    private Color modelColor = Color.rgb(160, 140, 190);

    //Model Material
    private PhongMaterial modelMaterial = new PhongMaterial(modelColor);

    //Model meshview
    private MeshView[] modelMesh;

    private Lighting lights;
    private PerspectiveCamera camera;



    public BuilderGroup() {
    }

    //Parameterized Constructor
    public BuilderGroup(MeshView[] m) {
        this.setTranslateX(0);
        this.setTranslateY(0);
        this.setTranslateZ(0);
        this.setScaleX(3);
        this.setScaleY(3);
        this.setScaleZ(3);

        this.modelMesh = m;
        this.addModelMesh();
        this.getChildren().addAll(m);
        this.addLighting();
    }

    public void setModelFromFileName(String fileName) throws IOException {
        this.modelMesh = ModeledMeshView.LoadMeshView(fileName);
        this.addModelMesh();
        this.getChildren().addAll(this.modelMesh);
        this.addLighting();
    }

    private void setMaterial(MeshView m) {
        modelMaterial.setSpecularColor(this.modelColor);
        modelMaterial.setSpecularPower(16);
        m.setMaterial(this.modelMaterial);
    }

    public void addUserInputControls(Stage primaryStage, SubScene dscene) {
        addMouseControl(dscene, primaryStage);
        addKeyboardControl(primaryStage);
        addCamera(dscene);
    }

    //Adds a single model mesh as a child to builder group
    private void addModelMesh() {
        for (MeshView m : this.modelMesh) {
            setMaterial(m);
            m.setTranslateX(0);
            m.setTranslateY(0);
            m.setTranslateZ(0);
            m.setScaleX(1);
            m.setScaleY(1);
            m.setScaleZ(1);
            m.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(0, Rotate.X_AXIS));
        }
    }

    private void addLighting() {
        this.lights = new Lighting();
        this.getChildren().add(this.lights.aLight);
        for (PointLight p : this.lights.pLights) {
            this.getChildren().add(p);
        }
    }

    public void setPointLightColor(Color color) {
        this.lights.setPointLightColor(color);
    }

    public void setAmbientLightColor(Color color) {
        this.lights.setPointLightColor(color);
    }

    public void setModelColor(Color modelColor) {
        this.modelColor = modelColor;
    }

    public void addCamera(SubScene scene) {
        this.camera = new PerspectiveCamera();
        this.camera.setFieldOfView(30);
        this.camera.setTranslateX(-VIEWPORT_SIZE);
        this.camera.setTranslateY(-VIEWPORT_SIZE);
        scene.setCamera(this.camera);
    }

    private void addMouseControl(SubScene scene, Stage primaryStage) {
        Rotate xRotate;
        Rotate yRotate;

        this.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(this.angleX);
        yRotate.angleProperty().bind(this.angleY);

        scene.setOnMousePressed(event -> {
            this.anchorX = event.getSceneX();
            this.anchorY = event.getSceneY();
            this.anchorAngleX = this.angleX.get();
            this.anchorAngleY = this.angleY.get();
        });

        primaryStage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            this.translateZProperty().set(this.getTranslateZ() - delta);
        });

        scene.setOnMouseDragged(event -> {
            this.angleX.set(this.anchorAngleX - (this.anchorY - event.getSceneY()));
            this.angleY.set(this.anchorAngleY + this.anchorX - event.getSceneX());
        });
    }

    private void addKeyboardControl(Stage primaryStage) {
        //Input handling right err
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
            switch (event.getCode()) {
                case S:
                    this.translateYProperty().set(this.getTranslateY() + 10);
                    break;
                case W:
                    this.translateYProperty().set(this.getTranslateY() - 10);
                    break;
                case A:
                    this.translateXProperty().set(this.getTranslateX() - 10);
                    break;
                case D:
                    this.translateXProperty().set(this.getTranslateX() + 10);
                    break;
                case Z:
                    for (MeshView m : this.modelMesh) {
                        m.setScaleX(m.getScaleX() - 0.5);
                        m.setScaleY(m.getScaleY() - 0.5);
                        m.setScaleZ(m.getScaleZ() - 0.5);
                    }
                    break;

                case X:
                    for (MeshView m : this.modelMesh) {
                        m.setScaleX(m.getScaleX() + 0.5);
                        m.setScaleY(m.getScaleY() + 0.5);
                        m.setScaleZ(m.getScaleZ() + 0.5);
                    }
            }
        });
    }
}