package com.jstien.displed.particle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DistributionPlanner {
    private int width;
    private int height;

    public DistributionPlanner(int maxW, int maxH) {
        width = maxW;
        height = maxH;
    }

    private BufferedImage scaleToFit(BufferedImage img) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        float scaleFactor = 1f;
        if ((float)width/(float)height> (float)imgWidth/(float)imgHeight) {
            // Target images' aspect ratio is WIDER
            scaleFactor = (float)height/ (float)imgHeight;
        } else {
            // Target images' aspect ratio is TALLER
            scaleFactor = (float)width/ (float)imgWidth;
        }

        int newWidth = (int)((float)imgWidth * scaleFactor);
        int newHeight = (int)((float)imgHeight * scaleFactor);

        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
