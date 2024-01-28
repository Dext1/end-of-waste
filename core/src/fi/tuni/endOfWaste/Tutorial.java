package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

import static fi.tuni.endOfWaste.ManagementGame.WORLD_HEIGHT;
import static fi.tuni.endOfWaste.ManagementGame.WORLD_WIDTH;

/**
 * Tutorial is class that opens dialog of tutorial pictures how to play this game.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class Tutorial extends Actor {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    /**
     * Holds the textures
     */
    private Texture texture, texture2, texture3, currentTexture;
    /**
     * Holds the textures size and position variables
     */
    private float textureHeight, textureWidth, x, y;
    /**
     * Holds the textures for buttons
     */
    private Texture next_texture, skip_texture, close_texture;
    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle style_next, style_skip, style_close;
    /**
     * Holds the menuButtons ImageButton object.
     */
    public ImageButton nextButton, skipButton, closeButton;
    /**
     * Boolean to see if any button is clicked
     */
    public boolean nextClicked, skipClicked, closeClicked;
    /**
     * Boolean to see if last tutorial screen is active
     */
    public boolean lastScreen;

    /**
     * Tutorial Constructor. Loads textures and sets base values. Calls createButtons method.
     */
    public Tutorial() {
        texture = new Texture(Gdx.files.internal(myBundle.get("firstTutorialScreen")));
        texture2 = new Texture(Gdx.files.internal(myBundle.get("secondTutorialScreen")));
        texture3 = new Texture(Gdx.files.internal(myBundle.get("thirdTutorialScreen")));

        currentTexture = texture;

        textureWidth = currentTexture.getWidth();
        textureHeight = currentTexture.getHeight();
        x = (WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
    }

    /**
     * Creates styles for buttons, creates imgaButton with that style,
     * adds listener for that button.
     */
    private void createButtons() {
        next_texture = new Texture(Gdx.files.internal(myBundle.get("next")));
        style_next = new ImageButton.ImageButtonStyle();
        style_next.up = new TextureRegionDrawable(new TextureRegion(next_texture));

        nextButton = new ImageButton(style_next);
        nextButton.setPosition(1900, 150);
        nextButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                nextClicked = true;
            }
        });

        skip_texture = new Texture(Gdx.files.internal(myBundle.get("skip")));
        style_skip = new ImageButton.ImageButtonStyle();
        style_skip.up = new TextureRegionDrawable(new TextureRegion(skip_texture));

        skipButton = new ImageButton(style_skip);
        skipButton.setPosition(1450, 150);
        skipButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                skipClicked = true;
            }
        });

        close_texture = new Texture(Gdx.files.internal(myBundle.get("closeTutorial")));
        style_close = new ImageButton.ImageButtonStyle();
        style_close.up = new TextureRegionDrawable(new TextureRegion(close_texture));

        closeButton = new ImageButton(style_close);
        closeButton.setPosition(1900, 150);
        closeButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                closeClicked = true;
            }
        });
    }

    /**
     * Actors update method. Gets called 60 times per second when actor is at stage.
     * Checks if menuButton is clicked.
     */
    @Override
    public void act(float delta) {
        if(nextClicked) {
            if(currentTexture == texture) {
                currentTexture = texture2;
                lastScreen = true;
            } else if(currentTexture == texture2) {
                currentTexture = texture3;
            }
            nextClicked = false;
        }
        if(skipClicked) {
            skipClicked = false;
        }
        if(closeClicked) {
            closeClicked = false;
        }
    }

    /**
     * Draw method.
     * @param batch takes batch as a parameter. We use same batch all around this game to draw
     *              everything.
     * @param alpha alpha value to change opacity of drawn texture.
     */
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(currentTexture, x, y, textureWidth, textureHeight);
    }

    /**
     * Disposes textures.
     */
    public void dispose() {
        texture.dispose();
        texture2.dispose();
        texture3.dispose();
        currentTexture.dispose();
        next_texture.dispose();
        skip_texture.dispose();
        close_texture.dispose();
    }
}