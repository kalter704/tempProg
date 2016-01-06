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


public class LogoutServletImplTest {

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountServiceImpl.class);
        when(accountService.logOut(httpSession)).thenReturn(true);

        Frontend logoutServlet = new LogoutServletImpl(accountService);
        logoutServlet.doGet(request, response);

        final String rightResponse = "{\"exit\":true}";

        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).logOut(httpSession);
    }

    @Test
    public void testDoGetErrors() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(response.getWriter()).thenReturn(writer);

        AccountService accountService = mock(AccountServiceImpl.class);
        when(accountService.logOut(httpSession)).thenReturn(false);

        Frontend logoutServlet = new LogoutServletImpl(accountService);
        logoutServlet.doGet(request, response);

        final String rightResponse = "{\"exit\":false}";

        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).logOut(httpSession);
    }

}