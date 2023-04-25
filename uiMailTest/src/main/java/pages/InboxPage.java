package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import panels.LeftPanel;
import utils.TableWork;
import utils.elementUtils.GetElement;
import utils.wrapper.ActionsWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.elementUtils.ByAttribute.byDataToolTip;
import static utils.elementUtils.ByAttribute.byRole;
import static utils.elementUtils.GetElement.getElementByRole;
import static utils.wrapper.matcher.VolodiumElementMatcher.assertTrue;

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

    /**
     * Получить toolbar текущей строки
     *
     * @param row - строка, у которой хотим получить toolbar
     * @return - WebElement toolbar
     */
    private WebElement toolbarElement(WebElement row) {
        return row.findElement(byRole("toolbar"));
    }

    @Step("Получить элемент toolbar и проверить видимость при наведении")
    public WebElement hoverRowAndGetToolbarVisible(WebElement row) {
        WebElement toolbar = toolbarElement(row);
        assertTrue(toolbar).isNotVisible();
        ActionsWrapper.hoverOnElement(row);
        assertTrue(toolbar).isVisible();
        return toolbar;
    }

    @Step("Нажать кнопку 'Удалить' у выбранного тулбара")
    public void toolbarDeleteClick(WebElement toolbar) {
        WebElement deleteButton = toolbar.findElement(byDataToolTip("Удалить"));
        assertTrue(deleteButton).isVisible().isEnabled();
        deleteButton.click();
    }

    @Step("Проверить, что фильтр 'Несортированные' выбран")
    public InboxPage checkNotSortedSelected() {
        assertTrue(notSortedFilterButton()).checkAttribute("aria-selected", "true");
        return this;
    }

    @Step("Проверить, что первая строка входящих писем содержит текст = {text}")
    public InboxPage checkFirstRowContainsText(String... text) {
        assertTrue(tableWork().getAllRows().get(0)).hasTexts(text);
        return this;
    }

    @Step("Дождаться прибытия письма (Ожидание 5сек)")
    public InboxPage waitForIncomingMsg(int msgCountWithoutNewMsg) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() < 5000) {
            try {
                assertEquals(msgCountWithoutNewMsg + 1, tableWork().getAllRows().size(),
                        "Новое письмо не пришло за 5 сек");
                return this;
            } catch (AssertionError ignored) {
            }
        }
        throw new AssertionError("Письмо не пришло за 5 сек");
    }
}