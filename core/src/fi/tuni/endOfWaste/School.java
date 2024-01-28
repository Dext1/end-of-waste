package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
/**
 * Holds variables and methods specific to school. Check Market documentation for documentation on
 * methods here.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class School extends Building {
    Preferences prefs = Gdx.app.getPreferences("School Preferences");

    private float wastePerDay = 80f;

    private int cookValue = 1;
    private final int cookLvl2Value = 2;
    private final int cookLvl3Value = 5;

    private int logisticsValue;
    private int displayedLogisticsValue = 8;
    private final int logisticsLvl2Value = 19;
    private final int logisticsLvl3Value = 38;

    private int menuValue;
    private int displayedMenuValue = 7;
    private final int menuLvl2Value = 17;
    private final int menuLvl3Value = 33;

    private int compostValue;
    private int displayedCompostValue = 5;
    private final int compostLvl2Value = 12;
    private final int compostLvl3Value = 23;

    private float cookPrice = 50f;
    private float cookUpgradePrice = 500f;
    private final float cookUpgrade2Price = 1000f;
    private final float cookLvl2Price = 85f;
    private final float cookLvl3Price = 125f;

    private float logisticsPrice = 250f;
    private float logisticsUpgradePrice = 625f;
    private final float logisticsUpgrade2Price = 1250f;

    private float menuPrice = 200f;
    private float menuUpgradePrice = 500f;
    private final float menuUpgrade2Price = 1000f;

    private float compostPrice = 150f;
    private float compostUpgradePrice = 375f;
    private final static float compostUpgrade2Price = 750f;

    private float enlightenPrice = 500f;

    private int cookAmount;
    private byte cookLvl = 1;
    private boolean cookMaxLvl;

    private boolean haveLogistics;
    private byte logisticsLvl;
    private boolean logisticsMaxLvl;

    private boolean haveMenu;
    private byte menuLvl;
    private boolean menuMaxLvl;

    private boolean haveCompost;
    private byte compostLvl;
    private boolean compostMaxLvl;

    private static int enlightenBuyDay = Integer.MAX_VALUE;
    private float enlightenModifier = 25f;

    private int enlightenTime = 5;
    private int enlightenCooldown = 30;

    private Texture schoolEvent;
    private int eventLaunchDay = Integer.MAX_VALUE;
    private int eventTime = 1;
    private float eventModifier = 30f;
    private int minTimeBetweenEvents = eventTime + 25;
    private int maxTimeBetweenEvents = eventTime + 40;

    public boolean clicked = false;
    private float textureWidth;
    private float textureHeight;

    private float wasteMultiplier;
    private int day;

    private Texture iconBg;
    private Texture cookIcon;
    private Texture logisticsIcon;
    private Texture monitoringIcon;
    private Texture analyzeIcon;
    private Texture lunchIcon;
    private Texture ingredientsIcon;
    private Texture menuIcon;
    private Texture wormcompostIcon;
    private Texture bokashiIcon;
    private Texture industrycompostIcon;
    private Texture enlighteningIcon;

    public School () {
        wasteRate = 80f;

        texture = new Texture(Gdx.files.internal("houses/school/texture_school.png"));

        schoolEvent = new Texture(Gdx.files.internal(myBundle.get("schoolEvent")));

        iconBg = new Texture(Gdx.files.internal("ui/iconBg.png"));
        cookIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_cook.png"));
        logisticsIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_logistics.png"));
        monitoringIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_monitoring.png"));
        analyzeIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_analyze_foodwaste.png"));
        lunchIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_lunch.png"));
        ingredientsIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_ingredients.png"));
        menuIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_menu.png"));
        wormcompostIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_worm_compost.png"));
        bokashiIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_bokashi.png"));
        industrycompostIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_industry_compost.png"));
        enlighteningIcon = new Texture(Gdx.files.internal("houses/school/icons/icon_enlightenment.png"));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();

        x = 1660;
        y = 650;

        setBounds(x, y, texture.getWidth(), texture.getHeight());
        setPosition(x, y);

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((School)event.getTarget()).clicked = true;
                return true;
            }
        });
    }

    public void update(float wasteMultiplier, int day) {
        this.day = day;
        this.wasteMultiplier = wasteMultiplier;

        wasteProduction();
    }

    @Override
    public void act(float delta) {
        if(clicked) {
            clicked = false;
        }
    }

    public void buyNewThing(byte buttonId) {
        if(buttonId == 1) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= cookPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - cookPrice);
                cookAmount++;
            }
        }
        if(buttonId == 2 && !haveLogistics) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= logisticsPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - logisticsPrice);
                haveLogistics = true;
                logisticsLvl = 1;
                displayedLogisticsValue = logisticsLvl2Value;
            }
        }
        if(buttonId == 3 && !haveMenu) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= menuPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - menuPrice);
                haveMenu = true;
                menuLvl = 1;
                displayedMenuValue = menuLvl2Value;
            }
        }
        if(buttonId == 4 && !haveCompost) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= compostPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - compostPrice);
                haveCompost = true;
                compostLvl = 1;
            }
        }
    }
    public void upgradeThing(byte buttonId) {
        if(buttonId == 1 && !cookMaxLvl) {
            if (cookUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(cookLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - cookUpgradePrice);
                    cookUpgradePrice = cookUpgrade2Price;
                    cookValue = cookLvl2Value;
                    cookPrice = cookLvl2Price;
                    cookLvl++;
                } else if (cookLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - cookUpgradePrice);
                    cookValue = cookLvl3Value;
                    cookPrice = cookLvl3Price;
                    cookMaxLvl = true;
                } else {
                }
            }
        }
        if(buttonId == 2 && !logisticsMaxLvl) {
            if (logisticsUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(logisticsLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - logisticsUpgradePrice);
                    logisticsUpgradePrice = logisticsUpgrade2Price;
                    logisticsValue = logisticsLvl2Value;
                    displayedLogisticsValue = logisticsLvl3Value;
                    logisticsLvl++;
                } else if (logisticsLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - logisticsUpgradePrice);
                    logisticsValue = logisticsLvl3Value;
                    logisticsMaxLvl = true;
                }
            }
        }
        if(buttonId == 3 && !menuMaxLvl) {
            if (menuUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(menuLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - menuUpgradePrice);
                    menuUpgradePrice = menuUpgrade2Price;
                    menuValue = menuLvl2Value;
                    displayedMenuValue = menuLvl3Value;
                    menuLvl++;
                } else if (menuLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - menuUpgradePrice);
                    menuValue = menuLvl3Value;
                    menuMaxLvl = true;
                }
            }
        }
        if(buttonId == 4 && !compostMaxLvl) {
            if (compostUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(compostLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - compostUpgradePrice);
                    compostUpgradePrice = compostUpgrade2Price;
                    compostValue = compostLvl2Value;
                    displayedCompostValue = compostLvl3Value;
                    compostLvl++;
                } else if (compostLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - compostUpgradePrice);
                    compostValue = compostLvl3Value;
                    compostMaxLvl = true;
                }
            }
        }
    }

    public void wasteProduction() {
        if(haveCompost && compostLvl == 1) {
            compostValue = displayedCompostValue;
        }
        if(haveLogistics && logisticsLvl == 1) {
            logisticsValue = displayedLogisticsValue;
        }
        if(haveMenu && menuLvl == 1) {
            menuValue = displayedMenuValue;
        }

        float reduction = cookAmount * cookValue + logisticsValue + compostValue
                + menuValue;

        if(isEnlightenActive()) {
            wastePerDay = (wasteRate * wasteMultiplier) * ((100 - enlightenModifier)*0.01f) - reduction;
        }
        else {
            wastePerDay = (wasteRate * wasteMultiplier) - reduction;
        }
        if(isEventActive()) {
            wastePerDay = wastePerDay * (1 + eventModifier * 0.01f);
        }
        if(wastePerDay < 0f) {
            wastePerDay = 0f;
        }
    }

    /**
     * Method for buying a temporary waste modifier. Checks player money and if sufficient, activates
     * the modifier and decreased player funds by the price of the modifier. Sets enlightenBuyDay
     * variable to be the day modifier was activated at.
     * @author Daniel Bardo
     */
    public void buyEnlightening() {

        if(!isEnlightenActive() && !isEnlightenOnCooldown()
                && enlightenPrice < fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
            fi.tuni.endOfWaste.PlayerData.setPlayerMoney(PlayerData.getPlayerMoney() - enlightenPrice);
            enlightenBuyDay = day;
        }
    }

    /**
     * Checks if modifier is currently active by checking the difference between current day and the
     * buy day of the modifier and if the time defined by the enlightenTime variable exceeds that.
     * @return True if enlightenTime exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    private boolean isEnlightenActive() {
        int timeActive = day - enlightenBuyDay;
        return timeActive >= 0 && timeActive <= enlightenTime;
    }

    /**
     * Checks if modifier is currently on cooldown by checking the difference between current day and the
     * buy day of the modifier and if the time defined by the enlightenCooldown variable exceeds that.
     * @return True if enlightenCooldown exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    public boolean isEnlightenOnCooldown() {
        int timeActive = day - enlightenBuyDay;
        return timeActive >= 0 && timeActive <= enlightenCooldown;
    }

    public void launchEvent() {
        int firstTimeDelay = MathUtils.random(minTimeBetweenEvents, maxTimeBetweenEvents);
        if(!isEventActive() && !isEventOnCooldown() && day > firstTimeDelay) {
            eventLaunchDay = day;
        }
    }

    private boolean isEventActive() {
        int timeActive = day - eventLaunchDay;
        return timeActive >= 0 && timeActive <= eventTime;
    }

    private boolean isEventOnCooldown() {
        int eventCooldown = MathUtils.random(minTimeBetweenEvents, maxTimeBetweenEvents);
        int timeActive = day - eventLaunchDay;
        return timeActive >= 0 && timeActive <= eventCooldown;
    }

    public float getWastePerDay() {
        return wastePerDay;
    }

    public int getCookValue() {
        return cookValue;
    }

    public int getDisplayedLogisticsValue() {
        return displayedLogisticsValue;
    }

    public int getDisplayedMenuValue() {
        return displayedMenuValue;
    }

    public int getDisplayedCompostValue() {
        return displayedCompostValue;
    }

    public float getCookPrice() {
        return cookPrice;
    }

    public float getCookUpgradePrice() {
        return cookUpgradePrice;
    }

    public float getCookLvl2Price() {
        return cookLvl2Price;
    }

    public float getCookLvl3Price() {
        return cookLvl3Price;
    }

    public float getLogisticsPrice() {
        return logisticsPrice;
    }

    public float getLogisticsUpgradePrice() {
        return logisticsUpgradePrice;
    }

    public float getMenuPrice() {
        return menuPrice;
    }

    public float getMenuUpgradePrice() {
        return menuUpgradePrice;
    }

    public float getCompostPrice() {
        return compostPrice;
    }

    public float getCompostUpgradePrice() {
        return compostUpgradePrice;
    }

    public float getEnlightenPrice() {
        return enlightenPrice;
    }

    public byte getCookLvl() {
        return cookLvl;
    }

    public boolean isCookMaxLvl() {
        return cookMaxLvl;
    }

    public boolean isHaveLogistics() {
        return haveLogistics;
    }

    public byte getLogisticsLvl() {
        return logisticsLvl;
    }

    public boolean isLogisticsMaxLvl() {
        return logisticsMaxLvl;
    }

    public boolean isHaveMenu() {
        return haveMenu;
    }

    public byte getMenuLvl() {
        return menuLvl;
    }

    public boolean isMenuMaxLvl() {
        return menuMaxLvl;
    }

    public boolean isHaveCompost() {
        return haveCompost;
    }

    public byte getCompostLvl() {
        return compostLvl;
    }

    public boolean isCompostMaxLvl() {
        return compostMaxLvl;
    }

    public float getEnlightenModifier() {
        return enlightenModifier;
    }

    public int getEnlightenTime() {
        return enlightenTime;
    }

    public int getCookAmount() {
        return cookAmount;
    }

    public void saveSchool() {
        prefs.putFloat("wastePerDay", wastePerDay);
        prefs.putInteger("cookValue", cookValue);
        prefs.putFloat("cookPrice", cookPrice);
        prefs.putFloat("cookUpgradePrice", cookUpgradePrice);
        prefs.putInteger("cookAmount", cookAmount);
        prefs.putInteger("cookLvl", cookLvl);
        prefs.putBoolean("cookMaxLvl", cookMaxLvl);

        prefs.putInteger("logisticsValue", logisticsValue);
        prefs.putInteger("displayedLogisticsValue", displayedLogisticsValue);
        prefs.putFloat("logisticsPrice", logisticsPrice);
        prefs.putFloat("logisticsUpgradePrice", logisticsUpgradePrice);
        prefs.putBoolean("haveLogistics", haveLogistics);
        prefs.putInteger("logisticsLvl", logisticsLvl);
        prefs.putBoolean("logisticsMaxLvl", logisticsMaxLvl);

        prefs.putInteger("menuValue", menuValue);
        prefs.putInteger("displayedMenuValue", displayedMenuValue);
        prefs.putFloat("menuPrice", menuPrice);
        prefs.putFloat("menuUpgradePrice", menuUpgradePrice);
        prefs.putBoolean("haveMenu", haveMenu);
        prefs.putInteger("menuLvl", menuLvl);
        prefs.putBoolean("menuMaxLvl", menuMaxLvl);

        prefs.putInteger("compostValue", compostValue);
        prefs.putInteger("displayedCompostValue", displayedCompostValue);
        prefs.putFloat("compostPrice", compostPrice);
        prefs.putFloat("compostUpgradePrice", compostUpgradePrice);
        prefs.putBoolean("haveCompost", haveCompost);
        prefs.putInteger("compostLvl", compostLvl);
        prefs.putBoolean("compostMaxLvl", compostMaxLvl);

        prefs.putInteger("enlightenBuyDay", enlightenBuyDay);
        prefs.putInteger("eventLaunchDay", eventLaunchDay);
        prefs.flush();
    }
    public void loadSchool() {
        wastePerDay = prefs.getFloat("wastePerDay");
        cookValue = prefs.getInteger("cookValue");
        cookPrice = prefs.getFloat("cookPrice");
        cookUpgradePrice = prefs.getFloat("cookUpgradePrice");
        cookAmount = prefs.getInteger("cookAmount");
        cookLvl = (byte) prefs.getInteger("cookLvl");
        cookMaxLvl = prefs.getBoolean("cookMaxLvl");
        cookAmount = prefs.getInteger("cookAmount");

        logisticsValue = prefs.getInteger("logisticsValue");
        displayedLogisticsValue = prefs.getInteger("displayedLogisticsValue");
        logisticsPrice = prefs.getFloat("logisticsPrice");
        logisticsUpgradePrice = prefs.getFloat("logisticsUpgradePrice");
        haveLogistics = prefs.getBoolean("haveLogistics");
        logisticsLvl = (byte) prefs.getInteger("logisticsLvl");
        logisticsMaxLvl = prefs.getBoolean("logisticsMaxLvl");

        menuValue = prefs.getInteger("menuValue");
        displayedMenuValue = prefs.getInteger("displayedMenuValue");
        menuPrice = prefs.getFloat("menuPrice");
        menuUpgradePrice = prefs.getFloat("menuUpgradePrice");
        haveMenu = prefs.getBoolean("haveMenu");
        menuLvl = (byte) prefs.getInteger("menuLvl");
        menuMaxLvl = prefs.getBoolean("menuMaxLvl");

        compostValue = prefs.getInteger("compostValue");
        displayedCompostValue = prefs.getInteger("displayedCompostValue");
        compostPrice = prefs.getFloat("compostPrice");
        compostUpgradePrice = prefs.getFloat("compostUpgradePrice");
        haveCompost = prefs.getBoolean("haveCompost");
        compostLvl = (byte) prefs.getInteger("compostLvl");
        compostMaxLvl = prefs.getBoolean("compostMaxLvl");

        enlightenBuyDay = prefs.getInteger("enlightenBuyDay");
        eventLaunchDay = prefs.getInteger("eventLaunchDay");
    }
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, x, y, textureWidth, textureHeight);

        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.33f);
        batch.draw(iconBg, 1915, 450, getWidth()*0.50f, getHeight()*0.6f);

        c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 1.0f);

        if(isEventActive()) {
            batch.draw(schoolEvent, 1600, 710, getWidth() * 0.7f, getWidth() * 0.7f);
            fontRegularSmall.draw(batch, String.format("%.0f", eventModifier) + "%", 1895, 950);
        }

        batch.draw(cookIcon, 1910, 650, getWidth()*0.11f, getWidth()*0.11f);
        if(isEnlightenActive()) {
            batch.draw(enlighteningIcon, 2170, 450, getWidth() * 0.15f, getWidth() * 0.15f);
        }
        if(haveLogistics) {
            batch.draw(logisticsIcon, 1930, 560, getWidth() * 0.14f, getWidth() * 0.14f);
            if(logisticsLvl == 2 || logisticsMaxLvl) {
                batch.draw(monitoringIcon, 2050, 573, getWidth() * 0.11f, getWidth() * 0.11f);
            }
            if(logisticsMaxLvl) {
                batch.draw(analyzeIcon, 2150, 590, getWidth() * 0.07f, getWidth() * 0.07f);
            }
        }
        if(haveMenu) {
            batch.draw(lunchIcon, 1930, 520, getWidth() * 0.09f, getWidth() * 0.09f);
            if(menuLvl == 2 || menuMaxLvl) {
                batch.draw(ingredientsIcon, 2020, 522, getWidth() * 0.08f, getWidth() * 0.08f);
            }
            if(menuMaxLvl) {
                batch.draw(menuIcon, 2090, 525, getWidth() * 0.07f, getWidth() * 0.07f);
            }
        }
        if(haveCompost) {
            batch.draw(wormcompostIcon, 1920, 455, getWidth() * 0.07f, getWidth() * 0.07f);
            if(compostLvl == 2 || compostMaxLvl) {
                batch.draw(bokashiIcon, 1980, 455, getWidth() * 0.07f, getWidth() * 0.07f);
            }
            if(compostMaxLvl) {
                batch.draw(industrycompostIcon, 2050, 445, getWidth() * 0.09f, getWidth() * 0.09f);
            }
        }
    }
    public void dispose() {
        iconBg.dispose();
        cookIcon.dispose();
        logisticsIcon.dispose();
        monitoringIcon.dispose();
        analyzeIcon.dispose();
        lunchIcon.dispose();
        ingredientsIcon.dispose();
        menuIcon.dispose();
        wormcompostIcon.dispose();
        bokashiIcon.dispose();
        industrycompostIcon.dispose();
        enlighteningIcon.dispose();
    }
}
