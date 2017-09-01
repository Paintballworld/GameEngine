package com.itechtopus.tanks.tank;

import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.Tank;
import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.interfaces.models.Direction;

public class MyStupidTanker implements Tanker {

    private Tank myTank;
    private int myId;
    private GameEngine engine;

    @Override
    public void setMyTank(Tank tank) {
        myTank = tank;
    }

    @Override
    public void setMyId(int id) {
        myId = id;
    }

    @Override
    public void setGameEngine(GameEngine gameEngine) {
        engine = gameEngine;
    }

    @Override
    public String getPlayerName() {
        return "Yerlan";
    }

    @Override
    public String getPlayerGroupName() {
        return "greetgo!";
    }

    @Override
    public String getTankName() {
        return "T68";
    }

    @Override
    public void run() {
        myTank.turn(Direction.RIGHT);
        myTank.go();
        while (true) {

        }
    }
}
