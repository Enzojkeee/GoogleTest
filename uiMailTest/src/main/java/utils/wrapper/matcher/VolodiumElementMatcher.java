package utils.wrapper.matcher;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import utils.wrapper.Config;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

/**
 * Класс осуществляющий проверки для VolodiumElements
 */
public class VolodiumElementMatcher {
    private final WebElement matchedElement;

    private VolodiumElementMatcher(WebElement matchedElement) {
        this.matchedElement = matchedElement;
    }

    /**
     * Метод для начала проверок VolodiumElement
     */
    public static VolodiumElementMatcher assertTrue(WebElement element) {
        return new VolodiumElementMatcher(element);
    }

    /**
     * Проверяет, что элемент видим на странице
     */
    public VolodiumElementMatcher isVisible() {
        flexCheck(webElement -> Assertions.assertTrue(webElement.isDisplayed(), "Элемент не отображается на странице"));
        return this;
    }

    /**
     * Проверяет, что элемент неактивен
     */
    public VolodiumElementMatcher isDisabled() {
        flexCheck(webElement -> Assertions.assertFalse(webElement.isEnabled(), "Элемент не отключен"));
        return this;
    }

    /**
     * Проверяет, что элемент активен
     */
    public VolodiumElementMatcher isEnabled() {
        flexCheck(webElement -> Assertions.assertTrue(webElement.isEnabled(), "Элемент включен"));
        return this;
    }

    /**
     * Проверяет, что элемент не отображается на странице
     */
    public VolodiumElementMatcher isNotVisible() {
        flexCheck(webElement -> Assertions.assertFalse(matchedElement.isDisplayed(), "Элемент не видим настранице"));
        return this;
    }

    /**
     * Проверить, что элемент содержит текст
     *
     * @param expectedText - подстрока текста элемента
     */
    public VolodiumElementMatcher hasText(String expectedText) {
        String actual = matchedElement.getText();
        flexCheck(webElement -> Assertions.assertTrue(matchedElement.getText().contains(expectedText),
                expectedText + "не содержится в строке " + actual));
        return this;
    }

    /**
     * Проверить, что текстовые значения содержатся в тексте элемента
     *
     * @param expectedTexts - Проверяет, что массив текста содержится в тексте элемента
     */
    public VolodiumElementMatcher hasTexts(String... expectedTexts) {
        flexCheck(webElement ->
                Arrays.stream(expectedTexts)
                        .forEach(text -> Assertions.assertTrue(matchedElement.getText().contains(text)))
        );
        return this;
    }

    /**
     * Проверяет, что эелемент выбран
     */
    public VolodiumElementMatcher isSelected() {
        flexCheck(webElement -> Assertions.assertTrue(webElement.isSelected(), "Данный элемент не выбран"));
        return this;
    }

    /**
     * Проверить значение атрибута
     *
     * @param attributeName  - название атрибута
     * @param attributeValue - значение атрибута
     */
    public VolodiumElementMatcher checkAttribute(String attributeName, String attributeValue) {
        flexCheck(webElement -> Assertions.assertEquals(attributeValue, webElement.getAttribute(attributeName),
                format("атрибут %s отличается от ожидаемого", attributeName)));
        return this;
    }

    public VolodiumElementMatcher checkCssProperty(String cssName, String cssValue) {
        flexCheck(webElement -> Assertions.assertEquals(cssValue, webElement.getCssValue(cssName),
                format("cssProperty %s отличается от ожидаемого", cssName)));
        return this;
    }

    /**
     * Метод для проверки условий с ожиданием
     *
     * @param action - проверка, производимая с элементом
     */
    private void flexCheck(@Nonnull Consumer<WebElement> action) {
        StopWatch stopWatch = StopWatch.createStarted();
        boolean scrolled = false;
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                action.accept(matchedElement);
                return;
            } catch (Throwable e) {
                try {
                    sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        action.accept(matchedElement);
    }
}
