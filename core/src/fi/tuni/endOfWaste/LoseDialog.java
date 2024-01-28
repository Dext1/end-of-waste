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
 * LoseDialog is class that opens dialog when game is lost.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class LoseDialog extends Actor {
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
     * Holds the texture file
     */
    private Texture backToMenu;

    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle style;
    /**
     * Holds the menuButtons ImageButton object.
     */
    public ImageButton menuButton;
    /**
     * Boolean to see if menu button is clicked.
     */
    public boolean menuClicked;

    /**
     * LoseDialog Constructor. Loads textures and sets base values. Calls createButtons method.
     */
    public LoseDialog() {
        texture = new Texture(Gdx.files.internal(myBundle.get("loseScreen")));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = (ManagementGame.WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (ManagementGame.WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
    }

    /**
     * Creates styles for buttons, creates imageButton with that style,
     * adds listener for that button.
     */
    private void createButtons() {
        backToMenu = new Texture(Gdx.files.internal(myBundle.get("backToMenu")));
        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(backToMenu));

        menuButton = new ImageButton(style);
        menuButton.setPosition(810, 300);
        menuButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                menuClicked = true;
            }
        });
    }

    /**
     * Actors update method. Gets called 60 times per second when actor is at stage.
     * Checks if menuButton is clicked.
     */
    @Override
    public void act(float delta) {
        if(menuClicked) {
            menuClicked = false;
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
     * Disposes texture.
     */
    public void dispose() {
        texture.dispose();
    }
}
