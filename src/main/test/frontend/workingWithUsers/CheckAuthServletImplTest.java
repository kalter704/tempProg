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

public class CheckAuthServletImplTest {

    @Test
    public void testDoPostIsAuth() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(httpSession);

        AccountService accountService = mock(AccountServiceImpl.class);
        when(accountService.checkAuth(httpSession)).thenReturn(true);

        Frontend checkAuthServlet = new CheckAuthServletImpl(accountService);
        checkAuthServlet.doGet(request, response);

        assertEquals("{\"auth\":true}", stringWriter.toString());
        verify(accountService, times(1)).checkAuth(httpSession);
    }

    @Test
    public void testDoPostIsNotAuth() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(httpSession);

        AccountService accountService = mock(AccountServiceImpl.class);
        when(accountService.checkAuth(httpSession)).thenReturn(false);

        Frontend checkAuthServlet = new CheckAuthServletImpl(accountService);
        checkAuthServlet.doGet(request, response);

        assertEquals("{\"auth\":false}", stringWriter.toString());
        verify(accountService, times(1)).checkAuth(httpSession);
    }

}