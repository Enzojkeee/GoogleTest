package utils.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static utils.driver.WebDriverContainer.INSTANCE;

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
        INSTANCE.getOrInitWebDriver().navigate().to(url);
    }


    /**
     * Закрывает текущий экземпляр браузера
     */
    public static void close() {
        INSTANCE.closeWebDriver();
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

    /**
     * Получить значение текущего URL
     * @return
     */
    public static String getCurrentUrl(){
        return INSTANCE.getRequiredWebDriver().getCurrentUrl();
    }

    /**
     * Передать текст активному элементу
     * @param text - текст, который хотим передать
     */
    public static void sendKeysToActiveElement(String text){
        Actions actions = new Actions(INSTANCE.getRequiredWebDriver());
        actions.sendKeys(text).perform();
    }

    /**
     * Навести мышку на выбранный элемент
     */
    public static void hoverElement(VolodiumElement element){
        getAction().moveToElement(element).perform();
    }

    /**
     * Получить экземпляр Action текущего драйвера
     * @return
     */
    public static Actions getAction(){
        Actions actions = new Actions(INSTANCE.getRequiredWebDriver());

        return actions;
    }

    /**
     * sleep
     * @param miliseconds - время в милисекундах
     */
    public static void sleep(int miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}