package files;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Класс для работы с файлами
 */
public class FilesWorker {
    /**
     * Находит файл в папке с ресурсами из которого вызывается метод
     * @param fileName - имя файла
     * @return - File по совпадению имени
     */
    public File getFileFromResources(String fileName) {
        try {
            return new File(Objects.requireNonNull(FilesWorker.class.getResource(fileName)).toURI());
        } catch (URISyntaxException e) {
            System.err.println(fileName + " файл не найден в папке с ресурсами");
            e.printStackTrace();
        }
        return null;
    }
}