package site.nomoreparties.stellarburgers;

import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.Client.ApiClient;
import site.nomoreparties.stellarburgers.pojo.Model.OrderData;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;
import java.util.List;
import java.util.Random;

public class CreateOrderTest {
    private ApiClient client;
    private UserData userData;
    private  UserData responseRegistration;

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
    @DisplayName("Создать заказ. Пользователь авторизован. Ингредиенты выбраны")
    public void createOrderWithValidIngredientsTest() {
        //авторизоваться
        client.createAuthorization(userData);
        //получить список ингредиентов из базы данных
        OrderData orderData = client.getIngredients()
                .then()
                .extract().as(OrderData.class);
        //выбрать пару ингредиентов для добавления в заказ
        OrderData chosenIngredients = new OrderData(List.of(orderData.getData().get(0).get_id(), orderData.getData().get(1).get_id()), null, null, null, null, null, List.of());
        //создать заказ с выбранными ингредиентами
        OrderData responseOfCreateOrder = client.createOrder(chosenIngredients)
                .then()
                .statusCode(200)
                .extract().as(OrderData.class);
        //проверить, что заказ создан успешно
        Assert.assertTrue(responseOfCreateOrder.getSuccess());
    }

    @Test
    @DisplayName("Создать заказ. Пользователь авторизован. Ингредиент с неверным хешем")
    @Issue("BUG-4")
    public void createOrderWithInvalidIngredientTest() {
        //авторизоваться
        client.createAuthorization(userData);
        //получить список ингредиентов из базы данных
        OrderData orderData = client.getIngredients()
                .then()
                .extract().as(OrderData.class);
        //передать невалидный хеш-код ингредиента для добавления в заказ
        OrderData ingredientsOfNewOrder = new OrderData(List.of(orderData.getData().get(0).get_id().replace("61c0c5a71d1f82001bdaaa6d", "Myid")), null, null, null, null, null, List.of());
        //создать заказ с выбранными ингредиентами
        client.createOrder(ingredientsOfNewOrder)
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Создать заказ. Пользователь авторизован. Ингредиенты не выбраны")
    public void createOrderWithoutIngredientsTest() {
        //зарегистрироваться
        client.createRegistration(userData);
        //авторизоваться
        client.createAuthorization(userData);
        //объявить пустой список ингредиентов для добавления в заказ
        OrderData orderData = new OrderData(List.of(), null, null, null, null, null, List.of());
        //создать заказ без ингредиентов
        OrderData responseOfCreateOrder = client.createOrder(orderData)
                .then()
                .statusCode(400)
                .extract().as(OrderData.class);
        //проверить, что вернулась ошибка
        Assert.assertFalse(responseOfCreateOrder.getSuccess());
        Assert.assertEquals("Ingredient ids must be provided", responseOfCreateOrder.getMessage());
    }

    @Test
    @DisplayName("Создать заказ. Пользователь не авторизован. Ингредиенты выбраны")
    @Issue("BUG-3")
    public void createOrderWithoutAuthorizationTest() {
        //получить список ингредиентов из базы данных
        OrderData orderData = client.getIngredients()
                .then()
                .extract().as(OrderData.class);
        //выбрать пару ингредиентов для добавления в заказ
        OrderData chosenIngredients = new OrderData(List.of(orderData.getData().get(0).get_id(), orderData.getData().get(1).get_id()), null, null, null, null, null, List.of());
        //создать заказ с выбранными ингредиентами
        OrderData responseOfCreateOrder = client.createOrder(chosenIngredients)
                .then()
                .statusCode(401)
                .extract().as(OrderData.class);
        //проверить, что заказ не создан (тк создание заказа доступно только авторизованным пользователям)
        Assert.assertFalse(responseOfCreateOrder.getSuccess());
    }
}
