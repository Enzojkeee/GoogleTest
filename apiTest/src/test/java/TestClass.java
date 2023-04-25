import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;
import models.user.UserAddRs;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static configs.PerfomanceLabApi.getAuthSpec;
import static encryption.UserCryptographer.getInstance;
import static encryption.UserCryptographer.getUser;
import static io.restassured.RestAssured.given;
import static io.restassured.mapper.ObjectMapperType.GSON;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.encryption.UserRoleApi.ADMIN;

public class TestClass {
    private static RequestSpecification basicAuth;
    private final Faker faker = new Faker(new Locale("ru", "RU"), new RandomService());


    @BeforeEach
    void beforeEach() {
        getInstance().decryptUsers();
        basicAuth = getAuthSpec(getUser(ADMIN));
    }

    @DisplayName("Добавление пользователя")
    @Test
    void addUserTest() {
        UserAddRs rs = new UserAddRs()
                .setAge(faker.number().numberBetween(0, 90))
                .setFirstName(faker.name().firstName())
                .setId(faker.number().numberBetween(300, 500))
                .setMoney(faker.number().randomDouble(2, 10000, 100000))
                .setSecondName(faker.name().lastName())
                .setSex(faker.expression(faker.regexify("MALE|FEMALE")));

        given()
                .spec(basicAuth)
                .body(rs, GSON)
                .when()
                .post("user")
                .then().assertThat()
                .statusCode(201)
                .body("firstName", equalTo(rs.getFirstName()))
                .body("secondName", equalTo(rs.getSecondName()))
                .body("age", equalTo(rs.getAge()))
                .body("sex", equalTo(rs.getSex()))
                .body("money", equalTo(rs.getMoney()))
                .body("id", not(rs.getId()))
                .body("id", IsNull.notNullValue());
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUserTest() {
        List<UserAddRs> userList = getAllUsers();
        UserAddRs user = userList.get(new Random().nextInt(userList.size()));
        given()
                .spec(basicAuth)
                .when()
                .delete(String.format("user/%d", user.getId()))
                .then()
                .assertThat()
                .statusCode(204);
    }

    @DisplayName("Начисление денег пользователю")
    @Test
    void addMoneyTest() {
        UserAddRs user = getRandomUser();
        System.out.println(user);
        double rndDouble = faker.number().randomDouble(2, 1, 100);
        given()
                .spec(basicAuth)
                .log().all()
                .when()
                .post(String.format("user/%d/money/%s", user.getId(), rndDouble))
                .then()
                .assertThat().statusCode(200);
        System.out.println(user.getMoney() + rndDouble);
        checkMoneyAdding(user, rndDouble);
    }

    @Step("Получить список сохраненных пользователей")
    private List<UserAddRs> getAllUsers() {
        return given()
                .spec(basicAuth)
                .when()
                .get("users")
                .then()
                .assertThat()
                .statusCode(200).extract().as(new TypeRef<>() {
                });
    }

    @Step("Получить сучайного пользователя")
    private UserAddRs getRandomUser() {
        List<UserAddRs> userList = getAllUsers();
        return userList.get(new Random().nextInt(userList.size()));
    }

    @Step("Получить пользователя с id = {id}")
    private UserAddRs getUserById(int id) {
        return given()
                .spec(basicAuth)
                .when()
                .get("user/" + id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id))
                .extract()
                .as(UserAddRs.class);
    }

    @Step("Проверить, что значение денег пользователя увеличилось на {money}")
    private void checkMoneyAdding(UserAddRs user, double money) {
        Double sum = user.getMoney() + money;
        assertEquals(sum, getUserById(user.getId()).getMoney(),
                "Значение денег не увеличилось");
    }
}
