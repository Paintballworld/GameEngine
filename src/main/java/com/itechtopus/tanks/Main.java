package com.itechtopus.tanks;

import com.itechtopus.tanks.impl.MyTanker;
import com.itechtopus.tanks.implementations.FieldReal;
import com.itechtopus.tanks.implementations.MainGameEngine;
import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.implementations.RealTank;
import com.itechtopus.tanks.interfaces.Field;
import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.Tank;
import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.interfaces.models.Direction;

public class Main {

    private Tanker tanker;

    public static void main(String[] args) {
        Field field = new FieldReal(100, 100);
        GameEngine engine = new MainGameEngine(field);
        Tank aTank = new RealTank(new PositionReal(0, 0, Direction.DOWN), 100, engine);

        Tanker player1 = new MyTanker();
        player1.setMyTank(aTank);

        Thread player1Routine = new Thread(player1);
        player1Routine.start();

    }
}
