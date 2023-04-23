package utils.encryption;

import models.UserRole;

public enum UserRoleUi implements UserRole {
    FIRST_USER,
    SECOND_USER;

    @Override
    public String getName() {
        return this.name();
    }
}
