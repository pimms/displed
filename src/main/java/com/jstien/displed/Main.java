package com.jstien.displed;

import com.jstien.displed.display.Configuration;
import com.jstien.displed.display.DisplayRenderer;
import com.jstien.displed.display.IDisplay;
import com.jstien.displed.display.rgbled.RgbMatrixDisplay;
import com.jstien.displed.display.simulator.SimulatorDisplay;

import java.awt.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.awt.Mutex;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Configuration config = new Configuration(64, 32, "adafruit-hat");
        IDisplay display = createDisplay(config);

        Thread appThread = new Thread(() -> {
            try {
                runApplication(display);
            } catch (Exception ex) {
                LOG.error("Application raised unexpected exception", ex);
            }
        });
        appThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.debug("ShutdownHook - begin. Joining appThread...");
            try {
                appThread.stop();
                onApplicationExit(display);
            } catch (Exception ex) {
                LOG.error("ShutdownHook - thread join exception", ex);
            }
        }));
    }

    private static IDisplay createDisplay(Configuration config) {
        IDisplay display = null;

        if (System.getenv("SIMULATOR") == null) {
            LOG.info("Using RgbMatrixDisplay");
            display = new RgbMatrixDisplay(config);
        } else {
            LOG.info("Using SimulatorDisplay");
            display = new SimulatorDisplay(config);
        }

        return display;
    }

    private static void runApplication(IDisplay display) throws Exception {
        try {
            DisplayRenderer renderer = new DisplayRenderer(display);
            renderer.setClearColor(Color.white);
            renderer.clear();
            renderer.displayImage("http://i0.kym-cdn.com/entries/icons/original/000/001/030/DButt.jpg");
            display.swapBuffers();
            Thread.sleep(5000);
        } catch (Exception ex) {
            LOG.error("Exception caught during application run", ex);
        }
    }

    private static void onApplicationExit(IDisplay display) {
        try {
            display.close();
        } catch (Exception e) {
            LOG.error("Closing display failed", e);
        }
    }

}
