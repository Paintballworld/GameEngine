package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.interfaces.models.Position;

public class PositionReal implements Position {

    private static final float POSITION_THRESHOLD = 0.01f;

    private float x;
    private float y;
    private Direction direction;

    public PositionReal(float x, float y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public PositionReal getAhead() {
        return new PositionReal(x + direction.xDelta(), y + direction.yDelta(), direction);
    }

    public void step(float stepValue) {
        this.x += direction.xDelta() * stepValue;
        this.y += direction.yDelta() * stepValue;
    }

    public PositionReal getFor(Direction direction) {
        PositionReal newPos = new PositionReal(x, y, direction);
        return newPos.getAhead();
    }

    @Override
    public Position getPositionAhead() {
        return getAhead();
    }

    @Override
    public boolean isInPosition() {
        return x >= Math.round(x) - POSITION_THRESHOLD &&
                x <= Math.round(x) + POSITION_THRESHOLD &&
                y >= Math.round(y) - POSITION_THRESHOLD &&
                y <= Math.round(y) + POSITION_THRESHOLD;
    }

    @Override
    public Position getPositionFor(Direction direction) {
        return getFor(direction);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return '(' + x +
                ", " + y +
                ')';
    }

}
