package com.itechtopus.tanks.model;

public enum ModelType {

    TANK(4,4), BLOCK(1,1);

    private int sixeX;
    private int sizeY;

    ModelType(int sixeX, int sizeY) {
        this.sixeX = sixeX;
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sixeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
