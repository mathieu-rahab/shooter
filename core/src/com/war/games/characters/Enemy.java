package com.war.games.characters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.war.games.projectiles.Projectile;
import com.war.games.projectiles.LaserBeam;
import com.war.games.projectiles.Rocket;
import com.war.games.blocks.Block;
import com.war.games.screens.Shooter;

import com.badlogic.gdx.graphics.Color;


import java.util.HashMap;

import static com.badlogic.gdx.math.MathUtils.random;

public class Enemy extends Character{
    private static final HashMap<String, Texture> texturesEnemies = new HashMap<>();
    static {
        // Blue textures
        texturesEnemies.put("blue-1", new Texture(Gdx.files.internal("elt/Enemies/enemyBlue1.png")));
        texturesEnemies.put("blue-2", new Texture(Gdx.files.internal("elt/Enemies/enemyBlue2.png")));
        texturesEnemies.put("blue-3", new Texture(Gdx.files.internal("elt/Enemies/enemyBlue3.png")));
        texturesEnemies.put("blue-4", new Texture(Gdx.files.internal("elt/Enemies/enemyBlue4.png")));
        texturesEnemies.put("blue-5", new Texture(Gdx.files.internal("elt/Enemies/enemyBlue5.png")));
        // Green textures
        texturesEnemies.put("green-1", new Texture(Gdx.files.internal("elt/Enemies/enemyGreen1.png")));
        texturesEnemies.put("green-2", new Texture(Gdx.files.internal("elt/Enemies/enemyGreen2.png")));
        texturesEnemies.put("green-3", new Texture(Gdx.files.internal("elt/Enemies/enemyGreen3.png")));
        texturesEnemies.put("green-4", new Texture(Gdx.files.internal("elt/Enemies/enemyGreen4.png")));
        texturesEnemies.put("green-5", new Texture(Gdx.files.internal("elt/Enemies/enemyGreen5.png")));
    }

    //private static final int  width = 40;
    //private static final int  height = 40;


    protected boolean isAlive = true;
    protected float moveTimer; //Régule les déplacements en fonction du temps écoulé

    public Enemy(int x, int y, int xSpeed,int ySpeed,int width, int height) {
        super(x,y,xSpeed,ySpeed,width,height,100, choiceVisual());
        setFirepower(20);
    }

    //Choix entre deux textures différentes
    public static Texture choiceVisual(){
        int i = MathUtils.random(1,5);
        int color =  MathUtils.random(1,2);
        switch(color){
            case 1: return texturesEnemies.get("blue" + "-" + i);
            case 2: return texturesEnemies.get("green-" + i);
            default: return null;
        }
    }




    public boolean isAlive() {
        return isAlive;
    }


    public void update() {
        if (TimeUtils.nanoTime() - moveTimer > 0.009F * 1_000_000_000) { //déplacement toutes les 0.09 secondes
            randomMove();
            stayInBounds();
            moveTimer = TimeUtils.nanoTime();
        }
    }
    public void stayInBounds(){
        //L'empeche le vaisseau ennemi de sortir de l'ecran

        // Assurer que le vaisseau ne sorte pas de l'écran horizontalement
        x = Math.max(x, Shooter.getWindowWidth()/2);  // Assure que x ne soit pas inférieur à 0
        x = Math.min(x, Shooter.getWindowWidth() - width);  // Assure que x ne dépasse pas la largeur de l'écran moins la largeur du vaisseau

        // Assurer que le vaisseau ne sorte pas de l'écran verticalement
        y = Math.max(y, (int) (Shooter.getWindowHeight() * 0.25));  // Assure que y ne soit pas inférieur à 0
        y = Math.min(y, Shooter.getWindowHeight() - height - (int) (Shooter.getWindowHeight() * 0.25));

    }

    //Fonction gérant le déplacement du vaisseau ennemi
    public void randomMove() {
        int direction = random.nextInt(8); // Génère un nombre entre 0 et 7
        switch (direction) {
            case 0: // Déplacement vers le haut
                y += ySpeed;
                break;
            case 1: // Déplacement vers le bas
                y -= ySpeed;
                break;
            case 2: // Déplacement vers la gauche
                x -= xSpeed;
                break;
            case 3: // Déplacement vers la droite
                x += xSpeed;
                break;
            case 4: // Déplacement haut-gauche
                x -= xSpeed;
                y += ySpeed;
                break;
            case 5: // Déplacement haut-droite
                x += xSpeed;
                y += ySpeed;
                break;
            case 6: // Déplacement bas-gauche
                x -= xSpeed;
                y -= ySpeed;
                break;
            case 7: // Déplacement bas-droite
                x += xSpeed;
                y -= ySpeed;
                break;
        }
    }


    //Permet aux ennemies de tirer en renvoyant un missile
    public Projectile shoot() {
        Projectile laser = new LaserBeam(this.x - this.width,this.y + this.height/2,-10,0, 44, 8,firepower, false);
        return laser;
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            this.isAlive = false;
        }
    }

    //Déssine la barre de vie des énemies
    public void drawBarLife(ShapeRenderer shape) {
        int health = this.getHealth();
        int height = 3;
        int width = 40;
        int posY = this.getY() + this.getHeight() + 7;
        int posX = this.getX();

        if (health >= 0) {
            // Activer le mélange alpha pour la transparence
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

            shape.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f)); // Gris avec transparence
            shape.rect(posX, posY, width, height);

            // Désactiver le mélange alpha après le rendu
            Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
            int healthWidth = (int) ((health / 100.0) * width);
            // Dessiner la barre de vie rouge
            shape.setColor(Color.RED);
            shape.rect(posX, posY, healthWidth, height);
        }
    }
}
