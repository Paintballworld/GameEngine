package com.itechtopus.tanks.visualisation.impl;

import com.itechtopus.tanks.implementations.FieldReal;
import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.BlockType;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.interfaces.models.Point;
import com.itechtopus.tanks.visualisation.CanvasDrawer;

import java.util.Arrays;

public class SoutCanvasDrawer implements CanvasDrawer {

    private Field field;

    public SoutCanvasDrawer(Field field, MovingModel... movingModels) {
        this.field = field;
        models.addAll(Arrays.asList(movingModels));
    }

    @Override
    public void draw() {
        for (int y = 0; y < field.getWidth(); y++) {
            for (int x = 0; x < field.getHeight(); x++) {
                String ch = " ";
                if (field.getBlockAt(x, y) == BlockType.BRICK) ch = "B";
                if (field.getBlockAt(x, y) == BlockType.STONE) ch = "S";
                for (MovingModel model : models) {
                    if (PositionReal.containsPoint(model.getPosition(), new Point(x, y)))
                        ch = "O";
                }
                System.out.print(ch);
            }
            System.out.println("|\n|");
        }

        System.out.println();
        System.out.println();
        System.out.println();

    }
}
