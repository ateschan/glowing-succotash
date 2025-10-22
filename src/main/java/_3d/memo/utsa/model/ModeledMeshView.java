package _3d.memo.utsa.model;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;

import java.io.File;

// Holds some static methods to load in MeshViews from the model importer jars
// Parameterized based on filetype loaded
public class ModeledMeshView extends MeshView {

    public static MeshView[] LoadMeshView(String fileName) {
        MeshView[] m = null;

        switch (fileName.toLowerCase().split("\\.")[1]) {
            case "fxml" :
                //fxml importer goes here
                break;
            case "obj" :
                return loadObjMesh(fileName);
            case "stl":
                return loadStlMesh(fileName);
            case "3ds":
                //3ds importer goes here
                break;
            case "col":
                //col importer goes here
                break;
        }
        System.out.println(m);

        return m;
    }


    private static MeshView[] loadObjMesh(String fileName) {
        File file = new File(fileName);
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(file);
        MeshView[] meshView = importer.getImport();
        return meshView;
    }


    private static MeshView[] loadStlMesh(String fileName) {
        File file = new File(fileName);
        StlMeshImporter importer = new StlMeshImporter();
        importer.read(file);
        Mesh mesh = importer.getImport();
        return new MeshView[] { new MeshView(mesh) };
    }
}
