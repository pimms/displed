package com.jstien.displed.display.simulator;

import com.jstien.displed.display.Configuration;
import com.jstien.displed.display.IDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

public class SimulatorDisplay implements IDisplay {
    private static final int SCALE = 10;

    private JFrame frame;
    private SimulatorCanvas canvas;
    private BufferedImage image;


    public SimulatorDisplay(Configuration config) {
        createImageBuffer(config);
        createWindow();
    }

    private void createImageBuffer(Configuration config) {
        int width = config.getWidth();
        int height = config.getHeight();
        int imgType = BufferedImage.TYPE_INT_ARGB;
        this.image = new BufferedImage(width, height, imgType);
    }

    private void createWindow() {
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

        frame.addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent e) { frame = null; }
            public void windowOpened(WindowEvent e) { }
            public void windowClosed(WindowEvent e) { }
            public void windowIconified(WindowEvent e) { }
            public void windowDeiconified(WindowEvent e) { }
            public void windowActivated(WindowEvent e) { }
            public void windowDeactivated(WindowEvent e) { }
        });
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        image.setRGB(x, y, color.getRGB());
    }

    @Override
    public void setPixel(int x, int y, int r, int g, int b) {
        setPixel(x, y, new Color(r, g, b));
    }

    @Override
    public void swapBuffers() {
        canvas.repaint();

        // Simulate v-sync. I believe the physical display
        // handles 100Hz.
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {}
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void close() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame.dispose();
            frame = null;
        }
    }
}
