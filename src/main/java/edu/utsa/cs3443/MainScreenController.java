package edu.utsa.cs3443;
import edu.utsa.cs3443.FX3D.BuilderGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static edu.utsa.cs3443.MainEntry.VIEWPORT_SIZE;

public class MainScreenController {
    private Stage primaryStage;
    private BuilderGroup mg;

    // PrimaryStage trick through dependency injection
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    protected void onNewNoteClick() throws IOException {}

    @FXML
    protected void onSaveNoteToFileClick() throws IOException {}

    //TODO: Direct setup copied from main, will need to be refactored to swap 3d model within 3d model class
    //Will need to use a filepicker or some shit
    @FXML
    protected void attachNewModelToPrimaryStage(String fileName) throws IOException {
        //In this sense, group can refer to our single 3d object
        BuilderGroup mg = new BuilderGroup();
        mg.setModelFromFileName(fileName);
        //Adding 3d scene and camera
        SubScene dscene = new SubScene(mg, VIEWPORT_SIZE, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        dscene.setFill(Color.rgb(135, 135, 240));

        //Adding ui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
        AnchorPane pane = loader.load();
        pane.getChildren().add(0, dscene);
        Scene scene = new Scene(pane);
        mg.addUserInputControls(this.primaryStage, dscene);

        primaryStage.setTitle("3D Memo");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Will need to use a filepicker or some shit
    @FXML
    protected void onOpenProjectClick() throws IOException {}

    @FXML
    protected void onNewProjectClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("oogabooga");
        File file = fileChooser.showOpenDialog(primaryStage);
        System.out.println(file.getAbsolutePath());
        attachNewModelToPrimaryStage(file.getAbsolutePath());
    }

    //swap current screen with note scene using primarystage
    @FXML
    protected void onNotesSreenClick() throws IOException {}

    //swap current screen with colors scene using primarystage
    @FXML
    protected void onThemeScreenClick() {}

    @FXML
    protected void onAboutScreenClick() {}

    @FXML
    protected void onControlsScreenClick() {}

    @FXML
    protected void onQuitClick() {
        System.exit(0);
    }
}
