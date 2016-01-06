package mechanics;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vasiliy on 25.10.15.
 */
public class GameUserTest {

    @Test
    public void testGetName() throws Exception {
        GameUser user = new GameUser("user1");
        user.setEnemyName("user2");
        assertTrue(user.getName().equals("user1"));
        assertTrue(user.getEnemyName().equals("user2"));
    }

    @Test
    public void testSetColorToMe() {
        GameUser user = new GameUser("user1");
        user.setColorToMe(GameUser.BLUE_COLOR);
        assertTrue(user.getPlayerColor() == GameUser.BLUE_COLOR);
        assertTrue(user.getEnemyColor() == GameUser.RED_COLOR);

        GameUser user2 = new GameUser("user2");
        user2.setColorToMe(GameUser.RED_COLOR);
        assertTrue(user2.getPlayerColor() == GameUser.RED_COLOR);
        assertTrue(user2.getEnemyColor() == GameUser.BLUE_COLOR);
    }

    @Test
    public void setRandomColorToMe() {
        GameUser user = new GameUser("user1");
        user.setRandomColorToMe();
        assertTrue(user.getPlayerColor() == GameUser.BLUE_COLOR || user.getPlayerColor() == GameUser.RED_COLOR);
    }

}