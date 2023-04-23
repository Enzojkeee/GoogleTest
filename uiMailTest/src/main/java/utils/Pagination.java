package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utils.elementUtils.ByAttribute;
import utils.wrapper.matcher.VolodiumElementMatcher;

import static java.lang.Boolean.parseBoolean;
import static utils.wrapper.Volodium.locate;

/**
 * Класс для работы с пагинацией страниц
 */
public class Pagination {
    private final WebElement elementPrefix;

    public Pagination() {
        this.elementPrefix = locate("body");
    }

    public Pagination(WebElement elementPrefix) {
        this.elementPrefix = elementPrefix;
    }

    //Кнопка следующая страница
    public WebElement nextButton() {
        return elementPrefix.findElement(ByAttribute.byDataToolTip("След."));
    }

    //Кнопка предыдущая страница
    public WebElement previousButton() {
        return elementPrefix.findElement(ByAttribute.byDataToolTip("Пред."));
    }

    /**
     * Вернуть true, если кнопка включена. False, если выключена
     *
     * @param button
     * @return
     */
    public Boolean isButtonDisabled(WebElement button) {
        return parseBoolean(button.getAttribute("aria-disabled"));
    }

    @Step("Нажать кнопку перехода на следующую страницу таблицы")
    public Pagination nextPageButtonClick() {
        VolodiumElementMatcher.assertTrue(nextButton()).isEnabled();
        nextButton().click();
        return this;
    }
}