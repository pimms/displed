package com.jstien.displed.particle;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParticleSystem {
    private class IntermediatePixel {
        int r;
        int g;
        int b;
        int a;

        public void clear() {
            r = g = b = a = 0;
        }
    }

    private int boundX;
    private int boundY;
    private Particle[] particles;

    private IntermediatePixel[] intermediatePixels;

    public ParticleSystem(int boundX, int boundY, int numParticles) {
        this.boundX = boundX;
        this.boundY = boundY;

        particles = new Particle[numParticles];
        for (int i=0; i<numParticles; i++)
            particles[i] = createParticle();
    }

    private Particle createParticle() {
        float x = (float)Math.random() * boundX;
        float y = (float)Math.random() * boundY;
        Particle particle = new Particle(x, y);
        return particle;
    }

    public void adjustTargetTo(BufferedImage target) {
        DistributionPlanner planner = new DistributionPlanner(boundX, boundY);

        // TODO: This is probably a really slow way of doing this..
        int width = target.getWidth();
        int height = target.getHeight();

    }

    public void update() {
        final float maxdist = 0.5f;
        int moved = 0;

        for (Particle part: particles) {
            Vector2 delta = new Vector2(part.target);
            delta.sub(part.position);
            float len = delta.len();
            if (len > 0.01f) {
                float distToMove = Math.min(len, maxdist);
                delta = delta.nor();
                delta.x *= distToMove;
                delta.y *= distToMove;
                part.position.add(delta);
                moved++;
            }
        }
    }

    public void render(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        allocateIntermediatesIfNeeded(width * height);

        for (int i=0; i<width*height; i++)
            intermediatePixels[i].clear();

        for (Particle part: particles) {
            int x = (int)part.position.x;
            int y = (int)part.position.y;
            int index = y*width + x;
            if (index >= 0 && index < intermediatePixels.length) {
                intermediatePixels[index].r += part.color.getRed();
                intermediatePixels[index].g += part.color.getGreen();
                intermediatePixels[index].b += part.color.getBlue();
                intermediatePixels[index].a += part.color.getAlpha();
            }
        }

        for (int i=0; i<intermediatePixels.length; i++) {
            int x = i % width;
            int y = i / width;

            Color color = new Color(
                Math.min(255, intermediatePixels[i].r),
                Math.min(255, intermediatePixels[i].g),
                Math.min(255, intermediatePixels[i].b),
                Math.min(255, intermediatePixels[i].a)
            );
            image.setRGB(x, y, color.getRGB());
        }
    }

    private void allocateIntermediatesIfNeeded(int count) {
        if (intermediatePixels == null || intermediatePixels.length != count) {
            intermediatePixels = new IntermediatePixel[count];
            for (int i=0; i<count; i++) {
                intermediatePixels[i] = new IntermediatePixel();
            }
        }
    }

}
