package panels;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utils.wrapper.ActionsWrapper;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;
import utils.wrapper.matcher.VolodiumElementMatcher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.elementUtils.ByAttribute.byDataToolTip;
import static utils.elementUtils.GetElement.elementByAriaLabel;
import static utils.elementUtils.GetElement.elementByTextContaining;
import static utils.wrapper.Volodium.getCurrentUrl;
import static utils.wrapper.matcher.VolodiumElementMatcher.assertTrue;

/**
 * Левая меню панель Почты Google
 */
public class LeftPanel {
    private final VolodiumElement elementPrefix = Volodium.locateX("//div[@role= 'navigation']");

    //Кнопка Входящие
    public WebElement incomingButton() {
        return elementByAriaLabel(elementPrefix, "Входящие");
    }

    //Помеченные
    public WebElement draftsButton() {
        return elementPrefix.findElement(byDataToolTip("Черновики"));
    }

    //Кнопка написать
    public WebElement writeButton() {
        return elementByTextContaining(elementPrefix, "Написать");
    }

    @Step("Нажать кнопку 'Написать'")
    public LeftPanel writeButtonClick() {
        assertTrue(writeButton()).isVisible();
        writeButton().click();
        return this;
    }

    @Step("Перейти на страницу Черновики")
    public LeftPanel redirectToDrafts() {
        assertTrue(draftsButton()).isVisible();
        draftsButton().click();
        ActionsWrapper.moveMouse(0, 0);
        VolodiumElementMatcher.assertTrue(draftsButton()).checkCssProperty("background-color", "rgba(211, 227, 253, 1)");
        assertTrue(getCurrentUrl().contains("drafts"), "Текущий URL не содержит drafts");
        return this;
    }
}