package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class FakeGenerator {

    static Faker faker = new Faker(new Locale("ru"));

    static LoginData dataName(String status) {

        return new LoginData(faker.name().lastName(), faker.internet().password(), status);
    }

    static String noValidLog() {
        String noValidLog = faker.name().username();
        return noValidLog;
    }

    static String noValidPass() {
        String noValidPass = faker.internet().password();
        return noValidPass;
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void sendRequest(LoginData user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
