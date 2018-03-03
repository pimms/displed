package com.jstien.displed.particle;

public class Particle {
    private Vector2 position;
    private Vector2 target;

    public Particle(Vector2 position) {
        this.position = position;
        this.target = new Vector2(position);
    }

    public Particle(float posX, float posY) {
        position = new Vector2(posX, posY);
        target = new Vector2(position);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public void moveToTarget(float maxDelta) {
        Vector2 delta = new Vector2(target);
        delta.sub(position);

        float len = delta.len();
        if (len < maxDelta) {
            position = target;
        } else {
            delta.x = (delta.x / len) * maxDelta;
            delta.y = (delta.y / len) * maxDelta;
            position.add(delta);
        }
    }
}
