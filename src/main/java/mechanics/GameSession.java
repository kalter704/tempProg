package mechanics;

import com.sun.rowset.internal.Row;

import java.util.Date;

public class GameSession {
    private long startSession;
    private long startRoundTime;
    private boolean inGame;
    private int emptyCells;
    private int numberRound;

    private final GameUser firstPlayer;
    private final GameUser secondPlayer;
    private boolean turnFirstPlayer;
    private boolean firstTurnFirstPlayerInLastRound;
    private boolean firstPlayerReady;
    private boolean secondPlayerReady;

    private final int MARK_FIRST_PLAYER = 1;
    private final int MARK_SECOND_PLAYER = 2;

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private int lastPointPosition;

    private int[] gameField = new int[ROWS * COLUMNS];

    //Its gonna magic, magic
    private static final int[] connectNumbers = {
            0, 6, 12, 18, 24, 30, 36,
            1, 7, 13, 19, 25, 31, 37,
            2, 8, 14, 20, 26, 32, 38,
            3, 9, 15, 21, 27, 33, 39,
            4, 10, 16, 22, 28, 34, 40,
            5, 11, 17, 23, 29, 35, 41
    };

    //Зачем они нужны?
    //private Map<String, GameUser> users = new HashMap<>();

    public GameSession(String first, String second) {
        startSession = new Date().getTime();
        startRoundTime = 0;
        inGame = false;

        GameUser firstPlayer = new GameUser(first);
        firstPlayer.setRandomColorToMe();
        if(firstPlayer.getPlayerColor() == GameUser.BLUE_COLOR) {
            turnFirstPlayer = true;
        } else {
            turnFirstPlayer = false;
        }
        firstPlayer.setEnemyName(second);

        GameUser secondPlayer = new GameUser(second);
        secondPlayer.setColorToMe(firstPlayer.getEnemyColor());
        secondPlayer.setEnemyName(first);

        //users.put(first, firstPlayer);
        //users.put(second, secondPlayer);

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        firstPlayerReady = false;
        secondPlayerReady = false;

        lastPointPosition = -1;

        emptyCells = ROWS * COLUMNS;
        numberRound = 1;


        for(int i = 0; i < ROWS; ++i) {
            for(int j = 0; j < COLUMNS; ++j) {
                gameField[COLUMNS * i + j] = 0;
            }
        }
    }

    public void setCurrectTimeToRound() { startRoundTime = new Date().getTime(); }

    public void nextTurn() {
        //startRound = new Date().getTime();
        setCurrectTimeToRound();
        //changeTurn();
        turnFirstPlayer = !turnFirstPlayer;
    }

    public void startRound() {
        turnFirstPlayer = !firstTurnFirstPlayerInLastRound;
        setCurrectTimeToRound();
        for(int i = 0; i < ROWS; ++i) {
            for(int j = 0; j < COLUMNS; ++j) {
                gameField[COLUMNS * i + j] = 0;
            }
        }
        emptyCells = ROWS * COLUMNS;
    }

    public int getLastPointPosition() {
        return lastPointPosition;
    }

    public boolean isTurnFirstPlayer() { return turnFirstPlayer; }

    public boolean isTurnSecondPlayer() { return !turnFirstPlayer; }

    public boolean isTurnByName(String name) {
        if(firstPlayer.getName().equals(name)) {
            return turnFirstPlayer;
        } else {
            return !turnFirstPlayer;
        }
    }

    public int getRound() { return numberRound; }

    public void incrementRound() { ++numberRound; }

    public GameUser getGameUserByName(String name) {
        if(name.equals(firstPlayer.getName())) {
            return firstPlayer;
        } else {
            return secondPlayer;
        }
    }

    public boolean isFirstPlayerReady() { return firstPlayerReady; }

    public boolean isSecondPlayerReady() { return secondPlayerReady; }

