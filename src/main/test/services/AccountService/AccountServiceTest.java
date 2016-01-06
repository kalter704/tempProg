package services.AccountService;

import services.UserProfile.UserProfile;
import base.AccountService;
import org.junit.Test;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService accountService = new AccountServiceImpl();

    @Test
    public void testSingUp() throws Exception {
        UserProfile user = new UserProfile("userSingUp", "123456", "user@mail.ru");
        assertTrue(accountService.singUp(user));
        UserProfile userResult = accountService.getUser(user.getLogin());
        assertNotNull(userResult);
        assertEquals(user.getLogin(), userResult.getLogin());
        assertEquals(user.getPassword(), userResult.getPassword());
        assertEquals(user.getEmail(), userResult.getEmail());
    }

    @Test
    public void testSingUpErrorName() throws Exception {
        UserProfile user1 = new UserProfile("user1", "123456", "user@mail.ru");
        UserProfile user2 = new UserProfile("user1", "12ewe", "qwe@mail.ru");

        assertTrue(accountService.singUp(user1));
        assertFalse(accountService.singUp(user2));

        UserProfile user1Result = accountService.getUser(user1.getLogin());
        assertNotNull(user1Result);
        assertEquals(user1.getLogin(), user1Result.getLogin());
        assertEquals(user1.getPassword(), user1Result.getPassword());
        assertEquals(user1.getEmail(), user1Result.getEmail());

        UserProfile user2Result = accountService.getUser(user2.getLogin());
        assertNotNull(user2Result);
        assertEquals(user2.getLogin(), user2Result.getLogin());
        assertNotEquals(user2.getPassword(), user2Result.getPassword());
        assertNotEquals(user2.getEmail(), user2Result.getEmail());
    }

    @Test
    public void testSingUpErrors() throws Exception {
        UserProfile user = new UserProfile("userSingUpErrors", "123456", "user@mail.ru");
        accountService.singUp(user);
        UserProfile userResult = accountService.getUser(user.getLogin());
        assertNotNull(userResult);
        assertNotEquals(userResult.getLogin(), "qwerty");
    }

    @Test
    public void testSingIn() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userId")).thenReturn((long) 0);
        UserProfile user = new UserProfile("userSingUp", "123456", "user@mail.ru");
        accountService.singUp(user);
        accountService.singIn(session, user.getLogin(), user.getPassword());
        assertTrue(accountService.checkAuth(session));
        verify(session, times(1)).getAttribute("userId");
    }

    @Test
    public void testCheckAuth() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userId")).thenReturn((long) 0);
        UserProfile user = new UserProfile("userCheckAuth", "123456", "user@mail.ru");
        accountService.singUp(user);
        accountService.singIn(session, user.getLogin(), user.getPassword());
        assertTrue(accountService.checkAuth(session));
        verify(session, times(1)).getAttribute("userId");
    }

    @Test
    public void testCheckAuthErrors() throws Exception {
        HttpSession session = mock(HttpSession.class);
        UserProfile user = new UserProfile("userCheckAuth", "123456", "user@mail.ru");
        accountService.singUp(user);
        accountService.singIn(session, user.getLogin(), user.getPassword());
        HttpSession temp_session = mock(HttpSession.class);
        when(temp_session.getAttribute("userId")).thenReturn(null);
        assertFalse(accountService.checkAuth(temp_session));
        verify(temp_session, times(1)).getAttribute("userId");
    }

    @Test
    public void testIsAvailableName() throws Exception {
        UserProfile user1 = new UserProfile("userIsAvailableName1", "123456", "user@mail.ru");
        UserProfile user2 = new UserProfile("userIsAvailableName2", "123456", "user@mail.ru");
        UserProfile user3 = new UserProfile("userIsAvailableName3", "123456", "user@mail.ru");
        accountService.singUp(user1);
        accountService.singUp(user2);
        accountService.singUp(user3);
        assertTrue(accountService.isAvailableName("my_name"));
    }

    @Test
    public void testIsAvailableNameErrors() throws Exception {
        UserProfile user1 = new UserProfile("userIsAvailableName1", "123456", "user@mail.ru");
        UserProfile user2 = new UserProfile("userIsAvailableName2", "123456", "user@mail.ru");
        UserProfile user3 = new UserProfile("userIsAvailableName3", "123456", "user@mail.ru");
        accountService.singUp(user1);
        accountService.singUp(user2);
        accountService.singUp(user3);
        assertFalse(accountService.isAvailableName("userIsAvailableName1"));
    }

    @Test
    public void testValidationName() throws Exception {
        String name1 = "wergw34tser";
        assertTrue(accountService.validationName(name1));
        String name2 = "231231412";
        assertTrue(accountService.validationName(name2));
        String name3 = "KYEGFWEF";
        assertTrue(accountService.validationName(name3));
    }

    @Test
    public void testValidationNameErrors() throws Exception {
        String name1 = "w";
        assertFalse(accountService.validationName(name1));
        String name2 = "231231412lif3g74ofqgl3h4lfRFKU6fdku6rid";
        assertFalse(accountService.validationName(name2));
        String name3 = "#wfs^nrt";
        assertFalse(accountService.validationName(name3));
    }

    @Test
    public void testValidationPassword() throws Exception {
        String password1 = "waeg$awef";
        assertTrue(accountService.validationPassword(password1));
        String password2 = "34634534tgergdsr";
        assertTrue(accountService.validationPassword(password2));
        String password3 = "KYUguyT@UYGjkyg";
        assertTrue(accountService.validationPassword(password3));
    }

    @Test
    public void testValidationPasswordErrors() throws Exception {
        String password1 = "wae";
        assertFalse(accountService.validationPassword(password1));
        String password2 = "34634534tgergdsrhergsergserhsfgfggaegaerg";
        assertFalse(accountService.validationPassword(password2));
    }

    @Test
    public void testValidationEmail() throws Exception {
        String emal1 = "qwe@mail.ru";
        assertTrue(accountService.validationEmail(emal1));

        String email2 = "asd";
        assertFalse(accountService.validationEmail(email2));

        String email3 = "asd@qwf";
        assertFalse(accountService.validationEmail(email3));

        String email4 = "@qwe.ru";
        assertFalse(accountService.validationEmail(email4));
    }

    @Test
    public void testGetRegisteredUsersCount() throws Exception {
        UserProfile user1 = new UserProfile("userIsAvailableName1", "123456", "user@mail.ru");
        UserProfile user2 = new UserProfile("userIsAvailableName2", "123456", "user@mail.ru");
        UserProfile user3 = new UserProfile("userIsAvailableName3", "123456", "user@mail.ru");

        accountService.singUp(user1);
        accountService.singUp(user2);
        accountService.singUp(user3);

        assertEquals(3, accountService.getRegisteredUsersCount());
    }

    @Test
    public void testGetLoggedUsersCount() throws Exception {
        UserProfile user1 = new UserProfile("userIsAvailableName1", "123456", "user@mail.ru");
        UserProfile user2 = new UserProfile("userIsAvailableName2", "123456", "user@mail.ru");
        UserProfile user3 = new UserProfile("userIsAvailableName3", "123456", "user@mail.ru");

        accountService.singUp(user1);
        accountService.singUp(user2);
        accountService.singUp(user3);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userId")).thenReturn((long) 0);

        accountService.singIn(session, user1.getLogin(), user1.getPassword());
        accountService.singIn(session, user2.getLogin(), user2.getPassword());
        accountService.singIn(session, user3.getLogin(), user3.getPassword());

        assertEquals(3, accountService.getLoggedUsersCount());
    }

    @Test
    public void testGetNameBySession() {
        UserProfile user1 = new UserProfile("user1", "123456", "user@mail.ru");
        accountService.singUp(user1);
        UserProfile user2 = new UserProfile("user2", "123456", "user@mail.ru");
        accountService.singUp(user2);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userId")).thenReturn((long) 0);
        accountService.singIn(session, user1.getLogin(), user1.getPassword());

        assert(user1.getLogin().equals(accountService.getNameBySession(session)));
    }
}