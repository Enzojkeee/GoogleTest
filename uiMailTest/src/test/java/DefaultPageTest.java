import io.qameta.allure.Step;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.InboxPage;
import pages.modal.NewMessageModal;
import utils.wrapper.Volodium;

import java.util.Random;

import static auth.Authorization.login;
import static encryption.UserCryptographer.getUser;
import static utils.encryption.UserRole.FIRST_USER;

public class DefaultPageTest {
    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = getUser(FIRST_USER);
        login(user);
    }

    /**
     * Тест 1
     * 1. Авторизоваться в почте Gmail
     * 2. Проверить, что выбраны Несортированные письма
     * 3. Проверить, что URL = https://mail.google.com/mail/u/0/#inbox
     */
    @Test
    @DisplayName("Проверка состояния страницы после логина")
    void checkPageAfterLogin() {
        InboxPage inboxPage = new InboxPage();
        System.out.println(inboxPage.notSortedFilterButton().getAttribute("aria-selected"));
        inboxPage.checkNotSortedSelected();
        checkURLContains();
    }

    /**
     * Тест 2
     * 1. Авторизоваться
     * 2. Нажать кнопку Написать - появилось окно новое сообщение
     * 3. Отправить письмо самому себе - проверить, что оно появилось в папке входящие
     */
    @Test
    @DisplayName("Проверка отправки письма самому себе")
    void checkSendingSelfMail() throws InterruptedException {
        String header = "AFT_" + System.currentTimeMillis();
        String msg = "AFT";
        InboxPage inboxPage = new InboxPage();
        int msgCount = inboxPage.tableWork().getAllRows().size();
        inboxPage.leftPanel.writeButtonClick();
        NewMessageModal newMessageModal = new NewMessageModal();
        newMessageModal.checkHeaderVisible()
                .fillConsignee(user.getLogin())
                .fillSubject(header)
                .fillTextOfMail(msg)
                .sendButtonClick();
        inboxPage.waitForIncomingMsg(msgCount);
        inboxPage.checkFirstRowContainsText(header, msg);
    }

    /**
     * Тест 3
     * 1. Авторизоваться
     * 2. Выбрать любое письмо из таблицы и удалить его
     * 3. Проверить, что письмо удалилось
     */
    void checkDeletingMsg(){
        InboxPage inboxPage = new InboxPage();
        WebElement rowToDelete = inboxPage.tableWork().getAllRows().get(new Random().nextInt(inboxPage.tableWork().getAllRows().size()));
        String rowText = rowToDelete.getText();

    }

    @Step("Проверить, что URL = содержит https://mail.google.com/mail/u/0/#inbox")
    private void checkURLContains() {
        Assertions.assertEquals("https://mail.google.com/mail/u/0/#inbox", Volodium.getCurrentUrl());
    }
}
