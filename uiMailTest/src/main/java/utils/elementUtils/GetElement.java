package utils.elementUtils;

import org.openqa.selenium.WebElement;
import utils.wrapper.VolodiumElement;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static utils.wrapper.Volodium.locateX;

/**
 * Класс для упрощения работы по поиску элементов в DOM
 */
public class GetElement {
    /**
     * Получить элемент кнопки по его названию
     *
     * @param buttonText - текст на кнопке
     */
    public static WebElement buttonByTextContaining(String buttonText) {
        return locateX("//button[contains(., '" + buttonText + "')]");
    }

    /**
     * Получить элемент кнопки по его названию
     *
     * @param elementPrefix - родительский элемент
     * @param buttonText    - текст кнопки
     */
    public static WebElement buttonByTextContaining(VolodiumElement elementPrefix, String buttonText) {
        return elementPrefix.findElement(xpath(".//button[contains(., '" + buttonText + "')]"));
    }

    /**
     * Получить элемент по совпадению его aria-label
     *
     * @param ariaLabel - значения атрибута aria-label
     */
    public static WebElement elementByAriaLabel(String ariaLabel) {
        return locateX("//*[@aria-label= '" + ariaLabel + "']");
    }

    /**
     * Получить элемент по совпадению его aria-label
     *
     * @param ariaLabel     - значения атрибута aria-label
     * @param elementPrefix - Родительский элемент
     */
    public static WebElement elementByAriaLabel(VolodiumElement elementPrefix, String ariaLabel) {
        return elementByAriaLabel(elementPrefix, "*", ariaLabel);
    }

    /**
     * Получить элемент по совпадению его aria-label
     *
     * @param ariaLabel     - значения атрибута aria-label
     * @param elementPrefix - Родительский элемент
     */
    public static WebElement elementByAriaLabel(VolodiumElement elementPrefix, String elementName, String ariaLabel) {
        return elementPrefix.findElement(xpath(format(".//%s[@aria-label= '%s']", elementName, ariaLabel)));
    }

    /**
     * Находит элемент по тексту
     *
     * @param elementPrefix - Родительский элемент
     * @param text          - текст содержащийся в элементе
     */
    public static WebElement elementByTextContaining(VolodiumElement elementPrefix, String text) {
        return elementPrefix.findElement(xpath(String.format(".//*[contains(text(), '%s')]", text)));
    }

    /**
     * Находит элемент по полному совпадению текста
     *
     * @param elementPrefix - родительский элемент
     * @param text          - тест содержащийся в элементе
     */
    public static WebElement elementByExactText(VolodiumElement elementPrefix, String text) {
        return elementPrefix.findElement(xpath(format(".//*[text()='%s']", text)));
    }

    /**
     * Находит элемент по полному совпадению атрибута @role
     *
     * @param roleValue - значение атрибута role
     */
    public static WebElement getElementByRole(String roleValue) {
        return locateX(format(".//*[@role='%s']", roleValue));
    }

    public static WebElement getElementByRole(WebElement elementPrefix, String roleValue) {
        return elementPrefix.findElement(xpath(format(".//*[@role= '%s']", roleValue)));
    }

    /**
     * Найти элемент по атрибуту @name
     *
     * @param nameAttribute - значение атрибута @name
     */
    public static WebElement getElementByName(String nameAttribute) {
        return locateX(format("//*[@name='%s']", nameAttribute));
    }
}
