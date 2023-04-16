package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Класс для работы с таблицами
 */
public class TableWork {
    private final WebElement tableElement;

    public TableWork(WebElement elementPrefix) {
        this.tableElement = elementPrefix;
    }

    private WebElement getTbody(){
        return tableElement.findElement(By.xpath("./tbody"));
    }

    public List<WebElement> getAllRows(){
        return getTbody().findElements(By.xpath("./tr"));
    }


}
