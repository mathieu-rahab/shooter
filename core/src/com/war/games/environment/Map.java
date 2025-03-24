package com.war.games.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.war.games.blocks.Block;
import com.war.games.characters.Character;
import com.war.games.characters.Enemy;
import com.war.games.characters.Player;
import com.war.games.characters.StageBoss;
import com.war.games.powerups.Powerup;
import com.war.games.powerups.Shield;
import com.war.games.powerups.Supercanon;
import com.war.games.projectiles.Projectile;
import com.war.games.screens.GameShooter;
import com.war.games.screens.ScoreBoard;
import com.war.games.screens.Shooter;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static com.badlogic.gdx.math.MathUtils.random;


public class Map {
    private int difficulty; //Influence la quantité d'ennemis et d'asteroides présents sur la Map
    private HashSet<Enemy> enemies = new HashSet<>(); // Characters présents dans la map
    private HashSet<Asteroid> asteroids = new HashSet<>(); // astéroides présents dans la map
    private HashSet<Powerup> powerups = new HashSet<>(); // Projectiles présents dans la map
    private HashSet<Projectile> enemyProjectiles = new HashSet<>(); // Projectiles ennemis présents dans la map
    private HashSet<Projectile> playerProjectiles = new HashSet<>(); // Projectiles présents dans la map
    private int bossKilled;
    private int enemiesKilled;

