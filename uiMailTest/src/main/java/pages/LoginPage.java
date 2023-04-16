package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.WebElement;
import utils.wrapper.Volodium;

import static utils.elementUtils.GetElement.buttonByTextContaining;
import static utils.elementUtils.GetElement.elementByAriaLabel;
import static utils.wrapper.matcher.VolodiumElementsMatcher.assertTrue;

/**
 * Класс для работы с элементами страницы ввода Логина/Пароля
 */
public class LoginPage {
    //Поле ввода email
    public WebElement emailInput() {
        return elementByAriaLabel("Телефон или адрес эл. почты");
    }

    //Поле ввода пароля
    public WebElement passInput(){
        return elementByAriaLabel("Введите пароль");
    }

    //Кнопка Далее
    public WebElement nextButton(){
        return buttonByTextContaining("Далее");
    }

    //Прогресс-бар
    public WebElement progressBar(){
        return Volodium.locate("#initialView");
    }

//    //Кнопка 'Не сейчас'
//    public VolodiumElement notNowButton(){
//        return
//    }

    @Step("Заполнить поле Email")
    public LoginPage fillEmailInput(String email){
//        assertTrue(emailInput()).isVisible();
        emailInput().sendKeys(email);
        return this;
    }

    public LoginPage nextButtonClick(){
        assertTrue(nextButton()).isVisible();
        nextButton().click();
        return this;
    }

    @Step("Нажать кнопку Далее")
    public LoginPage nextPassButtonClick(){
        assertTrue(nextButton()).isVisible();
        nextButton().click();
        return this;
    }

    @Step("Заполнить поле пароль")
    public LoginPage fillPasswordInput(String pass){
        assertTrue(passInput()).isVisible();
        passInput().sendKeys(pass);
//        passInput().sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Дождаться, пока уйдет прогресс бар")
    public LoginPage waitForProgressBarProcessing(){
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime()<15000){
            if (progressBar().getAttribute("aria-busy") == null) {
                return this;
            }
        }
        throw new RuntimeException("Спиннер все крутится");
    }
}
