package com.jstien.displed.simulator;

import com.jstien.displed.PixelBuffer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window {
    private static final int SCALE = 10;

    private JFrame frame;
    private SimulatorCanvas canvas;
    private PixelBuffer pixelBuffer;


    public Window(PixelBuffer pixelBuffer) {
        this.pixelBuffer = pixelBuffer;

        final int width = pixelBuffer.getWidth() * SCALE;
        final int height = pixelBuffer.getHeight() * SCALE;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        canvas = new SimulatorCanvas(this.pixelBuffer, SCALE);
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
