package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.data.Courier;
import org.example.webclients.CouriersClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.util.Constants.*;
import static org.hamcrest.Matchers.equalTo;


public class CreateCourierTest {
    private static CouriersClient couriersClient;
    private static String login;
    private static String password;
    private static String firstName;
    private static Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL.getConstant();
        couriersClient = new CouriersClient();
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
        firstName = RandomStringUtils.randomAlphabetic(20);
        courier = new Courier(login, password, firstName);
    }

    @After
    public void cleanDb() {
        ValidatableResponse dataCourier = couriersClient.loginCourierResponse(
                new Courier(courier.getLogin(), courier.getPassword()));
        Integer courierId = dataCourier.extract().path("id");
        if (courierId != null) {
            couriersClient.deleteCourierResponse(courierId);
        }
    }

    @Test
    @DisplayName("Test Create Courier")
    @Description("Проверяем, что курьер создан, возвращается статус 201 и сообщение ОК")
    public void testCreateCourierSuccess() {
        ValidatableResponse createRandomCourier = couriersClient.createCourierResponse(courier);
        createRandomCourier
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Test Create Courier With Duplicate Login")
    @Description("При запросе с повторяющимся логином жидаемый статус ответа: 409")
    public void testCreateCourierWithDuplicateLogin() {
        couriersClient.createCourierResponse(courier).statusCode(201);
        ValidatableResponse duplicateLogin =
                couriersClient.createCourierResponse(courier);
        duplicateLogin
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Test Error Message For Request Without Login")
    @Description("Если поле логин пустое, получаем статус-код 400 и сообщение об ошибке")
    public void testErrorMessageForRequestWithoutLogin() {
        ValidatableResponse emptyLoginField = couriersClient.
                createCourierResponse(new Courier
                        (null, EXISTING_PASSWORD.getConstant(), EXISTING_FIRSTNAME.getConstant()));
        emptyLoginField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Test Error Message For Request Without Password")
    @Description("Если поле пароля пустое, получаем статус-код 400 и сообщение об ошибке")
    public void testErrorMessageForRequestWithoutPassword() {
        ValidatableResponse emptyLoginField = couriersClient.
                createCourierResponse(new Courier
                        (EXISTING_LOGIN.getConstant(), null, EXISTING_FIRSTNAME.getConstant()));
        emptyLoginField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
