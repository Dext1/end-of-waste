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
 * This class holds methods and variables for the grocery store confirm dialog. Most methods here are also
 * at FarmConfirmDialog and SchoolConfirmDialog classes and aren't documented inside those classes for brevity.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class MarketConfirmDialog extends Actor {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    /**
     * Holds textures of all button images.
     */
    private Texture texture, confirm, cancel, confirm_pressed, cancel_pressed
            , logistics, analyze, poll, system
            , storage, openHours, freezer
            , charity, biowaste, biofuel;
    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle style_confirm, style_cancel;
    /**
     * Holds the buttons ImageButton objects.
     */
    public ImageButton confirmBtn, cancelBtn;
    /**
     * Booleans to see if any some of these buttons are clicked.
     */
    public boolean confirmClicked, cancelClicked;
    /**
     * Holds the textures size and position variables
     */
    private float textureWidth, textureHeight, x, y;
    /**
     * MarketConfirmDialog Constructor. Loads textures and sets base values.
     * Calls createButtons method
     */
    public MarketConfirmDialog() {
        logistics = new Texture(Gdx.files.internal(myBundle.get("storeLogisticsInfo")));
        analyze = new Texture(Gdx.files.internal(myBundle.get("storeAnalyzeInfo")));
        poll = new Texture(Gdx.files.internal(myBundle.get("storePollInfo")));
        system = new Texture(Gdx.files.internal(myBundle.get("storeSystemInfo")));
        storage = new Texture(Gdx.files.internal(myBundle.get("storeStorageInfo")));
        openHours = new Texture(Gdx.files.internal(myBundle.get("storeOpeningHoursInfo")));
        freezer = new Texture(Gdx.files.internal(myBundle.get("storeFreezerInfo")));
        charity = new Texture(Gdx.files.internal(myBundle.get("storeCharityInfo")));
        biowaste = new Texture(Gdx.files.internal(myBundle.get("storeBiowasteInfo")));
        biofuel = new Texture(Gdx.files.internal(myBundle.get("storeBiofuelInfo")));
        texture = logistics;

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = (WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
    }
    /**
     * Creates styles for buttons, creates imageButton with that style,
     * adds listener for that button.
     */
    private void createButtons() {
        confirm = new Texture(Gdx.files.internal(myBundle.get("buy")));
        confirm_pressed = new Texture(Gdx.files.internal(myBundle.get("buyPress")));

        cancel = new Texture(Gdx.files.internal(myBundle.get("close")));
        cancel_pressed = new Texture(Gdx.files.internal(myBundle.get("closePress")));

        style_confirm = new ImageButton.ImageButtonStyle();
        style_confirm.up = new TextureRegionDrawable(new TextureRegion(confirm));
        style_confirm.down = new TextureRegionDrawable(new TextureRegion(confirm_pressed));

        style_cancel = new ImageButton.ImageButtonStyle();
        style_cancel.up = new TextureRegionDrawable(new TextureRegion(cancel));
        style_cancel.down = new TextureRegionDrawable(new TextureRegion(cancel_pressed));

        confirmBtn = new ImageButton(style_confirm);
        confirmBtn.setPosition(WORLD_WIDTH/2 - (confirm.getWidth() / 2), 240);
        confirmBtn.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                confirmClicked = true;
            }
        });

        cancelBtn = new ImageButton(style_cancel);
        cancelBtn.setPosition(1680, 1110);
        cancelBtn.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                cancelClicked = true;
            }
        });
    }
    /**
     * Actors update method. Checks if any button is clicked.
     */
    @Override
    public void act(float delta) {
        if(cancelClicked) {
            cancelClicked = false;
        }
        if(confirmClicked) {
            confirmClicked = false;
        }
    }

    /**
     * Switches the image of the confirm dialog window based on the button pressed
     * @param buttonId Button identifier.
     */
    public void switchTexture(byte buttonId) {
        if(buttonId == 1) {
            texture = logistics;
        }
        if(buttonId == 2) {
            texture = analyze;
        }
        if(buttonId == 3) {
            texture = poll;
        }
        if(buttonId == 4) {
            texture = system;
        }
        if(buttonId == 5) {
            texture = storage;
        }
        if(buttonId == 6) {
            texture = openHours;
        }
        if(buttonId == 7) {
            texture = freezer;
        }
        if(buttonId == 8) {
            texture = charity;
        }
        if(buttonId == 9) {
            texture = biowaste;
        }
        if(buttonId == 10) {
            texture = biofuel;
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
        confirm.dispose();
        cancel.dispose();
        confirm_pressed.dispose();
        cancel_pressed.dispose();
        logistics.dispose();
        analyze.dispose();
        poll.dispose();
        system.dispose();
        storage.dispose();
        openHours.dispose();
        freezer.dispose();
        charity.dispose();
        biowaste.dispose();
        biofuel.dispose();
    }
}
