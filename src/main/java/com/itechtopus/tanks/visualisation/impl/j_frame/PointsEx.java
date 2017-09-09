package com.itechtopus.tanks.visualisation.impl.j_frame;

import com.itechtopus.tanks.implementations.FieldReal;
import com.itechtopus.tanks.interfaces.field.Field;
import com.itechtopus.tanks.interfaces.models.MovingModel;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import static com.itechtopus.tanks.staticSettings.Settings.BLOCK_SIZE;

public class PointsEx extends JFrame {

    Field field;
    List<MovingModel> models = new ArrayList<>();

    public PointsEx(Field field, List<MovingModel> models) {
        this.field = field;
        this.models = models;
        initUI();
    }

    private void initUI() {

        final Surface surface = new Surface(field, models);
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Points");
        setSize(field.getWidth() * BLOCK_SIZE, field.getHeight() * BLOCK_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                PointsEx ex = new PointsEx(null, null);
                ex.setVisible(true);
            }
        });
    }
}