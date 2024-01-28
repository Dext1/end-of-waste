package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Visual Meter to show how player is doing with waste. It goes full when player is losing and
 * empty when player is doing well.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class WasteMeterVisual extends Actor {
    /**
     * Holds the texture files
     */
    private Texture green, yellow, red, orange,  currentTexture, backgroundTexture;
    /**
     * Holds the textures size and position variables and totalWaste that is used to set
     * meters current size.
     */
    private float totalWaste, textureWidth, textureHeight, x, y;
    /**
     * maxWidth of texture bar.
     */
    private float maxWidth = 375;

    /**
     * WasteMeterVisual. Loads textures and sets base values.
     */
    WasteMeterVisual() {
        backgroundTexture = new Texture(Gdx.files.internal("gamescreen/hukka/wasteBar_bgr.png"));

        green = new Texture(Gdx.files.internal("gamescreen/hukka/green.png"));
        yellow = new Texture(Gdx.files.internal("gamescreen/hukka/yellow.png"));
        orange = new Texture(Gdx.files.internal("gamescreen/hukka/orange.png"));
        red = new Texture(Gdx.files.internal("gamescreen/hukka/red.png"));

        currentTexture = yellow;
        textureWidth = currentTexture.getWidth();
        textureHeight = currentTexture.getHeight() * 0.5f;

        x = 1060;
        y = 1075;
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);
    }

    /**
     * Update method for visual wasteMeter.
     * @param totalWaste uses games totalWaste to set current width of this bar.
     */
    void update(float totalWaste) {
        this.totalWaste = totalWaste;

        if(totalWaste >= GameScreen.criticalWaste) {
            textureWidth = maxWidth;
        } else {
            textureWidth = this.totalWaste / 2;
        }

        if(this.totalWaste <= 100) {
            setCurrentTexture(1);
        } else if(this.totalWaste > 100 && this.totalWaste <= 250) {
            setCurrentTexture(2);
        } else if(this.totalWaste > 250 && this.totalWaste <= 400) {
            setCurrentTexture(3);
        } else if(totalWaste > 400) {
            setCurrentTexture(4);
        }
    }

    /**
     * Method to set currentTexture.
     * @param textureId textures id 1-4. If id is 1, then it sets texture to match id 1 texture.
     * Also sets new position to match current textures size, because it's calculated from
     *                  textures width and height.
     */
    private void setCurrentTexture(int textureId) {
        if(textureId == 1) {
            currentTexture = green;
        } else if(textureId == 2) {
            currentTexture = yellow;
        } else if(textureId == 3) {
            currentTexture = orange;
        } else if(textureId == 4) {
            currentTexture = red;
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
        batch.draw(backgroundTexture, x -7.5f, y - 26, maxWidth + 12.5f, textureHeight + 25);
        batch.draw(currentTexture, x, y - 18.5f, textureWidth, textureHeight + 10);
    }
    /**
     * Disposes textures.
     */
    public void dispose() {
        green.dispose();
        yellow.dispose();
        red.dispose();
        currentTexture.dispose();
    }
}
