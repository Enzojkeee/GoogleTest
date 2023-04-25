import alerts.InboxWindowAlert;
import io.qameta.allure.Step;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.DraftsPage;
import pages.InboxPage;
import pages.modal.NewMessageModal;
import utils.Pagination;

import java.util.List;

import static auth.Authorization.login;
import static encryption.UserCryptographer.getUser;
import static utils.encryption.UserRoleUi.FIRST_USER;
import static utils.wrapper.Volodium.*;
import static utils.wrapper.matcher.VolodiumElementListMatcher.assertThat;

public class DefaultPageTest {
    private static User user;
    private final InboxPage inboxPage = new InboxPage();
    private final DraftsPage draftsPage = new DraftsPage();

    @BeforeAll
    static void beforeAll() {
        user = getUser(FIRST_USER);
        login(user);
    }

    /**
     * Тест 1
     * 1. Авторизоваться - Пользователь авторизован
     * 2. Проверить, что выбраны Несортированные письма
     * 3. Проверить, что URL = https://mail.google.com/mail/u/0/#inbox
     */
    @Test
    @DisplayName("Проверка состояния страницы после логина")
    void checkPageAfterLogin() {
        inboxPage.checkNotSortedSelected();
        checkUrlContains("https://mail.google.com/mail/u/0/#inbox");
    }

    /**
     * Тест 2
     * 1. Авторизоваться - пользователь авторизован
     * 2. Нажать кнопку Написать - появилось окно новое сообщение
     * 3. Отправить письмо самому себе - проверить, что оно появилось в папке входящие
     */
    @Test
    @DisplayName("Проверка отправки письма самому себе")
    void checkSendingSelfMail() {
        String header = "AFT_" + System.currentTimeMillis();
        String msg = "AFT" + System.currentTimeMillis();
        int msgCount = inboxPage.tableWork().getAllRows().size();
        inboxPage.leftPanel.writeButtonClick();

        new NewMessageModal()
                .checkHeaderVisible()
                .fillConsignee(user.getLogin())
                .fillSubject(header)
                .fillTextOfMail(msg)
                .sendButtonClick();

        inboxPage.waitForIncomingMsg(msgCount);
        inboxPage.checkFirstRowContainsText(header, msg);
    }

    /**
     * Тест 3
     * 1. Авторизоваться - пользователь авторизован
     * 2. Выбрать любое письмо из таблицы Входящие и удалить его - письмо удалилось, появилось уведомление
     * 3. Проверить, что письмо удалилось
     * 4. Проверить, что появилось увевдомление об удалении
     */
    @DisplayName("Проверить функцию удаления письма из таблицы входящих писем")
    @Test
    void checkDeletingMsg() {
        WebElement rowToDelete = inboxPage.tableWork().getRandomRow();
        String rowText = rowToDelete.getText();

        WebElement toolbar = inboxPage.hoverRowAndGetToolbarVisible(rowToDelete);
        inboxPage.toolbarDeleteClick(toolbar);

        new InboxWindowAlert().checkAlertTextAndVisible("Цепочка помещена в корзину.");
        checkFolderNotContainsRowTextOnAllPages(rowText);

    }

    /**
     * Тест 4
     * 1. Авторизоваться - пользователь авторизован
     * 2. Нажать написать - открылась форма отправки письма
     * 3. Заполнить графы: получатель тема, текст письма - данные заполнены
     * 4. Не отправлять письмо - письмо сохранено в черновики
     * 5. Перейти в черновики и проверить, что можно продолжить отправку письма и данные неотправленного письма совпадают
     */
    @DisplayName("Сохранение неотправленного письма и возобновление отправки")
    @Test
    public void checkSaveToDraftsAndResume() {
        String subject = "AFT_" + System.currentTimeMillis();
        String msg = "AFT_" + System.currentTimeMillis();
        inboxPage.leftPanel.writeButtonClick();

        NewMessageModal newMessageModal = new NewMessageModal()
                .checkHeaderVisible()
                .fillConsignee(user.getLogin())
                .fillSubject(subject)
                .fillTextOfMail(msg)
                .closeButtonClick();

        inboxPage.leftPanel.redirectToDrafts();
        sleep(3000);
        clickDraftByParam(msg, subject);

        newMessageModal.checkFromHasText(user.getLogin(), subject, msg);
    }

    @Step("Проверить, что строка сообщения не содержится в папке Входящие")
    private void checkFolderNotContainsRowTextOnAllPages(String rowText) {
        Pagination pagination = new Pagination();
        List<WebElement> rowsFromAllPages = inboxPage.tableWork().getAllRows();

        while (!pagination.isButtonDisabled(pagination.nextButton())) {
            pagination.nextPageButtonClick();
            rowsFromAllPages.addAll(inboxPage.tableWork().getAllRows());
        }
        assertThat(rowsFromAllPages).notContainsTextInAnyElement(rowText);
    }

    @Step("Найти закрытый черновик в таблице, проверить, что он один и нажать на него в таблице")
    private void clickDraftByParam(String... params) {
        List<WebElement> filteredDrafts = draftsPage.tableWork().findRowsWithText(params);
        assertThat(filteredDrafts).hasSize(1);
        filteredDrafts.get(0).click();
    }

    @AfterEach
    void afterEach() {
        close();
    }
}
