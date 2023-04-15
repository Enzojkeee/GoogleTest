package utils.elementUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;

import static utils.wrapper.Volodium.*;

/**
 * Класс для упрощения работы по поиску элементов в DOM
 */
public class GetElement {
    /**
     * Получить элемент кнопки по его названию
     * @param buttonText - текст на кнопке
     * @return - VolodiumElement кнопки
     */
    public static WebElement buttonByText(String buttonText){
        return locateX("//button[contains(., '" + buttonText + "')]");
    }

    /**
     * Получить элемент кнопки по его названию
     * @param elementPrefix - родительский элемент
     * @param buttonText - текст кнопки
     * @return
     */
    public static WebElement buttonByText(VolodiumElement elementPrefix, String buttonText){
        return elementPrefix.findElement(By.xpath("//button[contains(., '" + buttonText + "')]"));
    }

    /**
     * Получить элемент по совпадению его aria-label
     * @param ariaLabel - значения атрибута aria-label
     */
    public static WebElement elementByAriaLabel(String ariaLabel){
        return locateX("//*[contains(@aria-label, '" + ariaLabel + "')]");
    }

    public static WebElement elementByAriaLabel(VolodiumElement elementPrefix, String ariaLabel){
        return elementPrefix.findElement(By.xpath("//*[contains(@aria-label, '" + ariaLabel + "')]"));
    }

}
