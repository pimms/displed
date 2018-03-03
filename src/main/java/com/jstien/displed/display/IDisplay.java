package com.jstien.displed.display;

import java.awt.*;

public interface IDisplay extends AutoCloseable {
    void setPixel(int x, int y, Color color);
    void setPixel(int x, int y, int r, int g, int b);
    void swapBuffers();
    int getWidth();
    int getHeight();
}
