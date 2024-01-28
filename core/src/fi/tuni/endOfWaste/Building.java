package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Holds common building attributes and variables.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class Building extends Actor {

    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);
    public Texture texture;
    public float x, y;

    public float wasteRate;
    public BitmapFont fontRegularSmall;

    public Building() {
        fontRegularSmall = new BitmapFont(Gdx.files.internal("font/regular/roboto.fnt"));
        fontRegularSmall.setColor(Color.BLACK);
        fontRegularSmall.getData().setScale(0.4f);
    }
}