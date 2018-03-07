package com.jstien.displed.particle;

import com.jstien.displed.BinaryMap;

import java.util.ArrayList;
import java.util.List;

public class ParticlePlanner {
    private final class OpenPosition {
        public OpenPosition(int x, int y) {
            this.x = (float)x;
            this.y = (float)y;
        }
        public float x;
        public float y;
    }

    private List<Particle> particles;
    private IParticleFactory factory;

    public ParticlePlanner(List<Particle> particles, IParticleFactory factory) {
        this.particles = particles;
        this.factory = factory;
    }

    public void transitionTo(BinaryMap map) {
        List<Particle> unmoved = new ArrayList<>(particles);

        map.getFlagged().forEach(tuple -> {
            float x = (float)tuple.getFirst();
            float y = (float)tuple.getSecond();
            Particle particle = findClosestParticle(unmoved, x, y);
            if (particle == null) {
                particle = factory.createNewParticle();
                particles.add(particle);
            }
            particle.setTarget(x, y);
        });

        for (Particle unused: unmoved)
            particles.remove(unused);
    }

    private List<OpenPosition> mapOpenPositions(BinaryMap map) {
        List<OpenPosition> positions = new ArrayList<>();
        map.getFlagged().forEach(tuple -> {
            positions.add(new OpenPosition(tuple.getFirst(), tuple.getSecond()));
        });
        return positions;
    }


    private Particle findClosestParticle(List<Particle> available, float x, float y) {
        float len = Float.MAX_VALUE;
        Particle closest = null;
        int bestIdx = -1;

        for (int i=0; i<available.size(); i++) {
            Particle p = available.get(i);

            final float dx = x - p.getPosition().x;
            final float dy = y - p.getPosition().y;
            //float dist = Math.abs(x - p.getPosition().x) + Math.abs(y - p.getPosition().y);
            float dist = (float)Math.sqrt(dx*dx + dy*dy);

            if (dist < len) {
                closest = p;
                len = dist;
                bestIdx = i;
            }
        }

        if (bestIdx >= 0) {
            available.remove(bestIdx);
        }

        return closest;
    }
}
