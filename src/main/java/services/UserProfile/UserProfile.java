package services.UserProfile;


import org.jetbrains.annotations.NotNull;

public class UserProfile {
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    @NotNull
    public String getEmail() {
        return email;
    }
}
