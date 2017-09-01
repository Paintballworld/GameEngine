package com.itechtopus.tanks.visualisation.impl.j_frame;

import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.model.ModelType;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 100;
    private Timer timer;
    Field field;
    List<MovingModel> models = new ArrayList<>();
    private final int BLOCK_SIZE;

    public Surface(int blockSize, Field field, List<MovingModel> models) {
        this.field = field;
        this.models = models;
        BLOCK_SIZE = blockSize;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        drawCells(g2d);

        for (MovingModel model : models) {
            PositionReal p = (PositionReal) model.getPosition();
            g2d.setPaint(Color.RED);
            g2d.fill(new Rectangle2D.Double(
                    p.getRealMinX() * BLOCK_SIZE,
                    p.getRealMinY() * BLOCK_SIZE,
                    (p.getRealMaxX() - p.getRealMinX()) * BLOCK_SIZE,
                    (p.getRealMaxY() - p.getRealMinY()) * BLOCK_SIZE));
        }


        /*Random r = new Random();

        for (int i = 0; i < 2000; i++) {

            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
            g2d.drawLine(x, y, x, y);
        }*/
    }

    private void drawCells(Graphics2D g2d) {
        for (int y = 0; y < field.getHeight(); y++) {
            g2d.drawLine(0, y * BLOCK_SIZE, field.getWidth() * BLOCK_SIZE, y * BLOCK_SIZE);
        }
        for (int x = 0; x < field.getWidth(); x++) {
            g2d.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, field.getHeight() * BLOCK_SIZE);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

