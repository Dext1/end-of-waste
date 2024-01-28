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
 * Holds variables and methods specific to grocery store. Most methods here are also at Farm and
 * School classes and aren't documented inside those classes for brevity.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class Market extends Building {

    Preferences prefs = Gdx.app.getPreferences("Market Preferences");

    private float wastePerDay = 100f;

    private float incomePerMonth;

    private int employeeValue = 1;
    private final int employeeLvl2Value = 2;
    private final int employeeLvl3Value = 5;

    private int storageValue;
    private int displayedStorageValue = 7;
    private final int storageLvl2Value = 17;
    private final int storageLvl3Value = 33;

    private int disposalValue;
    private int displayedDisposalValue = 5;
    private final int disposalLvl2Value = 12;
    private final int disposalLvl3Value = 23;

    private int systemValue;
    private int displayedSystemValue = 8;
    private final int systemLvl2Value = 19;
    private final int systemLvl3Value = 38;
    private final int systemLvl4Value = 94;

    //Income per day
    private int  employeeIncome = 125;
    private final int employeeLvl2Income = 185;
    private final int employeeLvl3Income = 250;

    private int storageIncome;
    private int displayedStorageIncome = 1500;
    private final int storageLvl2Income = 1600;
    private final int storageLvl3Income = 2000;

    private int disposalIncome;
    private int displayedDisposalIncome = 1125;
    private final int disposalLvl2Income = 1200;
    private final int disposalLvl3Income = 1500;

    private int systemIncome;
    private int displayedSystemIncome = 1875;
    private final int systemLvl2Income = 2000;
    private final int systemLvl3Income = 2500;
    private final int systemLvl4Income = 3000;

    // Prices
    private float employeePrice = 100f;
    private float employeeUpgradePrice = 1000f;
    private final float employeeUpgrade2Price = 2000f;
    private final float employeeLvl2Price = 175f;
    private final float employeeLvl3Price = 250f;

    private float storagePrice = 400f;
    private float storageUpgradePrice = 1000f;
    private final float storageUpgrade2Price = 2000f;

    private float disposalPrice = 300f;
    private float disposalUpgradePrice = 750f;
    private final float disposalUpgrade2Price = 1500f;

    private float systemPrice = 500f;
    private float systemUpgradePrice = 1250f;
    private final static float systemUpgrade2Price = 2500f;
    private final static float systemUpgrade3Price = 6250f;

    private float discountPrice = 1000f;

    private int employeeAmount;
    private int employeeLvl = 1;
    private boolean employeeMaxLvl;

    private boolean haveStorage;
    private int storageLvl;
    private boolean storageMaxLvl;

    private boolean haveDisposal;
    private int disposalLvl;
    private boolean disposalMaxLvl;

    private boolean haveSystem;
    private int systemLvl;
    private boolean systemMaxLvl;

    private int discountBuyDay = Integer.MAX_VALUE;
    private float discountModifier = 30f;

    private int discountTime = 5;
    private int discountCooldown = 30;

    private Texture marketEvent;
    private int eventLaunchDay = Integer.MAX_VALUE;
    private int eventTime = 5;
    private float eventModifier = 30f;
    private int minTimeBetweenEvents = eventTime + 25;
    private int maxTimeBetweenEvents = eventTime + 40;

    public boolean clicked = false;

    private float textureWidth;
    private float textureHeight;

    private float wasteMultiplier;
    private int day;

    private Texture iconBg
                , employeeIcon
                , logisticsIcon
                , analyzeIcon
                , pollIcon
                , systemIcon
                , storageIcon
                , openHoursIcon
                , freezerIcon
                , charityIcon
                , biowasteIcon
                , biofuelIcon
                , discountIcon;

    public Market () {
        texture = new Texture(Gdx.files.internal("houses/market/texture_market.png"));

        marketEvent = new Texture(Gdx.files.internal(myBundle.get("marketEvent")));

        iconBg = new Texture(Gdx.files.internal("ui/iconBg.png"));
        employeeIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_shopkeeper.png"));
        logisticsIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_logistics.png"));
        analyzeIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_analyze_foodwaste.png"));
        pollIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_customer_poll.png"));
        systemIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_system.png"));
        storageIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_storage.png"));
        openHoursIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_opening_hours.png"));
        freezerIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_freezer.png"));
        charityIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_charity.png"));
        biowasteIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_biowaste.png"));
        biofuelIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_biofuel.png"));
        discountIcon = new Texture(Gdx.files.internal("houses/market/icons/icon_discount.png"));

        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        x = 740;
        y = 232;
        setBounds(x, y, textureWidth, textureHeight);
        setPosition(x, y);

        wasteRate = 100f;

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Market)event.getTarget()).clicked = true;
                return true;
            }
        });
    }

    public void update(float wasteMultiplier, int day) {
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

    /**
     * Method for purchasing the initial item. Checks for player funds and if sufficient, adds the
     * item to that building while decreasing player funds by the price of the item.
     * @param buttonId used for identifying which button was pressed.
     * @author Daniel Bardo
     */
    public void buyNewThing(byte buttonId) {
        if(buttonId == 1) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= employeePrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - employeePrice);
                employeeAmount++;
            }
        }
        if(buttonId == 2 && !haveSystem) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= systemPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - systemPrice);
                haveSystem = true;
                systemLvl = 1;
                displayedSystemIncome = systemLvl2Income;
                displayedSystemValue = systemLvl2Value;
            }
        }
        if(buttonId == 3 && !haveStorage) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= storagePrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - storagePrice);
                haveStorage = true;
                storageLvl = 1;
                displayedStorageIncome = storageLvl2Income;
                displayedStorageValue = storageLvl2Value;
            }
        }
        if(buttonId == 4 && !haveDisposal) {
            if (fi.tuni.endOfWaste.PlayerData.getPlayerMoney() >= disposalPrice) {
                fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - disposalPrice);
                haveDisposal = true;
                disposalLvl = 1;
                displayedDisposalIncome = disposalLvl2Income;
                displayedDisposalValue = disposalLvl2Value;
            }
        }
    }
    /**
     * Method for purchasing upgrades. Checks for player funds and if sufficient, upgrades the
     * item to that building while decreasing player funds by the price of the upgrade.
     * @param buttonId used for identifying which button was pressed.
     * @author Daniel Bardo
     */
    public void upgradeThing(byte buttonId) {
        if(buttonId == 1 && !employeeMaxLvl) {
            if (employeeUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(employeeLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - employeeUpgradePrice);
                    employeeUpgradePrice = employeeUpgrade2Price;
                    employeeIncome = employeeLvl2Income;
                    employeeValue = employeeLvl2Value;
                    employeePrice = employeeLvl2Price;
                    employeeLvl++;
                } else if (employeeLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - employeeUpgradePrice);
                    employeeIncome = employeeLvl3Income;
                    employeeValue = employeeLvl3Value;
                    employeePrice = employeeLvl3Price;
                    employeeMaxLvl = true;
                } else {
                }
            }
        }
        if(buttonId == 2 && !systemMaxLvl) {
            if (systemUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(systemLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - systemUpgradePrice);
                    systemUpgradePrice = systemUpgrade2Price;
                    systemValue = systemLvl2Value;
                    displayedSystemValue = systemLvl3Value;
                    systemIncome = systemLvl2Income;
                    displayedSystemIncome = systemLvl3Income;
                    systemLvl++;
                } else if (systemLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - systemUpgradePrice);
                    systemUpgradePrice = systemUpgrade3Price;
                    systemValue = systemLvl3Value;
                    displayedSystemValue = systemLvl4Value;
                    systemIncome = systemLvl3Income;
                    displayedSystemIncome = systemLvl4Income;
                    systemLvl++;
                } else if (systemLvl == 3) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - systemUpgradePrice);
                    systemValue = systemLvl4Value;
                    systemIncome = systemLvl4Income;
                    systemMaxLvl = true;
                }
            }
        }
        if(buttonId == 3 && !storageMaxLvl) {
            if (storageUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(storageLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - storageUpgradePrice);
                    storageUpgradePrice = storageUpgrade2Price;
                    storageValue = storageLvl2Value;
                    displayedStorageValue = storageLvl3Value;
                    storageIncome = storageLvl2Income;
                    displayedStorageIncome = storageLvl3Income;
                    storageLvl++;
                } else if (storageLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - storageUpgradePrice);
                    storageValue = storageLvl3Value;
                    storageIncome = storageLvl3Income;
                    storageMaxLvl = true;
                }
            }
        }
        if(buttonId == 4 && !disposalMaxLvl) {
            if (disposalUpgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                if(disposalLvl == 1) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - disposalUpgradePrice);
                    disposalUpgradePrice = disposalUpgrade2Price;
                    disposalValue = disposalLvl2Value;
                    displayedDisposalValue = disposalLvl3Value;
                    disposalIncome = disposalLvl2Income;
                    displayedDisposalIncome = disposalLvl3Income;
                    disposalLvl++;
                } else if (disposalLvl == 2) {
                    fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() - disposalUpgradePrice);
                    disposalValue = disposalLvl3Value;
                    disposalIncome = disposalLvl3Income;
                    disposalMaxLvl = true;
                }
            }
        }
    }
    /**
     * Calculates income based on upgrades bought. Multi-level single button upgrades only
     * get added to the calculation once the first level is bought.
     * @author Daniel Bardo
     */
    public void incomeCalculation () {
        if(haveSystem && systemLvl == 1) {
            systemIncome = 1875;
        }
        if(haveStorage && storageLvl == 1) {
            storageIncome = 1500;
        }
        if(haveDisposal && disposalLvl == 1) {
            disposalIncome = 1125;
        }
        incomePerMonth = employeeAmount * employeeIncome + storageIncome + systemIncome
                + disposalIncome;
    }
    /**
     * Calculates waste production based on upgrades bought. Multi-level single button upgrades only
     * get added to the calculation once the first level is bought. Temporary upgrades and events
     * add modifiers to calculation.
     * @author Daniel Bardo
     */
    public void wasteProduction() {
        if(haveSystem && systemLvl == 1) {
            systemValue = 8;
        }
        if(haveStorage && storageLvl == 1) {
            storageValue = 7;
        }
        if(haveDisposal && disposalLvl == 1) {
            disposalValue = 5;
        }
        float reduction = employeeAmount * employeeValue + storageValue + systemValue
                + disposalValue;
        if(isDiscountActive()) {
            wastePerDay = (wasteRate * wasteMultiplier) * ((100 - discountModifier)*0.01f) - reduction;
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
     * the modifier and decreased player funds by the price of the modifier. Sets discountBuyDay
     * variable to be the day modifier was activated at.
     * @author Daniel Bardo
     */
    public void buyDiscount() {
        if(!isDiscountActive() && !isDiscountOnCooldown()
                && discountPrice < fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
            fi.tuni.endOfWaste.PlayerData.setPlayerMoney(PlayerData.getPlayerMoney() - discountPrice);
            discountBuyDay = day;
        }
    }

    /**
     * Checks if modifier is currently active by checking the difference between current day and the
     * buy day of the modifier and if the time defined by the discountTime variable exceeds that.
     * @return True if discountTime exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    private boolean isDiscountActive() {
        int timeActive = day - discountBuyDay;
        return timeActive >= 0 && timeActive <= discountTime;
    }

    /**
     * Checks if modifier is currently on cooldown by checking the difference between current day and the
     * buy day of the modifier and if the time defined by the discountCooldown variable exceeds that.
     * @return True if discountCooldown exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    public boolean isDiscountOnCooldown() {
        int timeActive = day - discountBuyDay;
        return timeActive >= 0 && timeActive <= discountCooldown;
    }

    /**
     * Activates event modifier if it's not active or in a cooldown period. Allows a random grace period
     * from the start of the game where it doesn't activate. Sets eventLaunchDay variable to be the day
     * the event was activated at.
     * @author Daniel Bardo
     */
    public void launchEvent() {
        int firstTimeDelay = MathUtils.random(minTimeBetweenEvents, maxTimeBetweenEvents);
        if(!isEventActive() && !isEventOnCooldown() && day > firstTimeDelay) {
            eventLaunchDay = day;
        }
    }

    /**
     * Checks if event modifier is currently active by checking the difference between current day and the
     * launch day of the modifier and if the time defined by the eventTime variable exceeds that.
     * @return True if eventTime exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    private boolean isEventActive() {
        int timeActive = day - eventLaunchDay;
        return timeActive >= 0 && timeActive <= eventTime;
    }

    /**
     * Checks if event is currently on cooldown by checking the difference between current day and the
     * launch day of the modifier and if the time defined by the eventCooldown variable exceeds that.
     * Variable eventCooldown is randomly determined each time method is called.
     * @return True if eventCooldown exceeds or matches timeActive, false otherwise.
     * @author Daniel Bardo
     */
    private boolean isEventOnCooldown() {
        int eventCooldown = MathUtils.random(minTimeBetweenEvents, maxTimeBetweenEvents);
        int timeActive = day - eventLaunchDay;
        return timeActive >= 0 && timeActive <= eventCooldown;
    }

    public int getEmployeeAmount() {
        return employeeAmount;
    }

    public int getEmployeeValue() {
        return employeeValue;
    }

    public int getDisplayedStorageValue() {
        return displayedStorageValue;
    }

    public int getDisplayedDisposalValue() {
        return displayedDisposalValue;
    }

    public int getDisplayedSystemValue() {
        return displayedSystemValue;
    }

    public int getEmployeeIncome() {
        return employeeIncome;
    }

    public int getDisplayedStorageIncome() {
        return displayedStorageIncome;
    }

    public int getDisplayedDisposalIncome() {
        return displayedDisposalIncome;
    }

    public int getDisplayedSystemIncome() {
        return displayedSystemIncome;
    }

    public float getEmployeePrice() {
        return employeePrice;
    }

    public float getStoragePrice() {
        return storagePrice;
    }

    public float getDisposalPrice() {
        return disposalPrice;
    }

    public float getSystemPrice() {
        return systemPrice;
    }

    public float getEmployeeUpgradePrice() {
        return employeeUpgradePrice;
    }

    public float getEmployeeLvl2Price() {
        return employeeLvl2Price;
    }

    public float getEmployeeLvl3Price() {
        return employeeLvl3Price;
    }

    public float getStorageUpgradePrice() {
        return storageUpgradePrice;
    }

    public float getDisposalUpgradePrice() {
        return disposalUpgradePrice;
    }

    public float getSystemUpgradePrice() {
        return systemUpgradePrice;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public float getDiscountModifier() {
        return discountModifier;
    }

    public int getDiscountTime() {
        return discountTime;
    }

    public int getEmployeeLvl() {
        return employeeLvl;
    }

    public boolean isEmployeeMaxLvl() {
        return employeeMaxLvl;
    }

    public boolean isHaveStorage() {
        return haveStorage;
    }

    public int getStorageLvl() {
        return storageLvl;
    }

    public boolean isStorageMaxLvl() {
        return storageMaxLvl;
    }

    public boolean isHaveDisposal() {
        return haveDisposal;
    }

    public int getDisposalLvl() {
        return disposalLvl;
    }

    public boolean isDisposalMaxLvl() {
        return disposalMaxLvl;
    }

    public boolean isHaveSystem() {
        return haveSystem;
    }

    public int getSystemLvl() {
        return systemLvl;
    }

    public boolean isSystemMaxLvl() {
        return systemMaxLvl;
    }

    public float getWastePerDay() {
        return wastePerDay;
    }

    public float getIncomePerMonth() {
        return incomePerMonth;
    }

    /**
     * Writes current values to userPrefs.
     * @author Daniel Bardo
     */
    public void saveMarket() {
        prefs.putFloat("wastePerDay", wastePerDay);
        prefs.putInteger("employeeValue", employeeValue);
        prefs.putInteger("employeeIncome", employeeIncome);
        prefs.putFloat("employeePrice", employeePrice);
        prefs.putFloat("employeeUpgradePrice", employeeUpgradePrice);
        prefs.putInteger("employeeAmount", employeeAmount);
        prefs.putInteger("employeeLvl", employeeLvl);
        prefs.putBoolean("employeeMaxLvl", employeeMaxLvl);

        prefs.putInteger("systemValue", systemValue);
        prefs.putInteger("displayedSystemValue", displayedSystemValue);
        prefs.putInteger("systemIncome", systemIncome);
        prefs.putInteger("displayedSystemIncome", displayedSystemIncome);
        prefs.putFloat("systemPrice", systemPrice);
        prefs.putFloat("systemUpgradePrice", systemUpgradePrice);
        prefs.putBoolean("haveSystem", haveSystem);
        prefs.putInteger("systemLvl", systemLvl);
        prefs.putBoolean("systemMaxLvl", systemMaxLvl);

        prefs.putInteger("storageValue", storageValue);
        prefs.putInteger("displayedStorageValue", displayedStorageValue);
        prefs.putInteger("storageIncome", storageIncome);
        prefs.putInteger("displayedStorageIncome", displayedStorageIncome);
        prefs.putFloat("storagePrice", storagePrice);
        prefs.putFloat("storageUpgradePrice", storageUpgradePrice);
        prefs.putBoolean("haveStorage", haveStorage);
        prefs.putInteger("storageLvl", storageLvl);
        prefs.putBoolean("storageMaxLvl", storageMaxLvl);

        prefs.putInteger("disposalValue", disposalValue);
        prefs.putInteger("displayedDisposalValue", displayedDisposalValue);
        prefs.putInteger("disposalIncome", disposalIncome);
        prefs.putInteger("displayedDisposalIncome", displayedDisposalIncome);
        prefs.putFloat("disposalPrice", disposalPrice);
        prefs.putFloat("disposalUpgradePrice", disposalUpgradePrice);
        prefs.putBoolean("haveDisposal", haveDisposal);
        prefs.putInteger("disposalLvl", disposalLvl);
        prefs.putBoolean("disposalMaxLvl", disposalMaxLvl);

        prefs.putInteger("discountBuyDay", discountBuyDay);
        prefs.putInteger("eventLaunchDay", eventLaunchDay);
        prefs.flush();
    }

    /**
     * Reads and sets current values from userPrefs.
     * @author Daniel Bardo
     */
    public void loadMarket() {
        wastePerDay = prefs.getFloat("wastePerDay");
        employeeValue = prefs.getInteger("employeeValue");
        employeeIncome = prefs.getInteger("employeeIncome");
        employeePrice = prefs.getFloat("employeePrice");
        employeeUpgradePrice = prefs.getFloat("employeeUpgradePrice");
        employeeAmount = prefs.getInteger("employeeAmount");
        employeeLvl = prefs.getInteger("employeeLvl");
        employeeMaxLvl = prefs.getBoolean("employeeMaxLvl");
        employeeAmount = prefs.getInteger("employeeAmount");

        systemValue = prefs.getInteger("systemValue");
        displayedSystemValue = prefs.getInteger("displayedSystemValue");
        systemIncome = prefs.getInteger("systemIncome");
        displayedSystemIncome = prefs.getInteger("displayedSystemIncome");
        systemPrice = prefs.getFloat("systemPrice");
        systemUpgradePrice = prefs.getFloat("systemUpgradePrice");
        haveSystem = prefs.getBoolean("haveSystem");
        systemLvl = prefs.getInteger("systemLvl");
        systemMaxLvl = prefs.getBoolean("systemMaxLvl");

        storageValue = prefs.getInteger("storageValue");
        displayedStorageValue = prefs.getInteger("displayedStorageValue");
        storageIncome = prefs.getInteger("storageIncome");
        displayedStorageIncome = prefs.getInteger("displayedStorageIncome");
        storagePrice = prefs.getFloat("storagePrice");
        storageUpgradePrice = prefs.getFloat("storageUpgradePrice");
        haveStorage = prefs.getBoolean("haveStorage");
        storageLvl = prefs.getInteger("storageLvl");
        storageMaxLvl = prefs.getBoolean("storageMaxLvl");

        disposalValue = prefs.getInteger("disposalValue");
        displayedDisposalValue = prefs.getInteger("displayedDisposalValue");
        disposalIncome = prefs.getInteger("disposalIncome");
        displayedDisposalIncome = prefs.getInteger("displayedDisposalIncome");
        disposalPrice = prefs.getFloat("disposalPrice");
        disposalUpgradePrice = prefs.getFloat("disposalUpgradePrice");
        haveDisposal = prefs.getBoolean("haveDisposal");
        disposalLvl = prefs.getInteger("disposalLvl");
        disposalMaxLvl = prefs.getBoolean("disposalMaxLvl");

        discountBuyDay = prefs.getInteger("discountBuyDay");
        eventLaunchDay = prefs.getInteger("eventLaunchDay");
    }

    /**
     * Draws building, transparent background and bought upgrades next to building.
     * @author Daniel Bardo
     */
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, x, y, textureWidth, textureHeight);

        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.33f);
        batch.draw(iconBg, 1325, 410, getWidth()*0.41f, getHeight()*0.65f);

        c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 1.0f);

        if(isEventActive()) {
            batch.draw(marketEvent, 920, 510, getWidth() * 0.7f, getWidth() * 0.7f);
            fontRegularSmall.draw(batch, String.format("%.0f", eventModifier) + "%", 1270, 780);
        }

        batch.draw(employeeIcon, 1320, 610, getWidth()*0.09f, getWidth()*0.09f);
        if(isDiscountActive()) {
            batch.draw(discountIcon, 1600, 420, getWidth() * 0.1f, getWidth() * 0.1f);
        }
        if(haveSystem) {
                batch.draw(logisticsIcon, 1340, 520, getWidth() * 0.12f, getWidth() * 0.12f);
            if(systemLvl == 2 || systemLvl == 3 || systemMaxLvl) {
                batch.draw(analyzeIcon, 1465, 550, getWidth() * 0.06f, getWidth() * 0.06f);
            }
            if(systemLvl == 3 || systemMaxLvl) {
                batch.draw(pollIcon, 1540, 550, getWidth() * 0.06f, getWidth() * 0.06f);
            }
            if(systemMaxLvl) {
                batch.draw(systemIcon, 1610, 540, getWidth() * 0.08f, getWidth() * 0.08f);
            }
        }
        if(haveStorage) {
            batch.draw(storageIcon, 1340, 480, getWidth() * 0.06f, getWidth() * 0.06f);
            if(storageLvl == 2 || storageMaxLvl) {
                batch.draw(openHoursIcon, 1430, 480, getWidth() * 0.06f, getWidth() * 0.06f);
            }
            if(storageMaxLvl) {
                batch.draw(freezerIcon, 1510, 480, getWidth() * 0.06f, getWidth() * 0.06f);
            }
        }
        if(haveDisposal) {
            batch.draw(charityIcon, 1340, 415, getWidth() * 0.06f, getWidth() * 0.06f);
            if(disposalLvl == 2 || disposalMaxLvl) {
                batch.draw(biowasteIcon, 1420, 415, getWidth() * 0.06f, getWidth() * 0.06f);
            }
            if(disposalMaxLvl) {
                batch.draw(biofuelIcon, 1510, 415, getWidth() * 0.06f, getWidth() * 0.06f);
            }
        }
    }

    public void dispose() {
        iconBg.dispose();
        employeeIcon.dispose();
        logisticsIcon.dispose();
        analyzeIcon.dispose();
        pollIcon.dispose();
        systemIcon.dispose();
        storageIcon.dispose();
        openHoursIcon.dispose();
        freezerIcon.dispose();
        charityIcon.dispose();
        biowasteIcon.dispose();
        biofuelIcon.dispose();
        discountIcon.dispose();
        marketEvent.dispose();
    }
}
