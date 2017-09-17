package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.Tank;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.interfaces.models.Position;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RealTank implements Tank {

  private static final int RELOAD_TIMEOUT = 1_000;//TODO Переделать чтобы не стрелял пока снаряд не долетит
  private static final int TIMEOUT_PER_STEP = 15;
  private static final float DEFAULT_TANK_SPEED = 0.025f;

  private AtomicInteger stepCounter = new AtomicInteger(0);
  private AtomicInteger fireCounter = new AtomicInteger(0);
  private AtomicBoolean isMoving = new AtomicBoolean(false);

  private Queue<Direction> directionQueue = new LinkedList<>();

  private Runnable fireCounterJob = () -> {
    count(fireCounter);
  };
  private float tankSpeed = DEFAULT_TANK_SPEED;
  private PositionReal position;
  private int health;
  private Field field;
  private Runnable stepCounterJob = () -> {
    while (isMoving.get()) {
      count(stepCounter);
      step();
      stepCounter.set(TIMEOUT_PER_STEP);
    }
  };
  private GameEngine gameEngine;
  public RealTank(PositionReal position, int health, GameEngine gameEngine) {
    this.position = position;
    this.health = health;
    this.field = gameEngine.getField();
    this.gameEngine = gameEngine;
  }

  private void count(AtomicInteger aCounter) {
    while (true) {
      try {
        Thread.sleep(1);
        aCounter.decrementAndGet();
        if (aCounter.get() <= 0)
          break;
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void setTankSpeed(float tankSpeed) {
    this.tankSpeed = tankSpeed;
  }

  private void step() {
    if (ready() && canGo()) {
      if (position.positionIsExact() && !directionQueue.isEmpty()) {
        if (directionQueue.poll() == Direction.LEFT)
          turnLeft();
        else
          turnRight();
      }
      position.step(tankSpeed);
      checkLimits();
    }
  }

  public void fire() {
    if (isRecharged()) {
      System.out.println("Fire!!");
      fireCounter.set(RELOAD_TIMEOUT);
      new Thread(fireCounterJob).start();
    }
  }

  public boolean isRecharged() {
    return fireCounter.get() <= 0;
  }

  @Override
  public void turn(Direction direction) {

  }

  public void turnLeft() {
    if (!ready()) {
      directionQueue.offer(Direction.LEFT);
      return;
    }
    this.position.setDirection(Direction.turnLeftOf(getDirection()));
    System.out.println("Left:" + this.position.getDirection().name());
  }

  public void turnRight() {
    if (!ready()) {
      directionQueue.offer(Direction.RIGHT);
      return;
    }
    this.position.setDirection(Direction.turnRightOf(getDirection()));
    System.out.println("Right:" + this.position.getDirection().name());
  }

  public void reverse() {
    if (!ready()) return;
    this.position.setDirection(Direction.oppositeOf(getDirection()));
  }

  public void go() {
    if (!isMoving.get() && canGo() && checkLimits()) {
      System.out.println("Ahead!");
      isMoving.set(true);
      new Thread(stepCounterJob).start();
    }
  }

  public boolean ready() {
    return stepCounter.get() == 0;
  }

  public void stop() {
    System.out.println("Stop engine!");
    isMoving.set(false);
  }

  public Position getPosition() {
    return position;
  }

  public Direction getDirection() {
    return position.getDirection();
  }

  @Override
  public boolean collidesWith(Position target) {
    return false;
  }

  @Override
  public boolean coolidesWith(MovingModel target) {
    return false;
  }


  public int getHealth() {
    return health;
  }

  public boolean canGo() {
    return Stream.of(field.getBlocksAhead()).filter(Objects::nonNull).count() == 0;
  }

  public boolean isAlive() {
    return health > 0;
  }

  private boolean checkLimits() {
    if (getPosition().getMinX() == 0 ||
      getPosition().getMinY() == 0 ||
      getPosition().getMaxX() == field.getWidth() ||
      getPosition().getMaxY() == field.getHeight()) {
      if (isMoving.get()) {
        turnLeft();
      }
      return false;
    }
    return true;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RealTank realTank = (RealTank) o;

    if (health != realTank.health) return false;
    if (!directionQueue.equals(realTank.directionQueue)) return false;
    return position.equals(realTank.position);

  }

  @Override
  public int hashCode() {
    int result = directionQueue.hashCode();
    result = 31 * result + position.hashCode();
    result = 31 * result + health;
    return result;
  }
}
