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
    private Label typeLabel;


    public PrimitiveBox(MotorPrimitive mp) {
        container = new Stack();

        box = new Rectangle(0,0);
        typeLabel = new Label(mp.getType());



        container.getChildren().addAll(box,typeLabel);

    }

    public Double getBoxWidth() {
        return box.getWidth();
    }

    public void setBoxWidth(Double boxWidth) {
        box.setWidth(boxWidth);
    }

    public Double getBoxHeight() {
        return box.getHeight();
    }

    public void setBoxHeight(Double boxHeight) {
        box.setHeight(boxHeight);    }

    public void setX(Double newX) {
        box.setX(newX);
        typeLabel.setLayoutX(box.getLayoutX()-3);
        typeLabel.setLayoutY((box.getLayoutY()-3));
    }

    public void setY(Double newY) {
        box.setY(newY);
    }

    public Group getContainer() {
        return this.container;
    }




}
