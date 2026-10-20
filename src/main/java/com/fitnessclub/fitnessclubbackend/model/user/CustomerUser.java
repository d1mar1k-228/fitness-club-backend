package com.fitnessclub.fitnessclubbackend.model.user;

public class CustomerUser extends User {
    public CustomerUser(Long id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email, UserRole.CUSTOMER);
    }

    @Override
    public String getAccessDescription() {
        return "Доступ к просмотру расписания и своего абонемента";
    }
}