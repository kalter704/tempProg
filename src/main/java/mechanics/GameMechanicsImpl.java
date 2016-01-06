package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import utils.TimeHelper;

import java.util.*;

public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int SESSION_TIME = 3600 * 1000;

    private static final int ROUND_TIME = 60 * 1000;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private List<String> waiters = new LinkedList<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    // Добавляем регистрируем игрока в механике и ждем выбора
    @Override
    public void registerUser(String user) {
        waiters.add(user);
        webSocketService.waitEnemy(user);
    }

    @Override
    public void deleteUser(String user) {
        waiters.remove(user);
    }

    // Игрок сделал выбор присоедиться к игре или создать новую
    @Override
    public void selectGame(String user, String toUser) {
        if(!toUser.equals("")) {
            waiters.remove(toUser);
            GameSession newGameSession = new GameSession(toUser, user);
            allSessions.add(newGameSession);
            nameToGame.put(user, newGameSession);
            nameToGame.put(toUser, newGameSession);
            webSocketService.notifyEnemyConnect(newGameSession.getGameUserByName(toUser), newGameSession.isTurnByName(toUser));
            webSocketService.notifyConnectToRoom(newGameSession.getGameUserByName(user), newGameSession.isTurnByName(user));
        } else {
            waiters.add(user);
            webSocketService.waitEnemy(user);
        }
    }

    // Установка готовности игрока/игроков
    @Override
    public void readyPlayer(String user, String isReadyStr) {
        boolean isReady = isReadyStr.equals("true");
        nameToGame.get(user).setPlayerReady(user, isReady);
    }

    // Если игроки готовы начинается игра
    public void startGame(GameSession session) {
        session.startGame();
        session.setNotReady();
        boolean isFirstPlayerTurn = session.isTurnFirstPlayer();
        webSocketService.notifyStartGame(session.getFirstPlayer(), isFirstPlayerTurn);
        webSocketService.notifyStartGame(session.getSecondPlayer(), !isFirstPlayerTurn);
    }

    @Override
    public void beginRound(String user) {
        //GameUser gameUser = nameToGame.get(user).getGameUserByName(user);
        nameToGame.get(user).startRound();
        GameUser first = nameToGame.get(user).getFirstPlayer();
        GameUser second = nameToGame.get(user).getSecondPlayer();
        webSocketService.notifyStartRound(first, nameToGame.get(user).isTurnByName(first.getName()));
        webSocketService.notifyStartRound(second, nameToGame.get(user).isTurnByName(second.getName()));
    }

    @Override
    public void makeTurn(String user, int column) {
        int col = column;
        GameSession gameSession = nameToGame.get(user);
        gameSession.setCurrectTimeToRound();
        boolean fullColumn = gameSession.setPointPlayerByColumn(user, col);
        if(!fullColumn) {
            webSocketService.notifyTurn(gameSession.getGameUserByName(user), -1, gameSession.isTurnByName(user), false);
            return;
        }
        boolean fullTable = gameSession.isFullTable();
        boolean isFirstWin = gameSession.isFirstWin();
        boolean isSecondWin = gameSession.isSecondWin();
        if(fullTable && !isFirstWin && !isSecondWin) {
            webSocketService.notifyTurn(gameSession.getGameUserByName(user), gameSession.getLastPointPosition(),gameSession.isTurnByName(user), true);
            webSocketService.notifyGameOver(gameSession.getGameUserByName(user), "nobody", gameSession.getRound());
            gameSession.incrementRound();
            return;
        }
        if(isFirstWin) {
            webSocketService.notifyTurn(gameSession.getGameUserByName(user), gameSession.getLastPointPosition(),gameSession.isTurnByName(user), true);
            webSocketService.notifyGameOver(gameSession.getGameUserByName(user), gameSession.getFirstPlayer().getName(), gameSession.getRound());
            gameSession.incrementRound();
            return;
        }
        if(isSecondWin) {
            webSocketService.notifyTurn(gameSession.getGameUserByName(user), gameSession.getLastPointPosition(),gameSession.isTurnByName(user), true);
            webSocketService.notifyGameOver(gameSession.getGameUserByName(user), gameSession.getSecondPlayer().getName(), gameSession.getRound());
            gameSession.incrementRound();
            return;
        }
        gameSession.nextTurn();
        webSocketService.notifyTurn(gameSession.getGameUserByName(user), gameSession.getLastPointPosition(),gameSession.isTurnByName(user), true);
    }

    /*
    public void palyAgain(String user) {
        GameSession gameSession = nameToGame.get(user);
        gameSession.startRound();
        webSocketService.notifyStartRound();
    }
    */

    @Override
    public List<String> getWaiter() {
        return waiters;
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for(GameSession session : allSessions) {
            if(session.isInGame()) {
                //Рано засекаешь начало раунда. Пока игроки входят в игру,
                //время раунда превышает заданное, и первый ход осуществляет
                //компьютер автоматом
                /*
                if (session.getRoundTime() > ROUND_TIME) {
                    String user;
                    if (session.isTurnFirstPlayer()) {
                        user = session.getFirstPlayer().getName();
                    } else {
                        user = session.getSecondPlayer().getName();
                    }
                    Random random = new Random();
                    makeTurn(user, random.nextInt(7));
                }
                */
                if (session.getSessionTime() > SESSION_TIME) {
                    allSessions.remove(session);
                    String user1 = session.getFirstPlayer().getName();
                    String user2 = session.getSecondPlayer().getName();
                    nameToGame.remove(user1);
                    nameToGame.remove(user2);
                }
            } else {
                if(session.isSessionReady()) {
                    startGame(session);
                }
            }
        }
    }

}
