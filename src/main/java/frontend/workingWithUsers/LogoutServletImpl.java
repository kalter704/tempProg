package frontend.workingWithUsers;

import base.Frontend;
import base.AccountService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServletImpl extends HttpServlet implements Frontend {
    public static final String PAGE_URL = "/exit";

    @NotNull
    private AccountService accountService;

    public LogoutServletImpl(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("exit", false);

        HttpSession session = request.getSession();

        if(accountService.logOut(session)) {
            jsonResponse.put("exit", true);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {}
}
