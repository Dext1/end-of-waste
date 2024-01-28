package fi.tuni.endOfWaste;

/**
 * Class to keep track of players data.
 */
public class PlayerData {
    /**
     * Variable to store players money.
     */
    private static float playerMoney;

    /**
     * Method to get players money from other classes
     */
    public static float getPlayerMoney() {
        return playerMoney;
    }

    /**
     * Method to set players money from other classes
     * @param newPlayerMoney new amount of money
     */
    public static void setPlayerMoney(float newPlayerMoney) {
        playerMoney = newPlayerMoney;
    }
}
