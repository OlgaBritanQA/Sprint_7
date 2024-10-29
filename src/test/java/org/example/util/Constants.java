package org.example.util;

import lombok.Getter;

@Getter
public enum Constants {
    BASE_URL("https://qa-scooter.praktikum-services.ru"),
    COURIER_API("/api/v1/courier"),
    LOGIN_API("/api/v1/courier/login"),
    ORDER_API("/api/v1/orders"),

    EXISTING_LOGIN("OlgaLogin"),
    EXISTING_PASSWORD("OlgaPassword"),
    EXISTING_FIRSTNAME("Olga");

    private final String constant;

    Constants(final String constant) {
        this.constant = constant;
    }
}
