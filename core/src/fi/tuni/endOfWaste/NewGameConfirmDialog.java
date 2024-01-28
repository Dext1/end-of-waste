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

/**
 * When player wants to start new game, This class holds functionatility to show dialogs if player
 * already had game saved to confirm that he wants to start new game.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class NewGameConfirmDialog extends Actor {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    /**
     * Holds the texture file
     */
    private Texture texture;
    /**
     * Holds the textures size and position variables
     */
    private float textureHeight, textureWidth, x, y;

    /**
     * Holds the texture files for buttons not pressed and pressed down
     */
    private Texture texture_yes, texture_no, texture_yes_grey, texture_no_grey;
    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle style_yes, style_no;
    /**
     * Holds the ImageButton objects.
     */
    public ImageButton button_yes, button_no;
    /**
     * Booleans to see if yes or no is clicked.
     */
    public boolean yesClicked, noClicked;

    /**
     * NewGameConfirmDialog Constructor. Loads textures and sets base values. Calls createButtons method.
     */
    public NewGameConfirmDialog() {
        texture = new Texture(Gdx.files.internal(myBundle.get("newGameConfirm")));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = (ManagementGame.WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (ManagementGame.WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
    }

    /**
     * Creates styles for buttons, creates imgaButton with that style,
     * adds listener for that button.
     */
    private void createButtons() {
        texture_yes = new Texture(Gdx.files.internal(myBundle.get("confirmYes")));
        texture_yes_grey = new Texture(Gdx.files.internal(myBundle.get("confirmYesPress")));

        style_yes = new ImageButton.ImageButtonStyle();
        style_yes.up = new TextureRegionDrawable(new TextureRegion(texture_yes));
        style_yes.down = new TextureRegionDrawable(new TextureRegion(texture_yes_grey));

        button_yes = new ImageButton(style_yes);
        button_yes.setPosition(810, 400);
        button_yes.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                yesClicked = true;
            }
        });

        texture_no = new Texture(Gdx.files.internal(myBundle.get("confirmNo")));
        texture_no_grey = new Texture(Gdx.files.internal(myBundle.get("confirmNoPress")));

        style_no = new ImageButton.ImageButtonStyle();
        style_no.up = new TextureRegionDrawable(new TextureRegion(texture_no));
        style_no.down = new TextureRegionDrawable(new TextureRegion(texture_no_grey));

        button_no = new ImageButton(style_no);
        button_no.setPosition(1330, 400);
        button_no.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                noClicked = true;
            }
        });
    }

    /**
     * Actors update method. Gets called 60 times per second when actor is at stage.
     * Checks if yes or no is clicked.
     */
    @Override
    public void act(float delta) {
        if(yesClicked) {
            yesClicked = false;
        }
        if(noClicked) {
            noClicked = false;
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
        batch.draw(texture, x, y, textureWidth, textureHeight);
    }

    /**
     * Disposes textures.
     */
    public void dispose() {
        texture.dispose();
        texture_yes.dispose();
        texture_no.dispose();
        texture_yes.dispose();
        texture_no.dispose();
    }
}