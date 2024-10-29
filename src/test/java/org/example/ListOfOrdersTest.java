package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.example.util.Constants.BASE_URL;
import static org.example.util.Constants.ORDER_API;
import static org.hamcrest.Matchers.*;

public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL.getConstant();
    }

    @Test
    @DisplayName("Check Receiving the Order List")
    @Description("Проверяем, что можем получить заказы из списка заказов")
    public void testReceivingOrderList() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_API.getConstant())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", instanceOf(List.class)) // Проверяем, что orders - список
                .body("orders", hasSize(greaterThanOrEqualTo(1))); // Проверяем, что в списке есть заказы
    }
}
