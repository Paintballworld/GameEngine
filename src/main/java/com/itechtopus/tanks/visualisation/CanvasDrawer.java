package com.itechtopus.tanks.visualisation;

import com.itechtopus.tanks.interfaces.models.MovingModel;

import java.util.ArrayList;
import java.util.List;


public interface CanvasDrawer {

    List<MovingModel> models = new ArrayList<>();

    default void addModel(MovingModel model) {
        models.add(model);
    }

    default void removeModel(MovingModel model) {
        models.remove(model);
    }

    public void draw();
}
