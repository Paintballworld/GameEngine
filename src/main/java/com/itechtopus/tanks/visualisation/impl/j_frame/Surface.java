package com.itechtopus.tanks.visualisation.impl.j_frame;

import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.MovingModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 100;
    private Timer timer;
    private Field field;
    private List<MovingModel> models = new ArrayList<>();
    private final int BLOCK_SIZE;
    private Image tank;
    private static final String IMAGE_LOCATION = "com/itechtopus/tanks/visualization/imp/j_frame/images/";
    private static final String TANK_IMAGE_LOCATION = IMAGE_LOCATION + /*"boom.png";//*/"gold_tank.png";
    private BufferedImage bufTank;

    public Surface(int blockSize, Field field, List<MovingModel> models) {
        this.field = field;
        this.models = models;
        BLOCK_SIZE = blockSize;
        initTextures();
        initTimer();
    }

    private void initTextures() {
        URL url = Surface.class.getClassLoader().getResource(TANK_IMAGE_LOCATION);
        tank = new ImageIcon(url).getImage();
        bufTank = new BufferedImage(4 * BLOCK_SIZE, 4 * BLOCK_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D gt = bufTank.createGraphics();
        gt.drawImage(tank, 0, 0, 4 * BLOCK_SIZE, 4 * BLOCK_SIZE, null);
        AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5F);
        gt.setComposite(ac);
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
            g2d.setPaint(Color.BLACK);
            g2d.fillRect(0, 0, field.getWidth() * BLOCK_SIZE, field.getHeight() * BLOCK_SIZE);

            double rotationRequired = Math.toRadians(((model.getDirection().ordinal() - 1) % 4)* 90);
            AffineTransform transform = new AffineTransform();
            transform.rotate(rotationRequired, bufTank.getWidth(this)/2, bufTank.getHeight(this)/2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            tank = op.filter(bufTank, null);

            g2d.drawImage(tank, (int) (p.getRealMinX() * BLOCK_SIZE),
                    (int) (p.getRealMinY() * BLOCK_SIZE),
                    (int) ((p.getRealMaxX() - p.getRealMinX()) * BLOCK_SIZE),
                    (int) ((p.getRealMaxY() - p.getRealMinY()) * BLOCK_SIZE), null);
        }
    }

    private void drawCells(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
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

