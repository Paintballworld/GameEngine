package com.itechtopus.tanks.visualisation.impl;

import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.visualisation.CanvasDrawer;
import com.itechtopus.tanks.visualisation.impl.j_frame.PointsEx;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JFrameDrawer implements CanvasDrawer{

    Field field;
    List<MovingModel> models = new ArrayList<>();
    private final int BLOCK_SIZE;

    public JFrameDrawer(int blocksize, Field field, MovingModel... models) {
        this.field = field;
        this.models = Arrays.asList(models);
        this.BLOCK_SIZE = blocksize;
    }

    @Override
    public void draw() {
        EventQueue.invokeLater(() -> {
            PointsEx ex = new PointsEx(field, models);
            ex.setVisible(true);
        });
    }
}

