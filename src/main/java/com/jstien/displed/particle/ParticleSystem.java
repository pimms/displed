package com.jstien.displed.particle;

import com.jstien.displed.BinaryMap;
import com.jstien.displed.ByteMap;
import com.jstien.displed.display.IDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleSystem implements IParticleFactory{
    private IDisplay display;
    private List<Particle> particles;

    public ParticleSystem(IDisplay display) {
        this.display = display;
        this.particles = new ArrayList<>();
    }

    public void transitionTo(BinaryMap map) {
        ParticlePlanner planner = new ParticlePlanner(particles, this);
        planner.transitionTo(map);
    }

    public void update() {
        for (Particle p : particles) {
            p.moveToTarget(0.5f);
        }
    }

    public void render() {
        ByteMap map = new ByteMap(display.getWidth(), display.getHeight());
        for (Particle p : particles) {
            drawParticle(map, p);
        }

        for (int y=0; y<display.getHeight(); y++) {
            for (int x=0; x<display.getWidth(); x++) {
                display.setPixel(x, y, map.getColor(x, y));
            }
        }
    }


    public boolean isEntirelyAtRest() {
        for (Particle p: particles) {
            if (!p.isAtRest())
                return false;
        }
        return true;
    }

    @Override
    public Particle createNewParticle() {
        Vector2 start = createPerimeterPosition();
        Particle part = new Particle(start.x, start.y);
        return part;
    }

    private Vector2 createPerimeterPosition() {
        Vector2 vec = new Vector2();

        Random rand = new Random();
        if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
                vec.x = -1;
            } else {
                vec.x = display.getWidth();
            }
            vec.y = rand.nextInt(display.getHeight());
        } else {
            if (rand.nextBoolean()) {
                vec.y = -1;
            } else {
                vec.y = display.getHeight();
            }
            vec.x = rand.nextInt(display.getWidth());
        }

        return vec;
    }

    private void drawParticle(ByteMap map, Particle particle) {
        Vector2 pos = particle.getPosition();
        for (int y=-1; y<=1; y++) {
            for (int x=-1; x<=1; x++) {
                int coordX = (int)pos.x + x;
                int coordY = (int)pos.y + y;
                float len = pos.distanceTo(coordX, coordY);
                float amplitude = 1.f - len;
                if (amplitude > 0.f) {
                    map.setPixel(coordX, coordY, (int)(128.f*amplitude), (int)(128.f*amplitude), (int)(128.f*amplitude));
                }
            }
        }
    }

}

