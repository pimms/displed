package com.jstien.displed.display;

public class Configuration {
    private int width;
    private int height;
    private String gpioMapping;


    public Configuration(int width, int height, String gpioMapping) {
        this.width = width;
        this.height = height;
        this.gpioMapping = gpioMapping;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getGpioMapping() {
        return gpioMapping;
    }
}
