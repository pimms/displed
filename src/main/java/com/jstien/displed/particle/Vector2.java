package com.jstien.displed.particle;

public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public void add(Vector2 other) {
        x += other.x;
        y += other.y;
    }

    public void sub(Vector2 other) {
        x -= other.x;
        y -= other.y;
    }

    public float len() {
        double dsquare = (double)( x*x + y*y );
        return (float)Math.sqrt(dsquare);
    }

    public void nor() {
        float len = len();
        x /= len;
        y /= len;
    }
}
