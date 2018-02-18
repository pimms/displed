package com.jstien.displed.simulator;

import com.jstien.displed.PixelBuffer;

import java.awt.*;

public class SimulatorCanvas extends Canvas {
    private PixelBuffer pixelBuffer;
    private int pixelScale;

    public SimulatorCanvas(PixelBuffer pixelBuffer, int pixelScale) {
        this.pixelBuffer = pixelBuffer;
        this.pixelScale = pixelScale;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, pixelScale * pixelBuffer.getWidth(), pixelScale * pixelBuffer.getHeight());

        for (int y=0; y<this.pixelBuffer.getHeight(); y++) {
            for (int x=0; x<this.pixelBuffer.getWidth(); x++ ){
                int realX = x * pixelScale;
                int realY = y * pixelScale;

                g.setColor(pixelBuffer.getColor(x, y));
                g.fillRect(realX + 1, realY + 1, pixelScale - 2, pixelScale - 2);
            }
        }
    }
}
