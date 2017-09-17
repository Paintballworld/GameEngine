package com.itechtopus.tanks.visualisation.impl.j_frame;

import com.itechtopus.tanks.factory.ImageFactory;
import com.itechtopus.tanks.implementations.PositionReal;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.MovingModel;
import com.itechtopus.tanks.model.ModelType;
import com.itechtopus.tanks.model.RealMovingModel;
import com.itechtopus.tanks.util.ImageUploadingException;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static com.itechtopus.tanks.staticSettings.Settings.BLOCK_SIZE;
import static com.itechtopus.tanks.staticSettings.Settings.DRAWER_SLEEP_TIME;

class Surface extends JPanel implements ActionListener {

  private Timer timer;
  private Field field;
  private List<RealMovingModel> models = new ArrayList<>();

  public Surface(Field field, List<MovingModel> models) {
    this.field = field;
    initTexturesCache(models);
    initTimer();
  }

  private void initTexturesCache(List<MovingModel> models) {
    int texturesLoaded = 0;
    for (MovingModel model : models) {
      try {
        BufferedImage newBufImage = ImageFactory.getImage(ModelType.TANK);
        this.models.add(new RealMovingModel(model, newBufImage));
        texturesLoaded++;
      } catch (ImageUploadingException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
    System.out.println(texturesLoaded + " textures loaded successfully");
  }

  private void initTimer() {
    timer = new Timer(DRAWER_SLEEP_TIME, this);
    timer.start();
  }

  public Timer getTimer() {
    return timer;
  }

  private void doDrawing(Graphics g) {

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(Color.BLACK);
    g2d.fillRect(0, 0, field.getWidth() * BLOCK_SIZE, field.getHeight() * BLOCK_SIZE);


    int modelsDrawn = 0;

    for (RealMovingModel realModel : models) {

      BufferedImage bufTank = realModel.getImage();
      MovingModel model = realModel.getMovingModel();

      PositionReal p = (PositionReal) model.getPosition();

      double rotationRequired = Math.toRadians(((model.getDirection().ordinal() - 1) % 4) * 90);
      AffineTransform transform = new AffineTransform();
      transform.rotate(rotationRequired, bufTank.getWidth(this) / 2, bufTank.getHeight(this) / 2);
      AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
      Image image = op.filter(bufTank, null);

      g2d.drawImage(image, (int) (p.getRealMinX() * BLOCK_SIZE),
        (int) (p.getRealMinY() * BLOCK_SIZE),
        (int) ((p.getRealMaxX() - p.getRealMinX()) * BLOCK_SIZE),
        (int) ((p.getRealMaxY() - p.getRealMinY()) * BLOCK_SIZE), null);

      modelsDrawn++;
    }

    System.out.println(modelsDrawn + " models drawn");
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

