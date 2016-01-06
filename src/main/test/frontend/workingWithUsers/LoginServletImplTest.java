package frontend.workingWithUsers;

import base.Frontend;
import base.AccountService;
import services.AccountService.AccountServiceImpl;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LoginServletImplTest {

    @Test
    public void testDoPost() throws Exception {
        final String MY_NAME = "userName";
        final String MY_PASSWORD = "123456";

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(MY_NAME);
        when(request.getParameter("password")).thenReturn(MY_PASSWORD);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountServiceImpl.class);
        when(accountService.singIn(session, MY_NAME, MY_PASSWORD)).thenReturn((long) 1);

        Frontend loginServlet = new LoginServletImpl(accountService);
        loginServlet.doPost(request, response);

        final String rightResponse = '{'
                + "\"auth\":true" + ','
                + "\"name\":" + '\"' + MY_NAME + '\"' + ','
                + "\"NotNull\":true"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).singIn(session, MY_NAME, MY_PASSWORD);
    }

    @Test
    public void testDoPostErrors() throws Exception {
        final String MY_NAME = "";
        final String MY_PASSWORD = "";

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountServiceImpl.class);

        Frontend loginServlet = new LoginServletImpl(accountService);
        loginServlet.doPost(request, response);

        String rightResponse = '{'
                + "\"auth\":false" + ','
                + "\"NotNull\":false"
                + '}';

        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(0)).singIn(session, MY_NAME, MY_PASSWORD);
    }
}