    public void setPlayerReady(String name, boolean isReady) {
        if(name.equals(firstPlayer.getName())) {
            this.firstPlayerReady = isReady;
        } else {
            this.secondPlayerReady = isReady;
        }
    }

    public boolean isSessionReady() {
        return firstPlayerReady && secondPlayerReady;
    }

    public  void setNotReady() {
        firstPlayerReady = false;
        secondPlayerReady = false;
    }

    public void startGame() {
        this.inGame = true;
        firstTurnFirstPlayerInLastRound = turnFirstPlayer = firstPlayer.getPlayerColor() == GameUser.BLUE_COLOR;
    }

    public boolean isInGame() { return this.inGame; }

    /*
    public void setFirstPlayerReady() { this.firstPlayerReady = true; }

    public void setFirstPlayerNotReady() { this.firstPlayerReady = false; }

    public void setSecondPlayerReady() { this.secondPlayerReady = true; }

    public void setSecondPlayerNotReady() { this.secondPlayerReady = false; }
    */

    public long getSessionTime() { return new Date().getTime() - startSession; }

    public long getRoundTime() { return new Date().getTime() - startRoundTime; }

    public GameUser getFirstPlayer() { return firstPlayer; }

    public GameUser getSecondPlayer() { return secondPlayer; }

    public boolean setPointPlayerByColumn(String user, int col) {
        if(user.equals(firstPlayer.getName())) {
            return setPointFirstPlayerByColumn(col);
        } else {
            return setPointSecondPlayerByColumn(col);
        }
    }

    /*
    public boolean setPointFirstPlayer(int i, int j) {
        --emptyCells;
        return setPoint(i, j, MARK_FIRST_PLAYER);
    }

    public boolean setPointSecondPlayer(int i, int j) {
        --emptyCells;
        return setPoint(i, j, MARK_SECOND_PLAYER);
    }

    */

    public boolean setPointFirstPlayerByColumn(int j) {
        --emptyCells;
        return setPointByColumn(j, MARK_FIRST_PLAYER);
    }

    public boolean setPointSecondPlayerByColumn(int j) {
        --emptyCells;
        return setPointByColumn(j, MARK_SECOND_PLAYER);
    }

    public boolean isFullTable() {
        return emptyCells < 1;
    }

    public boolean isFirstWin() {
        return isWin(MARK_FIRST_PLAYER);
    }

    public boolean isSecondWin() {
        return isWin(MARK_SECOND_PLAYER);
    }

    /*
    private boolean setPoint(int i, int j, int mark) {
        if(gameField[COLUMNS * i + j] == 0) {
            gameField[COLUMNS * i + j] = mark;
            return true;
        } else {
            return false;
        }
    }
    */

    private boolean setPointByColumn(int j, int mark) {
        int i = ROWS - 1;
        int index = COLUMNS * i + j;
        //System.out.append(String.valueOf(ROWS - 1) + "\n");
        //System.out.append(String.valueOf(i) + "q \n");
        //System.out.append(String.valueOf(ROWS * i + j - 1) + "\n");
        //System.out.append(String.valueOf(index) + " \n");
        while (i >= 0 && gameField[index] != 0) {
            //System.out.append(String.valueOf(i) + "q \n");
            --i;
            index = COLUMNS * i + j;
        }
        //printGameFieldToLog();
        if(i >= 0) {
            gameField[index] = mark;
            //printGameFieldToLog();
            lastPointPosition = connectNumbers[index];
            return true;
        } else {
            return false;
        }
    }


