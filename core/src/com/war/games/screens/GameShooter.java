package com.war.games.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//class principale du jeu avec tous les attributs visuels
public class GameShooter extends Game {
    public SpriteBatch batch;
    private Cursor cursorVisual;
    private BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shape;
    private Stage stage;

    public static final int INITIAL_WINDOW_WIDTH = 840;
    public static final int INITIAL_WINDOW_HEIGHT = 473;

    public Texture visualBackButtonTextureGreen;
    public Texture visualBackButtonTextureRed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = generateFont("retro.ttf", 20);

        camera = new OrthographicCamera();
        viewport = new FitViewport(INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT, camera);
        shape = new ShapeRenderer();
        shape.setProjectionMatrix(camera.combined);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        initializeCursor();
        setCursorVisual();

        visualBackButtonTextureGreen = new Texture(Gdx.files.internal("UI/buttonGreen.png"));
        visualBackButtonTextureRed = new Texture(Gdx.files.internal("UI/buttonRed.png"));

        this.setScreen(new MainMenu(this));//défini l'affichage sur MainMenu en ce transmettant
    }

    //initialise la police d'écriture
    private BitmapFont generateFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    //crée un boutton avec image de fond, texte et écouteur d'événement
    public void createButton(int posX, int posY, int width, int height, String text, int fontSize, Runnable action) {
        if (posX == -1) { // -1 for center positioning
            posX = INITIAL_WINDOW_WIDTH/2;
        }
        ImageButton button = createImageButton(visualBackButtonTextureGreen, width, height, posX - (int)(width / 2), GameShooter.INITIAL_WINDOW_HEIGHT - posY, action);

        Label label = createLabel(text, fontSize);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
        addStackToStage(button, label);
    }

    //crée le boutton image de fond avec écouteur d'événement
    public ImageButton createImageButton(Texture texture, float width, float height, float x, float y, Runnable action) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture); // Crée un Drawable à partir de la texture fournie
        ImageButton button = new ImageButton(drawable); // Crée un bouton avec le Drawable créé
        button.setSize(width, height);
        button.setPosition(x, y);
        button.addListener(new ClickListener() { // Ajoute un écouteur de clic au bouton
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
        return button;
    }

    //crée le texte
    public Label createLabel(String text, int fontSize) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(); // Crée un style de Label
        labelStyle.font = getFont();
        labelStyle.font.getData().setScale(fontSize / 10f); // Redimensionne la police en fonction de la taille de police spécifiée
        labelStyle.fontColor = com.badlogic.gdx.graphics.Color.BLACK;
        return new Label(text, labelStyle); // Crée un Label avec le texte fourni et le style créé
    }

    //crée une pile d'élément
    public void addStackToStage(ImageButton button, Label label) {
        Stack stack = new Stack();
        stack.setSize(button.getWidth(), button.getHeight());
        stack.setPosition(button.getX(), button.getY());
        stack.add(button); // Ajoute le bouton au Stack

        Container<Label> labelContainer = new Container<>(label); // Crée un conteneur de type Container pour le Label
        labelContainer.setFillParent(true); // Remplit le conteneur parent (dans ce cas, le Stack)
        labelContainer.center(); // Centre le contenu du conteneur (dans ce cas, le Label)
        stack.add(labelContainer); // Ajoute le conteneur du Label au Stack

        stage.addActor(stack); // Ajoute le Stack à la scène
    }

    //initialise le curseur avec un visuel
    private void initializeCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("UI/cursor.png"));
        cursorVisual = Gdx.graphics.newCursor(pixmap, 0, 0);
        pixmap.dispose(); // Dispose of the pixmap after creating the cursor
    }

    public void setCursorVisual() {
        Gdx.graphics.setCursor(cursorVisual);
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

    public Stage getStage() {
        return this.stage;
    }

    public ShapeRenderer getShape() {
        return this.shape;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public Viewport getViewport() {
        return this.viewport;
    }




    @Override
    public void render() {
        super.render();
    }

    //Permets que l'écran reste aux bonnes dimensions
    @Override
    public void resize(int width, int height) {
        this.getViewport().update(width, height, true);
        this.getCamera().update();
        this.getShape().setProjectionMatrix(camera.combined);
    }





    @Override
    public void dispose() {
        batch.dispose();
        if (cursorVisual != null) {
            cursorVisual.dispose();
        }

        if (visualBackButtonTextureGreen != null) {
            visualBackButtonTextureGreen.dispose();
        }

        if (visualBackButtonTextureRed != null) {
            visualBackButtonTextureRed.dispose();
        }

        if (font != null) {
            font.dispose();
        }

        stage.dispose();
        shape.dispose();

        super.dispose();
    }
}
