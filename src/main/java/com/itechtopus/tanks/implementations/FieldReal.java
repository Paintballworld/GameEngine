package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.Field;
import com.itechtopus.tanks.interfaces.models.Position;

public class FieldReal implements Field {

    private final int width;
    private final int height;

    public FieldReal(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getPlayerCount() {
        return 0;
    }

    @Override
    public int getCellstrength(Position position) {
        return 0;
    }

    @Override
    public Position getFlagPosition(int i) {
        return null;
    }
}
