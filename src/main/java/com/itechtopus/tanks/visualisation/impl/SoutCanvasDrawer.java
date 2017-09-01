package com.itechtopus.tanks.visualisation.impl;

import com.itechtopus.tanks.example.MyTanker;
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
        for (int y = -1; y <= field.getHeight(); y++) {
            System.out.print(Math.abs(y) % 10);
            for (int x = 0; x < field.getWidth(); x++) {
                String ch = " ";
                if (y == field.getHeight())
                    ch = "-";
                else if (y == -1)
                    ch = "" + Math.abs(x) % 10;
                else {
                    if (field.getBlockAt(x, y) == BlockType.BRICK) ch = "B";
                    if (field.getBlockAt(x, y) == BlockType.STONE) ch = "S";
                    for (MovingModel model : models) {
                        if (PositionReal.containsPoint(model.getPosition(), new Point(x, y)))
                            ch = "T";
                    }
                }
                System.out.print(ch);
            }
            System.out.println('|');
        }

        System.out.println(models.get(0).getPosition().getMiddlePoint());
        System.out.println(models.get(0).getPosition().getMinX() + ", " + models.get(0).getPosition().getMinY());
        System.out.println(models.get(0).getPosition().getMaxX() + ", " + models.get(0).getPosition().getY());
    }

    public static void main(String[] args) {
        Field field = new FieldReal(40, 40, new MyTanker());
        SoutCanvasDrawer drawer = new SoutCanvasDrawer(field);
        drawer.draw();
    }
}
