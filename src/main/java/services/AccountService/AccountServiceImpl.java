package services.AccountService;

import services.UserProfile.UserProfile;
import base.AccountService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountServiceImpl implements AccountService {
    public static final int MIN_LENGTH_NAME = 2;
    public static final int MAX_LENGTH_NAME = 15;
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MAX_LENGTH_PASSWORD = 20;

    public static final String NAME_PATTERN = "^[a-zA-Z0-9_]{" + MIN_LENGTH_NAME + ',' + MAX_LENGTH_NAME + "}$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z]+$";
    public static final String PASSWORD_PATTERN = "^.{" + MIN_LENGTH_PASSWORD + ',' + MAX_LENGTH_PASSWORD + "}$";
    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private Map<String, UserProfile> sessions = new ConcurrentHashMap<>();

    private long _lastSessionId = 0;

    @Override
    public boolean singUp(@NotNull UserProfile userProfile) {
        if(isAvailableName(userProfile.getLogin())) {
            addUser(userProfile);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long singIn(@NotNull HttpSession session, @NotNull String login, @NotNull String password) {
        UserProfile profile = getUser(login);
        if (profile != null) {
            if (profile.getPassword().equals(password)) {
                long sessionId = getSessionIdAndIterat();
                session.setAttribute("userId", sessionId);
                addSessions(Long.toString(sessionId), profile);
                return sessionId;
            }
        }
        return -1;
    }

    @Override
    public boolean logOut(@NotNull HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        session.removeAttribute("userId");
        if (!sessions.isEmpty()) {
            sessions.remove(userId.toString());
        }
        return true;
    }

    @Override
    public boolean checkAuth(@NotNull HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        UserProfile userProfile = getSessions(userId.toString());
        return null != userProfile;
    }

    @Override
    public boolean isAvailableName(@NotNull String login) {
        return !users.containsKey(login);
    }

    @Override
    public boolean validationName(@NotNull String name) {
        return validation(name, NAME_PATTERN);
    }

    @Override
    public boolean validationEmail(@NotNull String email) {
        return validation(email, EMAIL_PATTERN);
    }

    @Override
    public boolean validationPassword(@NotNull String password) {
        return validation(password, PASSWORD_PATTERN);
    }

    @Override
    public int getRegisteredUsersCount(){
        return users.size();
    }

    @Override
    public int getLoggedUsersCount(){
        return sessions.size();
    }

    @Override
    public void addUser(@NotNull UserProfile userProfile) {
        users.put(userProfile.getLogin(), userProfile);
    }

    @Override
    public String getNameBySession(@NotNull HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "";
        }
        UserProfile userProfile = getSessions(userId.toString());
        return userProfile.getLogin();
    }

    @Override
    @Nullable
    public UserProfile getUser(@NotNull String userName) {
        return users.get(userName);
    }

    private void addSessions(@NotNull String sessionId, @NotNull UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    private UserProfile getSessions(@NotNull String sessionId) {
        return sessions.get(sessionId);
    }

    private long getSessionIdAndIterat() {
        return _lastSessionId++;
    }

    private boolean validation(@NotNull String str, @NotNull String regEx) {
        Matcher matcher = Pattern.compile(regEx).matcher(str);
        return matcher.matches();
    }

}
