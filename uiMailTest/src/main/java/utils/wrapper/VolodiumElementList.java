package utils.wrapper;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class VolodiumElementList implements List<WebElement> {
    private final By locator;
    private List<WebElement> webElements;

    public VolodiumElementList(By locator) {
        this.locator = locator;
    }

    private VolodiumElementList(List<WebElement>webElements) {
        locator = null;
        this.webElements = webElements;
    }

    /**
     * Производит какое-то действие с ожиданием
     * @param action - действие над элементом
     */
    private void execute(Consumer<List<WebElement>> action) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                action.accept(webElements);
                return;
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        action.accept(webElements);
    }

    /**
     * Производит какое-то действие с ожиданием и возвращает какое-то значение
     * @param action - действие над элементом
     */
    private <T> T execute(Function<List<WebElement>, T> action) {
        StopWatch stopWatch = StopWatch.createStarted();
        while (stopWatch.getTime() <= Config.CHROME.actionTimeout) {
            try {
                return action.apply(webElements);
            } catch (Exception e) {
                try {
                    Thread.sleep(Config.CHROME.defaultIterationTimeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return action.apply(webElements);
    }

    @Override
    public int size() {
        return execute((Function<List<WebElement>, Integer>) List::size);
    }

    @Override
    public boolean isEmpty() {
        return execute((Function<List<WebElement>, Boolean>) List::isEmpty);
    }

    @Override
    public boolean contains(Object o) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.contains(o));
    }

    @Override
    public Iterator<WebElement> iterator() {
        return execute((Function<List<WebElement>, Iterator<WebElement>>) List::iterator);
    }

    @Override
    public Object[] toArray() {
        return execute((Function<List<WebElement>, Object[]>) List::toArray);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return execute((Function<List<WebElement>, T[]>) webElements->webElements.toArray(a));
    }

    @Override
    public boolean add(WebElement webElement) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.add(webElement));
    }

    @Override
    public boolean remove(Object o) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.remove(o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.containsAll(c));
    }

    @Override
    public boolean addAll(Collection<? extends WebElement> c) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.addAll(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends WebElement> c) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.addAll(index, c));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.removeAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return execute((Function<List<WebElement>, Boolean>) webElements->webElements.removeAll(c));
    }

    @Override
    public void clear() {
        execute((Consumer<List<WebElement>>) List::clear);
    }

    @Override
    public WebElement get(int index) {
        return execute((Function<List<WebElement>, WebElement>) webElements->webElements.get(index));
    }

    @Override
    public WebElement set(int index, WebElement element) {
        return execute((Function<List<WebElement>, WebElement>) webElements->webElements.set(index, element));
    }

    @Override
    public void add(int index, WebElement element) {
        execute((Consumer<List<WebElement>>) webElements -> webElements.add(index, element));
    }

    @Override
    public WebElement remove(int index) {
        return execute((Function<List<WebElement>, WebElement>) webElements->webElements.remove(index));
    }

    @Override
    public int indexOf(Object o) {
        return execute((Function<List<WebElement>, Integer>) webElements->webElements.indexOf(o));
    }

    @Override
    public int lastIndexOf(Object o) {
        return execute((Function<List<WebElement>, Integer>) webElements->webElements.lastIndexOf(o));
    }

    @Override
    public ListIterator<WebElement> listIterator() {
        return execute((Function<List<WebElement>, ListIterator<WebElement>>) List::listIterator);
    }

    @Override
    public ListIterator<WebElement> listIterator(int index) {
        return execute((Function<List<WebElement>, ListIterator<WebElement>>) webElements->webElements.listIterator(index));
    }

    @Override
    public List<WebElement> subList(int fromIndex, int toIndex) {
        return execute((Function<List<WebElement>, List<WebElement>>) webElements->webElements.subList(fromIndex, toIndex));
    }
}
