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

    private float boundX;
    private float boundY;
    private Particle[] particles;

    private IntermediatePixel[] intermediatePixels;

    public ParticleSystem(int boundX, int boundY, int numParticles) {
        this.boundX = (float)boundX;
        this.boundY = (float)boundY;

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


    public void temp_reorganize() {
        float size = 5f;

        float squareCount = (float)Math.sqrt(particles.length);
        float step = size / squareCount;

        float x = boundX/2f - size/2f;
        float y = boundY/2f - size/2f;

        int index = 0;
        for (float i=0f; i<squareCount; i+=step) {
            for (float j=0f; j<squareCount && index < particles.length; j+=step) {
                Particle part = particles[index++];
                part.target.x = x + j;
                part.target.y = y + i;
            }
        }
        System.out.printf("Updated %d particles\n", index);
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
