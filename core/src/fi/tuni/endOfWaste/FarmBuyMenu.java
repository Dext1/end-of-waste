package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
/**
 * This class holds methods and variables for the farm buy menu. See MarketBuyMenu for method
 * documentation, they are omitted here for brevity.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class FarmBuyMenu extends Building {
    /**
     * Holds the textures width
     */
    private float textureWidth;
    /**
     * Holds the textures heigth
     */
    private float textureHeight;
    /**
     * Boolean to see if close button is clicked.
     */
    public boolean closeClicked;
    /**
     * Booleans to see if any some of these buttons are clicked.
     */
    public boolean button1Clicked, button2Clicked, button3Clicked, button4Clicked, clicked;

    /**
     * Holds textures of all button images.
     */
    private Texture buy_farmer, buy_farmer_pressed, buy_recognize, buy_recognize_pressed, buy_analyze
            , buy_analyze_pressed, buy_planting, buy_planting_pressed, buy_channels, buy_channels_pressed
            , buy_better_products, buy_better_products_pressed, buy_quality_control, buy_quality_control_pressed
            , buy_timing, buy_timing_pressed, buy_wormcompost, buy_wormcompost_pressed, buy_bokashi
            , buy_bokashi_pressed, buy_industrycompost, buy_industrycompost_pressed, maxLvlWide
            , closeTexture, closeTexturePressed;

    /**
     * Padding for button positioning
     */
    private float padding = 10;

    /**
     * Holds the buttons ImageButton objects.
     */
    public ImageButton buy1, buy2, buy3, buy4, close;

    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle button1_style1, button2_style1, button2_style2, button2_style3
            , button2_style4, button3_style1, button3_style2, button3_style3, button4_style1
            , button4_style2, button4_style3, maxLvlWide_style, style_close;

    /**
     * FarmBuyMenu Constructor. Loads textures and sets base values. Calls createButtons and
     * createCloseButton methods.
     */
    public FarmBuyMenu() {
        texture = new Texture(Gdx.files.internal(myBundle.get("farmBuyMenu")));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = (ManagementGame.WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (ManagementGame.WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
        createCloseButton();
    }
    /**
     * Creates styles for buttons, creates imageButton with that style,
     * adds listener for that button.
     */
    private void createButtons() {
        buy_farmer = new Texture(Gdx.files.internal(myBundle.get("buyFarmer")));
        buy_farmer_pressed = new Texture(Gdx.files.internal(myBundle.get("buyFarmerPress")));

        button1_style1 = new ImageButton.ImageButtonStyle();
        button1_style1.up = new TextureRegionDrawable(new TextureRegion(buy_farmer));
        button1_style1.down = new TextureRegionDrawable(new TextureRegion(buy_farmer_pressed));

        buy1 = new ImageButton(button1_style1);
        buy1.setPosition(180, 940);
        buy1.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button1Clicked = true;
            }
        });

        buy_recognize = new Texture(Gdx.files.internal(myBundle.get("buyRecognize")));
        buy_recognize_pressed = new Texture(Gdx.files.internal(myBundle.get("buyRecognizePress")));

        buy_analyze = new Texture(Gdx.files.internal(myBundle.get("buyFarmAnalyze")));
        buy_analyze_pressed = new Texture(Gdx.files.internal(myBundle.get("buyFarmAnalyzePress")));

        buy_planting = new Texture(Gdx.files.internal(myBundle.get("buyPlanting")));
        buy_planting_pressed = new Texture(Gdx.files.internal(myBundle.get("buyPlantingPress")));

        buy_channels = new Texture(Gdx.files.internal(myBundle.get("buyMarketing")));
        buy_channels_pressed = new Texture(Gdx.files.internal(myBundle.get("buyMarketingPress")));

        button2_style1 = new ImageButton.ImageButtonStyle();
        button2_style1.up = new TextureRegionDrawable(new TextureRegion(buy_recognize));
        button2_style1.down = new TextureRegionDrawable(new TextureRegion(buy_recognize_pressed));

        button2_style2 = new ImageButton.ImageButtonStyle();
        button2_style2.up = new TextureRegionDrawable(new TextureRegion(buy_analyze));
        button2_style2.down = new TextureRegionDrawable(new TextureRegion(buy_analyze_pressed));

        button2_style3 = new ImageButton.ImageButtonStyle();
        button2_style3.up = new TextureRegionDrawable(new TextureRegion(buy_planting));
        button2_style3.down = new TextureRegionDrawable(new TextureRegion(buy_planting_pressed));

        button2_style4 = new ImageButton.ImageButtonStyle();
        button2_style4.up = new TextureRegionDrawable(new TextureRegion(buy_channels));
        button2_style4.down = new TextureRegionDrawable(new TextureRegion(buy_channels_pressed));

        buy2 = new ImageButton(button2_style1);
        buy2.setPosition(buy1.getX(), buy1.getY() - buy_farmer.getHeight() - (padding));

        buy2.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button2Clicked = true;
            }
        });

        buy_better_products = new Texture(Gdx.files.internal(myBundle.get("buyProducts")));
        buy_better_products_pressed = new Texture(Gdx.files.internal(myBundle.get("buyProductsPress")));

        buy_quality_control = new Texture(Gdx.files.internal(myBundle.get("buyQuality")));
        buy_quality_control_pressed = new Texture(Gdx.files.internal(myBundle.get("buyQualityPress")));

        buy_timing = new Texture(Gdx.files.internal(myBundle.get("buyHarvest")));
        buy_timing_pressed = new Texture(Gdx.files.internal(myBundle.get("buyHarvestPress")));

        button3_style1 = new ImageButton.ImageButtonStyle();
        button3_style1.up = new TextureRegionDrawable(new TextureRegion(buy_better_products));
        button3_style1.down = new TextureRegionDrawable(new TextureRegion(buy_better_products_pressed));

        button3_style2 = new ImageButton.ImageButtonStyle();
        button3_style2.up = new TextureRegionDrawable(new TextureRegion(buy_quality_control));
        button3_style2.down = new TextureRegionDrawable(new TextureRegion(buy_quality_control_pressed));

        button3_style3 = new ImageButton.ImageButtonStyle();
        button3_style3.up = new TextureRegionDrawable(new TextureRegion(buy_timing));
        button3_style3.down = new TextureRegionDrawable(new TextureRegion(buy_timing_pressed));

        buy3 = new ImageButton(button3_style1);
        buy3.setPosition(buy1.getX(), buy1.getY() - (buy_farmer.getHeight() * 2) - (padding * 2));
        buy3.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button3Clicked = true;
            }
        });

        buy_wormcompost = new Texture(Gdx.files.internal(myBundle.get("buyFarmWormcompost")));
        buy_wormcompost_pressed = new Texture(Gdx.files.internal(myBundle.get("buyFarmWormcompostPress")));

        buy_bokashi = new Texture(Gdx.files.internal(myBundle.get("buyFarmBokashi")));
        buy_bokashi_pressed = new Texture(Gdx.files.internal(myBundle.get("buyFarmBokashiPress")));

        buy_industrycompost = new Texture(Gdx.files.internal(myBundle.get("buyFarmIndustryCompost")));
        buy_industrycompost_pressed = new Texture(Gdx.files.internal(myBundle.get("buyFarmIndustryCompostPress")));

        button4_style1 = new ImageButton.ImageButtonStyle();
        button4_style1.up = new TextureRegionDrawable(new TextureRegion(buy_wormcompost));
        button4_style1.down = new TextureRegionDrawable(new TextureRegion(buy_wormcompost_pressed));

        button4_style2 = new ImageButton.ImageButtonStyle();
        button4_style2.up = new TextureRegionDrawable(new TextureRegion(buy_bokashi));
        button4_style2.down = new TextureRegionDrawable(new TextureRegion(buy_bokashi_pressed));

        button4_style3 = new ImageButton.ImageButtonStyle();
        button4_style3.up = new TextureRegionDrawable(new TextureRegion(buy_industrycompost));
        button4_style3.down = new TextureRegionDrawable(new TextureRegion(buy_industrycompost_pressed));

        buy4 = new ImageButton(button4_style1);
        buy4.setPosition(buy1.getX(), buy1.getY() - (buy_farmer.getHeight() * 3) - (padding * 3));
        buy4.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button4Clicked = true;
            }
        });

        maxLvlWide = new Texture(Gdx.files.internal(myBundle.get("maxLevelWide")));

        maxLvlWide_style = new ImageButton.ImageButtonStyle();
        maxLvlWide_style.up = new TextureRegionDrawable(new TextureRegion(maxLvlWide));
        maxLvlWide_style.down = maxLvlWide_style.up;
    }
    /**
     * Creates styles for buttons, creates imageButton with that style,
     * adds listener for that button.
     */
    private void createCloseButton() {
        closeTexture = new Texture(Gdx.files.internal(myBundle.get("close")));
        closeTexturePressed = new Texture(Gdx.files.internal(myBundle.get("closePress")));

        style_close = new ImageButton.ImageButtonStyle();
        style_close.up = new TextureRegionDrawable(new TextureRegion(closeTexture));
        style_close.down = new TextureRegionDrawable(new TextureRegion(closeTexturePressed));
        close = new ImageButton(style_close);
        close.setPosition(2200, 1160);
        close.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                closeClicked = true;
            }
        });
    }
    public ImageButton.ImageButtonStyle getStyle(ImageButton button, int level, boolean maxLevel) {
        ImageButton.ImageButtonStyle style = button1_style1;
        if(button == buy2) {
            if (level == 0) {
                style = button2_style1;
            }
            if (level == 1) {
                style = button2_style2;
            }
            if (level == 2) {
                style = button2_style3;
            }
            if (level == 3) {
                style = button2_style4;
            }
            if (maxLevel) {
                style = maxLvlWide_style;
            }
        }
        if(button == buy3) {
            if (level == 0) {
                style = button3_style1;
            }
            if (level == 1) {
                style = button3_style2;
            }
            if (level == 2) {
                style = button3_style3;
            }
            if (maxLevel) {
                style = maxLvlWide_style;
            }
        }
        if(button == buy4) {
            if (level == 0) {
                style = button4_style1;
            }
            if (level == 1) {
                style = button4_style2;
            }
            if (level == 2) {
                style = button4_style3;
            }
            if (maxLevel) {
                style = maxLvlWide_style;
            }
        }
        return style;
    }

    /**
     * Actors update method. Gets called 60 times per second when actor is at stage.
     * Checks if menuButton is clicked.
     */
    @Override
    public void act(float delta) {
        if(clicked) {
            clicked = false;
        }
        if(closeClicked) {
            closeClicked = false;
        }
        if(button1Clicked) {
            button1Clicked = false;
        }
        if(button2Clicked) {
            button2Clicked = false;
        }
        if(button3Clicked) {
            button3Clicked = false;
        }
        if(button3Clicked) {
            button3Clicked = false;
        }
        if(button4Clicked) {
            button4Clicked = false;
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
        buy_farmer.dispose();
        buy_farmer_pressed.dispose();
        buy_recognize.dispose(); buy_recognize_pressed.dispose();
        buy_analyze.dispose();
        buy_analyze_pressed.dispose();
        buy_planting.dispose();
        buy_planting_pressed.dispose();
        buy_channels.dispose();
        buy_channels_pressed.dispose();
        buy_better_products.dispose();
        buy_better_products_pressed.dispose();
        buy_quality_control.dispose();
        buy_quality_control_pressed.dispose();
        buy_timing.dispose();
        buy_timing_pressed.dispose();
        buy_wormcompost.dispose();
        buy_wormcompost_pressed.dispose();
        buy_bokashi.dispose();
        buy_bokashi_pressed.dispose();
        buy_industrycompost.dispose();
        buy_industrycompost_pressed.dispose();
        maxLvlWide.dispose();
        closeTexture.dispose();
        closeTexturePressed.dispose();
    }
}
