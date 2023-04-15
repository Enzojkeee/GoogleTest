package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.elementUtils.GetElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;
import utils.wrapper.matcher.VolodiumElementsMatcher;

import static utils.elementUtils.GetElement.*;
import static utils.wrapper.Volodium.*;
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
        return buttonByText("Далее");
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
//        assertTrue(nextButton()).isVisible();
        nextButton().click();
        return this;
    }

    public LoginPage nextPassButtonClick(){
        assertTrue(nextButton()).isVisible();
        nextButton().click();
        return this;
    }

    public LoginPage fillPasswordInput(String pass){
        assertTrue(passInput()).isVisible();
        passInput().sendKeys(pass);
//        passInput().sendKeys(Keys.ENTER);
        return this;
    }
}
