package com.jstien.displed.display.simulator;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Window {
    private static final int SCALE = 10;

    private JFrame frame;
    private SimulatorCanvas canvas;
    private BufferedImage image;


    public Window(BufferedImage image) {
        this.image = image;

        final int width = image.getWidth() * SCALE;
        final int height = image.getHeight() * SCALE;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        canvas = new SimulatorCanvas(this.image, SCALE);
        canvas.setSize(width, height);
        frame.getContentPane().add(canvas);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void repaint() {
        canvas.repaint();
    }
}
