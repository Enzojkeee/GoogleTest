package util.encryption;

import models.UserRole;

public enum UserRoleApi implements UserRole {
    ADMIN;

    @Override
    public String getName() {
        return this.name();
    }
}
