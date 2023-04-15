package utils.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.driver.WebDriverContainer;

/**
 * Базовый класс для работы с Вебдрайвером
 */
public class Volodium {

    /**
     * Создает экземпляр браузера, открывая выбранный url
     *
     * @param url - URL который необходимо открыть
     */
    public static void open(String url) {
        WebDriverContainer.INSTANCE.getOrInitWebDriver().navigate().to(url);
    }


    /**
     * Закрывает текущий экземпляр браузера
     */
    public static void close() {
        WebDriverContainer.INSTANCE.closeWebDriver();
    }

    /**
     * Найти элемент по Css селектору
     *
     * @param cssSelector - cssSelector
     * @return - Возвращает VolodiumElement найденного элемента
     */
    public static VolodiumElement locate(String cssSelector) {
        By locator = By.cssSelector(cssSelector);
        return new VolodiumElement(locator);
    }

    /**
     * Найти элемент по Css селектору
     *
     * @param xpathSelector - xPathSelector
     * @return - Возвращает VolodiumElement найденного элемента
     */
    public static VolodiumElement locateX(String xpathSelector) {
        By locator = By.xpath(xpathSelector);
        return new VolodiumElement(locator);
    }

    /**
     * Вернуть все найденные элементы по Css селектору
     *
     * @param cssSelector - xPathSelector
     * @return - Возвращает VolodiumElementList найденного элемента
     */
    public static VolodiumElementList locateAll(String cssSelector) {
        By locator = By.cssSelector(cssSelector);
        return new VolodiumElementList(locator);
    }

    /**
     * Вернуть все найденные элементы по xpath селектору
     *
     * @param xpathSelector - xPathSelector
     * @return - Возвращает VolodiumElementList найденного элемента
     */
    public static VolodiumElementList locateAllX(String xpathSelector) {
        By locator = By.xpath(xpathSelector);
        return new VolodiumElementList(locator);
    }

    public static String getCurrentUrl(){
        return WebDriverContainer.INSTANCE.getRequiredWebDriver().getCurrentUrl();
    }
}