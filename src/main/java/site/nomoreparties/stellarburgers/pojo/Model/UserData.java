package site.nomoreparties.stellarburgers.pojo.Model;

import java.util.Objects;

public class UserData {
    private String email;
    private String password;
    private String name;
    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public UserData(String email, String password, String name, Boolean success, User user, String accessToken, String refreshToken, String message) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    public UserData() {
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getSuccess() {
        return success;
    }
    public User getUser() {
        return user;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", success=" + success +
                ", user=" + user +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData that = (UserData) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(success, that.success) && Objects.equals(user, that.user) && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, success, user, accessToken, refreshToken, message);
    }
}
