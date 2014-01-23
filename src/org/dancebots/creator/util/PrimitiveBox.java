package org.dancebots.creator.util;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import org.dancebots.creator.model.MotorPrimitive;

import java.util.UUID;

/**
 * Created by codingcasp on 1/23/14.
 */
public class PrimitiveBox {

    private UUID id;
    private Group container;

    private Rectangle box;
    private DoubleProperty boxHeight;
    private DoubleProperty boxWidth;



    public PrimitiveBox(MotorPrimitive mp) {
        container = new Group();

        box = new Rectangle(boxHeight.doubleValue(), boxWidth.doubleValue());
        Label typeLabel = new Label(mp.getType());



        container.getChildren().addAll(box,typeLabel);

    }

    public DoubleProperty getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(DoubleProperty boxWidth) {
        this.boxWidth = boxWidth;
    }

    public DoubleProperty getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(DoubleProperty boxHeight) {
        this.boxHeight = boxHeight;
    }







}
