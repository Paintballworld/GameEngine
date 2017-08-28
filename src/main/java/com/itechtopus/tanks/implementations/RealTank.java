package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.Tank;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.interfaces.models.Position;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RealTank implements Tank {

    private static final int RELOAD_TIMEOUT = 1_000;//TODO Переделать чтобы не стрелял пока снаряд не долетит
    private static final int TIMEOUT_PER_STEP = 100;
    private static final float DEFAULT_TANK_SPEED = 0.2f;

    private AtomicInteger stepCounter = new AtomicInteger(0);
    private AtomicInteger fireCounter = new AtomicInteger(0);
    private AtomicBoolean isMoving = new AtomicBoolean(false);

    private Runnable fireCounterJob = () -> {
        while (true) {
            try {
                Thread.sleep(10);
                fireCounter.decrementAndGet();
                if (fireCounter.get() <= 0)
                    break;
            } catch (InterruptedException e) {
                break;
            }
        }
    };

    private Runnable stepCounterJob = () -> {
        while (isMoving.get()) {
            while (true) {
                try {
                    Thread.sleep(10);
                    stepCounter.decrementAndGet();
                    if (stepCounter.get() <= 0)
                        break;
                } catch (InterruptedException e) {
                    break;
                }
            }
            step();
            stepCounter.set(TIMEOUT_PER_STEP);
        }
    };

    private float tankSpeed = DEFAULT_TANK_SPEED;
    private PositionReal position;
    private int health;
    private Field field;
    private GameEngine gameEngine;

    public RealTank(PositionReal position, int health, GameEngine gameEngine) {
        this.position = position;
        this.health = health;
        this.field = gameEngine.getField();
        this.gameEngine = gameEngine;
    }

    public void setTankSpeed(float tankSpeed) {
        this.tankSpeed = tankSpeed;
    }

    private void step() {
        if (ready() && canGo(getDirection())) {
            position.step(tankSpeed);
            if (position.positionIsExact())
                System.out.println("Step " + position);
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
        if (!ready()) return;
        System.out.println("Left");
        this.position.setDirection(Direction.turnLeftOf(getDirection()));
    }

    public void turnRight() {
        if (!ready()) return;
        System.out.println("Right");
        this.position.setDirection(Direction.turnRightOf(getDirection()));
    }

    public void reverse() {
        if (!ready()) return;
        this.position.setDirection(Direction.oppositeOf(getDirection()));
    }

    public void go() {
        if (!isMoving.get()) {
            System.out.println("Ahead!");
            isMoving.set(true);
            new Thread(stepCounterJob).start();
        }
    }

    public boolean ready() {
        return stepCounter.get() <= 0;
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

    public boolean canGo(Direction direction) {
        return Stream.of(field.getBlocksAhead()).filter(Objects::nonNull).count() == 0;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
