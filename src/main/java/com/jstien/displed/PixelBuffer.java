package com.jstien.displed;

import java.awt.*;

public class PixelBuffer {
    private int width;
    private int height;

    final int[] buffer;

    public PixelBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        buffer = new int[width * height];
    }

    public void clear() {
        for (int i=0; i<buffer.length; i++) {
            buffer[i] = 0;
        }
    }


    public int[] getBuffer() {
        return buffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setPixel(int x, int y, Color color) {
        setPixel(x, y, color.getRGB());
    }

    public void setPixel(int x, int y, int rgb) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Pixel index out of range");
        buffer[y*width + x] = rgb;
    }


    public int getRGB(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Pixel index out of range");
        return buffer[y*width + x];
    }

    public Color getColor(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Pixel index out of range");
        return new Color(buffer[y*width + x]);
    }

}
