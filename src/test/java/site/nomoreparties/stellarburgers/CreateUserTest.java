package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.Client.ApiClient;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;
import java.util.Random;

public class CreateUserTest {
    private String email;
    private String name;
    private String password;
    private ApiClient client;
    private UserData userData;
    private UserData responseRegistration;

    @Before
    public void setUp() {
        client = new ApiClient();
        //сгенерировать данные для регистрации
        email = new Random().nextInt(100) + "@mail.ru";
        name = new Random().nextInt(100) + "Mia";
        password = "12345";
        //объявить данные пользователя
        userData = new UserData(email, password, name, null, null, null, null, null);
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
    @DisplayName("Создать уникального пользователя")
    public void validCreateUserTest() {
        //зарегистрировать юзера
        responseRegistration = client.createRegistration(userData)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        //проверить, что вернулся не пустой ответ
        Assert.assertNotNull(responseRegistration);
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createNotUniqueUserTest() {
        //зарегистрировать юзера
        responseRegistration = client.createRegistration(userData).then().statusCode(200).extract().as(UserData.class);
        //зарегистрировать этого же пользователя еще раз
        UserData responseRegistrationNotUniqueUser = client.createRegistration(userData)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("User already exists", responseRegistrationNotUniqueUser.getMessage());
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей: емейл")
    public void createUserWithEmptyEmailFieldTest() {
        userData = new UserData("", password, name, null, null, null, null, null);
        responseRegistration = client.createRegistration(userData)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("Email, password and name are required fields", responseRegistration.getMessage());
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей: пароль")
    public void createUserWithEmptyPasswordFieldTest() {
        userData = new UserData(email, "", name, null, null, null, null, null);
        responseRegistration = client.createRegistration(userData)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        Assert.assertEquals("Email, password and name are required fields", responseRegistration.getMessage());
    }
}
