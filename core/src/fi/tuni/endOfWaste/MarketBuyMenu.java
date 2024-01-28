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
 * This class holds methods and variables for the grocery store buy menu. Most methods here are also
 * at FarmBuyMenu and SchoolBuyMenu classes and aren't documented inside those classes for brevity.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class MarketBuyMenu extends Building {
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
    public boolean button1Clicked, button2Clicked, button3Clicked, button4Clicked, button5Clicked, button6Clicked, clicked;

    /**
     * Holds textures of all button images.
     */
    private Texture buy_trainee, buy_worker, buy_robot, upgrade_worker, upgrade_robot,
            buy_trainee_pressed, buy_worker_pressed, buy_robot_pressed, upgrade_robot_pressed,
            upgrade_worker_pressed, upgrade_logistics, upgrade_waste_analytics, upgrade_survey,
            upgrade_system, upgrade_system_pressed, upgrade_survey_pressed,
            upgrade_waste_analytics_pressed, upgrade_logistics_pressed, upgrade_sort_storage,
            upgrade_longer_opening_hours, upgrade_cold_room, upgrade_longer_opening_hours_pressed,
            upgrade_sort_storage_pressed, upgrade_cold_room_pressed, discount, discount_pressed,
            buy_charity, buy_biowaste, buy_biofuel, buy_charity_pressed, buy_biowaste_pressed,
            buy_biofuel_pressed, maxLvl, maxLvlWide, closeTexture, closeTexturePressed;

    /**
     * Padding for button positioning
     */
    private float padding = 10;

    /**
     * Holds the buttons ImageButton objects.
     */
    public ImageButton buy1, buy2, buy3, buy4, buy5, buy6, close;

    /**
     * Holds the ImageButtonStyle style, it can be used to change buttons style to another
     */
    public ImageButton.ImageButtonStyle button2_style1, button2_style2, button3_style1, button3_style2, button3_style3, button3_style4, button4_style1, button4_style2, button4_style3, button5_style1,
    button5_style2, button5_style3, button1_style1, button1_style2, button1_style3, button6_style1, maxLvl_style, maxLvlWide_style, style_close;

    /**
     * MarketBuyMenu Constructor. Loads textures and sets base values. Calls createButtons and
     * createCloseButton methods.
     */
    public MarketBuyMenu() {
        texture = new Texture(Gdx.files.internal(myBundle.get("marketBuyMenu")));

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
        buy_trainee = new Texture(Gdx.files.internal(myBundle.get("buyTrainee")));
        buy_trainee_pressed = new Texture(Gdx.files.internal(myBundle.get("buyTraineePress")));

        buy_worker = new Texture(Gdx.files.internal(myBundle.get("buyEmployee")));
        buy_worker_pressed = new Texture(Gdx.files.internal(myBundle.get("buyEmployeePress")));

        buy_robot = new Texture(Gdx.files.internal(myBundle.get("buyRobot")));
        buy_robot_pressed = new Texture(Gdx.files.internal(myBundle.get("buyRobotPress")));

        button1_style1 = new ImageButton.ImageButtonStyle();
        button1_style1.up = new TextureRegionDrawable(new TextureRegion(buy_trainee));
        button1_style1.down = new TextureRegionDrawable(new TextureRegion(buy_trainee_pressed));

        button1_style2 = new ImageButton.ImageButtonStyle();
        button1_style2.up = new TextureRegionDrawable(new TextureRegion(buy_worker));
        button1_style2.down = new TextureRegionDrawable(new TextureRegion(buy_worker_pressed));

        button1_style3 = new ImageButton.ImageButtonStyle();
        button1_style3.up = new TextureRegionDrawable(new TextureRegion(buy_robot));
        button1_style3.down = new TextureRegionDrawable(new TextureRegion(buy_robot_pressed));

        buy1 = new ImageButton(button1_style1);
        buy1.setPosition(180, 940);
        buy1.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button1Clicked = true;
            }
        });

        upgrade_worker = new Texture(Gdx.files.internal(myBundle.get("upgradeEmployee")));
        upgrade_worker_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeEmployeePress")));
        upgrade_robot = new Texture(Gdx.files.internal(myBundle.get("upgradeRobot")));
        upgrade_robot_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeRobotPress")));

        button2_style1 = new ImageButton.ImageButtonStyle();
        button2_style1.up = new TextureRegionDrawable(new TextureRegion(upgrade_worker));
        button2_style1.down = new TextureRegionDrawable(new TextureRegion(upgrade_worker_pressed));

        button2_style2 = new ImageButton.ImageButtonStyle();
        button2_style2.up = new TextureRegionDrawable(new TextureRegion(upgrade_robot));
        button2_style2.down = new TextureRegionDrawable(new TextureRegion(upgrade_robot_pressed));

        buy2 = new ImageButton(button2_style1);
        buy2.setPosition(buy1.getX() + buy1.getWidth(), buy1.getY());

        buy2.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button2Clicked = true;
            }
        });

        upgrade_logistics = new Texture(Gdx.files.internal(myBundle.get("upgradeStoreLogistics")));
        upgrade_logistics_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeStoreLogisticsPress")));

        upgrade_waste_analytics = new Texture(Gdx.files.internal(myBundle.get("upgradeStoreWasteAnalytics")));
        upgrade_waste_analytics_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeStoreWasteAnalyticsPress")));

        upgrade_survey = new Texture(Gdx.files.internal(myBundle.get("upgradeSurvey")));
        upgrade_survey_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeSurveyPress")));

        upgrade_system = new Texture(Gdx.files.internal(myBundle.get("upgradeSystem")));
        upgrade_system_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeSystemPress")));

        button3_style1 = new ImageButton.ImageButtonStyle();
        button3_style1.up = new TextureRegionDrawable(new TextureRegion(upgrade_logistics));
        button3_style1.down = new TextureRegionDrawable(new TextureRegion(upgrade_logistics_pressed));

        button3_style2 = new ImageButton.ImageButtonStyle();
        button3_style2.up = new TextureRegionDrawable(new TextureRegion(upgrade_waste_analytics));
        button3_style2.down = new TextureRegionDrawable(new TextureRegion(upgrade_waste_analytics_pressed));

        button3_style3 = new ImageButton.ImageButtonStyle();
        button3_style3.up = new TextureRegionDrawable(new TextureRegion(upgrade_survey));
        button3_style3.down = new TextureRegionDrawable(new TextureRegion(upgrade_survey_pressed));

        button3_style4 = new ImageButton.ImageButtonStyle();
        button3_style4.up = new TextureRegionDrawable(new TextureRegion(upgrade_system));
        button3_style4.down = new TextureRegionDrawable(new TextureRegion(upgrade_system_pressed));

        buy3 = new ImageButton(button3_style1);
        buy3.setPosition(buy1.getX(), buy1.getY() - buy_trainee.getHeight() - (padding));

        buy3.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button3Clicked = true;
            }
        });

        upgrade_sort_storage = new Texture(Gdx.files.internal(myBundle.get("upgradeStorage")));
        upgrade_sort_storage_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeStoragePress")));

        upgrade_longer_opening_hours = new Texture(Gdx.files.internal(myBundle.get("upgradeOpeningHours")));
        upgrade_longer_opening_hours_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeOpeningHoursPress")));

        upgrade_cold_room = new Texture(Gdx.files.internal(myBundle.get("upgradeFreezer")));
        upgrade_cold_room_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeFreezerPress")));

        button4_style1 = new ImageButton.ImageButtonStyle();
        button4_style1.up = new TextureRegionDrawable(new TextureRegion(upgrade_sort_storage));
        button4_style1.down = new TextureRegionDrawable(new TextureRegion(upgrade_sort_storage_pressed));

        button4_style2 = new ImageButton.ImageButtonStyle();
        button4_style2.up = new TextureRegionDrawable(new TextureRegion(upgrade_longer_opening_hours));
        button4_style2.down = new TextureRegionDrawable(new TextureRegion(upgrade_longer_opening_hours_pressed));

        button4_style3 = new ImageButton.ImageButtonStyle();
        button4_style3.up = new TextureRegionDrawable(new TextureRegion(upgrade_cold_room));
        button4_style3.down = new TextureRegionDrawable(new TextureRegion(upgrade_cold_room_pressed));

        buy4 = new ImageButton(button4_style1);
        buy4.setPosition(buy1.getX(), buy1.getY() - (buy_trainee.getHeight() * 2) - (padding * 2));
        buy4.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button4Clicked = true;
            }
        });

        buy_charity = new Texture(Gdx.files.internal(myBundle.get("upgradeCharity")));
        buy_charity_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeCharityPress")));

        buy_biowaste = new Texture(Gdx.files.internal(myBundle.get("upgradeBiowaste")));
        buy_biowaste_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeBiowastePress")));

        buy_biofuel = new Texture(Gdx.files.internal(myBundle.get("upgradeBiofuel")));
        buy_biofuel_pressed = new Texture(Gdx.files.internal(myBundle.get("upgradeBiofuelPress")));

        button5_style1 = new ImageButton.ImageButtonStyle();
        button5_style1.up = new TextureRegionDrawable(new TextureRegion(buy_charity));
        button5_style1.down = new TextureRegionDrawable(new TextureRegion(buy_charity_pressed));

        button5_style2 = new ImageButton.ImageButtonStyle();
        button5_style2.up = new TextureRegionDrawable(new TextureRegion(buy_biowaste));
        button5_style2.down = new TextureRegionDrawable(new TextureRegion(buy_biowaste_pressed));

        button5_style3 = new ImageButton.ImageButtonStyle();
        button5_style3.up = new TextureRegionDrawable(new TextureRegion(buy_biofuel));
        button5_style3.down = new TextureRegionDrawable(new TextureRegion(buy_biofuel_pressed));

        buy5 = new ImageButton(button5_style1);
        buy5.setPosition(buy1.getX(), buy1.getY() - (buy_trainee.getHeight() * 3) - (padding * 3));
        buy5.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button5Clicked = true;
            }
        });

        discount = new Texture(Gdx.files.internal(myBundle.get("discount")));
        discount_pressed = new Texture(Gdx.files.internal(myBundle.get("discountPress")));

        button6_style1 = new ImageButton.ImageButtonStyle();
        button6_style1.up = new TextureRegionDrawable(new TextureRegion(discount));
        button6_style1.down = new TextureRegionDrawable(new TextureRegion(discount_pressed));

        buy6 = new ImageButton(button6_style1);
        buy6.setPosition(buy1.getX(), buy1.getY() - (buy_trainee.getHeight() * 4) - (padding * 4));
        buy6.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button6Clicked = true;
            }
        });

        maxLvl = new Texture(Gdx.files.internal(myBundle.get("maxLevel")));
        maxLvlWide = new Texture(Gdx.files.internal(myBundle.get("maxLevelWide")));

        maxLvl_style = new ImageButton.ImageButtonStyle();
        maxLvl_style.up = new TextureRegionDrawable(new TextureRegion(maxLvl));
        maxLvl_style.down = maxLvl_style.up;

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

    /**
     * Gets the current style of the specified button by checking current level of item.
     * @param button Button to be checked
     * @param level Current item level
     * @param maxLevel Is the item at max upgrade level
     * @return Current ImageButton style.
     * @author Daniel Bardo
     */
    public ImageButton.ImageButtonStyle getStyle(ImageButton button, int level, boolean maxLevel) {
        ImageButton.ImageButtonStyle style = button1_style1;
        if(button == buy1) {
            if (level == 1) {
                style = button1_style1;
            }
            if (level == 2) {
                style = button1_style2;
            }
            if (maxLevel) {
                style = button1_style3;
            }
        }
        if(button == buy2) {
            if (level == 1) {
                style = button2_style1;
            }
            if (level == 2) {
                style = button2_style2;
            }
            if (maxLevel) {
                style = maxLvl_style;
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
            if (level == 3) {
                style = button3_style4;
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
        if(button == buy5) {
            if (level == 0) {
                style = button5_style1;
            }
            if (level == 1) {
                style = button5_style2;
            }
            if (level == 2) {
                style = button5_style3;
            }
            if (maxLevel) {
                style = maxLvlWide_style;
            }
        }
        return style;
    }

    /**
     * Actors update method. Checks if any button is clicked.
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
        if(button5Clicked) {
            button5Clicked = false;
        }
        if(button6Clicked) {
            button6Clicked = false;
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
        buy_trainee.dispose();
        buy_worker.dispose();
        buy_robot.dispose();
        upgrade_worker.dispose();
        upgrade_robot.dispose();
        buy_trainee_pressed.dispose();
        buy_worker_pressed.dispose();
        buy_robot_pressed.dispose();
        upgrade_robot_pressed.dispose();
        upgrade_worker_pressed.dispose();
        upgrade_logistics.dispose();
        upgrade_waste_analytics.dispose();
        upgrade_survey.dispose();
        upgrade_system.dispose();
        upgrade_system_pressed.dispose();
        upgrade_survey_pressed.dispose();
        upgrade_waste_analytics_pressed.dispose();
        upgrade_logistics_pressed.dispose();
        upgrade_sort_storage.dispose();
        upgrade_longer_opening_hours.dispose();
        upgrade_cold_room.dispose();
        upgrade_longer_opening_hours_pressed.dispose();
        upgrade_sort_storage_pressed.dispose();
        upgrade_cold_room_pressed.dispose();
        discount.dispose();
        discount_pressed.dispose();
        buy_charity.dispose();
        buy_biowaste.dispose();
        buy_biofuel.dispose();
        buy_charity_pressed.dispose();
        buy_biowaste_pressed.dispose();
        buy_biofuel_pressed.dispose();
        maxLvl.dispose();
        maxLvlWide.dispose();
        closeTexture.dispose();
        closeTexturePressed.dispose();
    }
}
