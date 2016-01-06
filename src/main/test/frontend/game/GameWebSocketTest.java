package frontend.game;

import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import mechanics.GameMechanicsImpl;
import org.junit.Test;
import services.AccountService.AccountServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GameWebSocketTest {

    @Test
    public void testGetName() throws Exception {
        WebSocketService webSocketService = mock(WebSocketService.class);
        GameMechanics gameMechanics = mock(GameMechanics.class);

        GameWebSocket gameWebSocket = new GameWebSocket("user1", gameMechanics, webSocketService);

    }

    @Test
    public void testSetSession() throws Exception {

    }

    @Test
    public void testGetSession() throws Exception {

    }

    @Test
    public void testConnectEnemy() throws Exception {

    }

    @Test
    public void testConnectRoom() throws Exception {

    }

    @Test
    public void testWaitEnemy() throws Exception {

    }

    @Test
    public void testStartGame() throws Exception {

    }

    @Test
    public void testStartRound() throws Exception {

    }

    @Test
    public void testMakeTurn() throws Exception {

    }

    @Test
    public void testGameOver() throws Exception {

    }

    @Test
    public void testNextTurn() throws Exception {

    }

    @Test
    public void testOnMessage() throws Exception {

    }

    @Test
    public void testOnOpen() throws Exception {

    }

    @Test
    public void testOnClose() throws Exception {

    }
}