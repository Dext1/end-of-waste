package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Methods and attributes needed to draw and modify the wolf at the top of the screen.
 * @author Aleksi Asikainen
 */
public class WasteMeter extends Actor {
    /**
     * Holds the texture files
     */
    private Texture hukka_small, hukka_medium, hukka_large, hukka_largest, currentTexture;
    /**
     * Holds the textures size and position variables
     */
    private float textureWidth, textureHeight, x, y;

    /**
     * WasteMeter Constructor. Loads textures and sets base values.
     */
    public WasteMeter() {
        hukka_small = new Texture(Gdx.files.internal("gamescreen/hukka/hukka_thin.png"));
        hukka_medium = new Texture(Gdx.files.internal("gamescreen/hukka/hukka_not_too_fat.png"));
        hukka_large = new Texture(Gdx.files.internal("gamescreen/hukka/hukka_getting_fat.png"));
        hukka_largest = new Texture(Gdx.files.internal("gamescreen/hukka/hukka_unhealthy_fat.png"));

        currentTexture = hukka_medium;
        textureWidth = currentTexture.getWidth()*1.6f;
        textureHeight = currentTexture.getHeight()*1.6f;

        x = ManagementGame.WORLD_WIDTH / 2 - (textureWidth / 2);
        y = ManagementGame.WORLD_HEIGHT - textureHeight;
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);
    }

    /**
     * Method to set currentTexture.
     * @param textureId textures id 1-4. If id is 1, then it sets texture to match id 1 texture.
     * Also sets new position to match current textures size, because it's calculated from
     *                  textures width and height.
     */
    public void setCurrentTexture(int textureId) {
        if(textureId == 1) {
            currentTexture = hukka_small;
        } else if(textureId == 2) {
            currentTexture = hukka_medium;
        } else if(textureId == 3) {
            currentTexture = hukka_large;
        } else if(textureId == 4) {
            currentTexture = hukka_largest;
        }

        textureWidth = currentTexture.getWidth()*1.6f;
        textureHeight = currentTexture.getHeight()*1.6f;
        x = ManagementGame.WORLD_WIDTH / 2 - (textureWidth / 2);
        y = ManagementGame.WORLD_HEIGHT - textureHeight;
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);
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
        hukka_small.dispose();
        hukka_medium.dispose();
        hukka_large.dispose();
        hukka_largest.dispose();
        currentTexture.dispose();
    }
}
