package com.war.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.war.games.blocks.Block;
import com.war.games.blocks.BlockExt;
import com.war.games.blocks.BlockInt;
import com.war.games.characters.Enemy;
import com.war.games.characters.Player;
import com.war.games.powerups.Shield;
import com.war.games.projectiles.LaserBeam;
import com.war.games.environment.Map;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import com.badlogic.gdx.utils.TimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Screen;
import com.war.games.characters.Enemy;

import static com.badlogic.gdx.math.MathUtils.map;
import static com.badlogic.gdx.math.MathUtils.random;


public class Shooter implements Screen {
    private static final int INITIAL_WINDOW_WIDTH = 840;
    private static final int INITIAL_WINDOW_HEIGHT = 473;
    private static final int sizeLine = 99;

    ArrayList<Block> blocks = new ArrayList<>();
    //private OrthographicCamera camera;
    //private SpriteBatch batch;
    private Texture backgroundTexture;

    private Music backgroundMusic;

    private Map gameMap;

    //gestion souris invisible
    private Cursor invisibleCursor;
    private Pixmap pixmap;
    private final GameShooter game;
    private boolean isPaused;
    private Stage stage;

    private long lastShoot = TimeUtils.nanoTime();


    private int level;

    //ensemble des textures nécessaires pour l'affichage des HUD du joueur
    private static final HashMap<String, Texture> texturesProgressBar = new HashMap<>();
    static {
        // Textures pour la couleur rouge
        texturesProgressBar.put("extGaucheRed", new Texture(Gdx.files.internal("widget/red/extGauche.png")));
        texturesProgressBar.put("extDroitRed", new Texture(Gdx.files.internal("widget/red/extDroit.png")));
        texturesProgressBar.put("extCentreRed", new Texture(Gdx.files.internal("widget/red/extCentre.png")));
        texturesProgressBar.put("iconRed", new Texture(Gdx.files.internal("widget/red/iconRed.png")));
        texturesProgressBar.put("intGaucheRed", new Texture(Gdx.files.internal("widget/red/intGauche.png")));
        texturesProgressBar.put("intDroitRed", new Texture(Gdx.files.internal("widget/red/intDroit.png")));
        texturesProgressBar.put("intCentreRed", new Texture(Gdx.files.internal("widget/red/intCentre.png")));

        // Textures pour la couleur purple
        texturesProgressBar.put("extGauchePurple", new Texture(Gdx.files.internal("widget/purple/extGauche.png")));
        texturesProgressBar.put("extDroitPurple", new Texture(Gdx.files.internal("widget/purple/extDroit.png")));
        texturesProgressBar.put("extCentrePurple", new Texture(Gdx.files.internal("widget/purple/extCentre.png")));
        texturesProgressBar.put("iconPurple", new Texture(Gdx.files.internal("widget/purple/iconPurple.png")));
        texturesProgressBar.put("intGauchePurple", new Texture(Gdx.files.internal("widget/purple/intGauche.png")));
        texturesProgressBar.put("intDroitPurple", new Texture(Gdx.files.internal("widget/purple/intDroit.png")));
        texturesProgressBar.put("intCentrePurple", new Texture(Gdx.files.internal("widget/purple/intCentre.png")));
    }

    private static final Texture iconHealth = new Texture(Gdx.files.internal("widget/icons/health.png"));
    private static final Texture iconShield = new Texture(Gdx.files.internal("widget/icons/shield.png"));

    private LocalDateTime startTime;





    public Shooter(GameShooter game, int level) {
        this.game = game;
        this.level = level;
    }


    @Override
    public void show() {
        create();
    }

