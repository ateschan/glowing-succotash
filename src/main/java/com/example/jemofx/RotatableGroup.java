package com.example.jemofx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;

class RotatableGroup extends Group {
    //Next three track moused drag and object angle. Will need to be separated into classes.
    //Tracks drag starting point for x and y
    public double anchorX, anchorY;

    //Keep track of current angle for x and y
    public double anchorAngleX = -1;
    public double anchorAngleY = -1;

    //We will update these after drag. Using JavaFX property to bind with object
    public final DoubleProperty angleX = new SimpleDoubleProperty(-1);
    public final DoubleProperty angleY = new SimpleDoubleProperty(-1);

    public RotatableGroup() {}

}