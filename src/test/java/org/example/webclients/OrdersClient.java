package org.example.webclients;

import org.example.data.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.util.Constants.ORDER_API;

public class OrdersClient {

    @Step("Получение ответа при создании заказа")
    public ValidatableResponse createOrderResponse(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_API.getConstant())
                .then();
    }

    @Step("Проверяем получение ответа при запросе списка заказов")
    public ValidatableResponse getOrdersResponse() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_API.getConstant())
                .then();
    }
}
