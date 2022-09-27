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

public class UpdateUserDataWithoutAuthorizationTest {
    private ApiClient client;
    private String accessToken;

    @Before
    public void setUp() {
        client = new ApiClient();
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    @Issue("BUG-2")
    public void updateUserDataWithoutAuthorizationTest() {
        //объявить имя и почту для регистрации
        String email = "a" + new Random().nextInt(500) + "@ya.ru";
        String name = "Anna";
        String password = "12345";
        //создать юзера
        final UserData userData = new UserData(email, password, name, null, null, null, null, null);
        //зарегистрировать юзера
        final UserData responseRegistration = client.createRegistration(userData)
                .then()
                .extract().as(UserData.class);
        //получить аксесс токен, сгенерированный при регистрации
        accessToken = responseRegistration.getAccessToken();
        //получить с сервера данные пользователя, используя токен
        final UserData userDataGotByToken = client.getUserDataByAccessToken(accessToken)
                .then()
                .extract().as(UserData.class);
        name = userDataGotByToken.getUser().getName();
        email = userDataGotByToken.getUser().getEmail();
        //объявить новые данные пользователя
        final UserData newUserData = new UserData(new Random().nextInt(500) + email, password, name + new Random().nextInt(50), null, null, null, null, null);
        //отправить на сервер новые данные
        final UserData updatedData = client.updateUserData(accessToken, newUserData)
                .then()
                .statusCode(401)
                .extract().as(UserData.class);
        //Баг: должна быть ошибка, так как мы обновляем данные пользователя без авторизации, но возвращается 200
        Assert.assertFalse(updatedData.getSuccess());
        Assert.assertEquals("You should be authorised", updatedData.getMessage());
    }

    @After
    public void deleteUser() {
        client.deleteUser(accessToken);
    }
}
