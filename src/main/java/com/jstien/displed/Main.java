package com.jstien.displed;

import com.jstien.displed.display.Configuration;
import com.jstien.displed.display.IDisplay;
import com.jstien.displed.display.rgbled.RgbMatrixDisplay;
import com.jstien.displed.display.simulator.SimulatorDisplay;
import com.sun.deploy.Environment;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration(64, 32, "adafruit-hat");
        IDisplay display = createDisplay(config);

        try {
            runApplication(display);
        } catch (Exception ex) {
            System.out.println("Application raised unexpected exception: " + ex);
        } finally {
            onApplicationExit(display);
        }
    }

    private static IDisplay createDisplay(Configuration config) {
        IDisplay display = null;

        if (Environment.getenv("SIMULATOR").equals("1")) {
            display = new SimulatorDisplay(config);
        } else {
            display = new RgbMatrixDisplay(config);
        }

        return display;
    }

    private static void runApplication(IDisplay display) throws Exception {
        try {
            final int width = display.getWidth();
            final int height = display.getHeight();

            for (int x=0; x<width*3000; x++) {
                int prevX = (x % width) - 1;
                if (prevX < 0)
                    prevX += width;

                for (int y = 0; y < height; y++) {
                    display.setPixel(prevX, y, Color.black);
                    display.setPixel((x + 0) % width, y, Color.red);
                    display.setPixel((x + 1) % width, y, Color.white);
                    display.setPixel((x + 2) % width, y, Color.blue);
                    display.setPixel((x + 3) % width, y, Color.white);
                    display.setPixel((x + 4) % width, y, Color.red);
                }
                display.swapBuffers();
            }
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.toString());
        }
    }

    private static void onApplicationExit(IDisplay display) {
        try {
            display.close();
        } catch (Exception e) {
            System.out.println("Closing display failed: " + e);
        }
    }

}
