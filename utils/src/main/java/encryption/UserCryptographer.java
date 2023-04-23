package encryption;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import files.FilesWorker;
import models.User;
import models.UserRole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static encryption.Cryptographer.decrypt;

/**
 * Класс для расшифровки/дешифровки данных пользователя
 */
public class UserCryptographer {
    public static HashMap<String, User> users = new HashMap<>();
    public static UserCryptographer instance;

    public static UserCryptographer getInstance() {
        if (instance == null) {
            instance = new UserCryptographer();
        }
        return instance;
    }

    public static User getUser(UserRole role) {
        return users.get(role.getName());
    }

    /**
     * Метод для дешифровки пользователей
     *
     * @return -
     */
    public List<User> decryptUsers() {
        File json = new FilesWorker().getFileFromResources("/encryption/users.json");
        try {
            Gson gson = new Gson();
            JsonArray userArray = gson.fromJson(new FileReader(json), JsonObject.class)
                    .getAsJsonArray("data");
            List<User> userList = new ArrayList<>();
            userArray.forEach((it) -> userList.add(gson.fromJson(it, User.class)));
            userList.forEach((it) -> {
                it.setPassword(decrypt(it.getPassword()));
                users.put(it.getRole(), it);
            });

            return userList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
