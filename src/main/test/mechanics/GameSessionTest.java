package mechanics;

import org.junit.Test;
import utils.TimeHelper;

import static org.junit.Assert.*;

public class GameSessionTest {

    @Test
    public void testIsFirstWinInColumn() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(0);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());
    }

    @Test
    public void testIsFirstWinInRow() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        assertTrue(gameSession.setPointFirstPlayerByColumn(0));
        assertTrue(gameSession.setPointFirstPlayerByColumn(1));
        assertTrue(gameSession.setPointFirstPlayerByColumn(2));
        assertTrue(gameSession.setPointFirstPlayerByColumn(3));
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(4);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(3);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(2);
        //gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(2);
        //gameSession.printGameFieldToLog();
        assertFalse(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());
    }

    @Test
    public void testIsFirstWinInDiagonals() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointSecondPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointSecondPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointSecondPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointSecondPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointSecondPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointSecondPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointSecondPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(0);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        gameSession.startRound();
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(6);
        gameSession.setPointFirstPlayerByColumn(6);
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointFirstPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(4);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.printGameFieldToLog();
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());
    }

    @Test
    public void testTestT() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.startRound();
        gameSession.setPointSecondPlayerByColumn(0);
        gameSession.setPointFirstPlayerByColumn(2);
        gameSession.setPointSecondPlayerByColumn(2);
        gameSession.setPointFirstPlayerByColumn(1);
        gameSession.setPointSecondPlayerByColumn(1);
        gameSession.setPointFirstPlayerByColumn(3);
        gameSession.printGameFieldToLog();
        assertFalse(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());
    }

    /*
    @Test
    public void testIsFirstWinInDiagonals() throws Exception {

        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.setPointFirstPlayer(1, 1);
        gameSession.setPointFirstPlayer(2, 2);
        gameSession.setPointFirstPlayer(3, 3);
        gameSession.setPointFirstPlayer(4, 4);
        assertTrue(gameSession.isFirstWin());
        assertFalse(gameSession.isSecondWin());

        GameSession gameSession2 = new GameSession("user1", "user2");
        gameSession2.setPointFirstPlayer(3, 2);
        gameSession2.setPointFirstPlayer(2, 3);
        gameSession2.setPointFirstPlayer(1, 4);
        gameSession2.setPointFirstPlayer(0, 5);
        assertTrue(gameSession2.isFirstWin());
        assertFalse(gameSession2.isSecondWin());

        GameSession gameSession3 = new GameSession("user1", "user2");
        gameSession3.setPointFirstPlayer(5, 2);
        gameSession3.setPointFirstPlayer(4, 3);
        gameSession3.setPointFirstPlayer(3, 4);
        gameSession3.setPointFirstPlayer(2, 5);
        assertTrue(gameSession3.isFirstWin());
        assertFalse(gameSession3.isSecondWin());

    }
    */

    /*
    @Test
    public void testIsSecondWinInColumn() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(5);
        gameSession.setPointSecondPlayerByColumn(5);
        assertTrue(gameSession.isSecondWin());
        assertFalse(gameSession.isFirstWin());
    }

    @Test
    public void testIsSecondWinInRow() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.setPointSecondPlayerByColumn(2);
        gameSession.setPointSecondPlayerByColumn(3);
        gameSession.setPointSecondPlayerByColumn(4);
        gameSession.setPointSecondPlayerByColumn(5);
        assertTrue(gameSession.isSecondWin());
        assertFalse(gameSession.isFirstWin());
    }

    @Test
    public void testIsSecondWinInDiagonals() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        gameSession.setPointSecondPlayer(5, 6);
        gameSession.setPointSecondPlayer(4, 5);
        gameSession.setPointSecondPlayer(3, 4);
        gameSession.setPointSecondPlayer(2, 3);
        assertTrue(gameSession.isSecondWin());
        assertFalse(gameSession.isFirstWin());

        GameSession gameSession2 = new GameSession("user1", "user2");
        gameSession2.setPointSecondPlayer(2, 6);
        gameSession2.setPointSecondPlayer(3, 5);
        gameSession2.setPointSecondPlayer(4, 4);
        gameSession2.setPointSecondPlayer(5, 3);
        assertTrue(gameSession2.isSecondWin());
        assertFalse(gameSession2.isFirstWin());

        GameSession gameSession3 = new GameSession("user1", "user2");
        gameSession3.setPointSecondPlayer(0, 3);
        gameSession3.setPointSecondPlayer(1, 4);
        gameSession3.setPointSecondPlayer(2, 5);
        gameSession3.setPointSecondPlayer(3, 6);
        assertTrue(gameSession3.isSecondWin());
        assertFalse(gameSession3.isFirstWin());
    }

    @Test
    public void testNextTurn() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        boolean firstTrun = gameSession.isTurnFirstPlayer();
        TimeHelper.sleep(500);
        long sessionTime1 = gameSession.getSessionTime();

        gameSession.nextTurn();

        assertTrue(firstTrun != gameSession.isTurnFirstPlayer());
        assertTrue(sessionTime1 > gameSession.getRound());
    }

    @Test
    public void testStartRound() throws Exception {
        GameSession gameSession = new GameSession("user1", "user2");
        assertFalse(gameSession.isFullTable());
        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j< 7; ++j) {
                gameSession.setPointSecondPlayer(i, j);
            }
        }
        assertTrue(gameSession.isFullTable());
        gameSession.startRound();
        assertFalse(gameSession.isFullTable());
    }

    @Test
    public void testIsTurnByName() {
        GameSession gameSession = new GameSession("user1", "user2");
        boolean firstTrun = gameSession.isTurnFirstPlayer();
        assertTrue(firstTrun == gameSession.isTurnByName("user1"));
        assertTrue(firstTrun != gameSession.isTurnByName("user2"));
    }

    @Test
    public void getGameUserByName() {
        String name1 = "user1";
        String name2 = "user2";
        GameSession gameSession = new GameSession(name1, name2);
        GameUser user1 = gameSession.getGameUserByName(name1);
        GameUser user2 = gameSession.getGameUserByName(name2);
        assertTrue(name1.equals(user1.getName()));
        assertTrue(name2.equals(user2.getName()));
    }
    */

}