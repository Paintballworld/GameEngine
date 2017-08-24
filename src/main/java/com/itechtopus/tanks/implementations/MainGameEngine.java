package com.itechtopus.tanks.implementations;

import com.itechtopus.tanks.interfaces.Field;
import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.TankInfo;

import java.util.List;

public class MainGameEngine implements GameEngine{

    private final Field field;

    public MainGameEngine(Field field) {
        this.field = field;
    }

    @Override
    public List<TankInfo> getAllTankInfo() {
        return null;
    }

    @Override
    public List<Integer> getEnemyIdList() {
        return null;
    }

    @Override
    public Field getField() {
        return field;
    }
}
