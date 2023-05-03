package ru.netology.web.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import com.google.gson.Gson;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void validLogin(DataHelper.AuthInfo user) {
        given()
                .spec(requestSpec)
                .body("{'login': " + user.getLogin() + ", 'password': " + user.getPassword() + "}")
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    public static String getToken(DataHelper.AuthInfo user, String verificationCode) {
        return given()
                .spec(requestSpec)
                .body("{'login': " + "'" + user.getLogin() + "'" + ", 'code': " + "'" + verificationCode + "'" + "}")
                .when()
                .post("/api/auth/verification")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public static void transferCard(String token, String from, String to, int amount) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(DataHelper.transfer(from, to, amount));
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(jsonStr)
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(200);

    }


}
