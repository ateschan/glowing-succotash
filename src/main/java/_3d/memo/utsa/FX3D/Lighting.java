package _3d.memo.utsa.FX3D;

import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import java.util.ArrayList;

import static _3d.memo.utsa.MainEntry.VIEWPORT_SIZE;

//Holds lights fro builder group
public class Lighting {
    ArrayList<PointLight> pLights;
    AmbientLight aLight;

    private Color lightColor = Color.rgb(244, 255, 250);
    private Color ambientColor = Color.rgb(80, 80, 80, 0);

    public Color getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Color ambientColor)
    {
        this.ambientColor = ambientColor;
    }

    public void setPointLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public Lighting() {
        this.aLight = new AmbientLight();
        this.pLights = new ArrayList<>();

        PointLight pointLight = new PointLight(lightColor);
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

        this.pLights.add(pointLight);
        this.pLights.add(pointLight2);
        this.pLights.add(pointLight3);

        this.aLight = new AmbientLight(ambientColor);
    }
}
