package com.jstien.displed;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ByteMap {
    private List<Pixel> pixels;
    private int width;
    private int height;

    private class Pixel {
        int r;
        int g;
        int b;
    }

    public ByteMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new ArrayList<>();
        for (int i=0; i<width*height; i++) {
            this.pixels.add(new Pixel());
        }
    }

    public void addColor(int x, int y, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        addColor(x, y, r, g, b);
    }

    public void addColor(int x, int y, int r, int g, int b) {
        getPixel(x, y).ifPresent(pixel -> {
            pixel.r = Math.min(pixel.r + r, 255);
            pixel.g = Math.min(pixel.g + g, 255);
            pixel.b = Math.min(pixel.b + b, 255);
        });
    }

    public Color getColor(int x, int y) {
        Pixel p = pixels.get(getIndex(x, y));
        return new Color(p.r, p.g, p.b);
    }

    private Optional<Pixel> getPixel(int x, int y) {
        int index = getIndex(x, y);
        if (index == -1)
            return Optional.empty();
        return Optional.of(pixels.get(index));
    }

    private int getIndex(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y>= height)
            return -1;
        return x + (y * width);
    }
}
