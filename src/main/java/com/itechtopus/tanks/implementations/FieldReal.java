package com.itechtopus.tanks.implementations;


import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.BlockType;
import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.interfaces.models.Point;
import com.itechtopus.tanks.interfaces.models.Position;

public class FieldReal implements Field {

    private final int width;
    private final int height;
    private final Tanker player;

    public FieldReal(int width, int height, Tanker player) {
        this.width = width;
        this.height = height;
        this.player = player;
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
    public BlockType getBlockAt(int x, int y) {
        return null;
    }

    @Override
    public BlockType getBlockAt(Point point) {
        return null;
    }

    @Override
    public BlockType[] getBlocksAhead() {
        return new BlockType[0];
    }

    @Override
    public BlockType[][] getAllBlocks() {
        return new BlockType[0][0];
    }

    @Override
    public Position getFlagPosition(int i) {
        return null;
    }

    @Override
    public Position getFarthestPosition() {
        return null;
    }

    @Override
    public Position getFarthestPosition(Direction direction) {
        return null;
    }

    @Override
    public boolean deadEnd() {
        return false;
    }
}
