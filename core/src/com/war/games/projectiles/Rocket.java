package com.war.games.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class Rocket extends Projectile{
    protected int damage = 20;
    private final static Texture visualPlayer= new Texture(Gdx.files.internal("elt/Lasers/rocketRight.png"));
    private final static Texture visualEnemies = new Texture(Gdx.files.internal("elt/Lasers/rocketLeft.png"));

    private static final Music sound = Gdx.audio.newMusic(Gdx.files.internal("music/laser.wav"));;


    public Rocket(int x, int y, int xSpeed, int ySpeed, int width, int height,  int damage, Boolean isPlayer) {
        super(x, y, xSpeed, ySpeed, width, height, damage, Rocket.choiceTexture(isPlayer), Rocket.sound);
    }


    private static Texture choiceTexture(Boolean isPlayer){
        if(isPlayer){
            return Rocket.visualPlayer;
        }
        return Rocket.visualEnemies;
    }
}
