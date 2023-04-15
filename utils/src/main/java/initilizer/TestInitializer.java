package initilizer;

import encryption.UserCryptographer;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestInitializer implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        UserCryptographer.getInstance().decryptUsers();
    }
}
