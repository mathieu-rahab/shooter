package com.war.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


import java.time.Duration;
import java.time.LocalDateTime;

public class ScoreBoard implements Screen {
    private final GameShooter game;
    private Texture backgroundTexture;
    private Boolean playerWin;
    private int levePlay;
    private int score;

    private String time;






    public ScoreBoard(final GameShooter game, Boolean playerWin, int levelPlay, int score, LocalDateTime startTime) {
        this.game = game;
        this.playerWin = playerWin;
        this.levePlay = levelPlay;
        this.score = score;
        this.time = this.getElapsedTime(startTime);
    }

    @Override
    public void show() {
        initialize();
    }

    private void initialize() {
        game.getStage().clear();
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));

        createButtonTryAgain();
        createButtonMainMenu();

        game.setCursorVisual();

    }

    private void createButtonMainMenu() {
        Runnable action = () -> game.setScreen(new MainMenu(game));
        game.createButton(-1, 350, 222, 39, "Main menu", 10, action);
    }

    private void createButtonTryAgain() {//boutton qui apparait dans le menu pause, et permet de reprendre
        Runnable action = () -> game.setScreen(new Shooter(game, this.levePlay));
        game.createButton(-1, 300, 222, 39, "Try Again", 10, action);
    }



    private void drawTitle() {
        BitmapFont font = game.getFont();
        font.getData().setScale(2f); // 4f
        String t;
        if(this.playerWin){
            t = "You Win";
        }
        else{
            t = "GAME OVER";
        }
        GlyphLayout layout = new GlyphLayout(font, t);
        font.draw(game.getBatch(), layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 40);
        font.getData().setScale(1f);

    }


    private void drawScore() {
        BitmapFont font = game.getFont();
        font.getData().setScale(1f); // 4f
        String t = "Enemy killed : " + this.score;
        GlyphLayout layout = new GlyphLayout(font, t);
        font.draw(game.getBatch(), layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 100);
        font.getData().setScale(1f);

    }

    private void drawTime() {
        BitmapFont font = game.getFont();
        font.getData().setScale(1f); // 4f
        GlyphLayout layout = new GlyphLayout(font, this.time);
        font.draw(game.getBatch(), layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 140);
        font.getData().setScale(1f);

    }


    public String getElapsedTime(LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        return String.format("%d : %d", minutes, seconds);
    }


    private void drawBackground() {
        game.batch.draw(backgroundTexture, 0, 0, GameShooter.INITIAL_WINDOW_WIDTH, GameShooter.INITIAL_WINDOW_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getCamera().update();

        game.getBatch().setProjectionMatrix(game.getCamera().combined);
        game.getBatch().begin();
        this.drawBackground();
        this.drawTitle();
        this.drawScore();
        this.drawTime();
        game.getBatch().end();

        game.getStage().act(Gdx.graphics.getDeltaTime());
        game.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if necessary
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
