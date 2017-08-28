package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.TankInfo;
import com.itechtopus.tanks.interfaces.field.Field;

import java.util.List;

public class MainGameEngine implements GameEngine{

    private final Field field;

    public MainGameEngine(Field field) {
        this.field = field;
    }

    @Override
    public List<TankInfo> getEnemyInfo() {
        return null;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public int getMyLifesLeft() {
        return 0;
    }

    @Override
    public int getEnemyLifesLeft() {
        return 0;
    }
}
