package utils.wrapper;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.driver.WebDriverContainer;

/**
 * Класс для более удобной работы с классом Actions
 */
public class ActionsWrapper {
    private static Actions actions;

    /**
     * Получить Вэбдрайвер текущего потока
     */
    private static synchronized void actionInitialize() {
        actions = new Actions(WebDriverContainer.INSTANCE.getRequiredWebDriver());
    }

    /**
     * Навести мышку на элемент
     */
    public static void hoverOnElement(WebElement element) {
        actionInitialize();
        actions.moveToElement(element).perform();
    }

    /**
     * Перемещает указатель по координатам
     * @param x
     * @param y
     */
    public static void moveMouse(int x, int y) {
        actionInitialize();
        actions.moveByOffset(x, y).perform();
    }
}