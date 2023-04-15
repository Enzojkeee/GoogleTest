package utils.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.wrapper.Config;

import javax.annotation.Nonnull;
import java.time.Duration;

/**
 * Класс для создания WebDriver
 */
public class WebDriverFactory {
    @Nonnull
    @SuppressWarnings("all")
    public static WebDriver createWebDriver() {
        switch (Config.CHROME.browser) {
            case CHROME: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (Config.CHROME.headless) options.addArguments("--headless=new");
                options.setPageLoadStrategy(Config.CHROME.pageLoadStrategy);
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--no-sandbox", "--ignore-ssl-errors=yes", "--ignore-certificate-errors");
                WebDriver driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                return driver;
            }
            default: {
                throw new IllegalStateException("Выбран неверный браузер");
            }
        }
    }
}
