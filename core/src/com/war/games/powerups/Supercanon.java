package com.war.games.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Supercanon extends Powerup{
    private final static Texture visual = new Texture(Gdx.files.internal("elt/Powerups/powerupRed_bolt.png"));
    public Supercanon(int x, int y, int xSpeed, int ySpeed, int width, int height, int duration) {
        super(x, y, xSpeed, ySpeed, width, height,visual,duration);
        this.duration = duration;
        setName("Supercanon");
    }
}
