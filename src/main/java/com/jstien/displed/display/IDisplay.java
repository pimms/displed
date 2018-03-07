package com.jstien.displed.display;

public interface IDisplay extends AutoCloseable, ICanvas {
    void swapBuffers();
}
