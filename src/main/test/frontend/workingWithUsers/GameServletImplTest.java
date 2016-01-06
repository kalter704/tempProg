package frontend.workingWithUsers;

import base.AccountService;
import base.Frontend;
import base.GameMechanics;
import org.junit.Test;
import services.AccountService.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameServletImplTest {

    @Test
    public void testDoGetWithAuth() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        AccountService accountService = mock(AccountServiceImpl.class);
        GameMechanics gameMechanics = mock(GameMechanics.class);
        when(accountService.checkAuth(session)).thenReturn(true);
        when(accountService.getNameBySession(session)).thenReturn("qwerty");

        Frontend gameServlet = new GameServletImpl(accountService, gameMechanics);
        gameServlet.doGet(request, response);

        String rightResponse = '{'
                + "\"auth\":true,"
                + "\"name\":\"qwerty\""
                + '}';
        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).checkAuth(session);
    }

    @Test
    public void testDoGetWithNotAuth() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        AccountService accountService = mock(AccountServiceImpl.class);
        GameMechanics gameMechanics = mock(GameMechanics.class);
        when(accountService.checkAuth(session)).thenReturn(false);
        when(accountService.getNameBySession(session)).thenReturn("qwerty");

        Frontend gameServlet = new GameServletImpl(accountService, gameMechanics);
        gameServlet.doGet(request, response);

        String rightResponse = '{'
                + "\"auth\":false,"
                + "\"name\":\"\""
                + '}';
        assertEquals(rightResponse, stringWriter.toString());
        verify(accountService, times(1)).checkAuth(session);
    }
}