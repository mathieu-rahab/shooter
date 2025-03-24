package com.war.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MainMenu implements Screen {
    private final GameShooter game;
    private Texture backgroundTexture;

    public MainMenu(final GameShooter game) {
        this.game = game;
    }

    @Override
    public void show() {
        initialize();
    }

    private void initialize() {
        game.getStage().clear();
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        createButtonLevels();
        createButtonClose();
        game.setCursorVisual();
    }

    //crée les 3 boutons de niveau
    private void createButtonLevels() {
        for (int i = 1; i <= 3; i++) {
            final int level = i;
            Runnable action = () -> game.setScreen(new Shooter(game, level));
            game.createButton(-1, 200 + (i - 1) * 50, 222, 39, "Level " + i, 10, action);
        }
    }

    private void createButtonClose() {
        Runnable action = () -> Gdx.app.exit();
        game.createButton(GameShooter.INITIAL_WINDOW_WIDTH - 80, GameShooter.INITIAL_WINDOW_HEIGHT, 150, 39, "Close game", 10, action);
    }

    private void drawTitle() {
        BitmapFont font = game.getFont();
        font.getData().setScale(2f); // 4f
        GlyphLayout layout = new GlyphLayout(font, "Universe Invader");
        font.draw(game.batch, layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 40);

        font.getData().setScale(1f);
        layout = new GlyphLayout(font, "by war games");
        font.draw(game.batch, layout, (GameShooter.INITIAL_WINDOW_WIDTH - layout.width) / 2, GameShooter.INITIAL_WINDOW_HEIGHT - 80);
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
        drawBackground();
        drawTitle();
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
