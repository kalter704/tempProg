package frontend.workingWithUsers;

import base.AccountService;
import base.Frontend;
import base.GameMechanics;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServletImpl extends HttpServlet implements Frontend {
    public static final String PAGE_URL = "/game";

    @NotNull
    private AccountService accountService;

    @NotNull
    private GameMechanics gameMechanics;

    public GameServletImpl(@NotNull AccountService accountService, @NotNull GameMechanics gameMechanics) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {

        List<String> waiters = gameMechanics.getWaiter();
        JSONArray jsonArrayResponse = new JSONArray();

        String name = accountService.getNameBySession(request.getSession());
        if(waiters.contains(name)) {
            waiters.remove(name);
        }
        for(String waiter: waiters) {
            jsonArrayResponse.put(waiter);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArrayResponse.toString().toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {}

}
