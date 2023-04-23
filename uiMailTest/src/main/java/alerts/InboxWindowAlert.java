package alerts;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utils.wrapper.matcher.VolodiumElementMatcher;

import static utils.elementUtils.GetElement.getElementByRole;

/**
 * Класс для работы с всплывающими уведомлениями на вкладке Входящие
 */
public class InboxWindowAlert {
    public WebElement alertElement() {
        return getElementByRole("alert");
    }

    @Step("Проверить видимость и текст уведомления")
    public InboxWindowAlert checkAlertTextAndVisible(String alertText) {
        VolodiumElementMatcher.assertTrue(alertElement()).isVisible().hasText(alertText);
        return this;
    }
}