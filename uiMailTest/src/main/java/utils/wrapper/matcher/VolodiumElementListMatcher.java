package utils.wrapper.matcher;

import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.driver.WebDriverContainer;
import utils.wrapper.Config;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Класс для работы с асертами  List<VolodiumElementList>
 */
public class VolodiumElementListMatcher {
    private final List<WebElement> matchedList;

    private VolodiumElementListMatcher(List<WebElement> matchedList) {
        this.matchedList = matchedList;
    }

    public static VolodiumElementListMatcher assertThat(@Nonnull List<WebElement> list) {
        return new VolodiumElementListMatcher(list);
    }

    /**
     * Проверить, что размер колекции больше указанного значения
     *
     * @param expectedSize - значения больше которого должна быть коллекция
     */
    public VolodiumElementListMatcher hasSizeGreaterThan(int expectedSize) {
        flexCheck(webElements -> Assertions.assertThat(webElements)
                .hasSizeGreaterThan(expectedSize));
        return this;
    }

    /**
     * Проверить, что хотя бы один элемент содержит текст
     *
     * @param expectedText - текст, который нужно проверить
     */
    public VolodiumElementListMatcher containsTextInAnyElement(@Nonnull String expectedText) {
        flexCheck(webElements ->
                Assertions.assertThat(
                                webElements
                                        .stream()
                                        .map(WebElement::getText)
                                        .anyMatch(text -> text.toLowerCase(Locale.ROOT).contains(expectedText.toLowerCase(Locale.ROOT))))
                        .isTrue()
        );
        return this;
    }

    /**
     * Проверить, что ни один элемент не содержит текст
     *
     * @param expectedText - текст, который нужно проверить
     */
    public VolodiumElementListMatcher notContainsTextInAnyElement(@Nonnull String expectedText) {
        flexCheck(webElements ->
                Assertions.assertThat(webElements.stream().map(WebElement::getText)
                                        .anyMatch(text -> text.toLowerCase(Locale.ROOT).contains(expectedText.toLowerCase(Locale.ROOT))))
                        .isFalse());
        return this;
    }

    /**
     * Проверить, что размер коллекции = @size
     * @param size - размер коллекции
     */
    public VolodiumElementListMatcher hasSize(int size){
        flexCheck((webElements -> Assertions.assertThat(webElements.size() == size).isTrue()));
        return this;
    }

    private void flexCheck(@Nonnull Consumer<List<WebElement>> action) {
        StopWatch stopWatch = StopWatch.createStarted();
        boolean scrolled = false;
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                if (!scrolled) {
                    ((JavascriptExecutor) WebDriverContainer.INSTANCE.getRequiredWebDriver())
                            .executeScript("arguments[0].scrollIntoView(false)", matchedList.get(0));
                    scrolled = true;
                }
                action.accept(matchedList);
                return;
            } catch (Throwable e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        action.accept(matchedList);
    }
}
