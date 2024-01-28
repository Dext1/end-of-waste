package fi.tuni.endOfWaste;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Class that opens first when game is launched. Loads basic functionatility and changes
 * game screen to main menu.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class ManagementGame extends Game {
	/**
	 * Used to draw textures, we use same batch in all screens so we declare it here.
	 */
	public SpriteBatch batch;
	/**
	 * Stores font to use in other classes.
	 */
	public BitmapFont font;
	/**
	 * Stores font to use in other classes.
	 */
	public BitmapFont fontSmall;
	/**
	 * Stores font to use in other classes.
	 */
	public BitmapFont fontSmallest;
	/**
	 * Stores font to use in other classes.
	 */
	public BitmapFont fontRegular;
	/**
	 * Stores FitViewPort to use in other classes.
	 */
	public FitViewport viewport;

	/**
	 * Sets world width value to 2560 pixels.
	 */
	public static final int WORLD_WIDTH = 2560;
	/**
	 * Sets world width value to 1440 pixels.
	 */
	public static final int WORLD_HEIGHT = 1440;

	/**
	 * Creates fonts and batch, also sets screen to MainMenuSreen.
	 */
	@Override
	public void create () {
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
		font = new BitmapFont(Gdx.files.internal("font/roboto.fnt"));
		font.setColor(Color.BLACK);
		font.getData().setScale(0.9f);

		fontSmall = new BitmapFont(Gdx.files.internal("font/roboto.fnt"));
		fontSmall.setColor(Color.BLACK);
		fontSmall.getData().setScale(0.7f);

		fontSmallest = new BitmapFont(Gdx.files.internal("font/roboto.fnt"));
		fontSmallest.setColor(Color.BLACK);
		fontSmallest.getData().setScale(0.5f);

		fontRegular = new BitmapFont(Gdx.files.internal("font/regular/roboto.fnt"));
		fontRegular.setColor(Color.BLACK);
		fontRegular.getData().setScale(0.7f);

		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Render method, calls render from Game classes render method.
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Disposes all fonts and batch.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		fontSmall.dispose();
		fontSmallest.dispose();
		fontRegular.dispose();
	}
}
