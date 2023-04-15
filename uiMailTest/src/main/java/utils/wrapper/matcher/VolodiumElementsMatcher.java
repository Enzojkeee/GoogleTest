package utils.wrapper.matcher;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.driver.WebDriverContainer;
import utils.wrapper.Config;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;

/**
 * Класс осуществляющий проверки для VolodiumElements
 */
public class VolodiumElementsMatcher {
    private final WebElement matchedElement;

    private VolodiumElementsMatcher(WebElement matchedElement) {
        this.matchedElement = matchedElement;
    }

    /**
     * Метод для начала проверок VolodiumElement
     */
    public static VolodiumElementsMatcher assertTrue(WebElement element) {
        return new VolodiumElementsMatcher(element);
    }

    /**
     * Проверяет, что элемент видим на странице
     */
    public VolodiumElementsMatcher isVisible() {
        flexCheck(webElement -> Assertions.assertTrue(webElement.isDisplayed(), "Элемент не отображается на странице"));
        return this;
    }

    /**
     * Проверяет, что элемент неактивен
     */
    public VolodiumElementsMatcher isDisabled() {
        flexCheck(webElement -> Assertions.assertFalse(webElement.isEnabled(), "Элемент не отключен"));
        return this;
    }

    /**
     * Проверяет, что элемент не отображается на странице
     */
    public VolodiumElementsMatcher isNotVisible() {
        flexCheck(webElement -> Assertions.assertFalse(matchedElement.isDisplayed(), "Элемент не видим настранице"));
        return this;
    }

    /**
     * Проверить, что элемент содержит текст
     *
     * @param expectedText - подстрока текста элемента
     */
    public VolodiumElementsMatcher hasText(String expectedText) {
        String actual = matchedElement.getText();
        flexCheck(webElement -> Assertions.assertTrue(matchedElement.getText().contains(expectedText),
                expectedText + "не содержится в строке " + actual));
        return this;
    }

    /**
     * Проверить, что текстовые значения содержатся в тексте элемента
     *
     * @param expectedTexts
     */
    public VolodiumElementsMatcher hasTexts(String... expectedTexts) {
        flexCheck(webElement ->
            Arrays.stream(expectedTexts)
                    .forEach(text -> Assertions.assertTrue(matchedElement.getText().contains(text)))
        );
        return this;
    }

    /**
     *
     * @return
     */
    public VolodiumElementsMatcher isSelected(){
        flexCheck(webElement -> Assertions.assertTrue(webElement.isSelected(), "Данный элемент не выбран"));
        return this;
    }

    /**
     * Проверить значение атрибута
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public VolodiumElementsMatcher checkAttribute(String attributeName, String attributeValue){
        flexCheck(webElement -> Assertions.assertEquals(attributeValue, webElement.getAttribute(attributeName)));
        return this;
    }

    /**
     * Метод для проверки условий с ожиданием
     * Скролит положение страницы к элементу для скриншота
     *
     * @param action - проверка произваодимая с элементом
     */
    private void flexCheck(@Nonnull Consumer<WebElement> action) {
        StopWatch stopWatch = StopWatch.createStarted();
        boolean scrolled = false;
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
//                if (!scrolled) {
//                    ((JavascriptExecutor) WebDriverContainer.INSTANCE.getRequiredWebDriver())
//                            .executeScript("arguments[0].scrollIntoView(false)", matchedElement);
//                    scrolled = true;
//                }
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
