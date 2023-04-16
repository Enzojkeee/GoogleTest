package auth;

import io.qameta.allure.Step;
import models.User;
import pages.InboxPage;
import pages.LoginPage;
import utils.wrapper.Volodium;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.wrapper.Volodium.open;

public class Authorization {
    @Step("Авторизоваться с ролью {user.role}")
    public static void login(User user) {
        open(user.getUrl());
        LoginPage loginPage = new LoginPage()
                .fillEmailInput(user.getLogin())
                .nextButtonClick()
                .waitForProgressBarProcessing()
                .fillPasswordInput(user.getPassword())
                .nextPassButtonClick();
        InboxPage inboxPage = new InboxPage();
//        assertTrue(Volodium.getCurrentUrl().contains("inbox"));
    }
}
