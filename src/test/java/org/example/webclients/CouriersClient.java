package org.example.webclients;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.data.Courier;

import static io.restassured.RestAssured.given;
import static org.example.util.Constants.COURIER_API;
import static org.example.util.Constants.LOGIN_API;

public class CouriersClient {

    @Step("Получение ответа при создании курьера")
    public ValidatableResponse createCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_API.getConstant())
                .then();
    }

    @Step("Получение ответа при логине курьером")
    public ValidatableResponse loginCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_API.getConstant())
                .then();
    }
}
