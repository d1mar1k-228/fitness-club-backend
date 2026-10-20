package com.fitnessclub.fitnessclubbackend.model.user;

public class AdminUser extends User {
    public AdminUser(Long id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email, UserRole.ADMIN);
    }

    @Override
    public String getAccessDescription() {
        return "Полный доступ ко всем функциям системы";
    }
}
