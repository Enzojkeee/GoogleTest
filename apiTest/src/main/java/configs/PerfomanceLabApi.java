package configs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class PerfomanceLabApi {
    /**
     * Получить RequestSpecification
     *
     * @return
     */
    public static RequestSpecification getBasicPerfSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("http://77.50.236.203")
                .setPort(4879)
                .addHeader(ACCEPT, APPLICATION_JSON.getMimeType())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType()).build();
    }

    /**
     * Получить RequestSpecification для perfomanceLabAPI (авторизованный пользователь)
     *
     * @return -
     */
    public static RequestSpecification getAuthSpec(User user) {
        String accessToken = given().spec(getBasicPerfSpec()).body(String.format("{" +
                        "  \"password\": \"%s\",\n" +
                        "  \"username\": \"%s\"\n" +
                        "}", user.getPassword(), user.getLogin()))
                .when()
                .post("login")
                .then()
                .extract().jsonPath().get("access_token");
        return given().spec(getBasicPerfSpec()).auth().oauth2(accessToken);
    }
}