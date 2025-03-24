package com.war.games.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.war.games.screens.Shooter;

public class Block {
    private int x;
    private int y;
    private static final int width = 25;
    private static final int height = 25;
    private Texture texture;

    public Block(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }






    public void update() {
        if(this.getX() <= (- Block.getWidth()) ){//si le block ce trouve entièrement hors de l'écran à gauche
            this.setX(Shooter.getSizeLine() * Block.width);
        }
        else{
            this.setX(this.getX() - 1);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public int getX() {
        return x;
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

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }


}
