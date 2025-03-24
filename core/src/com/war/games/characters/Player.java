package com.war.games.characters;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Null;
import com.war.games.powerups.Powerup;
import com.war.games.powerups.Shield;
import com.war.games.powerups.Supercanon;
import com.war.games.projectiles.LaserBeam;
import com.war.games.projectiles.Projectile;
import com.war.games.projectiles.Rocket;
import com.war.games.screens.GameShooter;
import com.war.games.screens.ScoreBoard;
import com.war.games.screens.Shooter;

import java.io.IOException;


public class Player extends Character{

    private int shield;
    private Powerup powerupEquipped;
    boolean powerupOn = false;
    private static final int PlayerWidth = 50;
    private static final int PlayerHeight = 50;

    private static final Texture visualBasicRight = new Texture(Gdx.files.internal("elt/playerShips/playerShip1_orangeRight.png"));

    private boolean useMouse;

    public Powerup getPowerupEquipped() {
        return powerupEquipped;
    }

    public Player(int x, int y, int xSpeed, int ySpeed, boolean useMouse) { // Position de départ en paramètre
        super(x,y,xSpeed,ySpeed,PlayerWidth,PlayerHeight,100, Player.visualBasicRight);
        this.useMouse = useMouse;
        setFirepower(50);
    }

    @Override
    public void update(){
        if(!this.useMouse){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                this.updateX(-(this.xSpeed)) ;
            }if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                this.updateX(this.xSpeed) ;
            }if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                this.updateY(this.ySpeed); ;
            }if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                this.updateY(-(this.ySpeed)); ;
            }
        }
        else {//suivi de souris
            this.setXMouse(Gdx.input.getX() - (this.getWidth()/2));//setXMouse pour bloqué le vaisseau à la moitié
            this.setY((Shooter.getWindowHeight() - Gdx.input.getY()) - (getHeight()/2));
        }
    }

    public boolean powerUpUseability(){
        return (powerupEquipped != null && powerupOn == true);
    }
    public void expirePowerup() {
        if (powerupEquipped != null && powerupEquipped.getDuration() < 1) {
            powerupEquipped = null;
            powerupOn = false;
        }
    }

    //Fonction non terminée
    @Override
    public void takeDamage(int amount) {
        //bouclier present
        if(powerUpUseability()
                && powerupEquipped instanceof Shield){
            //Pas de dégâts subis
            powerupEquipped.setDuration(powerupEquipped.getDuration() - 20);//Diminution de l'intégrité du bouclier
            expirePowerup();
        }
        //Sinon bouclier non present
        else{health -= amount;}
    }

    //Fonction non terminée
    public Projectile shoot() {
        Projectile projectile;

        /*S'il s'agit d'un supercanon avec une durée d'utilisation encore active,
        augmente la puissance de feu (donc dégâts infligés) du projectile tiré.*/
        if(powerUpUseability()){
            if (powerupEquipped instanceof Supercanon) {
                projectile = new Rocket(this.x + this.width, this.y + this.height/2 -12, 10, 0, 30, 25, firepower * 2,true);
                powerupEquipped.setDuration(powerupEquipped.getDuration() - 1);
                expirePowerup();
                return projectile;
            }
        }

        //Sinon tire un projectile basique.
        projectile = new LaserBeam(this.x + this.width,this.y + this.height/2 -4,10,0, 44, 9,firepower, true);
        return projectile;
    }


    //Fonction non terminée
    //Permet d'utiliser un powerup
    public void usePowerup(Powerup powerup) {
        powerupEquipped = powerup;
        powerupOn = true;
    }

    public void updateX(int x){
        //methode spéciale pour le joueur qui l'empeche de sortir de l'écran ou de dépasser une moitié de l'écran
        int newX = this.getX() + x;
        if(newX > 0 && newX + this.width < Shooter.getWindowWidth()/2){
            this.x = newX;
        }
    }

    public void setXMouse(int x){
        if(x > 0 && x + this.width < Shooter.getWindowWidth()/2){
            this.x = x;
        }

    }

}
