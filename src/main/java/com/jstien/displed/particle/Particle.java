package com.jstien.displed.particle;

public class Particle {
    private static final float AT_REST_THRESH = 0.01f;

    private final Vector2 position;
    private final Vector2 target;

    public Particle(Vector2 position) {
        this.position = position;
        this.target = new Vector2(position.x, position.y);
    }

    public Particle(float posX, float posY) {
        position = new Vector2(posX, posY);
        target = new Vector2(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setTarget(Vector2 target) {
        this.target.x = target.x;
        this.target.y = target.y;
    }

    public void setTarget(float x, float y) {
        target.x = x;
        target.y = y;
    }

    public void moveToTarget(float maxDelta) {
        Vector2 delta = new Vector2(target);
        delta.sub(position);

        float len = delta.len();
        if (len < maxDelta) {
            position.x = target.x;
            position.y = target.y;
        } else {
            delta.x = (delta.x / len) * maxDelta;
            delta.y = (delta.y / len) * maxDelta;
            position.add(delta);
        }
    }

    public boolean isAtRest() {
        float xdiff = Math.abs(position.x - target.x);
        float ydiff = Math.abs(position.y - target.y);
        return (xdiff < AT_REST_THRESH && ydiff < AT_REST_THRESH);
    }
}
