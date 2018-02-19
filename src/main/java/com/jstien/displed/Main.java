package com.jstien.displed;

import com.jstien.displed.particle.ParticleSystem;
import com.jstien.displed.simulator.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
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

        while (true) {
            particleSystem.update();
            particleSystem.render(image);
            window.repaint();
            try { Thread.sleep(32); } catch (Exception e) {}
        }
    }
}
