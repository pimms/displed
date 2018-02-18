package com.jstien.displed;

import com.jstien.displed.particle.ParticleSystem;
import com.jstien.displed.simulator.Window;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        PixelBuffer pixelBuffer = new PixelBuffer(64, 32);

        ParticleSystem particleSystem = new ParticleSystem(64, 32, 5000);

        Window window = new Window(pixelBuffer);

        particleSystem.temp_reorganize();

        while (true) {
            particleSystem.update();
            particleSystem.render(pixelBuffer);
            window.repaint();
            try { Thread.sleep(32); } catch (Exception e) {}
        }
    }
}
