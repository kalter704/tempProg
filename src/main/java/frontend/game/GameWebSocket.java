package frontend.game;

import base.GameMechanics;
import base.WebSocketService;
import mechanics.GameUser;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import javax.jws.WebService;
import java.io.IOException;

@WebSocket
public class GameWebSocket {
    private String name;
    private Session session;
    private WebSocketService webSocketService;
    private GameMechanics gameMechanics;

    public GameWebSocket(String name, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.name = name;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getName() { return name; }

    public void setSession(Session session) {this.session = session; }

    public Session getSession() { return session; }

    public void connectEnemy(GameUser user, boolean isTurn) {
        try {
            System.out.append(name + " ! GameWebSocket::connectEnemy" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "ready");
            jsonMessage.put("enemyName", user.getEnemyName());
            jsonMessage.put("chipColor", user.getPlayerColorStr());
            jsonMessage.put("enemyChipColor", user.getEnemyColorStr());
            jsonMessage.put("isMyTurn", isTurn);
            session.getRemote().sendString(jsonMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectRoom(GameUser user, boolean isTurn) {
        try {
            System.out.append(name + " ! GameWebSocket::connectRoom" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "ready");
            jsonMessage.put("enemyName", user.getEnemyName());
            jsonMessage.put("chipColor", user.getPlayerColorStr());
            jsonMessage.put("enemyChipColor", user.getEnemyColorStr());
            jsonMessage.put("isMyTurn", isTurn);
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }



    public void waitEnemy(String userName) {
        try {
            System.out.append(name + " ! GameWebSocket::waitEnemy" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "wait");
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    public void startGame(GameUser user, boolean isTurn) {
        try {
            System.out.append(name + " ! GameWebSocket::startGame" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "startGame");
            jsonMessage.put("enemyName", user.getEnemyName());
            jsonMessage.put("chipColor", user.getPlayerColorStr());
            jsonMessage.put("isMyTurn", isTurn);
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    public void startRound(GameUser user, boolean isTurn) {
        try {
            System.out.append(name + " ! GameWebSocket::startRound" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "startRound");
            jsonMessage.put("isMyTurn", isTurn);
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    public void changeTurn(int cell, boolean isTurn, boolean succesTurn) {
        try {
            System.out.append(name + " ! GameWebSocket::makeTurn" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "changeTurn");
            jsonMessage.put("succesTurn", succesTurn);
            jsonMessage.put("cell", cell);
            jsonMessage.put("isMyTurn", isTurn);
            System.out.append(name + " ! GameWebSocket::onMessage cellChoosed: " + String.valueOf(cell) + '\n');
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(String winner, int numRound) {
        try {
            System.out.append(name + " ! GameWebSocket::gameOver" + '\n');
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("status", "gameOver");
            jsonMessage.put("winner", winner);
            jsonMessage.put("runRound", numRound);
            session.getRemote().sendString(jsonMessage.toString());
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.append(name + " ! GameWebSocket::GameWebSocket onMessage" + '\n');
        try {
            JSONObject jsonMessage = new JSONObject(data);
            String status = jsonMessage.getString("status");
            if(status.equals("newGame")) {
                System.out.append(name + " ! GameWebSocket::onMessage newGame" + '\n');
                gameMechanics.registerUser(name);
            }
            if(status.equals("joinGame")) {
                System.out.append(name + " ! GameWebSocket::onMessage joinGame" + '\n');
                String nameEnemy = jsonMessage.getString("roomHolder");
                gameMechanics.selectGame(name, nameEnemy);
            }
            if(status.equals("ready")) {
                System.out.append(name + " ! GameWebSocket::onMessage ready" + '\n');
                gameMechanics.readyPlayer(name, "true");
            }
            if(status.equals("collumnChoosed")) {
                int column = jsonMessage.getInt("collumn");
                System.out.append(name + " ! GameWebSocket::onMessage collumnChoosed: " + String.valueOf(column) + '\n');
                gameMechanics.makeTurn(name, column);
            }
            if(status.equals("playAgain")) {
                Boolean answer = jsonMessage.getBoolean("answer");
                System.out.append(name + " ! GameWebSocket::onMessage playAgain: answer = " + answer.toString() + '\n');
                // а тут надо доделать!!!!!!!!!
                gameMechanics.beginRound(name);
            }
        } catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        webSocketService.registerSocket(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        gameMechanics.deleteUser(name);
    }
}
