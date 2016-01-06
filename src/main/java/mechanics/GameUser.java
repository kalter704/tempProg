package mechanics;

import java.util.Random;

public class GameUser {
    public static final int RED_COLOR = 1;
    public static final int BLUE_COLOR = 2;

    private String name;
    private int playerColor;

    private String enemyName;

    public GameUser(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public String getEnemyName() { return this.enemyName; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }

    public void setRandomColorToMe() {
        Random random = new Random();
        playerColor = random.nextInt(2) + 1;
    }

    public int getPlayerColor() { return playerColor; }

    public String getPlayerColorStr() {
        if(playerColor == RED_COLOR) {
            return "red";
        } else {
            return "blue";
        }
    }
    public String getEnemyColorStr() {
        if(playerColor == RED_COLOR) {
            return "blue";
        } else {
            return "red";
        }
    }

    public int getEnemyColor() {
        if(playerColor == RED_COLOR) {
            return BLUE_COLOR;
        } else {
            return RED_COLOR;
        }
    }

    public void setColorToMe(int color) { this.playerColor = color; }

}