    public int getBossKilled() {
        return bossKilled;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    // Déclaration des variables de temporisation
    private long lastAsteroidSpawnTime;
    private long lastPowerupSpawnTime;
    private boolean bosspresence = false;
    private final long asteroidSpawnInterval = 7000000000L; // 8 secondes en nanosecondes
    private final long powerupSpawnInterval = 12000000000L; // 12 secondes en nanosecondes
    private ArrayList<Block> blocks;
    GameShooter game;
    private Player player = new Player(50, (Shooter.getWindowHeight()/2), 3,2, false);
    LocalDateTime startTime; //Pour calculer la durée de la partie


    public Map(int difficulty, ArrayList<Block> blocks, GameShooter game, LocalDateTime startTime ) {
        this.difficulty = difficulty;
        this.blocks = blocks;
        enemiesGen();
        asteroidsSpawn();
        lastAsteroidSpawnTime = TimeUtils.nanoTime();
        lastPowerupSpawnTime = TimeUtils.nanoTime();
        this.game = game;
        this.startTime = startTime;
    }

    //Vérifie le niveau de santé du joueur et affiche un écran de game over
    public void checkPlayerHealth(){
        if (this.player.getHealth() <= 0) {
            game.setScreen(new ScoreBoard(game, false, this.difficulty, (enemiesKilled + bossKilled),startTime));
        }
    }
    public Player getPlayer() {
        return player;
    }

    //Fonction permettant de faire spawn les ennemies
    public void enemiesGen(){
        float limiteBasse = Shooter.getWindowHeight() * 0.25f;  // 1/4 de la hauteur de l'écran
        float limiteHaute = Shooter.getWindowHeight() * 0.75f;  // 3/4 de la hauteur de l'écran
        int cpt = 0;
        while(cpt < this.difficulty * 3){ //Génère un certain nombre d'ennemies, en fonction de la difficulté
            int spawnY = MathUtils.random((int) limiteBasse, (int) limiteHaute);  // Génère une position y entre le 1/4 et le 3/4 de l'écran
            Enemy enemy = new Enemy(Shooter.getWindowWidth() - 40, spawnY, -5, 10, 40, 40);
            enemies.add(enemy);
            cpt++;
        }
    }

    //Fonction permettant de faire spawn le boss de stage
    public void stageBossSpawn(){
        Enemy enemy = new StageBoss(Shooter.getWindowWidth() - 80, Shooter.getWindowHeight()/2,0,12,130,120, this.difficulty);
        enemies.add(enemy);
        bosspresence = true;
    }

    //Fonction permettant de faire spawn des astéroides
    public void asteroidsSpawn() {
        float lowerBound = Shooter.getWindowHeight() * 0.25f;  // 1/4 de la hauteur de l'écran
        float upperBound = Shooter.getWindowHeight() * 0.75f;  // 3/4 de la hauteur de l'écran
        int spawnY = MathUtils.random((int) lowerBound, (int) upperBound);  // Génère une position y entre le 1/4 et le 3/4 de l'écran
        int randomSpeed = MathUtils.random(2,10); //Détermine une vitesse random
        Asteroid asteroid = new Asteroid(Shooter.getWindowWidth(), spawnY, -randomSpeed, 0, 30, 25);
        asteroids.add(asteroid);
    }

    public void powerupSpawn() {
        int powerUpType = random.nextInt(100); // Détermine si ce sera un bouclier ou un supercanon
        float lowerBound = Shooter.getWindowHeight() * 0.25f;  // 1/4 de la hauteur de l'écran
        float upperBound = Shooter.getWindowHeight() * 0.75f;  // 3/4 de la hauteur de l'écran
        int spawnY = MathUtils.random((int) lowerBound, (int) upperBound);  // Génère une position y entre le 1/4 et le 3/4 de l'écran
        Powerup powerup;
        if (powerUpType > 49) { //50 % de chances de faire spawn un bouclier
            powerup = new Shield(Shooter.getWindowWidth(), spawnY, -2, 0, 25, 20, 100);
        } else {
            powerup = new Supercanon(Shooter.getWindowWidth(), spawnY, -2, 0, 25, 20, 15);
        }
        powerups.add(powerup);
    }



    public boolean detectCollision(Projectile projectile, Character character){
        // Coordonnées et dimensions du Projectile
        int projLeft = projectile.getX();
        int projRight = projectile.getX() + projectile.getWidth();
        int projTop = projectile.getY() + projectile.getHeight();
        int projBottom = projectile.getY();

        // Coordonnées et dimensions du Character
        int charLeft = character.getX();
        int charRight = character.getX() + character.getWidth();
        int charTop = character.getY() + character.getHeight();
        int charBottom = character.getY();

        // Vérification de la superposition des rectangles
        return (projRight >= charLeft) && (projLeft <= charRight) &&
                (projTop >= charBottom) && (projBottom <= charTop);
    }

    public boolean detectCollision(Powerup powerup, Player player){
        // Coordonnées et dimensions du powerup
        int powLeft = powerup.getX();
        int powRight = powerup.getX() + powerup.getWidth();
        int powTop = powerup.getY() + powerup.getHeight();
        int powBottom = powerup.getY();

        // Coordonnées et dimensions du Character
        int charLeft = player.getX();
        int charRight = player.getX() + player.getWidth();
        int charTop = player.getY() + player.getHeight();
        int charBottom = player.getY();

        // Vérification de la superposition des rectangles
        return (powRight >= charLeft) && (powLeft <= charRight) &&
                (powTop >= charBottom) && (powBottom <= charTop);
    }

    public boolean detectCollision(Asteroid asteroid, Player player){
        // Coordonnées et dimensions de l'asteroid
        int AsterLeft = asteroid.getX();
        int AsterRight = asteroid.getX() + asteroid.getWidth();
        int AsterTop = asteroid.getY() + asteroid.getHeight();
        int AsterBottom = asteroid.getY();

        // Coordonnées et dimensions du Character
        int charLeft = player.getX();
        int charRight = player.getX() + player.getWidth();
        int charTop = player.getY() + player.getHeight();
        int charBottom = player.getY();

        // Vérification de la superposition des rectangles
        return (AsterRight >= charLeft) && (AsterLeft <= charRight) &&
                (AsterTop >= charBottom) && (AsterBottom <= charTop);
    }

    private boolean detectCollision(Projectile projectile, Asteroid asteroid) {
        // Coordonnées et dimensions de l'asteroid
        int AsterLeft = asteroid.getX();
        int AsterRight = asteroid.getX() + asteroid.getWidth();
        int AsterTop = asteroid.getY() + asteroid.getHeight();
        int AsterBottom = asteroid.getY();

        // Coordonnées et dimensions du projectile
        int projLeft = projectile.getX();
        int projRight = projectile.getX() + projectile.getWidth();
        int projTop = projectile.getY() + projectile.getHeight();
        int projBottom = projectile.getY();

        // Vérification de la superposition des rectangles
        return (AsterRight >= projLeft) && (AsterLeft <= projRight) &&
                (AsterTop >= projBottom) && (AsterBottom <= projTop);
    }
    private boolean detectCollision(Projectile projectile, Projectile EnemyProjectile) {
        // Coordonnées et dimensions du projectile ennemi
        int EprojLeft = EnemyProjectile.getX();
        int EprojRight = EnemyProjectile.getX() + EnemyProjectile.getWidth();
        int EprojTop = EnemyProjectile.getY() + EnemyProjectile.getHeight();
        int EprojBottom = EnemyProjectile.getY();

        // Coordonnées et dimensions du projectile player
        int projLeft = projectile.getX();
        int projRight = projectile.getX() + projectile.getWidth();
        int projTop = projectile.getY() + projectile.getHeight();
        int projBottom = projectile.getY();

        // Vérification de la superposition des rectangles
        return (EprojRight >= projLeft) && (EprojLeft <= projRight) &&
                (EprojTop >= projBottom) && (EprojBottom <= projTop);
    }

    private boolean detectCollision(Projectile projectile, Block block) {
        // Coordonnées et dimensions du block
        int blockLeft = block.getX();
        int blockRight = block.getX() + Block.getWidth();
        int blockTop = block.getY() + Block.getHeight();
        int blockBottom = block.getY();

        // Coordonnées et dimensions du projectile player
        int projLeft = projectile.getX();
        int projRight = projectile.getX() + projectile.getWidth();
        int projTop = projectile.getY() + projectile.getHeight();
        int projBottom = projectile.getY();

        // Vérification de la superposition des rectangles
        return (blockRight >= projLeft) && (blockLeft <= projRight) &&
                (blockTop >= projBottom) && (blockBottom <= projTop);
    }



    public void manageCollisions() {
        //Detection des collisions entre les projectiles des ennemies et le player
        Iterator<Projectile> enemyProjectileIterator = enemyProjectiles.iterator();
        while (enemyProjectileIterator.hasNext()) {
            Projectile projectile = enemyProjectileIterator.next();
            if (detectCollision(projectile, player)) {
                player.takeDamage(projectile.getDamage());
                enemyProjectileIterator.remove();
            }
        }

        /*Detection d'éventuelles collisions entre les projectiles de player
        et les enemies, asteroids, ou projectiles enemies*/
        Iterator<Projectile> playerProjectileIterator = playerProjectiles.iterator();
        while (playerProjectileIterator.hasNext()) {
            Projectile projectile = playerProjectileIterator.next();

            Iterator<Enemy> enemyIterator = enemies.iterator();
            boolean projectileRemoved = false;
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (detectCollision(projectile, enemy)) {
                    enemy.takeDamage(projectile.getDamage());
                    playerProjectileIterator.remove();
                    projectileRemoved = true;
                    break;  // Sortir de la boucle après la suppression pour éviter les erreurs
                }
            }

            if (!projectileRemoved) {
                Iterator<Asteroid> asteroidIterator = asteroids.iterator();
                while (asteroidIterator.hasNext()) {
                    Asteroid asteroid = asteroidIterator.next();
                    if (detectCollision(projectile, asteroid)) {
                        asteroidIterator.remove();
                        playerProjectileIterator.remove();
                        projectileRemoved = true;
                        break;
                    }
                }
            }

            if (!projectileRemoved) {
                Iterator<Projectile> enemyProjectileIterator_II = enemyProjectiles.iterator();
                while (enemyProjectileIterator_II.hasNext()) {
                    Projectile Eprojectile = enemyProjectileIterator_II.next();
                    if (detectCollision(projectile, Eprojectile)) {
                        enemyProjectileIterator_II.remove();
                        playerProjectileIterator.remove();
                        break;  // Sortir de la boucle après la suppression
                    }
                }
            }
        }


        //Detection des collisions entre le player et les astéroides
        Iterator<Asteroid> asteroidIterator = asteroids.iterator();
        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            if (detectCollision(asteroid, player)) {
                player.takeDamage(50);
                asteroidIterator.remove();
            } else {
                // Cas où le player tire sur un asteroid
                playerProjectileIterator = playerProjectiles.iterator();
                while (playerProjectileIterator.hasNext()) {
                    Projectile projectile = playerProjectileIterator.next();
                    if (detectCollision(projectile, asteroid)) {
                        asteroidIterator.remove();
                        playerProjectileIterator.remove();
                        break;  // Sortir de la boucle après la suppression
                    }
                }
            }
        }


