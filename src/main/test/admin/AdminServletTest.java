package admin;

import base.AccountService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdminServletTest {

    @Test
    public void testDoGetNotShutdown() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("shutdown")).thenReturn("opiuh");
        when(request.getParameter("count_regist")).thenReturn(null);
        when(request.getParameter("count_logged")).thenReturn(null);
        when(request.getParameter("admin")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountService.class);

        AdminServlet adminServlet = new AdminServlet(accountService);
        adminServlet.doGet(request, response);

        final String rightResponse = '{'
                + "\"count_regist\":-1,"
                + "\"count_logged\":-1,"
                + "\"NumberFormatException\":true,"
                + "\"singin\":true"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
    }

    @Test
    public void testDoGetNotSinginAdmin() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("shutdown")).thenReturn("opiuh");
        when(request.getParameter("count_regist")).thenReturn(null);
        when(request.getParameter("count_logged")).thenReturn(null);
        when(request.getParameter("admin")).thenReturn("oij");
        when(request.getParameter("password")).thenReturn("admin");

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountService.class);

        AdminServlet adminServlet = new AdminServlet(accountService);
        adminServlet.doGet(request, response);

        final String rightResponse = '{'
                + "\"count_regist\":-1,"
                + "\"count_logged\":-1,"
                + "\"singin\":false"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
    }

    @Test
    public void testDoGetErrorAdmin() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("shutdown")).thenReturn("1000");
        when(request.getParameter("count_regist")).thenReturn("true");
        when(request.getParameter("count_logged")).thenReturn("true");
        when(request.getParameter("admin")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountService.class);

        AdminServlet adminServlet = new AdminServlet(accountService);
        adminServlet.doGet(request, response);

        final String rightResponse = '{'
                + "\"count_regist\":-1,"
                + "\"count_logged\":-1,"
                + "\"singin\":false"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
    }

    @Test
    public void testDoGetCountUsers() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("shutdown")).thenReturn(null);
        when(request.getParameter("count_regist")).thenReturn("true");
        when(request.getParameter("count_logged")).thenReturn("true");
        when(request.getParameter("admin")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountService.class);
        when(accountService.getRegisteredUsersCount()).thenReturn(10);
        when(accountService.getLoggedUsersCount()).thenReturn(5);

        AdminServlet adminServlet = new AdminServlet(accountService);
        adminServlet.doGet(request, response);

        final String rightResponse = '{'
                + "\"count_regist\":10,"
                + "\"count_logged\":5,"
                + "\"singin\":true"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).getRegisteredUsersCount();
        verify(accountService, times(1)).getLoggedUsersCount();
    }
}