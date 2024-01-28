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
 * Holds variables and methods specific to farm. Check Market documentation for documentation on
 * methods here.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class Farm extends Building {

    Preferences prefs = Gdx.app.getPreferences("Farm Preferences");

    private float wastePerDay = 120f;

    private float incomePerMonth;

    private int farmerValue = 5;

    private int harvestValue;
    private int displayedHarvestValue = 9;
    private final int harvestLvl2Value = 21;
    private final int harvestLvl3Value = 40;

    private int identifyValue;
    private int displayedIdentifyValue = 10;
    private final int identifyLvl2Value = 23;
    private final int identifyLvl3Value = 46;
    private final int identifyLvl4Value = 113;

    private int compostValue;
    private int displayedCompostValue = 6;
    private final int compostLvl2Value = 15;
    private final int compostLvl3Value = 28;

    private int farmerIncome = 200;

    private int harvestIncome;
    private int displayedHarvestIncome = 1500;
    private final int harvestLvl2Income = 1600;
    private final int harvestLvl3Income = 2000;

    private int identifyIncome;
    private int displayedIdentifyIncome = 1875;
    private final int identifyLvl2Income = 2000;
    private final int identifyLvl3Income = 2500;
    private final int identifyLvl4Income = 3000;

    private int compostIncome;
    private int displayedCompostIncome = 1125;
    private final int compostLvl2Income = 1200;
    private final int compostLvl3Income = 1500;

    private float farmerPrice = 250f;

    private float harvestPrice = 400f;
    private float harvestUpgradePrice = 1000f;
    private final float harvestUpgrade2Price = 2000f;

    private float identifyPrice = 500f;
    private float identifyUpgradePrice = 1250f;
    private final float identifyUpgrade2Price = 2500f;
    private final float identifyUpgrade3Price = 6250f;

    private float compostPrice = 300f;
    private float compostUpgradePrice = 750f;
    private final static float compostUpgrade2Price = 1500f;

    private int farmerAmount;

    private boolean haveHarvest;
    private byte harvestLvl;
    private boolean harvestMaxLvl;

    private boolean haveIdentify;
    private byte identifyLvl;
    private boolean identifyMaxLvl;

    private boolean haveCompost;
    private byte compostLvl;
    private boolean compostMaxLvl;

    private Texture farmEvent;
    private int eventLaunchDay = Integer.MAX_VALUE;
    private int eventTime = 5;

    private float eventModifier = 30f;
    private int minTimeBetweenEvents = eventTime + 30;
    private int maxTimeBetweenEvents = eventTime + 45;

    public boolean clicked = false;
    private float textureWidth;
    private float textureHeight;

    private float wasteMultiplier;
    private int day;

    private Texture iconBg;
    private Texture farmerIcon;
    private Texture recognizeIcon;
    private Texture analyzeIcon;
    private Texture plantingIcon;
    private Texture marketingIcon;
    private Texture productsIcon;
    private Texture qualityIcon;
    private Texture timingIcon;
    private Texture wormcompostIcon;
    private Texture bokashiIcon;
    private Texture industrycompostIcon;

    public Farm () {
        wasteRate = 120f;

        texture = new Texture(Gdx.files.internal("houses/farm/texture_farm.png"));

        farmEvent = new Texture(Gdx.files.internal(myBundle.get("farmEvent")));

        iconBg = new Texture(Gdx.files.internal("ui/iconBg.png"));
        farmerIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_farmer.png"));
        recognizeIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_recognising_methods.png"));
        analyzeIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_analyze_demand.png"));
        plantingIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_planting_planning.png"));
        marketingIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_marketing_channels.png"));
        productsIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_farming_products.png"));
        qualityIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_quality_control.png"));
        timingIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_harvest_timing.png"));
        wormcompostIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_worm_compost.png"));
        bokashiIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_bokashi.png"));
        industrycompostIcon = new Texture(Gdx.files.internal("houses/farm/icons/icon_industry_compost.png"));
        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();

        x = 100;
        y = 530;

        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Farm)event.getTarget()).clicked = true;
                return true;
            }
        });

    }


    void update(float wasteMultiplier, int day) {
        this.day = day;
        this.wasteMultiplier = wasteMultiplier;

        wasteProduction();
        incomeCalculation();
    }

    @Override
    public void act(float delta) {
        if(clicked) {
            clicked = false;
        }
    }

    void buyNewThing(byte buttonId) {
        if(buttonId == 1) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= farmerPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - farmerPrice);
                farmerAmount++;
            }
        }
        if(buttonId == 2 && !haveIdentify) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= identifyPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - identifyPrice);
                haveIdentify = true;
                identifyLvl = 1;
                displayedIdentifyIncome = identifyLvl2Income;
                displayedIdentifyValue = identifyLvl2Value;
            }
        }
        if(buttonId == 3 && !haveHarvest) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= harvestPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - harvestPrice);
                haveHarvest = true;
                harvestLvl = 1;
                displayedHarvestIncome = harvestLvl2Income;
                displayedHarvestValue = harvestLvl2Value;
            }
        }
        if(buttonId == 4 && !haveCompost) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= compostPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - compostPrice);
                haveCompost = true;
                compostLvl = 1;
                displayedCompostIncome = compostLvl2Income;
                displayedCompostValue = compostLvl2Value;
            }
        }
    }
    void upgradeThing(byte buttonId) {
        if(buttonId == 1 && !identifyMaxLvl) {
            if (identifyUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(identifyLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - identifyUpgradePrice);
                    identifyUpgradePrice = identifyUpgrade2Price;
                    identifyValue = identifyLvl2Value;
                    displayedIdentifyValue = identifyLvl3Value;
                    identifyIncome = identifyLvl2Income;
                    displayedIdentifyIncome = identifyLvl3Income;
                    identifyLvl++;
                } else if (identifyLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - identifyUpgradePrice);
                    identifyUpgradePrice = identifyUpgrade3Price;
                    identifyValue = identifyLvl3Value;
                    displayedIdentifyValue = identifyLvl4Value;
                    identifyIncome = identifyLvl3Income;
                    displayedIdentifyIncome = identifyLvl4Income;
                    identifyLvl++;
                } else if (identifyLvl == 3) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - identifyUpgradePrice);
                    identifyValue = identifyLvl4Value;
                    identifyIncome = identifyLvl4Income;
                    identifyMaxLvl = true;
                }
            }
        }
        if(buttonId == 2 && !harvestMaxLvl) {
            if (harvestUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(harvestLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - harvestUpgradePrice);
                    harvestUpgradePrice = harvestUpgrade2Price;
                    harvestValue = harvestLvl2Value;
                    displayedHarvestValue = harvestLvl3Value;
                    harvestIncome = harvestLvl2Income;
                    displayedHarvestIncome = harvestLvl3Income;
                    harvestLvl++;
                } else if (harvestLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - harvestUpgradePrice);
                    harvestValue = harvestLvl3Value;
                    harvestIncome = harvestLvl3Income;
                    harvestMaxLvl = true;
                }
            }
        }
        if(buttonId == 3 && !compostMaxLvl) {
            if (compostUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(compostLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - compostUpgradePrice);
                    compostUpgradePrice = compostUpgrade2Price;
                    compostValue = compostLvl2Value;
                    displayedCompostValue = compostLvl3Value;
                    compostIncome = compostLvl2Income;
                    displayedCompostIncome = compostLvl3Income;
                    compostLvl++;
                } else if (compostLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(PlayerData.getPlayerMoney() - compostUpgradePrice);
                    compostValue = compostLvl3Value;
                    compostIncome = compostLvl3Income;
                    compostMaxLvl = true;
                }
            }
        }
    }

    public void incomeCalculation () {
        if(haveCompost && compostLvl == 1) {
            compostIncome = 1125;
        }
        if(haveHarvest && harvestLvl == 1) {
            harvestIncome = 1500;
        }
        if(haveIdentify && identifyLvl == 1) {
            identifyIncome = 1875;
        }
        incomePerMonth = farmerAmount * farmerIncome + harvestIncome + compostIncome
                + identifyIncome;
    }

    public void wasteProduction() {
        if(haveCompost && compostLvl == 1) {
            compostValue = 6;
        }
        if(haveHarvest && harvestLvl == 1) {
            harvestValue = 9;
        }
        if(haveIdentify && identifyLvl == 1) {
            identifyValue = 10;
        }

        float reduction = farmerAmount * farmerValue + harvestValue + compostValue
                + identifyValue;

        wastePerDay = (wasteRate * wasteMultiplier) - reduction;

        if(isEventActive()) {
            wastePerDay = wastePerDay * (1 + eventModifier * 0.01f);
        }

        if(wastePerDay < 0f) {
            wastePerDay = 0f;
        }
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

    public int getFarmerAmount() {
        return farmerAmount;
    }

    public float getWastePerDay() {
        return wastePerDay;
    }

    public float getIncomePerMonth() {
        return incomePerMonth;
    }

    public int getFarmerValue() {
        return farmerValue;
    }

    public int getDisplayedHarvestValue() {
        return displayedHarvestValue;
    }

    public int getDisplayedIdentifyValue() {
        return displayedIdentifyValue;
    }

    public int getDisplayedCompostValue() {
        return displayedCompostValue;
    }

    public int getFarmerIncome() {
        return farmerIncome;
    }

    public int getDisplayedHarvestIncome() {
        return displayedHarvestIncome;
    }

    public int getDisplayedIdentifyIncome() {
        return displayedIdentifyIncome;
    }

    public int getDisplayedCompostIncome() {
        return displayedCompostIncome;
    }

    public float getFarmerPrice() {
        return farmerPrice;
    }

    public float getHarvestPrice() {
        return harvestPrice;
    }

    public float getHarvestUpgradePrice() {
        return harvestUpgradePrice;
    }

    public float getIdentifyPrice() {
        return identifyPrice;
    }

    public float getIdentifyUpgradePrice() {
        return identifyUpgradePrice;
    }

    public float getCompostPrice() {
        return compostPrice;
    }

    public float getCompostUpgradePrice() {
        return compostUpgradePrice;
    }

    public boolean isHaveHarvest() {
        return haveHarvest;
    }

    public byte getHarvestLvl() {
        return harvestLvl;
    }

    public boolean isHarvestMaxLvl() {
        return harvestMaxLvl;
    }

    public boolean isHaveIdentify() {
        return haveIdentify;
    }

    public byte getIdentifyLvl() {
        return identifyLvl;
    }

    public boolean isIdentifyMaxLvl() {
        return identifyMaxLvl;
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
    public void saveFarm() {
        prefs.putFloat("wastePerDay", wastePerDay);
        prefs.putInteger("farmerValue", farmerValue);
        prefs.putInteger("farmerIncome", farmerIncome);
        prefs.putFloat("farmerPrice", farmerPrice);
        prefs.putInteger("farmerAmount", farmerAmount);

        prefs.putInteger("identifyValue", identifyValue);
        prefs.putInteger("displayedIdentifyValue", displayedIdentifyValue);
        prefs.putInteger("identifyIncome", identifyIncome);
        prefs.putInteger("displayedIdentifyIncome", displayedIdentifyIncome);
        prefs.putFloat("identifyPrice", identifyPrice);
        prefs.putFloat("identifyUpgradePrice", identifyUpgradePrice);
        prefs.putBoolean("haveIdentify", haveIdentify);
        prefs.putInteger("identifyLvl", identifyLvl);
        prefs.putBoolean("identifyMaxLvl", identifyMaxLvl);

        prefs.putInteger("harvestValue", harvestValue);
        prefs.putInteger("displayedHarvestValue", displayedHarvestValue);
        prefs.putInteger("harvestIncome", harvestIncome);
        prefs.putInteger("displayedHarvestIncome", displayedHarvestIncome);
        prefs.putFloat("harvestPrice", harvestPrice);
        prefs.putFloat("harvestUpgradePrice", harvestUpgradePrice);
        prefs.putBoolean("haveHarvest", haveHarvest);
        prefs.putInteger("harvestLvl", harvestLvl);
        prefs.putBoolean("harvestMaxLvl", harvestMaxLvl);

        prefs.putInteger("compostValue", compostValue);
        prefs.putInteger("displayedCompostValue", displayedCompostValue);
        prefs.putInteger("compostIncome", compostIncome);
        prefs.putInteger("displayedCompostIncome", displayedCompostIncome);
        prefs.putFloat("compostPrice", compostPrice);
        prefs.putFloat("compostUpgradePrice", compostUpgradePrice);
        prefs.putBoolean("haveCompost", haveCompost);
        prefs.putInteger("compostLvl", compostLvl);
        prefs.putBoolean("compostMaxLvl", compostMaxLvl);

        prefs.putInteger("eventLaunchDay", eventLaunchDay);
        prefs.flush();
    }
    public void loadFarm() {
        wastePerDay = prefs.getFloat("wastePerDay");
        farmerValue = prefs.getInteger("farmerValue");
        farmerIncome = prefs.getInteger("farmerIncome");
        farmerPrice = prefs.getFloat("farmerPrice");
        farmerAmount = prefs.getInteger("farmerAmount");

        identifyValue = prefs.getInteger("identifyValue");
        displayedIdentifyValue = prefs.getInteger("displayedIdentifyValue");
        identifyIncome = prefs.getInteger("identifyIncome");
        displayedIdentifyIncome = prefs.getInteger("displayedIdentifyIncome");
        identifyPrice = prefs.getFloat("identifyPrice");
        identifyUpgradePrice = prefs.getFloat("identifyUpgradePrice");
        haveIdentify = prefs.getBoolean("haveIdentify");
        identifyLvl = (byte) prefs.getInteger("identifyLvl");
        identifyMaxLvl = prefs.getBoolean("identifyMaxLvl");

        harvestValue = prefs.getInteger("harvestValue");
        displayedHarvestValue = prefs.getInteger("displayedHarvestValue");
        harvestIncome = prefs.getInteger("harvestIncome");
        displayedHarvestIncome = prefs.getInteger("displayedHarvestIncome");
        harvestPrice = prefs.getFloat("harvestPrice");
        harvestUpgradePrice = prefs.getFloat("harvestUpgradePrice");
        haveHarvest = prefs.getBoolean("haveHarvest");
        harvestLvl = (byte) prefs.getInteger("harvestLvl");
        harvestMaxLvl = prefs.getBoolean("harvestMaxLvl");

        compostValue = prefs.getInteger("compostValue");
        displayedCompostValue = prefs.getInteger("displayedCompostValue");
        compostIncome = prefs.getInteger("compostIncome");
        displayedCompostIncome = prefs.getInteger("displayedCompostIncome");
        compostPrice = prefs.getFloat("compostPrice");
        compostUpgradePrice = prefs.getFloat("compostUpgradePrice");
        haveCompost = prefs.getBoolean("haveCompost");
        compostLvl = (byte) prefs.getInteger("compostLvl");
        compostMaxLvl = prefs.getBoolean("compostMaxLvl");

        eventLaunchDay = prefs.getInteger("eventLaunchDay");
    }


    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, x, y, textureWidth, textureHeight);

        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.33f);
        batch.draw(iconBg, 250, 320, getWidth()*0.50f, getHeight()*0.6f);

        c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 1.0f);

        if(isEventActive()) {
            batch.draw(farmEvent, 580, 630, getWidth() * 0.7f, getWidth() * 0.7f);
            fontRegularSmall.draw(batch, String.format("%.0f", eventModifier) + "%", 860, 855);
        }

        batch.draw(farmerIcon, 250, 560, getWidth()*0.11f, getWidth()*0.11f);
        if(haveIdentify) {
            batch.draw(recognizeIcon, 270, 480, getWidth() * 0.12f, getWidth() * 0.12f);
            if(identifyLvl == 2 || identifyLvl == 3 || identifyMaxLvl) {
                batch.draw(analyzeIcon, 370, 495, getWidth() * 0.08f, getWidth() * 0.08f);
            }
            if(identifyLvl == 3 || identifyMaxLvl) {
                batch.draw(plantingIcon, 440, 490, getWidth() * 0.1f, getWidth() * 0.1f);
            }
            if(identifyMaxLvl) {
                batch.draw(marketingIcon, 540, 495, getWidth() * 0.08f, getWidth() * 0.08f);
            }
        }
        if(haveHarvest) {
            batch.draw(productsIcon, 270, 400, getWidth() * 0.12f, getWidth() * 0.12f);
            if(harvestLvl == 2 || harvestMaxLvl) {
                batch.draw(qualityIcon, 370, 410, getWidth() * 0.08f, getWidth() * 0.08f);
            }
            if(harvestMaxLvl) {
                batch.draw(timingIcon, 440, 410, getWidth() * 0.08f, getWidth() * 0.08f);
            }
        }
        if(haveCompost) {
            batch.draw(wormcompostIcon, 270, 325, getWidth() * 0.09f, getWidth() * 0.09f);
            if(compostLvl == 2 || compostMaxLvl) {
                batch.draw(bokashiIcon, 350, 330, getWidth() * 0.08f, getWidth() * 0.08f);
            }
            if(compostMaxLvl) {
                batch.draw(industrycompostIcon, 430, 320, getWidth() * 0.1f, getWidth() * 0.1f);
            }
        }
    }
    public void dispose() {
        iconBg.dispose();
        farmerIcon.dispose();
        recognizeIcon.dispose();
        analyzeIcon.dispose();
        plantingIcon.dispose();
        marketingIcon.dispose();
        productsIcon.dispose();
        qualityIcon.dispose();
        timingIcon.dispose();
        wormcompostIcon.dispose();
        bokashiIcon.dispose();
        industrycompostIcon.dispose();
    }
}
