package pages.modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.wrapper.Volodium;
import utils.wrapper.VolodiumElement;

import static utils.elementUtils.GetElement.*;
import static utils.wrapper.matcher.VolodiumElementsMatcher.assertTrue;

/**
 * Класс для работы с модальным окном Новое сообщение
 */
public class NewMessageModal {
    private final VolodiumElement elementPrefix = Volodium.locateX("//*[@role='dialog']");

    //Хидер Новое сообщение
    public WebElement headerTitle() {
        return elementByTextContaining(elementPrefix, "Новое сообщение");
    }

    //Поле Получатели (До нажатия)
    public WebElement consigneeButton() {
        return elementByExactText(elementPrefix, "Получатели");
    }

    //Строка ввода Получатели
    public WebElement consigneeInput() {
        return elementByAriaLabel(elementPrefix, "Строка поиска").findElement(By.xpath(".//input"));
    }

    //Кнопка Кому
    public WebElement toWhomButton() {
        return elementByExactText(elementPrefix, "Кому");
    }

    //Поле Тема
    public WebElement subjectInput(){
        return elementByAriaLabel(elementPrefix, "Тема");
    }

    //Инпут содержания письма
    public WebElement mailTextInput(){
        return elementByAriaLabel(elementPrefix,"div", "Текст письма");
    }

    //Кнопка Отправить
    public WebElement sendButton(){
        return elementByExactText(elementPrefix,"Отправить");
    }

    @Step("Проверить, что хидер Новое сообщение видим")
    public NewMessageModal checkHeaderVisible() {
        assertTrue(headerTitle()).isVisible();
        return this;
    }

    @Step("Заполнить поле Кому/Получатели значением = {text}")
    public NewMessageModal fillConsignee(String text) {
        if (consigneeButton().findElement(By.xpath("./parent::div")).getAttribute("style").equals("'display: none;'"))
            consigneeButton().click();
        assertTrue(toWhomButton()).isVisible();
        consigneeInput().sendKeys(text, Keys.ENTER);
        return this;
    }

    @Step("Заполнить поле тема = {text}")
    public NewMessageModal fillSubject(String text){
        subjectInput().click();
        subjectInput().sendKeys(text);
        return this;
    }

    @Step("Заполнить содержания письма")
    public NewMessageModal fillTextOfMail(String text){
        mailTextInput().click();
        Volodium.sendKeysToActiveElement(text);
        return this;
    }

    @Step("Нажать кнопку отправить")
    public NewMessageModal sendButtonClick(){
        assertTrue(sendButton()).isVisible();
        sendButton().click();
        return this;
    }
}
