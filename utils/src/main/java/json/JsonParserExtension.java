package json;

import com.google.gson.Gson;

/**
 * Класс для парсинга Json в объекты
 */
public class JsonParserExtension {
    private static final Gson gson = new Gson();

    public static <T> T parseJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
}
