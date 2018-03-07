package com.jstien.displed.display;

import java.awt.*;

public interface ICanvas {
    void setPixel(int x, int y, Color color);
    void setPixel(int x, int y, int r, int g, int b);
    int getWidth();
    int getHeight();
    void clear();
}
