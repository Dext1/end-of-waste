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
 * This class holds methods and variables for the school buy menu. See MarketBuyMenu for method
 * documentation, they are omitted here for brevity.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class SchoolBuyMenu extends Building {
    private float textureWidth;
    private float textureHeight;
    public boolean closeClicked;

    public boolean button1Clicked, button2Clicked, button3Clicked, button4Clicked, button5Clicked, button6Clicked, clicked;

    private Texture buy_school_first_cook, buy_school_second_cook, buy_school_culinarist;
    private Texture grey_buy_school_first_cook, grey_buy_school_second_cook, grey_buy_school_culinarist;

    private Texture upgrade_school_cook , upgrade_school_culinarist;
    private Texture grey_upgrade_school_cook , grey_upgrade_school_culinarist;

    private Texture buy_school_logistics, buy_school_monitoring,buy_school_analyze_foodwaste;
    private Texture grey_buy_school_logistics, grey_buy_school_monitoring, grey_buy_school_analyze_foodwaste;

    private Texture buy_school_lunch, buy_school_raw_materials, buy_school_menu;
    private Texture grey_buy_school_lunch, grey_buy_school_raw_materials, grey_buy_school_menu;

    private Texture buy_school_enlightenment, grey_buy_school_enlightenment;

    private Texture buy_school_worm_compost, buy_school_bokashi, buy_school_industry_compost;
    private Texture grey_buy_school_worm_compost, grey_buy_school_bokashi, grey_buy_school_industry_compost;

    private Texture maxLvl, maxLvlWide;

    private Texture closeTexture, closeTexturePressed;

    private float padding = 10;

    public ImageButton buy1, buy2, buy3, buy4, buy5, buy6, close;

    public ImageButton.ImageButtonStyle
            button1_style1, button1_style2, button1_style3,
            button2_style1, button2_style2,
            button3_style1, button3_style2, button3_style3,
            button4_style1, button4_style2, button4_style3,
            button5_style1,
            button6_style1, button6_style2, button6_style3,
            maxLvl_style, maxLvlWide_style, style_close;

    public SchoolBuyMenu() {
        texture = new Texture(Gdx.files.internal(myBundle.get("schoolBuyMenu")));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = (ManagementGame.WORLD_WIDTH / 2) - (textureWidth / 2);
        y = (ManagementGame.WORLD_HEIGHT / 2) - (textureHeight / 2);
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        createButtons();
        createCloseButton();
    }
    private void createButtons() {
        buy_school_first_cook = new Texture(Gdx.files.internal(myBundle.get("buyFirstCook")));
        grey_buy_school_first_cook = new Texture(Gdx.files.internal(myBundle.get("buyFirstCookPress")));

        buy_school_second_cook = new Texture(Gdx.files.internal(myBundle.get("buySecondCook")));
        grey_buy_school_second_cook = new Texture(Gdx.files.internal(myBundle.get("buySecondCookPress")));

        buy_school_culinarist = new Texture(Gdx.files.internal(myBundle.get("buyCulinarist")));
        grey_buy_school_culinarist = new Texture(Gdx.files.internal(myBundle.get("buyCulinaristPress")));

        button1_style1 = new ImageButton.ImageButtonStyle();
        button1_style1.up = new TextureRegionDrawable(new TextureRegion(buy_school_first_cook));
        button1_style1.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_first_cook));

        button1_style2 = new ImageButton.ImageButtonStyle();
        button1_style2.up = new TextureRegionDrawable(new TextureRegion(buy_school_second_cook));
        button1_style2.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_second_cook));

        button1_style3 = new ImageButton.ImageButtonStyle();
        button1_style3.up = new TextureRegionDrawable(new TextureRegion(buy_school_culinarist));
        button1_style3.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_culinarist));

        buy1 = new ImageButton(button1_style1);
        buy1.setPosition(180, 940);
        buy1.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button1Clicked = true;
            }
        });

        upgrade_school_cook = new Texture(Gdx.files.internal(myBundle.get("upgradeCook")));
        grey_upgrade_school_cook = new Texture(Gdx.files.internal(myBundle.get("upgradeCookPress")));

        upgrade_school_culinarist = new Texture(Gdx.files.internal(myBundle.get("upgradeCulinarist")));
        grey_upgrade_school_culinarist = new Texture(Gdx.files.internal(myBundle.get("upgradeCulinaristPress")));

        button2_style1 = new ImageButton.ImageButtonStyle();
        button2_style1.up = new TextureRegionDrawable(new TextureRegion(upgrade_school_cook));
        button2_style1.down = new TextureRegionDrawable(new TextureRegion(grey_upgrade_school_cook));

        button2_style2 = new ImageButton.ImageButtonStyle();
        button2_style2.up = new TextureRegionDrawable(new TextureRegion(upgrade_school_culinarist));
        button2_style2.down = new TextureRegionDrawable(new TextureRegion(grey_upgrade_school_culinarist));

        buy2 = new ImageButton(button2_style1);
        buy2.setPosition(buy1.getX() + buy1.getWidth(), buy1.getY());

        buy2.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button2Clicked = true;
            }
        });

        buy_school_logistics = new Texture(Gdx.files.internal(myBundle.get("buySchoolLogistics")));
        grey_buy_school_logistics = new Texture(Gdx.files.internal(myBundle.get("buySchoolLogisticsPress")));

        buy_school_monitoring = new Texture(Gdx.files.internal(myBundle.get("buySchoolMonitoring")));
        grey_buy_school_monitoring = new Texture(Gdx.files.internal(myBundle.get("buySchoolMonitoringPress")));

        buy_school_analyze_foodwaste = new Texture(Gdx.files.internal(myBundle.get("buySchoolAnalyze")));
        grey_buy_school_analyze_foodwaste = new Texture(Gdx.files.internal(myBundle.get("buySchoolAnalyzePress")));

        button3_style1 = new ImageButton.ImageButtonStyle();
        button3_style1.up = new TextureRegionDrawable(new TextureRegion(buy_school_logistics));
        button3_style1.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_logistics));

        button3_style2 = new ImageButton.ImageButtonStyle();
        button3_style2.up = new TextureRegionDrawable(new TextureRegion(buy_school_monitoring));
        button3_style2.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_monitoring));

        button3_style3 = new ImageButton.ImageButtonStyle();
        button3_style3.up = new TextureRegionDrawable(new TextureRegion(buy_school_analyze_foodwaste));
        button3_style3.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_analyze_foodwaste));

        buy3 = new ImageButton(button3_style1);
        buy3.setPosition(buy1.getX(), buy1.getY() - buy_school_first_cook.getHeight() - (padding));

        buy3.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button3Clicked = true;
            }
        });

        buy_school_lunch = new Texture(Gdx.files.internal(myBundle.get("buyLunch")));
        grey_buy_school_lunch = new Texture(Gdx.files.internal(myBundle.get("buyLunchPress")));

        buy_school_raw_materials = new Texture(Gdx.files.internal(myBundle.get("buyIngredients")));
        grey_buy_school_raw_materials = new Texture(Gdx.files.internal(myBundle.get("buyIngredientsPress")));

        buy_school_menu = new Texture(Gdx.files.internal(myBundle.get("buySchoolMenu")));
        grey_buy_school_menu = new Texture(Gdx.files.internal(myBundle.get("buySchoolMenuPress")));

        button4_style1 = new ImageButton.ImageButtonStyle();
        button4_style1.up = new TextureRegionDrawable(new TextureRegion(buy_school_lunch));
        button4_style1.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_lunch));

        button4_style2 = new ImageButton.ImageButtonStyle();
        button4_style2.up = new TextureRegionDrawable(new TextureRegion(buy_school_raw_materials));
        button4_style2.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_raw_materials));

        button4_style3 = new ImageButton.ImageButtonStyle();
        button4_style3.up = new TextureRegionDrawable(new TextureRegion(buy_school_menu));
        button4_style3.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_menu));

        buy4 = new ImageButton(button4_style1);
        buy4.setPosition(buy1.getX(), buy1.getY() - (buy_school_first_cook.getHeight() * 2) - (padding * 2));
        buy4.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button4Clicked = true;
            }
        });

        buy_school_enlightenment = new Texture(Gdx.files.internal(myBundle.get("buyEnlighten")));
        grey_buy_school_enlightenment = new Texture(Gdx.files.internal(myBundle.get("buyEnlightenPress")));

        button5_style1 = new ImageButton.ImageButtonStyle();
        button5_style1.up = new TextureRegionDrawable(new TextureRegion(buy_school_enlightenment));
        button5_style1.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_enlightenment));

        buy5 = new ImageButton(button5_style1);
        buy5.setPosition(buy1.getX(), buy1.getY() - (buy_school_first_cook.getHeight() * 3) - (padding * 3));
        buy5.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                button5Clicked = true;
            }
        });

        buy_school_worm_compost = new Texture(Gdx.files.internal(myBundle.get("buySchoolWormcompost")));
        grey_buy_school_worm_compost = new Texture(Gdx.files.internal(myBundle.get("buySchoolWormcompostPress")));

        buy_school_bokashi = new Texture(Gdx.files.internal(myBundle.get("buySchoolBokashi")));
        grey_buy_school_bokashi = new Texture(Gdx.files.internal(myBundle.get("buySchoolBokashiPress")));

        buy_school_industry_compost = new Texture(Gdx.files.internal(myBundle.get("buySchoolIndustryCompost")));
        grey_buy_school_industry_compost = new Texture(Gdx.files.internal(myBundle.get("buySchoolIndustryCompostPress")));

        button6_style1 = new ImageButton.ImageButtonStyle();
        button6_style1.up = new TextureRegionDrawable(new TextureRegion(buy_school_worm_compost));
        button6_style1.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_worm_compost));

        button6_style2 = new ImageButton.ImageButtonStyle();
        button6_style2.up = new TextureRegionDrawable(new TextureRegion(buy_school_bokashi));
        button6_style2.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_bokashi));

        button6_style3 = new ImageButton.ImageButtonStyle();
        button6_style3.up = new TextureRegionDrawable(new TextureRegion(buy_school_industry_compost));
        button6_style3.down = new TextureRegionDrawable(new TextureRegion(grey_buy_school_industry_compost));

        buy6 = new ImageButton(button6_style1);
        buy6.setPosition(buy1.getX(), buy1.getY() - (buy_school_first_cook.getHeight() * 4) - (padding * 4));
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
        if(button == buy6) {
            if (level == 0) {
                style = button6_style1;
            }
            if (level == 1) {
                style = button6_style2;
            }
            if (level == 2) {
                style = button6_style3;
            }
            if (maxLevel) {
                style = maxLvlWide_style;
            }
        }
        return style;
    }

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

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, x, y, textureWidth, textureHeight);
    }

    public void dispose() {
        buy_school_first_cook.dispose();
        buy_school_second_cook.dispose();
        buy_school_culinarist.dispose();
        grey_buy_school_first_cook.dispose();
        grey_buy_school_second_cook.dispose();
        grey_buy_school_culinarist.dispose();
        upgrade_school_cook.dispose();
        upgrade_school_culinarist.dispose();
        grey_upgrade_school_cook.dispose();
        grey_upgrade_school_culinarist.dispose();
        buy_school_logistics.dispose();
        buy_school_monitoring.dispose();
        buy_school_analyze_foodwaste.dispose();
        grey_buy_school_logistics.dispose();
        grey_buy_school_monitoring.dispose();
        grey_buy_school_analyze_foodwaste.dispose();
        buy_school_lunch.dispose();
        buy_school_raw_materials.dispose();
        buy_school_menu.dispose();
        grey_buy_school_lunch.dispose();
        grey_buy_school_raw_materials.dispose();
        grey_buy_school_menu.dispose();
        buy_school_enlightenment.dispose();
        grey_buy_school_enlightenment.dispose();
        buy_school_worm_compost.dispose();
        buy_school_bokashi.dispose();
        buy_school_industry_compost.dispose();
        grey_buy_school_worm_compost.dispose();
        grey_buy_school_bokashi.dispose();
        grey_buy_school_industry_compost.dispose();
        maxLvl.dispose();
        maxLvlWide.dispose();
        closeTexture.dispose();
        closeTexturePressed.dispose();
    }
}