    private boolean isWin(int mark) {
        int countPoint = 0;

        for(int i = 0; i < ROWS; ++i) {
            for(int j = 0; j < COLUMNS - 3; ++j) {
                //System.out.append(String.valueOf(i) + " " + String.valueOf(j));
                for(int h = 0; h < 4; ++h) {
                    if(h == 1 && countPoint == 0) { break; }
                    if (gameField[COLUMNS * i + j + h] == mark) {
                        ++countPoint;
                    } else {
                        countPoint = 0;
                    }
                    if (countPoint == 4) {
                        return true;
                    }
                }
                countPoint = 0;
            }
        }

        countPoint = 0;
        for(int j = 0; j < COLUMNS; ++j) {
            for(int i = ROWS - 1; i >= ROWS - 3; --i) {
                for(int h = 0; h < 4; ++h) {
                    if(h == 1 && countPoint == 0) { break; }
                    if (gameField[(i - h) * COLUMNS + j] == mark) {
                        ++countPoint;
                    } else {
                        countPoint = 0;
                    }
                    if (countPoint == 4) {
                        return true;
                    }
                }
                countPoint = 0;
            }
        }

        countPoint = 0;
        for(int i = ROWS - 1; i >= ROWS - 3; --i) {
            for(int j = 0; j <= COLUMNS - 4; ++j) {
                for(int h = 0; h < 4; ++h) {
                    if(h == 1 && countPoint == 0) { break; }
                    if(gameField[(i - h) * COLUMNS + j + h] == mark) {
                        ++countPoint;
                    } else {
                        countPoint = 0;
                    }
                    if(countPoint == 4) {
                        return true;
                    }
                }
                countPoint = 0;
            }
        }

        countPoint = 0;
        for(int i = ROWS - 1; i >= ROWS - 3; --i) {
            for(int j = 3; j < COLUMNS; ++j) {
                for(int h = 0; h < 4; ++h) {
                    if(h == 1 && countPoint == 0) { break; }
                    if(gameField[(i - h) * COLUMNS + j - h] == mark) {
                        ++countPoint;
                    } else {
                        countPoint = 0;
                    }
                    if(countPoint == 4) {
                        return true;
                    }
                }
                countPoint = 0;
            }
        }



/*
        int countPoints = 0;
        for(int i = 0; i < ROWS; ++i) {
            countPoints = 0;
            for(int j = 0; j < COLUMNS; ++j) {
                if (gameField[COLUMNS * i + j] == mark) {
                    ++countPoints;
                } else {
                    countPoints = 0;
                }
                if(countPoints == 4) {
                    return true;
                }
            }
        }

        for(int j = 0; j < COLUMNS; ++j) {
            countPoints = 0;
            for(int i = 0; i < ROWS; ++i) {
                if (gameField[COLUMNS * i + j] == mark) {
                    ++countPoints;
                } else {
                    countPoints = 0;
                }
                if(countPoints == 4) {
                    return true;
                }
            }
        }

        for(int i = 0; i < ROWS - 3; ++i) {
            for (int j = 0; j < COLUMNS - 2; ++j) {
                countPoints = 0;
                for (int h = 0, g = 0; h < 4 && COLUMNS * i + j + g < ROWS * COLUMNS; ++h, g += COLUMNS + 1) {
                    if (gameField[COLUMNS * i + j + g] == mark) {
                        ++countPoints;
                        if (countPoints == 4) {
                            return true;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < ROWS - 3; ++i) {
            for (int j = 3; j < COLUMNS; ++j) {
                countPoints = 0;
                for (int h = 0, g = 0; h < 4 && COLUMNS * i + j + g < ROWS * COLUMNS; ++h, g += COLUMNS - 1) {
                    if (gameField[COLUMNS * i + j + g] == mark) {
                        ++countPoints;
                        if (countPoints == 4) {
                            return true;
                        }
                    }
                }
            }
        }
*/

            return false;
        }

    //private void changeTurn() { turnFirstPlayer = !turnFirstPlayer; }

    public void printGameFieldToLog() {
        for(int i = 0; i < ROWS; ++i) {
            for(int j = 0; j < COLUMNS; ++j) {
                System.out.append(String.valueOf(gameField[COLUMNS * i + j]) + "   ");
            }
            System.out.append('\n');
        }
        System.out.append('\n');
    }

}
