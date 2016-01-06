package admin;

import base.AccountService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AdminServlet extends HttpServlet {
    public static final String PAGE_URL = "/admin";
    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @NotNull
    private AccountService accountService;

    public AdminServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {
        String shutdown = request.getParameter("shutdown");
        String registCount = request.getParameter("count_regist");
        String logCount = request.getParameter("count_logged");
        String admin_name = request.getParameter("admin");
        String admin_password = request.getParameter("password");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("count_regist", -1);
        jsonResponse.put("count_logged", -1);
        jsonResponse.put("singin", false);

        if(admin_name == null || admin_password == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if(!admin_name.equals(ADMIN_NAME) || !admin_password.equals(ADMIN_PASSWORD)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        jsonResponse.put("singin", true);

        if (shutdown != null && !shutdown.isEmpty()) {
            try {
                int shut = Integer.parseInt(shutdown);
                System.out.print("выключение сервера через " + shut + " ms");
                Thread.sleep(shut);
                System.out.print("\nShutdown");
                System.exit(0);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (NumberFormatException nfe) {
                jsonResponse.put("NumberFormatException", true);
            }
        }
        if (registCount != null && registCount.equals("true")) {
            jsonResponse.put("count_regist", accountService.getRegisteredUsersCount());
        }
        if (logCount != null && logCount.equals("true")) {
            jsonResponse.put("count_logged", accountService.getLoggedUsersCount());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

