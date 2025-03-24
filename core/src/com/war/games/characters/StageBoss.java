package com.war.games.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.war.games.projectiles.LaserBeam;
import com.war.games.projectiles.Projectile;
import com.war.games.projectiles.Rocket;

import static com.badlogic.gdx.math.MathUtils.random;

public class StageBoss extends Enemy{
    private int load = 0; //Tire un certain nombre de fois avant de lancer son attaque spécial
    private static final Texture visual = new Texture(Gdx.files.internal("elt/Enemies/enemyRed3.png"));
    private int difficulty;
    public StageBoss(int x, int y, int xSpeed, int ySpeed, int width, int height, int difficulty){
        super(x,y,xSpeed,ySpeed,width,height);
        this.health = 100;
        setFirepower(25);
        this.difficulty = difficulty;
    }
    @Override
    public void randomMove() {
        int direction = random.nextInt(2);
        switch (direction) {
            case 0: // Déplacement vers le haut
                y += ySpeed;
                break;
            case 1: // Déplacement vers le bas
                y -= ySpeed;
                break;
        }
    }

    public void takeDamage(int amount) {
        health -= (int) (amount / 10);
        if (health <= 0) {
            this.isAlive = false;
        }
    }

    //Lance une attaque spéciale
    public Projectile specialAttack() {
        Projectile rocket = new Rocket(this.x - this.width,this.y + this.height/2,-7,0, 80, 80,firepower*2, false);
        return rocket;
    }


    public Projectile shoot() {
        Projectile projectile;
        if(this.load > 5) {
            projectile = specialAttack(); //Si a déjà tiré au moins 5 fois, lance une attaque attaque spéciale.
            load = 0;
        }
        else{
            projectile = new LaserBeam(this.x - this.width,this.y + this.height/2,-10,0, 88, 16,firepower,false);
            this.load++;
        }
        return projectile;
    }

}
