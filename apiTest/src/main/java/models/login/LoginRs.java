package models.login;

/**
 * Модель запроса для получения токена авторизации
 */
public class LoginRs {
    private final String password;
    private final String username;

    public LoginRs(String password, String username) {
        this.password = password;
        this.username = username;
    }

    @Override
    public String toString() {
        return
                "LoginRs{" +
                        "password = '" + password + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}