    private void create() {
        game.getStage().clear();//retire tout les element precedent du menu principal
        this.backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));



        this.readLevel("levels/level2.txt");

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Mars.wav"));
        backgroundMusic.setVolume(0.6f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Gestion curseur invisible (pour ne pas l'afficher pendant une partie)
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        invisibleCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(invisibleCursor);


        createButtonToResume();
        createButtonTryAgain();
        createButtonMainMenu();


        this.startTime = LocalDateTime.now();
        gameMap = new Map(this.level, blocks,game,this.startTime);

    }





    private void createButtonToResume() {//boutton qui apparait dans le menu pause, et permet de reprendre
        Runnable action = () -> {
            this.isPaused = false;
            Gdx.graphics.setCursor(invisibleCursor);
            backgroundMusic.play();
        };
        game.createButton(-1, 200, 222, 39, "Resume", 10, action);
    }

    private void createButtonTryAgain() {//boutton qui apparait dans le menu pause, et permet de reprendre
        Runnable action = () -> game.setScreen(new Shooter(game, this.level));//game.setScreen(new Shooter(game, this.level))
        game.createButton(-1, 250, 222, 39, "Try Again", 10, action);
    }

    private void createButtonMainMenu() {
        Runnable action = () -> game.setScreen(new MainMenu(game));
        game.createButton(-1, 300, 222, 39, "Main menu", 10, action);
    }


    private void drawLevel() {
        BitmapFont font = game.getFont();
        font.getData().setScale(2f); // 4f
        GlyphLayout layout = new GlyphLayout(font, "Level " + this.level);
        font.draw(game.batch, layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 40);
        font.getData().setScale(1f);
    }




    public void readLevel(String nameFile) {
        FileHandle file = Gdx.files.internal(nameFile);
        if (file.exists()) {
            try (InputStream inputStream = file.read();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                int blockHeight = Block.getHeight();
                int blockWidth = Block.getWidth();
                int x = 0;
                int y = INITIAL_WINDOW_HEIGHT;

                int character;
                while ((character = reader.read()) != -1) {
                    char ch = (char) character;
                    if (ch == '\n') {
                        x = 0;
                        y -= blockHeight;
                    } else {
                        if (ch == '█') {
                            BlockInt bInt = new BlockInt(x, y);
                            blocks.add(bInt);
                        } else if(ch != ' '){
                            blocks.add(new BlockExt(x, y, ch));
                        }


                        x += blockWidth;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier n'existe pas.");
        }
    }




    public void updateBlocks() {
        for (Block block : blocks) {
            block.update();
        }
    }

    public void drawBlocks() {
        for (Block block : blocks) {
            block.draw(game.batch);
        }
    }


    private void drawBackground() {
        game.batch.draw(this.backgroundTexture, 0, 0, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    }



    private void  drawBarExt(int height, int width, int posY, int posX, int size, String color){
        game.batch.draw(texturesProgressBar.get("extGauche" + color), posX, posY, width, height);
        for(int i = 1; i < size ; i++){
            game.batch.draw(texturesProgressBar.get("extCentre" + color), posX + width*i, posY, width, height);
        }
        game.batch.draw(texturesProgressBar.get("extDroit" + color), posX + width*size, posY, width, height);
    }


    public void drawStatsPlayer(int progression, int spaceTop, String color, Texture icon) {
        int height = 30;
        int width = 20;
        int posY = INITIAL_WINDOW_HEIGHT - height - spaceTop;
        int posX = 40;
        int size = 10;
        drawBarExt(height, width, posY, posX, size, color);

        int widthInt = 20;
        if(progression >= 10){
            int progressionDraw = progression - 10;
            game.batch.draw(texturesProgressBar.get("intGauche" + color), posX, posY, 20, height);
            int i = 1;
            int widthInt2 = 10;
            while (progressionDraw >= 5) {
                i++;
                game.batch.draw(texturesProgressBar.get("intCentre" + color), posX + widthInt2*i, posY, widthInt2, height);
                progressionDraw -= 5;
            }
            if(progression == 100 ){
                game.batch.draw(texturesProgressBar.get("intDroit" + color), posX + widthInt*size, posY, widthInt, height);
            }
        }
        game.batch.draw(texturesProgressBar.get("icon" + color), posX - 20, posY - 5, 40, 40);
        game.batch.draw(icon, posX - 9, posY + 5, 18, 18);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 0);

        game.getCamera().update();
        game.batch.setProjectionMatrix(game.getCamera().combined);


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {//si appuie sur echap, met en pause
            if (!isPaused) {
                isPaused = true;
                game.setCursorVisual();
                backgroundMusic.pause();
            } else {
                isPaused = false;
                Gdx.graphics.setCursor(invisibleCursor);
                backgroundMusic.play();
            }
        }

        if (!isPaused) {//si jeu en cours
            updateBlocks();
            gameMap.update(blocks);

            for (Block block : blocks) {//vérfie si le jour touche un block
                if(block instanceof BlockExt){//lance l'opération uniquement sur les blocks exterieur
                    if (gameMap.getPlayer().collidesWith(block)) {
                        game.setScreen(new ScoreBoard(game,false, this.level, gameMap.getBossKilled()+gameMap.getEnemiesKilled(), this.startTime));
                    }
                }

            }

            if (TimeUtils.nanoTime() - lastShoot > 0.3F * 1_000_000_000) {
                gameMap.playerShoot();
                lastShoot = TimeUtils.nanoTime();
            }
        }
        game.getBatch().begin();
        drawBackground();
        drawBlocks();


        game.getShape().begin(ShapeRenderer.ShapeType.Filled);
        gameMap.draw(game.getBatch(), game.getShape());
        game.getShape().end();
        game.getBatch().end();

        game.getBatch().begin();
        this.drawStatsPlayer(gameMap.getPlayer().getHealth(), 10, "Red", iconHealth);
        if(gameMap.getPlayer().getPowerupEquipped() instanceof Shield){
            this.drawStatsPlayer(gameMap.getPlayer().getPowerupEquipped().getDuration(), 55, "Purple", iconShield);}
        game.getBatch().end();

        if (isPaused) {
            // Draw semi-transparent overlay
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            game.getShape().begin(ShapeRenderer.ShapeType.Filled);
            game.getShape().setColor(new Color(0, 0, 0, 0.5f));
            game.getShape().rect(0, 0, GameShooter.INITIAL_WINDOW_WIDTH, GameShooter.INITIAL_WINDOW_HEIGHT);
            game.getShape().end();
            Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

            game.getStage().act(Gdx.graphics.getDeltaTime());
            game.getStage().draw();

            game.getBatch().begin();
            this.drawLevel();
            game.getBatch().end();
        }
    }


    @Override
    public void dispose() {
        Gdx.app.log("Shooter", "Disposing Shooter screen");
        backgroundTexture.dispose();
        if (backgroundMusic != null) {
            Gdx.app.log("Shooter", "Disposing background music");

            backgroundMusic.dispose();
        }
        if (invisibleCursor != null) {
            invisibleCursor.dispose();
        }
        // Dispose all other resources
        //super.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}





    public static int getWindowWidth() {
        return INITIAL_WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return INITIAL_WINDOW_HEIGHT;
    }

    public static int getSizeLine() {
        return sizeLine;
    }
}
