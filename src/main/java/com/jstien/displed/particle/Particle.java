package com.jstien.displed.particle;

import java.awt.*;

public class Particle {
    public Particle(float posX, float posY) {
        position = new Vector2(posX, posY);
        target = new Vector2(position);
    }

    Vector2 position;
    Vector2 target;
}
