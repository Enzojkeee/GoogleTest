package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.wrapper.matcher.VolodiumElementListMatcher;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static utils.wrapper.Volodium.locateX;

/**
 * Класс для работы с таблицами
 */
public class TableWork {
    private final WebElement tableElement;

    public TableWork(WebElement elementPrefix) {
        this.tableElement = elementPrefix;
    }

    public TableWork() {
        this.tableElement = locateX("//table");
    }

    //Tbody таблицы
    private WebElement getTbody() {
        return tableElement.findElement(By.xpath(".//tbody"));
    }

    @Step("Выбрать случайную строку в таблице")
    public WebElement getRandomRow() {
        List<WebElement> allRows = getAllRows();
        return allRows.get(new Random().nextInt(allRows.size()));
    }

    /**
     * Получить коллекцию всех строк таблицы
     *
     * @return - List<WebElement> всех строк таблицы
     */
    public List<WebElement> getAllRows() {
        return getTbody().findElements(By.xpath("./tr"));
    }


    /**
     * Получить кол-во всех строк в таблице
     */
    public int getAllRowSize() {
        return getAllRows().size();

    }

    /**
     * Находит строку по совпадению любого из элементов массива
     *
     * @param texts - набор строк для поиска по сопданению
     * @return - коллекцию отфильтрованных элементов
     */
    public List<WebElement> findRowsWithText(String... texts) {
        List<WebElement> allRows = getAllRows();
        VolodiumElementListMatcher.assertThat(allRows).hasSizeGreaterThan(0);
        return allRows.stream().filter((row) -> {
            boolean result = false;
            String rowText = row.getText();
            for (String text : texts) {
                if (rowText.contains(text)) {
                    result = true;
                    break;
                }
            }
            return result;
        }).collect(toList());
    }
}
