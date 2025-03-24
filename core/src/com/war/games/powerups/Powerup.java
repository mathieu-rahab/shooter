package com.war.games.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.war.games.characters.Player;

public class Powerup {
    protected int x,y;
    protected int width, height;
    protected int xSpeed,ySpeed;
    protected Texture visual;

    protected int duration;

    protected boolean used;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public boolean isUsed() {
        return used;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Powerup(int x, int y, int xSpeed, int ySpeed, int width, int height, Texture visual, int duration) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.height = height;
        this.width = width;
        this.visual = visual;
        this.duration = duration;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.visual, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void update() {
        this.setX(this.getX() + this.getxSpeed());
    }

    public void used(){
        this.duration = 0;
        this.used = true;
    }
}
