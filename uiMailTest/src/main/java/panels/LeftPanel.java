package panels;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;

import static utils.elementUtils.GetElement.elementByAriaLabel;
import static utils.elementUtils.GetElement.elementByTextContaining;
import static utils.wrapper.matcher.VolodiumElementsMatcher.assertTrue;

/**
 * Левая меню панель Почты Google
 */
public class LeftPanel {
    private final VolodiumElement elementPrefix = Volodium.locateX("//div[@role= 'navigation']");

    //Кнопка Входящие
    public WebElement incomingButton(){
        return elementByAriaLabel(elementPrefix, "Входящие");
    }

    //Помеченные
    public WebElement draftsButton(){
        return elementByAriaLabel(elementPrefix, "Черновики");
    }

    //Кнопка написать
    public WebElement writeButton(){
        return elementByTextContaining(elementPrefix, "Написать");
    }

    @Step("Нажать кнопку 'Написать'")
    public LeftPanel writeButtonClick(){
        assertTrue(writeButton()).isVisible();
        writeButton().click();
        return this;
    }
}
