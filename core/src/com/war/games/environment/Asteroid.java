package com.war.games.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Asteroid {
    protected int x,y;
    protected int width, height;
    protected int xSpeed,ySpeed;
    private final static Texture visual = new Texture(Gdx.files.internal("elt/Meteors/meteorBrown_med1.png"));

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

    public Asteroid(int x, int y, int xSpeed, int ySpeed, int width, int height) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.height = height;
        this.width = width;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.visual, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void update() {
        this.setX(this.getX() + this.getxSpeed());
    }

}
