package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.interfaces.models.Point;
import com.itechtopus.tanks.interfaces.models.Position;
import com.itechtopus.tanks.model.ModelType;

public class PositionReal implements Position {

    private static final float POSITION_THRESHOLD = 0.01f;

    private final ModelType modelType;

    private float x;
    private float y;
    private Direction direction;

    public PositionReal(ModelType modelType, int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.modelType = modelType;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public PositionReal getAhead() {
        return new PositionReal(modelType, (int)(x + direction.xDelta()), (int)(y + direction.yDelta()), direction);
    }

    public void step(float stepValue) {
        this.x += direction.xDelta() * stepValue;
        this.y += direction.yDelta() * stepValue;
    }

    public PositionReal getFor(Direction direction) {
        PositionReal newPos = new PositionReal(modelType, (int)x, (int)y, direction);
        return newPos.getAhead();
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getX() {
        return (int)x;
    }

    @Override
    public int getY() {
        return (int)y;
    }

    @Override
    public int getMinX() {
        return (int)(x - modelType.getSixeX() / 2 - 1);
    }

    @Override
    public int getMinY() {
        return (int) (y - modelType.getSizeY() / 2 - 1);
    }

    @Override
    public int getMaxX() {
        return (int) (x + modelType.getSixeX() / 2);
    }

    @Override
    public int getMaxY() {
        return (int) (x + modelType.getSizeY() / 2);
    }

    @Override
    public Point getMiddlePoint() {
        return new Point((int)x, (int)y);
    }

    @Override
    public Point next() {
        return new Point((int)(x + direction.xDelta()), (int)(y + direction.yDelta()));
    }

    @Override
    public String toString() {
        return '(' + x +
                ", " + y +
                ')';
    }

    public boolean positionIsExact() {
        return Math.abs((Math.round(x) - x)) <= POSITION_THRESHOLD &&
                Math.abs((Math.round(y) - y)) <= POSITION_THRESHOLD;
    }

    public static boolean containsPoint(Position position, Point point) {
        return position.getMinX() >= point.getX() &&
                position.getMaxX() < point.getX() &&
                position.getMinY() >= point.getY() &&
                position.getMaxY() < point.getY();

    }
}
