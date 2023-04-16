package utils.elementUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;

import static utils.wrapper.Volodium.locateX;

/**
 * Класс для упрощения работы по поиску элементов в DOM
 */
public class GetElement {
    /**
     * Получить элемент кнопки по его названию
     *
     * @param buttonText - текст на кнопке
     * @return - VolodiumElement кнопки
     */
    public static WebElement buttonByTextContaining(String buttonText) {
        return locateX("//button[contains(., '" + buttonText + "')]");
    }

    /**
     * Получить элемент кнопки по его названию
     *
     * @param elementPrefix - родительский элемент
     * @param buttonText    - текст кнопки
     * @return - Возвращает WebElement кнопки по названию
     */
    public static WebElement buttonByTextContaining(VolodiumElement elementPrefix, String buttonText) {
        return elementPrefix.findElement(By.xpath(".//button[contains(., '" + buttonText + "')]"));
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
        return elementPrefix.findElement(By.xpath(String.format(".//%s[@aria-label= '" + ariaLabel + "']", elementName)));
    }

    /**
     * Находит элемент по тексту
     *
     * @param elementPrefix - Родительский элемент
     * @param text          - текст содержащийся в элементе
     * @return VolodiumElement
     */
    public static WebElement elementByTextContaining(VolodiumElement elementPrefix, String text) {
        return elementPrefix.findElement(By.xpath(".//*[contains(text(), '" + text + "')]"));
    }

    /**
     * Находит элемент по полному совпадению текста
     *
     * @param elementPrefix - родительский элемент
     * @param text          - тест содержащийся в элементе
     * @return - VolodiumElement
     */
    public static WebElement elementByExactText(VolodiumElement elementPrefix, String text) {
        return elementPrefix.findElement(By.xpath(".//*[text()='" + text + "']"));
    }

    public static VolodiumElement getElementByRole(String roleValue){
        return Volodium.locateX("//*[@role= '" + roleValue + "']");
    }
}
