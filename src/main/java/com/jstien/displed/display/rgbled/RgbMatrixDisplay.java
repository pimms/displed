package com.jstien.displed.display.rgbled;

import com.jstien.displed.display.Configuration;
import com.jstien.displed.display.IDisplay;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.types.u_int8_t;

import java.awt.*;

public class RgbMatrixDisplay implements IDisplay {
    public static interface NativeInterface {
        Pointer led_matrix_create_single(int rows, int cols, String gpio_map);
        Pointer led_matrix_create_offscreen_canvas(Pointer matrix);
        Pointer led_matrix_swap_on_vsync(Pointer matrix, Pointer canvas);
        void led_canvas_set_pixel(Pointer canvas, int x, int y, @u_int8_t char r, @u_int8_t char g, @u_int8_t char b);
        void led_matrix_delete(Pointer matrix);
    }

    private int width;
    private int height;

    private NativeInterface nativeInterface;
    private Pointer matrix;
    private Pointer canvas;

    public class NativeRgbException extends RuntimeException {
        public NativeRgbException(String msg) {
            super(msg);
        }
    }

    public RgbMatrixDisplay(Configuration config) {
        nativeInterface = LibraryLoader.create(NativeInterface.class).load("rgbmatrix");

        width = config.getWidth();
        height = config.getHeight();
        String gpioMapping = config.getGpioMapping();
        matrix = nativeInterface.led_matrix_create_single(height, width, gpioMapping);
        if (isNull(matrix)) {
            throw new NativeRgbException("Failed to initialize native RGB Matrix");
        }

        canvas = nativeInterface.led_matrix_create_offscreen_canvas(matrix);
        if (isNull(canvas)) {
            throw new NativeRgbException("Failed to create a canvas");
        }
    }


    @Override
    public void clear() {
        // The native implementations in rgb-led-matrix seems to be
        // column-major, so iterate through the pixels in the same order.
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                setPixel(x, y, Color.black);
            }
        }
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        char r = (char)color.getRed();
        char g = (char)color.getGreen();
        char b = (char)color.getBlue();

        nativeInterface.led_canvas_set_pixel(canvas, x, y, r, g, b);
    }

    @Override
    public void visualizeFrame() {
        swapBuffer();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void close() {
        if (!isNull(matrix)) {
            nativeInterface.led_matrix_delete(matrix);
            matrix = null;
            canvas = null;
        }
    }


    private void swapBuffer() {
        canvas = nativeInterface.led_matrix_swap_on_vsync(matrix, canvas);
        if (isNull(canvas)) {
            throw new NativeRgbException("Canvas swap returned null");
        }
    }

    private boolean isNull(Pointer p) {
        if (p == null) {
            return true;
        }

        int value = p.getInt(0);
        return value == 0;
    }
}
