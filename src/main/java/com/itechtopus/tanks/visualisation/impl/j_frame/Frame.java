package com.itechtopus.tanks.visualisation.impl.j_frame;

import com.itechtopus.tanks.implementations.FieldReal;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    private static final int BLOCK_SIZE = 25;
    Panel panel = new Panel();

    public Frame(FieldReal field) {

        this.setVisible(true);
        this.setSize(field.getWidth() * BLOCK_SIZE, field.getHeight() * BLOCK_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(panel);

        Graphics2D graph = null;

    }
}
