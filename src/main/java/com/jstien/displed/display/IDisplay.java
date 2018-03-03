package com.jstien.displed.display;

import java.awt.*;

public interface IDisplay extends AutoCloseable {
    void clear();
    void setPixel(int x, int y, Color color);
    void swapBuffers();
    int getWidth();
    int getHeight();
}
