package site.nomoreparties.stellarburgers;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.Client.ApiClient;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;

import java.util.Random;

public class UpdateUserDataAfterAuthorizationTest {
    private ApiClient client;
    private UserData userData;
    private String email;
    private String name;
    private String password;
    private String accessToken;

    @Before
    public void setUp() {
        client = new ApiClient();
        //объявить данные для регистрации
        email = "a" + new Random().nextInt(500) + "@ya.ru";
        name = "Anna";
        password = "12345";
        //создать юзера
        userData = new UserData(email, password, name, null, null, null, null, null);
        //зарегистрировать юзера
        client.createRegistration(userData);
        //авторизоваться
        final UserData responseAuthorization = client.createAuthorization(userData)
                .then()
                .extract().as(UserData.class);
        //получить аксесс токен, сгенерированный при авторизации
        accessToken = responseAuthorization.getAccessToken();
        //получить с сервера данные пользователя, используя токен
        final UserData userDataGotByToken = client.getUserDataByAccessToken(accessToken)
                .then()
                .extract().as(UserData.class);
        name = userDataGotByToken.getUser().getName();
        email = userDataGotByToken.getUser().getEmail();
    }

    @After
    public void deleteUser() {
        client.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Обновление почты и имени пользователя для авторизованного пользователя")
    public void updateEmailAndNameOfUserTest() {
        //сгенерировть новые почту и имя
        String newEmail = new Random().nextInt() + email;
        String newName = new Random().nextInt() + name;
        userData = new UserData(newEmail, password, newName, null, null, null, null, null);
        //отправить на сервер новые данные
        final UserData updatedData = client.updateUserData(accessToken, userData)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        //проверка, что данные обновились
        Assert.assertEquals(newEmail, updatedData.getUser().getEmail());
        Assert.assertEquals(newName, updatedData.getUser().getName());
    }

    @Test
    @DisplayName("Обновление почты пользователя для авторизованного пользователя")
    public void updateEmailOfUserTest() {
        //сгенерировть новый  емейл
        String newEmail = new Random().nextInt() + email;
        userData = new UserData(newEmail, password, name, null, null, null, null, null);
        //отправить на сервер новые данные
        final UserData responseOfUpdateEmail = client.updateUserData(accessToken, userData)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        //проверка, что данные обновились
        Assert.assertEquals(newEmail, responseOfUpdateEmail.getUser().getEmail());
    }

    @Test
    @DisplayName("Обновление имени пользователя для авторизованного пользователя")
    public void updateNameOfUserTest() {
        //сгенерировть новое имя
        String newName = new Random().nextInt() + name;
        userData = new UserData(email, password, newName, null, null, null, null, null);
        //отправить на сервер новые данные
        final UserData responseOfUpdatedData = client.updateUserData(accessToken, userData)
                .then()
                .statusCode(200)
                .extract().as(UserData.class);
        //проверка, что данные обновились
        Assert.assertEquals(newName, responseOfUpdatedData.getUser().getName());
    }

    @Test
    @DisplayName("Обновление почты невалидными данными")
    @Issue("BUG-1")
    public void updateEmailOfUserWithInvalidDataTest() {
        //объявить данные пользователя
        userData = new UserData(email, password, name, null, null, null, null, null);
        //обновить данные (должна быть ошибка, тк этот емейл уже зарегистрирован в системе))
        final UserData responseOfUpdatedData = client.updateUserData(accessToken, userData)
                .then()
                .statusCode(403)
                .extract().as(UserData.class);
        //Баг: система должна выдавать ошибку, но она возвращает 200
        Assert.assertEquals("User with such email already exists", responseOfUpdatedData.getMessage());
    }
}
