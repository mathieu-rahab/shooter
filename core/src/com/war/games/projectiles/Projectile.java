package com.war.games.projectiles;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.war.games.environment.Asteroid;
import com.war.games.characters.Character;
import com.war.games.environment.Map;

import java.util.HashSet;

import static java.lang.Math.sqrt;
public class Projectile {
    protected int damage;
    protected int x,y;
    protected int width, height;
    protected int xSpeed;
    protected int ySpeed;
    protected Texture visual;
    protected Music sound;

    protected boolean isActive;

    protected String targetType; // Détermine le type d'ennemi touché par le projectile. Valeurs possibles : Hero // Enemy

    public Projectile(int x, int y, int xSpeed, int ySpeed, int width, int height, int damage, Texture visual, Music sound) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.height = height;
        this.width = width;
        this.damage = damage;
        this.visual = visual;
        this.sound = sound;

        sound.setVolume(0.6f);
        sound.play();
    }

    public int getX() {
        return x;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public int getDamage() {
        return damage;
    }



    public void draw(SpriteBatch batch) {

        batch.draw(this.visual, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void update() {
        this.setX(this.getX() + this.getxSpeed());
    }

}

