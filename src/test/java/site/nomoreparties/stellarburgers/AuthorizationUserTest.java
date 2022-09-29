package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.Client.ApiClient;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;
import java.util.Random;

public class AuthorizationUserTest {
    private ApiClient client;
    private UserData userData;
    private UserData responseRegistration;
    private UserData responseAuthorization;

    @Before
    public void setUp() {
        client = new ApiClient();
        //сгенерировать данные для регистрации
        String email = new Random().nextInt(100) + "@mail.ru";
        String name = new Random().nextInt(100) + "Mia";
        String password = "12345";
        //объявить данные пользователя
        userData = new UserData(email, password, name, null, null, null, null, null);
        //зарегистрироваться
        responseRegistration = client.createRegistration(userData).then().extract().as(UserData.class);
    }

    @After
    public void deleteUser() {
        //если пользователь был создан
        if(responseRegistration.getAccessToken() != null) {
            //получить токен пользователя, сгенерированный при регистрации
            String accessToken = responseRegistration.getAccessToken();
            //удалить пользователя
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Авторизация под существующим пользователем")
    public void validAuthorization() {
        responseAuthorization = client.createAuthorization(userData)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        Assert.assertTrue(responseAuthorization.getSuccess());
    }

    @Test
    @DisplayName("Авторизация с неверным логином и паролем")
    public void invalidLogInAndPasswordAuthorization() {
        //объявить невалидные данные пользователя
        final UserData invalidUserData = new UserData("qaqa@ya.ru", "12", null, null, null, null, null, null);
        //авторизоваться с невалидными данными
        responseAuthorization = client.createAuthorization(invalidUserData)
                .then()
                .statusCode(401)
                .extract().as(UserData.class);
        Assert.assertFalse(responseAuthorization.getSuccess());
        Assert.assertEquals("email or password are incorrect", responseAuthorization.getMessage());
    }
}