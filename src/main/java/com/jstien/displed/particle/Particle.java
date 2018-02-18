package com.jstien.displed.particle;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Particle {
    public Particle(float posX, float posY) {
        color = new Color(
                (int)(Math.random()*20f + 10f),
                (int)(Math.random()*20f + 10f),
                (int)(Math.random()*20f + 10f),
                (int)(Math.random()*30f + 20f)
        );

        position = new Vector2(posX, posY);
        target = new Vector2(position);
    }

    Vector2 position;
    Vector2 target;
    Color color;
}
