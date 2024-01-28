package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import static fi.tuni.endOfWaste.ManagementGame.WORLD_HEIGHT;
import static fi.tuni.endOfWaste.ManagementGame.WORLD_WIDTH;

/**
 * MainMenuScreen is class that shows Main Menu.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class MainMenuScreen implements Screen {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    Preferences prefs = Gdx.app.getPreferences("Main Preferences");

    /**
     * Stores object of ManagementGame class.
     */
    private final fi.tuni.endOfWaste.ManagementGame game;

    /**
     * Stores Scene2D's stage. Stage is where all actors are put and drawn.
     */
    private Stage stage;

    /**
     * Stores texture for background.
     */
    private Texture backgroundTexture;

    /**
     * Stores texture for play button
     */
    private Texture playTexture, playPressTexture;
    /**
     * Stores texture for continue button
     */
    private Texture continueTexture, continuePressTexture;
    /**
     * Stores texture for highscore button
     */
    private Texture highscoresTexture, highscoresPressTexture;
    /**
     * Stores texture for play button
     */
    private Texture quitTexture, quitPressTexture;
    /**
     * Holds the menuButtons ImageButton object.
     */
    ImageButton play, continueButton, quitButton, highscore, languageButton;
    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    private ImageButton.ImageButtonStyle style;
    /**
     * Holds the texture for language button
     */
    private Texture language_texture;
    /**
     * Boolean to see if language button is clicked.
     */
    private boolean languageButtonClicked;
    /**
     * Stores confirmDialog object
     */
    private fi.tuni.endOfWaste.NewGameConfirmDialog confirmDialog;
    /**
     * Creates group to put confirmDialog and buttons inside of it,
     * so we can hide them same time with one command.
     */
    private Group confirmGroup;

    /**
     * MainMenuScreen Constructor. We set stage and inputprocessor for stage here.
     */
    MainMenuScreen(final ManagementGame game) {
        this.game = game;
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Scene2D's create method. Loads textures and sets values for button locations.
     * Creates styles for buttons, creates imageButton with that style,
     * Creates group for confirm dialog. Hides that dialog for further use.
     * Adds all button actors to the scene.
     */
    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal(myBundle.get("mainMenuBg")));
        Image backround = new Image(backgroundTexture);

        playTexture = new Texture(Gdx.files.internal(myBundle.get("newGame")));
        playPressTexture = new Texture(Gdx.files.internal(myBundle.get("newGamePress")));

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2 + 225, Align.center);

        continueTexture = new Texture(Gdx.files.internal(myBundle.get("continue")));
        continuePressTexture = new Texture(Gdx.files.internal(myBundle.get("continuePress")));

        continueButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(continueTexture)), new TextureRegionDrawable(new TextureRegion(continuePressTexture)));
        continueButton.setPosition(WORLD_WIDTH / 2, play.getY() - 120, Align.center);

        highscoresTexture = new Texture(Gdx.files.internal(myBundle.get("highscores")));
        highscoresPressTexture = new Texture(Gdx.files.internal(myBundle.get("highscoresPress")));

        highscore = new ImageButton(new TextureRegionDrawable(new TextureRegion(highscoresTexture)), new TextureRegionDrawable(new TextureRegion(highscoresPressTexture)));
        highscore.setPosition(WORLD_WIDTH / 2, continueButton.getY() - 120, Align.center);

        quitTexture = new Texture(Gdx.files.internal(myBundle.get("quit")));
        quitPressTexture = new Texture(Gdx.files.internal(myBundle.get("quitPress")));

        quitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(quitTexture)), new TextureRegionDrawable(new TextureRegion(quitPressTexture)));
        quitButton.setPosition(WORLD_WIDTH / 2, highscore.getY() - 120, Align.center);

        createLanguageButton();

        confirmGroup = new Group();
        confirmDialog = new NewGameConfirmDialog();

        confirmGroup.addActor(confirmDialog);
        confirmGroup.addActor(confirmDialog.button_yes);
        confirmGroup.addActor(confirmDialog.button_no);
        confirmGroup.setVisible(false);
        
        stage.addActor(backround);
        stage.addActor(play);
        stage.addActor(continueButton);
        stage.addActor(highscore);
        stage.addActor(quitButton);
        stage.addActor(confirmGroup);

        addListeners();
    }

    /**
     * Creates styles for language button, creates imageButton with that style,
     * adds listener for that button.
     */
    private void createLanguageButton() {
        language_texture = new Texture(Gdx.files.internal(myBundle.get("flag")));
        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(language_texture));

        languageButton = new ImageButton(style);
        languageButton.setPosition(200, 1200);
        languageButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                languageButtonClicked = true;
            }
        });
    }

    /**
     * Adds listeners for buttons
     */
    private void addListeners() {
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                if(prefs.getInteger("day") > 1) {
                    confirmGroup.setVisible(true);
                }
                else {
                    game.setScreen(new GameScreen(game, false));
                    dispose();
                    confirmGroup.setVisible(false);
                    stage.clear();
                }
            }
        });
        continueButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen(game, true));
                dispose();
                stage.clear();
            }
        });
        highscore.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new HighScoreScreen(game));
                dispose();
                stage.clear();
            }
        });
        quitButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                Gdx.app.exit();
            }
        });
    }

    /**
     * Render method to draw dialogs etc.
     * Clears screen, updates actors, and draws stage on screen.
     * @param delta takes delta as parameter. This is used to corrections for fps drops.
     */
    @Override
    public void render(float delta) {
        clearScreen();

        if(confirmDialog.yesClicked) {
            game.setScreen(new GameScreen(game, false));
            dispose();
            confirmGroup.setVisible(false);
            stage.clear();
        }
        if(confirmDialog.noClicked) {
            confirmGroup.setVisible(false);
        }
        if(languageButtonClicked) {
            languageButtonClicked = false;
        }

        stage.act(delta);
        stage.draw();
    }

    /**
     * Clears screen between frames.
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * When screen size changes changes viewPort to match it.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }

    /**
     * Disposes all textures.
     */
    @Override
    public void dispose() {
        stage.dispose();

        backgroundTexture.dispose();
        confirmDialog.dispose();
        playTexture.dispose();
        playPressTexture.dispose();
        continueTexture.dispose();
        continuePressTexture.dispose();
        highscoresTexture.dispose();
        highscoresPressTexture.dispose();
        quitTexture.dispose();
        quitPressTexture.dispose();
    }
}