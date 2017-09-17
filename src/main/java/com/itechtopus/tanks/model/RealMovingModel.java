package com.itechtopus.tanks.model;

import com.itechtopus.tanks.interfaces.models.MovingModel;

import java.awt.image.BufferedImage;

public class RealMovingModel {

    private MovingModel movingModel;
    private BufferedImage image;

    public RealMovingModel(MovingModel movingModel, BufferedImage image) {
        this.movingModel = movingModel;
        this.image = image;
    }

    public MovingModel getMovingModel() {
        return movingModel;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setMovingModel(MovingModel movingModel) {
        this.movingModel = movingModel;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
