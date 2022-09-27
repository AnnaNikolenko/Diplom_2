package site.nomoreparties.stellarburgers;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.Client.ApiClient;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;

import java.util.Random;

public class CreateUserTest {
    private ApiClient client;

    @Before
    public void setUp() {
        client = new ApiClient();
    }

    @Test
    @DisplayName("Создать уникального пользователя")
    public void validCreateUserTest() {
        final UserData registration = new UserData("a" + new Random().nextInt(1000) + "@ya.ru", "12345", "Anna", null, null, null, null, null);
        UserData responseRegistration = client.createRegistration(registration)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        //проверить, что вернулся не пустой ответ
        Assert.assertNotNull(responseRegistration);

        String accessToken = responseRegistration.getAccessToken();
        client.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createNotUniqueUserTest() {
        final UserData registration = new UserData("test-data@yandex.ru", "12345", "Anna", null, null, null, null, null);
        final UserData responseRegistration = client.createRegistration(registration)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("User already exists", responseRegistration.getMessage());
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей: емейл")
    public void createUserWithEmptyEmailFieldTest() {
        final UserData registration = new UserData("", "12345", "Anna", null, null, null, null, null);
        final UserData responseRegistration = client.createRegistration(registration)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("Email, password and name are required fields", responseRegistration.getMessage());
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей: пароль")
    public void createUserWithEmptyPasswordFieldTest() {
        final UserData registration = new UserData("a" + new Random().nextInt(500) + "@ya.ru", "", "Anna", null, null, null, null, null);
        final UserData responseRegistration = client.createRegistration(registration)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("Email, password and name are required fields", responseRegistration.getMessage());
    }


}
