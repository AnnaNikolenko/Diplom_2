package site.nomoreparties.stellarburgers.pojo.Client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.pojo.Model.Ingredients;
import site.nomoreparties.stellarburgers.pojo.Model.OrderData;
import site.nomoreparties.stellarburgers.pojo.Model.UserData;

public class ApiClient {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    //логировать запросы и ответы
    private final Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFilter = new ResponseLoggingFilter();

    @Step("Объявлен метод создания пользователя, отправляющий запрос на сервер")
    public Response createRegistration(UserData registration) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(BASE_URL)
                .body(registration)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/auth/register");
    }

    @Step("Логин под существующим пользователем")
    public Response createAuthorization(UserData registration) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(BASE_URL)
                .body(registration)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/auth/login");
    }

    @Step("Получение данных пользователя")
    public Response getUserDataByAccessToken(String accessToken) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .header("Authorization", accessToken)
                .baseUri(BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/api/auth/user");
    }

    @Step("Изменение данных пользователя")
    public Response updateUserData(String accessToken, UserData userData) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .header("Authorization", accessToken)
                .baseUri(BASE_URL)
                .body(userData)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .patch("/api/auth/user");
    }

    @Step("Изменение данных пользователя")
    public Response deleteUser(String accessToken) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .header("Authorization", accessToken)
                .baseUri(BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete("/api/auth/user");
    }


    @Step("Получение данных об ингредиентах")
    public Response getIngredients() {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/api/ingredients");
    }

    @Step("Создание заказа")
    public Response createOrder(OrderData orderData) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(BASE_URL)
                .body(orderData)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/orders");
    }


}
