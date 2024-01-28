package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.List;

import static fi.tuni.endOfWaste.ManagementGame.WORLD_HEIGHT;
import static fi.tuni.endOfWaste.ManagementGame.WORLD_WIDTH;

/**
 * Main class responsible for controlling most of the gameflow.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class GameScreen implements Screen, HighScoreListener, Input.TextInputListener {
    FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle);

    Preferences prefs = Gdx.app.getPreferences("Main Preferences");

    /**
     * Amount of waste that player can get when game is set to end.
     */
    public final static int criticalWaste = 500;

    /**
     * Stores object of ManagementGame class.
     */
    private final fi.tuni.endOfWaste.ManagementGame game;

    /**
     * Stores Scene2D's stage. Stage is where all actors are put and drawn.
     */
    public Stage stage;

    /**
     * Stores background textures
     */
    private Texture background1, background2, currentBackground, topRightBg, marketWasteBg,
            marketIncomeBg, farmWasteBg, farmIncomeBg, schoolWasteBg, totalWasteMeterBg, soundOff,
            soundOn, exitDoor;

    /**
     * Stores Image for exit to main menu
     */
    private Image exitDoorButton;

    /**
     * School object
     */
    private fi.tuni.endOfWaste.School school;
    /**
     * Farm object
     */
    private fi.tuni.endOfWaste.Farm farm;
    /**
     * Market object
     */
    private fi.tuni.endOfWaste.Market market;
    /**
     * WasteMeter object
     */
    private fi.tuni.endOfWaste.WasteMeter wasteMeter;
    /**
     * Visual Wastebar object
     */
    private fi.tuni.endOfWaste.WasteMeterVisual wasteBar;


    private float wasteMultiplier = 1;

    private float difficulty = 0.0015f;

    private float totalIncomePerDay;

    private float totalWaste = 1;


    private final float dayLength = 3.0f;
    private float timeSeconds = 0f;

    /**
     * Current day ingame, used to keep track of what day it is
     */
    private int day = 1;
    /**
     * We keep track when month changes, so we can easily make monthly method calls.
     */
    private int daysToSwitchMonth = 30;

    /**
     * Boolean to see if any menu is open
     */
    private boolean menuOpen;
    /**
     * Boolean to see if market menu is open
     */
    private boolean marketMenuOpen;
    /**
     * Boolean to see if farm menu is open
     */
    private boolean farmMenuOpen;
    /**
     * Boolean to see if school menu is open
     */
    private boolean schoolMenuOpen;
    /**
     * Boolean to see if any dialog is open
     */
    private boolean dialogOpen;

    private byte marketButtonId;
    private byte farmButtonId;
    private byte schoolButtonId;

    /**
     * Object for marketBuyMenu
     */
    private fi.tuni.endOfWaste.MarketBuyMenu marketBuyMenu;
    /**
     * Group to have marketBuyMenu's content inside of it
     */
    private Group marketMenuGroup;
    /**
     * Object for farmBuyMenu
     */
    private fi.tuni.endOfWaste.FarmBuyMenu farmBuyMenu;
    /**
     * Group to have farmBuyMenu's content inside of it
     */
    private Group farmMenuGroup;
    /**
     * Object for schoolBuyMenu
     */
    private fi.tuni.endOfWaste.SchoolBuyMenu schoolBuyMenu;
    /**
     * Group to have schoolMenuGroup's content inside of it
     */
    private Group schoolMenuGroup;

    /**
     * Object for market purchase confirm dialog
     */
    private fi.tuni.endOfWaste.MarketConfirmDialog marketDialog;
    /**
     * Group to have market purchase confirm dialog content inside of it
     */
    private Group marketDialogGroup;
    /**
     * Object for farm purchase confirm dialog
     */
    private FarmConfirmDialog farmDialog;
    /**
     * Group to have farm purchase confirm dialog content inside of it
     */
    private Group farmDialogGroup;
    /**
     * Object for school purchase confirm dialog
     */
    private fi.tuni.endOfWaste.SchoolConfirmDialog schoolDialog;
    /**
     * Group to have school purchase confirm dialog content inside of it
     */
    private Group schoolDialogGroup;

    /**
     * Group to have winning Screens content inside of it
     */
    private Group winGroup;
    /**
     * Group to have losing Screens content inside of it
     */
    private Group loseGroup;

    /**
     * Object for WinDialog class
     */
    private fi.tuni.endOfWaste.WinDialog winScreen;
    /**
     * Object for LoseDialog class
     */
    private fi.tuni.endOfWaste.LoseDialog loseScreen;
    /**
     * Boolean to see if game is running or game is won
     */
    private boolean gameRunning, gameWon;
    /**
     * Int to keep track of players highscore
     */
    private int highscore;
    /**
     * Timer for losing screens next dialogue
     */
    private float timer = 4f;
    /**
     * Group to have losing Screens content inside of it
     */
    private Group tutorialGroup;
    /**
     * Object to store Tutorial classes object
     */
    private Tutorial tutorial;
    /**
     * Boolean to see if we want to show tutorial to player.
     */
    private boolean enableTutorial = true;

    /**
     * Object for ExplosionAnimation classes explosion
     */
    private fi.tuni.endOfWaste.ExplosionAnimation hukkaExplosion;

    /**
     * We store players name in this
     */
    public String name;

    /**
     * If player has entered name, then we can continue sending highscore
     */
    private boolean nameEntered = false;

    /**
     * When we want to save players highscore we have this set to true.
     */
    private boolean saveHighScore = true;

    /**
     * Stores sound buttons imagebutton object.
     */
    private ImageButton soundButton;

    /**
     * Stores sound buttons style for imagebutton
     */
    private ImageButton.ImageButtonStyle style_on, style_off;

    /**
     * Stores sound buttons textures
     */
    private Texture sound_texture_on, sound_texture_off;

    /**
     * Game music object
     */
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
    /**
     * Music volume variable
     */
    private float musicVolume = 0.5f;

    GameScreen(final ManagementGame game, boolean hasContinue) {

        school = new School();
        farm = new Farm();
        market = new Market();

        fi.tuni.endOfWaste.PlayerData.setPlayerMoney(300);

        if(hasContinue) {
            loadSave();
            enableTutorial=false;
        }
        this.game = game;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Scene2D create method
     * Calls all create methods and starts playing music
     */
    @Override
    public void show() {
        createUiElements();
        createExitDoorButton();
        createHouses();
        createWasteMeter();
        createSoundButton();
        createBuyMenu();
        createDialog();
        createWinScreen();
        createLoseScreen();
        createTutorial();
        createAnimation();
        resetGame();
        music.play();
        music.setLooping(true);
        music.setVolume(musicVolume);
    }

    /**
     * Game main render method
     */
    @Override
    public void render(float delta) {
        clearScreen();

        update(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(currentBackground, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        stage.getBatch().draw(topRightBg, 1580, 1310, topRightBg.getWidth() * 1.5f, topRightBg.getHeight());
        stage.getBatch().end();

        if(market.isDiscountOnCooldown()) {
            marketBuyMenu.buy6.setVisible(false);
        } else {
            marketBuyMenu.buy6.setVisible(true);
        }
        if(school.isEnlightenOnCooldown()) {
            schoolBuyMenu.buy5.setVisible(false);
        } else {
            schoolBuyMenu.buy5.setVisible(true);
        }

        if(!gameRunning) {
            marketMenuGroup.setVisible(false);
            farmMenuGroup.setVisible(false);
            schoolMenuGroup.setVisible(false);
        }

        stage.draw();

        if(!menuOpen) {
            stage.getBatch().begin();
            stage.getBatch().draw(marketWasteBg, 900, 250, marketWasteBg.getWidth()*0.9f, marketWasteBg.getHeight()*0.9f);
            stage.getBatch().draw(marketIncomeBg, 900, 145, marketIncomeBg.getWidth()*0.9f, marketIncomeBg.getHeight()*0.9f);
            stage.getBatch().draw(farmWasteBg, 130, 1050, farmWasteBg.getWidth()*0.9f, farmWasteBg.getHeight()*0.9f);
            stage.getBatch().draw(farmIncomeBg, 130, 945, farmIncomeBg.getWidth()*0.9f, farmIncomeBg.getHeight()*0.9f);
            stage.getBatch().draw(schoolWasteBg, 1830, 1150, schoolWasteBg.getWidth()*0.9f, schoolWasteBg.getHeight()*0.9f);
            stage.getBatch().draw(totalWasteMeterBg, 1055, 1100, totalWasteMeterBg.getWidth()*0.6f, totalWasteMeterBg.getHeight()*0.9f);
            stage.getBatch().end();
        }

        drawTexts();

        stage.getBatch().begin();
        if(!hukkaExplosion.isAnimationFinished()) {
            hukkaExplosion.draw(stage.getBatch());
        }
        stage.getBatch().end();
    }

    /**
     * Game main update method
     * updates actors, keeps track if game is won or lost.
     */
    private void update(float delta) {
        if(enableTutorial) {
            menuOpen = true;
            tutorialGroup.setVisible(true);
            if(tutorial.skipClicked || tutorial.closeClicked) {
                enableTutorial = false;
                gameRunning = true;
                menuOpen = false;
                tutorialGroup.remove();
            }
            if(tutorial.nextClicked && tutorial.lastScreen) {
                tutorial.nextButton.remove();
                tutorial.skipButton.remove();
                tutorialGroup.addActor(tutorial.closeButton);
            }
            stage.act(delta);
        } else if(gameRunning) {
            dayCounter(delta);

            market.update(wasteMultiplier, day);
            school.update(wasteMultiplier, day);
            farm.update(wasteMultiplier, day);

            buttonActionListener();

            calculateTotalWaste();
            wasteBar.update(totalWaste);
            setWasteMeterSize();

            if(totalWaste >= criticalWaste) {
                loseGame();
            } else if(totalWaste <= 0f) {
                winGame();
            }

            stage.act(delta);
        } else {
            if(gameWon) {
                winListener();
                if(name == null || name.length() < 1) {
                    nameEntered = false;
                }
                if(nameEntered && saveHighScore) {
                    HighScoreEntry scoreEntry = new HighScoreEntry(name, highscore);
                    HighScoreServer.sendNewHighScore(scoreEntry, this);
                    saveHighScore = false;
                    nameEntered = false;
                }
            } else {
                hukkaExplosion.update(delta);
                loseListener();

                if(hukkaExplosion.isAnimationFinished()) {
                    currentBackground = background2;
                }

                timer-=delta;
                if(timer <= 0) {
                    loseGroup.setVisible(true);
                    hukkaExplosion.setStateTime(0f);
                }
            }
            stage.act(delta);
        }
    }

    /**
     * Creates textures for backgrounds
     */
    private void createUiElements() {
        background1 = new Texture(Gdx.files.internal("gamescreen/game_background.png"));
        background2 = new Texture(Gdx.files.internal("gamescreen/background_waste.png"));
        currentBackground = background1;

        soundOn = new Texture(Gdx.files.internal("gamescreen/soundOn.png"));
        soundOff = new Texture(Gdx.files.internal("gamescreen/soundOff.png"));
        exitDoor = new Texture(Gdx.files.internal("gamescreen/door.png"));
        topRightBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        totalWasteMeterBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        marketWasteBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        marketIncomeBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        farmWasteBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        farmIncomeBg = new Texture(Gdx.files.internal("ui/long_box.png"));
        schoolWasteBg = new Texture(Gdx.files.internal("ui/long_box.png"));
    }

    /**
     * Creates button for exiting to main menu, adds listener to it.
     */
    private void createExitDoorButton() {
        exitDoorButton = new Image(exitDoor);
        exitDoorButton.setPosition(20, 1310);
        exitDoorButton.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                saveGame();
                game.setScreen(new fi.tuni.endOfWaste.MainMenuScreen(game));
                dispose();
                stage.clear();
                return true;
            }
        });
    }

    /**
     * Creates tutorial group and adds actor to it, hides that group for further use.
     */
    private void createTutorial() {
        tutorialGroup = new Group();
        tutorial = new Tutorial();

        tutorialGroup.addActor(tutorial);
        tutorialGroup.addActor(tutorial.skipButton);
        tutorialGroup.addActor(tutorial.nextButton);
        stage.addActor(tutorialGroup);

        tutorialGroup.setVisible(false);
    }

    /**
     * Creates win group and adds actor to it, hides that group for further use.
     */
    private void createWinScreen() {
        winGroup = new Group();
        winScreen = new WinDialog();

        winGroup.addActor(winScreen);
        winGroup.addActor(winScreen.menuButton);
        stage.addActor(winGroup);

        winGroup.setVisible(false);
    }
    /**
     * Creates lose group and adds actor to it, hides that group for further use.
     */
    private void createLoseScreen() {
        loseGroup = new Group();
        loseScreen = new LoseDialog();

        loseGroup.addActor(loseScreen);
        loseGroup.addActor(loseScreen.menuButton);
        stage.addActor(loseGroup);
        loseGroup.setVisible(false);
    }

    /**
     * Creates object for losing animation.
     */
    private void createAnimation() {
        hukkaExplosion = new ExplosionAnimation();
    }

    /**
     * Creates wasteMeter object, visualWasteMeter object and adds them to stage.
     */
    private void createWasteMeter() {
        wasteMeter = new WasteMeter();
        wasteBar = new WasteMeterVisual();
        stage.addActor(wasteMeter);
        stage.addActor(wasteBar);
        stage.addActor(exitDoorButton);
    }

    /**
     * Adds school, farm and market to stage.
     */
    private void createHouses() {
        stage.addActor(school);
        stage.addActor(farm);
        stage.addActor(market);
    }

    /**
     * Creates all confirm dialogs
     */
    private void createDialog() {
        marketDialogGroup = new Group();
        marketDialog = new MarketConfirmDialog();

        marketDialogGroup.addActor(marketDialog);
        marketDialogGroup.addActor(marketDialog.confirmBtn);
        marketDialogGroup.addActor(marketDialog.cancelBtn);
        stage.addActor(marketDialogGroup);

        marketDialogGroup.setVisible(false);

        farmDialogGroup = new Group();
        farmDialog = new FarmConfirmDialog();

        farmDialogGroup.addActor(farmDialog);
        farmDialogGroup.addActor(farmDialog.confirmBtn);
        farmDialogGroup.addActor(farmDialog.cancelBtn);
        stage.addActor(farmDialogGroup);

        farmDialogGroup.setVisible(false);

        schoolDialogGroup = new Group();
        schoolDialog = new SchoolConfirmDialog();

        schoolDialogGroup.addActor(schoolDialog);
        schoolDialogGroup.addActor(schoolDialog.confirmBtn);
        schoolDialogGroup.addActor(schoolDialog.cancelBtn);
        stage.addActor(schoolDialogGroup);

        schoolDialogGroup.setVisible(false);
    }

    /**
     * Creates all buy menus, hides them for further use
     */
    private void createBuyMenu() {
        marketMenuGroup = new Group();
        marketBuyMenu = new MarketBuyMenu();
        farmMenuGroup = new Group();
        farmBuyMenu = new FarmBuyMenu();

        marketMenuGroup.addActor(marketBuyMenu);
        marketMenuGroup.addActor(marketBuyMenu.buy1);
        marketMenuGroup.addActor(marketBuyMenu.buy2);
        marketMenuGroup.addActor(marketBuyMenu.buy3);
        marketMenuGroup.addActor(marketBuyMenu.buy4);
        marketMenuGroup.addActor(marketBuyMenu.buy5);
        marketMenuGroup.addActor(marketBuyMenu.buy6);
        marketMenuGroup.addActor(marketBuyMenu.close);

        farmMenuGroup.addActor(farmBuyMenu);
        farmMenuGroup.addActor(farmBuyMenu.buy1);
        farmMenuGroup.addActor(farmBuyMenu.buy2);
        farmMenuGroup.addActor(farmBuyMenu.buy3);
        farmMenuGroup.addActor(farmBuyMenu.buy4);
        farmMenuGroup.addActor(farmBuyMenu.close);

        schoolMenuGroup = new Group();
        schoolBuyMenu = new SchoolBuyMenu();
        schoolMenuGroup.addActor(schoolBuyMenu);
        schoolMenuGroup.addActor(schoolBuyMenu.buy1);
        schoolMenuGroup.addActor(schoolBuyMenu.buy2);
        schoolMenuGroup.addActor(schoolBuyMenu.buy3);
        schoolMenuGroup.addActor(schoolBuyMenu.buy4);
        schoolMenuGroup.addActor(schoolBuyMenu.buy5);
        schoolMenuGroup.addActor(schoolBuyMenu.buy6);
        schoolMenuGroup.addActor(schoolBuyMenu.close);

        setStyles();

        stage.addActor(marketMenuGroup);
        stage.addActor(farmMenuGroup);
        stage.addActor(schoolMenuGroup);

        marketMenuGroup.setVisible(false);
        farmMenuGroup.setVisible(false);
        schoolMenuGroup.setVisible(false);
    }

    /**
     * Creates Sound button to mute game.
     */
    private void createSoundButton() {
        sound_texture_on = new Texture(Gdx.files.internal("gamescreen/soundOn.png"));
        style_on = new ImageButton.ImageButtonStyle();
        style_on.up = new TextureRegionDrawable(new TextureRegion(sound_texture_on));

        sound_texture_off = new Texture(Gdx.files.internal("gamescreen/soundOff.png"));
        style_off = new ImageButton.ImageButtonStyle();
        style_off.up = new TextureRegionDrawable(new TextureRegion(sound_texture_off));

        soundButton = new ImageButton(style_on);
        soundButton.setPosition(200, 1300);
        soundButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                if(soundButton.getStyle() == style_on) {
                    soundButton.setStyle(style_off);
                    music.pause();
                } else {
                    soundButton.setStyle(style_on);
                    music.play();
                    music.setLooping(true);
                }
            }
        });
        stage.addActor(soundButton);
    }

    /**
     * Creates Listener when player loses game and clicks to go back to main menu.
     */
    private void loseListener() {
        if(loseScreen.menuClicked) {
            game.setScreen(new fi.tuni.endOfWaste.MainMenuScreen(game));
            prefs.putInteger("day", 1);
            dispose();
            stage.clear();
        }
    }

    /**
     * Creates Listener when player wins game and clicks to go back to main menu.
     */
    private void winListener() {
        if(winScreen.menuClicked) {
            game.setScreen(new MainMenuScreen(game));
            prefs.putInteger("day", 1);
            dispose();
            stage.clear();
        }
    }

    /**
     * Method to set game to won. Calculates highscore, sends it to winscreen wich then
     * shows it. Asks player name for highscore.
     */
    private void winGame() {
        highscore = 1000 - day;
        winScreen.setScore(highscore);
        winGroup.setVisible(true);

        gameRunning = false;
        gameWon = true;
        menuOpen = true;
        wasteMeter.remove();
        wasteBar.remove();

        Gdx.input.getTextInput(this, myBundle.get("saveHighScore"), "", myBundle.get("namePrompt"));
    }

    /**
     * Method to set game to lost. Plays losing animation.
     */
    private void loseGame() {
        gameRunning = false;
        gameWon = false;
        menuOpen = true;
        wasteMeter.remove();

        hukkaExplosion.playAnimation();
    }

    /**
     * Resets game state.
     */
    private void resetGame() {
        gameRunning = true;
        gameWon = false;
    }

    /**
     * Waste multiplier is increased based on current day and difficulty level.
     * @author Daniel Bardo
     */
    private void wasteRateIncrease() {
        wasteMultiplier += (day * difficulty);
    }
    /**
     * Calculates total daily income based on individual monthly income levels of each building
     * divided by 30 and adds that amount to player funds.
     * @author Daniel Bardo
     */
    private void calculateTotalDailyIncome() {
        totalIncomePerDay = (market.getIncomePerMonth() + farm.getIncomePerMonth()) / 30f;
        fi.tuni.endOfWaste.PlayerData.setPlayerMoney(fi.tuni.endOfWaste.PlayerData.getPlayerMoney() + totalIncomePerDay);
    }
    /**
     * Individual waste rates of buildings are used to calculate total waste.
     * @author Daniel Bardo
     */
    private void calculateTotalWaste() {
        totalWaste = (market.getWastePerDay() + school.getWastePerDay()  + farm.getWastePerDay());
    }
    /**
     * Increases day each interval determined by the dayLength variable.
     * Also calls the dailyCalculations method.
     * @param deltaTime delta time
     * @author Daniel Bardo
     */
    private void dayCounter(float deltaTime) {
        timeSeconds += deltaTime;
        if(timeSeconds > dayLength) {
            timeSeconds -= dayLength;
            day++;
            dailyCalculations();
        }
    }
    /**
     * Calls methods that need to be called daily.
     * @author Aleksi Asikainen
     */
    private void dailyCalculations() {
        monthCounter();
        calculateTotalDailyIncome();
        market.launchEvent();
        school.launchEvent();
        farm.launchEvent();
    }

    /**
     * Calculates months from days. This helps to make monthly method calls. Also increases
     * difficulty if game has been going on for too long.
     */
    private void monthCounter() {
        daysToSwitchMonth--;
        if(daysToSwitchMonth == 0) {
            if(day >= 149) {
                difficulty = difficulty * 1.1f;
            }
            wasteRateIncrease();
            daysToSwitchMonth = 30;
        }
    }

    private void buttonActionListener() {
        if(marketDialog.cancelClicked) {
            marketDialogGroup.setVisible(false);
            dialogOpen = false;
        }
        if(farmDialog.cancelClicked) {
            farmDialogGroup.setVisible(false);
            dialogOpen = false;
        }
        if(schoolDialog.cancelClicked) {
            schoolDialogGroup.setVisible(false);
            dialogOpen = false;
        }

        if(market.clicked) {
            marketMenuGroup.setVisible(true);
            menuOpen = true;
            marketMenuOpen = true;
        }

        if(marketBuyMenu.closeClicked) {
            marketMenuGroup.setVisible(false);
            menuOpen = false;
            marketMenuOpen = false;
        }

        if(farm.clicked) {
            farmMenuGroup.setVisible(true);
            menuOpen = true;
            farmMenuOpen = true;
        }

        if(farmBuyMenu.closeClicked) {
            farmMenuGroup.setVisible(false);
            menuOpen = false;
            farmMenuOpen = false;
        }

        if(school.clicked) {
            schoolMenuGroup.setVisible(true);
            menuOpen = true;
            schoolMenuOpen = true;
        }

        if(schoolBuyMenu.closeClicked) {
            schoolMenuGroup.setVisible(false);
            menuOpen = false;
            schoolMenuOpen = false;
        }

        if(marketBuyMenu.button1Clicked) {
            market.buyNewThing((byte)1);
        }

        if(marketBuyMenu.button2Clicked) {
            if(market.getEmployeeLvl() == 1 && market.getEmployeeUpgradePrice() <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                marketBuyMenu.buy1.setStyle(marketBuyMenu.button1_style2);
                marketBuyMenu.buy2.setStyle(marketBuyMenu.button2_style2);
            }
            if(market.getEmployeeLvl() == 2 && market.getEmployeeUpgradePrice() <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                marketBuyMenu.buy1.setStyle(marketBuyMenu.button1_style3);
                marketBuyMenu.buy2.setStyle(marketBuyMenu.maxLvl_style);
            }
            market.upgradeThing((byte) 1);
        }

        if(marketBuyMenu.button3Clicked && isEnoughMoney(market.getSystemLvl(), market.getSystemPrice(),
                market.getSystemUpgradePrice()) && !market.isSystemMaxLvl()) {
            if(market.getSystemLvl() == 0) {
                marketDialog.switchTexture((byte) 1);
            }
            if(market.getSystemLvl() == 1) {
                marketDialog.switchTexture((byte) 2);
            }
            if(market.getSystemLvl() == 2) {
                marketDialog.switchTexture((byte) 3);
            }
            if(market.getSystemLvl() == 3) {
                marketDialog.switchTexture((byte) 4);
            }
            marketButtonId = 1;
            marketDialogGroup.setVisible(true);
        }

        if(marketDialog.confirmClicked && marketButtonId == 1) {

            if(!market.isHaveSystem()) {
                marketBuyMenu.buy3.setStyle(marketBuyMenu.button3_style2);
                marketDialog.switchTexture((byte)2);
            }
            if(market.getSystemLvl() == 1) {
                marketBuyMenu.buy3.setStyle(marketBuyMenu.button3_style3);
                marketDialog.switchTexture((byte)3);
            }
            if(market.getSystemLvl() == 2) {
                marketBuyMenu.buy3.setStyle(marketBuyMenu.button3_style4);
                marketDialog.switchTexture((byte)4);
            }
            if(market.getSystemLvl() == 3) {
                marketBuyMenu.buy3.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            market.upgradeThing((byte) 2);
            market.buyNewThing((byte) 2);
            marketButtonId = 0;
            marketDialogGroup.setVisible(false);
        }

        if(marketBuyMenu.button4Clicked && isEnoughMoney(market.getStorageLvl(), market.getStoragePrice(),
                market.getStorageUpgradePrice())&& !market.isStorageMaxLvl()) {
            if(market.getStorageLvl() == 0) {
                marketDialog.switchTexture((byte) 5);
            }
            if(market.getStorageLvl() == 1) {
                marketDialog.switchTexture((byte) 6);
            }
            if(market.getStorageLvl() == 2) {
                marketDialog.switchTexture((byte) 7);
            }
            marketButtonId = 2;
            marketDialogGroup.setVisible(true);
        }
        if(marketDialog.confirmClicked && marketButtonId == 2) {
            if(!market.isHaveStorage()) {
                marketBuyMenu.buy4.setStyle(marketBuyMenu.button4_style2);
                marketDialog.switchTexture((byte) 6);
            }
            if(market.getStorageLvl() == 1) {
                marketBuyMenu.buy4.setStyle(marketBuyMenu.button4_style3);
                marketDialog.switchTexture((byte) 7);
            }
            if(market.getStorageLvl() == 2) {
                marketBuyMenu.buy4.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            market.upgradeThing((byte) 3);
            market.buyNewThing((byte) 3);
            marketButtonId = 0;
            marketDialogGroup.setVisible(false);
        }

        if(marketBuyMenu.button5Clicked && isEnoughMoney(market.getDisposalLvl(), market.getDisposalPrice(),
                market.getDisposalUpgradePrice()) && !market.isDisposalMaxLvl()) {
            if(market.getDisposalLvl() == 0) {
                marketDialog.switchTexture((byte) 8);
            }
            if(market.getDisposalLvl() == 1) {
                marketDialog.switchTexture((byte) 9);
            }
            if(market.getDisposalLvl() == 2) {
                marketDialog.switchTexture((byte) 10);
            }
            marketButtonId = 3;
            marketDialogGroup.setVisible(true);
        }

        if(marketDialog.confirmClicked && marketButtonId == 3) {
            if(!market.isHaveDisposal()) {
                marketBuyMenu.buy5.setStyle(marketBuyMenu.button5_style2);
                marketDialog.switchTexture((byte) 9);
            }
            if(market.getDisposalLvl() == 1) {
                marketBuyMenu.buy5.setStyle(marketBuyMenu.button5_style3);
                marketDialog.switchTexture((byte) 10);
            }
            if(market.getDisposalLvl() == 2) {
                marketBuyMenu.buy5.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            market.upgradeThing((byte) 4);
            market.buyNewThing((byte) 4);
            marketButtonId = 0;
            marketDialogGroup.setVisible(false);
        }

        if(marketBuyMenu.button6Clicked) {
            market.buyDiscount();
        }

        if(farmBuyMenu.button1Clicked) {
            farm.buyNewThing((byte)1);
        }

        if(farmBuyMenu.button2Clicked && isEnoughMoney(farm.getIdentifyLvl(), farm.getIdentifyPrice(),
                farm.getIdentifyUpgradePrice()) && !farm.isIdentifyMaxLvl()) {
            if(farm.getIdentifyLvl() == 0) {
                farmDialog.switchTexture((byte) 1);
            }
            if(farm.getIdentifyLvl() == 1) {
                farmDialog.switchTexture((byte) 2);
            }
            if(farm.getIdentifyLvl() == 2) {
                farmDialog.switchTexture((byte) 3);
            }
            if(farm.getIdentifyLvl() == 3) {
                farmDialog.switchTexture((byte) 4);
            }
            farmButtonId = 1;
            farmDialogGroup.setVisible(true);
        }

        if(farmDialog.confirmClicked && farmButtonId == 1) {
            if(!farm.isHaveIdentify()) {
                farmBuyMenu.buy2.setStyle(farmBuyMenu.button2_style2);
                farmDialog.switchTexture((byte) 2);
            }
            if(farm.getIdentifyLvl() == 1) {
                farmBuyMenu.buy2.setStyle(farmBuyMenu.button2_style3);
                farmDialog.switchTexture((byte) 3);
            }
            if(farm.getIdentifyLvl() == 2) {
                farmBuyMenu.buy2.setStyle(farmBuyMenu.button2_style4);
                farmDialog.switchTexture((byte) 4);
            }
            if(farm.getIdentifyLvl() == 3) {
                farmBuyMenu.buy2.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            farm.upgradeThing((byte) 1);
            farm.buyNewThing((byte) 2);
            farmButtonId = 0;
            farmDialogGroup.setVisible(false);
        }

        if(farmBuyMenu.button3Clicked && isEnoughMoney(farm.getHarvestLvl(), farm.getHarvestPrice(),
                farm.getHarvestUpgradePrice()) && !farm.isHarvestMaxLvl()) {
            if(farm.getHarvestLvl() == 0) {
                farmDialog.switchTexture((byte) 5);
            }
            if(farm.getHarvestLvl() == 1) {
                farmDialog.switchTexture((byte) 6);
            }
            if(farm.getHarvestLvl() == 2) {
                farmDialog.switchTexture((byte) 7);
            }
            farmButtonId = 2;
            farmDialogGroup.setVisible(true);
        }

        if(farmDialog.confirmClicked && farmButtonId == 2) {
            if(!farm.isHaveHarvest()) {
                farmBuyMenu.buy3.setStyle(farmBuyMenu.button3_style2);
                farmDialog.switchTexture((byte) 6);
            }
            if(farm.getHarvestLvl() == 1) {
                farmBuyMenu.buy3.setStyle(farmBuyMenu.button3_style3);
                farmDialog.switchTexture((byte) 7);
            }
            if(farm.getHarvestLvl() == 2) {
                farmBuyMenu.buy3.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            farm.upgradeThing((byte) 2);
            farm.buyNewThing((byte) 3);
            farmButtonId = 0;
            farmDialogGroup.setVisible(false);
        }

        if(farmBuyMenu.button4Clicked && isEnoughMoney(farm.getCompostLvl(), farm.getCompostPrice(),
                farm.getCompostUpgradePrice()) && !farm.isCompostMaxLvl()) {
            if(farm.getCompostLvl() == 0) {
                farmDialog.switchTexture((byte) 8);
            }
            if(farm.getCompostLvl() == 1) {
                farmDialog.switchTexture((byte) 9);
            }
            if(farm.getCompostLvl() == 2) {
                farmDialog.switchTexture((byte) 10);
            }
            farmButtonId = 3;
            farmDialogGroup.setVisible(true);
        }

        if(farmDialog.confirmClicked && farmButtonId == 3) {
            if(!farm.isHaveCompost()) {
                farmBuyMenu.buy4.setStyle(farmBuyMenu.button4_style2);
                farmDialog.switchTexture((byte) 9);
            }
            if(farm.getCompostLvl() == 1) {
                farmBuyMenu.buy4.setStyle(farmBuyMenu.button4_style3);
                farmDialog.switchTexture((byte) 10);
            }
            if(farm.getCompostLvl() == 2) {
                farmBuyMenu.buy4.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            farm.upgradeThing((byte) 3);
            farm.buyNewThing((byte) 4);
            farmButtonId = 0;
            farmDialogGroup.setVisible(false);
        }

        if(schoolBuyMenu.button1Clicked) {
            school.buyNewThing((byte)1);
        }

        if(schoolBuyMenu.button2Clicked) {
            if(school.getCookLvl() == 1 && school.getCookUpgradePrice() <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                schoolBuyMenu.buy1.setStyle(schoolBuyMenu.button1_style2);
                schoolBuyMenu.buy2.setStyle(schoolBuyMenu.button2_style2);
            }
            if(school.getCookLvl() == 2 && school.getCookUpgradePrice() <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney()) {
                schoolBuyMenu.buy1.setStyle(schoolBuyMenu.button1_style3);
                schoolBuyMenu.buy2.setStyle(marketBuyMenu.maxLvl_style);
            }
            school.upgradeThing((byte) 1);
        }

        if(schoolBuyMenu.button3Clicked && isEnoughMoney(school.getLogisticsLvl(), school.getLogisticsPrice(),
                school.getLogisticsUpgradePrice()) && !school.isLogisticsMaxLvl()) {
            if(school.getLogisticsLvl() == 0) {
                schoolDialog.switchTexture((byte) 1);
            }
            if(school.getLogisticsLvl() == 1) {
                schoolDialog.switchTexture((byte) 2);
            }
            if(school.getLogisticsLvl() == 2) {
                schoolDialog.switchTexture((byte) 3);
            }
            schoolButtonId = 1;
            schoolDialogGroup.setVisible(true);
        }

        if(schoolDialog.confirmClicked && schoolButtonId == 1) {
            if(!school.isHaveLogistics()) {
                schoolBuyMenu.buy3.setStyle(schoolBuyMenu.button3_style2);
                schoolDialog.switchTexture((byte)2);
            }
            if(school.getLogisticsLvl() == 1) {
                schoolBuyMenu.buy3.setStyle(schoolBuyMenu.button3_style3);
                schoolDialog.switchTexture((byte)3);
            }
            if(school.getLogisticsLvl() == 2) {
                schoolBuyMenu.buy3.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            school.upgradeThing((byte) 2);
            school.buyNewThing((byte) 2);
            schoolButtonId = 0;
            schoolDialogGroup.setVisible(false);
        }

        if(schoolBuyMenu.button4Clicked && isEnoughMoney(school.getMenuLvl(), school.getMenuPrice(),
                school.getMenuUpgradePrice())&& !school.isMenuMaxLvl()) {
            if(school.getMenuLvl() == 0) {
                schoolDialog.switchTexture((byte) 4);
            }
            if(school.getMenuLvl() == 1) {
                schoolDialog.switchTexture((byte) 5);
            }
            if(school.getMenuLvl() == 2) {
                schoolDialog.switchTexture((byte) 6);
            }
            schoolButtonId = 2;
            schoolDialogGroup.setVisible(true);
        }

        if(schoolDialog.confirmClicked && schoolButtonId == 2) {
            if(!school.isHaveMenu()) {
                schoolBuyMenu.buy4.setStyle(schoolBuyMenu.button4_style2);
                schoolDialog.switchTexture((byte) 5);
            }
            if(school.getMenuLvl() == 1) {
                schoolBuyMenu.buy4.setStyle(schoolBuyMenu.button4_style3);
                schoolDialog.switchTexture((byte) 6);
            }
            if(school.getMenuLvl() == 2) {
                schoolBuyMenu.buy4.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            school.upgradeThing((byte) 3);
            school.buyNewThing((byte) 3);
            schoolButtonId = 0;
            schoolDialogGroup.setVisible(false);
        }

        if(schoolBuyMenu.button6Clicked && isEnoughMoney(school.getCompostLvl(), school.getCompostPrice(),
                school.getCompostUpgradePrice()) && !school.isCompostMaxLvl()) {
            if(school.getCompostLvl() == 0) {
                schoolDialog.switchTexture((byte) 7);
            }
            if(school.getCompostLvl() == 1) {
                schoolDialog.switchTexture((byte) 8);
            }
            if(school.getCompostLvl() == 2) {
                schoolDialog.switchTexture((byte) 9);
            }
            schoolButtonId = 3;
            schoolDialogGroup.setVisible(true);
        }

        if(schoolDialog.confirmClicked && schoolButtonId == 3) {
            if(!school.isHaveCompost()) {
                schoolBuyMenu.buy6.setStyle(schoolBuyMenu.button6_style2);
                schoolDialog.switchTexture((byte) 8);
            }
            if(school.getCompostLvl() == 1) {
                schoolBuyMenu.buy6.setStyle(schoolBuyMenu.button6_style3);
                schoolDialog.switchTexture((byte) 9);
            }
            if(school.getCompostLvl() == 2) {
                schoolBuyMenu.buy6.setStyle(marketBuyMenu.maxLvlWide_style);
            }
            school.upgradeThing((byte) 4);
            school.buyNewThing((byte) 4);
            schoolButtonId = 0;
            schoolDialogGroup.setVisible(false);
        }

        if(schoolBuyMenu.button5Clicked) {
            school.buyEnlightening();
        }
    }
    /**
     * Waste multiplier is increased based on current day and difficulty level.
     * @param level current level of specific upgrade
     * @param price price of initial purchase
     * @param upgradePrice price of current upgrade
     * @return True if player has enough money for current purchase, false otherwise.
     * @author Daniel Bardo
     */
    private boolean isEnoughMoney(int level, float price, float upgradePrice) {
        if(level == 0) {
            return price <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney();
        }
        else return upgradePrice <= fi.tuni.endOfWaste.PlayerData.getPlayerMoney();
    }

    private void setWasteMeterSize() {
        if(totalWaste <= 100) {
            wasteMeter.setCurrentTexture(1);
        } else if(totalWaste > 100 && totalWaste <= 250) {
            wasteMeter.setCurrentTexture(2);
        } else if(totalWaste > 250 && totalWaste <= 400) {
            wasteMeter.setCurrentTexture(3);
        } else if(totalWaste > 400) {
            wasteMeter.setCurrentTexture(4);
        }
    }
    /**
     * Draws HUD texts and dynamic menu texts.
     * @author Daniel Bardo
     */
    private void drawTexts() {
        stage.getBatch().begin();
        game.font.draw(game.batch, String.format(myBundle.get("money"), fi.tuni.endOfWaste.PlayerData.getPlayerMoney()),1620, 1395);
        game.font.draw(game.batch, (myBundle.get("day") + day), 2150, 1395);

        if(!menuOpen) {
            game.fontSmall.draw(game.batch, String.format(myBundle.get("waste"), market.getWastePerDay()), 920, 320);
            game.fontSmall.draw(game.batch, String.format(myBundle.get("income"), market.getIncomePerMonth()), 920, 215);
            game.fontSmall.draw(game.batch, String.format(myBundle.get("waste"), farm.getWastePerDay()), 150, 1120);
            game.fontSmall.draw(game.batch, String.format(myBundle.get("income"), farm.getIncomePerMonth()), 150, 1015);
            game.fontSmall.draw(game.batch, String.format(myBundle.get("waste"), school.getWastePerDay()), 1850, 1220);
            game.fontSmall.draw(game.batch, String.format("%.0fkg", totalWaste) + "/" + criticalWaste + "kg", 1080, 1175);
            game.fontSmallest.draw(game.batch, "x " + market.getEmployeeAmount(), 1400, 645);
            game.fontSmallest.draw(game.batch, "x " + farm.getFarmerAmount(), 325, 595);
            game.fontSmallest.draw(game.batch, "x " + school.getCookAmount(), 1990, 685);
        }
        if(gameRunning) {
            if (marketMenuOpen && !marketDialogGroup.isVisible()) {
                game.fontRegular.draw(game.batch, String.format("%.0f€", market.getEmployeePrice()), 1200, 1025);
                game.fontRegular.draw(game.batch, "" + market.getEmployeeValue(), 1035, 1085);
                game.fontRegular.draw(game.batch, "" + market.getEmployeeIncome(), 1035, 1020);
                if (!market.isEmployeeMaxLvl()) {
                    game.fontRegular.draw(game.batch, String.format("%.0f€", market.getEmployeeUpgradePrice()), 2180, 1030);
                    if (market.getEmployeeLvl() == 1) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getEmployeeLvl2Price()), 1840, 1030);
                    }
                    if (market.getEmployeeLvl() == 2) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getEmployeeLvl3Price()), 1840, 1030);
                    }
                }
                if (!market.isSystemMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedSystemValue(), 1930, 895);
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedSystemIncome(), 1930, 825);
                    if (!market.isHaveSystem()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getSystemPrice()), 2170, 835);
                    } else if (market.isHaveSystem()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getSystemUpgradePrice()), 2170, 835);
                    }
                }
                if (!market.isStorageMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedStorageValue(), 1930, 710);
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedStorageIncome(), 1930, 640);
                    if (!market.isHaveStorage()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getStoragePrice()), 2170, 650);
                    } else if (market.isHaveStorage()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getStorageUpgradePrice()), 2170, 650);
                    }
                }
                if (!market.isDisposalMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedDisposalValue(), 1930, 527);
                    game.fontRegular.draw(game.batch, "" + market.getDisplayedDisposalIncome(), 1930, 457);
                    if (!market.isHaveDisposal()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getDisposalPrice()), 2170, 465);
                    } else if (market.isHaveDisposal()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", market.getDisposalUpgradePrice()), 2170, 465);
                    }
                }
                if (!market.isDiscountOnCooldown()) {
                    game.fontRegular.draw(game.batch, String.format("%.0f", market.getDiscountModifier()) + "%", 1750, 350);
                    game.fontRegular.draw(game.batch, market.getDiscountTime() + myBundle.get("durationDay"), 1750, 280);
                    game.fontRegular.draw(game.batch, String.format("%.0f€", market.getDiscountPrice()), 2155, 285);
                }
            }

            if (farmMenuOpen && !farmDialogGroup.isVisible()) {
                game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getFarmerPrice()), 2170, 1020);
                game.fontRegular.draw(game.batch, "" + farm.getFarmerValue(), 1930, 1080);
                game.fontRegular.draw(game.batch, "" + farm.getFarmerIncome(), 1930, 1010);
                if (!farm.isIdentifyMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedIdentifyValue(), 1930, 895);
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedIdentifyIncome(), 1930, 825);
                    if (!farm.isHaveIdentify()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getIdentifyPrice()), 2170, 835);
                    } else if (farm.isHaveIdentify()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getIdentifyUpgradePrice()), 2170, 835);
                    }
                }
                if (!farm.isHarvestMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedHarvestValue(), 1930, 710);
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedHarvestIncome(), 1930, 640);
                    if (!farm.isHaveHarvest()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getHarvestPrice()), 2170, 650);
                    } else if (farm.isHaveHarvest()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getHarvestUpgradePrice()), 2170, 650);
                    }
                }
                if (!farm.isCompostMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedCompostValue(), 1930, 527);
                    game.fontRegular.draw(game.batch, "" + farm.getDisplayedCompostIncome(), 1930, 457);
                    if (!farm.isHaveCompost()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getCompostPrice()), 2170, 465);
                    } else if (farm.isHaveCompost()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", farm.getCompostUpgradePrice()), 2170, 465);
                    }
                }
            }

            if (schoolMenuOpen && !schoolDialogGroup.isVisible()) {
                game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCookPrice()), 1200, 1025);
                game.fontRegular.draw(game.batch, "" + school.getCookValue(), 1035, 1055);
                if (!school.isCookMaxLvl()) {
                    game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCookUpgradePrice()), 2180, 1030);
                    if (school.getCookLvl() == 1) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCookLvl2Price()), 1840, 1030);
                    }
                    if (school.getCookLvl() == 2) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCookLvl3Price()), 1840, 1030);
                    }
                }
                if (!school.isLogisticsMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + school.getDisplayedLogisticsValue(), 1930, 865);
                    if (!school.isHaveLogistics()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getLogisticsPrice()), 2170, 835);
                    } else if (school.isHaveLogistics()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getLogisticsUpgradePrice()), 2170, 835);
                    }
                }
                if (!school.isMenuMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + school.getDisplayedMenuValue(), 1930, 680);
                    if (!school.isHaveMenu()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getMenuPrice()), 2170, 650);
                    } else if (school.isHaveMenu()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getMenuUpgradePrice()), 2170, 650);
                    }
                }
                if (!school.isCompostMaxLvl()) {
                    game.fontRegular.draw(game.batch, "" + school.getDisplayedCompostValue(), 1930, 310);
                    if (!school.isHaveCompost()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCompostPrice()), 2170, 285);
                    } else if (school.isHaveCompost()) {
                        game.fontRegular.draw(game.batch, String.format("%.0f€", school.getCompostUpgradePrice()), 2170, 285);
                    }
                }
                if (!school.isEnlightenOnCooldown()) {
                    game.fontRegular.draw(game.batch, String.format("%.0f", school.getEnlightenModifier()) + "%", 1750, 530);
                    game.fontRegular.draw(game.batch, school.getEnlightenTime() + "pv", 1750, 460);
                    game.fontRegular.draw(game.batch, String.format("%.0f€", school.getEnlightenPrice()), 2155, 465);
                }
            }
        }
        stage.getBatch().end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    /**
     * Calls the save method of each building class and writes current day and money to userPrefs.
     * @author Daniel Bardo
     */
    private void saveGame() {
        market.saveMarket();
        farm.saveFarm();
        school.saveSchool();
        prefs.putInteger("day", day);
        prefs.putFloat("money", fi.tuni.endOfWaste.PlayerData.getPlayerMoney());
        prefs.flush();
    }
    /**
     * Calls the load method of each building class and sets day and money from userPrefs.
     * @author Daniel Bardo
     */
    private void loadSave() {
        market.loadMarket();
        farm.loadFarm();
        school.loadSchool();
        day = prefs.getInteger("day");
        PlayerData.setPlayerMoney(prefs.getFloat("money"));
    }
    /**
     * Gets the current style from each buy menu class of each building and sets that style when
     * creating the menus. Used to load correct initial style when loading from a saved game.
     * @author Daniel Bardo
     */
    private void setStyles() {
        marketBuyMenu.buy1.setStyle(marketBuyMenu.getStyle(marketBuyMenu.buy1, market.getEmployeeLvl(), market.isEmployeeMaxLvl()));
        marketBuyMenu.buy2.setStyle(marketBuyMenu.getStyle(marketBuyMenu.buy2, market.getEmployeeLvl(), market.isEmployeeMaxLvl()));
        marketBuyMenu.buy3.setStyle(marketBuyMenu.getStyle(marketBuyMenu.buy3, market.getSystemLvl(), market.isSystemMaxLvl()));
        marketBuyMenu.buy4.setStyle(marketBuyMenu.getStyle(marketBuyMenu.buy4, market.getStorageLvl(), market.isStorageMaxLvl()));
        marketBuyMenu.buy5.setStyle(marketBuyMenu.getStyle(marketBuyMenu.buy5, market.getDisposalLvl(), market.isDisposalMaxLvl()));

        schoolBuyMenu.buy1.setStyle((schoolBuyMenu.getStyle(schoolBuyMenu.buy1, school.getCookLvl(), school.isCookMaxLvl())));
        schoolBuyMenu.buy2.setStyle((schoolBuyMenu.getStyle(schoolBuyMenu.buy2, school.getCookLvl(), school.isCookMaxLvl())));
        schoolBuyMenu.buy3.setStyle((schoolBuyMenu.getStyle(schoolBuyMenu.buy3, school.getLogisticsLvl(), school.isLogisticsMaxLvl())));
        schoolBuyMenu.buy4.setStyle((schoolBuyMenu.getStyle(schoolBuyMenu.buy4, school.getMenuLvl(), school.isMenuMaxLvl())));
        schoolBuyMenu.buy6.setStyle((schoolBuyMenu.getStyle(schoolBuyMenu.buy6, school.getCompostLvl(), school.isCompostMaxLvl())));

        farmBuyMenu.buy2.setStyle((farmBuyMenu.getStyle(farmBuyMenu.buy2, farm.getIdentifyLvl(), farm.isIdentifyMaxLvl())));
        farmBuyMenu.buy3.setStyle((farmBuyMenu.getStyle(farmBuyMenu.buy3, farm.getHarvestLvl(), farm.isHarvestMaxLvl())));
        farmBuyMenu.buy4.setStyle((farmBuyMenu.getStyle(farmBuyMenu.buy4, farm.getCompostLvl(), farm.isCompostMaxLvl())));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        saveGame();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * @author Viljami Pietarila
     */
    @Override
    public void receiveHighScore(List<HighScoreEntry> highScores) {
    }

    /**
     * @author Viljami Pietarila
     */
    @Override
    public void receiveSendReply(Net.HttpResponse httpResponse) {
    }

    /**
     * @author Viljami Pietarila
     */
    @Override
    public void failedToRetrieveHighScores(Throwable t) {
    }

    /**
     * @author Viljami Pietarila
     */
    @Override
    public void failedToSendHighScore(Throwable t) {
    }

    /**
     * Takes name as input. Calls checkForValidInput method.
     * @param text puts text to name string wich then is checked for validness.
     */
    @Override
    public void input(String text) {
        name = text;
        checkForValidInput(name);
    }

    /**
     * Takes name as input and replaces all , with empty characters.
     * @param text Takes name as input. And sets nameEntered true if its valid and false
     *             if its not valid.
     */
    private void checkForValidInput(String text) {
        if(isAllASCII(text) && !text.isEmpty() && text.length() <= 12) {
            name = text.replaceAll(",","");
            nameEntered = true;
        } else {
            nameEntered = false;
            Gdx.input.getTextInput(this, myBundle.get("saveHighScore"), "", myBundle.get("namePrompt"));
        }
    }

    /**
     * Method that checks if all characters are ascii characters.
     * @param input Takes string and returns true or false if it is or is not.
     */
    private static boolean isAllASCII(String input) {
        boolean isASCII = true;
        for (int i = 0; i < input.length(); i++) {
            int c = input.charAt(i);
            if (c > 0x7F) {
                isASCII = false;
                break;
            }
        }
        return isASCII;
    }

    /**
     * If name input is canceled when asking highscore.
     */
    @Override
    public void canceled() {
        nameEntered = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
        hukkaExplosion.dispose();

        sound_texture_on.dispose();
        sound_texture_off.dispose();
        background1.dispose();
        background2.dispose();
        currentBackground.dispose();
        topRightBg.dispose();
        marketWasteBg.dispose();
        marketIncomeBg.dispose();
        farmWasteBg.dispose();
        farmIncomeBg.dispose();
        schoolWasteBg.dispose();
        exitDoor.dispose();

        farm.dispose();
        farmBuyMenu.dispose();
        farmDialog.dispose();

        market.dispose();
        marketBuyMenu.dispose();
        marketDialog.dispose();

        school.dispose();
        schoolBuyMenu.dispose();
        schoolDialog.dispose();

        tutorial.dispose();
        wasteMeter.dispose();
        loseScreen.dispose();
        winScreen.dispose();
    }
}