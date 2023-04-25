package configs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.User;
import models.login.LoginRs;
import org.hamcrest.CoreMatchers;

import static io.restassured.RestAssured.given;
import static io.restassured.mapper.ObjectMapperType.GSON;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class PerfomanceLabApi {
    /**
     * Получить RequestSpecification адресом, и базовыми хидерами
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
     */
    public static RequestSpecification getAuthSpec(User user) {
        String accessToken = given().spec(getBasicPerfSpec())
                .body(new LoginRs(user.getPassword(), user.getLogin()), GSON)
                .when()
                .post("login")
                .then()
                .assertThat().body("access_token", CoreMatchers.notNullValue())
                .statusCode(202)
                .extract().jsonPath().get("access_token");
        return given().spec(getBasicPerfSpec()).auth().oauth2(accessToken);
    }
}