package com.itechtopus.tanks.factory;

import com.itechtopus.tanks.model.ModelType;
import com.itechtopus.tanks.util.ImageUploadingException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import static com.itechtopus.tanks.staticSettings.Settings.BLOCK_SIZE;

/**
 * @author Yerlan Akzhanov
 * @link GitHub : github.com/Paintballworld
 * @link personal : www.itechtopus.com
 * Paintballworld@rambler.ru
 * date: 09.09.17
 * GameEngine
 */
public class ImageFactory {

    private static final String IMAGES_LOCATION = "com/itechtopus/tanks/images/scrap/";
    private static final AtomicInteger tankSkinCounter = new AtomicInteger(0);

    public static BufferedImage getImage(ModelType type) throws ImageUploadingException {
        BufferedImage result;
        Image image ;
        image = loadImage(type);
        result = new BufferedImage(type.getSizeX() * BLOCK_SIZE,
                type.getSizeY() * BLOCK_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D gt = result.createGraphics();
        gt.drawImage(image, 0, 0, type.getSizeX() * BLOCK_SIZE,
                type.getSizeY() * BLOCK_SIZE, null);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5F);
        gt.setComposite(ac);
        return result;
    }

    private static Image loadImage(ModelType type) throws ImageUploadingException {
        String imageFileName = IMAGES_LOCATION + getImageFileName(type);
        URL url = ImageFactory.class.getClassLoader().getResource(imageFileName);
        if (url == null)
            throw new ImageUploadingException("Unable to load image <" +
                    getImageFileName(type) + "> for type <" + type.name() + '>');
        return new ImageIcon(url).getImage();
    }

    private static final String[] tankImages = new String[]{
            "gold_tank.png",
            "green_tank.jpg"};

    private static String getImageFileName(ModelType type) {
        switch (type) {
            case SOLID_BLOCK: return "solid_block2x2.jpg";
            case BRICK_BLOCK: return "brick_wall2x2.jpg";
            case EXPLOSION: return "boom.png";
            case TANK: {
                int skinOrdinal = tankSkinCounter.getAndIncrement();
                if (skinOrdinal == tankImages.length - 1)
                    tankSkinCounter.set(0);
                return tankImages[skinOrdinal];
            }
            default: return "no image file for <" + type.name() + "> type";
        }
    }

    //TODO implement dynamic tank skin loading to give an ability to upload new tank skins in Runtime



}
