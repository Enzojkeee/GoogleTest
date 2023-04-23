package pages;

import org.openqa.selenium.WebElement;
import utils.TableWork;

import static utils.elementUtils.GetElement.getElementByRole;

/**
 * Класс для работы с элементами страницы Черновики
 */
public class DraftsPage {

    private WebElement mainTableArea() {
        return getElementByRole("main");
    }

    public TableWork tableWork() {
        return new TableWork(mainTableArea());
    }
}