package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import panels.LeftPanel;
import utils.TableWork;
import utils.elementUtils.GetElement;

import static utils.elementUtils.GetElement.getElementByRole;
import static utils.wrapper.matcher.VolodiumElementsMatcher.assertTrue;

/**
 * Страница Входящих писем
 */
public class InboxPage {
    //Доступ к левой панеле
    public LeftPanel leftPanel = new LeftPanel();

    //Кнопка-фильтр 'Несортированные'
    public WebElement notSortedFilterButton() {
        return GetElement.elementByAriaLabel("Несортированные");
    }

    //Корневой элемент таблицы входящих писем
    private WebElement inboxTabelElement() {
        return getElementByRole("tabpanel").findElement(By.xpath(".//table"));
    }

    public TableWork tableWork() {
        return new TableWork(inboxTabelElement());
    }

    @Step("Проверить, что фильтр 'Несортированные' выбран")
    public InboxPage checkNotSortedSelected() {
        assertTrue(notSortedFilterButton()).checkAttribute("aria-selected", "true");
        return this;
    }

    @Step("Проверить, что в первая строка входящих писем содержит текст = {text}")
    public InboxPage checkFirstRowContainsText(String... text) {
        assertTrue(tableWork().getAllRows().get(0)).hasTexts(text);
        return this;
    }

    @Step("Дождаться прибытия письма (Ожидание 5сек)")
    public InboxPage waitForIncomingMsg(int msgCountWithoutNewMsg) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() < 5000) {
            try {
                Assertions.assertEquals(msgCountWithoutNewMsg + 1, tableWork().getAllRows().size());
                return this;
            } catch (AssertionError ignored) {
            }
        }
        throw new AssertionError("Письмо не пришло за 5 сек");
    }
}