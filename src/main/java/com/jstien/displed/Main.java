package com.jstien.displed;

import com.jstien.displed.particle.ParticleSystem;
import com.jstien.displed.rgbled.RgbMatrix;
import com.jstien.displed.simulator.Window;
import sun.awt.Mutex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        /*
        BufferedImage image = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);

        ParticleSystem particleSystem = new ParticleSystem(64, 32, 5000);

        Window window = new Window(image);

        try {
            URL url = new URL("http://www.pngmart.com/files/3/Play-Button-Transparent-Background.png");
            BufferedImage target = ImageIO.read(url);
            particleSystem.adjustTargetTo(target);
        } catch (Exception ex) {
            System.out.println("Failed to get URL: " + ex);
        }

        */

        Mutex mutex = new Mutex();
        RgbMatrix matrix = new RgbMatrix();
        Runtime.getRuntime().addShutdownHook(new Thread( ()-> {
            mutex.lock();
            matrix.close();
            mutex.unlock();
        }));

        try {
            mutex.lock();
            for (int x=0; x<matrix.getWidth()*3; x++) {
                for (int y = 0; y < matrix.getHeight(); y++) {
                    matrix.setPixel((x + 0) % matrix.getWidth(), y, Color.red);
                    matrix.setPixel((x + 1) % matrix.getWidth(), y, Color.white);
                    matrix.setPixel((x + 2) % matrix.getWidth(), y, Color.blue);
                    matrix.setPixel((x + 3) % matrix.getWidth(), y, Color.white);
                    matrix.setPixel((x + 4) % matrix.getWidth(), y, Color.red);
                    matrix.setPixel((x + 5) % matrix.getWidth(), y, Color.black);
                }
                matrix.swapBuffer();
            }
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.toString());
        } finally {
            matrix.close();
            mutex.unlock();
        }
    }

}
