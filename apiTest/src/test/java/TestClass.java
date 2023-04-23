import configs.PerfomanceLabApi;
import encryption.Cryptographer;
import encryption.UserCryptographer;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.encryption.UserRoleApi;

import static configs.PerfomanceLabApi.getAuthSpec;
import static encryption.UserCryptographer.getUser;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static util.encryption.UserRoleApi.*;

public class TestClass {
    private static RequestSpecification basicAuth;
        @BeforeEach
        void beforeEach(){
            UserCryptographer.getInstance().decryptUsers();
            basicAuth = getAuthSpec(getUser(ADMIN));
        }

    @Test
    void test() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://77.50.236.203")
                .setPort(4879)
                .addHeader(ACCEPT, APPLICATION_JSON.getMimeType())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType()).build();
        String accessToken = given().spec(requestSpecification).log().all().body("{\n" +
                "  \"password\": \"admin\",\n" +
                "  \"username\": \"admin@pflb.ru\"\n" +
                "}").when().post("login").then().extract().jsonPath().get("access_token");
        PreemptiveOAuth2HeaderScheme auth = new PreemptiveOAuth2HeaderScheme();
        auth.setAccessToken(accessToken);
        given().spec(requestSpecification).body("{\n" +
                "  \"age\": 19,\n" +
                "  \"firstName\": \"Василий\",\n" +
                "  \"id\": 10,\n" +
                "  \"money\": 0,\n" +
                "  \"secondName\": \"Тестов\",\n" +
                "  \"sex\": \"MALE\"\n" +
                "}").auth().oauth2(accessToken).when().log().all().post("user").then().assertThat().statusCode(200);
    }

    @Test
    void test1(){
        given().spec(basicAuth).body("{\n" +
                "  \"age\": 19,\n" +
                "  \"firstName\": \"Василий\",\n" +
                "  \"id\": 10,\n" +
                "  \"money\": 0,\n" +
                "  \"secondName\": \"Тестов\",\n" +
                "  \"sex\": \"MALE\"\n" +
                "}").when().post("user").then().assertThat().statusCode(201).log().all();
    }
}
