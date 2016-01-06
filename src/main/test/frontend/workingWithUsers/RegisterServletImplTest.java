package frontend.workingWithUsers;

import base.Frontend;
import base.AccountService;
import services.AccountService.AccountServiceImpl;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegisterServletImplTest {

    @Test
    public void testDoPost() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("email")).thenReturn("user@user.ru");

        AccountService accountService = new AccountServiceImpl();

        Frontend registerServlet = new RegisterServletImpl(accountService);

        registerServlet.doPost(request, response);

        final String rightResponse = '{'
                    + "\"password\":true,"
                    + "\"login\":\"notexists\","
                    + "\"signup\":true,"
                    + "\"NotNull\":true,"
                    + "\"email\":true"
                    + '}';

        assertEquals(rightResponse, stringWriter .toString());
    }

    @Test
    public void testDoPostNullParameters() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("email")).thenReturn("user@user.ru");

        AccountService accountService = new AccountServiceImpl();

        Frontend registerServlet = new RegisterServletImpl(accountService);

        registerServlet.doPost(request, response);

        final String rightResponse = '{'
                + "\"password\":true,"
                + "\"login\":\"notexists\","
                + "\"signup\":false,"
                + "\"NotNull\":false,"
                + "\"email\":true"
                + '}';

        assertEquals(rightResponse, stringWriter .toString());
    }

    @Test
    public void testDoPostNoValidationParameters() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("login")).thenReturn("q");
        when(request.getParameter("password")).thenReturn("12");
        when(request.getParameter("email")).thenReturn("useruser.ru");

        AccountService accountService = new AccountServiceImpl();

        Frontend registerServlet = new RegisterServletImpl(accountService);

        registerServlet.doPost(request, response);

        final String rightResponse = '{'
                + "\"password\":false,"
                + "\"login\":\"notexists\","
                + "\"signup\":false,"
                + "\"NotNull\":true,"
                + "\"email\":false"
                + '}';

        assertEquals(rightResponse, stringWriter .toString());
    }

}