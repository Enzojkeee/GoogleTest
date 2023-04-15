package utils.driver;

import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.currentThread;

public enum WebDriverContainer {
    INSTANCE;

    private final Map<Thread, WebDriver> webDriverHolder = new ConcurrentHashMap<>();
    private final Queue<Thread> allDriverThreads = new ConcurrentLinkedQueue<>();
    private volatile boolean cleanupThreadStarted = false;

    /**
     * Возвращает вэбдрайвер принадлежащий потоку
     */
    public WebDriver getRequiredWebDriver() {
        final Thread th = currentThread();
        return Optional.ofNullable(webDriverHolder.get(th))
                .orElseThrow(() -> new IllegalStateException("Не найден вебдравер в текущем потоке " + th.getId()));
    }

    /**
     * Возвращает существующий вебдрайвер из потока или создает новый, если его не существует
     */
    public WebDriver getOrInitWebDriver() {
        final Thread thread = currentThread();
        if (!webDriverHolder.containsKey(thread)) {
            WebDriver driver = WebDriverFactory.createWebDriver();
            webDriverHolder.put(thread, driver);
            markForAutoClose(thread);
        }
        return getRequiredWebDriver();
    }

    /**
     * Закрывает вэбдрайвер в текущем потоке
     */
    public void closeWebDriver() {
        final Thread th = currentThread();
        WebDriver webDriver = getWebDriver(th);
        if (webDriver != null) {
            webDriver.quit();
        }
        webDriverHolder.remove(th);
    }

    /**
     * Получает вэбдрайвер из текущего потока
     * @param thread - поток
     * @return - вэбдрайвер
     */
    public WebDriver getWebDriver(Thread thread) {
        return webDriverHolder.get(thread);
    }

    /**
     * Метод для очистки потоков от драйверов
     * @param thread
     */
    private void markForAutoClose(Thread thread) {
        allDriverThreads.add(thread);
        if (!cleanupThreadStarted) {
            synchronized (this) {
                if (!cleanupThreadStarted) {
                    new CloseDriverThread(allDriverThreads).start();
                    cleanupThreadStarted = true;
                }
            }
        }
    }
}
