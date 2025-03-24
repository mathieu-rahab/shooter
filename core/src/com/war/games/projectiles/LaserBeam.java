package com.war.games.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class LaserBeam extends Projectile{
    protected int damage = 20;
    private final static Texture visualPlayer= new Texture(Gdx.files.internal("elt/Lasers/laserBlue01.png"));
    private final static Texture visualEnemies = new Texture(Gdx.files.internal("elt/Lasers/laserRed01.png"));

    private static final Music sound = Gdx.audio.newMusic(Gdx.files.internal("music/laser.wav"));;


    public LaserBeam(int x, int y, int xSpeed, int ySpeed, int width, int height,  int damage, Boolean isPlayer) {
        super(x, y, xSpeed, ySpeed, width, height, damage, LaserBeam.choiceTexture(isPlayer), LaserBeam.sound);
    }


    private static Texture choiceTexture(Boolean isPlayer){
        if(isPlayer){
            return LaserBeam.visualPlayer;
        }
        return LaserBeam.visualEnemies;
    }
}
