package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import panels.LeftPanel;
import utils.elementUtils.GetElement;
import utils.wrapper.matcher.VolodiumElementsMatcher;

import static utils.wrapper.matcher.VolodiumElementsMatcher.assertTrue;

/**
 * Страница Входящих писем
 */
public class InboxPage {
    //Доступ к левой панеле
    public LeftPanel leftPanel = new LeftPanel();

    //Кнопка-фильтр 'Несортированные'
    public WebElement notSortedFilterButton(){
        return GetElement.elementByAriaLabel("Несортированные");
    }

    @Step("Проверить, что фильтр 'Несортированные' выбран")
    public InboxPage checkNotSortedSelected(){
        assertTrue(notSortedFilterButton()).checkAttribute("aria-selected", "true");
        return this;
    }
}
