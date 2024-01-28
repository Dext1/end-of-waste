package fi.tuni.endOfWaste;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ExplosionAnimation is class that loads animation and plays it.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class ExplosionAnimation {
    /**
     * Holds the geometry, color, and texture information for drawing 2D sprite
     */
    private Sprite sprite;

    /**
     * Holds the texture file
     */
    private Texture wasteExplosionTexture;
    /**
     * Stores objects presenting animated sequence, example textureRegion
     */
    private Animation<TextureRegion> wasteExplosionAnimation;
    /**
     * Texture
     */
    private TextureRegion currentFrameTexture;
    /**
     * State time measures how long each picture of animation will show
     * When statetime reaches zero it will change to next picture and reset stateTime.
     */
    private float stateTime;
    /**
     * Boolean to see if animation is finished.
     */
    private boolean animationFinished;
    /**
     * X position where animation will be drawn.
     */
    private float x;
    /**
     * Y position where animation will be drawn.
     */
    private float y;
    /**
     * Width of animation in pixels.
     */
    private float width;
    /**
     * Height of animation in pixels.
     */
    private float height;

    /**
     * Boolean for playing animation, when this is true animation will start playing.
     */
    private boolean playAnimation;

    /**
     * ExplosionAnimation Constructor. Loads textures and sets base values.
     */
    public ExplosionAnimation() {
        wasteExplosionTexture = new Texture("gamescreen/hukka/explosionSheet.png");

        createAnimations();

        height = (currentFrameTexture.getRegionHeight() * 3);
        width = (currentFrameTexture.getRegionWidth() * 3);
        x = ManagementGame.WORLD_WIDTH / 2;
        y = ManagementGame.WORLD_HEIGHT / 2;
        sprite = new Sprite(currentFrameTexture);
        sprite.setSize(width,height);
        sprite.setPosition(650, 350);

        animationFinished = false;
        playAnimation = false;
    }

    /**
     * Creates textureRegion from texture file.
     */
    private void createAnimations() {
        final int FRAME_COLS = 4;
        final int FRAME_ROWS = 4;

        int tileWidth = wasteExplosionTexture.getWidth() / FRAME_COLS;
        int tileHeight = wasteExplosionTexture.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(wasteExplosionTexture, tileWidth, tileHeight);
        TextureRegion[] attackFrames = Util.toTextureArray(tmp, FRAME_COLS, FRAME_ROWS);

        wasteExplosionAnimation = new Animation(10 / 60f, (Object[])attackFrames);

        currentFrameTexture = wasteExplosionAnimation.getKeyFrame(stateTime, true);
    }

    /**
     * Update method for animation. Will update animation, and set position of it.
     */
    void update(float deltaTime) {
        x = sprite.getX();
        y = sprite.getY();
        animationHandler(deltaTime);
    }

    /**
     * Method for playing animation, you can call it from other classes to play animation.
     */
    void playAnimation() {
        playAnimation = true;
        stateTime = 0;
    }

    /**
     * Method for reseting stateTime outside of this class if needed.
     */
    void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    /**
     * Animation handler method. It will see if animation wants to be played.
     * If animation is finished, it will go out from this loop and stop playing it.
     * @param deltaTime Takes deltaTime as parameter, it will use it to increase stateTime wich
     *                  makes animation go forward.
     */
    private void animationHandler(float deltaTime) {
        if(playAnimation) {
            currentFrameTexture = wasteExplosionAnimation.getKeyFrame(stateTime, false);
            if(wasteExplosionAnimation.isAnimationFinished(stateTime)) {
                playAnimation = false;
                animationFinished = true;
            }
        }
        stateTime += deltaTime;
    }

    /**
     * Method to see if animation is finished.
     */
    boolean isAnimationFinished() {
        return animationFinished;
    }

    /**
     * Draw method.
     * @param batch takes batch as a parameter. We use same batch all around this game to draw
     *              everything.
     */
    public void draw(Batch batch){
        batch.draw(currentFrameTexture, x, y, width, height);
    }

    /**
     * Disposes texture.
     */
    public void dispose() {
        wasteExplosionTexture.dispose();
    }
}