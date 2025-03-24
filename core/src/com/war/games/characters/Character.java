package com.war.games.characters;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.war.games.blocks.Block;
import com.war.games.projectiles.Projectile;
import com.war.games.powerups.Powerup;

public abstract class Character {
    protected int x, y; //Position du vaisseau
    protected int xSpeed, ySpeed; //Vitesse de déplacement, éventuellement modifiée par un power up
    protected Texture visual; //Visuel du vaisseau
    protected int width, height;
    protected int health;  //Santé du vaisseau
    protected int firepower; //Puissance de feu
    protected int healthStart;

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public Character(int x, int y, int xSpeed, int ySpeed, int width, int height,int health, Texture visual) { // Position de départ en paramètre
        this.x = x;
        this.y = y - height/2;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.height = height;
        this.width = width;
        this.health = health;
        this.visual = visual;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    //Vérifie une éventuelle collision entre le vaisseau Player et un block
    public boolean collidesWith(Block block) {
        int characterLeft = this.getX();
        int characterRight = this.getX() + this.getWidth();
        int characterTop = this.getY() + this.getHeight();
        int characterBottom = this.getY();

        int blockLeft = block.getX();
        int blockRight = block.getX() + Block.getWidth();
        int blockTop = block.getY() + Block.getHeight();
        int blockBottom = block.getY();

        return ((characterRight > blockLeft) && (characterLeft < blockRight) && (characterTop > blockBottom) && (characterBottom < blockTop));
    }

    public void takeDamage(int amount) {}

    public void update() {

    }

    public void render() {

    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.visual, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void updateX(int x){
        this.x += x;
    }
    public void updateY(int y){
        this.y += y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setVisual(Texture visual) {
        this.visual = visual;
    }
}
