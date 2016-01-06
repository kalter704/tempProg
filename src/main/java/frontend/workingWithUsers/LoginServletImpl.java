package frontend.workingWithUsers;

import base.Frontend;
import base.AccountService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginServletImpl extends HttpServlet implements Frontend {
    public static final String PAGE_URL = "/login";

    @NotNull
    private AccountService accountService;

    public LoginServletImpl(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {}

    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("name", login);
        jsonResponse.put("auth", false);
        jsonResponse.put("NotNull", false);

        HttpSession session = request.getSession();

        if (login != null && password != null) {
            jsonResponse.put("NotNull", true);
            if (accountService.singIn(session, login, password) != -1) {
                jsonResponse.put("auth", true);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
