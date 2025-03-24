package com.war.games.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Shield extends Powerup{
    private final static Texture visual = new Texture(Gdx.files.internal("elt/Powerups/powerupBlue_shield.png"));
    public Shield(int x, int y, int xSpeed, int ySpeed, int width, int height, int duration) {
        super(x, y, xSpeed, ySpeed, width, height, visual,duration);
        this.duration = duration;
        setName("Shield");
    }
}
