package utils.wrapper;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Класс осуществляющий поиск WebElement
 */
public enum VolodiumElementsLocator {
    INSTANCE;

    /**
     * Метод для поиска одного элемента
     * @param ctx - драйвер
     * @param selector - селектор
     * @return
     */
    public WebElement findElement(SearchContext ctx, By selector) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                return ctx.findElement(selector);
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return ctx.findElement(selector);
    }

    /**
     * Метод для поиска нескольких элементов
     * @param ctx - драйвер
     * @param selector - селектор
     * @return
     */
    public List<WebElement> findElements(SearchContext ctx, By selector) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                List<WebElement> elements = ctx.findElements(selector);
                if (elements == null || elements.isEmpty()) {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } else {
                    return elements;
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return ctx.findElements(selector);
    }
}