        //Cas où un projectile touche un bloc
        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            enemyProjectileIterator = enemyProjectiles.iterator();
            while (enemyProjectileIterator.hasNext()) {
                Projectile projectile = enemyProjectileIterator.next();
                if (detectCollision(projectile, block)) {
                    enemyProjectileIterator.remove();
                }
            }

            playerProjectileIterator = playerProjectiles.iterator();
            while (playerProjectileIterator.hasNext()) {
                Projectile projectile = playerProjectileIterator.next();
                if (detectCollision(projectile, block)) {
                    playerProjectileIterator.remove();
                }
            }
        }



        //Cas où le player récupère un power up
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            if (detectCollision(powerup, player)) {
                player.usePowerup(powerup);
                powerupIterator.remove();
            }
        }

    }

    //Retire les éléments qui ne sont pas sur l'écran
    public void removeOutOfBounds() {
        // Traitement des projectiles du joueur
        Iterator<Projectile> playerProjectileIterator = playerProjectiles.iterator();
        while (playerProjectileIterator.hasNext()) {
            Projectile projectile = playerProjectileIterator.next();
            if (projectile.getX() > Shooter.getWindowWidth()+10) {
                playerProjectileIterator.remove();
            }
        }

        // Traitement des projectiles ennemis
        Iterator<Projectile> enemyProjectileIterator = enemyProjectiles.iterator();
        while (enemyProjectileIterator.hasNext()) {
            Projectile projectile = enemyProjectileIterator.next();
            if (projectile.getX() < -10) {
                enemyProjectileIterator.remove();
            }
        }

        // Traitement des astéroïdes
        Iterator<Asteroid> asteroidIterator = asteroids.iterator();
        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            if (asteroid.getX() < -10) {
                asteroidIterator.remove();
            }
        }

        // Traitement des powerups
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            if (powerup.getX() < -10) {
                powerupIterator.remove();
            }
        }
    }


    //Nettoie la Map
    public void mapCleanup() {
        // Vérifie et retire les ennemis morts
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (!enemy.isAlive()) {
                if (enemy instanceof StageBoss) {
                    bossKilled++;
                } else {
                    enemiesKilled++;
                }
                enemyIterator.remove();  // Retire les ennemis morts
            }
        }

        // Retire les objets non présents sur l'écran
        removeOutOfBounds();
    }

    //Gère les déplacements et les tirs des ennemies
    public void enemiesHunting(){
        for(Enemy enemy : enemies){
            enemy.update();
            int shootIntent = MathUtils.random(100);
            if(shootIntent > 98){
                Projectile p = enemy.shoot();
                enemyProjectiles.add(p);
            }
        }
    }

    public void playerShoot(){
        Projectile projectile = player.shoot();
        playerProjectiles.add(projectile);
    }



    public void asteroidsMoves(){
        for(Asteroid asteroid : asteroids){
            asteroid.update();
        }
    }

    public void powerUpsMoves(){
        for(Powerup powerup : powerups){
            powerup.update();
        }
    }
    public void projectilesMoves(){
        for(Projectile projectile : enemyProjectiles){
            projectile.update();
        }
        for(Projectile projectile : playerProjectiles){
            projectile.update();
        }
    }

    //-----------------------------------------------------------------------------------//
    public void draw(SpriteBatch batch, ShapeRenderer shape){
        player.draw(batch);
        for(Projectile projectile : enemyProjectiles){
            projectile.draw(batch);
        }
        for(Projectile projectile : playerProjectiles){
            projectile.draw(batch);
        }

        for(Asteroid asteroid : asteroids){
            asteroid.draw(batch);
        }
        for(Powerup powerup : powerups){
            powerup.draw(batch);
        }
        for(Enemy enemy : enemies){
            enemy.draw(batch);
            //enemy.drawBarLife(shape); //Décommenter pour afficher la barre de vie des ennemies
        }
    }

    public void update(ArrayList<Block> blocks){
        this.player.update();
        checkPlayerHealth();
        this.blocks = blocks; //MaJ de l'emplacement des blocs présents dans la map
        projectilesMoves();
        enemiesHunting();

        int spawnNumber = random.nextInt(5); //Détermine le nombre max d'astéroids pouvant être générés à la fois
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastAsteroidSpawnTime > asteroidSpawnInterval) {
            for(int i = 0; i<spawnNumber; i++){
                asteroidsSpawn();
            }
            lastAsteroidSpawnTime = currentTime; // Met à jour le dernier temps de spawn
        }
        if (currentTime - lastPowerupSpawnTime > powerupSpawnInterval) {
            powerupSpawn();
            lastPowerupSpawnTime = currentTime;
        }

        asteroidsMoves();
        powerUpsMoves();
        manageCollisions();
        mapCleanup();

        //S'il n'y a plus d'ennemis
        if (enemies.isEmpty() && enemiesKilled <= 12) {
            enemiesGen();
        } else if (enemiesKilled >= 12 && !this.bosspresence) {
            stageBossSpawn();
        }

        //Si tous les ennemis sont morts, faire apparaître l'écran de victoire
        if(bossKilled > 0 && enemies.isEmpty()){
            game.setScreen(new ScoreBoard(game, true, this.difficulty, (enemiesKilled + bossKilled),startTime));
        }
    }
}


