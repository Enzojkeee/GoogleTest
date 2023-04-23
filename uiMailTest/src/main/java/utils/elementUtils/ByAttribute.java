package utils.elementUtils;

import org.openqa.selenium.By;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

/**
 * Класс для упрощения работы с By
 */
public class ByAttribute {
    /**
     * Возвращает By по полному совпадению атрибута @role
     *
     * @param roleValue - значение атрибута role
     */
    public static By byRole(String roleValue) {
        return xpath(format(".//*[@role='%s']", roleValue));
    }

    /**
     * Возвращает By по полному совпадению атрибута @data-tooltip
     *
     * @param value - Значение атрибута data-tooltip
     */
    public static By byDataToolTip(String value) {
        return xpath(format(".//*[@data-tooltip='%s']", value));
    }
}