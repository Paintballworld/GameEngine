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
import com.itechtopus.tanks.visualisation.impl.JFrameDrawer;

public class Main {

  public static void main(String[] args) {
    Tanker player1 = new MyTanker();
    Tanker player2 = new MyTanker();

    Field field = new FieldReal(52, 52, player1);
    GameEngine engine = new MainGameEngine(field);

    Tank aTank = new RealTank(new PositionReal(ModelType.TANK, 5, 5, Direction.DOWN), 100, engine);
    Tank bTank = new RealTank(new PositionReal(ModelType.TANK, 12, 12, Direction.UP), 100, engine);

    player1.setMyTank(aTank);
    player2.setMyTank(bTank);

    player1.setGameEngine(engine);
    player2.setGameEngine(engine);

    aTank.go();
    bTank.go();

    CanvasDrawer drawer = new JFrameDrawer(10, field, aTank, bTank);

    drawer.draw();

    Thread player1Routine = new Thread(player1);
    Thread player2Routine = new Thread(player2);

    player1Routine.start();
    player2Routine.start();

    new Thread(() -> {
      while (true) {
        System.out.println("Memory:" + Runtime.getRuntime().maxMemory());
        System.out.println("Free memory:" + Runtime.getRuntime().freeMemory());
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          System.out.println("Time to exit");
          break;
        }
      }
    }).start();

  }
}
