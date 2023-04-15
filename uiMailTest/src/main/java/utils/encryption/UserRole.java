package utils.encryption;

import models.UserRoleImpl;

public enum UserRole implements UserRoleImpl {
    FIRST_USER,
    SECOND_USER;

    @Override
    public String getName() {
        return this.name();
    }
}
