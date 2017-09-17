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

  public static boolean containsPoint(Position position, Point point) {
    return point.getX() >= position.getMinX() &&
      point.getX() < position.getMaxX() &&
      point.getY() >= position.getMinY() &&
      point.getY() < position.getMaxY();

  }

  public static void main(String[] args) throws InterruptedException {
    PositionReal p = new PositionReal(ModelType.TANK, 5, 5, Direction.RIGHT);
    System.out.println(p.getDirection().xDelta() + ":" + p.getDirection().yDelta() + ": must be +1:0");
    System.out.println(p.getAhead().getMiddlePoint() + ": must be (6, 5)");
    RealTank tank = new RealTank(p, 100, new MainGameEngine(new FieldReal(40, 10, null)));
    System.out.println(tank.getPosition().getMiddlePoint() + ": must be (5, 5)");
    System.out.println(tank.getPosition().getMinX() + ", " + tank.getPosition().getMinY() + " : must be (3, 3)");
    System.out.println(tank.getPosition().getMiddlePoint() + ": must be (5, 5)");
    System.out.println(tank.getPosition().getMaxX() + ", " + tank.getPosition().getMaxY() + " : must be (7, 7)");
    tank.go();
    Thread.sleep(3000);
    System.out.println(tank.getPosition().getMinX() + ", " + tank.getPosition().getMinY() + " : (4, 3)");
    System.out.println(tank.getPosition().getMiddlePoint() + ": must be (6, 5)");
    System.out.println(tank.getPosition().getMaxX() + ", " + tank.getPosition().getMaxY() + " : (8, 7)");


  }

  public PositionReal getAhead() {
    return new PositionReal(modelType, (int) (x + direction.xDelta()), (int) (y + direction.yDelta()), direction);
  }

  public void step(float stepValue) {
    this.x += direction.xDelta() * stepValue;
    this.y += direction.yDelta() * stepValue;
  }

  public PositionReal getFor(Direction direction) {
    PositionReal newPos = new PositionReal(modelType, (int) x, (int) y, direction);
    return newPos.getAhead();
  }

  @Override
  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction newDirection) {
    this.direction = newDirection;
  }

  @Override
  public int getX() {
    return (int) x;
  }

  @Override
  public int getY() {
    return (int) y;
  }

  @Override
  public int getMinX() {
    return (int) (x - modelType.getSizeX() / 2);
  }

  public float getRealMinX() {
    return x - modelType.getSizeX() / 2;
  }

  @Override
  public int getMinY() {
    return (int) (y - modelType.getSizeY() / 2);
  }

  public float getRealMinY() {
    return y - modelType.getSizeY() / 2;
  }

  @Override
  public int getMaxX() {
    return (int) (x + modelType.getSizeX() / 2);
  }

  public float getRealMaxX() {
    return x + modelType.getSizeX() / 2;
  }

  @Override
  public int getMaxY() {
    return (int) (y + modelType.getSizeY() / 2);
  }

  public float getRealMaxY() {
    return y + modelType.getSizeY() / 2;
  }

  @Override
  public Point getMiddlePoint() {
    return new Point((int) x, (int) y);
  }

  @Override
  public Point next() {
    return new Point((int) (x + direction.xDelta()), (int) (y + direction.yDelta()));
  }

  @Override
  public String toString() {
    return "(" + x +
      ", " + y +
      ')';
  }

  public boolean positionIsExact() {
    return Math.abs((Math.round(x) - x)) <= POSITION_THRESHOLD &&
      Math.abs((Math.round(y) - y)) <= POSITION_THRESHOLD;
  }
}
