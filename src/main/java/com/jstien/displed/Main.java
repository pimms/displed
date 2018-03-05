package com.jstien.displed;

import com.jstien.displed.display.Configuration;
import com.jstien.displed.display.IDisplay;
import com.jstien.displed.display.TextRenderer;
import com.jstien.displed.display.rgbled.RgbMatrixDisplay;
import com.jstien.displed.display.simulator.SimulatorDisplay;
import com.jstien.displed.font.BDFFont;
import com.jstien.displed.particle.ParticleSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static boolean exiting = false;

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
        appThread.setName("appThread");
        appThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.debug("ShutdownHook - begin");
            LOG.debug("ShutdownHook - stopping appThread...");
            try {
                exiting = true;
                if (appThread.isAlive())
                    appThread.join();
                LOG.debug("ShutdownHook - appThread joined");
                onApplicationExit(display);
            } catch (Exception ex) {
                LOG.error("ShutdownHook - thread join exception", ex);
            }

            LOG.debug("ShutdownHook - completed");
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
            ParticleSystem system = new ParticleSystem(display);

            BinaryMap map = new BinaryMap(64, 32);
            for (int y=0; y<32; y++)
                for (int x=0; x<64; x++)
                    map.set(x, y, ((x/8)%2) != ((y/8)%2));

            system.transitionTo(map);

            BDFFont font = new BDFFont("5x7.bdf");
            TextRenderer textRenderer = new TextRenderer(display, font);
            textRenderer.setTextColor(Color.red);

            int n = 0;
            while (!exiting) {
                system.update();
                system.render();
                textRenderer.drawText((n % (display.getWidth()*2))-display.getWidth(), 0, "Hello, World!");
                display.swapBuffers();
                n++;
            }
        } catch (Exception ex) {
            LOG.error("Exception caught during application run", ex);
            display.close();
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
