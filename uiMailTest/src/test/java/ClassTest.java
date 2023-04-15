import auth.Authorization;
import io.qameta.allure.Step;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.LoginPage;
import utils.wrapper.Volodium;

import static auth.Authorization.login;
import static encryption.UserCryptographer.getUser;
import static utils.encryption.UserRole.FIRST_USER;
import static utils.wrapper.Volodium.open;

public class ClassTest {
    @BeforeAll
    static void beforeAll() {
        login(getUser(FIRST_USER));
    }

    /**
     * Тест 1
     * 1. Авторизоваться в почте Gmail
     * 2. Проверить, что выбраны Несортированные письма
     * 3. Проверить, что URL = https://mail.google.com/mail/u/0/#inbox
     */
    @Test
    @DisplayName("ХУЙ")
    void test() {
        InboxPage inboxPage = new InboxPage();
        System.out.println(inboxPage.notSortedFilterButton().getAttribute("aria-selected"));
        inboxPage.checkNotSortedSelected();
        checkURLContains();
    }

    @Step("Проверить, что URL = содержит https://mail.google.com/mail/u/0/#inbox")
    private void checkURLContains(){
        Assertions.assertEquals("https://mail.google.com/mail/u/0/#inbox", Volodium.getCurrentUrl());
    }

}
