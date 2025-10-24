package edu.utsa.cs3443.FX3D;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import com.interactivemesh.jfx.importer.col.ColModelImporter;
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
                return load3dsMesh(fileName);
            case "col":
                return loadColMesh(fileName);
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

    // TODO: NEEDS TO GET DONE
    // DOES NOT WORK ATM
    private static MeshView[] loadFxmlMesh(String fileName) {
        File file = new File(fileName);
        TdsModelImporter importer = new TdsModelImporter();
        importer.read(file);
        MeshView[] meshView = (MeshView[]) importer.getImport();
        return meshView;
    }

    // TODO: NEEDS TO GET DONE
    // DOES NOT WORK ATM
    private static MeshView[] load3dsMesh(String fileName) {
        File file = new File(fileName);
        TdsModelImporter importer = new TdsModelImporter();
        importer.read(file);
        MeshView[] meshView = (MeshView[]) importer.getImport();
        return meshView;
    }

    private static MeshView[] loadColMesh(String fileName) {
        File file = new File(fileName);
        ColModelImporter importer = new ColModelImporter();
        importer.read(file);
        MeshView[] meshView = (MeshView[]) importer.getImport();
        return meshView;
    }
}
