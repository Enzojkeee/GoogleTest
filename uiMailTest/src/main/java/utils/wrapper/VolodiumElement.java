package utils.wrapper;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static utils.driver.WebDriverContainer.INSTANCE;

public class VolodiumElement implements WebElement {
    private final By locator;
    private WebElement webElement;

    public VolodiumElement(By locator) {
        this.locator = locator;
    }

    private VolodiumElement(WebElement webElement) {
        locator = null;
        this.webElement = webElement;
    }

    public WebElement wrap() {
        return webElement;
    }

    /**
     * Производит какое-то действие с ожиданием
     *
     * @param action - действие над элементом
     */
    private void execute(Consumer<WebElement> action) {
        checkWebElement();

        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                action.accept(webElement);
                return;
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        action.accept(webElement);
    }

    /**
     * Производит какое-то действие с ожиданием и возвращает какое-то значение
     *
     * @param action - действие над элементом
     */
    private <T> T execute(Function<WebElement, T> action) {
        checkWebElement();

        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                return action.apply(webElement);
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return action.apply(webElement);
    }

    @Override
    public void click() {
        execute(WebElement::click);
    }

    @Override
    public void submit() {
        execute(WebElement::submit);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        execute((Consumer<WebElement>) (it) -> it.sendKeys(keysToSend));
    }

    @Override
    public void clear() {
        execute(WebElement::clear);
    }

    @Override
    public String getTagName() {
        return execute(WebElement::getTagName);
    }

    @Override
    public String getAttribute(String name) {
        return execute((Function<WebElement, String>) (it) -> it.getAttribute(name));
    }

    @Override
    public boolean isSelected() {
        return execute(WebElement::isSelected);
    }

    @Override
    public boolean isEnabled() {
        return execute(WebElement::isEnabled);
    }

    @Override
    public String getText() {
        return execute(WebElement::getText);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return VolodiumElementsLocator.INSTANCE.findElements(INSTANCE.getRequiredWebDriver(), by);
    }

    @Override
    public WebElement findElement(By by) {
        return VolodiumElementsLocator.INSTANCE.findElement(INSTANCE.getRequiredWebDriver(), by);
    }

    @Override
    public boolean isDisplayed() {
        return execute(WebElement::isDisplayed);
    }

    @Override
    public Point getLocation() {
        return execute(WebElement::getLocation);
    }

    @Override
    public Dimension getSize() {
        return execute(WebElement::getSize);
    }

    @Override
    public Rectangle getRect() {
        return execute(WebElement::getRect);
    }

    @Override
    public String getCssValue(String propertyName) {
        return execute((Function<WebElement, String>) (it) -> it.getCssValue(propertyName));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return execute((Function<WebElement, X>) it -> it.getScreenshotAs(target));
    }

    @Override
    public String getDomProperty(String name) {
        return webElement.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name) {
        return webElement.getDomAttribute(name);
    }

    @Override
    public String getAriaRole() {
        return webElement.getAriaRole();
    }

    @Override
    public String getAccessibleName() {
        return webElement.getAccessibleName();
    }

    @Override
    public SearchContext getShadowRoot() {
        return webElement.getShadowRoot();
    }

    /**
     * Если webElement == null то находит webElement с нужным вэбдрайвером
     */
    private void checkWebElement() {
        if (webElement == null) {
            webElement = VolodiumElementsLocator
                    .INSTANCE.findElement(INSTANCE.getRequiredWebDriver(), Objects.requireNonNull(locator));
        }
    }
}