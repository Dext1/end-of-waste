package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.List;
/**
 * HighScoreScreen is class that shows highscores.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class HighScoreScreen implements Screen, HighScoreListener {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    /**
     * Stores object of ManagementGame class.
     */
    private final ManagementGame game;

    /**
     * Stores Scene2D's stage. Stage is where all actors are put and drawn.
     */
    private Stage stage;

    /**
     * Stores texture for background.
     */
    private Texture backgroundTexture;
    /**
     * Stores texture for quitbutton.
     */
    private Texture quitTexture;
    /**
     * Stores texture for quitbutton when pressed.
     */
    private Texture quitPressTexture;
    /**
     * Holds the menuButtons ImageButton object.
     */
    ImageButton quitButton;
    /**
     * Holds Scene2D's skin files
     */
    private Skin skin;
    /**
     * Table to put all score labels inside it. They are much easier to position when inside table.
     */
    private Table content;

    /**
     * HighScoreScreen Constructor. We set stage and inputprocessor for stage here.
     */
    HighScoreScreen(final ManagementGame game) {
        this.game = game;
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Gets called from show method
     * Reads config file for highscoreserver to use and updates drawn highscores.
     * calls otherSetup method
     */
    public void create () {
        HighScoreServer.readConfig("highscore/highscore.config");
        HighScoreServer.fetchHighScores(this);
        otherSetup();
    }

    /**
     * Sets skin for scene2d skin functions, creates table for score labels, adds content actor to
     * stage and positions it.
     */
    private void otherSetup() {
        skin = new Skin();
        skin = new Skin (Gdx.files.internal("ui/uiskin.json"));
        skin.get(Label.LabelStyle.class).font = game.font;

        content = new Table();
        createTable();
        stage.addActor(content);
        content.setPosition(0, -115);
    }

    /**
     * Scene2D's create method. Loads textures and sets values for button locations etc.
     * Adds background and quitbutton to the screen. Calls create method. Calls addListeners method.
     */
    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal(myBundle.get("highscoreBg")));
        Image backround = new Image(backgroundTexture);

        quitTexture = new Texture(Gdx.files.internal(myBundle.get("quit")));
        quitPressTexture = new Texture(Gdx.files.internal(myBundle.get("quitPress")));

        quitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(quitTexture)), new TextureRegionDrawable(new TextureRegion(quitPressTexture)));
        quitButton.setPosition(2220, 175, Align.center);
        quitButton.setHeight(100);

        stage.addActor(backround);
        create();
        stage.addActor(quitButton);

        addListeners();
    }
    /**
     * Adds listener for quit button. When tapped, it will put back to Main menu screen,
     * dispose current screen and clear stage from actors etc.
     */
    private void addListeners() {
        quitButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenuScreen(game));
                dispose();
                stage.clear();
            }
        });
    }

    /**
     * Render method
     * Clears screen, updates actors, and draws stage on screen.
     * @param delta takes delta as parameter. This is used to corrections for fps drops.
     */
    @Override
    public void render(float delta) {
        clearScreen();
        stage.act(delta);
        stage.draw();
    }

    /**
     * ArrayList to hold scores.
     */
    private ArrayList<Label> scoreLabels;

    /**
     * Method to update scores.
     * @param scores Updates HighScoreEntry list and puts new entry in there.
     */
    private void updateScores(List<HighScoreEntry> scores) {
        int i = 0;
        for (HighScoreEntry e : scores) {
            String entry = e.getScore() + " - " + e.getName();
            scoreLabels.get(i).setText(entry);
            i++;
        }
    }

    /**
     * Create table to hold scoreLabels, position scores properly on screen.
     */
    private void createTable() {
        content.setFillParent(true);

        scoreLabels = new ArrayList<>();

        for (int n = 0; n < 11; n++) {
            content.row();
            Label l = new Label("", skin);
            content.add(l).colspan(3).padTop(13);
            scoreLabels.add(l);
        }
    }

    @Override
    public void receiveHighScore(List<HighScoreEntry> highScores) {
        updateScores(highScores);
    }

    @Override
    public void receiveSendReply(Net.HttpResponse httpResponse) {
        HighScoreServer.fetchHighScores(this);
    }

    @Override
    public void failedToRetrieveHighScores(Throwable t) {
    }

    @Override
    public void failedToSendHighScore(Throwable t) {
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
        backgroundTexture.dispose();
        skin.dispose();

        quitTexture.dispose();
        quitPressTexture.dispose();
        stage.dispose();
    }
}