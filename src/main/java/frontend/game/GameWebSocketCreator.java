package frontend.game;

import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.HttpSession;

public class GameWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketCreator(AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        HttpSession session = req.getHttpServletRequest().getSession();
        String name = accountService.getNameBySession(session);
        GameWebSocket gameWebSocket = new GameWebSocket(name, gameMechanics, webSocketService);
        return gameWebSocket;
    }
}
