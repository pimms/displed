package com.jstien.displed.display;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Optional;

public class DisplayRenderer {
    private Logger LOG = LogManager.getLogger(DisplayRenderer.class);
    private IDisplay display;
    private Color clearColor;

    public DisplayRenderer(IDisplay display) {
        this.display = display;
        this.clearColor = Color.black;
    }

    public void setClearColor(Color color) {
        clearColor = color;
    }

    public void clear() {
        final int width = display.getWidth();
        final int height = display.getHeight();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                display.setPixel(x, y, clearColor);
            }
        }
    }

    public void displayImage(String url) {
        loadImage(url).ifPresent(img -> {
            img = scaleToFit(img);
            final int dispWidth = display.getWidth();
            final int dispHeight = display.getHeight();
            final float dispAr = (float)dispWidth / (float)dispHeight;
            final float imgAr = (float)img.getWidth(null) / (float)img.getHeight(null);

            int offX = 0;
            int offY = 0;
            if (dispAr > imgAr) {
                offX = (dispWidth - img.getWidth(null)) / 2;
            } else {
                offY = (dispHeight - img.getHeight(null)) / 2;
            }

            drawImage(offX, offY, img);
        });
    }

    private Optional<BufferedImage> loadImage(String url) {
        try {
            return Optional.of(ImageIO.read(new URL(url)));
        } catch (Exception ex) {
            LOG.error("Failed to load image '"+url+"'", ex);
            return Optional.empty();
        }
    }

    private void drawImage(int destX, int destY, BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();

        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                display.setPixel(destX+x, destY+y, new Color(image.getRGB(x, y)));
            }
        }
    }

    private BufferedImage scaleToFit(BufferedImage img) {
        final int displayWidth = display.getWidth();
        final int displayHeight = display.getHeight();
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        float scaleFactor = 1f;
        if ((float)displayWidth/(float)displayHeight> (float)imgWidth/(float)imgHeight) {
            // Target images' aspect ratio is WIDER
            scaleFactor = (float)displayHeight/ (float)imgHeight;
        } else {
            // Target images' aspect ratio is TALLER
            scaleFactor = (float)displayWidth/ (float)imgWidth;
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
