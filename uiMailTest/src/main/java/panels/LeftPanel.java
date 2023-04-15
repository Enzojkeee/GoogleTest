package panels;

import org.openqa.selenium.WebElement;
import utils.elementUtils.GetElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;

import static utils.elementUtils.GetElement.elementByAriaLabel;

/**
 * Левая меню панель Почты Google
 */
public class LeftPanel {
    private final VolodiumElement elementPrefix = Volodium.locateX("//div[@role, 'navigation']");

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
        return elementByAriaLabel(elementPrefix, "Написать");
    }
}
