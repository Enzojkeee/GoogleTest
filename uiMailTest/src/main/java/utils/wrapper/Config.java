package utils.wrapper;


import org.openqa.selenium.PageLoadStrategy;
import utils.browsers.Browsers;

/**
 * Конфиги для WebDrivera
 */
public enum Config {
    CHROME(Browsers.CHROME);

    Config(Browsers browser) {
        this.browser = browser;
    }

    public final Browsers browser;
    public final long actionTimeout = 10000L;
    public final int defaultIterationTimeout = 250;
    public final boolean headless = false;
    public final PageLoadStrategy pageLoadStrategy = PageLoadStrategy.EAGER;
}
