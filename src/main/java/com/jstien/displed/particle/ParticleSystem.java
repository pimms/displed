package com.jstien.displed.particle;

import com.badlogic.gdx.math.Vector2;
import com.jstien.displed.PixelBuffer;

import java.awt.*;

public class ParticleSystem {
    private float boundX;
    private float boundY;
    private Particle[] particles;


    private class IntermediatePixel {
        int r;
        int g;
        int b;
        int a;
    }

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
        float size = 10f;

        float squareCount = (float)Math.sqrt(particles.length);
        float step = size / squareCount;

        float x = boundX/2f - size/2f;
        float y = boundY/2f - size/2f;

        int index = 0;
        for (float i=0f; i<squareCount; i+=step) {
            for (float j=0f; j<squareCount && index < particles.length; j+=step) {
                Particle part = particles[index++];
                part.target.x = x + i;
                part.target.y = y + j;
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

    public void render(PixelBuffer pixelBuffer) {
        pixelBuffer.clear();

        final int width = pixelBuffer.getWidth();
        final int height = pixelBuffer.getHeight();

        IntermediatePixel[] intermediate = new IntermediatePixel[width * height];
        for (int i=0; i<width*height; i++)
            intermediate[i] = new IntermediatePixel();

        for (Particle part: particles) {
            int x = (int)part.position.x;
            int y = (int)part.position.y;
            int index = y*width + x;
            if (index >= 0 && index < intermediate.length) {
                intermediate[index].r += part.color.getRed();
                intermediate[index].g += part.color.getGreen();
                intermediate[index].b += part.color.getBlue();
                intermediate[index].a += part.color.getAlpha();
            }
        }

        for (int i=0; i<intermediate.length; i++) {
            int x = i % width;
            int y = i / width;

            Color color = new Color(
                Math.min(255, intermediate[i].r),
                Math.min(255, intermediate[i].g),
                Math.min(255, intermediate[i].b),
                Math.min(255, intermediate[i].a)
            );
            pixelBuffer.setPixel(x, y, color);
        }
    }

}
