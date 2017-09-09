package com.itechtopus.tanks.model;

public enum ModelType {

    TANK(4,4),
    SOLID_BLOCK(1,1),
    BRICK_BLOCK(1, 1),
    EXPLOSION(4, 4),
    ;

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
