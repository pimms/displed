package com.jstien.displed.display.simulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimulatorCanvas extends Canvas {
    private BufferedImage image;
    private int pixelScale;

    public SimulatorCanvas(BufferedImage image, int pixelScale) {
        this.image = image;
        this.pixelScale = pixelScale;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, pixelScale * image.getWidth(), pixelScale * image.getHeight());

        for (int y=0; y<image.getHeight(); y++) {
            for (int x=0; x<image.getWidth(); x++ ){
                int realX = x * pixelScale;
                int realY = y * pixelScale;

                g.setColor(new Color(image.getRGB(x, y)));
                g.fillRect(realX + 1, realY + 1, pixelScale - 2, pixelScale - 2);
            }
        }
    }
}
