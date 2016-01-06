package base;

import java.util.List;

public interface GameMechanics {
    public void registerUser(String user);

    public void deleteUser(String user);

    public void selectGame(String user, String toUser);

    public void readyPlayer(String user, String isReadyStr);

    public void beginRound(String user);

    public void makeTurn(String user, int column);

    public List<String> getWaiter();

    public void run();
}