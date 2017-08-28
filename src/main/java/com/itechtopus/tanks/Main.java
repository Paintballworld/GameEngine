package com.itechtopus.tanks;


import com.itechtopus.tanks.example.MyTanker;
import com.itechtopus.tanks.implementations.FieldReal;
import com.itechtopus.tanks.implementations.MainGameEngine;
import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.implementations.RealTank;
import com.itechtopus.tanks.interfaces.GameEngine;
import com.itechtopus.tanks.interfaces.Tank;
import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.Direction;
import com.itechtopus.tanks.model.ModelType;
import com.itechtopus.tanks.visualisation.CanvasDrawer;
import com.itechtopus.tanks.visualisation.impl.SoutCanvasDrawer;

public class Main {

    private static final long DRAWER_SLEEP_TIME = 100L;
    private Tanker tanker;

    public static void main(String[] args) {
        Tanker player1 = new MyTanker();

        Field field = new FieldReal(52, 52, player1);
        GameEngine engine = new MainGameEngine(field);
        Tank aTank = new RealTank(new PositionReal(ModelType.TANK, 0, 0, Direction.DOWN), 100, engine);

        player1.setMyTank(aTank);
        player1.setGameEngine(engine);

        CanvasDrawer drawer = new SoutCanvasDrawer(field, aTank);

        Thread player1Routine = new Thread(player1);
        player1Routine.start();

        new Thread(() -> {
           while (true) {
               drawer.draw();
               try {
                   Thread.sleep(DRAWER_SLEEP_TIME);
               } catch (InterruptedException e) {
                   System.out.println("Time to exit");
                   break;
               }
           }
        }).start();

    }
}
