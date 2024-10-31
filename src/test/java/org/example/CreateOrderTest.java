package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.data.Order;
import org.example.webclients.OrdersClient;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.example.util.Constants.BASE_URL;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> colorValue;

    public CreateOrderTest(List<String> colorValue) {
        this.colorValue = colorValue;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getScooterColor() {
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {null},
                {List.of("GREY", "BLACK")}
        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL.getConstant();
    }

    @Test
    @DisplayName("Test order creation")
    public void testOrderCreation() {
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse emptyPasswordField = ordersClient.createOrderResponse(
                new Order("Гарри", "Поттер", "Тисовая Улица,4", 7, "+7 800 355 35 35", 3, "2024-10-31", "Пришлите Нимбус 2000", colorValue));
        emptyPasswordField
                .statusCode(201);
        MatcherAssert.assertThat("track", notNullValue());
    }
}
