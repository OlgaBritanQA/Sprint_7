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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {
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
        firstName = RandomStringUtils.randomAlphabetic(8);
        courier = new Courier(login, password, firstName);
        couriersClient.createCourierResponse(courier);
    }

    @After
    public void cleanDb() {
        ValidatableResponse dataCourier = couriersClient.loginCourierResponse(
                new Courier(login, password));
        Integer courierId = dataCourier.extract().path("id");
        if (courierId != null) {
            couriersClient.deleteCourierResponse(courierId);
        }
    }

    @Test
    @DisplayName("Test Successful Login")
    @Description("Проверяем успешный логин курьера, статус-код ответа 200")

    public void testSuccessfulLogin() {
        ValidatableResponse dataCourier = couriersClient.loginCourierResponse(
                new Courier(login, password));
        dataCourier
                .statusCode(200);
        assertThat("id", notNullValue());
    }

    @Test
    @DisplayName("Test Request Without Login")
    @Description("Отправка запроса с пустым логином вернет ошибку и статус-код 400")
    public void testRequestWithoutLogin() {
        ValidatableResponse dataCourierWithoutLogin = couriersClient.loginCourierResponse(
                new Courier(null, password)
        );
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Test Request Without Password")
    @Description("Отправка запроса с пустым паролем вернет ошибку и статус-код 400")
    public void testRequestWithoutPassword() {
        ValidatableResponse dataCourierWithoutLogin = couriersClient.loginCourierResponse(
                new Courier(login, ""));
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Test With not Existing Credentials")
    @Description("Отправка запроса с несуществующими логином и паролем вернет ошибку и статус-код 404")
    public void testWithNotExistingCredentials() {
        ValidatableResponse notExistingCredentials = couriersClient.loginCourierResponse(
                new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(8)));
        notExistingCredentials
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
