package com.jstien.displed.display;

import java.awt.*;

public interface IDisplay {
    void clear();
    void setPixel(int x, int y, Color color);
    void visualizeFrame();
    int getWidth();
    int getHeight();
}
